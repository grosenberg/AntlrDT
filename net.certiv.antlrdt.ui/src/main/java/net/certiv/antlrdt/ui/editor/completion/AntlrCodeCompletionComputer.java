package net.certiv.antlrdt.ui.editor.completion;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.text.templates.TemplateCompletionProcessor;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.templates.AntlrTemplateCompletionProcessor;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.completion.DslCompletionProposalCollector;
import net.certiv.dsl.ui.editor.text.completion.DslCompletionProposalComputer;
import net.certiv.dsl.ui.editor.text.completion.DslContentAssistInvocationContext;
import net.certiv.dsl.ui.editor.text.completion.IDslCompletionProposal;

public class AntlrCodeCompletionComputer extends DslCompletionProposalComputer {

	@Override
	protected DslCompletionProposalCollector createCollector(DslContentAssistInvocationContext context) {
		return new AntlrCollector(context.getUnit());
	}

	@Override
	protected TemplateCompletionProcessor createTemplateProposalComputer(DslContentAssistInvocationContext context) {
		return new AntlrTemplateCompletionProcessor(context);
	}

	@Override
	public List<IDslCompletionProposal> computeCompletionProposals(DslContentAssistInvocationContext context,
			IProgressMonitor monitor) {
		List<IDslCompletionProposal> proposals = new ArrayList<>();
		proposals.addAll(computeCompletionProposals(context.getInvocationOffset(), context, monitor));
		return proposals;
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}
}
