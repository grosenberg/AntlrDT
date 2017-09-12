package net.certiv.antlrdt.ui.graph;

import org.eclipse.draw2d.SWTEventDispatcher;
import org.eclipse.draw2d.ToolTipHelper;

import net.certiv.dsl.core.util.Reflect;

/** Enables use of an enhanced tip helper. */
public class EnhSWTEventDispatcher extends SWTEventDispatcher {

	/** Returns the ToolTipHelper used to display tooltips on hover events. */
	protected ToolTipHelper getToolTipHelper() {
		ToolTipHelper helper = (ToolTipHelper) Reflect.getSuper(this, "toolTipHelper");
		if (helper == null || !(helper instanceof EnhTipHelper)) {
			Reflect.setSuper(this, "toolTipHelper", new EnhTipHelper(control));
		}
		return super.getToolTipHelper();
	}

	public EnhTipHelper getEnhTipHelper() {
		return (EnhTipHelper) getToolTipHelper();
	}
}
