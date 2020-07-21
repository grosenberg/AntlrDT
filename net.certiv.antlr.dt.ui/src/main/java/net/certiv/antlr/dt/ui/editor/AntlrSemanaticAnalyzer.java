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
package net.certiv.antlr.dt.ui.editor;

import net.certiv.antlr.dt.core.lang.AntlrLangManager;
import net.certiv.antlr.dt.core.model.SpecializedType;
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.semantic.SemanticAnalyzer;

public class AntlrSemanaticAnalyzer extends SemanticAnalyzer {

	public AntlrSemanaticAnalyzer(DslUI ui) {
		super(ui, AntlrLangManager.DSL_NAME);

		addKey(SpecializedType.ParserRuleName, PrefsKey.EDITOR_RULE_NAME_COLOR);
		addKey(SpecializedType.LexerRuleName, PrefsKey.EDITOR_RULE_NAME_COLOR);
		addKey(SpecializedType.FragmentRuleName, PrefsKey.EDITOR_RULE_NAME_COLOR);
		addKey(SpecializedType.ModeName, PrefsKey.EDITOR_MODE_NAME_COLOR);
	}

	@Override
	protected boolean qualified(IStatement stmt) {
		switch ((SpecializedType) stmt.getSpecializedType()) {
			case ParserRuleName:
			case LexerRuleName:
			case FragmentRuleName:
			case ModeName:
				return true;

			default:
				return false;
		}
	}
}
