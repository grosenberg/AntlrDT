package net.certiv.antlrdt.ui.graph.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import net.certiv.antlrdt.core.parser.PathsNode;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.AntlrImageManager;
import net.certiv.antlrdt.ui.graph.paths.PathHelper;
import net.certiv.antlrdt.ui.graph.paths.PathsEditor;

public class PathsAddSupAction extends Action {

	private PathsEditor editor;
	private PathHelper helper;

	public PathsAddSupAction(PathsEditor editor, PathHelper helper) {
		this.editor = editor;
		this.helper = helper;

		AntlrImageManager imgMgr = AntlrDTUI.getDefault().getImageManager();
		setText("Add Super Paths");
		setToolTipText("Add super paths to this path node");
		setImageDescriptor(imgMgr.getDescriptor(imgMgr.IMG_ACTION_ADD_CALLERS));
		setId(PathsEditor.ADD_SUP_PATHS);
	}

	@Override
	public void run() {
		List<PathsNode> selected = editor.getCurrentSelectedNodes();
		if (selected != null) {
			for (PathsNode select : selected) {
				helper.addSupPaths(select.getRuleName());
			}
		}
		editor.refresh();
		editor.setFocus();
	}
}
