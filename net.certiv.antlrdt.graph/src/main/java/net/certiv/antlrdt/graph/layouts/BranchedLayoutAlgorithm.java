/*******************************************************************************
 * Copyright 2005-2019, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.antlrdt.graph.layouts;

import java.util.List;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.layout.ILayoutAlgorithm;
import org.eclipse.gef.layout.LayoutContext;
import org.eclipse.gef.layout.LayoutProperties;

import com.google.common.collect.Lists;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.preferences.DslPrefsManager;

public class BranchedLayoutAlgorithm implements ILayoutAlgorithm {

	private static final String BRANCHED = "branched_layout";

	private int horzSpacing = 40;
	private int vertSpacing = 20;
	private int targetLead = 10;

	private DslPrefsManager prefsMgr;

	@SuppressWarnings("unused")
	private int styles;

	private final List<Block> blocks = Lists.newArrayList();
	private final List<Node> completed = Lists.newArrayList();

	public BranchedLayoutAlgorithm(int styles) {
		super();
		this.styles = styles;
		prefsMgr = AntlrDTCore.getDefault().getPrefsManager();
	}

	@Override
	public void applyLayout(LayoutContext context, boolean clean) {
		if (!clean) return;

		Log.info(this, "Starting layout . . .");
		horzSpacing = prefsMgr.getInt(PrefsKey.PT_HORZ_SPACING);
		vertSpacing = prefsMgr.getInt(PrefsKey.PT_VERT_SPACING);
		targetLead = prefsMgr.getInt(PrefsKey.PT_TARGET_LEAD);

		// (1) build the association network

		List<Node> entities = init(context.getNodes());
		List<Node> roots = findRealRoots(entities);

		if (roots.size() == 0) {
			Log.error(this, "No roots; has to be a directed graph at least at the root level");
			return;
		}

		// (2) layout each distinctly rooted sequence of nodes relative to prior root

		for (int idx = 0; idx < roots.size(); idx++) {
			Node prev = idx > 0 ? roots.get(idx - 1) : null;
			Node root = roots.get(idx);
			initRootPosition(root, prev);
			layoutEntity(root);
		}

		// (3) distribute the blocks to remove overlaps
		boolean overlaps = true;
		int revs = 0;
		while (overlaps && revs < 100) {
			overlaps = false;
			revs++;
			for (Block srcBlock : blocks) {
				for (Block tgtBlock : blocks) {
					if (srcBlock.equals(tgtBlock)) continue; // skip identity
					if (srcBlock.overlaps(tgtBlock)) {
						pushOverlapping(srcBlock, tgtBlock);
						overlaps = true;
					}
				}
			}
		}

		// (4) tidy the distribution of blocks

		// (5) assign actual locations to the nodes
		for (Node entity : entities) {
			LayoutData data = getLayoutData(entity);
			LayoutProperties.setLocation(entity, data.location);
			Log.debug(this, "Node: " + entity.toString());
		}
	}

	private List<Node> init(Node[] nodes) {
		List<Node> entities = Lists.newArrayList();
		for (Node node : nodes) {
			new LayoutData(node);
			entities.add(node);
		}
		return entities;
	}

	private List<Node> findRealRoots(List<Node> entities) {
		List<Node> roots = Lists.newArrayList();

		for (Node entity : entities) {
			if (entity.getAllIncomingEdges().isEmpty()) {
				roots.add(entity);
			}
		}
		return roots;
	}

	private void initRootPosition(Node root, Node prev) {
		LayoutData rootData = getLayoutData(root);
		if (prev == null) {
			rootData.location.x = 0;
			rootData.location.y = 0;
		} else {
			LayoutData prevData = getLayoutData(prev);
			LayoutData lastData = prevData.lastChild();
			if (lastData == null) {
				double nodeHeight = getHeight(prevData.entity);
				rootData.location.x = prevData.location.x;
				rootData.location.y = (int) (prevData.location.y + nodeHeight + vertSpacing);
			} else {
				double nodeHeight = getHeight(lastData.entity);
				rootData.location.x = prevData.location.x;
				rootData.location.y = (int) (lastData.location.y + nodeHeight + vertSpacing);
			}
		}
		rootData.size.x = (int) getWidth(root);
		rootData.size.y = (int) getHeight(root);
	}

	// perform default layout of children
	private void layoutEntity(Node source) {
		Block block = new Block(source);
		for (Edge edge : source.getAllOutgoingEdges()) {
			positionChild(source, edge.getTarget());
			block.add(edge.getTarget());
		}
		completed.add(source); // mark to block recurse

		// if there are unique children, save the block
		if (!block.children.isEmpty()) {
			block.addToParent();
			blocks.add(block);
		}

		// recurse - layout children if not already completed
		for (Edge edge : source.getAllOutgoingEdges()) {
			Node target = edge.getTarget();
			if (!completed.contains(target)) layoutEntity(target);
		}
	}

	private void positionChild(Node source, Node target) {
		LayoutData srcData = getLayoutData(source);
		LayoutData tgtData = getLayoutData(target);
		LayoutData lstData = srcData.lastChild();
		if (!tgtData.positioned) {

			if (lstData == null) { // place relative to source
				double nodeWidth = getWidth(srcData.entity);
				tgtData.location.x = (int) (srcData.location.x + nodeWidth + horzSpacing);
				tgtData.location.y = srcData.location.y;
				srcData.children.add(tgtData);

			} else { // place relative to last target
				double nodeHeight = getHeight(lstData.entity);
				tgtData.location.x = lstData.location.x;
				tgtData.location.y = (int) (lstData.location.y + nodeHeight + vertSpacing);
				srcData.children.add(tgtData);
			}

			tgtData.size.x = (int) getWidth(target);
			tgtData.size.y = (int) getHeight(target);
			tgtData.positioned = true;
		}
	}

	private void pushOverlapping(Block srcBlock, Block tgtBlock) {
		Dimension delta = srcBlock.getOverlapDelta(tgtBlock);
		if (srcBlock.isRightOf(tgtBlock) || srcBlock.isBelow(tgtBlock)) {
			pushSubtree(srcBlock, delta);
		} else {
			pushSubtree(tgtBlock, delta);
		}
	}

	private void pushSubtree(Block block, Dimension delta) {
		block.move(delta);
		for (Block child : block.subBlocks) {
			pushSubtree(child, delta);
		}
	}

	private LayoutData getLayoutData(Node node) {
		return (LayoutData) node.getAttributes().get(BRANCHED);
	}

	private double getWidth(Node node) {
		return LayoutProperties.getSize(node).getWidth();
	}

	private double getHeight(Node node) {
		return LayoutProperties.getSize(node).getHeight();
	}

	private class Block {

		Node source;
		Rectangle bounds;

		final List<Block> subBlocks = Lists.newArrayList();
		final List<Node> children = Lists.newArrayList();

		Block(Node source) {
			this.source = source;
		}

		void addToParent() {
			for (Block parent : blocks) {
				if (parent.children.contains(source)) {
					parent.subBlocks.add(this);
					break;
				}
			}
		}

		// if the node exists in a prior Block, do not add it to this one
		boolean add(Node node) {
			for (Block b : blocks) {
				if (b.children.contains(node)) return true;
			}

			// add the node and update the bounds
			LayoutData data = getLayoutData(node);
			Point loc = data.location;
			Rectangle rect = new Rectangle((int) loc.x, (int) loc.y, (int) data.size.x, (int) data.size.y);
			if (bounds == null) {
				bounds = rect;
			} else {
				bounds.union(rect);
			}
			return children.add(node);
		}

		@SuppressWarnings("unused")
		boolean isAbove(Block other) {
			Dimension delta = getOverlapDelta(other);
			return delta.height > 0 && bounds.getY() < other.bounds.getY();
		}

		boolean isBelow(Block other) {
			Dimension delta = getOverlapDelta(other);
			return delta.height > 0 && bounds.getY() > other.bounds.getY();
		}

		boolean isRightOf(Block other) {
			Dimension delta = getOverlapDelta(other);
			return delta.width > 0 && bounds.getX() > other.bounds.getX();
		}

		@SuppressWarnings("unused")
		boolean isLeftOf(Block other) {
			Dimension delta = getOverlapDelta(other);
			return delta.width > 0 && bounds.getX() < other.bounds.getX();
		}

		// with left and right guard bands
		boolean overlaps(Block block) {
			int horzGuard = targetLead * 3; // 1.5 per side
			Rectangle guardBlock = block.bounds.getExpanded(horzGuard, 0);
			return !bounds.getIntersected(guardBlock).isEmpty();
		}

		Rectangle getOverlap(Block other) {
			return bounds.getIntersected(other.bounds);
		}

		Dimension getOverlapDelta(Block other) {
			Dimension delta = getOverlap(other).getSize();
			if (delta.width > delta.height) {
				delta.width = 0;
				delta.height += vertSpacing;
			} else {
				delta.width += horzSpacing;
				delta.height = 0;
			}
			return delta;
		}

		void move(Dimension delta) {
			move(delta.width, delta.height);
		}

		void move(double width, double height) {
			bounds.translate(width, height);	// update bounds
			for (Node node : children) {		// move children
				Point loc = getLayoutData(node).location;
				loc.x += width;
				loc.y += height;
			}
		}
	}

	private class LayoutData {

		Node entity;
		Point location = new Point();
		Point size = new Point();
		boolean positioned = false;

		final List<LayoutData> children = Lists.newArrayList();

		LayoutData(Node entity) {
			this.entity = entity;
			entity.getAttributes().put(BRANCHED, this);
		}

		LayoutData lastChild() {
			if (children.size() > 0) return children.get(children.size() - 1);
			return null;
		}
	}
}
