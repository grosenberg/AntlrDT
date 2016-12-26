package net.certiv.antlrdt.ui.templates;

import net.certiv.dsl.core.model.ITranslationUnit;
import net.certiv.dsl.ui.templates.DslTemplateContext;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.templates.TemplateContextType;

public class AntlrDTTemplateContext extends DslTemplateContext {

	protected AntlrDTTemplateContext(TemplateContextType type, IDocument document, int completionOffset,
			int completionLength, ITranslationUnit sourceModule) {

		super(type, document, completionOffset, completionLength, sourceModule);
	}
}
