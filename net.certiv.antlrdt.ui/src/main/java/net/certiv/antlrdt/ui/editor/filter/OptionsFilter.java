package net.certiv.antlrdt.ui.editor.filter;

import net.certiv.dsl.ui.viewsupport.NamePatternFilter;

public class OptionsFilter extends NamePatternFilter {

	public OptionsFilter() {
		super();
		super.setPatterns(new String[] { "options", "tokens" });
	}

	@Override
	public String getFilteringType() {
		return "OptionsFilter";
	}
}