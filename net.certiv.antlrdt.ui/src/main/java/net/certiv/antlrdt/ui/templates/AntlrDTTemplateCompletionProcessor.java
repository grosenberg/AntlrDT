package net.certiv.antlrdt.ui.templates;

import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.dsl.ui.editor.text.completion.DslContentAssistInvocationContext;
import net.certiv.dsl.ui.templates.DslTemplateCompletionProcessor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.BadPartitioningException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.templates.Template;
import org.eclipse.jface.text.templates.TemplateContextType;

public class AntlrDTTemplateCompletionProcessor extends DslTemplateCompletionProcessor {

	private static char[] IGNORE = new char[] { '.' };

	public AntlrDTTemplateCompletionProcessor(DslContentAssistInvocationContext context) {
		super(AntlrDTUI.getDefault(), context);
	}

	@Override
	protected String getContextTypeId() {
		return AntlrDTTemplateContextType.ANTLRDT_CONTEXT_TYPE_ID;
	}

	@Override
	protected char[] getIgnore() {
		return IGNORE;
	}

	@Override
	protected AntlrDTTemplateAccess getTemplateAccess() {
		return AntlrDTTemplateAccess.getInstance();
	}

	// NOTE: this handles empty prefixes
	// TODO: implement for globally enabling blank line template completions
	protected boolean isValid(ITextViewer viewer, Region region, String prefix) {
		if (prefix == null) return false;
		if (prefix.length() == 0) {
			TemplateContextType contextType = getContextType(viewer, region);
			String cId = contextType.getId();
			if (AntlrDTTemplateContextType.ANTLRDT_CONTEXT_TYPE_ID.equals(cId)) {
				return true;
			}
			return false;
		}
		return true;
	}

	@Override
	protected TemplateContextType getContextType(ITextViewer viewer, IRegion region) {
		IDocumentExtension3 doc = (IDocumentExtension3) viewer.getDocument();
		ITypedRegion typedRegion = null;
		try {
			typedRegion = doc.getPartition(Partitions.ANTLRDT_PARTITIONING, region.getOffset(), true);
		} catch (BadLocationException | BadPartitioningException e) {
			e.printStackTrace();
		}
		if (typedRegion == null) return null;
		String type = typedRegion.getType();

		if (type.equals(IDocument.DEFAULT_CONTENT_TYPE)) {
			return getTemplateAccess().getContextTypeRegistry()
					.getContextType(AntlrDTTemplateContextType.GRAMMAR_CONTEXT_TYPE_ID);
			// } else if (type.equals(Partitions.OPTIONS)) {
			// return getTemplateAccess().getContextTypeRegistry().getContextType(
			// AntlrDTTemplateContextType.OPTIONS_CONTEXT_TYPE_ID);
		} else if (type.equals(Partitions.COMMENT_JD)) {
			return getTemplateAccess().getContextTypeRegistry()
					.getContextType(AntlrDTTemplateContextType.JAVADOC_CONTEXT_TYPE_ID);
		} else if (isValidPartition(type)) {
			return getTemplateAccess().getContextTypeRegistry()
					.getContextType(AntlrDTTemplateContextType.ACTIONS_CONTEXT_TYPE_ID);
		}

		return super.getContextType(viewer, region);
	}

	private boolean isValidPartition(String type) {
		for (String partition : Partitions.getContentTypes()) {
			if (type.equals(partition)) return true;
		}
		return false;
	}

	@Override
	protected Template[] getTemplates(String contextTypeId) {
		if (contextTypeId.equals(AntlrDTTemplateContextType.GRAMMAR_CONTEXT_TYPE_ID)
				|| contextTypeId.equals(AntlrDTTemplateContextType.OPTIONS_CONTEXT_TYPE_ID)
				|| contextTypeId.equals(AntlrDTTemplateContextType.ACTIONS_CONTEXT_TYPE_ID)
				|| contextTypeId.equals(AntlrDTTemplateContextType.JAVADOC_CONTEXT_TYPE_ID)) {
			return getTemplateAccess().getTemplateStore().getTemplates(contextTypeId);
		}
		return new Template[0];
	}
}
