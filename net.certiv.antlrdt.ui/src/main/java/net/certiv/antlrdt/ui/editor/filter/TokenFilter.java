package net.certiv.antlrdt.ui.editor.filter;

import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.ui.viewsupport.AbstractDslElementFilter;

import org.eclipse.jface.viewers.Viewer;

public class TokenFilter extends AbstractDslElementFilter {

	public String getFilteringType() {
		return "TokenFilter";
	}

	public boolean isFilterProperty(Object element, Object property) {
		return false;
	}

	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (element instanceof IStatement) {
			IStatement statement = (IStatement) element;
			String name = statement.getElementName();
			if (name != null && name.length() > 0) {
				char c = name.charAt(0);
				if (Character.isUpperCase(c) && c != '@') {
					return false;
				}
			}
		}
		return true;
	}
}
