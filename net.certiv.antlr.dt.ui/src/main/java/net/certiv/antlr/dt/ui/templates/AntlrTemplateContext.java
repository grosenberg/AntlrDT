package net.certiv.antlr.dt.ui.templates;

import org.eclipse.jface.text.IRegion;

import net.certiv.dsl.ui.editor.text.completion.CompletionContext;
import net.certiv.dsl.ui.editor.text.completion.DslTemplateContext;

public class AntlrTemplateContext extends DslTemplateContext {

	protected AntlrTemplateContext(AntlrContextType type, CompletionContext context, IRegion region) {
		super(type, context, region);
	}
}
