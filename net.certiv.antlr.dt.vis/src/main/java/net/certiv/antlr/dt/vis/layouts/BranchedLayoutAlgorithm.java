/*******************************************************************************
 * Copyright 2005-2020, Gerald B. Rosenberg, Certiv Analytics . All rights reserved. This program
 * and the accompanying materials are made available under the terms of the Eclipse Public License
 * v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics
 *******************************************************************************/
package net.certiv.antlr.dt.vis.layouts;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;

import org.eclipse.draw2d.Animation;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.zest.layouts.algorithms.AbstractLayoutAlgorithm;
import org.eclipse.zest.layouts.dataStructures.InternalNode;
import org.eclipse.zest.layouts.dataStructures.InternalRelationship;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.common.log.Log;
import net.certiv.dsl.core.preferences.IPrefsManager;

public class BranchedLayoutAlgorithm extends AbstractLayoutAlgorithm {

	private static final Object CALL_GRAPH = "callGraphData";
	private int horzSpacing = 40;
	private int vertSpacing = 20;
	private int targetLead = 10;

	private LinkedHashMap<InternalNode, ArrayList<InternalRelationship>> virt;
	private ArrayList<InternalNode> roots;
	private ArrayList<InternalNode> completedLayout; // TODO: better as a HashSet?
	private ArrayList<Block> blockList;

	public BranchedLayoutAlgorithm(int styles) {
		super(styles);
	}

	private IPrefsManager getPrefs() {
		return AntlrCore.getDefault().getPrefsManager();
	}

	@Override
	protected void applyLayoutInternal(InternalNode[] entitiesToLayout, InternalRelationship[] relationshipsToConsider,
			double boundsX, double boundsY, double boundsWidth, double boundsHeight) {

		Log.info( "Starting layout . . .");
		boolean disableAnimation = getPrefs().getInt(PrefsKey.PT_ANIMATION_LIMIT) < entitiesToLayout.length;
		if (disableAnimation) Animation.run();

		horzSpacing = getPrefs().getInt(PrefsKey.PT_HORZ_SPACING);
		vertSpacing = getPrefs().getInt(PrefsKey.PT_VERT_SPACING);
		targetLead = getPrefs().getInt(PrefsKey.PT_TARGET_LEAD);

		ArrayList<InternalNode> entitiesList = convert(entitiesToLayout);
		ArrayList<InternalRelationship> relationshipList = convert(relationshipsToConsider);

		// TODO: reduce globals?
		blockList = new ArrayList<>(); // capture of child blocks
		completedLayout = new ArrayList<>(); // used to prevent cycles

		// (1) build the association network
		constructVirtualLayout(entitiesList, relationshipList);
		if (roots.size() == 0) {
			Log.error( "No roots; has to be a directed graph at least at the root level");
			return;
		}

		// (2) layout each distinctly rooted sequence of nodes relative to prior root
		for (int idx = 0; idx < roots.size(); idx++) {
			InternalNode prev = null;
			if (idx > 0) prev = roots.get(idx - 1);
			InternalNode root = roots.get(idx);
			initRootPosition(root, prev);
			layoutEntity(root);
		}

		// (3) distribute the blocks to remove overlaps
		boolean overlaps = true;
		int revs = 0;
		while (overlaps && revs < 100) {
			overlaps = false;
			revs++;
			for (Block srcBlock : blockList) {
				for (Block tgtBlock : blockList) {
					if (srcBlock.equals(tgtBlock)) continue; // skip identity
					// Log.debug( "Block: " + str(srcBlock) + ", Examining: " + str(tgtBlock));
					if (srcBlock.overlaps(tgtBlock)) {
						// Rectangle overlap = srcBlock.getOverlap(tgtBlock); // debugging
						// Dimension delta = srcBlock.getOverlapDelta(tgtBlock);
						// Log.debug( "Overlap: " + str(overlap) + ", delta: " + str(delta));
						pushOverlapping(srcBlock, tgtBlock);
						overlaps = true;
					}
				}
			}
		}
		Log.debug( "Resolved [iterations=" + revs + "]");

		// (4) tidy the distribution of blocks TODO: implement block tidy

		// (5) assign actual locations to the nodes
		for (InternalNode entity : entitiesList) {
			LayoutData data = (LayoutData) entity.getAttributeInLayout(CALL_GRAPH);
			double x = data.getLocation().x;
			double y = data.getLocation().y;
			entity.setLocation(x, y);
		}

		if (disableAnimation) Animation.markBegin();
	}

