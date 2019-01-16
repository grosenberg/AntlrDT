package net.certiv.antlrdt.graph;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class GraphUI extends AbstractUIPlugin {

	private static GraphUI plugin;
	private EventManager evtMgr;

	public GraphUI() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		evtMgr = new EventManager();
		evtMgr.start();
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		evtMgr.stop();
		plugin = null;
		super.stop(context);
	}

	public static GraphUI getDefault() {
		return plugin;
	}

	public static IDialogSettings getSettings() {
		return plugin.getDialogSettings();
	}

	public EventManager getEventManager() {
		return evtMgr;
	}
}
