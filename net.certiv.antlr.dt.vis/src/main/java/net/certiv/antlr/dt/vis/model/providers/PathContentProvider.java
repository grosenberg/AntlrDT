/*******************************************************************************
 * Copyright (c) 2005-2019, Certiv Analytics. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.vis.model.providers;

import java.util.List;
import java.util.stream.Collectors;

import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import net.certiv.antlr.dt.vis.model.PathModel;
import net.certiv.antlr.dt.vis.model.PathNode;

public class PathContentProvider implements IGraphEntityContentProvider {

	private final GraphViewer viewer;
	private PathModel model;

	public PathContentProvider(GraphViewer viewer) {
		this.viewer = viewer;
		model = new PathModel();
	}

	public void updateModel(PathModel model) {
		dispose();
		this.model = model;
		viewer.refresh();
		viewer.applyLayout();
	}

	@Override
	public Object[] getElements(Object member) {
		if (member == null) return new Object[] { member };

		List<PathNode> members = model.getNodeList().stream().filter(n -> !n.isHidden()).collect(Collectors.toList());
		members.remove(member);
		return members.toArray();
	}

	@Override
	public Object[] getConnectedTo(Object member) {
		if (member == null) return new Object[] { member };

		PathNode node = (PathNode) member;
		List<PathNode> callees = node.getChildren().stream().filter(n -> !n.isHidden()).collect(Collectors.toList());
		return callees.toArray();
	}

	public double getWeight(Object entity1, Object entity2) {
		return -1;
	}

	@Override
	public void dispose() {
		model.clear();
	}
}
