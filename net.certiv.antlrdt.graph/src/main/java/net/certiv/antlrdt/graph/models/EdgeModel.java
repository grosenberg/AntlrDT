package net.certiv.antlrdt.graph.models;

import org.eclipse.gef.fx.nodes.AbstractRouter;
import org.eclipse.gef.graph.Edge;

import net.certiv.antlrdt.graph.layouts.BranchedConnectionRouter;
import net.certiv.antlrdt.graph.layouts.BranchedConnectionRouter.Branched;

public class EdgeModel extends Edge {

	private DiagramModel model;
	private boolean hidden;

	public EdgeModel(DiagramModel model, NodeModel source, NodeModel target) {
		super(source, target);
		this.model = model;
	}

	@Override
	public NodeModel getSource() {
		return (NodeModel) super.getSource();
	}

	@Override
	public NodeModel getTarget() {
		return (NodeModel) super.getTarget();
	}

	public DiagramModel getDiagramModel() {
		return model;
	}

	public AbstractRouter getRouter() {
		AbstractRouter router = model.getRouter().getRouter();
		if (router instanceof BranchedConnectionRouter) {
			Branched style = model.getLayout().getRouterStyle();
			((BranchedConnectionRouter) router).setStyle(style);
		}
		return router;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	@Override
	public String toString() {
		String state = hidden ? "..." : "-->";
		return String.format("{ '%s' %s '%s' }", getSource().getText(), state, getTarget().getText());
	}
}
