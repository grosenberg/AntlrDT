package net.certiv.antlrdt.vis.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.antlrdt.vis.views.IAdjustableViewPart;
import net.certiv.antlrdt.vis.views.parse.TreeView;

public class LayoutToolbarGroup extends ActionGroup {

	private IAdjustableViewPart view;

	public LayoutToolbarGroup(IAdjustableViewPart view) {
		this.view = view;
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		MenuManager subMenu = new MenuManager("Layouts");
		menu.appendToGroup(TreeView.GROUP_LAYOUT_TOOLBAR, subMenu);

		ImageManager imgr = AntlrUI.getDefault().getImageManager();

		Action action = new LayoutAction(Layout.HTREE);
		action.setImageDescriptor(imgr.getDescriptor(imgr.IMG_LAYOUT_TREE_HORIZ));
		subMenu.add(action);

		action = new LayoutAction(Layout.VTREE);
		action.setImageDescriptor(imgr.getDescriptor(imgr.IMG_LAYOUT_TREE));
		subMenu.add(action);

		action = new LayoutAction(Layout.HFLOW);
		action.setImageDescriptor(imgr.getDescriptor(imgr.IMG_LAYOUT_CALL));
		subMenu.add(action);

		action = new LayoutAction(Layout.CALL);
		action.setImageDescriptor(imgr.getDescriptor(imgr.IMG_LAYOUT_GRAPHFLOW));
		subMenu.add(action);

		subMenu.add(new Separator());

		action = new LayoutAction(Layout.SPRING);
		action.setImageDescriptor(imgr.getDescriptor(imgr.IMG_LAYOUT_SPRING));
		subMenu.add(action);

		action = new LayoutAction(Layout.RADIAL);
		action.setImageDescriptor(imgr.getDescriptor(imgr.IMG_LAYOUT_RADIAL));
		subMenu.add(action);

		action = new LayoutAction(Layout.GRID);
		action.setImageDescriptor(imgr.getDescriptor(imgr.IMG_LAYOUT_GRID));
		subMenu.add(action);
	}

	private class LayoutAction extends Action {

		private Layout layout;

		public LayoutAction(Layout layout) {
			this.layout = layout;
			setText(layout.getDisplayName());
		}

		@Override
		public void run() {
			LayoutAlgorithm layoutAlgorithm = layout.getAlgorithm();
			if (layoutAlgorithm instanceof SpringLayoutAlgorithm) {
				((SpringLayoutAlgorithm) layoutAlgorithm).setIterations(10000);
				((SpringLayoutAlgorithm) layoutAlgorithm).setSpringTimeout(10000);
			}
			((GraphViewer) view.getZoomableViewer()).setLayoutAlgorithm(layoutAlgorithm, true);
			view.setLayout(layout);
		}
	}
}