package net.certiv.antlrdt.graph.providers;

import javafx.scene.Group;
import javafx.scene.Node;

import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Path;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;

import net.certiv.antlrdt.graph.util.FXUtil;

public class LineVisualBoundsProvider extends ShapeOutlineProvider {

	@Override
	public IGeometry get() {
		Path fullPath = new Path();
		Group group = (Group) getAdaptable().getVisual();
		for (Node node : group.getChildren()) {
			Path nodePath = (Path) NodeUtils.localToParent(node, FXUtil.toPath(node));
			fullPath.add(nodePath.getSegments());
		}
		return fullPath;
	}
}
