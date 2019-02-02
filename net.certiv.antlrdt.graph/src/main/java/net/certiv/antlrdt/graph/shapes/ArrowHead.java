package net.certiv.antlrdt.graph.shapes;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.StrokeLineJoin;

public class ArrowHead extends Polygon {

	public ArrowHead() {
		super(8.0, 0.0, 10.0, -5.0, 0.0, 0.0, 10.0, 5.0, 8.0, 0.0);
		setFill(Color.DARKBLUE);
		setStrokeLineJoin(StrokeLineJoin.ROUND);
	}
}
