package net.certiv.antlrdt.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.BundleContext;

import net.certiv.antlrdt.core.parser.AntlrDTSourceParser;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.parser.DslSourceParser;

public class AntlrDTCore extends DslCore {

	public static final String[] EXTENSIONS = new String[] { "g4" };

	// unique parser language types
	public static final String ANTLR = "antlr";

	private static AntlrDTCore plugin;

	public AntlrDTCore() {
		super();
	}

	public static AntlrDTCore getDefault() {
		return plugin;
	}

	@Override
	public DslCore getDslCore() {
		return getDefault();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		plugin = this;
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	@Override
	public String getPluginId() {
		return getDefault().getBundle().getSymbolicName();
	}

	@Override
	public DslSourceParser createSourceParser(String type) {
		switch (type) {
			case ANTLR:
				return new AntlrDTSourceParser();
			default:
				return null;
		}
	}

	@Override
	public String getProblemMakerId(String type) {
		return getPluginId() + String.format(".%s_marker", type);
	}

	@Override
	public String[] getDslFileExtensions() {
		return EXTENSIONS;
	}

	@Override
	public IPath convertImport(IPath container, String name) {
		IPath path = new Path(name);
		String ext = path.getFileExtension();
		if (ext == null) {
			path = path.append("." + EXTENSIONS[0]);
		}
		if (!path.isAbsolute() && container != null) {
			path = container.append(path);
		}
		return path;
	}
}
