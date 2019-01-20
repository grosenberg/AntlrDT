package net.certiv.antlrdt.graph.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;

import org.eclipse.gef.fx.nodes.Connection;

import net.certiv.antlrdt.graph.models.EdgeModel;

public class EdgeShape extends Connection {

	public static final String STYLE = "-fx-stroke-width: 1; -fx-stroke: darkblue;";

	public static final String CSS_CLASS = "edge";
	public static final String CSS_CLASS_CURVE = "curve";
	public static final String CSS_CLASS_DECORATION = "decoration";

	class ArrowHead extends Polygon {

		public ArrowHead() {
			super(8.0, 0.0, 10.0, -5.0, 0.0, 0.0, 10.0, 5.0, 8.0, 0.0);
			setFill(Color.DARKBLUE);
			setStrokeLineJoin(StrokeLineJoin.ROUND);
		}
	}

	public EdgeShape(EdgeModel model) {
		getCurve().setStyle(STYLE);
		setEndDecoration(new ArrowHead());
		setVisible(!model.isHidden());
	}
}
