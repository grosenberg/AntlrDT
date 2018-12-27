package net.certiv.antlrdt.graph.parts;

import java.util.Map;

import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IContentPartFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.models.EdgeModel;
import net.certiv.antlrdt.graph.models.NodeModel;

/** The {@link TreePartsFactory} creates parts for the graph. */
public class TreePartsFactory implements IContentPartFactory {

	@Inject
	private Injector injector;

	@Override
	public IContentPart<? extends javafx.scene.Node> createContentPart(Object content, Map<Object, Object> map) {

		if (content instanceof DiagramModel) return injector.getInstance(TreeModelPart.class);
		if (content instanceof NodeModel) return injector.getInstance(TreeNodePart.class);
		if (content instanceof EdgeModel) return injector.getInstance(TreeEdgePart.class);

		throw new IllegalArgumentException("Unknown model element: <" + content + ">");
	}
}
