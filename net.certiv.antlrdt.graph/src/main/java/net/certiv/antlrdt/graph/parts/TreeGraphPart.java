package net.certiv.antlrdt.graph.parts;

import java.util.List;

import javafx.scene.Group;
import javafx.scene.Node;

import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;

import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.models.NodeModel;

public class TreeGraphPart extends AbstractContentPart<Group> {

	@Override
	public DiagramModel getContent() {
		return (DiagramModel) super.getContent();
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		List<Object> children = Lists.newArrayList();
		children.addAll(getContent().getEdges()); // order forces edges crossing nodes
		children.addAll(getContent().getNodes()); // to be drawn under the nodes!
		return children;
	}

	@Override
	protected void doAddContentChild(Object child, int index) {
		if (child instanceof NodeModel) {
			getContent().addNode((NodeModel) child, index);
		} else {
			throw new IllegalArgumentException("Content child type invalid: " + child.getClass());
		}
	}

	@Override
	protected void doRemoveContentChild(Object contentChild) {
		if (contentChild instanceof NodeModel) {
			getContent().removeNode((NodeModel) contentChild);
		} else {
			throw new IllegalArgumentException("Content child type invalid: " + contentChild.getClass());
		}
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		return HashMultimap.create();
	}

	@Override
	protected Group doCreateVisual() {
		Group visual = new Group();
		visual.setAutoSizeChildren(false);
		return visual;
	}

	@Override
	protected void doRefreshVisual(Group visual) {}

	@Override
	protected void doAddChildVisual(IVisualPart<? extends Node> child, int index) {
		getVisual().getChildren().add(index, child.getVisual());
	}

	@Override
	protected void doRemoveChildVisual(IVisualPart<? extends Node> child, int index) {
		getVisual().getChildren().remove(index);
	}

	@Override
	public boolean isSelectable() {
		return false;
	}
}
