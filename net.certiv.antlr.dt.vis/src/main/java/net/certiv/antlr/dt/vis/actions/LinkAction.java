package net.certiv.antlr.dt.vis.actions;

import static org.eclipse.ui.IWorkbenchCommandConstants.NAVIGATE_TOGGLE_LINK_WITH_EDITOR;

import org.eclipse.jface.action.Action;

import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.VisUI;
import net.certiv.antlr.dt.vis.graph.IAdjustableViewPart;

public class LinkAction extends Action {

	private IAdjustableViewPart view;

	public LinkAction(IAdjustableViewPart view) {
		this.view = view;

		ImageManager imgMgr = VisUI.getDefault().getImageManager();

		setText("Link with Editor");
		setToolTipText("Link with Editor");
		setActionDefinitionId(NAVIGATE_TOGGLE_LINK_WITH_EDITOR);
		setChecked(view.isLinked());
		setImageDescriptor(imgMgr.getDescriptor(imgMgr.LINK));
	}

	@Override
	public void run() {
		boolean state = isChecked();
		view.setLinked(state);
	}
}
