/*******************************************************************************
 * Copyright 2005-2019, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.antlrdt.graph.layouts;

import java.util.Map.Entry;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.layout.ILayoutAlgorithm;
import org.eclipse.gef.layout.LayoutContext;
import org.eclipse.gef.layout.LayoutProperties;

import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.dsl.core.log.Log;

/**
 * A layering algorithm that places nodes in cLayers subject to a bound on the maximum number of
 * (original) nodes per layer. The bound is set via the
 * {@link LayeredOptions#LAYERING_COFFMAN_GRAHAM_LAYER_BOUND} layout option. The algorithm is
 * described in
 * <ul>
 * <li>Coffman, E. G., Jr. and Graham, R. L., "Optimal scheduling for two-processor systems",
 * <i>Acta Informatica</i> (1972).</li>
 * </ul>
 * <dl>
 * <dt>Precondition:</dt>
 * <dd>the graph has no cycles.</dd>
 * <dt>Postcondition:</dt>
 * <dd>all nodes have been assigned a layer such that edges connect only nodes from cLayers with
 * increasing indices and the specified layer bound is met.</dd>
 * </dl>
 */
public class CoffmanGrahamLayoutAlgorithm implements ILayoutAlgorithm {

	private static final Lock lock = new ReentrantLock(true);

	public enum Flow {
		TOP_BOTTOM,
		LEFT_RIGHT;
	}

	private Flow style = Flow.LEFT_RIGHT;

	private Point anchor;		// holds anchor co-ordinates

	public CoffmanGrahamLayoutAlgorithm(Flow style) {
		super();
		this.style = style;
		Log.setLevel(this, Log.LogLevel.Debug);
	}

	@Override
	public void applyLayout(LayoutContext context, boolean clean) {
		if (!clean) return;

		lock.lock();
		try {

			// 1) build directed acyclic graph (Coffman-Graham)

			CGraph graph = new CGraph(style, context.getNodes());
			if (graph.root == null) {
				Log.error(this, "No root; has to be a directed graph at least at the root level.");
				return;
			}

			// 2 set root node location

			Rectangle bounds = LayoutProperties.getBounds(context.getGraph());
			double midpt = 0;
			for (CLayer layer : graph.layers) {
				midpt = Math.max(midpt, layer.getSpread() / 2);
			}
			double depth = graph.root.virtual ? -graph.sDepth : 0;
			switch (style) {
				case LEFT_RIGHT:
					midpt = Math.max(midpt, bounds.getHeight());
					anchor = new Point(depth, midpt);
					break;
				case TOP_BOTTOM:
					midpt = Math.max(midpt, bounds.getWidth());
					anchor = new Point(midpt, depth);
					break;
			}

			// 3 placement of nodes

			Point pos = anchor.getCopy();
			for (int idx = graph.layers.size() - 1; idx >= 0; idx--) {
				CLayer layer = graph.layers.get(idx);
				pos = layer.setDepth(pos);
			}

			// 4 update graph layout positions

			for (Entry<Node, CNode> entry : graph.lookup.entrySet()) {
				Node node = entry.getKey();
				Point loc = entry.getValue().loc;
				String text = ((NodeModel) node).getDisplayText();
				Log.debug(this, String.format("Loc @%s:%s '%s'", (int) loc.x, (int) loc.y, text));
				LayoutProperties.setLocation(node, loc);
			}

		} catch (Exception e) {
			Log.error(this, "Layout failed: " + e.getMessage(), e);
		} finally {
			lock.unlock();
		}
	}
}
