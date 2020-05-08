package net.certiv.antlr.dt.vis.views.tokens.providers;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;

public class ListViewerContentProvider implements IStructuredContentProvider {

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
