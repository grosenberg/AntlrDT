/*******************************************************************************
 * Copyright 2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.cst;

import java.util.ArrayList;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.action.IStatusLineManager;

import net.certiv.antlrdt.ui.graph.IGraphModel;
import net.certiv.antlrdt.ui.graph.IHelper;
import net.certiv.antlrdt.ui.graph.cst.model.CstModel;

public class CstHelper implements IHelper {

	private CstEditor view;
	private IGraphModel model;

	public CstHelper(CstEditor view) {
		this.view = view;
		model = new CstModel();
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
	public void setModel(IGraphModel model) {
		if (this.model != model) this.model.clear();
		this.model = model;
	}

	/** Return an array of all model member nodes excluding the given member node. */
	@Override
	public Object[] getMembers(Object member) {
		ArrayList<ParseTree> members = ((CstModel) model).getNodeList();
		members.remove(member);
		return members.toArray(new Object[members.size()]);
	}

	/** Return the nodes that are direct decendants of this given node. */
	@Override
	public Object[] getConnectedMembers(Object member) {
		ArrayList<ParseTree> callees = ((CstModel) model).findCallees((ParseTree) member);
		return callees.toArray(new Object[callees.size()]);
	}

	@Override
	public IProgressMonitor getProgressMonitor() {
		IStatusLineManager statusLineMgr = view.getEditorSite().getActionBars().getStatusLineManager();
		return statusLineMgr.getProgressMonitor();
	}
}
