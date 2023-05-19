/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.vis.layouts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.zest.layouts.LayoutEntity;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.constraints.LayoutConstraint;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.dataStructures.InternalRelationship;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.common.log.Level;
import net.certiv.common.log.Log;
import net.certiv.dsl.core.preferences.IPrefsManager;

/**
 * TreeLayout that computes a tidy layout of a node-link tree diagram. This algorithm lays
 * out a rooted tree such that each depth level of the tree is on a shared line. The
 * orientation of the tree can be set such that the tree goes left-to-right (default),
 * right-to-left, top-to-bottom, or bottom-to-top.
 * <p>
 * The algorithm used is that of Christoph Buchheim, Michael Junger, and Sebastian Leipert
 * from their research paper
 * <a href="http://citeseer.ist.psu.edu/buchheim02improving.html"> Improving Walker's
 * Algorithm to Run in Linear Time</a>, Graph Drawing 2002. This algorithm corrects
 * performance issues in Walker's algorithm, which generalizes Reingold and Tilford's
 * method for tidy drawings of trees to support trees with an arbitrary number of children
 * at any given node.
 * <p>
 * Derived from the <a href="http://prefuse.org">Prefuse</a> NodeLinkTreeLayout
 * implementation (BSD License). Modifications include adaption for use with Zest,
 * consistent handling of cyclic relations, and proper support for multiple real root
 * nodes.
 *
 * @author <a href="http://jheer.org">jeffrey heer</a>
 * @author <a href="http://www.certiv.net">Gerald Rosenberg</a>
 */
public class LinWalkersLayoutAlgorithm extends AbstractLayoutAlgorithm {

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

	/** The data field in which the parameters used by this layout are stored. */
	private static final String PARAMS = "_reingoldTilfordParams";

	private int m_orientation; // orientation of the tree
	private double m_bspace; // spacing between sibling nodes
	private double m_tspace; // spacing between subtrees
	private double m_dspace; // spacing between depth levels
	private double m_offset; // pixel offset for root node position

	private Point m_anchor; // holds anchor co-ordinates
	private double[] m_depths;
	private int m_maxDepth;

	// map of reversed relationships to prevent cycles
	// private HashMap<Integer, InternalRelationship> revRelations;

	// ------------------------------------------------------------------------

	/** Create a new NodeLinkTreeLayout. A left-to-right orientation is assumed. */
	public LinWalkersLayoutAlgorithm(int styles) {
		this(styles, ORIENT_LEFT_RIGHT);
	}

	/** Create a new NodeLinkTreeLayout with the given orientation. */
	public LinWalkersLayoutAlgorithm(int styles, int orientation) {
		super(styles);
		m_orientation = orientation;
		Log.setLevel(Level.INFO);
	}

	/**
	 * Set the orientation of the tree layout.
	 *
	 * @param orientation the orientation value. One of
	 *                    {@link net.certiv.callgraph.ui.layouts.NodeLinkTreeLayout#ORIENT_LEFT_RIGHT}
	 *                    ,
	 *                    {@link net.certiv.callgraph.ui.layouts.NodeLinkTreeLayout#ORIENT_RIGHT_LEFT}
	 *                    ,
	 *                    {@link net.certiv.callgraph.ui.layouts.NodeLinkTreeLayout#ORIENT_TOP_BOTTOM}
	 *                    , or
	 *                    {@link net.certiv.callgraph.ui.layouts.NodeLinkTreeLayout#ORIENT_BOTTOM_TOP}
	 *                    .
	 */
	public void setOrientation(int orientation) {
		if (orientation < 0 || orientation >= ORIENTATION_COUNT) {
			throw new IllegalArgumentException("Unsupported orientation value: " + orientation);
		}
		m_orientation = orientation;
	}

