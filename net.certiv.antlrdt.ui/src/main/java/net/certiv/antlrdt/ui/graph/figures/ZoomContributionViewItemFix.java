package net.certiv.antlrdt.ui.graph.figures;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;

import net.certiv.antlrdt.ui.graph.DslGraphViewer;
import net.certiv.antlrdt.ui.graph.IZoomableEditor;
import net.certiv.dsl.core.util.Reflect;

public class ZoomContributionViewItemFix extends ZoomContributionViewItem {

	public ZoomContributionViewItemFix(IZoomableEditor editor) {
		super(editor);

		DslGraphViewer viewer = (DslGraphViewer) editor.getZoomableViewer();
		Object zMgr = Reflect.invokeSuperDeclared(viewer, "getZoomManager", null, null);
		String[] levels = (String[]) Reflect.invoke(zMgr, "getZoomLevelsAsText", null, null);
		Reflect.setSuper(this, "zoomLevels", levels);
	}

	@Override
	public void fill(ToolBar parent, int index) {
		Class<?>[] params = new Class[] { Composite.class };
		Object[] args = new Object[] { parent };
		Object c = Reflect.invokeSuperDeclared(this, "createCombo", params, args);

		ToolItem item = new ToolItem(parent, SWT.SEPARATOR);
		Combo combo = (Combo) c;
		item.setControl(combo);

		combo.pack();
		item.setWidth(combo.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
		zoomChanged(100);
	}
}
