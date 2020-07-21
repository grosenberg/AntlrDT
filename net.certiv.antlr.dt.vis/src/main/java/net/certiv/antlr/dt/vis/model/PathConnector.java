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

public class PathConnector {

	private PathNode source;
	private PathNode target;

	public PathConnector(PathNode source, PathNode target) {
		this.source = source;
		this.target = target;
	}

	public PathNode getSource() {
		return this.source;
	}

	public void setSource(PathNode source) {
		this.source = source;
	}

	public PathNode getTarget() {
		return this.target;
	}

	public void setTarget(PathNode target) {
		this.target = target;
	}

	@Override
	public String toString() {
		return String.format("%s -> %s", source.toString(), target.toString());
	}
}
