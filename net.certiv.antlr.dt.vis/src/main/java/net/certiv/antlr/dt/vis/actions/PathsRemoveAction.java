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

import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.model.PathNode;
import net.certiv.antlr.dt.vis.views.paths.PathOp;
import net.certiv.antlr.dt.vis.views.paths.PathView;

public class PathsRemoveAction extends Action {

	private PathView view;

	public PathsRemoveAction(PathView view) {
		this.view = view;

		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		setText("Remove Node");
		setToolTipText("Remove this node and immediate path connections ");
		setImageDescriptor(imgMgr.getDescriptor(imgMgr.IMG_ACTION_REMOVE_PATHS));
		setId(PathView.REMOVE_PATHS);
	}

	@Override
	public void run() {
		List<PathNode> selected = view.getCurrentSelectedNodes();
		if (selected != null) {
			for (PathNode select : selected) {
				view.update(PathOp.REMOVE_NODE, null, select.getNodeName());
			}
		}
	}
}
