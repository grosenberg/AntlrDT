package net.certiv.antlrdt.ui.editor.completion;

import org.eclipse.jface.resource.ImageDescriptor;

import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.completion.CompletionProposal;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.ui.editor.text.completion.CompletionLabelProvider;

public class AntlrCompletionLabelProvider extends CompletionLabelProvider {

	public AntlrCompletionLabelProvider() {
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
				return createSimpleLabel(proposal);
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
				descriptor = imgMgr.getDescriptor(imgMgr.IMG_OBJS_PUBLIC_METHOD);
				break;
			case CompletionProposal.LOCAL_VARIABLE_REF:
			case CompletionProposal.VARIABLE_DECLARATION:
			case CompletionProposal.FIELD_REF:
				descriptor = imgMgr.getDescriptor(imgMgr.IMG_OBJS_LOCAL_VARIABLE);
				break;
			case CompletionProposal.KEYWORD:
				descriptor = imgMgr.getDescriptor(imgMgr.IMG_OBJS_KEYWORD);
				break;
			case CompletionProposal.LABEL_REF:
				descriptor = imgMgr.getDescriptor(imgMgr.IMG_OBJS_LABEL);
				break;
			default:
				descriptor = null;
				Log.warn(this, "Unknown proposal type");
		}

		if (descriptor == null) return null;
		return decorateImageDescriptor(descriptor, proposal);
	}
}
