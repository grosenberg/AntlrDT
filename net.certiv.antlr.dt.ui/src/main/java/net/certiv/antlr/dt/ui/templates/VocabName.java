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

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.ui.editor.text.completion.DslTemplateContext;

public class VocabName extends TemplateVariableResolver {

	public VocabName() {
		super();
	}

	public VocabName(String type, String description) {
		super(type, description);
	}

	@Override
	protected boolean isUnambiguous(TemplateContext context) {
		return resolve(context) != null;
	}

	@Override
	protected String resolve(TemplateContext context) {
		ICodeUnit unit = ((DslTemplateContext) context).getSourceModule();
		String vocabName = null;
		if (unit != null) {
			vocabName = unit.getElementName();
			if (vocabName != null && vocabName.lastIndexOf('.') > 0) {
				vocabName = vocabName.substring(0, vocabName.lastIndexOf('.'));
			}
		}
		return vocabName;
	}
}
