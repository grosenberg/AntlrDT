package net.certiv.antlrdt.ui.graph;

import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;

import net.certiv.antlrdt.ui.graph.actions.Layout;
import net.certiv.antlrdt.ui.graph.actions.Router;

public interface IZoomableEditor extends IZoomableWorkbenchPart {

	void setLayoutAlgorithm(Layout layout);

	void setConnectionRouter(Router router);

}
