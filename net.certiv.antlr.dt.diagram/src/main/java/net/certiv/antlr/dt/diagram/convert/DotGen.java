/*******************************************************************************
 * Copyright (c) 2016 - 2020 Certiv Analytics.
 * All rights reserved.
 *
 * This program and theaccompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this
 * distribution, and is available athttp://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package net.certiv.antlr.dt.diagram.convert;

import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.osgi.framework.Bundle;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Platform;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.diagram.preferences.Prefs;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.core.util.FileUtils;
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.core.util.exec.Cmd;
import net.certiv.dsl.ui.DslUI;

public class DotGen {

	private static final String NL = "\\R";
	private static final String DOC = "\\<\\!DOC.+?\\>";
	private static final String BEG_DIV = "<div class=\"dot\">";
	private static final String END_DIV = "</div>" + Strings.EOL;

	private static final String[] DOTOPS = new String[] { Strings.EMPTY, "-Tsvg" };
	private static final Map<Integer, String> cache = new LinkedHashMap<>(20);

	private final String pluginId;
	private final PrefsManager store;

	public DotGen() {
		pluginId = AntlrUI.getDefault().getPluginId();
		store = AntlrCore.getDefault().getPrefsManager();
	}

	/**
	 * Return an HTML document of the given kind containing the given DOT content.
	 *
	 * @param item defines the intended use: export, the embedded view, or minimal
	 */
	public String getHtml(Kind kind, String content, String base, IPath pathname) {
		StringBuilder sb = new StringBuilder();
		switch (kind) {
			case EXPORT:
				sb.append("<html><head>" + Strings.EOL);
				sb.append(FileUtils.fromBundle(pluginId, "html/meta.html") + Strings.EOL);
				sb.append("<style media=\"screen\" type=\"text/css\">" + Strings.EOL);
				sb.append(getStyle(pathname) + Strings.EOL);
				sb.append("</style>" + Strings.EOL);
				sb.append("</head><body>" + Strings.EOL);
				sb.append(gen(content) + Strings.EOL);
				sb.append("</body></html>");
				break;

			case MIN:
				sb.append("<html><head>" + Strings.EOL);
				sb.append("</head><body>" + Strings.EOL);
				sb.append(gen(content) + Strings.EOL);
				sb.append("</body></html>");
				break;

			case VIEW:
				String preview = FileUtils.fromBundle(pluginId, "html/preview.html");
				preview = preview.replaceFirst("%path%", base);
				sb.append(preview.replaceFirst("%styles%", getStyle(pathname)));
				break;

			case UPDATE:
				sb.append(gen(content) + Strings.EOL);
				break;
		}
		return sb.toString();
	}

	// path is the searchable base for the style to use; returns the content
	private String getStyle(IPath path) {
		try {
			URL url = findStyle(path);
			return FileUtils.readFromStream(url.openStream());
		} catch (Exception e) {
			Log.error(this, "Failed reading stylesheet", e);
		}
		return Strings.EMPTY;
	}

	private URL findStyle(IPath path) throws Exception {
		// String builtinCss = store.getString(Prefs.EDITOR_PREVIEW_INTERNAL_DIR);
		// if (!builtinCss.isEmpty()) {
		// try {
		// URI uri = new URI(builtinCss.replace(Editor.DOT_CSS, Editor.DOT_MIN_CSS));
		// URL url = FileLocator.toFileURL(uri.toURL());
		// File file = URIUtil.toFile(URIUtil.toURI(url));
		// if (file.isFile()) return url;
		// } catch (URISyntaxException e) {
		// MessageDialog.openInformation(null, "Default CSS from bundle", builtinCss);
		// }
		// }

		// read 'preview/diagram.css' from the bundle
		Bundle bundle = Platform.getBundle(DslUI.PLUGIN_ID);
		URL url = FileLocator.find(bundle, new Path(Prefs.PREVIEW).append(Prefs.DIAGRAM_CSS), null);
		url = FileLocator.toFileURL(url);
		return url;
	}

	// private String gen(List<String> lines) {
	// return gen(String.join(Strings.EOL, lines));
	// }

	private String gen(String data) {
		String cmd = store.getString(Prefs.DIAGRAM_DOT_PROGRAM);
		if (data.trim().isEmpty() || cmd.trim().isEmpty()) return Strings.EMPTY;

		// return cached value, if present
		int key = data.hashCode();
		String value = cache.get(key);
		if (value != null) return value;

		// generate a new value by executing dot
		String[] args = DOTOPS;
		args[0] = cmd;

		StringBuilder out = new StringBuilder(BEG_DIV);
		out.append(Cmd.process(args, null, data).replaceAll(NL, Strings.EMPTY).replaceFirst(DOC, Strings.EMPTY));
		out.append(END_DIV);
		value = out.toString();

		// update cache if valid value
		if (value != null && !value.trim().isEmpty()) {
			cache.put(key, value);
		} else {
			Log.error(DotGen.class, "Dot created no output for:\n %s", data);
		}
		return value;
	}
}
