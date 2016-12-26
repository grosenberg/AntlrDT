package net.certiv.antlrdt.ui.editor.completion;

import java.util.ArrayList;

import org.antlr.v4.runtime.Token;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.text.ScannerKeyWord;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.completion.CompletionProposal;
import net.certiv.dsl.core.model.DslModelException;
import net.certiv.dsl.core.model.ITranslationUnit;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.text.completion.CompletionLabelProvider;
import net.certiv.dsl.ui.text.completion.DslCollector;
import net.certiv.dsl.ui.text.completion.DslCompletionProposal;

public class AntlrDTCollector extends DslCollector {

	public AntlrDTCollector(ITranslationUnit tu) {
		super(tu);
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
	protected DslCompletionProposal createDslCompletionProposal(String completion, int start, int length, Image image,
			String label, int relevance) {
		return createDslCompletionProposal(completion, start, length, image, new StyledString(label), relevance, false);
	}

	@Override
	protected DslCompletionProposal createDslCompletionProposal(String completion, int start, int length, Image image,
			StyledString label, int relevance, boolean inDoc) {
		return new AntlrDTCompletionProposal(completion, start, length, image, label, relevance, inDoc);
	}

	@Override
	protected char[] getVarTrigger() {
		return VAR_TRIGGER;
	}

	/**
	 * @param offset invocation offset
	 */
	@Override
	public void prepareProposals(ITranslationUnit sourceModule, int offset) throws DslModelException {

		if (!parseValid()) return;

		// 1) handle lexer and parser rule names: captured as a list of tokens
		ArrayList<Token> rules = sourceModule.getSourceParser().getCodeAssistElements();
		for (Token rule : rules) {
			char[] name = rule.getText().toCharArray();
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
