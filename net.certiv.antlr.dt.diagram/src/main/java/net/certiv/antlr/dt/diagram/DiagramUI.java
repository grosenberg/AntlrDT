package net.certiv.antlr.dt.diagram;

import org.osgi.framework.BundleContext;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.ui.plugin.AbstractUIPlugin;

public class DiagramUI extends AbstractUIPlugin {

	private static DiagramUI plugin;

	public DiagramUI() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static DiagramUI getDefault() {
		return plugin;
	}

	public static IDialogSettings getSettings() {
		return plugin.getDialogSettings();
	}
}
