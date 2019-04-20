package net.certiv.antlrdt.vis.views;

import org.eclipse.draw2d.SWTEventDispatcher;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.widgets.Graph;

import net.certiv.dsl.core.util.Reflect;

public class EnhGraphViewer extends GraphViewer implements IEnhGraphViewer {

	private SWTEventDispatcher dispatcher;
	// private EnhTipHelper helper;

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

		dispatcher = new SWTEventDispatcher();
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
	public SWTEventDispatcher getEventDispatcher() {
		return dispatcher;
	}

	// @Override
	// public EnhTipHelper getCoolTipHelper() {
	// return helper;
	// }

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
