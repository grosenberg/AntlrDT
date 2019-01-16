package net.certiv.antlrdt.graph.models;

import org.eclipse.gef.fx.nodes.AbstractRouter;
import org.eclipse.gef.graph.Edge;

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
		return model.getRouter().getRouter();
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}
}
