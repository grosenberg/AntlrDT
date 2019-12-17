package net.certiv.antlrdt.ui.editor;

import java.util.Arrays;
import java.util.List;

import net.certiv.antlrdt.core.lang.AntlrLangManager;
import net.certiv.antlrdt.core.model.SpecType;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.semantic.HighlightPosition;
import net.certiv.dsl.ui.editor.semantic.HighlightStyle;
import net.certiv.dsl.ui.editor.semantic.SemanticAnalyzer;
import net.certiv.dsl.ui.styles.Selector;

public class AntlrSemanaticAnalyzer extends SemanticAnalyzer {

	public AntlrSemanaticAnalyzer(DslUI ui) {
		super(ui, AntlrLangManager.DSL_NAME);

		addKey(SpecType.RuleName, PrefsKey.EDITOR_RULE_NAME_COLOR);
		addKey(SpecType.ModeName, PrefsKey.EDITOR_MODE_NAME_COLOR);
	}

	@Override
	protected boolean qualified(IStatement stmt) {
		switch ((SpecType) stmt.getSpecType()) {
			case RuleName:
			case ModeName:
				return true;

			default:
				return false;
		}
	}

	@Override
	protected void applySemanticEffects(HighlightPosition position, IStatement stmt) {
		super.applySemanticEffects(position, stmt);
		SpecType type = (SpecType) stmt.getSpecType();
		update(position, type.css); // Note: could build selector progression
	}

	private void update(HighlightPosition position, String sel) {
		update(position, Arrays.asList(sel));
	}

	private void update(HighlightPosition position, List<String> sel) {
		Selector selector = getRegistry().createSelector(sel);
		HighlightStyle style = stylesMgr.update(position.getStyle(), sheetRef, selector);
		position.setStyle(style);
	}
}
