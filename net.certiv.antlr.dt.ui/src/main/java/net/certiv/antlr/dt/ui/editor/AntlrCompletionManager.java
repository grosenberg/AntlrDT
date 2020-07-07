package net.certiv.antlr.dt.ui.editor;

import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.templates.CompletionManager;

public class AntlrCompletionManager extends CompletionManager {

	public static final String GRAMMAR = "grammar";
	public static final String OPTIONS = "options";
	public static final String RULE = "rule";

	public AntlrCompletionManager(DslUI ui, String editorId) {
		super(ui, editorId, GRAMMAR, OPTIONS, RULE);
	}

	@Override
	public String getContentAssistScope(IStatement stmt) {
		if (stmt == null) return GRAMMAR;

		switch (stmt.getModelType()) {
			case STATEMENT:
				switch (stmt.getElementName()) {
					case OPTIONS:
						return OPTIONS;
					case RULE:
						return RULE;
					default:
						return GRAMMAR;
				}

			default:
				return null;
		}
	}

	// TODO: customize the strategy
	// @Override
	// public IAutoEditStrategy getSmartTriggerStrategy(String contentType) {
	// String partitioning = ui.getTextTools().getDocumentPartitioning();
	// return new DefaultAutoEditSemicolonStrategy(partitioning, mgr);
	// }
}
