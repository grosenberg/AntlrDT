/*******************************************************************************
 * Copyright (c) 2014, 2017 itemis AG and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Matthias Wienand (itemis AG) - initial API & implementation
 *******************************************************************************/
package net.certiv.antlrdt.graph.behaviors;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.layout.ILayoutAlgorithm;
import org.eclipse.gef.layout.LayoutContext;
import org.eclipse.gef.layout.LayoutProperties;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;

import net.certiv.antlrdt.graph.parts.TreeModelPart;

/**
 * The {@link GraphLayoutBehavior} is responsible for initiating layout passes. Only applicable to
 * {@link GraphPart}.
 */
public class GraphLayoutBehavior extends AbstractLayoutBehavior {

	private Runnable postLayout = new Runnable() {

		@Override
		public void run() {
			postLayout();
		}
	};

	private Runnable preLayout = new Runnable() {

		@Override
		public void run() {
			preLayout();
		}
	};

	private ChangeListener<? super Bounds> viewportBoundsChangeListener = new ChangeListener<Bounds>() {

		@Override
		public void changed(ObservableValue<? extends Bounds> observable, Bounds oldBounds, Bounds newBounds) {
			updateBounds();
		}
	};

	private ListChangeListener<IVisualPart<? extends Node>> childrenObserver = new ListChangeListener<IVisualPart<? extends Node>>() {

		@Override
		public void onChanged(ListChangeListener.Change<? extends IVisualPart<? extends Node>> c) {
			applyLayout();
		}
	};

	@Override
	public TreeModelPart getHost() {
		return (TreeModelPart) super.getHost();
	}

	/** Performs a layout pass using the layout algorithm that is configured in the model. */
	public void applyLayout() {
		LayoutContext context = getLayoutContext();
		IViewer viewer = getHost().getRoot().getViewer();
		TreeModelPart part = viewer.getAdapter(TreeModelPart.class);
		ILayoutAlgorithm algorithm = part.getContent().getLayout().getAlgorithm();
		if (algorithm != null) {
			if (context.getLayoutAlgorithm() != algorithm) {
				context.setLayoutAlgorithm(algorithm);
			}

		} else {
			if (context.getLayoutAlgorithm() != null) {
				context.setLayoutAlgorithm(null);
			}
		}

		context.applyLayout(true);
	}

	/** Updates the bounds property from the visual */
	protected void updateBounds() {
		if (getHost().getVisual().getScene().getWindow() == null) return;

		LayoutContext context = getLayoutContext();
		Graph graph = context.getGraph();
		if (graph == null) return;

		Rectangle newBounds = computeLayoutBounds();
		Rectangle oldBounds = LayoutProperties.getBounds(graph);
		if (oldBounds != newBounds && (oldBounds == null || !oldBounds.equals(newBounds))) {
			LayoutProperties.setBounds(graph, newBounds);
			applyLayout();
		}
	}

	/** Determines the layout bounds for the graph. */
	protected Rectangle computeLayoutBounds() {
		Rectangle newBounds = new Rectangle();
		InfiniteCanvas canvas = getInfiniteCanvas();
		// Use minimum of window size and canvas size, because the
		// canvas size is invalid when its scene is changed.
		double windowWidth = canvas.getScene().getWindow().getWidth();
		double windowHeight = canvas.getScene().getWindow().getHeight();
		newBounds = new Rectangle(0, 0,
				Double.isFinite(windowWidth) ? Math.min(canvas.getWidth(), windowWidth) : canvas.getWidth(),
				Double.isFinite(windowHeight) ? Math.min(canvas.getHeight(), windowHeight) : canvas.getHeight());
		return newBounds;
	}

	@Override
	protected void doActivate() {
		getHost().getChildrenUnmodifiable().addListener(childrenObserver);

		LayoutContext layoutContext = getLayoutContext();
		layoutContext.schedulePreLayoutPass(preLayout);
		layoutContext.schedulePostLayoutPass(postLayout);
		getInfiniteCanvas().scrollableBoundsProperty().addListener(viewportBoundsChangeListener);
	}

	@Override
	protected void doDeactivate() {
		getHost().getChildrenUnmodifiable().removeListener(childrenObserver);

		LayoutContext layoutContext = getLayoutContext();
		layoutContext.unschedulePreLayoutPass(preLayout);
		layoutContext.unschedulePostLayoutPass(postLayout);
		getInfiniteCanvas().scrollableBoundsProperty().removeListener(viewportBoundsChangeListener);
	}

	@Override
	protected void preLayout() {
		for (IVisualPart<? extends Node> child : getHost().getChildrenUnmodifiable()) {
			if (child.getViewer() == null) continue;

			AbstractLayoutBehavior behavior = child.getAdapter(AbstractLayoutBehavior.class);
			if (behavior != null) behavior.preLayout();
		}
	}

	@Override
	protected void postLayout() {
		for (IVisualPart<? extends Node> child : getHost().getChildrenUnmodifiable()) {
			if (child.getViewer() == null) continue;

			AbstractLayoutBehavior behavior = child.getAdapter(AbstractLayoutBehavior.class);
			if (behavior != null) behavior.postLayout();
		}
	}

	@Override
	protected LayoutContext getLayoutContext() {
		return getHost().getAdapter(LayoutContext.class);
	}

	/** Returns the {@link InfiniteCanvas} of the {@link IViewer} of the {@link #getHost() host}. */
	protected InfiniteCanvas getInfiniteCanvas() {
		return ((InfiniteCanvasViewer) getHost().getRoot().getViewer()).getCanvas();
	}
}
