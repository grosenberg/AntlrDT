package net.certiv.antlrdt.graph.shapes;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.RoundedRectangle;

import net.certiv.antlrdt.graph.models.NodeModel;

public class NodeShape extends Group {

	private static final double H_PADDING = 4d;
	private static final double V_PADDING = 4d;
	private static final double H_SPACING = 4d;

	private HBox hbox;

	private GeometryNode<RoundedRectangle> shape;
	private ImageView icon;
	private Text text;
	private LinearGradient gradient;

	public NodeShape(NodeModel model) {
		shape = new GeometryNode<>(new RoundedRectangle(0, 0, 70, 27, 4, 4));
		shape.setFill(linearGradient(model));
		shape.setStroke(Color.BLACK);
		shape.setStrokeType(StrokeType.INSIDE);

		icon = new ImageView(model.getIconUrl());
		text = new Text(model.getText());
		text.setTextOrigin(VPos.TOP);
		text.getStyleClass().add(model.getCssClass());

		hbox = new HBox(H_SPACING);
		hbox.setPadding(new Insets(V_PADDING, H_PADDING, V_PADDING, H_PADDING));
		hbox.getChildren().addAll(icon, text);
		hbox.setAlignment(Pos.CENTER);

		Bounds ilb = icon.getLayoutBounds();
		Bounds tlb = text.getLayoutBounds();

		double visX = ilb.getWidth() + H_SPACING + tlb.getWidth();
		double visY = Math.max(ilb.getHeight(), tlb.getHeight());

		shape.setLayoutX(visX);
		shape.setLayoutY(visY);
		shape.setPrefWidth(visX + H_PADDING * 2);
		shape.setPrefHeight(visY + V_PADDING * 2);

		DropShadow shadow = new DropShadow(2.0, Color.DARKGREY);
		shadow.setOffsetX(2.0);
		shadow.setOffsetY(2.0);
		shape.setEffect(shadow);

		getChildren().addAll(hbox);
	}

	@Override
	public Orientation getContentBias() {
		return Orientation.HORIZONTAL;
	}

	private Paint linearGradient(NodeModel model) {
		if (gradient == null) {
			Color color = model.getColor();
			Color darkr = color.darker();
			Stop[] stops = new Stop[] { new Stop(0, color), new Stop(1, darkr) };
			gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		}
		return gradient;
	}
}
