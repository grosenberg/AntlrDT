/*******************************************************************************
 * Copyright 2005-2019, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.antlrdt.graph.layouts;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.log.Log.LogLevel;
import net.certiv.dsl.core.preferences.DslPrefsManager;

/**
 * TreeLayout that computes a tidy layout of a node-link tree diagram. This algorithm lays out a
 * rooted tree such that each depth level of the tree is on a shared line. The orientation of the
 * tree can be set such that the tree goes left-to-right (default), right-to-left, top-to-bottom, or
 * bottom-to-top.
 * <p>
 * The algorithm used is that of Christoph Buchheim, Michael Junger, and Sebastian Leipert from
 * their research paper <a href="http://citeseer.ist.psu.edu/buchheim02improving.html"> Improving
 * Walker's Algorithm to Run in Linear Time</a>, Graph Drawing 2002. This algorithm corrects
 * performance issues in Walker's algorithm, which generalizes Reingold and Tilford's method for
 * tidy drawings of trees to support trees with an arbitrary number of children at any given node.
 * <p>
 * Derived from the <a href="http://prefuse.org">Prefuse</a> NodeLinkTreeLayout implementation (BSD
 * License). Modifications include adaption for use with GEF5, consistent handling of cyclic
 * relations, and proper support for multiple real root nodes.
 * </p>
 *
 * @author <a href="http://jheer.org">jeffrey heer</a>
 * @author <a href="http://www.certiv.net">Gerald Rosenberg</a>
 */
public class LinWalkersLayoutAlgorithm implements ILayoutAlgorithm {

	public static class LW {
		/** A left-to-right layout orientation */
		public static final int ORIENT_LEFT_RIGHT = 0;
		/** A right-to-left layout orientation */
		public static final int ORIENT_RIGHT_LEFT = 1;
		/** A top-to-bottom layout orientation */
		public static final int ORIENT_TOP_BOTTOM = 2;
		/** A bottom-to-top layout orientation */
		public static final int ORIENT_BOTTOM_TOP = 3;
		/** The total number of orientation values */
		public static final int ORIENTATION_COUNT = 4;
	}

	private DslPrefsManager prefsMgr;
	private int m_orientation; // tree orientation

	@SuppressWarnings("unused")
	private int m_styles;

	private double m_bspace; // spacing between sibling nodes
	private double m_tspace; // spacing between subtrees
	private double m_dspace; // spacing between depth levels
	private double m_offset; // pixel offset for root node position

	private Point m_anchor;  // holds anchor co-ordinates
	private double[] m_depths;
	private int m_maxDepth;

	/** Create a new NodeLinkTreeLayout. A left-to-right orientation is assumed. */
	public LinWalkersLayoutAlgorithm(int styles) {
		this(styles, LW.ORIENT_LEFT_RIGHT);
	}

	/** Create a new NodeLinkTreeLayout with the given orientation. */
	public LinWalkersLayoutAlgorithm(int styles, int orientation) {
		super();

		m_styles = styles;
		m_orientation = orientation;
		prefsMgr = AntlrDTCore.getDefault().getPrefsManager();

		Log.setLevel(this, Log.LogLevel.Debug);
	}

	/**
	 * Returns the tree layout orientation.
	 *
	 * @return the orientation value. One of {@link ORIENT_LEFT_RIGHT}, {@link ORIENT_RIGHT_LEFT},
	 *         {@link ORIENT_TOP_BOTTOM}, or {@link ORIENT_BOTTOM_TOP}.
	 */
	public int getOrientation() {
		return m_orientation;
	}

	/**
	 * Sets the tree layout orientation.
	 *
	 * @param orientation the orientation value. One of {@link ORIENT_LEFT_RIGHT},
	 *            {@link ORIENT_RIGHT_LEFT}, {@link ORIENT_TOP_BOTTOM}, or {@link ORIENT_BOTTOM_TOP}.
	 */
	public void setOrientation(int orientation) {
		if (orientation < 0 || orientation >= LW.ORIENTATION_COUNT) {
			throw new IllegalArgumentException("Unsupported orientation value: " + orientation);
		}
		m_orientation = orientation;
	}

