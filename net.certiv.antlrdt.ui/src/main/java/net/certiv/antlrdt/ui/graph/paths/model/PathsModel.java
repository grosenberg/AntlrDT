package net.certiv.antlrdt.ui.graph.paths.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import net.certiv.antlrdt.core.parser.PathsData;
import net.certiv.antlrdt.core.parser.PathsNode;
import net.certiv.antlrdt.core.parser.PathsData.Entry;
import net.certiv.antlrdt.ui.graph.IGraphModel;
import net.certiv.dsl.core.util.Log;

public class PathsModel implements IGraphModel {

	private LinkedHashMap<PathsNode, ArrayList<PathsConnector>> connectors;
	private LinkedHashMap<PathsNode, LinkedHashSet<PathsNode>> filteredData;

	private PathsData data;

	public PathsModel() {
		this.connectors = new LinkedHashMap<>();
		this.filteredData = new LinkedHashMap<>();
	}

	public PathsModel(PathsData data, String ruleName) {
		this();
		this.data = data;
		boolean ok = filterData(ruleName);
		if (ok) connectGraph();
	}

	private boolean filterData(String ruleName) {
		Entry start = data.namedEntry(ruleName);
		if (start == null) {
			Log.error(this, "Start failed (" + ruleName + ")");
			return false;
		}
		filterData(start);
		return true;
	}

	// 'out' = rules that contain 'in' from 'data'
	// add out to filtered
	// recurse with in = out
	// until out is empty
	private void filterData(Entry in) {
		for (PathsNode item : data.containingRules(in)) {
			boolean known = accumulateData(item, in.getRef());
			if (known) continue;		// item known; already been visited

			Entry out = data.namedEntry(item.getRuleName());
			if (out != null) {
				filterData(out);		// recurse
			}
		}
	}

	// 'entry' is a rule that contains the term 'in'
	// the set of 'in' terms are those that are on the filtered path
	private boolean accumulateData(PathsNode out, PathsNode pathsNode) {
		boolean known = true;
		LinkedHashSet<PathsNode> set = filteredData.get(out);
		if (set == null) {
			set = new LinkedHashSet<>();
			filteredData.put(out, set);
			known = false;
		}
		set.add(pathsNode);
		return known;
	}

	private void connectGraph() {
		for (PathsNode parent : filteredData.keySet()) {
			LinkedHashSet<PathsNode> terms = filteredData.get(parent);
			for (PathsNode child : terms) {
				add(parent, child);
			}
		}
	}

	private void add(PathsNode parent, PathsNode child) {
		if (parent != null) {
			PathsConnector connector = new PathsConnector(parent, child);
			addEndpoint(parent, connector);
			addEndpoint(child, connector);
		}
	}

	private void addEndpoint(PathsNode parent, PathsConnector conn) {
		ArrayList<PathsConnector> list = connectors.get(parent);
		if (list == null) {
			list = new ArrayList<PathsConnector>();
			connectors.put(parent, list);
		}
		list.add(conn);
	}

	@Override
	public void clear() {
		dispose();
		connectors = new LinkedHashMap<>();
	}

	public PathsNode namedPathsNode(String selected) {
		Entry entry = data.namedEntry(selected);
		return entry.getRef();
	}

	public ArrayList<PathsNode> findCallers(PathsNode target) {
		ArrayList<PathsNode> sources = new ArrayList<>();
		ArrayList<PathsConnector> conns = connectors.get(target);
		if (conns == null) return sources;
		for (PathsConnector conn : conns) {
			if (conn.getTarget().equals(target)) {
				sources.add(conn.getSource());
			}
		}
		return sources;
	}

	public List<PathsNode> findCallees(PathsNode source) {
		ArrayList<PathsNode> targets = new ArrayList<>();
		ArrayList<PathsConnector> conns = connectors.get(source);
		if (conns == null) return targets;
		for (PathsConnector conn : conns) {
			if (conn.getSource() != null && conn.getSource().equals(source)) {
				targets.add(conn.getTarget());
			}
		}
		return targets;
	}

	public List<PathsNode> getNodeList() {
		if (connectors != null) {
			Set<PathsNode> nodes = connectors.keySet();
			return new ArrayList<PathsNode>(nodes);
		}
		return Collections.emptyList();
	}

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
