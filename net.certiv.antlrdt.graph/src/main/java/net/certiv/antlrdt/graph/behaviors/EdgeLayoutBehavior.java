package net.certiv.antlrdt.graph.behaviors;

import javafx.scene.Node;

import org.eclipse.gef.layout.LayoutContext;
import org.eclipse.gef.mvc.fx.parts.IContentPart;

import net.certiv.antlrdt.graph.parts.TreeEdgePart;

public class EdgeLayoutBehavior extends AbstractLayoutBehavior {

	@Override
	public TreeEdgePart getHost() {
		return (TreeEdgePart) super.getHost();
	}

	@Override
	protected LayoutContext getLayoutContext() {
		IContentPart<? extends Node> graphPart = getHost().getRoot().getViewer().getContentPartMap()
				.get(getHost().getContent().getGraph());
		return graphPart.getAdapter(GraphLayoutBehavior.class).getLayoutContext();
	}

	@Override
	protected void preLayout() {
		getHost().refreshVisual();
	}

	@Override
	protected void postLayout() {
		layoutLabels();
	}
}
