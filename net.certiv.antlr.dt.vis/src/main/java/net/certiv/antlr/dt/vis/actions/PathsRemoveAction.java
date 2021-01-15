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

public class PathsRemoveAction extends Action {

	private PathView view;

	public PathsRemoveAction(PathView view) {
		this.view = view;

		ImageManager imgMgr = VisUI.getDefault().getImageManager();
		setText("Remove Node");
		setToolTipText("Remove this node and immediate path connections ");
		setImageDescriptor(imgMgr.getDescriptor(imgMgr.IMG_ACTION_REMOVE_PATHS));
		setId(PathOp.REMOVE.text);
	}

	@Override
	public void run() {
		List<PathNode> selected = view.getCurrentSelectedNodes();
		if (selected != null) {
			for (PathNode node : selected) {
				view.update(PathOp.REMOVE, node);
			}
		}
	}
}
