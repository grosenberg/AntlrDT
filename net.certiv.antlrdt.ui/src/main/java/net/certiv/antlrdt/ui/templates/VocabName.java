package net.certiv.antlrdt.ui.templates;

import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.ui.templates.DslTemplateContext;

import org.eclipse.jface.text.templates.TemplateContext;
import org.eclipse.jface.text.templates.TemplateVariableResolver;

public class VocabName extends TemplateVariableResolver {

	public VocabName() {}

	public VocabName(String type, String description) {
		super(type, description);
	}

	@Override
	protected boolean isUnambiguous(TemplateContext context) {
		return resolve(context) != null;
	}

	@Override
	protected String resolve(TemplateContext context) {
		ICodeUnit unit = ((DslTemplateContext) context).getSourceModule();
		String vocabName = null;
		if (unit != null) {
			vocabName = unit.getElementName();
			if (vocabName != null && vocabName.lastIndexOf('.') > 0) {
				vocabName = vocabName.substring(0, vocabName.lastIndexOf('.'));
			}
		}
		return vocabName;
	}
}