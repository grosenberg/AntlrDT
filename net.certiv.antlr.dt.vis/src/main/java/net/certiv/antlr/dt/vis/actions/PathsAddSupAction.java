package net.certiv.antlr.dt.vis.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.model.PathNode;
import net.certiv.antlr.dt.vis.views.paths.PathOp;
import net.certiv.antlr.dt.vis.views.paths.PathView;

public class PathsAddSupAction extends Action {

	private PathView view;

	public PathsAddSupAction(PathView view) {
		this.view = view;

		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		setText("Add Super Paths");
		setToolTipText("Add super paths to this path node");
		setImageDescriptor(imgMgr.getDescriptor(imgMgr.IMG_ACTION_ADD_CALLERS));
		setId(PathView.ADD_SUP_PATHS);
	}

	@Override
	public void run() {
		List<PathNode> selected = view.getCurrentSelectedNodes();
		if (selected != null) {
			for (PathNode select : selected) {
				view.update(PathOp.ADD_CALLERS, null, select.getNodeName());
			}
		}
	}
}
