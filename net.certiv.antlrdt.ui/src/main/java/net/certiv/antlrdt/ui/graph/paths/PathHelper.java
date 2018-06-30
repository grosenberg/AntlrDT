/*******************************************************************************
 * Copyright 2009, Gerald B. Rosenberg, Certiv Analytics and others.
 * All rights reserved. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Certiv Analytics
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.paths;

import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;

import net.certiv.antlrdt.core.parser.PathsNode;
import net.certiv.antlrdt.ui.graph.IGraphModel;
import net.certiv.antlrdt.ui.graph.IHelper;
import net.certiv.antlrdt.ui.graph.paths.model.PathsModel;

public class PathHelper implements IHelper {

	private PathsEditor editor;
	private PathsModel model;

	public PathHelper(PathsEditor editor) {
		this.editor = editor;
	}

	@Override
	public void clear() {
		if (model != null) model.clear();
	}

	public void dispose() {
		if (model != null) model.dispose();
	}

	@Override
	public PathsModel getModel() {
		return model;
	}

	@Override
	public void setModel(IGraphModel pathsModel) {
		clear();
		model = (PathsModel) pathsModel;
	}

	/** Return an array of all model member nodes excluding the given member node. */
	@Override
	public Object[] getMembers(Object member) {
		List<PathsNode> members = model.getNodeList();
		members.remove(member);
		return members.toArray(new Object[members.size()]);
	}

	/** Return the nodes that are direct decendants of this given node. */
	@Override
	public Object[] getConnectedMembers(Object member) {
		List<PathsNode> callees = model.findCallees((PathsNode) member);
		return callees.toArray(new Object[callees.size()]);
	}

	@Override
	public IProgressMonitor getProgressMonitor() {
		IStatusLineManager statusLineMgr = editor.getEditorSite().getActionBars().getStatusLineManager();
		return statusLineMgr.getProgressMonitor();
	}

	public PathsNode getPathsNode(String selected) {
		return model.namedPathsNode(selected);
	}

	public PathsModel addSupPaths(String ruleName) {
		PathsNode current = model.namedPathsNode(ruleName);
		model.addSupPaths(current);
		return model;
	}

	public PathsModel addSubPaths(String ruleName) {
		PathsNode current = model.namedPathsNode(ruleName);
		model.addSubPaths(current);
		return model;
	}

	public PathsModel removeNode(String ruleName) {
		PathsNode current = model.namedPathsNode(ruleName);
		model.removeNode(current);
		return model;
	}
}
