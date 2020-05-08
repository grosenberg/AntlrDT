package net.certiv.antlr.dt.ui.editor.filter;

import net.certiv.dsl.ui.viewsupport.NamePatternFilter;

public class AtFilter extends NamePatternFilter {

	public AtFilter() {
		super();
		super.setPatterns(new String[] { "@*" });
	}

	@Override
	public String getFilteringType() {
		return "AtFilter";
	}
}
