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
package net.certiv.antlr.dt.ui.editor.filter;

import org.eclipse.jface.viewers.Viewer;

import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.ui.viewsupport.AbstractDslElementFilter;

public class RuleFilter extends AbstractDslElementFilter {

	public String getFilteringType() {
		return "RuleFilter";
	}

	public boolean isFilterProperty(Object element, Object property) {
		return false;
	}

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IStatement) {
			IStatement statement = (IStatement) element;
			String name = statement.getElementName();
			if (name != null && name.length() > 0) {
				char c = name.charAt(0);
				if (Character.isLowerCase(c)
						&& c != '@'
						&& !name.startsWith("options")
						&& !name.startsWith("tokens")) {
					return false;
				}
			}
		}
		return true;
	}
}
