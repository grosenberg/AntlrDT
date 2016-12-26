package net.certiv.antlrdt.ui.graph;

import org.eclipse.core.runtime.IProgressMonitor;

public interface IHelper {

	Object SEARCH_CANCELED = new Object();
	Object[] EMPTY_ARRAY = new Object[0];

	void clear();

	IGraphModel getModel();

	void setModel(IGraphModel model);

	/** Return an array of all model member nodes excluding the given member node. */
	Object[] getMembers(Object member);

	/** Return the nodes that are direct decendants of this given node. */
	Object[] getConnectedMembers(Object member);

	IProgressMonitor getProgressMonitor();

}
