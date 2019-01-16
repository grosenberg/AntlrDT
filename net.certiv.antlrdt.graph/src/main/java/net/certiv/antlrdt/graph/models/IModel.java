package net.certiv.antlrdt.graph.models;

/** Base class providing {@link PropertyChangeSupport} */
public interface IModel {

	// graph construction
	String LABEL = "label";
	String CSS_ID = "css_id";
	String ICON_URL = "node_icon_url";
	String MODEL = "node_model";

	// node event types: addition/remove, location, & size change
	String PROP_NODE = "nodeElement";
	String PROP_EDGE = "edgeElement";
	String PROP_BOUNDS = "bounds";
	String PROP_LAYOUT = "layout";
	String PROP_ROUTER = "router";

	// graph attributes
	String LAYOUT_ENABLED = "graph-layout-enabled";
	String LAYOUT_ALGORITHM = "graph-layout-algorithm";
	String LAYOUT_ROUTER = "edge-router";

	// node connection change event types
	String PROP_INCOMING_CONNECTIONS = "incomingConnections";
	String PROP_OUTGOING_CONNECTIONS = "outgoingConnections";

	// edge connection change event types
	String PROP_SOURCE = "sourceNode";
	String PROP_TARGET = "targetNode";

	int SPREAD = 2000;
}
