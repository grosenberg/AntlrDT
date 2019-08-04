package net.certiv.antlrdt.ui;

import org.eclipse.jface.text.IAutoEditStrategy;

import net.certiv.dsl.core.model.IDslElement;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.DefaultAutoEditSemicolonStrategy;
import net.certiv.dsl.ui.templates.CompletionManager;

public class AntlrCompletionManager extends CompletionManager {

	private static final String GRAMMAR = "grammar";
	private static final String OPTIONS = "options";
	private static final String RULE = "rule";

	public AntlrCompletionManager(DslUI ui, String editorId) {
		super(ui, editorId, MODULE, IMPORT, GRAMMAR, OPTIONS, RULE);
	}

	@Override
	public String getContentAssistScope(IStatement stmt) {
		switch (stmt.getKind()) {
			case IDslElement.DSL_UNIT:
			case IDslElement.MODULE:
			case IDslElement.DECLARATION:
			case IDslElement.IMPORT:
				return GRAMMAR;

			case IDslElement.STATEMENT:
				if (stmt.getElementName().equals(OPTIONS)) return OPTIONS;
				if (stmt.getElementName().equals(RULE)) return RULE;
				return GRAMMAR;

			case IDslElement.FIELD:
			case IDslElement.BEG_BLOCK:
			case IDslElement.END_BLOCK:
			default:
				return null;
		}
	}

	@Override
	public IAutoEditStrategy getSmartTriggerStrategy(String contentType) {
		String partitioning = ui.getTextTools().getDocumentPartitioning();
		// TODO: customize the strategy
		return new DefaultAutoEditSemicolonStrategy(partitioning, mgr);
	}
}
