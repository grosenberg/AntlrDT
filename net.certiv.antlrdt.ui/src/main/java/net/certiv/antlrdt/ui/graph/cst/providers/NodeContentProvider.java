/*******************************************************************************
 * Copyright 2005-2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.cst.providers;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.zest.core.viewers.IGraphEntityContentProvider;

import net.certiv.antlrdt.ui.graph.IHelper;

public class NodeContentProvider implements IGraphEntityContentProvider {

	Object model = null;
	private IHelper helper;

	public NodeContentProvider(IHelper helper) {
		super();
		this.helper = helper;
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		model = newInput;
	}

	/** Get all objects that this given object is directly connected to */
	@Override
	public Object[] getConnectedTo(Object member) {
		if (member == null) return new Object[] { member };
		return helper.getConnectedMembers(member);
	}

	@Override
	public Object[] getElements(Object member) {
		if (member == null) return new Object[] { member };
		return helper.getMembers(member);
	}

	@Override
	public void dispose() {}

	public double getWeight(Object entity1, Object entity2) {
		return 0;
	}
}
