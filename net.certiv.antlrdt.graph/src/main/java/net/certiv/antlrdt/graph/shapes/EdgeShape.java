package net.certiv.antlrdt.graph.shapes;

import org.eclipse.gef.fx.nodes.Connection;

import net.certiv.antlrdt.graph.models.EdgeModel;

public class EdgeShape extends Connection {

	public static final String STYLE = "-fx-stroke-width: 1; -fx-stroke: darkblue;";

	public static final String CSS_CLASS = "edge";
	public static final String CSS_CLASS_CURVE = "curve";
	public static final String CSS_CLASS_DECORATION = "decoration";

	private EdgeModel model;

	public EdgeShape(EdgeModel model) {
		this.model = model;
		getCurve().setStyle(STYLE);
		setEndDecoration(new ArrowHead());
		setVisible(!model.isHidden());
	}

	public EdgeModel getContent() {
		return model;
	}
}
