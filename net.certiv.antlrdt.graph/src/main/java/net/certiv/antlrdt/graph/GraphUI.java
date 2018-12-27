package net.certiv.antlrdt.graph;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

public class GraphUI extends AbstractUIPlugin {

	private static GraphUI plugin;

	public GraphUI() {
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

	public static GraphUI getDefault() {
		return plugin;
	}
}
