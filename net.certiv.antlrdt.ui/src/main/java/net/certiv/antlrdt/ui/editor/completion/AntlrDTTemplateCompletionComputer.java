package net.certiv.antlrdt.ui.editor.completion;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.contentassist.ICompletionProposal;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;

import net.certiv.antlrdt.ui.templates.AntlrDTTemplateCompletionProcessor;
import net.certiv.dsl.ui.text.completion.ContentAssistInvocationContext;
import net.certiv.dsl.ui.text.completion.DslCollector;
import net.certiv.dsl.ui.text.completion.DslCompletionProposalComputer;
import net.certiv.dsl.ui.text.completion.DslContentAssistInvocationContext;

public class AntlrDTTemplateCompletionComputer extends DslCompletionProposalComputer {

	@Override
	protected DslCollector createCollector(DslContentAssistInvocationContext context) {
		return new AntlrDTCollector(context.getSourceModule());
	}

	@Override
	protected TemplateCompletionProcessor createTemplateProposalComputer(DslContentAssistInvocationContext context) {
		return new AntlrDTTemplateCompletionProcessor(context);
	}

	// Completion proposals
	@Override
	public List<ICompletionProposal> computeCompletionProposals(ContentAssistInvocationContext context,
			IProgressMonitor monitor) {

		if (context instanceof DslContentAssistInvocationContext) {
			DslContentAssistInvocationContext dslContext = (DslContentAssistInvocationContext) context;
			int offset = dslContext.getInvocationOffset();
			List<ICompletionProposal> proposals = new ArrayList<>();
			proposals.addAll(computeTemplateCompletionProposals(offset, dslContext, monitor));
			return proposals;
		}
		return Collections.emptyList();
	}
}
