package net.certiv.antlrdt.graph.view.tree;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.gef.graph.Edge;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.certiv.antlrdt.core.parser.IPathProcessor;
import net.certiv.antlrdt.core.parser.Path;
import net.certiv.antlrdt.core.parser.gen.PathVisitor;
import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.models.EdgeModel;
import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.util.stores.Pair;

public class PathsProcessor implements IPathProcessor {

	class Elem {
		ParserRuleContext rule;
		Path kind;
		final List<String> children = Lists.newArrayList();

		protected Elem(ParserRuleContext rule, Path kind) {
			this.rule = rule;
			this.kind = kind;
		}

		public void addChild(String text) {
			children.add(text);
		}
	}

	private final Deque<Elem> stack = new ArrayDeque<>();

	// key = rule context; value = context model
	private final Map<ParseTree, NodeModel> elements = Maps.newLinkedHashMap();
	// key = rule name; value = context model
	private final Map<String, NodeModel> names = Maps.newLinkedHashMap();

	private final List<Elem> connections = Lists.newArrayList();

	private DslParseRecord record;
	private DiagramModel model;

	public PathsProcessor(DslParseRecord record) {
		this.record = record;
		model = new DiagramModel();
	}

	public DiagramModel getModel(PathsView pathsView, String rulename) {
		try {
			PathVisitor walker = new PathVisitor(record.tree);
			walker.setHelper(this);
			walker.findAll();
		} catch (Exception e) {
			Log.error(this, "Error in paths walk: " + e.getMessage());
			return null;
		}

		for (Elem conn : connections) {
			NodeModel source = elements.get(conn.rule);
			for (String child : conn.children) {
				NodeModel target = names.get(child);
				if (target != null) {
					if (!connected(source, target)) {
						EdgeModel edgeModel = new EdgeModel(model, source, target);
						edgeModel.setHidden(true);
						source.addOutgoingConnection(edgeModel);
						target.addIncomingConnection(edgeModel);
						model.addEdge(edgeModel);
					}
				}
			}
		}

		NodeModel target = names.get(rulename);
		if (target == null) return null;

		unhideRootPaths(target);
		return model;
	}

	private void unhideRootPaths(NodeModel target) {
		target.setHidden(false);
		for (Edge in : target.getAllIncomingEdges()) {
			EdgeModel edge = (EdgeModel) in;
			edge.setHidden(false);
			NodeModel source = edge.getSource();
			if (source.isHidden()) unhideRootPaths(source);
		}
	}

	/** Unhide the nodes connected as outbound path 'callees' of the given rulename identified node. */
	public void addCalleePaths(String rulename) {
		NodeModel source = names.get(rulename);
		for (Edge out : source.getAllOutgoingEdges()) {
			EdgeModel edge = (EdgeModel) out;
			edge.setHidden(false);
			NodeModel target = edge.getTarget();
			target.setHidden(false);
		}
	}

	/** Unhide the nodes connected as inbound path 'callers' of the given rulename identified node. */
	public void addCallerPaths(String rulename) {
		NodeModel target = names.get(rulename);
		for (Edge in : target.getAllIncomingEdges()) {
			EdgeModel edge = (EdgeModel) in;
			edge.setHidden(false);
			NodeModel source = edge.getSource();
			source.setHidden(false);
		}
	}

	public void removeNode(String rulename) {
		NodeModel source = names.get(rulename);
		source.setHidden(true);
		for (Edge in : source.getAllIncomingEdges()) {
			EdgeModel edge = (EdgeModel) in;
			if (!edge.isHidden()) {
				edge.setHidden(true);
				NodeModel node = edge.getSource();
				if (orphan(node)) node.setHidden(true);
			}
		}
		for (Edge out : source.getAllOutgoingEdges()) {
			EdgeModel edge = (EdgeModel) out;
			if (!edge.isHidden()) {
				edge.setHidden(true);
				NodeModel node = edge.getTarget();
				if (orphan(node)) node.setHidden(true);
			}
		}
	}

	private boolean orphan(NodeModel source) {
		for (Edge in : source.getAllIncomingEdges()) {
			EdgeModel edge = (EdgeModel) in;
			if (!edge.isHidden()) return false;
		}
		for (Edge out : source.getAllOutgoingEdges()) {
			EdgeModel edge = (EdgeModel) out;
			if (!edge.isHidden()) return false;
		}
		return true;
	}

	// -------------------------------------------
	// Tree walker callbacks

	@Override
	public void startRule(ParserRuleContext rule, Path kind) {
		Log.debug(this, "Starting " + kind);
		stack.push(new Elem(rule, kind));
	}

	@Override
	public void endRule(Path kind) {
		Log.debug(this, "Ending   " + kind);
		connections.add(stack.pop());
	}

	/** Add a rule node. */
	@Override
	public void addPathRule(ParserRuleContext rule, Token term, Path kind) {
		Log.debug(this, "Rule     " + term.getText());

		Pair<NodeModel, Boolean> source = createNodeModel(rule, term, kind);
		if (source.right) model.addNode(source.left);
	}

	@Override
	public void addPathChild(Token term) {
		Log.debug(this, "Child    " + term.getText());
		stack.peek().addChild(term.getText());
	}

	private Pair<NodeModel, Boolean> createNodeModel(ParseTree ctx, Token term, Path kind) {
		if (ctx == null) return null;

		NodeModel nodeModel = elements.get(ctx);
		if (nodeModel != null) return Pair.of(nodeModel, false);

		nodeModel = new NodeModel();
		nodeModel.init(ctx, new PathInfo(term, kind, record));
		nodeModel.setHidden(true);
		elements.put(ctx, nodeModel);
		names.put(term.getText(), nodeModel);
		return Pair.of(nodeModel, true);
	}

	private boolean connected(NodeModel source, NodeModel target) {
		for (Edge edge : source.getAllOutgoingEdges()) {
			if (edge.getTarget().equals(target)) return true;
		}
		return false;
	}
}
