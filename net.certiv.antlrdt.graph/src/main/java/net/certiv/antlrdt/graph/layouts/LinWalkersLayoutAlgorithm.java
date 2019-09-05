/*******************************************************************************
 * Copyright 2005-2019, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.antlrdt.graph.layouts;

import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.layout.ILayoutAlgorithm;
import org.eclipse.gef.layout.LayoutContext;
import org.eclipse.gef.layout.LayoutProperties;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.preferences.DslPrefsManager;

/**
 * TreeLayout that computes a tidy layout of a node-link tree diagram. This
 * algorithm lays out a rooted tree such that each depth level of the tree is on
 * a shared line. The orientation of the tree can be set such that the tree goes
 * left-to-right (default), right-to-left, top-to-bottom, or bottom-to-top.
 * <p>
 * The algorithm used is that of Christoph Buchheim, Michael Junger, and
 * Sebastian Leipert from their research paper
 * <a href="http://citeseer.ist.psu.edu/buchheim02improving.html"> Improving
 * Walker's Algorithm to Run in Linear Time</a>, Graph Drawing 2002. This
 * algorithm corrects performance issues in Walker's algorithm, which
 * generalizes Reingold and Tilford's method for tidy drawings of trees to
 * support trees with an arbitrary number of children at any given node.
 * <p>
 * Derived from the <a href="http://prefuse.org">Prefuse</a> NodeLinkTreeLayout
 * implementation (BSD License). Modifications include adaption for use with
 * GEF5, consistent handling of cyclic relations, and proper support for
 * multiple real root nodes.
 * 
 *
 * @author <a href="http://jheer.org">jeffrey heer</a>
 * @author <a href="http://www.certiv.net">Gerald Rosenberg</a>
 */
public class LinWalkersLayoutAlgorithm implements ILayoutAlgorithm {

	public static class Orient {
		/** A left-to-right layout orientation */
		public static final int LEFT_RIGHT = 0;
		/** A right-to-left layout orientation */
		public static final int RIGHT_LEFT = 1;
		/** A top-to-bottom layout orientation */
		public static final int TOP_BOTTOM = 2;
		/** A bottom-to-top layout orientation */
		public static final int BOTTOM_TOP = 3;
		/** The total number of orientation values */
		public static final int COUNT = 4;
	}

	private static final Lock lock = new ReentrantLock(true);

	private DslPrefsManager prefsMgr;
	private int m_orientation; // tree orientation

	private double m_bspace; // spacing between sibling nodes
	private double m_tspace; // spacing between subtrees
	private double m_dspace; // spacing between depth levels
	private double m_offset; // pixel offset for root node position

	// key = node model; value = wrapping node
	private Map<Node, LNode> lookup;

	private Point m_anchor;  // holds anchor co-ordinates
	private double[] m_depths;
	private int m_maxDepth;

	/** Create a new NodeLinkTreeLayout. A left-to-right orientation is assumed. */
	public LinWalkersLayoutAlgorithm() {
		this(Orient.LEFT_RIGHT);
	}

	/** Create a new NodeLinkTreeLayout with the given orientation. */
	public LinWalkersLayoutAlgorithm(int orientation) {
		super();
		m_orientation = orientation;
		prefsMgr = AntlrCore.getDefault().getPrefsManager();
		Log.setLevel(this, Log.LogLevel.Info);
	}

	/**
	 * Returns the tree layout orientation.
	 *
	 * @return the orientation value. One of {@link LEFT_RIGHT}, {@link RIGHT_LEFT},
	 *             {@link TOP_BOTTOM}, or {@link BOTTOM_TOP}.
	 */
	public int getOrientation() {
		return m_orientation;
	}

	/**
	 * Sets the tree layout orientation.
	 *
	 * @param orientation the orientation value. One of {@link LEFT_RIGHT},
	 *                        {@link RIGHT_LEFT}, {@link TOP_BOTTOM}, or
	 *                        {@link BOTTOM_TOP}.
	 */
	public void setOrientation(int orientation) {
		if (orientation < 0 || orientation >= Orient.COUNT) {
			throw new IllegalArgumentException("Unsupported orientation value: " + orientation);
		}
		m_orientation = orientation;
	}