	private void constructVirtualLayout(ArrayList<InternalNode> entitiesList,
			ArrayList<InternalRelationship> relationshipList) {

		// start with all entities as potential roots
		roots = new ArrayList<>(entitiesList);
		virt = new LinkedHashMap<>();
		ArrayList<InternalNode> parentedList = new ArrayList<>();

		for (InternalNode entity : entitiesList) {
			ArrayList<InternalRelationship> relations = new ArrayList<>();
			new LayoutData(entity);
			virt.put(entity, relations);
		}
		for (InternalRelationship relation : relationshipList) {
			// build the entity associations
			InternalNode source = relation.getSource();
			ArrayList<InternalRelationship> relations = virt.get(source);
			relations.add(relation);
			// accumulate nodes that cannot be roots
			InternalNode target = relation.getDestination();
			parentedList.add(target);
		}
		// remainder is the list of nodes without parents
		roots.removeAll(parentedList);
	}

	private void initRootPosition(InternalNode root, InternalNode prev) {
		LayoutData rootData = (LayoutData) root.getAttributeInLayout(CALL_GRAPH);
		if (prev == null) {
			rootData.getLocation().x = 0;
			rootData.getLocation().y = 0;
		} else {
			LayoutData prevData = (LayoutData) prev.getAttributeInLayout(CALL_GRAPH);
			LayoutData lastData = prevData.lastChild();
			if (lastData == null) {
				double nodeHeight = prevData.getEntity().getHeightInLayout();
				rootData.getLocation().x = prevData.getLocation().x;
				rootData.getLocation().y = (int) (prevData.getLocation().y + nodeHeight + vertSpacing);
			} else {
				double nodeHeight = lastData.getEntity().getHeightInLayout();
				rootData.getLocation().x = prevData.getLocation().x;
				rootData.getLocation().y = (int) (lastData.getLocation().y + nodeHeight + vertSpacing);
			}
		}
		rootData.getSize().x = (int) root.getWidthInLayout();
		rootData.getSize().y = (int) root.getHeightInLayout();
	}

	private void layoutEntity(InternalNode source) {
		ArrayList<InternalRelationship> relations = virt.get(source);

		// perform default layout of children
		Block block = new Block(source);
		for (InternalRelationship relation : relations) {
			positionChild(source, relation.getDestination());
			block.add(relation.getDestination()); // add unique children to block
		}
		completedLayout.add(source); // mark to block recurse

		// if there are unique children, save the block
		if (!block.isEmpty()) {
			block.addToParent();
			blockList.add(block);
		}

		// recurse - layout children if not already completed
		for (InternalRelationship relation : relations) {
			InternalNode dstNode = relation.getDestination();
			if (!completedLayout.contains(dstNode)) layoutEntity(dstNode);
		}
	}

