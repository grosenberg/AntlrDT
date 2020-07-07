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