	@Override
	public void applyLayout(LayoutContext context, boolean clean) {
		if (!clean) return;

		lock.lock();
		try {
			m_bspace = prefsMgr.getInt(PrefsKey.PT_SIBLING_SPACING);	// between sibling nodes
			m_tspace = prefsMgr.getInt(PrefsKey.PT_SUBTREE_SPACING);	// between subtrees
			m_dspace = prefsMgr.getInt(PrefsKey.PT_DEPTH_SPACING);		// between depth levels
			m_offset = prefsMgr.getInt(PrefsKey.PT_ROOT_OFFSET);		// offset for root node

			// (1) build the layout graph
			LNode graphRoot = make(context.getNodes());
			if (graphRoot == null) {
				Log.error(this, "No root; has to be a directed graph at least at the root level.");
				return;
			}

			// (2) set root node location, etc.
			Rectangle bounds = LayoutProperties.getBounds(context.getGraph());
			m_anchor = getLayoutAnchor(bounds, graphRoot.isVirtual());
			m_maxDepth = 0;
			m_depths = new double[10];

			// (3) do first Walker pass - compute breadth information, collect depth info
			firstWalk(graphRoot, 0, 1);

			// (4) sum up the depth info
			determineDepths();

			// (5) do second Walker pass - assign layout positions
			secondWalk(graphRoot, null, -graphRoot.prelim, 0);

			// (6) update graph layout positions
			for (Entry<Node, LNode> entry : lookup.entrySet()) {
				Node node = entry.getKey();
				Point loc = entry.getValue().loc;
				Log.debug(this, String.format("Loc '%s' @%s:%s", ((NodeModel) node).getDisplayText(), loc.x, loc.y));
				LayoutProperties.setLocation(node, loc);
			}

		} catch (Exception e) {
			Log.error(this, "Layout failed: " + e.getMessage(), e);
		} finally {
			lock.unlock();
		}
	}

	private Point getLayoutAnchor(Rectangle bounds, boolean virtual) {
		if (virtual) m_offset -= m_dspace;
		Point anchor = new Point();
		switch (m_orientation) {
			case Orient.LEFT_RIGHT:
				anchor.setLocation((int) m_offset, (int) (bounds.getHeight() / 2.0));
				break;
			case Orient.RIGHT_LEFT:
				anchor.setLocation((int) (bounds.getWidth() - m_offset), (int) (bounds.getHeight() / 2.0));
				break;
			case Orient.TOP_BOTTOM:
				anchor.setLocation((int) (bounds.getWidth() / 2.0), (int) m_offset);
				break;
			case Orient.BOTTOM_TOP:
				anchor.setLocation((int) (bounds.getWidth() / 2.0), (int) (bounds.getHeight() - m_offset));
				break;
		}
		return anchor;
	}

	private void firstWalk(LNode node, int num, int depth) {
		Log.debug(this, "Walk1 [entity=" + node.toString() + ", num=" + num + ", depth=" + depth + "]");

		node.number = num;
		updateDepths(depth, node);

		if (node.childCount() == 0) { // the definition of a leaf
			LNode sibling = node.previousSibling();
			if (sibling == null) {
				node.prelim = 0;
			} else {
				node.prelim = sibling.prelim + spacing(sibling, node, true);
			}
		} else { // all other nodes define subtrees
			LNode leftMost = node.firstChild();
			LNode rightMost = node.lastChild();
			LNode defaultAncestor = leftMost;
			LNode current = leftMost;
			for (int idx = 0; current != null; ++idx, current = current.nextSibling()) {
				firstWalk(current, idx, depth + 1);
				defaultAncestor = apportion(current, defaultAncestor);
			}

			executeShifts(node);
			double midpoint = 0.5 * (leftMost.prelim + rightMost.prelim);

			LNode prev = node.previousSibling();
			if (prev != null) {
				node.prelim = prev.prelim + spacing(prev, node, true);
				node.mod = node.prelim - midpoint;
			} else {
				node.prelim = midpoint;
			}
		}
	}

