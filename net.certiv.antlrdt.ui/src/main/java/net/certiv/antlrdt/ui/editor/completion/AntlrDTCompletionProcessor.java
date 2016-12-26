package net.certiv.antlrdt.ui.editor.completion;

import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.ui.IEditorPart;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.templates.DslTemplateContext;
import net.certiv.dsl.ui.text.completion.CompletionLabelProvider;
import net.certiv.dsl.ui.text.completion.DslCompletionProcessor;
import net.certiv.dsl.ui.text.completion.DslCompletionProposal;

public class AntlrDTCompletionProcessor extends DslCompletionProcessor {

	public AntlrDTCompletionProcessor(IEditorPart editor, ContentAssistant assistant, String partition) {
		super(editor, assistant, partition);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	protected CompletionLabelProvider getProposalLabelProvider() {
		return new AntlrDTCompletionLabelProvider();
	}

	@Override
	public void createCategories() {
		addCategory(DslCompletionProposal.ID, DslCompletionProposal.NAME, new AntlrDTCodeCompletionComputer());
		addCategory(DslTemplateContext.ID, DslTemplateContext.NAME, new AntlrDTTemplateCompletionComputer());
	}
}
