/*******************************************************************************
 * Copyright 2005-2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.cst.model;

import org.antlr.v4.runtime.tree.ParseTree;

public class CstConnector {

	private ParseTree source;
	private ParseTree target;

	public CstConnector(ParseTree source, ParseTree target) {
		setSource(source);
		setTarget(target);
	}

	public ParseTree getTarget() {
		return this.target;
	}

	public void setTarget(ParseTree target) {
		this.target = target;
	}

	public ParseTree getSource() {
		return this.source;
	}

	public void setSource(ParseTree source) {
		this.source = source;
	}
}
