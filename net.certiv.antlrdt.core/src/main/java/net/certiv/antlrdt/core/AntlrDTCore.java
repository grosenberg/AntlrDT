package net.certiv.antlrdt.core;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.osgi.framework.BundleContext;

import net.certiv.antlrdt.core.parser.AntlrDTSourceParser;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.parser.DslSourceParser;

public class AntlrDTCore extends DslCore {

	public static final String[] EXTENSIONS = new String[] { "g4" };

	// Should be unique, lower case, single word;
	// also, unique parser language type
	public static final String DSL_NAME = "antlr";

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
		if (DSL_NAME.equals(type) || getContentTypeId().equals(type)) {
			return new AntlrDTSourceParser();
		}
		return null;
	}

	@Override
	public String getProblemMakerId(String type) {
		return getPluginId() + String.format(".%s_marker", type);
	}

	/** Expected prefix for Maven located grammars. */
	@Override
	public String[] getSpecialSourceRoots() {
		return new String[] { "src/main/antlr4" };
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
