/*******************************************************************************
 * Copyright 2005-2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.ui.actions.ActionGroup;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import net.certiv.antlrdt.ui.AntlrDTImages;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.graph.IZoomableEditor;
import net.certiv.antlrdt.ui.graph.cst.CstEditor;

public class LayoutToolbarGroup extends ActionGroup {

	private IZoomableEditor editor;

	public LayoutToolbarGroup(IZoomableEditor editor) {
		this.editor = editor;
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		MenuManager subMenu = new MenuManager("Layouts");
		menu.appendToGroup(CstEditor.GROUP_LAYOUT_TOOLBAR, subMenu);

		Action action = null;

		action = new LayoutAction(Layout.HTREE);
		action.setImageDescriptor(getImageProvider().IMG_LAYOUT_TREE_HORIZ);
		subMenu.add(action);

		action = new LayoutAction(Layout.VTREE);
		action.setImageDescriptor(getImageProvider().IMG_LAYOUT_TREE);
		subMenu.add(action);

		action = new LayoutAction(Layout.HFLOW);
		action.setImageDescriptor(getImageProvider().IMG_LAYOUT_CALL);
		subMenu.add(action);

		action = new LayoutAction(Layout.CALL);
		action.setImageDescriptor(getImageProvider().IMG_LAYOUT_GRAPHFLOW);
		subMenu.add(action);

		subMenu.add(new Separator());

		action = new LayoutAction(Layout.SPRING);
		action.setImageDescriptor(getImageProvider().IMG_LAYOUT_SPRING);
		subMenu.add(action);

		action = new LayoutAction(Layout.RADIAL);
		action.setImageDescriptor(getImageProvider().IMG_LAYOUT_RADIAL);
		subMenu.add(action);

		action = new LayoutAction(Layout.GRID);
		action.setImageDescriptor(getImageProvider().IMG_LAYOUT_GRID);
		subMenu.add(action);

		// action = new LayoutAction("Walker Layout", new
		// GXTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING));
		// action.setImageDescriptor(getImageProvider().IMG_LAYOUT_TREE));
		// subMenu.add(action);

		// action = new LayoutAction("Directed Graph Layout", new
		// CompositeLayoutAlgorithm(
		// LayoutStyles.NO_LAYOUT_NODE_RESIZING, new LayoutAlgorithm[] {
		// new DirectedGraphLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
		// new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING) }));
		// action.setImageDescriptor(getImageProvider().IMG_LAYOUT_TREE));
		// subMenu.add(action);

		// SpringLayoutAlgorithm layoutAlgorithm = new
		// SpringLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING);
		// layoutAlgorithm.setIterations(200); // TODO: make preference
		// action = new LayoutAction("Spring", layoutAlgorithm);
		// action.setImageDescriptor(getImageProvider().IMG_LAYOUT_SPRING);
		// subMenu.add(action);

		// action = new LayoutAction("Vertical Tree Layout", new CompositeLayoutAlgorithm(
		// LayoutStyles.NO_LAYOUT_NODE_RESIZING, new LayoutAlgorithm[] {
		// new TreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
		// new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING) }));
		// action.setImageDescriptor(getImageProvider().IMG_LAYOUT_TREE));
		// subMenu.add(action);
		//
		// action = new LayoutAction("Horizontal Tree Layout", new
		// CompositeLayoutAlgorithm(
		// LayoutStyles.NO_LAYOUT_NODE_RESIZING, new LayoutAlgorithm[] {
		// new HorizontalTreeLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
		// new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING) }));
		// action.setImageDescriptor(getImageProvider().IMG_LAYOUT_TREE_HORIZ));
		// subMenu.add(action);
		//
		// action = new LayoutAction("Horizontal Layout", new CompositeLayoutAlgorithm(
		// LayoutStyles.NO_LAYOUT_NODE_RESIZING, new LayoutAlgorithm[] {
		// new HorizontalLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
		// new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING) }));
		// action.setImageDescriptor(getImageProvider().IMG_LAYOUT_HORIZONTAL));
		// subMenu.add(action);
		//
		// action = new LayoutAction("Vertical Layout", new CompositeLayoutAlgorithm(
		// LayoutStyles.NO_LAYOUT_NODE_RESIZING, new LayoutAlgorithm[] {
		// new VerticalLayoutAlgorithm(LayoutStyles.NO_LAYOUT_NODE_RESIZING),
		// new HorizontalShift(LayoutStyles.NO_LAYOUT_NODE_RESIZING) }));
		// action.setImageDescriptor(getImageProvider().IMG_LAYOUT_GRAPHFLOW));
		// subMenu.add(action);
	}

	private AntlrDTImages getImageProvider() {
		return (AntlrDTImages) AntlrDTUI.getDefault().getImageProvider();
	}

	private class LayoutAction extends Action {

		private Layout layout;

		public LayoutAction(Layout layout) {
			this.layout = layout;
			setText(layout.getName());
		}

		@Override
		public void run() {
			LayoutAlgorithm layoutAlgorithm = layout.getAlgorithm();
			if (layoutAlgorithm instanceof SpringLayoutAlgorithm) {
				((SpringLayoutAlgorithm) layoutAlgorithm).setIterations(10000);
				((SpringLayoutAlgorithm) layoutAlgorithm).setSpringTimeout(10000);
			}
			((GraphViewer) editor.getZoomableViewer()).setLayoutAlgorithm(layoutAlgorithm, true);
			editor.setLayoutAlgorithm(layout);
		}
	}
}