	private void updateDepths(int depth, LNode lNode) {
		if (lNode.node == null) {
			Log.error(this, "Wrapped node is null!");
			return;
		}
		Dimension size = LayoutProperties.getSize(lNode.node);
		boolean vert = (m_orientation == Orient.TOP_BOTTOM || m_orientation == Orient.BOTTOM_TOP);
		double d = vert ? size.height : size.width;
		if (m_depths.length <= depth) m_depths = resize(m_depths, 3 * depth / 2);
		m_depths[depth] = Math.max(m_depths[depth], d);
		m_maxDepth = Math.max(m_maxDepth, depth);
	}

	private double spacing(LNode sibling, LNode lNode, boolean areSiblings) {
		Dimension sibSize = LayoutProperties.getSize(sibling.node);
		Dimension refSize = LayoutProperties.getSize(lNode.node);

		double aveSize = 0;
		boolean vert = (m_orientation == Orient.TOP_BOTTOM || m_orientation == Orient.BOTTOM_TOP);
		if (vert) {
			aveSize = 0.5 * sibSize.getWidth() + refSize.getWidth();
		} else {
			aveSize = 0.5 * sibSize.getHeight() + refSize.getHeight();
		}
		return (areSiblings ? m_bspace : m_tspace) + aveSize;
	}

	private void determineDepths() {
		for (int idx = 1; idx < m_maxDepth; ++idx) {
			m_depths[idx] += m_depths[idx - 1] + m_dspace;
		}
	}

	private LNode apportion(LNode current, LNode defaultAncestor) {
		LNode w = current.previousSibling();
		if (w != null) {
			LNode vip, vim, vop, vom;
			double sip, sim, sop, som;

			vip = vop = current;
			vim = w;
			vom = vip.firstSibling();

			sip = vip.mod;
			sop = vop.mod;
			sim = vim.mod;
			som = vom.mod;

			LNode nr = nextRight(vim);
			LNode nl = nextLeft(vip);
			while (nr != null && nl != null) {
				vim = nr;
				vip = nl;
				vom = nextLeft(vom);
				vop = nextRight(vop);
				vop.ancestor = current;
				double shift = (vim.prelim + sim) - (vip.prelim + sip) + spacing(vim, vip, false);
				if (shift > 0) {
					moveSubtree(ancestor(vim, current, defaultAncestor), current, shift);
					sip += shift;
					sop += shift;
				}
				sim += vim.mod;
				sip += vip.mod;
				som += vom.mod;
				sop += vop.mod;

				nr = nextRight(vim);
				nl = nextLeft(vip);
			}
			if (nr != null && nextRight(vop) == null) {
				vop.thread = nr;
				vop.mod += sim - sop;
			}
			if (nl != null && nextLeft(vom) == null) {
				vom.thread = nl;
				vom.mod += sip - som;
				defaultAncestor = current;
			}
		}
		return defaultAncestor;
	}

	private LNode ancestor(LNode vim, LNode v, LNode a) {
		LNode p = v.parent;
		if (vim.ancestor != null && vim.ancestor.parent == p) {
			return vim.ancestor;
		}
		return a;
	}

	private LNode nextLeft(LNode lNode) {
		if (lNode.hasChildren()) return lNode.firstChild();
		return lNode.thread;
	}

	private LNode nextRight(LNode vim) {
		if (vim.hasChildren()) return vim.lastChild();
		return vim.thread;
	}

	private void moveSubtree(LNode wm, LNode wp, double shift) {
		double subtrees = wp.number - wm.number;
		wp.change -= shift / subtrees;
		wp.shift += shift;
		wm.change += shift / subtrees;
		wp.prelim += shift;
		wp.mod += shift;
	}

	private void executeShifts(LNode lNode) {
		Log.debug(this, "Shift [entity=" + lNode.toString() + "]");

		double shift = 0;
		double change = 0;
		for (LNode current = lNode.lastChild(); current != null; current = current.previousSibling()) {
			current.prelim += shift;
			current.mod += shift;
			change += current.change;
			shift += current.shift + change;
		}
	}

