package net.certiv.antlr.dt.vis.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.model.PathNode;
import net.certiv.antlr.dt.vis.views.paths.PathOp;
import net.certiv.antlr.dt.vis.views.paths.PathView;

public class PathsAddSubAction extends Action {

	private PathView view;

	public PathsAddSubAction(PathView view) {
		this.view = view;

		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		setText("Add Sub Paths");
		setToolTipText("Add sub paths to this path node");
		setImageDescriptor(imgMgr.getDescriptor(imgMgr.IMG_ACTION_ADD_PATHS));
		setId(PathView.ADD_SUB_PATHS);
	}

	@Override
	public void run() {
		List<PathNode> selected = view.getCurrentSelectedNodes();
		if (selected != null) {
			for (PathNode select : selected) {
				view.update(PathOp.ADD_CALLEES, null, select.getNodeName());
			}
		}
	}
}
