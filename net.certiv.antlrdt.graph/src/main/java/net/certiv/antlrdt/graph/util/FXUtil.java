package net.certiv.antlrdt.graph.util;

import java.util.List;

import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.effect.Blend;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Light.Distant;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Paint;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Arc;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import org.eclipse.gef.geometry.convert.fx.FX2Geometry;
import org.eclipse.gef.geometry.planar.Ellipse;
import org.eclipse.gef.geometry.planar.Path;
import org.eclipse.gef.geometry.planar.RoundedRectangle;

public class FXUtil {

	private FXUtil() {}

	/**
	 * Return a contrasting color for the given color based on perceptive luminance. For bright colors,
	 * use a black font; for dark colors, a white font.
	 */
	public static Color contrastColor(Color color) {
		double luminance = (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue()) / 255;
		if (luminance > 0.5) return Color.BLACK;
		return Color.WHITE;
	}

	public static Paint linearGradient(Color color) {
		Stop[] stops = new Stop[] { new Stop(0, color.brighter()), new Stop(0.7, color), new Stop(1, color.darker()) };
		return new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
	}

	public static Effect createShadowEffect() {
		DropShadow shadow = new DropShadow();
		shadow.setRadius(2);
		shadow.setSpread(0.2);
		shadow.setOffsetX(2);
		shadow.setOffsetY(2);
		shadow.setColor(new Color(0.7, 0.8, 0.9, 1));

		Distant dist = new Distant();
		dist.setAzimuth(-135.0f);

		Lighting light = new Lighting();
		light.setLight(dist);
		light.setSurfaceScale(2.0f);

		Blend effects = new Blend();
		effects.setTopInput(light);
		effects.setBottomInput(shadow);
		return effects;
	}

	public static Path toPath(Node node) {
		return node instanceof Shape ? FXUtil.toPath((Shape) node) : new Path();
	}

	public static Path toPath(Shape shape) {
		Path path = FX2Geometry.toRectangle(shape.getLayoutBounds()).toPath();
		if (shape instanceof Line) {
			Line line = (Line) shape;
			path = new Path();
			path.moveTo(line.getStartX(), line.getStartY());
			path.lineTo(line.getEndX(), line.getEndY());
		} else if (shape instanceof Polyline) {
			Polyline polyline = (Polyline) shape;
			double[] p = FXUtil.toDoubleArray(polyline, polyline.getPoints());
			path = new org.eclipse.gef.geometry.planar.Polyline(p).toPath();
		} else if (shape instanceof Rectangle) {
			Rectangle rectangle = (Rectangle) shape;
			if (rectangle.getArcHeight() > 0.0 || rectangle.getArcWidth() > 0.0) {
				org.eclipse.gef.geometry.planar.Rectangle bounds = FX2Geometry.toRectangle(shape.getLayoutBounds());
				RoundedRectangle roundedRect = new RoundedRectangle(bounds, rectangle.getArcWidth(),
						rectangle.getArcHeight());
				path = roundedRect.toPath();
			}
		} else if (shape instanceof Arc || shape instanceof Circle) {
			org.eclipse.gef.geometry.planar.Rectangle r = FX2Geometry.toRectangle(shape.getLayoutBounds());
			Ellipse ellipse = new Ellipse(r);
			path = ellipse.toPath();
		} else if (shape instanceof Polygon) {
			Polygon polygon = (Polygon) shape;
			double[] p = FXUtil.toDoubleArray(polygon, polygon.getPoints());
			path = new org.eclipse.gef.geometry.planar.Polygon(p).toPath();
		}
		return path;
	}

	private static double[] toDoubleArray(Shape shape, List<Double> list) {
		double[] p = new double[list.size()];
		int i = 0;
		while (i < list.size()) {
			p[i] = list.get(i);
			p[i + 1] = list.get(i + 1);
			i += 2;
		}
		return p;
	}

	public static double computeXOffset(double width, double contentWidth, HPos hpos) {
		if (hpos == null) return 0;

		switch (hpos) {
			case LEFT:
				return 0;
			case CENTER:
				return (width - contentWidth) / 2;
			case RIGHT:
				return width - contentWidth;
			default:
				return 0;
		}
	}

	public static double computeYOffset(double height, double contentHeight, VPos vpos) {
		if (vpos == null) return 0;

		switch (vpos) {
			case TOP:
				return 0;
			case CENTER:
				return (height - contentHeight) / 2;
			case BOTTOM:
				return height - contentHeight;
			default:
				return 0;
		}
	}
}
