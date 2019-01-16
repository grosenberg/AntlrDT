package net.certiv.antlrdt.graph.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Random;

import org.eclipse.core.resources.IFile;
import org.eclipse.gef.geometry.planar.Point;

import net.certiv.dsl.core.util.Strings;

/** Base class providing {@link PropertyChangeSupport} */
public class BaseModel {

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	// graph construction
	public static final String LABEL = "label";
	public static final String CSS_ID = "css_id";
	public static final String ICON_URL = "node_icon_url";
	public static final String MODEL = "node_model";

	// node event types: addition/remove, location, & size change
	public static final String PROP_NODE = "nodeElement";
	public static final String PROP_CONN = "connElement";
	public static final String PROP_BOUNDS = "bounds";
	public static final String PROP_LAYOUT = "layout";
	public static final String PROP_ROUTER = "router";

	// node connection change event types
	public static final String PROP_INCOMING_CONNECTIONS = "incomingConnections";
	public static final String PROP_OUTGOING_CONNECTIONS = "outgoingConnections";

	// edge connection change event types
	public static final String PROP_SOURCE = "sourceNode";
	public static final String PROP_TARGET = "targetNode";

	private static final int SPREAD = 2000;

	// parser data
	protected final IFile grammar;
	protected final String[] ruleNames;
	protected final String[] tokenNames;
	private Random ran;

	public BaseModel() {
		this(null, Strings.EMPTY_STRINGS, Strings.EMPTY_STRINGS);
	}

	public BaseModel(IFile grammar, String[] ruleNames, String[] tokenNames) {
		this.grammar = grammar;
		this.ruleNames = ruleNames;
		this.tokenNames = tokenNames;
		ran = new Random();
	}

	// Model Construction

	protected Point ranPoint() {
		return new Point(ran.nextDouble() * SPREAD, ran.nextDouble() * SPREAD);
	}

	public IFile getGrammar() {
		return grammar;
	}

	// Property Change Support

	public void fire(String prop, Object oldValue, Object newValue) {
		pcs.firePropertyChange(prop, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		pcs.removePropertyChangeListener(listener);
	}

	// protected Node makeNode(Graph graph, NodeModel model) {
	// Node node = cache.get(model);
	// if (node != null) return node;
	//
	// node = buildNode(model);
	// LayoutProperties.setLocation(node, model.getLocation());
	// LayoutProperties.setSize(node, model.getDimension());
	// cache.put(model, node);
	// graph.getNodes().add(node);
	// return node;
	// }
	//
	// protected Node buildNode(NodeModel model) {
	// Node.Builder builder = new Node.Builder();
	// builder.attr(ICON_URL, model.getIconUrl());
	// builder.attr(LABEL, model.getDisplayText());
	// builder.attr(CSS_ID, model.getCssID());
	// builder.attr(MODEL, model);
	// return builder.buildNode();
	// }
	//
	// protected Edge makeEdge(Graph graph, Node beg, Node end, Object... attr) {
	// Edge edge = makeEdge(beg, end, attr);
	// graph.getEdges().add(edge);
	// return edge;
	// }
	//
	// protected Edge makeEdge(Node beg, Node end, Object... attr) {
	// Builder builder = new Edge.Builder(beg, end);
	// for (int idx = 0; idx < attr.length; idx += 2) {
	// builder.attr(attr[idx].toString(), attr[idx + 1]);
	// }
	// return builder.buildEdge();
	// }
}
