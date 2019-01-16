package net.certiv.antlrdt.graph.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

import javafx.beans.property.ReadOnlyMapProperty;
import javafx.collections.ObservableMap;

import org.eclipse.gef.common.attributes.IAttributeStore;
import org.eclipse.gef.graph.Graph;

import com.google.common.collect.Lists;

import net.certiv.antlrdt.core.parser.ITargetInfo;
import net.certiv.antlrdt.graph.GraphUI;
import net.certiv.antlrdt.graph.actions.Layout;
import net.certiv.antlrdt.graph.actions.Router;
import net.certiv.antlrdt.graph.behaviors.GraphLayoutBehavior;
import net.certiv.antlrdt.graph.view.tree.TreeView;
import net.certiv.dsl.core.log.Log;

public class DiagramModel implements IModel, IAttributeStore {

	private final PropertyChangeSupport queue = new PropertyChangeSupport(this);

	private final List<NodeModel> nodes = Lists.newArrayList();
	private final List<EdgeModel> edges = Lists.newArrayList();

	private ITargetInfo info;
	private Graph graph;

	private GraphLayoutBehavior behavior;

	public DiagramModel() {
		super();
	}

	@Override
	public ReadOnlyMapProperty<String, Object> attributesProperty() {
		return graph.attributesProperty();
	}

	@Override
	public ObservableMap<String, Object> getAttributes() {
		return graph.getAttributes();
	}

	public ITargetInfo getTargetInfo() {
		return info;
	}

	public void setTargetInfo(ITargetInfo info) {
		this.info = info;
	}

	public void addNode(NodeModel model) {
		nodes.add(model);
		fire(PROP_NODE, null, model);
	}

	public void addNode(NodeModel model, int index) {
		nodes.add(index, model);
		fire(PROP_NODE, null, model);
	}

	public void removeNode(NodeModel model) {
		nodes.remove(model);
		fire(PROP_NODE, model, null);
	}

	public List<NodeModel> getNodes() {
		return Lists.newArrayList(nodes);
	}

	public void addEdge(EdgeModel model) {
		edges.add(model);
		fire(PROP_EDGE, null, model);
	}

	public void addEdge(EdgeModel model, int index) {
		edges.add(index, model);
		fire(PROP_EDGE, null, model);
	}

	public void removeEdge(EdgeModel model) {
		edges.remove(model);
		fire(PROP_EDGE, model, null);
	}

	public List<EdgeModel> getEdges() {
		return Lists.newArrayList(edges);
	}

	// Property Change Support
	public void fire(String prop, Object oldValue, Object newValue) {
		queue.firePropertyChange(prop, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		queue.addPropertyChangeListener(listener);
		Log.info(this, "Adding property change for " + listener.toString());
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		queue.removePropertyChangeListener(listener);
	}

	// --------------

	// Graph construction

	public Graph getGraph() {
		if (graph == null) {
			graph = new Graph();
			graph.getAttributes().put(MODEL, this);
			disableLayout();
			graph.getNodes().addAll(nodes);
			graph.getEdges().addAll(edges);
			enableLayout();
		}
		return graph;
	}

	public void doLayout() {
		enableLayout();
		behavior.applyLayout();
	}

	public void setLayoutBehavior(GraphLayoutBehavior behavior) {
		this.behavior = behavior;
	}

	public void enableLayout() {
		if (graph != null) graph.getAttributes().put(LAYOUT_ENABLED, true);
	}

	public void disableLayout() {
		if (graph != null) graph.getAttributes().put(LAYOUT_ENABLED, false);
	}

	public Layout getLayout() {
		String name = GraphUI.getSettings().get(TreeView.NODE_LAYOUT);
		return Layout.getEnum(name);
	}

	public void setLayout(Layout layout) {
		if (layout == null) return;

		Layout prior = getLayout();
		if (prior != layout) {
			GraphUI.getSettings().put(TreeView.NODE_LAYOUT, layout.getDisplayName());
			fire(PROP_LAYOUT, prior, layout);
		}
	}

	public Router getRouter() {
		String name = GraphUI.getSettings().get(TreeView.EDGE_ROUTER);
		return Router.getEnum(name);
	}

	public void setRouter(Router router) {
		if (router == null) return;

		Router prior = getRouter();
		if (prior != router) {
			GraphUI.getSettings().put(TreeView.EDGE_ROUTER, router.getDisplayName());
			fire(PROP_ROUTER, prior, router);
		}
	}

	// --------------

	// Paths support

	// public List<ParseTree> getParseTreeList() {
	// return Lists.newArrayList(elements.keySet());
	// }
	//
	// public List<ParseTree> findCallers(ParseTree ctx) {
	// List<ParseTree> targets = Lists.newArrayList();
	//
	// NodeModel elem = elements.get(ctx);
	// if (elem == null) return targets;
	//
	// List<EdgeModel> conns = elem.getIncomingConnections();
	// for (EdgeModel conn : conns) {
	// targets.add(conn.getSource().ctx);
	// }
	// return targets;
	// }
	//
	// public List<ParseTree> findCallees(ParseTree ctx) {
	// List<ParseTree> targets = Lists.newArrayList();
	//
	// NodeModel elem = elements.get(ctx);
	// if (elem == null) return targets;
	//
	// List<EdgeModel> conns = elem.getOutgoingConnections();
	// for (EdgeModel conn : conns) {
	// targets.add(conn.getTarget().ctx);
	// }
	// return targets;
	// }

	public void clear() {
		dispose();
	}

	public void dispose() {
		if (graph != null) {
			graph.getAttributes().clear();
			// graph.getNodes().clear();
			// graph.getEdges().clear();
			graph = null;
		}
		for (NodeModel model : nodes) {
			model.dispose();
		}
	}
}