	/**
	 * Get the orientation of the tree layout.
	 *
	 * @return the orientation value. One of
	 *         {@link net.certiv.callgraph.ui.layouts.NodeLinkTreeLayout#ORIENT_LEFT_RIGHT}
	 *         ,
	 *         {@link net.certiv.callgraph.ui.layouts.NodeLinkTreeLayout#ORIENT_RIGHT_LEFT}
	 *         ,
	 *         {@link net.certiv.callgraph.ui.layouts.NodeLinkTreeLayout#ORIENT_TOP_BOTTOM}
	 *         , or
	 *         {@link net.certiv.callgraph.ui.layouts.NodeLinkTreeLayout#ORIENT_BOTTOM_TOP}
	 *         .
	 */
	public int getOrientation() {
		return m_orientation;
	}

	private IPrefsManager getPrefs() {
		return AntlrCore.getDefault().getPrefsManager();
	}

	// ------------------------------------------------------------------------

	@Override
	protected void applyLayoutInternal(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double boundsX, double boundsY,
			double boundsWidth, double boundsHeight) {

		Log.debug("Starting layout ...");

		m_bspace = getPrefs().getInt(PrefsKey.PT_SIBLING_SPACING); // between sibling
																	 // nodes
		m_tspace = getPrefs().getInt(PrefsKey.PT_SUBTREE_SPACING); // between subtrees
		m_dspace = getPrefs().getInt(PrefsKey.PT_DEPTH_SPACING); // between depth levels
		m_offset = getPrefs().getInt(PrefsKey.PT_ROOT_OFFSET); // offset for root node

		int eCnt = entitiesToLayout.length;
		int rCnt = relationshipsToConsider.length;
		Log.debug("Content [entities=" + eCnt + ", relations=" + rCnt + "]");

		ArrayList<InternalNode> entitiesList = convert(entitiesToLayout);
		ArrayList<InternalRelationship> relationshipList = convert(relationshipsToConsider);

		// (1) build the association network
		// revRelations = new HashMap<>();
		InternalNode root = constructVirtualLayout(entitiesList, relationshipList);
		if (root == null) {
			Log.error("No root; has to be a directed graph at least at the root level.");
			return;
		}

		// (2) set root node location, etc.
		boolean virtualAnchor = (root.getLayoutEntity() instanceof VirtualRootNode) ? true : false;
		m_anchor = getLayoutAnchor(boundsX, boundsY, boundsWidth, boundsHeight, virtualAnchor);
		m_maxDepth = 0;
		m_depths = new double[10];

		// (3) do first Walker pass - compute breadth information, collect depth info
		firstWalk(root, 0, 1);

		// (4) sum up the depth info
		determineDepths();

		// (5) do second Walker pass - assign layout positions
		Params npRoot = getParams(root);
		secondWalk(root, null, -npRoot.prelim, 0);

		Log.debug("Layout complete.");
	}

	private InternalNode constructVirtualLayout(ArrayList<InternalNode> entitiesList,
			ArrayList<InternalRelationship> relationshipList) {

		// start with a copy of all entities as potential roots
		// roots are nodes without any parent
		LinkedHashSet<InternalNode> roots = new LinkedHashSet<>(entitiesList);
		// omitted are the excess parenting connections to nodes
		ArrayList<InternalRelationship> omitted = new ArrayList<>();

		// initialize the entities
		for (InternalNode entity : entitiesList) {
			new Params(entity);
		}

		// construct the naive relationship network
		for (InternalRelationship relation : relationshipList) {
			InternalNode source = relation.getSource();
			InternalNode target = relation.getDestination();

			// add relation iff child does not already have a parent
			Params npChild = getParams(target);
			if (!npChild.hasParent()) {
				npChild.setParent(source);
				getParams(source).addChild(target);
				Log.debug("Parent= " + source.toString() + "; child " + target.toString());
			} else {
				omitted.add(relation);
				Log.debug("Parent? " + source.toString() + "; child " + target.toString());
			}

			roots.remove(target); // target cannot be a root
		}

		if (roots.size() == 0) return null;

		InternalNode virtRoot = null;
		if (roots.size() > 1) {
			Log.debug("Adding virtual root for multiple real roots ...");
			virtRoot = new InternalNode(new VirtualRootNode());
			new Params(virtRoot);
			for (InternalNode root : roots) {
				getParams(root).setParent(virtRoot);
				getParams(virtRoot).addChild(root);
			}
		} else {
			virtRoot = roots.iterator().next();
		}

		omitted = analyzeOmitted(virtRoot, omitted);
		if (!omitted.isEmpty()) {
			// replace existing parents of ommited targets
			for (InternalRelationship relation : omitted) {
				InternalNode source = relation.getSource();
				InternalNode target = relation.getDestination();

				// ignore self loops
				if (source.equals(target)) continue;

				// reverse edge direction and connect
				Params npChild = getParams(target);
				InternalNode oldParent = npChild.getParent();
				getParams(oldParent).removeChild(target);
				Log.debug("Parent- " + oldParent.toString() + "; child " + target.toString());

				npChild.setParent(source);
				getParams(source).addChild(target);
				Log.debug("Parent+ " + source.toString() + "; child " + target.toString());
			}
		}

		return virtRoot;
	}

