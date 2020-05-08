package net.certiv.antlr.dt.vis;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class VisUI extends AbstractUIPlugin {

	public static final String PLUGIN_ID = "net.certiv.antlr.dt.vis"; //$NON-NLS-1$

	private static VisUI plugin;

	public VisUI() {
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

	public static VisUI getDefault() {
		return plugin;
	}
}
