package net.certiv.antlrdt.graph.parts;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collections;
import java.util.List;

import javafx.scene.control.Tooltip;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Translate;

import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.ITransformableContentPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import net.certiv.antlrdt.graph.models.BaseModel;
import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.antlrdt.graph.shapes.NodeShape;

public class TreeNodePart extends AbstractContentPart<NodeShape>
		implements ITransformableContentPart<NodeShape>, PropertyChangeListener {

	public static final double PADDING = 5;
	public static final String CSS_CLASS = "node";
	public static final String CSS_CLASS_LABEL = "label";
	public static final String CSS_CLASS_ICON = "icon";

	private NodeShape shape;
	private Tooltip tooltip;

	@Override
	public NodeModel getContent() {
		return (NodeModel) super.getContent();
	}

	public NodeShape getShape() {
		return shape;
	}

	@Override
	protected void doActivate() {
		super.doActivate();
		getContent().addPropertyChangeListener(this);
	}

	@Override
	protected void doDeactivate() {
		getContent().removePropertyChangeListener(this);
		super.doDeactivate();
	}

	@Override
	protected NodeShape doCreateVisual() {
		return new NodeShape(getContent());
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
		Point location = model.getBounds().getLocation();
		Affine transform = new Affine(new Translate(location.x, location.y));
		if (!NodeUtils.equals(getVisualTransform(), transform)) {
			setVisualTransform(transform);
		}

		if (tooltip == null) {
			tooltip = new Tooltip(model.getText());
			Tooltip.install(getVisual(), tooltip);
		}
	}

	@Override
	public Affine getContentTransform() {
		Rectangle bounds = getContent().getBounds();
		return new Affine(new Translate(bounds.getX(), bounds.getY()));
	}

	@Override
	public void setContentTransform(Affine transform) {
		Rectangle bounds = getContent().getBounds().getCopy();
		bounds.setX(transform.getTx());
		bounds.setY(transform.getTy());
		getContent().setBounds(bounds);
	}

	@Override
	public void propertyChange(PropertyChangeEvent event) {
		String prop = event.getPropertyName();
		if (BaseModel.PROP_BOUNDS.equals(prop)) {
			refreshVisual();
		}
	}
}