	@Override
	public void applyLayout(LayoutContext context, boolean clean) {
		if (!clean) return;

		Log.debug(this, "Starting layout ...");

		m_bspace = prefsMgr.getInt(PrefsKey.PT_SIBLING_SPACING); // between sibling nodes
		m_tspace = prefsMgr.getInt(PrefsKey.PT_SUBTREE_SPACING); // between subtrees
		m_dspace = prefsMgr.getInt(PrefsKey.PT_DEPTH_SPACING); // between depth levels
		m_offset = prefsMgr.getInt(PrefsKey.PT_ROOT_OFFSET); // offset for root node

		// (1) build the layout graph
		LwNode graphRoot = make(context.getNodes());;
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

		Log.debug(this, "Layout complete.");
	}

	private Point getLayoutAnchor(Rectangle bounds, boolean virtual) {
		if (virtual) m_offset -= m_dspace;
		Point anchor = new Point();
		switch (m_orientation) {
			case LW.ORIENT_LEFT_RIGHT:
				anchor.setLocation((int) m_offset, (int) (bounds.getHeight() / 2.0));
				break;
			case LW.ORIENT_RIGHT_LEFT:
				anchor.setLocation((int) (bounds.getWidth() - m_offset), (int) (bounds.getHeight() / 2.0));
				break;
			case LW.ORIENT_TOP_BOTTOM:
				anchor.setLocation((int) (bounds.getWidth() / 2.0), (int) m_offset);
				break;
			case LW.ORIENT_BOTTOM_TOP:
				anchor.setLocation((int) (bounds.getWidth() / 2.0), (int) (bounds.getHeight() - m_offset));
				break;
		}
		return anchor;
	}

	private void firstWalk(LwNode node, int num, int depth) {
		Log.debug(this, "Walk1 [entity=" + node.toString() + ", num=" + num + ", depth=" + depth + "]");

		node.number = num;
		updateDepths(depth, node);

		if (node.childCount() == 0) { // the definition of a leaf
			LwNode sibling = node.previousSibling();
			if (sibling == null) {
				node.prelim = 0;
			} else {
				node.prelim = sibling.prelim + spacing(sibling, node, true);
			}
		} else { // all other nodes define subtrees
			LwNode leftMost = node.firstChild();
			LwNode rightMost = node.lastChild();
			LwNode defaultAncestor = leftMost;
			LwNode current = leftMost;
			for (int idx = 0; current != null; ++idx, current = current.nextSibling()) {
				firstWalk(current, idx, depth + 1);
				defaultAncestor = apportion(current, defaultAncestor);
			}

			executeShifts(node);
			double midpoint = 0.5 * (leftMost.prelim + rightMost.prelim);

			LwNode left = node.previousSibling();
			if (left != null) {
				node.prelim = left.prelim + spacing(left, node, true);
				node.mod = node.prelim - midpoint;
			} else {
				node.prelim = midpoint;
			}
		}
	}

	private void updateDepths(int depth, LwNode lwNode) {
		Dimension size = LayoutProperties.getSize(lwNode.node);
		boolean vert = (m_orientation == LW.ORIENT_TOP_BOTTOM || m_orientation == LW.ORIENT_BOTTOM_TOP);
		double d = vert ? size.height : size.width;
		if (m_depths.length <= depth) m_depths = resize(m_depths, 3 * depth / 2);
		m_depths[depth] = Math.max(m_depths[depth], d);
		m_maxDepth = Math.max(m_maxDepth, depth);
	}

	private double spacing(LwNode sibling, LwNode lwNode, boolean areSiblings) {
		Dimension sibSize = LayoutProperties.getSize(sibling.node);
		Dimension refSize = LayoutProperties.getSize(lwNode.node);

		double aveSize = 0;
		boolean vert = (m_orientation == LW.ORIENT_TOP_BOTTOM || m_orientation == LW.ORIENT_BOTTOM_TOP);
		if (vert) {
			aveSize = 0.5 * sibSize.getWidth() + refSize.getWidth();
		} else {
			aveSize = 0.5 * sibSize.getHeight() + refSize.getHeight();
		}
		return (areSiblings ? m_bspace : m_tspace) + aveSize;
	}

	private void determineDepths() {
		for (int idx = 1; idx < m_maxDepth; ++idx)
			m_depths[idx] += m_depths[idx - 1] + m_dspace;
	}

