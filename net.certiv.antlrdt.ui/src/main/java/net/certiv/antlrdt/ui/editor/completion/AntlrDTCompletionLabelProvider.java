package net.certiv.antlrdt.ui.editor.completion;

import org.eclipse.core.runtime.Assert;
import org.eclipse.jface.resource.ImageDescriptor;

import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.completion.CompletionProposal;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.ui.text.completion.CompletionLabelProvider;

public class AntlrDTCompletionLabelProvider extends CompletionLabelProvider {

	public AntlrDTCompletionLabelProvider() {
		super(AntlrDTUI.getDefault());
	}

	@Override
	public String createLabel(CompletionProposal proposal) {
		switch (proposal.getKind()) {
			case CompletionProposal.METHOD_NAME_REFERENCE:
			case CompletionProposal.METHOD_REF:
			case CompletionProposal.POTENTIAL_METHOD_DECLARATION:
			case CompletionProposal.METHOD_DECLARATION:
				return createMethodLabel(proposal);
			case CompletionProposal.TYPE_REF:
				return createTypeLabel(proposal);
			case CompletionProposal.FIELD_REF:
				return createDeclarationLabelWithType(proposal);
			case CompletionProposal.LOCAL_VARIABLE_REF:
			case CompletionProposal.VARIABLE_DECLARATION:
				return createSimpleLabelWithType(proposal);
			case CompletionProposal.KEYWORD:
				return createKeywordLabel(proposal);
			case CompletionProposal.PACKAGE_REF:
			case CompletionProposal.LABEL_REF:
				return createSimpleLabel(proposal);
			default:
				Assert.isTrue(false);
				return null;
		}
	}

	@Override
	public ImageDescriptor createImageDescriptor(CompletionProposal proposal) {
		ImageDescriptor descriptor;
		switch (proposal.getKind()) {
			case CompletionProposal.METHOD_DECLARATION:
			case CompletionProposal.METHOD_NAME_REFERENCE:
			case CompletionProposal.METHOD_REF:
			case CompletionProposal.POTENTIAL_METHOD_DECLARATION:
				// descriptor =
				// DslElementImageProvider.getStatementImageDescriptor(proposal.getFlags());
				descriptor = dslUI.getImageProvider().DESC_OBJS_PUBLIC_METHOD;
				break;
			case CompletionProposal.LOCAL_VARIABLE_REF:
			case CompletionProposal.VARIABLE_DECLARATION:
			case CompletionProposal.FIELD_REF:
				descriptor = dslUI.getImageProvider().DESC_OBJS_LOCAL_VARIABLE;
				break;
			case CompletionProposal.KEYWORD:
				descriptor = dslUI.getImageProvider().DESC_OBJS_KEYWORD;
				break;
			case CompletionProposal.LABEL_REF:
				descriptor = dslUI.getImageProvider().DESC_OBJS_LABEL;
				break;
			default:
				descriptor = null;
				Log.warn(this, "Unknown proposal type");
		}

		if (descriptor == null) return null;
		return decorateImageDescriptor(descriptor, proposal);
	}
}
