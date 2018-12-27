package net.certiv.antlrdt.graph.parts;

import org.eclipse.gef.fx.anchors.DynamicAnchor;
import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.mvc.fx.providers.DefaultAnchorProvider;

import net.certiv.antlrdt.graph.shapes.NodeShape;

public class TreeNodePartAnchorProvider extends DefaultAnchorProvider {

	@Override
	protected IGeometry computeAnchorageReferenceGeometry(DynamicAnchor anchor) {
		final NodeShape shape = ((TreeNodePart) getAdaptable()).getShape();

		if (shape != null) {
			return NodeUtils.localToParent(shape, NodeUtils.getShapeOutline(shape));
		}

		return NodeUtils.getShapeOutline(((TreeNodePart) getAdaptable()).getVisual());
	}
}