	private LwNode apportion(LwNode current, LwNode defaultAncestor) {
		LwNode w = current.previousSibling();
		if (w != null) {
			LwNode vip, vim, vop, vom;
			double sip, sim, sop, som;

			vip = vop = current;
			vim = w;
			vom = vip.firstSibling();

			sip = vip.mod;
			sop = vop.mod;
			sim = vim.mod;
			som = vom.mod;

			LwNode nr = nextRight(vim);
			LwNode nl = nextLeft(vip);
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

	private LwNode ancestor(LwNode vim, LwNode v, LwNode a) {
		LwNode p = v.parent;
		if (vim.ancestor != null && vim.ancestor.parent == p) {
			return vim.ancestor;
		}
		return a;
	}

	private LwNode nextLeft(LwNode lwNode) {
		if (lwNode.hasChildren()) return lwNode.firstChild();
		return lwNode.thread;
	}

	private LwNode nextRight(LwNode vim) {
		if (vim.hasChildren()) return vim.lastChild();
		return vim.thread;
	}

	private void moveSubtree(LwNode wm, LwNode wp, double shift) {
		double subtrees = wp.number - wm.number;
		wp.change -= shift / subtrees;
		wp.shift += shift;
		wm.change += shift / subtrees;
		wp.prelim += shift;
		wp.mod += shift;
	}

	private void executeShifts(LwNode lwNode) {
		double shift = 0;
		double change = 0;
		for (LwNode current = lwNode.lastChild(); current != null; current = current.previousSibling()) {
			current.prelim += shift;
			current.mod += shift;
			change += current.change;
			shift += current.shift + change;
		}
	}

	private void secondWalk(LwNode n, LwNode p, double m, int depth) {
		Log.debug(this, "Walk2 [entity=" + n.toString() + ", num=" + m + ", depth=" + depth + "]");

		setBreadth(n, n.prelim + m);
		setDepth(n, m_depths[depth]);

		if (n.hasChildren()) {
			depth += 1;
			for (LwNode current = n.firstChild(); current != null; current = current.nextSibling()) {
				secondWalk(current, n, m + n.mod, depth);
			}
		}
		n.clear();
	}

	private void setBreadth(LwNode n, double b) {
		switch (m_orientation) {
			case LW.ORIENT_LEFT_RIGHT:
			case LW.ORIENT_RIGHT_LEFT:
				setY(n, m_anchor.y + b);
				break;
			case LW.ORIENT_TOP_BOTTOM:
			case LW.ORIENT_BOTTOM_TOP:
				setX(n, m_anchor.x + b);
				break;
			default:
				throw new IllegalStateException();
		}
	}

	private void setDepth(LwNode n, double d) {
		switch (m_orientation) {
			case LW.ORIENT_LEFT_RIGHT:
				setX(n, m_anchor.x + d);
				break;
			case LW.ORIENT_RIGHT_LEFT:
				setX(n, m_anchor.x - d);
				break;
			case LW.ORIENT_TOP_BOTTOM:
				setY(n, m_anchor.y + d);
				break;
			case LW.ORIENT_BOTTOM_TOP:
				setY(n, m_anchor.y - d);
				break;
			default:
				throw new IllegalStateException();
		}
	}

	private void setX(LwNode n, double x) {
		Point loc = LayoutProperties.getLocation(n.node);
		loc.x = x;
		LayoutProperties.setLocation(n.node, loc);
	}

	private void setY(LwNode n, double y) {
		Point loc = LayoutProperties.getLocation(n.node);
		loc.y = y;
		LayoutProperties.setLocation(n.node, loc);
	}

	/** Resize the given array as needed to meet a target size. */
	private double[] resize(double[] inArray, int size) {
		if (inArray.length >= size) return inArray;
		double[] outArray = new double[size];
		System.arraycopy(inArray, 0, outArray, 0, inArray.length);
		return outArray;
	}

	/** Wrapper class holding Node layout parameters. */
	static class LwNode {

		Node node = null;		// the entity wrapped by this

		LwNode parent = null;	// immediate parent node
		LwNode ancestor = null;	// a common connected predecessor/parent
		LwNode thread = null;	// the successor leaf in the contour
		List<LwNode> children = Lists.newArrayList();

		double prelim;	// preliminary virtual x-axis position
		double mod; 	// accumulated modifier of the prelim
		double shift;
		double change;
		int number = -2;

		// virtual root node contructor
		LwNode() {
			this(null);
		}

		// real node contructor
		LwNode(Node node) {
			this.node = node;
			number = -1;
			Log.setLevel(this, LogLevel.Info);
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

		boolean hasChildren() {
			return !children.isEmpty();
		}

		int childCount() {
			return children.size();
		}

		/** adds an immediate connected child */
		void addChild(LwNode child) {
			children.add(child);
		}

		void removeChild(LwNode target) {
			children.remove(target);
		}

		LwNode firstChild() {
			if (hasChildren()) return children.get(0);
			return null;
		}

		LwNode lastChild() {
			if (hasChildren()) return children.get(children.size() - 1);
			return null;
		}

		LwNode childAt(int idx) {
			if (children.size() > idx) return children.get(idx);
			return null;
		}

		boolean hasParent() {
			return parent != null;
		}

		/** adds an immediate common connected parent */
		void setParent(LwNode parent) {
			this.parent = parent;
			ancestor = parent;
		}

		LwNode getParent() {
			return parent;
		}

		LwNode firstSibling() {
			if (hasParent()) return parent.childAt(0);
			return null;
		}

		// return null if no parent or no next sibling
		LwNode nextSibling() {
			if (!hasParent()) return null;

			int nextChild = parent.children.indexOf(this) + 1;
			int numChildren = parent.childCount();
			if (nextChild < 1 || nextChild >= numChildren) return null;

			Log.debug(this, "Next [entity=" + nextChild + ":" + parent.childAt(nextChild) + "]");
			return parent.childAt(nextChild);
		}

		// return null if no parent or no previous sibling
		LwNode previousSibling() {
			if (hasParent()) return null;

			int prevChild = parent.children.indexOf(this) - 1;
			if (prevChild < 0) return null;

			Log.debug(this, "Previous [entity=" + prevChild + ":" + parent.childAt(prevChild) + "]");
			return parent.childAt(prevChild);
		}

		@Override
		public String toString() {
			String source = parent != null ? parent.toString() : "<empty>";
			return source + " -> " + children.toString();
		}
	}

	// makes a layout graph from the given nodes
	private static LwNode make(Node[] nodes) {
		if (nodes.length == 0) return null;

		// 1) wrap nodes

		Map<Node, LwNode> lookup = Maps.newLinkedHashMap();
		for (Node node : nodes) {
			lookup.put(node, new LwNode(node));
		}

		// 2) construct well-ordered acyclic directed graph

		// roots are parentless nodes
		List<LwNode> roots = Lists.newArrayList(lookup.values());
		// omitted are the excess parenting connections to nodes
		List<Edge> cycles = Lists.newArrayList();

		for (LwNode lwParent : lookup.values()) {
			for (Edge edge : lwParent.node.getAllOutgoingEdges()) {
				LwNode lwChild = lookup.get(edge.getTarget());
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

		LwNode graphRoot = roots.get(0);
		if (roots.size() > 1) {
			graphRoot = new LwNode(); // virtual
			for (LwNode root : roots) {
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
				LwNode lwChild = lookup.get(target);
				LwNode oldParent = lwChild.parent;
				oldParent.children.remove(lwChild);

				lwChild.setParent(lookup.get(source));
				lwChild.parent.children.add(lwChild);
			}
		}

		return graphRoot;
	}

	// walk upwards to validate edge targets; returning edges with unreachable targets
	private static List<Edge> analyzeCycles(Map<Node, LwNode> lookup, LwNode lwRoot, List<Edge> omitted) {
		if (omitted.isEmpty()) return omitted;

		List<Edge> edges = Lists.newArrayList();
		for (Edge edge : omitted) {
			LwNode lwChild = lookup.get(edge.getTarget());
			if (!connected(lwRoot, lwChild)) edges.add(edge);
		}
		return edges;
	}

	private static boolean connected(LwNode lwRoot, LwNode lwChild) {
		Set<LwNode> lwSeen = new HashSet<>();
		LwNode lwNode = lwChild;
		while (lwNode.parent != null) {
			LwNode lwParent = lwNode.parent;
			if (lwRoot.equals(lwParent)) return true;
			if (lwSeen.contains(lwParent)) return false;
			lwSeen.add(lwParent);
			lwNode = lwParent;
		}
		return false;
	}
}
