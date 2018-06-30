package net.certiv.antlrdt.ui.view.providers;

import java.util.ArrayList;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class ListViewerContentProvider implements IStructuredContentProvider {

	public void dispose() {}

	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {}

	@SuppressWarnings("unchecked")
	public Object[] getElements(Object parent) {
		if (parent instanceof ArrayList) {
			ArrayList<String[]> list = (ArrayList<String[]>) parent;
			return list.toArray(new String[list.size()][]);
		}
		return null;
	}
}
