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
package net.certiv.antlr.dt.vis.graph;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;

import net.certiv.antlr.dt.vis.figures.EnhSWTEventDispatcher;
import net.certiv.antlr.dt.vis.figures.EnhTipHelper;
import net.certiv.common.util.Reflect;

public class EnhGraphViewer extends GraphViewer implements IEnhGraphViewer {

	private EnhSWTEventDispatcher dispatcher;
	private EnhTipHelper helper;

	public EnhGraphViewer(Composite parent, int style) {
		super(parent, style);
		Graph graph = new Graph(parent, style) {

			@Override
			public Point computeSize(int hint, int hint2, boolean changed) {
				return new Point(0, 0);
			}
		};
		graph.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent event) {
				setAntialias(event.gc, SWT.ON);
			}
		});
		setControl(graph);

		dispatcher = new EnhSWTEventDispatcher(this);
		graph.getLightweightSystem().setEventDispatcher(dispatcher);
	}

	public static void setAntialias(GC gc, int style) {
		if (!gc.getAdvanced()) {
			gc.setAdvanced(true);
			if (!gc.getAdvanced()) return;
		}
		gc.setAntialias(style);
		gc.setTextAntialias(style);
		gc.setInterpolation((style == SWT.ON) ? SWT.HIGH : SWT.DEFAULT);
	}

	@Override
	public EnhSWTEventDispatcher getEventDispatcher() {
		return dispatcher;
	}

	@Override
	public EnhTipHelper getCoolTipHelper() {
		return helper;
	}

	public void setZoomLevels(double[] zoomLevels) {
		Object zMgr = Reflect.invokeSuperDeclared(this, "getZoomManager", null, null);
		Class<?>[] params = new Class[] { zoomLevels.getClass() };
		Object[] args = new Object[] { zoomLevels };
		Reflect.invoke(zMgr, "setZoomLevels", params, args);
	}

	public void setZoomAsText(String text) {
		Object zMgr = Reflect.invokeSuperDeclared(this, "getZoomManager", null, null);
		Class<?>[] params = new Class[] { text.getClass() };
		Object[] args = new Object[] { text };
		Reflect.invoke(zMgr, "setZoomAsText", params, args);
	}

	public void zoomIn() {
		Object zMgr = Reflect.invokeSuperDeclared(this, "getZoomManager", null, null);
		Reflect.invoke(zMgr, "zoomIn", Reflect.NoParams, Reflect.NoArgs);
	}

	public void zoomOut() {
		Object zMgr = Reflect.invokeSuperDeclared(this, "getZoomManager", null, null);
		Reflect.invoke(zMgr, "zoomOut", Reflect.NoParams, Reflect.NoArgs);
	}
}
