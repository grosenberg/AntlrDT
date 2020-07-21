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
package net.certiv.antlr.dt.vis.model.providers;

import org.eclipse.jface.viewers.ILabelProvider;

public interface INodeLabelProvider extends ILabelProvider {

	/** Sets the currently selected Object */
	void setCurrentSelection(Object root, Object selection);
}
