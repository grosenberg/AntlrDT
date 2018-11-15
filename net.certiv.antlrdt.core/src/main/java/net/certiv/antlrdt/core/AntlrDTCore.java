package net.certiv.antlrdt.core;

import org.osgi.framework.BundleContext;

import net.certiv.antlrdt.core.parser.AntlrSourceParser;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.parser.DslSourceParser;

public class AntlrDTCore extends DslCore {

	public static final String[] EXTENSIONS = new String[] { "g4" };

	// Should be unique, lower case, single word;
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
	public DslSourceParser createSourceParser(ICodeUnit unit, String contentType) {
		if (DSL_NAME.equals(contentType) || getContentTypeId().equals(contentType)) {
			return new AntlrSourceParser(unit.getParseRecord());
		}
		return null;
	}

	/** For Maven located grammars. */
	@Override
	public String[][] getExtraSourceRoots() {
		return new String[][] { { "src/main/antlr4" }, { "src/main/antlr4/import" } };
	}

	@Override
	public String[] getDslFileExtensions() {
		return EXTENSIONS;
	}
}
