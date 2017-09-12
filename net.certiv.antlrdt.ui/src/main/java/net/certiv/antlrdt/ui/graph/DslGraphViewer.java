/*******************************************************************************
 * Copyright 2005-2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph;

import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;

import net.certiv.dsl.core.util.Reflect;

public class DslGraphViewer extends GraphViewer implements IDslGraphViewer {

	public DslGraphViewer(Composite parent, int style) {
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
				GC gc = event.gc;
				setAntialias(gc, SWT.ON);
			}
		});

		setControl(graph);
		graph.getLightweightSystem().setEventDispatcher(new EnhSWTEventDispatcher());
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
		LightweightSystem lws = graph.getLightweightSystem();
		return (EnhSWTEventDispatcher) Reflect.get(lws, "dispatcher", true);
	}

	@Override
	public EnhTipHelper getTipHelper() {
		return getEventDispatcher().getEnhTipHelper();
	}

	public void setZoomLevels(double[] zoomLevels) {
		// ////////////////////////////////////////////////////////////////////////////////
		// TODO: fix when Bug 247788 is addressed
		// Necessary hack to circumvent getZoomManager being overly restricted/protected.
		// So, instead of this:
		// getZoomManager().setZoomLevels(zoomLevels);
		// have to do this:
		Object zMgr = Reflect.invokeSuperDeclared(this, "getZoomManager", null, null);
		Class<?>[] params = new Class[] { zoomLevels.getClass() };
		Object[] args = new Object[] { zoomLevels };
		Reflect.invoke(zMgr, "setZoomLevels", params, args);
		// ////////////////////////////////////////////////////////////////////////////////
	}

	public void setZoomAsText(String text) {
		Object zMgr = Reflect.invokeSuperDeclared(this, "getZoomManager", null, null);
		Class<?>[] params = new Class[] { text.getClass() };
		Object[] args = new Object[] { text };
		Reflect.invoke(zMgr, "setZoomAsText", params, args);
	}

	public void zoomIn() {
		Object zMgr = Reflect.invokeSuperDeclared(this, "getZoomManager", null, null);
		Class<?>[] params = new Class[] {};
		Object[] args = new Object[] {};
		Reflect.invoke(zMgr, "zoomIn", params, args);
	}

	public void zoomOut() {
		Object zMgr = Reflect.invokeSuperDeclared(this, "getZoomManager", null, null);
		Class<?>[] params = new Class[] {};
		Object[] args = new Object[] {};
		Reflect.invoke(zMgr, "zoomOut", params, args);
	}
}
