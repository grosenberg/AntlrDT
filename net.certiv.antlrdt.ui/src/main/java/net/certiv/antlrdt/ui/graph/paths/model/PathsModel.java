package net.certiv.antlrdt.ui.graph.paths.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;

import net.certiv.antlrdt.core.parser.PathsData;
import net.certiv.antlrdt.core.parser.PathsData.Entry;
import net.certiv.antlrdt.core.parser.PathsNode;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.graph.IGraphModel;
import net.certiv.antlrdt.ui.graph.paths.PathsEditor;

public class PathsModel implements IGraphModel {

	/*
	 * The {@code fanoutNodes} map represents a directred graph having a root. The key set represents
	 * the unique, ordered set of nodes in the graph. The first key node is a root node. Each value set
	 * represents the unique, ordered set of fanout nodes that are directly connnected to a
	 * corresponding key node.
	 */
	private LinkedHashMap<PathsNode, LinkedHashSet<PathsNode>> fanoutNodes;
	private LinkedHashMap<PathsNode, ArrayList<PathsConnector>> connectors;

	private PathsData data;
	private PathsEditor editor;

	/**
	 * Constructs the model. Searches up the connection network from the target node to the network root
	 * node that represents the first grammar rule.
	 *
	 * @param data the connection network representing the rule and token relations of the grammar.
	 * @param target the name of the target node in the connecton network.
	 */
	public PathsModel(PathsEditor editor, PathsData data, String target) {
		this.editor = editor;
		this.data = data;
		fanoutNodes = new LinkedHashMap<>();
		connectors = new LinkedHashMap<>();
		boolean ok = addAllCallers(target);
		if (ok) buildConnectors();
	}

	// ------------------------------------------------

	@Override
	public void clear() {
		dispose();
		connectors = new LinkedHashMap<>();
	}

	public PathsNode namedPathsNode(String selected) {
		Entry entry = data.namedEntry(selected);
		return entry.getPathNode();
	}

	/** Returns the direct callers of the given target based on the existing fanouts. */
	public ArrayList<PathsNode> findCallers(PathsNode target) {
		ArrayList<PathsNode> callers = new ArrayList<>();
		for (Map.Entry<PathsNode, LinkedHashSet<PathsNode>> entry : fanoutNodes.entrySet()) {
			if (entry.getValue().contains(target)) {
				callers.add(entry.getKey());
			}
		}
		return callers;
	}

	/** Returns the direct callees of the given target based on the existing fanouts. */
	public List<PathsNode> findCallees(PathsNode target) {
		LinkedHashSet<PathsNode> callees = fanoutNodes.get(target);
		return new ArrayList<>(callees);
	}

	public List<PathsNode> getNodeList() {
		return new ArrayList<>(fanoutNodes.keySet());
	}

	// ------------------------------------------------

	/**
	 * Adds the direct callers of the given node to the model. References the underlying
	 * {@code PathsData} to find the actual callers.
	 */
	public void addSupPaths(PathsNode node) {
		Entry ne = data.namedEntry(node.getRuleName());
		List<PathsNode> callers = data.getCallingRules(ne);
		if (callers.isEmpty()) return;

		for (PathsNode caller : callers) {
			LinkedHashSet<PathsNode> fanoutSet = fanoutNodes.get(caller);
			if (fanoutSet == null) {
				fanoutSet = new LinkedHashSet<>();
				fanoutNodes.put(caller, fanoutSet);
			}
			fanoutSet.add(node);
		}
		buildConnectors();
	}

	/**
	 * Adds the direct callees of the given node to the model. References the underlying
	 * {@code PathsData} to find the actual callees.
	 */
	public void addSubPaths(PathsNode node) {
		Entry ne = data.namedEntry(node.getRuleName());
		List<PathsNode> callees = data.getCalledRules(ne);
		if (callees.isEmpty()) return;

		LinkedHashSet<PathsNode> fanoutSet = fanoutNodes.get(node);
		if (fanoutSet == null) {
			fanoutSet = new LinkedHashSet<>();
			fanoutNodes.put(node, fanoutSet);
		}
		for (PathsNode callee : callees) {
			addCallers(callee);
		}
		buildConnectors();
	}

