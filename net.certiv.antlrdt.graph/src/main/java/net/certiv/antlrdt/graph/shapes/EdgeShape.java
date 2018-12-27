package net.certiv.antlrdt.graph.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

import org.eclipse.gef.fx.nodes.Connection;

import net.certiv.antlrdt.graph.models.EdgeModel;

public class EdgeShape extends Connection {

	private static final String STYLE = "-fx-stroke-width: 1; -fx-stroke: blue;";

	public static final String CSS_CLASS = "edge";
	public static final String CSS_CLASS_CURVE = "curve";
	public static final String CSS_CLASS_DECORATION = "decoration";

	public static class Arrowhead extends Polygon {

		public Arrowhead() {
			super(0, 0, 6, 3, 6, -3);
		}
	}

	public EdgeShape() {
		this(null);
	}

	public EdgeShape(EdgeModel model) {
		// this.model = model;

		Arrowhead endDecoration = new Arrowhead();
		endDecoration.setFill(Color.BLACK);
		setEndDecoration(endDecoration);

		getStyleClass().add(CSS_CLASS);
		getCurve().getStyleClass().add(CSS_CLASS_CURVE);
		getCurve().setStyle(STYLE);

		// initialized style class for (default) decorations
		if (getStartDecoration() != null) {
			if (!getStartDecoration().getStyleClass().contains(CSS_CLASS_DECORATION)) {
				getStartDecoration().getStyleClass().add(CSS_CLASS_DECORATION);
			}
		}
		if (getEndDecoration() != null) {
			if (!getEndDecoration().getStyleClass().contains(CSS_CLASS_DECORATION)) {
				getEndDecoration().getStyleClass().add(CSS_CLASS_DECORATION);
			}
		}
	}
}
