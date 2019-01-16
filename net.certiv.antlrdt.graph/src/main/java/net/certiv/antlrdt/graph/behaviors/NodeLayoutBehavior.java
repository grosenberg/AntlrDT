package net.certiv.antlrdt.graph.behaviors;

import javafx.geometry.Bounds;
import javafx.scene.Node;
import javafx.scene.transform.Affine;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.layout.LayoutContext;
import org.eclipse.gef.layout.LayoutProperties;
import org.eclipse.gef.mvc.fx.parts.IContentPart;

import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.antlrdt.graph.parts.TreeNodePart;

public class NodeLayoutBehavior extends AbstractLayoutBehavior {

	private Dimension size;

	@Override
	public TreeNodePart getHost() {
		return (TreeNodePart) super.getHost();
	}

	@Override
	protected LayoutContext getLayoutContext() {
		IContentPart<? extends Node> graphPart = getHost().getRoot().getViewer().getContentPartMap()
				.get(getHost().getContent().getGraph());
		return graphPart.getAdapter(GraphLayoutBehavior.class).getLayoutContext();
	}

	@Override
	protected void preLayout() {
		NodeModel model = getHost().getContent();

		Node visual = getHost().getVisual();
		Bounds bounds = visual.getLayoutBounds();
		double minx = bounds.getMinX();
		double miny = bounds.getMinY();
		double maxx = bounds.getMaxX();
		double maxy = bounds.getMaxY();

		// initialize size
		size = model.getSize().getCopy();
		if (size == null) {	// then use visual
			size = new Dimension(maxx - minx, maxy - miny);
		}

		// constrain to visual's min-size
		double minWidth = visual.minWidth(-1);
		double minHeight = visual.minHeight(-1);
		if (size.width < minWidth) {
			size.width = minWidth;
		}
		if (size.height < minHeight) {
			size.height = minHeight;
		}

		// initialize location (layout location is center while visual position is top-left)
		Point loc = model.getLocation();
		if (loc != null) {
			loc = loc.getTranslated(size.getScaled(0.5));
		} else {
			Affine transform = getHost().getVisualTransform();
			loc = new Point(transform.getTx() + minx + (maxx - minx) / 2, transform.getTy() + miny + (maxy - miny) / 2);
		}

		model.setBounds(loc, size);

		// additional information inferred from visual
		LayoutProperties.setResizable(model, visual.isResizable());
	}

	@Override
	protected void postLayout() {
		NodeModel model = getHost().getContent();

		// location is center, position is top-left
		Point postLocation = model.getLocation();
		Dimension postSize = model.getSize();
		if (postLocation != null) {
			postLocation = postLocation.getTranslated((postSize == null ? size : postSize).getScaled(0.5).getNegated());
		}

		model.setBounds(postLocation, postSize);
		getHost().refreshVisual();

		// update label positions (from visual locations)
		layoutLabels();
	}
}
