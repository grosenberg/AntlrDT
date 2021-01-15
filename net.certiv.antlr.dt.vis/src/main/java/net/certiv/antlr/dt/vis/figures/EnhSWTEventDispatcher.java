package net.certiv.antlr.dt.vis.figures;

import org.eclipse.draw2d.SWTEventDispatcher;
import org.eclipse.draw2d.ToolTipHelper;

import net.certiv.antlr.dt.vis.graph.EnhGraphViewer;
import net.certiv.dsl.core.util.Reflect;

/** Enables use of an enhanced tip helper. */
public class EnhSWTEventDispatcher extends SWTEventDispatcher {

	private static final String TT_HELPER = "toolTipHelper";
	private EnhGraphViewer viewer;

	public EnhSWTEventDispatcher(EnhGraphViewer viewer) {
		super();
		this.viewer = viewer;
	}

	/** Returns the ToolTipHelper used to display tooltips on hover events. */
	@Override
	protected ToolTipHelper getToolTipHelper() {
		ToolTipHelper helper = (ToolTipHelper) Reflect.getSuper(this, TT_HELPER);
		if (helper == null || !(helper instanceof EnhTipHelper)) {
			Reflect.setSuper(this, TT_HELPER, new EnhTipHelper(viewer, control));
		}
		return super.getToolTipHelper();
	}

	public EnhTipHelper getEnhTipHelper() {
		return (EnhTipHelper) getToolTipHelper();
	}
}