	// walk to the root to validate the targets of the omitted
	// return omitted with unreachable targets
	private ArrayList<InternalRelationship> analyzeOmitted(InternalNode root,
			ArrayList<InternalRelationship> omitted) {
		if (omitted.isEmpty()) return omitted;

		ArrayList<InternalRelationship> fixList = new ArrayList<>();
		for (InternalRelationship relation : omitted) {
			InternalNode target = relation.getDestination();
			if (!connected(root, target)) fixList.add(relation);
		}
		return fixList;
	}

	private boolean connected(InternalNode root, InternalNode target) {
		List<InternalNode> seen = new ArrayList<>();
		Params child = getParams(target);
		while (child.hasParent()) {
			InternalNode parent = child.getParent();
			if (root.equals(parent)) return true;
			if (seen.contains(parent)) return false;
			seen.add(parent);
			child = getParams(parent);
		}
		return false;
	}

	private void firstWalk(InternalNode entity, int num, int depth) {
		Log.debug("Walk1 [entity=" + entity.toString() + ", num=" + num + ", depth=" + depth + "]");

		Params np = getParams(entity);
		np.number = num;
		updateDepths(depth, entity);

		if (np.childCount() == 0) { // the definition of a leaf
			InternalNode sibling = np.previousSibling();
			if (sibling == null) {
				np.prelim = 0;
			} else {
				Params npSibling = getParams(sibling);
				np.prelim = npSibling.prelim + spacing(sibling, entity, true);
			}
		} else { // all other nodes define subtrees
			InternalNode leftMost = np.firstChild();
			InternalNode rightMost = np.lastChild();
			InternalNode defaultAncestor = leftMost;
			InternalNode c = leftMost;
			for (int idx = 0; c != null; ++idx, c = getParams(c).nextSibling()) {
				firstWalk(c, idx, depth + 1);
				defaultAncestor = apportion(c, defaultAncestor);
			}

			executeShifts(entity);

			double midpoint = 0.5 * (getParams(leftMost).prelim + getParams(rightMost).prelim);

			InternalNode left = np.previousSibling();
			if (left != null) {
				np.prelim = getParams(left).prelim + spacing(left, entity, true);
				np.mod = np.prelim - midpoint;
			} else {
				np.prelim = midpoint;
			}
		}
	}

	// ------------------------------------------------------------------------

	private double spacing(InternalNode first, InternalNode second, boolean areSiblings) {
		double aveSize = 0;
		boolean vert = (m_orientation == ORIENT_TOP_BOTTOM || m_orientation == ORIENT_BOTTOM_TOP);
		if (vert) {
			aveSize = 0.5 * first.getWidthInLayout() + second.getWidthInLayout();
		} else {
			aveSize = 0.5 * first.getHeightInLayout() + second.getHeightInLayout();
		}
		return (areSiblings ? m_bspace : m_tspace) + aveSize;
	}

	private void updateDepths(int depth, InternalNode entity) {
		boolean vert = (m_orientation == ORIENT_TOP_BOTTOM || m_orientation == ORIENT_BOTTOM_TOP);
		double d = vert ? entity.getHeightInLayout() : entity.getWidthInLayout();
		if (m_depths.length <= depth) m_depths = resize(m_depths, 3 * depth / 2);
		m_depths[depth] = Math.max(m_depths[depth], d);
		m_maxDepth = Math.max(m_maxDepth, depth);
	}

