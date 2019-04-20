package net.certiv.antlrdt.graph.layouts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Node;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.graph.layouts.CoffmanGrahamLayoutAlgorithm.Flow;
import net.certiv.dsl.core.preferences.DslPrefsManager;

public class CGraph {

	public static final String EID = "EdgeID";

	// key = node model; value = wrapping node
	final Map<Node, CNode> lookup = Maps.newLinkedHashMap();

	CNode root;
	final LinkedHashSet<Node> nodes = Sets.newLinkedHashSet();
	final List<CLayer> layers = Lists.newArrayList();
	final Flow style;

	double sSiblings;	// spacing between sibling nodes
	double sSubtrees;	// spacing between subtrees
	double sDepth;		// spacing between depth levels
	double sRoot;		// pixel offset for root node position

	public CGraph(Flow style, Node[] nodes) {
		this.style = style;
		this.nodes.addAll(Arrays.asList(nodes));
		init();
	}

	// makes a layout graph from the given nodes
	private void init() {
		if (nodes.size() == 0) return;

		DslPrefsManager prefsMgr = AntlrCore.getDefault().getPrefsManager();
		sSiblings = prefsMgr.getInt(PrefsKey.PT_SIBLING_SPACING);
		sSubtrees = prefsMgr.getInt(PrefsKey.PT_SUBTREE_SPACING);
		sDepth = prefsMgr.getInt(PrefsKey.PT_DEPTH_SPACING);
		sRoot = prefsMgr.getInt(PrefsKey.PT_ROOT_OFFSET);

		// 1) wrap nodes & collect terminals

		Set<CNode> terminals = Sets.newLinkedHashSet();
		for (Node node : nodes) {
			CNode cnode = new CNode(this, node);
			lookup.put(node, cnode);
			if (cnode.isTerminal()) terminals.add(cnode);
		}

		// 2) construct well-ordered directed acyclic graph

		// roots are parentless nodes
		List<CNode> roots = Lists.newArrayList(lookup.values());

		// build graph
		for (CNode parent : lookup.values()) {
			for (Edge edge : parent.node.getAllOutgoingEdges()) {
				CNode child = lookup.get(edge.getTarget());
				if (child == null) continue;
				child.parents.add(parent);
				parent.children.add(child);
				roots.remove(child); // child cannot be a root
			}
		}
		if (roots.size() == 0) return;

		// remove cycles
		for (CNode cnode : lookup.values()) {
			Set<Edge> dagin = cnode.getIncomingEdges();
			Set<Edge> edges = cnode.node.getAllIncomingEdges();
			edges.removeAll(dagin); // result is set of cyclic edges
			if (!edges.isEmpty()) {
				for (Edge edge : edges) { // disconnect
					CNode parent = lookup.get(edge.getSource());
					parent.children.remove(cnode);
					cnode.parents.remove(parent);
				}
			}
		}

		// select single graph root
		root = roots.get(0);
		if (roots.size() > 1) {
			root = new CNode(this); 	// create virtual
			for (CNode real : roots) {
				real.parents.add(root);
				root.children.add(real);
			}
		}

		// 3) assign ranks & labels

		assignRanks(0, terminals);
		assignTopologicalLabels();

		// 4) optomize graph
		// TBD
	}

	// find rank above, assign labels to the current rank, recurse
	private void assignRanks(int rank, Set<CNode> targets) {
		Set<CNode> sources = singleLevelUpSources(targets);
		CLayer layer = new CLayer(this, style);
		layers.add(layer);
		for (CNode target : targets) {
			target.rank = rank;
			layer.add(target);
		}
		if (!sources.isEmpty()) assignRanks(++rank, sources);
	}

	// returns the set of one level up sources that refer to targets
	private Set<CNode> singleLevelUpSources(Set<CNode> targets) {
		LinkedHashSet<CNode> sources = Sets.newLinkedHashSet();
		targets.forEach(target -> {
			Set<Edge> in = target.getIncomingEdges();
			target.degree = in.size();
			in.forEach(e -> {
				CNode source = lookup.get(e.getSource());
				// source will be null if hidden or otherwise deemed irrelevant
				if (source != null && includeSource(source, targets)) sources.add(source);
			});
		});
		return sources;
	}

	// true iff all unranked out edges refer to targets
	private boolean includeSource(CNode source, Set<CNode> targets) {
		LinkedHashSet<CNode> noms = Sets.newLinkedHashSet();
		source.getOutgoingEdges().forEach(e -> {
			CNode target = lookup.get(e.getTarget());
			if (target.rank < 0) noms.add(target);
		});
		return targets.containsAll(noms);
	}

	private void assignTopologicalLabels() {
		List<CNode> sorted = new ArrayList<>();
		Stack<CNode> roots = new Stack<>();
		roots.add(root);

		while (!roots.isEmpty()) {
			CNode cnode = roots.pop();
			sorted.add(cnode);
			List<CNode> targets = Lists.newArrayList();
			cnode.node.getAllOutgoingEdges().forEach(edge -> {
				CNode target = lookup.get(edge.getTarget());
				// target will be null if hidden or otherwise deemed irrelevant
				if (target != null) targets.add(target);
			});
			targets.forEach(target -> {
				target.degree--;
				if (target.degree == 0) roots.add(target);
			});
		}
		Collections.reverse(sorted);
		for (int idx = 0; idx < sorted.size(); idx++) {
			sorted.get(idx).id = idx;
		}
	}
}
