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
package net.certiv.antlr.dt.vis.tokens;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class TokensViewContentProvider implements IStructuredContentProvider {

	@Override
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object parent) {
		if (parent instanceof ArrayList) {
			ArrayList<String[]> list = (ArrayList<String[]>) parent;
			return list.toArray(new String[list.size()][]);
		}
		return null;
	}
}
