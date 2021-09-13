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
package net.certiv.antlr.dt.ui.templates;

import org.eclipse.jface.text.templates.TemplateVariableResolver;

import net.certiv.dsl.ui.editor.text.completion.DslTemplateContextType;

public class AntlrContextType extends DslTemplateContextType {

	public AntlrContextType(String id, TemplateVariableResolver... resolvers) {
		super(id, resolvers);
	}
}
