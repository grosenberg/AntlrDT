package net.certiv.antlrdt.ui.editor;

import net.certiv.antlrdt.core.lang.AntlrLangManager;
import net.certiv.antlrdt.core.model.SpecializedType;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.semantic.SemanticAnalyzer;

public class AntlrSemanaticAnalyzer extends SemanticAnalyzer {

	public AntlrSemanaticAnalyzer(DslUI ui) {
		super(ui, AntlrLangManager.DSL_NAME);

		addKey(SpecializedType.RuleName, PrefsKey.EDITOR_RULE_NAME_COLOR);
		addKey(SpecializedType.ModeName, PrefsKey.EDITOR_MODE_NAME_COLOR);
	}

	@Override
	protected boolean qualified(IStatement stmt) {
		switch ((SpecializedType) stmt.getSpecializedType()) {
			case RuleName:
			case ModeName:
				return true;

			default:
				return false;
		}
	}
}
