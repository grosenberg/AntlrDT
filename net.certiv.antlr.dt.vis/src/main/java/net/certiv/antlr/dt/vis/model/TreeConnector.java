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
package net.certiv.antlr.dt.vis.model;

public class TreeConnector {

	private TreeNode source;
	private TreeNode target;

	public TreeConnector(TreeNode source, TreeNode target) {
		this.source = source;
		this.target = target;
	}

	public TreeNode getTarget() {
		return this.target;
	}

	public void setTarget(TreeNode target) {
		this.target = target;
	}

	public TreeNode getSource() {
		return this.source;
	}

	public void setSource(TreeNode source) {
		this.source = source;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", source.toString(), target.toString());
	}
}
