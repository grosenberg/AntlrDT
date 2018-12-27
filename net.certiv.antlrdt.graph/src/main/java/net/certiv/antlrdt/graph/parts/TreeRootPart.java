package net.certiv.antlrdt.graph.parts;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.mvc.fx.models.GridModel;
import org.eclipse.gef.mvc.fx.parts.LayeredRootPart;
import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.graph.view.tree.TreeView;

public class TreeRootPart extends LayeredRootPart {

	@Override
	protected void doActivate() {
		getVisual().getScene().getStylesheets().add(TreeView.StylesCss);
		getContentLayer().getStyleClass().add("background");

		boolean visibility = AntlrDTCore.getDefault().getPrefsManager().getBoolean(PrefsKey.PT_SHOW_GRID);
		getViewer().getAdapter(GridModel.class).setShowGrid(visibility);

		super.doActivate();
	}

	@Override
	protected void doDeactivate() {
		super.doDeactivate();
		getVisual().getScene().getStylesheets().remove(TreeView.StylesCss);
	}

	public InfiniteCanvas getCanvas() {
		return ((InfiniteCanvasViewer) getViewer()).getCanvas();
	}
}
