package net.certiv.antlr.dt.vis.model.providers;

import org.eclipse.jface.viewers.ILabelProvider;

public interface INodeLabelProvider extends ILabelProvider {

	/** Sets the currently selected Object */
	void setCurrentSelection(Object root, Object selection);
}
