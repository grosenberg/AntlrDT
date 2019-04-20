package net.certiv.antlr.dt.vis;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class AntlrVis implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		AntlrVis.context = bundleContext;
	}

	public void stop(BundleContext bundleContext) throws Exception {
		AntlrVis.context = null;
	}

}
