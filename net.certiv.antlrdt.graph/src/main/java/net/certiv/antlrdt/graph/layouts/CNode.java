package net.certiv.antlrdt.graph.layouts;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javafx.collections.ObservableMap;

import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Node;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

import net.certiv.antlrdt.graph.models.NodeModel;

/** Wrapper class holding layout parameters. */
public class CNode implements Comparable<CNode> {

	final CGraph graph;	// back reference
	final Node node;	// the wrapped entity
	final Point loc;	// node layout location

	boolean virtual;	// virtual root
	int id = -1;		// node label
	int rank = -1;		// rank label
	int degree;			// number of incomming edges

	final List<CNode> parents = Lists.newArrayList();
	final List<CNode> children = Lists.newArrayList();
	private LinkedHashSet<Edge> inEdges;
	private LinkedHashSet<Edge> outEdges;

	// virtual root node contructor
	public CNode(CGraph graph) {
		this(graph, new Node());
		virtual = true;
	}

	// real node contructor
	public CNode(CGraph graph, Node node) {
		this.graph = graph;
		this.node = node;
		this.loc = new Point();
	}

	public ObservableMap<String, Object> getAttributes() {
		return node.getAttributes();
	}

	public boolean isTerminal() {
		Set<Edge> outs = node.getAllOutgoingEdges();
		if (outs.isEmpty()) return true;
		for (Edge out : outs) {
			NodeModel target = (NodeModel) out.getTarget();
			if (!target.isHidden()) return false;
		}
		return true;
	}

	// return the non-transitive input edges: trace out edges that cyle to an in edge
	public Set<Edge> getIncomingEdges() {
		if (inEdges == null) {
			Set<Node> visited = Sets.newLinkedHashSet();
			inEdges = Sets.newLinkedHashSet(node.getAllIncomingEdges());
			dfs(visited, inEdges, node);
		}
		return inEdges;
	}

	// depth first search
	private void dfs(Set<Node> visited, Set<Edge> incoming, Node node) {
		if (!visited.contains(node)) {
			visited.add(node);
			incoming.removeAll(node.getAllOutgoingEdges());
			for (Edge out : node.getAllOutgoingEdges()) {
				dfs(visited, incoming, out.getTarget());
			}
		}
	}

	public Set<Edge> getOutgoingEdges() {
		if (outEdges == null) {
			outEdges = Sets.newLinkedHashSet();
			for (Edge out : node.getAllOutgoingEdges()) {
				CNode target = graph.lookup.get(out.getTarget());
				if (children.contains(target)) {
					outEdges.add(out);
				}
			}
		}
		return outEdges;
	}

	public String getText() {
		return ((NodeModel) node).getDisplayText();
	}

	@Override
	public int compareTo(CNode o) {
		if (rank < o.rank) return -1;
		if (rank > o.rank) return 1;
		if (id < o.id) return -1;
		if (id > o.id) return 1;
		return 0;
	}

	@Override
	public String toString() {
		List<String> source = parents.stream().map(elem -> elem.getText()).collect(Collectors.toList());
		List<String> target = children.stream().map(elem -> elem.getText()).collect(Collectors.toList());
		return source + " -> " + target;
	}
}
