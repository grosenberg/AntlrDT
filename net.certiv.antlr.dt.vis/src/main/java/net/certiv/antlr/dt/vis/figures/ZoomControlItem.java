/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.vis.figures;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.zest.core.viewers.ZoomContributionViewItem;

import net.certiv.antlr.dt.vis.graph.EnhGraphViewer;
import net.certiv.antlr.dt.vis.graph.IAdjustableViewPart;
import net.certiv.common.util.Reflect;

public class ZoomControlItem extends ZoomContributionViewItem {

	public ZoomControlItem(IAdjustableViewPart editor) {
		super(editor);

		EnhGraphViewer viewer = (EnhGraphViewer) editor.getZoomableViewer();
		// zoomLevels = zoomManager.getZoomLevelsAsText();

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
		combo.pack();
		item.setControl(combo);
		item.setWidth(combo.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
		zoomChanged(100);
	}
}
