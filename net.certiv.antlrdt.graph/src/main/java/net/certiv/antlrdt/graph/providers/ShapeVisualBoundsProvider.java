package net.certiv.antlrdt.graph.providers;

import javafx.scene.Group;
import javafx.scene.Node;

import org.eclipse.gef.fx.utils.NodeUtils;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.mvc.fx.providers.ShapeOutlineProvider;

import net.certiv.antlrdt.graph.util.FXUtil;

public class ShapeVisualBoundsProvider extends ShapeOutlineProvider {

	@Override
	public IGeometry get() {
		Group group = (Group) getAdaptable().getVisual();
		if (!group.getChildren().isEmpty()) {
			Node node = group.getChildren().get(0);
			return NodeUtils.localToParent(node, FXUtil.toPath(node));
		}
		return super.get();
	}
}
