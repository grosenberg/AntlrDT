
package net.certiv.antlr.dt.ui.templates;

import org.eclipse.jface.text.templates.TemplateVariableResolver;

import net.certiv.dsl.ui.editor.text.completion.DslTemplateContextType;

public class AntlrContextType extends DslTemplateContextType {

	public AntlrContextType(String id, TemplateVariableResolver... resolvers) {
		super(id, resolvers);
	}
}
