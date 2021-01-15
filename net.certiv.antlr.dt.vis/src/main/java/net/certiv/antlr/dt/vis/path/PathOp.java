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
package net.certiv.antlr.dt.vis.path;

public enum PathOp {

	PATH_FUNC("path.func"),

	CALLEE("callee"),
	CALLER("caller"),

	FULL_BUILD("fullBuild"),	// complete path rebuild

	ADD_CALLERS("addCallers"),	// add super paths to selected path node
	ADD_CALLEES("addCallees"),	// add sub paths to selected path node
	REMOVE("remove"),			// remove selected path node

	CLEAR("clear"),				// clear all path nodes

	;

	public final String text;

	PathOp(String text) {
		this.text = text;
	}

	public static PathOp from(String text) {
		for (PathOp op : PathOp.values()) {
			if (op.text.equalsIgnoreCase(text)) return op;
			if (op.name().equalsIgnoreCase(text)) return op;
		}
		return null;
	}

	@Override
	public String toString() {
		return text;
	}
}
