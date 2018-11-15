package net.certiv.antlrdt.ui.editor.completion;

import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.model.DslModelException;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.completion.CompletionLabelProvider;
import net.certiv.dsl.ui.editor.text.completion.DslCompletionProposal;
import net.certiv.dsl.ui.editor.text.completion.DslCompletionProposalCollector;

public class AntlrCollector extends DslCompletionProposalCollector {

	public AntlrCollector(ICodeUnit unit) {
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
	protected CompletionLabelProvider createProposalLabelProvider() {
		return new AntlrCompletionLabelProvider();
	}

	@Override
	protected DslCompletionProposal createDslProposal(String completion, int offset, int length, Image image,
			String label, int relevance) {
		return createDslProposal(completion, offset, length, image, new StyledString(label), relevance, false);
	}

	@Override
	protected DslCompletionProposal createDslProposal(String completion, int offset, int length, Image image,
			StyledString label, int relevance, boolean inDoc) {
		return new AntlrProposal(completion, offset, length, image, label, relevance, inDoc);
	}

	@Override
	protected char[] getVarTrigger() {
		return VAR_TRIGGER;
	}

	@Override
	public void prepareProposals(ICodeUnit unit, int offset) throws DslModelException {
		if (unit.getParseRecord() == null || !unit.getParseRecord().hasTree()) return;

		// // 1) handle lexer and parser rule names
		// Set<IStatement> rules = getDslCore().getModelManager().getCodeAssistStatements(unit);
		// for (IStatement rule : rules) {
		// char[] name = rule.getElementName().toCharArray();
		// int type = CompletionProposal.METHOD_REF; // parser
		// if (Character.isUpperCase(name[0])) type = CompletionProposal.FIELD_REF; // lexer
		// CompletionProposal proposal = CompletionProposal.create(type, offset);
		// proposal.setName(name);
		// proposal.setCompletion(name);
		// accept(proposal);
		// }
		//
		// // 2) handle keywords: exists as a static list
		// for (String word : ScannerKeyWord.KEYWORDS) {
		// CompletionProposal proposal = CompletionProposal.create(CompletionProposal.KEYWORD, offset);
		// char[] name = word.toCharArray();
		// proposal.setName(name);
		// proposal.setCompletion(name);
		// accept(proposal);
		// }
	}
}