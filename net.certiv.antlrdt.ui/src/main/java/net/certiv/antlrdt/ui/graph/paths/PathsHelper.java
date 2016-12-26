/*******************************************************************************
 * Copyright 2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.paths;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;

import net.certiv.antlrdt.core.parser.PathsNode;
import net.certiv.antlrdt.ui.graph.IGraphModel;
import net.certiv.antlrdt.ui.graph.IHelper;
import net.certiv.antlrdt.ui.graph.paths.model.PathsModel;

public class PathsHelper implements IHelper {

	private PathsEditor view;
	private IGraphModel model;

	public PathsHelper(PathsEditor view) {
		this.view = view;
		model = new PathsModel();
	}

	@Override
	public void clear() {
		model.clear();
	}

	@Override
	public IGraphModel getModel() {
		return model;
	}

	@Override
	public void setModel(IGraphModel pathsModel) {
		if (this.model != pathsModel) this.model.clear();
		this.model = pathsModel;
	}

	/** Return an array of all model member nodes excluding the given member node. */
	@Override
	public Object[] getMembers(Object member) {
		List<PathsNode> members = ((PathsModel) model).getNodeList();
		members.remove(member);
		return members.toArray(new Object[members.size()]);
	}

	/** Return the nodes that are direct decendants of this given node. */
	@Override
	public Object[] getConnectedMembers(Object member) {
		List<PathsNode> callees = ((PathsModel) model).findCallees((PathsNode) member);
		return callees.toArray(new Object[callees.size()]);
	}

	@Override
	public IProgressMonitor getProgressMonitor() {
		IStatusLineManager statusLineMgr = view.getEditorSite().getActionBars().getStatusLineManager();
		return statusLineMgr.getProgressMonitor();
	}

	public PathsNode getPathsNode(String selected) {
		return ((PathsModel) model).namedPathsNode(selected);
	}

}