	private void positionChild(InternalNode source, InternalNode target) {
		LayoutData srcData = (LayoutData) source.getAttributeInLayout(CALL_GRAPH);
		LayoutData tgtData = (LayoutData) target.getAttributeInLayout(CALL_GRAPH);
		LayoutData lstData = srcData.lastChild();
		if (!tgtData.isPositioned()) {
			if (lstData == null) { // place relative to source
				double nodeWidth = srcData.getEntity().getWidthInLayout();
				tgtData.getLocation().x = (int) (srcData.getLocation().x + nodeWidth + horzSpacing);
				tgtData.getLocation().y = srcData.getLocation().y;
				srcData.addChild(tgtData);
			} else { // place relative to last target
				double nodeHeight = lstData.getEntity().getHeightInLayout();
				tgtData.getLocation().x = lstData.getLocation().x;
				tgtData.getLocation().y = (int) (lstData.getLocation().y + nodeHeight + vertSpacing);
				srcData.addChild(tgtData);
			}
			tgtData.getSize().x = (int) target.getWidthInLayout();
			tgtData.getSize().y = (int) target.getHeightInLayout();
			tgtData.setPositioned(true);
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
		for (Block child : block.getChildBlocks()) {
			pushSubtree(child, delta);
		}
	}

	// ///////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////

	private <E> ArrayList<E> convert(E[] elements) {
		return new ArrayList<>(Arrays.asList(elements));
	}

	// ///////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////

	@SuppressWarnings("unused")
	private String str(Block block) {
		Rectangle r = block.getBounds();
		return str(r);
	}

	private String str(Rectangle r) {
		return "[x=" + r.x + ", y=" + r.y + ", w=" + r.width + ", h=" + r.height + "]";
	}

	@SuppressWarnings("unused")
	private String str(Dimension d) {
		return "[x=" + d.width + ", y=" + d.height + "]";
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
	protected void preLayoutAlgorithm(InternalNode[] entitiesToLayout, InternalRelationship[] relationshipsToConsider,
			double x, double y, double width, double height) {}

	@Override
	protected void postLayoutAlgorithm(InternalNode[] entitiesToLayout,
			InternalRelationship[] relationshipsToConsider) {}

	@Override
	public void setLayoutArea(double x, double y, double width, double height) {}

	// ///////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////

	class Block extends ArrayList<InternalNode> {

		private static final long serialVersionUID = 1L;
		private InternalNode source;
		private ArrayList<Block> childList;
		private Rectangle bounds;

		public Block(InternalNode source) {
			this.source = source;
			this.childList = new ArrayList<>();
		}

		public void addToParent() {
			for (Block parent : blockList) {
				if (parent.contains(source)) {
					parent.addChildBlock(this);
					break;
				}
			}
		}

		private void addChildBlock(Block block) {
			childList.add(block);
		}

		public ArrayList<Block> getChildBlocks() {
			return this.childList;
		}

		@Override
		public boolean add(InternalNode node) {
			// if the node exists in a prior Block, do not add it to this one
			for (Block b : blockList) {
				if (b.contains(node)) return true; // per override contract
			}

			// add the node and update the bounds
			LayoutData data = (LayoutData) node.getAttributeInLayout(CALL_GRAPH);
			Point loc = data.getLocation();
			Rectangle nRect = new Rectangle(loc.x, loc.y, data.getSize().x, data.getSize().y);
			if (bounds == null) {
				bounds = nRect;
			} else {
				bounds.union(nRect);
			}
			// Log.debug( "BlockAdd: " + blockList.size() + ":" +
			// this.size() + "::" + bounds);
			return super.add(node);
		}

		public InternalNode getSource() {
			return source;
		}

		public Rectangle getBounds() {
			return this.bounds;
		}

		public boolean isAbove(Block other) {
			Dimension delta = getOverlapDelta(other);
			return delta.height > 0 && this.bounds.y < other.getBounds().y;
		}

		public boolean isBelow(Block other) {
			Dimension delta = getOverlapDelta(other);
			return delta.height > 0 && this.bounds.y > other.getBounds().y;
		}

		public boolean isRightOf(Block other) {
			Dimension delta = getOverlapDelta(other);
			return delta.width > 0 && this.bounds.x > other.getBounds().x;
		}

		public boolean isLeftOf(Block other) {
			Dimension delta = getOverlapDelta(other);
			return delta.width > 0 && this.bounds.x < other.getBounds().x;
		}

		public boolean overlaps(Block block) {
			// add left and right guard bands
			int horzGuard = targetLead * 3; // 1.5 per side
			Rectangle guardBlock = block.getBounds().getExpanded(horzGuard, 0);
			return this.bounds.intersects(guardBlock);
		}

		public Rectangle getOverlap(Block other) {
			return this.bounds.getIntersection(other.getBounds());
		}

		public Dimension getOverlapDelta(Block other) {
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

		public void move(Dimension delta) {
			move(delta.width, delta.height);
		}

		public void move(int x, int y) {
			bounds.translate(x, y); // update block bounds
			for (InternalNode node : this) { // move block children
				LayoutData data = (LayoutData) node.getAttributeInLayout(CALL_GRAPH);
				Point loc = data.getLocation();
				loc.x += x;
				loc.y += y;
			}
		}
	}

	// ///////////////////////////////////////////////////////////////////////
	// ///////////////////////////////////////////////////////////////////////

	class LayoutData {

		private InternalNode entity;
		private Point location = new Point();
		private Point size = new Point();
		private ArrayList<LayoutData> childDataList = new ArrayList<>();
		private boolean positioned = false;

		public LayoutData(InternalNode entity) {
			this.entity = entity;
			entity.setAttributeInLayout(CALL_GRAPH, this);
		}

		public boolean isPositioned() {
			return positioned;
		}

		public void setPositioned(boolean positioned) {
			this.positioned = positioned;
		}

		public InternalNode getEntity() {
			return entity;
		}

		public Point getLocation() {
			return location;
		}

		public Rectangle getBounds() {
			Point loc = getLocation();
			return new Rectangle(loc.x, loc.y, size.x, size.y);
		}

		public void setLocation(Point location) {
			this.location = location;
		}

		public Point getSize() {
			return size;
		}

		public void setSize(Point size) {
			this.size = size;
		}

		public ArrayList<LayoutData> getChildDataList() {
			return childDataList;
		}

		public void addChild(LayoutData childData) {
			this.childDataList.add(childData);
		}

		public LayoutData firstChild() {
			if (childDataList.size() > 0) return childDataList.get(0);
			return null;
		}

		public LayoutData lastChild() {
			if (childDataList.size() > 0) return childDataList.get(childDataList.size() - 1);
			return null;
		}

		public LayoutData getChild(int idx) {
			if (childDataList.size() > idx) return childDataList.get(idx);
			return null;
		}
	}
}
