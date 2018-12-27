package net.certiv.antlrdt.graph.models;

import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.core.resources.IFile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.certiv.antlrdt.graph.actions.Layout;
import net.certiv.antlrdt.graph.actions.Router;

public class DiagramModel extends BaseModel {

	private final Map<ParseTree, NodeModel> elements = Maps.newLinkedHashMap();

	private final List<NodeModel> nodes = Lists.newArrayList();
	private final List<EdgeModel> connections = Lists.newArrayList();

	private Layout layout;
	private Router router;

	public DiagramModel(IFile grammar, String[] ruleNames, String[] tokenNames) {
		super(grammar, ruleNames, tokenNames);
	}

	/** Add a connected node pair. Called by the parse-tree walker. */
	public void addElements(ParseTree parent, ParseTree child) {
		if (parent != null && child != null) {
			NodeModel source = createNodeModel(parent);
			NodeModel target = createNodeModel(child);

			addElement(source);
			addElement(target);

			EdgeModel conn = new EdgeModel(source, target);
			addConnection(conn);
			source.addOutgoingConnection(conn);
			target.addIncomingConnection(conn);
		}
	}

	private NodeModel createNodeModel(ParseTree ctx) {
		NodeModel model = elements.get(ctx);
		if (model == null) {
			model = new NodeModel(ctx, grammar, ruleNames, tokenNames);
		}
		return model;
	}

	public void addElement(NodeModel model) {
		if (elements.get(model.ctx) == null) {
			elements.put(model.ctx, model);
			nodes.add(model);
			fire(PROP_NODE, null, model);
		}
	}

	public void removeElement(NodeModel model) {
		elements.remove(model.ctx);
		nodes.remove(model);
		fire(PROP_NODE, model, null);
	}

	public List<NodeModel> getNodes() {
		return Lists.newArrayList(elements.values());
	}

	public void addConnection(EdgeModel conn) {
		connections.add(conn);
		fire(PROP_CONN, null, conn);
	}

	public void removeConnection(EdgeModel model) {
		connections.remove(model);
		fire(PROP_CONN, model, null);
	}

	public List<EdgeModel> getConnections() {
		return Lists.newArrayList(connections);
	}

	public Layout getLayout() {
		return layout;
	}

	public void setLayout(Layout layout) {
		layout = layout != null ? layout : Layout.SPRING;
		if (this.layout != layout) {
			Layout prior = this.layout;
			this.layout = layout;
			fire(PROP_LAYOUT, (prior != null ? prior.getAlgorithm() : null), layout.getAlgorithm());
		}
	}

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		router = router != null ? router : Router.FAN;
		if (this.router != router) {
			Router prior = this.router;
			this.router = router;
			fire(PROP_ROUTER, (prior != null ? prior.getRouter() : null), router.getRouter());
		}
	}

	public List<ParseTree> getParseTreeList() {
		return Lists.newArrayList(elements.keySet());
	}

	public List<ParseTree> findCallers(ParseTree ctx) {
		List<ParseTree> targets = Lists.newArrayList();

		NodeModel elem = elements.get(ctx);
		if (elem == null) return targets;

		List<EdgeModel> conns = elem.getIncomingConnections();
		for (EdgeModel conn : conns) {
			targets.add(conn.getSource().ctx);
		}
		return targets;
	}

	public List<ParseTree> findCallees(ParseTree ctx) {
		List<ParseTree> targets = Lists.newArrayList();

		NodeModel elem = elements.get(ctx);
		if (elem == null) return targets;

		List<EdgeModel> conns = elem.getOutgoingConnections();
		for (EdgeModel conn : conns) {
			targets.add(conn.getTarget().ctx);
		}
		return targets;
	}

	public void clear() {
		dispose();
	}

	public void dispose() {
		for (NodeModel model : elements.values()) {
			model.dispose();
		}
		elements.clear();
		connections.clear();
	}
}