	private void secondWalk(LNode n, LNode p, double m, int depth) {
		Log.debug(this, "Walk2 [entity=" + n.toString() + ", num=" + m + ", depth=" + depth + "]");

		setBreadth(n, n.prelim + m);
		setDepth(n, m_depths[depth]);

		if (n.hasChildren()) {
			depth += 1;
			for (LNode current = n.firstChild(); current != null; current = current.nextSibling()) {
				secondWalk(current, n, m + n.mod, depth);
			}
		}
		n.clear();
	}

	private void setBreadth(LNode n, double b) {
		switch (m_orientation) {
			case Orient.LEFT_RIGHT:
			case Orient.RIGHT_LEFT:
				Log.debug(this, "BreadthY: " + (m_anchor.y + b));
				setY(n, m_anchor.y + b);
				break;
			case Orient.TOP_BOTTOM:
			case Orient.BOTTOM_TOP:
				Log.debug(this, "BreadthX: " + (m_anchor.x + b));
				setX(n, m_anchor.x + b);
				break;
			default:
				throw new IllegalStateException();
		}
	}

	private void setDepth(LNode n, double d) {
		switch (m_orientation) {
			case Orient.LEFT_RIGHT:
				Log.debug(this, "DepthX: " + (m_anchor.x + d));
				setX(n, m_anchor.x + d);
				break;
			case Orient.RIGHT_LEFT:
				Log.debug(this, "DepthX: " + (m_anchor.x - d));
				setX(n, m_anchor.x - d);
				break;
			case Orient.TOP_BOTTOM:
				Log.debug(this, "DepthY: " + (m_anchor.y + d));
				setY(n, m_anchor.y + d);
				break;
			case Orient.BOTTOM_TOP:
				Log.debug(this, "DepthY: " + (m_anchor.y - d));
				setY(n, m_anchor.y - d);
				break;
			default:
				throw new IllegalStateException();
		}
	}

	private void setX(LNode n, double x) {
		n.loc.x = x;
	}

	private void setY(LNode n, double y) {
		n.loc.y = y;
	}

	/** Resize the given array as needed to meet a target size. */
	private double[] resize(double[] inArray, int size) {
		if (inArray.length >= size) return inArray;
		double[] outArray = new double[size];
		System.arraycopy(inArray, 0, outArray, 0, inArray.length);
		return outArray;
	}

	/** Wrapper class holding Node layout parameters. */
	class LNode {

		Node node = null;		// the wrapped entity
		Point loc;				// graph layout location

		LNode parent = null;	// immediate parent node
		LNode ancestor = null;	// a common connected predecessor/parent
		LNode thread = null;	// the successor leaf in the contour
		int childNum;			// index in parent's list of children
		final List<LNode> children = Lists.newArrayList();

		double prelim;	// preliminary virtual x-axis position
		double mod; 	// accumulated modifier of the prelim
		double shift;
		double change;
		int number = -2;

		// virtual root node contructor
		LNode() {
			this(null);
		}

		// real node contructor
		LNode(Node node) {
			this.node = node;
			number = -1;
		}

		void clear() {
			number = -2;
			prelim = 0;
			mod = 0;
			shift = 0;
			change = 0;
			ancestor = null;
			thread = null;
		}

		boolean isVirtual() {
			return node == null;
		}

		String getId() {
			return ((NodeModel) node).getDisplayText();
		}

		boolean hasChildren() {
			return !children.isEmpty();
		}

		int childCount() {
			return children.size();
		}

		/** adds an immediate connected child */
		void addChild(LNode child) {
			children.add(child);
		}

		void removeChild(LNode target) {
			children.remove(target);
		}

		LNode firstChild() {
			if (hasChildren()) return children.get(0);
			return null;
		}

		LNode lastChild() {
			if (hasChildren()) return children.get(children.size() - 1);
			return null;
		}

		LNode childAt(int idx) {
			if (children.size() > idx) return children.get(idx);
			return null;
		}

		boolean hasParent() {
			return parent != null;
		}

		/** adds an immediate common connected parent */
		void setParent(LNode parent) {
			this.parent = parent;
			ancestor = parent;
		}

