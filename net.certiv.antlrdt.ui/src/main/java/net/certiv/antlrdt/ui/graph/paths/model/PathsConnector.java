/*******************************************************************************
 * Copyright 2005-2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.paths.model;

import net.certiv.antlrdt.core.parser.PathsNode;

public class PathsConnector {

	private PathsNode source;
	private PathsNode target;

	public PathsConnector(PathsNode parent, PathsNode child) {
		setSource(parent);
		setTarget(child);
	}

	public PathsNode getTarget() {
		return this.target;
	}

	public void setTarget(PathsNode child) {
		this.target = child;
	}

	public PathsNode getSource() {
		return this.source;
	}

	public void setSource(PathsNode parent) {
		this.source = parent;
	}
}
