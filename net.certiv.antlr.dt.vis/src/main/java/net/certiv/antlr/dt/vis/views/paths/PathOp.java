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
package net.certiv.antlr.dt.vis.views.paths;

public enum PathOp {
	FULL_BUILD,		// complete path rebuild
	ADD_CALLERS,	// add super paths to selected path node
	ADD_CALLEES,	// add sub paths to selected path node
	REMOVE_NODE;	// remove selected path node
}
