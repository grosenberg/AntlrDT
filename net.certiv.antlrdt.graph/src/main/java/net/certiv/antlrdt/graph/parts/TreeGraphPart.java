package net.certiv.antlrdt.graph.parts;

import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;

import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.SetMultimap;

import net.certiv.antlrdt.graph.models.DiagramModel;

/** The {@link TreeModelPart} creates visuals from the {@link DiagramModel}. */
public class TreeModelPart extends AbstractContentPart<Group> {

	private Group rootGroup;

	@Override
	public DiagramModel getContent() {
		return (DiagramModel) super.getContent();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return getContent().getNodes();
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected Group doCreateVisual() {
		rootGroup = new Group();
		return rootGroup;
	}

	@Override
	protected void doRefreshVisual(Group visual) {}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		rootGroup.getChildren().add(index, child.getVisual());
	}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		rootGroup.getChildren().remove(index);
	}
}