		LNode getParent() {
			return parent;
		}

		LNode firstSibling() {
			if (hasParent()) return parent.childAt(0);
			return null;
		}

		// return null if no parent or no next sibling
		LNode nextSibling() {
			if (!hasParent()) return null;
			if (parent.childCount() <= childNum + 1) return null;

			return parent.children.get(childNum + 1);
		}

		// return null if no parent or no prior sibling
		LNode previousSibling() {
			if (!hasParent()) return null;
			if (childNum < 1) return null;

			return parent.children.get(childNum - 1);
		}

		@Override
		public String toString() {
			String source = parent != null ? parent.getId() : "<empty>";
			return source + " -> " + children.stream().map(elem -> elem.getId()).collect(Collectors.toList());
		}
	}

	// makes a layout graph from the given nodes
	private LNode make(Node[] nodes) {
		if (nodes.length == 0) return null;

		// 1) wrap nodes

		lookup = Maps.newLinkedHashMap();

		for (Node node : nodes) {
			LNode wrapped = new LNode(node);
			wrapped.loc = LayoutProperties.getLocation(node).getCopy();
			lookup.put(node, wrapped);
		}

		// 2) construct well-ordered acyclic directed graph

		// roots are parentless nodes
		List<LNode> roots = Lists.newArrayList(lookup.values());
		// omitted are the excess parenting connections to nodes
		List<Edge> cycles = Lists.newArrayList();

		for (LNode lwParent : lookup.values()) {
			for (Edge edge : lwParent.node.getAllOutgoingEdges()) {
				LNode lwChild = lookup.get(edge.getTarget());
				if (lwChild == null) continue;
				if (lwChild.parent == null) {
					lwChild.setParent(lwParent);
					lwParent.children.add(lwChild);
				} else {
					cycles.add(edge);
				}
				roots.remove(lwChild); // child cannot be a root
			}
		}

		if (roots.size() == 0) return null;

		// 3) find/create graph root

		LNode graphRoot = roots.get(0);
		if (roots.size() > 1) {
			graphRoot = new LNode(); // virtual
			for (LNode root : roots) {
				root.setParent(graphRoot);
				graphRoot.children.add(root);
			}
		}

		// 4) fix the cyclic edges: reverse direction for layout purposes

		cycles = analyzeCycles(lookup, graphRoot, cycles);
		if (!cycles.isEmpty()) {

			// replace existing parents of cycle targets
			for (Edge edge : cycles) {
				Node source = edge.getSource();
				Node target = edge.getTarget();

				// self-cycle loops are allowed
				if (source.equals(target)) continue;

				// reverse edge direction and connect
				LNode lwChild = lookup.get(target);
				LNode oldParent = lwChild.parent;
				oldParent.children.remove(lwChild);

				lwChild.setParent(lookup.get(source));
				lwChild.parent.children.add(lwChild);
			}
		}

		// 5) assign sibling index values

		for (LNode parent : lookup.values()) {
			for (int idx = 0; idx < parent.childCount(); idx++) {
				parent.children.get(idx).childNum = idx;
			}
		}

		return graphRoot;
	}

	// walk upwards to validate edge targets; returning edges with unreachable
	// targets
	private List<Edge> analyzeCycles(Map<Node, LNode> lookup, LNode lwRoot, List<Edge> omitted) {
		if (omitted.isEmpty()) return omitted;

		List<Edge> edges = Lists.newArrayList();
		for (Edge edge : omitted) {
			LNode lwChild = lookup.get(edge.getTarget());
			if (!connected(lwRoot, lwChild)) edges.add(edge);
		}
		return edges;
	}

	private boolean connected(LNode lwRoot, LNode lwChild) {
		Set<LNode> lwSeen = new HashSet<>();
		LNode lNode = lwChild;
		while (lNode.parent != null) {
			LNode lwParent = lNode.parent;
			if (lwRoot.equals(lwParent)) return true;
			if (lwSeen.contains(lwParent)) return false;
			lwSeen.add(lwParent);
			lNode = lwParent;
		}
		return false;
	}
}
