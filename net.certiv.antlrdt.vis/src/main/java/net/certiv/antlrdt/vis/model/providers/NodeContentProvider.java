/*******************************************************************************
 * Copyright (c) 2005-2019, Certiv Analytics. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlrdt.vis.model.providers;

import java.util.List;

import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import net.certiv.antlrdt.vis.model.TreeModel;
import net.certiv.antlrdt.vis.model.TreeNode;

public class NodeContentProvider implements IGraphEntityContentProvider {

	private final GraphViewer viewer;
	private TreeModel model;

	public NodeContentProvider(GraphViewer viewer) {
		this.viewer = viewer;
		model = new TreeModel();
	}

	public void updateModel(TreeModel model) {
		dispose();
		this.model = model;
		viewer.refresh();
		viewer.applyLayout();
	}

	/** Get all objects directly connected to the given object. */
	@Override
	public Object[] getElements(Object member) {
		if (member == null) return new Object[] { member };
		List<TreeNode> members = model.getNodeList();
		members.remove(member);
		return members.toArray();
	}

	@Override
	public Object[] getConnectedTo(Object member) {
		if (member == null) return new Object[] { member };
		List<TreeNode> callees = model.findCallees((TreeNode) member);
		return callees.toArray();
	}

	public double getWeight(Object entity1, Object entity2) {
		return -1;
	}

	@Override
	public void dispose() {
		model.clear();
	}

	// // called from GraphViewer#setInput
	// @Override
	// public void inputChanged(Viewer viewer, Object prior, Object input) {
	// if (prior == input) return;
	//
	// dispose();
	// if (input instanceof ICodeUnit) {
	// unit = (ICodeUnit) input;
	// buildModel();
	// }
	// }
	//
	// private void buildModel() {
	// Job job = new Job("Build model") {
	//
	// @Override
	// protected IStatus run(IProgressMonitor monitor) {
	// DslParseRecord record = unit.getParseRecord();
	// if (record != null && record.hasTree()) {
	// ParseTreeWalker.DEFAULT.walk(new TreeProcessor(model), record.tree);
	// viewer.refresh();
	// viewer.applyLayout();
	// }
	// return Status.OK_STATUS;
	// }
	// };
	// job.setPriority(Job.SHORT);
	// job.schedule();
	// }
}
