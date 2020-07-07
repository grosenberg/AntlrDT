package net.certiv.antlr.dt.vis.views.tokens;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class TokensViewContentProvider implements IStructuredContentProvider {

	@Override
	@SuppressWarnings("unchecked")
	public Object[] getElements(Object parent) {
		if (parent instanceof ArrayList) {
			ArrayList<String[]> list = (ArrayList<String[]>) parent;
			return list.toArray(new String[list.size()][]);
		}
		return null;
	}
}