	private void determineDepths() {
		for (int idx = 1; idx < m_maxDepth; ++idx) m_depths[idx] += m_depths[idx - 1] + m_dspace;
	}

	private InternalNode apportion(InternalNode v, InternalNode a) {
		InternalNode w = getParams(v).previousSibling();
		if (w != null) {
			InternalNode vip, vim, vop, vom;
			double sip, sim, sop, som;

			vip = vop = v;
			vim = w;
			vom = getParams(vip).firstSibling();

			sip = getParams(vip).mod;
			sop = getParams(vop).mod;
			sim = getParams(vim).mod;
			som = getParams(vom).mod;

			InternalNode nr = nextRight(vim);
			InternalNode nl = nextLeft(vip);
			while (nr != null && nl != null) {
				vim = nr;
				vip = nl;
				vom = nextLeft(vom);
				vop = nextRight(vop);
				getParams(vop).ancestor = v;
				double shift = (getParams(vim).prelim + sim) - (getParams(vip).prelim + sip)
						+ spacing(vim, vip, false);
				if (shift > 0) {
					moveSubtree(ancestor(vim, v, a), v, shift);
					sip += shift;
					sop += shift;
				}
				sim += getParams(vim).mod;
				sip += getParams(vip).mod;
				som += getParams(vom).mod;
				sop += getParams(vop).mod;

				nr = nextRight(vim);
				nl = nextLeft(vip);
			}
			if (nr != null && nextRight(vop) == null) {
				Params vopp = getParams(vop);
				vopp.thread = nr;
				vopp.mod += sim - sop;
			}
			if (nl != null && nextLeft(vom) == null) {
				Params vomp = getParams(vom);
				vomp.thread = nl;
				vomp.mod += sip - som;
				a = v;
			}
		}
		return a;
	}

	private InternalNode nextLeft(InternalNode n) {
		Params np = getParams(n);
		if (np.hasChildren()) return np.firstChild();
		return np.thread;
	}

	private InternalNode nextRight(InternalNode n) {
		Params np = getParams(n);
		if (np.hasChildren()) return np.lastChild();
		return np.thread;
	}

	private void moveSubtree(InternalNode wm, InternalNode wp, double shift) {
		Params wmp = getParams(wm);
		Params wpp = getParams(wp);
		double subtrees = wpp.number - wmp.number;
		wpp.change -= shift / subtrees;
		wpp.shift += shift;
		wmp.change += shift / subtrees;
		wpp.prelim += shift;
		wpp.mod += shift;
	}

	private void executeShifts(InternalNode n) {
		double shift = 0;
		double change = 0;
		Params np = getParams(n);
		for (InternalNode c = np.lastChild(); c != null; c = getParams(c).previousSibling()) {
			Params cp = getParams(c);
			cp.prelim += shift;
			cp.mod += shift;
			change += cp.change;
			shift += cp.shift + change;
		}
	}

	private InternalNode ancestor(InternalNode vim, InternalNode v, InternalNode a) {
		InternalNode p = getParams(v).getParent();
		Params vimp = getParams(vim);
		if (vimp.ancestor != null && getParams(vimp.ancestor).getParent() == p) {
			return vimp.ancestor;
		} else {
			return a;
		}
	}

	private void secondWalk(InternalNode n, InternalNode p, double m, int depth) {
		Log.debug("Walk2 [entity=" + n.toString() + ", num=" + m + ", depth=" + depth + "]");

		Params np = getParams(n);
		setBreadth(n, p, np.prelim + m);
		setDepth(n, p, m_depths[depth]);

		if (getParams(n).hasChildren()) {
			depth += 1;
			for (InternalNode c = np.firstChild(); c != null; c = getParams(c).nextSibling()) {
				secondWalk(c, n, m + np.mod, depth);
			}
		}
		np.clear();
	}

