package net.certiv.antlrdt.ui.templates;

import org.eclipse.jface.text.IDocument;

import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.ui.editor.text.completion.tmpl.DslTemplateContext;
import net.certiv.dsl.ui.editor.text.completion.tmpl.DslTemplateContextType;

public class AntlrDTTemplateContextType extends DslTemplateContextType {

	public static final String CONTEXT_TYPE_ID = "net.certiv.antlrdt.ui.DefaultContext";
	public static final String CUSTOM_TEMPLATES_KEY = "net.certiv.antlrdt.ui.CustomContext.templates";

	public static final String GRAMMAR_CONTEXT_TYPE_ID = "antlrGrammar";
	public static final String OPTIONS_CONTEXT_TYPE_ID = "antlrOptions";
	public static final String ACTIONS_CONTEXT_TYPE_ID = "java"; // the JDT context id
	public static final String JAVADOC_CONTEXT_TYPE_ID = "javadoc"; // the JDT context id

	public AntlrDTTemplateContextType(String id) {
		super(id);
	}

	public AntlrDTTemplateContextType(String id, String name) {
		super(id, name);
	}

	@Override
	public DslTemplateContext createContext(IDocument document, int completionPosition, int length,
			ICodeUnit sourceModule) {

		return new AntlrDTTemplateContext(this, document, completionPosition, length, sourceModule);
	}

	@Override
	protected void addDslResolvers() {}
}
