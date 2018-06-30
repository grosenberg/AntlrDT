package net.certiv.antlrdt.ui.editor.completion;

import java.util.Set;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.text.ScannerKeyWord;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.completion.CompletionProposal;
import net.certiv.dsl.core.model.DslModelException;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.completion.CompletionLabelProvider;
import net.certiv.dsl.ui.editor.text.completion.DslCollector;
import net.certiv.dsl.ui.editor.text.completion.DslCompletionProposal;

public class AntlrDTCollector extends DslCollector {

	public AntlrDTCollector(ICodeUnit unit) {
		super(unit);
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
	protected CompletionLabelProvider createCompletionProposalLabelProvider() {
		return new AntlrDTCompletionLabelProvider();
	}

	@Override
	protected DslCompletionProposal createDslCompletionProposal(String completion, int offset, int length, Image image,
			String label, int relevance) {
		return createDslCompletionProposal(completion, offset, length, image, new StyledString(label), relevance,
				false);
	}

	@Override
	protected DslCompletionProposal createDslCompletionProposal(String completion, int offset, int length, Image image,
			StyledString label, int relevance, boolean inDoc) {
		return new AntlrDTCompletionProposal(completion, offset, length, image, label, relevance, inDoc);
	}

	@Override
	protected char[] getVarTrigger() {
		return VAR_TRIGGER;
	}

	@Override
	public void prepareProposals(ICodeUnit unit, int offset) throws DslModelException {
		if (!parseValid()) return;

		// 1) handle lexer and parser rule names
		Set<IStatement> rules = getDslCore().getModelManager().getCodeAssistElements(unit);
		for (IStatement rule : rules) {
			char[] name = rule.getElementName().toCharArray();
			int type = CompletionProposal.METHOD_REF; // parser
			if (Character.isUpperCase(name[0])) type = CompletionProposal.FIELD_REF; // lexer
			CompletionProposal proposal = CompletionProposal.create(type, offset);
			proposal.setName(name);
			proposal.setCompletion(name);
			accept(proposal);
		}

		// 2) handle keywords: exists as a static list
		for (String word : ScannerKeyWord.KEYWORDS) {
			CompletionProposal proposal = CompletionProposal.create(CompletionProposal.KEYWORD, offset);
			char[] name = word.toCharArray();
			proposal.setName(name);
			proposal.setCompletion(name);
			accept(proposal);
		}
	}
}