	private Point getLayoutAnchor(double boundsX, double boundsY, double boundsWidth, double boundsHeight,
			boolean virtualAnchor) {

		if (virtualAnchor) m_offset -= m_dspace;
		Point anchor = new Point();
		switch (m_orientation) {
			case ORIENT_LEFT_RIGHT:
				anchor.setLocation((int) m_offset, (int) (boundsHeight / 2.0));
				break;
			case ORIENT_RIGHT_LEFT:
				anchor.setLocation((int) (boundsWidth - m_offset), (int) (boundsHeight / 2.0));
				break;
			case ORIENT_TOP_BOTTOM:
				anchor.setLocation((int) (boundsWidth / 2.0), (int) m_offset);
				break;
			case ORIENT_BOTTOM_TOP:
				anchor.setLocation((int) (boundsWidth / 2.0), (int) (boundsHeight - m_offset));
				break;
		}
		return anchor;
	}

	private void setBreadth(InternalNode n, InternalNode p, double b) {
		switch (m_orientation) {
			case ORIENT_LEFT_RIGHT:
			case ORIENT_RIGHT_LEFT:
				setY(n, p, m_anchor.y + b);
				break;
			case ORIENT_TOP_BOTTOM:
			case ORIENT_BOTTOM_TOP:
				setX(n, p, m_anchor.x + b);
				break;
			default:
				throw new IllegalStateException();
		}
	}

	private void setDepth(InternalNode n, InternalNode p, double d) {
		switch (m_orientation) {
			case ORIENT_LEFT_RIGHT:
				setX(n, p, m_anchor.x + d);
				break;
			case ORIENT_RIGHT_LEFT:
				setX(n, p, m_anchor.x - d);
				break;
			case ORIENT_TOP_BOTTOM:
				setY(n, p, m_anchor.y + d);
				break;
			case ORIENT_BOTTOM_TOP:
				setY(n, p, m_anchor.y - d);
				break;
			default:
				throw new IllegalStateException();
		}
	}

	private void setX(InternalNode n, InternalNode p, double d) {
		n.setLocation(d, n.getCurrentY());
	}

	private void setY(InternalNode n, InternalNode p, double y) {
		n.setLocation(n.getCurrentX(), y);
	}

	/** Wrapper class holding parameters used for each node in this layout. */
	class Params implements Cloneable {

		double prelim; // preliminary virtual x-axis position
		double mod; // accumulated modifier of the prelim
		double shift;
		double change;
		int number = -2;
		InternalNode ancestor = null; // a common connected predecessor/parent
		InternalNode thread = null; // the successor leaf in the contour

		InternalNode self = null; // the containing entity
		InternalNode parent = null; // immediate parent node
		private ArrayList<InternalNode> childList = new ArrayList<>();

		public Params(InternalNode item) {
			self = item;
			self.setAttributeInLayout(PARAMS, this);
			number = -1;
			Log.setLevel(Level.INFO);
		}

		public void clear() {
			number = -2;
			prelim = 0;
			mod = 0;
			shift = 0;
			change = 0;
			ancestor = null;
			thread = null;
		}

		/** adds an immediate connected child */
		public void addChild(InternalNode childData) {
			childList.add(childData);
		}

		public void removeChild(InternalNode target) {
			childList.remove(target);
		}

		public boolean hasChildren() {
			return childList.size() > 0;
		}

		public int childCount() {
			return childList.size();
		}

		public InternalNode firstChild() {
			if (hasChildren()) return childList.get(0);
			return null;
		}

		public InternalNode lastChild() {
			if (hasChildren()) return childList.get(childList.size() - 1);
			return null;
		}

		public InternalNode childAt(int idx) {
			if (childList.size() > idx) return childList.get(idx);
			return null;
		}

		public boolean hasParent() {
			return parent != null;
		}

		/** adds an immediate common connected parent */
		public void setParent(InternalNode parent) {
			this.parent = parent;
			/* if (ancestor != null) */ancestor = parent;
		}

		public InternalNode getParent() {
			return parent;
		}

		public InternalNode firstSibling() {
			if (hasParent()) {
				InternalNode parent = getParent();
				Params npParent = getParams(parent);
				return npParent.childAt(0);
			}
			return null;
		}