	/** adds caller links to the given node, for callers within the existing network */
	private void addCallers(PathsNode callee) {
		Entry ne = data.namedEntry(callee.getRuleName());
		List<PathsNode> callers = data.getCallingRules(ne);
		for (PathsNode caller : callers) {
			LinkedHashSet<PathsNode> fanoutSet = fanoutNodes.get(caller);
			if (fanoutSet != null) {
				fanoutSet.add(callee);
			}
		}
	}

	// adds callees of the given node with caller links limited to within the existing network
	private boolean addAllCallers(String target) {
		Entry current = data.namedEntry(target);
		recurse(current, editor.getPrefs().getInt(PrefsKey.PT_DEPTH_LIMIT));
		return true;
	}

	/*
	 * Evaluates the caller relations, as identified from the network, for a current target entry.
	 * Recurses over each new caller until done.
	 */
	private void recurse(Entry current, int limit) {
		if (limit == 0) return;
		limit--;
		for (PathsNode caller : data.getCallingRules(current)) {
			boolean known = accumulate(caller, current.getPathNode());
			if (known) continue;	// caller already visited

			Entry nextCurrent = data.namedEntry(caller.getRuleName());
			if (nextCurrent != null) recurse(nextCurrent, limit);
		}
	}

	// records the relation between the caller and current nodes
	private boolean accumulate(PathsNode caller, PathsNode current) {
		boolean known = true;
		LinkedHashSet<PathsNode> fanoutSet = fanoutNodes.get(caller);
		if (fanoutSet == null) {
			fanoutSet = new LinkedHashSet<>();
			fanoutNodes.put(caller, fanoutSet);
			known = false;
		}
		fanoutSet.add(current);
		return known;
	}

	// ------------------------------------------------

	/**
	 * Removes the given node and connections to the immediate callee nodes of the given node. If the
	 * callee node has no other callers, removes that node.
	 */
	public void removeNode(PathsNode node) {
		removeSubNodes(node, 1);
		for (PathsNode caller : findCallers(node)) {
			fanoutNodes.get(caller).remove(node);
		}
		fanoutNodes.remove(node);
		buildConnectors();
	}

	private void removeSubNodes(PathsNode node, int level) {
		LinkedHashSet<PathsNode> callees = fanoutNodes.get(node);
		if (callees == null || callees.isEmpty()) return;

		LinkedHashSet<PathsNode> calleeSet = new LinkedHashSet<>(callees);
		for (PathsNode callee : calleeSet) {
			fanoutNodes.get(node).remove(callee);		// rm link to the callee
			ArrayList<PathsNode> callers = findCallers(callee);
			if (callers.size() == 0) {
				removeSubNodes(callee, level + 1);		// recurse
				fanoutNodes.remove(callee);				// rm callee
			}
		}
	}

	// ------------------------------------------------

	private void buildConnectors() {
		connectors.clear();
		for (PathsNode caller : fanoutNodes.keySet()) {
			LinkedHashSet<PathsNode> fanoutSet = fanoutNodes.get(caller);
			for (PathsNode callee : fanoutSet) {
				connect(caller, callee);
			}
		}
	}

	private void connect(PathsNode caller, PathsNode callee) {
		if (caller != null) {
			PathsConnector connector = new PathsConnector(caller, callee);
			addEndpoint(connector, caller);
			addEndpoint(connector, callee);
		}
	}

	private void addEndpoint(PathsConnector conn, PathsNode node) {
		ArrayList<PathsConnector> list = connectors.get(node);
		if (list == null) {
			list = new ArrayList<>();
			connectors.put(node, list);
		}
		list.add(conn);
	}

	// ------------------------------------------------

	@Override
	public void dispose() {
		if (connectors != null) {
			connectors.clear();
			connectors = null;
		}
	}

	@Override
	public String toString() {
		return "PathsModel [connectors=" + connectors.size() + "]";
	}
}
