/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.vis.actions;

import java.util.List;

import org.eclipse.jface.action.Action;

import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.VisUI;
import net.certiv.antlr.dt.vis.model.PathNode;
import net.certiv.antlr.dt.vis.path.PathOp;
import net.certiv.antlr.dt.vis.path.PathView;

public class PathsAddSubAction extends Action {

	private PathView view;

	public PathsAddSubAction(PathView view) {
		this.view = view;

		ImageManager imgMgr = VisUI.getDefault().getImageManager();
		setText("Add Callee Paths");
		setToolTipText("Add call target paths to this path node");
		setImageDescriptor(imgMgr.getDescriptor(imgMgr.IMG_ACTION_ADD_PATHS));
		setId(PathOp.ADD_CALLEES.text);
	}

	@Override
	public void run() {
		List<PathNode> selected = view.getCurrentSelectedNodes();
		if (selected != null) {
			for (PathNode node : selected) {
				view.update(PathOp.ADD_CALLEES, node);
			}
		}
	}
}
