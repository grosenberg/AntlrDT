package net.certiv.antlrdt.graph.parts;

import java.util.Collections;
import java.util.List;

import javafx.scene.control.Tooltip;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;

import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.ITransformableContentPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.antlrdt.graph.shapes.NodeShape;
import net.certiv.antlrdt.graph.tips.Infotip;

public class TreeNodePart extends AbstractContentPart<NodeShape> implements ITransformableContentPart<NodeShape> {

	// public static final double PADDING = 5;
	// public static final String CSS_CLASS = "node";
	// public static final String CSS_CLASS_LABEL = "label";
	// public static final String CSS_CLASS_ICON = "icon";

	protected TreeNodePart() {
		super();
	}

	@Override
	public NodeModel getContent() {
		return (NodeModel) super.getContent();
	}

	@Override
	protected NodeShape doCreateVisual() {
		NodeModel model = getContent();
		NodeShape visual = new NodeShape(getContent());
		Tooltip.install(visual, new Infotip(model));
		return visual;
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}

	@Override
	protected void doRefreshVisual(NodeShape visual) {
		NodeModel model = getContent();
		if (model == null) throw new IllegalStateException();

		visual.setVisible(!model.isHidden());

		Point loc = model.getLocation();
		if (loc != null) {
			Affine transform = new Affine(new Translate(loc.x, loc.y));
			if (!NodeUtils.equals(getVisualTransform(), transform)) {
				setVisualTransform(transform);
			}
		}

		Dimension size = model.getSize();
		if (size != null) {
			visual.resize(size.width, size.height);
		} else {
			visual.autosize();
		}
	}

	@Override
	public Affine getContentTransform() {
		Point loc = getContent().getLocation();
		if (loc == null) loc = new Point();
		return new Affine(new Translate(loc.x, loc.y));
	}

	@Override
	public void setContentTransform(Affine transform) {
		Rectangle bounds = getContent().getBounds();
		bounds.setLocation(transform.getTx(), transform.getTy());
		getContent().setBounds(bounds);
	}

	@Override
	public String toString() {
		return String.format("TreeNodePart '%s'", getContent().getText());
	}
}