		// return null if no parent or no next sibling
		public InternalNode nextSibling() {
			if (hasParent()) {
				InternalNode parent = getParent();
				Params npParent = getParams(parent);
				int nextChild = npParent.childList.indexOf(self) + 1;
				int numChildren = npParent.childCount();
				if (nextChild < numChildren) {
					Log.debug("Next [entity=" + nextChild + ":" + npParent.childAt(nextChild) + "]");
					return npParent.childAt(nextChild);
				}
				return null;
			}
			return null;
		}

		// return null if no parent or no previous sibling
		public InternalNode previousSibling() {
			if (hasParent()) {
				InternalNode parent = getParent();
				Params npParent = getParams(parent);
				int prevChild = npParent.childList.indexOf(self) - 1;
				if (prevChild < 0) return null;
				Log.debug("Previous [entity=" + prevChild + ":" + npParent.childAt(prevChild) + "]");
				return npParent.childAt(prevChild);
			}
			return null;
		}

		@Override
		public String toString() {
			return parent.toString() + " -> " + childList.toString();
		}
	}

	// ///////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////

	private Params getParams(InternalNode entity) {
		if (entity == null) {
			Log.debug("entity is null");
		}
		if (entity.getAttributeInLayout(PARAMS) == null) {
			Log.debug("params is null");
		}

		return (Params) entity.getAttributeInLayout(PARAMS);
	}

	private <E> ArrayList<E> convert(E[] elements) {
		return new ArrayList<>(Arrays.asList(elements));
	}

	/** Resize the given array as needed to meet a target size. */
	private double[] resize(double[] inArray, int size) {
		if (inArray.length >= size) return inArray;
		double[] outArray = new double[size];
		System.arraycopy(inArray, 0, outArray, 0, inArray.length);
		return outArray;
	}

	// ///////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////

	@Override
	protected int getCurrentLayoutStep() {
		return 0;
	}

	@Override
	protected int getTotalNumberOfLayoutSteps() {
		return 0;
	}

	@Override
	protected boolean isValidConfiguration(boolean asynchronous, boolean continuous) {
		return true;
	}

	@Override
	protected void postLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider) {}

	@Override
	protected void preLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider, double x, double y, double width,
			double height) {}

	@Override
	public void setLayoutArea(double x, double y, double width, double height) {}

	// ///////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////

	class VirtualRootNode implements LayoutEntity {
		private String name;
		private double x;
		private double y;
		private double width;
		private double height;
		private HashMap<Object, Object> attributes;
		private Object internalNode;

		public VirtualRootNode() {
			name = "VirtualRoot";
			x = 0;
			y = 0;
			width = 0;
			height = 0;
			attributes = new HashMap<>();
		}

		@Override
		public double getHeightInLayout() {
			return height;
		}

		@Override
		public double getWidthInLayout() {
			return width;
		}

		@Override
		public double getXInLayout() {
			return x;
		}

		@Override
		public double getYInLayout() {
			return y;
		}

		@Override
		public Object getLayoutInformation() {
			return internalNode;
		}

		@Override
		public void setLayoutInformation(Object internalEntity) {
			internalNode = internalEntity;
		}

		@Override
		public void setSizeInLayout(double width, double height) {
			this.width = width;
			this.height = height;
		}

		public void setLocation(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public void setLocationInLayout(double x, double y) {
			this.x = x;
			this.y = y;
		}

		@Override
		public Object getGraphData() {
			return null;
		}

		@Override
		public void setGraphData(Object o) {}

		@Override
		public void populateLayoutConstraint(LayoutConstraint constraint) {}

		public void setAttributeInLayout(Object attribute, Object value) {
			attributes.put(attribute, value);
		}

		public Object getAttributeInLayout(Object attribute) {
			return attributes.get(attribute);
		}

		@Override
		public int compareTo(Object o) {
			return 1;
		}

		@Override
		public boolean equals(Object object) {
			if (object instanceof VirtualRootNode) return true;
			return false;
		}

		@Override
		public int hashCode() {
			return (name + "_135791315").hashCode();
		}

		@Override
		public String toString() {
			return name.toString();
		}
	}
}
