package net.certiv.antlrdt.ui.graph.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import net.certiv.antlrdt.core.parser.PathsNode;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.AntlrImageManager;
import net.certiv.antlrdt.ui.graph.paths.PathHelper;
import net.certiv.antlrdt.ui.graph.paths.PathsEditor;

public class PathsAddSubAction extends Action {

	private PathsEditor editor;
	private PathHelper helper;

	public PathsAddSubAction(PathsEditor editor, PathHelper helper) {
		this.editor = editor;
		this.helper = helper;

		AntlrImageManager imgMgr = AntlrDTUI.getDefault().getImageManager();
		setText("Add Sub Paths");
		setToolTipText("Add sub paths to this path node");
		setImageDescriptor(imgMgr.getDescriptor(imgMgr.IMG_ACTION_ADD_PATHS));
		setId(PathsEditor.ADD_SUB_PATHS);
	}

	@Override
	public void run() {
		List<PathsNode> selected = editor.getCurrentSelectedNodes();
		if (selected != null) {
			for (PathsNode select : selected) {
				helper.addSubPaths(select.getRuleName());
			}
		}
		editor.refresh();
		editor.setFocus();
	}
}
