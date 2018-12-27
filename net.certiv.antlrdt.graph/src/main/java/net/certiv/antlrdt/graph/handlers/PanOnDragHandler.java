package net.certiv.antlrdt.graph.handlers;

import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnDragHandler;
import org.eclipse.gef.mvc.fx.policies.ViewportPolicy;

public class PanOnDragHandler extends AbstractHandler implements IOnDragHandler {

	private boolean initd;

	@Override
	public void startDrag(MouseEvent e) {
		initd = false;
	}

	@Override
	public void drag(MouseEvent e, Dimension delta) {
		if (delta.height != 0 || delta.width != 0) {
			if (!initd) {
				init(getViewportPolicy());
				initd = true;
			}
			getViewportPolicy().scroll(false, delta.getWidth(), delta.getHeight());
		}
	}

	@Override
	public void endDrag(MouseEvent e, Dimension delta) {
		if (delta.height != 0 || delta.width != 0) {
			commit(getViewportPolicy());
		}
	}

	@Override
	public void abortDrag() {
		rollback(getViewportPolicy());
	}

	@Override
	public boolean showIndicationCursor(KeyEvent event) {
		return false;
	}

	@Override
	public boolean showIndicationCursor(MouseEvent event) {
		return false;
	}

	@Override
	public void hideIndicationCursor() {}

	private ViewportPolicy getViewportPolicy() {
		return getHost().getRoot().getAdapter(ViewportPolicy.class);
	}
}
