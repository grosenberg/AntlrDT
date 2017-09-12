package net.certiv.antlrdt.ui.graph.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import net.certiv.antlrdt.core.parser.PathsNode;
import net.certiv.antlrdt.ui.AntlrDTImages;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.graph.paths.PathHelper;
import net.certiv.antlrdt.ui.graph.paths.PathsEditor;

public class PathsAddAction extends Action {

	private PathsEditor editor;
	private PathHelper helper;

	public PathsAddAction(PathsEditor editor, PathHelper helper) {
		this.editor = editor;
		this.helper = helper;

		setText("Add SubPaths");
		setToolTipText("Add subpaths to this path node");

		AntlrDTImages imageProvider = AntlrDTUI.getDefault().getImageProvider();
		setImageDescriptor(imageProvider.IMG_ACTION_ADD_PATHS);
		setId(PathsEditor.ADD_PATHS);
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
