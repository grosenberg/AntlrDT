package net.certiv.antlrdt.ui.graph.cst.providers;

import org.eclipse.jface.viewers.ILabelProvider;

public interface INodeLabelProvider extends ILabelProvider {

	/** Sets the currently selected Object */
	public void setCurrentSelection(Object root, Object selection);

	/** Get all the interesting relationships. */
	public Object[] getInterestingRelationships();

	/** Sets a node to be pinned on this viewer */
	public void setPinnedNode(Object node);
}
