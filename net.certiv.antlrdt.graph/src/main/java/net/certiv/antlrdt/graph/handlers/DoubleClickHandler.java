package net.certiv.antlrdt.graph.handlers;

import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;

import net.certiv.antlrdt.graph.parts.TreeNodePart;
import net.certiv.antlrdt.graph.util.EditorUtil;

public class DoubleClickHandler extends AbstractHandler implements IOnClickHandler {

	@Override
	public void click(MouseEvent event) {
		if (event.getClickCount() != 2) return;

		IVisualPart<? extends Node> host = getHost();
		if (host instanceof TreeNodePart) {
			TreeNodePart part = (TreeNodePart) host;
			EditorUtil.openSourceFileAtRule(part.getContent());
		}
	}
}
