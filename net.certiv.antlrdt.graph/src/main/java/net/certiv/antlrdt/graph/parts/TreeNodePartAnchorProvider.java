package net.certiv.antlrdt.graph.parts;

import org.eclipse.gef.fx.anchors.DynamicAnchor;
import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.mvc.fx.providers.DefaultAnchorProvider;

public class TreeNodePartAnchorProvider extends DefaultAnchorProvider {

	@Override
	protected IGeometry computeAnchorageReferenceGeometry(DynamicAnchor anchor) {
		return NodeUtils.getShapeOutline(((TreeNodePart) getAdaptable()).getVisual());
	}
}
