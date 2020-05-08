package net.certiv.antlr.dt.core;

import org.osgi.framework.BundleContext;

import net.certiv.antlr.dt.core.lang.AntlrLangManager;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.lang.LanguageManager;

public class AntlrCore extends DslCore {

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
	public LanguageManager getLangManager() {
		if (langMgr == null) {
			langMgr = new AntlrLangManager(this);
		}
		return langMgr;
	}
}
