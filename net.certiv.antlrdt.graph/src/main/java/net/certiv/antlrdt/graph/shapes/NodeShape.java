package net.certiv.antlrdt.graph.shapes;

import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeType;
import javafx.scene.text.Text;

import org.eclipse.gef.fx.nodes.GeometryNode;
import org.eclipse.gef.geometry.planar.RoundedRectangle;

import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.antlrdt.graph.util.FXUtil;

public class NodeShape extends Group {

	private static final double H_PADDING = 6d;
	private static final double V_PADDING = 4d;
	private static final double H_SPACING = 4d;
	private static final double H_NUDGING = 2d;

	private HBox hbox;

	private GeometryNode<RoundedRectangle> shape;
	private ImageView icon;
	private Text text;

	public NodeShape(NodeModel model) {
		shape = new GeometryNode<>(new RoundedRectangle(0, 0, 70, 30, 16, 16));
		shape.setStroke(Color.DARKBLUE);
		shape.setStrokeType(StrokeType.INSIDE);
		shape.setFill(FXUtil.linearGradient(model.getColor()));

		hbox = new HBox(H_SPACING);
		hbox.setPadding(new Insets(V_PADDING, H_PADDING, V_PADDING, H_PADDING));

		icon = new ImageView(model.getIconUrl());
		text = new Text(model.getText());
		text.setTextOrigin(VPos.TOP);
		text.getStyleClass().add(model.getCssClass());

		hbox.getChildren().addAll(icon, text);
		hbox.setAlignment(Pos.CENTER);
		shape.setSmooth(true);
		shape.setSnapToPixel(true);

		Bounds ilb = icon.getLayoutBounds();
		Bounds tlb = text.getLayoutBounds();
		double width = ilb.getWidth() + H_SPACING + tlb.getWidth();
		double height = Math.max(ilb.getHeight(), tlb.getHeight());

		shape.setPrefWidth(width + H_PADDING * 2);
		shape.setPrefHeight(height + V_PADDING * 2);
		shape.setLayoutX(H_NUDGING);
		shape.setEffect(FXUtil.createShadowEffect());

		getChildren().addAll(shape, hbox);

		model.setShape(this);
	}

	@Override
	public Orientation getContentBias() {
		return Orientation.HORIZONTAL;
	}
}
