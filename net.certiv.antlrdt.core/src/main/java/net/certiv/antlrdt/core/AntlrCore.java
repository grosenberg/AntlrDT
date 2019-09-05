package net.certiv.antlrdt.core;

import org.osgi.framework.BundleContext;

import net.certiv.antlrdt.core.parser.AntlrSourceParser;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.lang.LangManager;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.parser.DslSourceParser;

public class AntlrCore extends DslCore {

	// Should be unique, lower case, single word;
	public static final String DSL_NAME = "antlr";

	private static AntlrCore plugin;

	private AntlrLangManager langMgr;

	public AntlrCore() {
		super();
	}

	public static AntlrCore getDefault() {
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

	@Override
	public LangManager getLangManager() {
		if (langMgr == null) {
			langMgr = new AntlrLangManager(this);
		}
		return langMgr;
	}
}
