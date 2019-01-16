package net.certiv.antlrdt.graph.parse;

import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.common.collect.Maps;

import net.certiv.antlrdt.core.parser.ITargetInfo;
import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.models.EdgeModel;
import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.dsl.core.util.stores.Pair;

public class TreeProcessor extends ParseTreeWalker implements ParseTreeListener {

	private final ITargetInfo info;
	private final DiagramModel model;

	private final Map<ParseTree, NodeModel> elements = Maps.newLinkedHashMap();

	public TreeProcessor(ITargetInfo info, DiagramModel model) {
		super();
		this.info = info;
		this.model = model;
	}

	public DiagramModel getModel() {
		return model;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		addElements(ctx.getParent(), ctx);
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		addElements(node.getParent(), node);
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		addElements(node.getParent(), node);
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {}

	/** Add a connected node pair if the parent is non-null. Otherwise just add the child node. */
	private void addElements(ParseTree parent, ParseTree child) {
		if (child != null) {
			Pair<NodeModel, Boolean> source = createNodeModel(parent);
			Pair<NodeModel, Boolean> target = createNodeModel(child);

			EdgeModel conn = null;
			if (source != null) {
				conn = new EdgeModel(model, source.left, target.left);
				source.left.addOutgoingConnection(conn);
				target.left.addIncomingConnection(conn);
				model.addEdge(conn);
				if (source.right) model.addNode(source.left);
			}
			if (target.right) model.addNode(target.left);
		}
	}

	private Pair<NodeModel, Boolean> createNodeModel(ParseTree ctx) {
		if (ctx == null) return null;

		NodeModel nodeModel = elements.get(ctx);
		if (nodeModel != null) return Pair.of(nodeModel, false);

		nodeModel = new NodeModel();
		nodeModel.init(ctx, info);
		elements.put(ctx, nodeModel);
		return Pair.of(nodeModel, true);
	}
}
