package net.certiv.antlrdt.graph.behaviors;

import java.time.Instant;
import java.util.List;
import java.util.Map;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.ListChangeListener;
import javafx.geometry.Bounds;
import javafx.scene.Node;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.layout.ILayoutFilter;
import org.eclipse.gef.layout.LayoutContext;
import org.eclipse.gef.layout.LayoutProperties;
import org.eclipse.gef.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;

import net.certiv.antlrdt.graph.actions.Layout;
import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.models.EdgeModel;
import net.certiv.antlrdt.graph.models.IModel;
import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.antlrdt.graph.parts.TreeEdgePart;
import net.certiv.antlrdt.graph.parts.TreeGraphPart;
import net.certiv.antlrdt.graph.util.Mark;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Time;

/** The {@link GraphLayoutBehavior} is responsible for initiating layout passes. */
public class GraphLayoutBehavior extends AbstractLayoutBehavior {

	private static final int SPRING_ITRS = 50;
	private static final int SPRING_SECS = 10;

	private ILayoutFilter layoutFilter;
	private Runnable postLayout = () -> postLayout();
	private Runnable preLayout = () -> preLayout();

	private ChangeListener<? super Bounds> viewportBoundsChangeListener = (observable, oldBounds, newBounds) -> {
		updateBounds();
	};

	private ListChangeListener<IVisualPart<? extends Node>> childrenObserver = c -> {
		if (c.next()) {
			List<? extends IVisualPart<? extends Node>> change = c.getAddedSubList();
			if (change.size() > 0) {
				if (change.get(0) instanceof TreeEdgePart) return;
				getHost().getContent().setLayoutBehavior(this);
			}
		}
	};

	protected GraphLayoutBehavior() {
		super();
	}

	@Override
	public TreeGraphPart getHost() {
		return (TreeGraphPart) super.getHost();
	}

	/** Performs a layout pass using the layout algorithm that is configured in the model. */
	public synchronized void applyLayout() {
		if (CoreUtil.getActiveWorkbench().isClosing()) return;

		DiagramModel model = getHost().getContent();
		Graph graph = model.getGraph();

		// skip if graph layout is disabled
		Boolean en = (Boolean) graph.getAttributes().get(IModel.LAYOUT_ENABLED);
		if (en == null || !en) return;

		LayoutProperties.setBounds(graph, computeLayoutBounds());

		Layout layout = model.getLayout();
		LayoutContext context = getLayoutContext();
		context.setGraph(graph);
		context.setLayoutAlgorithm(layout.getAlgorithm());

		Time.start(Mark.MAIN);
		context.applyLayout(true);
		Log.info(this, Time.elapsed(Mark.MAIN, "Graph layout required %s ms"));

		if (layout == Layout.SPRING) {
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					new Animator((SpringLayoutAlgorithm) context.getLayoutAlgorithm()).start();
				}
			});
		}
	}

	protected class Animator extends AnimationTimer {
		private final Instant done = Instant.now().plusSeconds(SPRING_SECS);
		private final SpringLayoutAlgorithm algo;

		public Animator(SpringLayoutAlgorithm algo) {
			super();
			this.algo = algo;
		}

		@Override
		public void handle(long now) {
			algo.performNIteration(SPRING_ITRS);
			if (Instant.now().compareTo(done) > 0) stop();
		}
	};

	/** Updates the bounds property from the visual */
	protected void updateBounds() {
		if (getHost().getVisual().getScene().getWindow() == null) return;

		LayoutContext context = getLayoutContext();
		Graph graph = context.getGraph();
		if (graph == null) return;

		Rectangle pres = computeLayoutBounds();
		Rectangle prev = LayoutProperties.getBounds(graph);
		if (prev != pres && (prev == null || !prev.equals(pres))) {
			LayoutProperties.setBounds(graph, pres);
			applyLayout();
		}
	}

	/** Determines the layout bounds for the graph. */
	protected Rectangle computeLayoutBounds() {
		InfiniteCanvas canvas = getInfiniteCanvas();
		// Use minimum of window size and canvas size, because the
		// canvas size is invalid when its scene is changed.
		double windowX = canvas.getScene().getWindow().getWidth();
		double windowY = canvas.getScene().getWindow().getHeight();

		double canvasX = Double.isFinite(canvas.getWidth()) ? canvas.getWidth() : 0;
		double canvasY = Double.isFinite(canvas.getHeight()) ? canvas.getHeight() : 0;

		windowX = Math.max(canvasX, windowX);
		windowY = Math.max(canvasY, windowY);

		return new Rectangle(0, 0, windowX, windowY);
	}

	@Override
	protected void doActivate() {
		getHost().getChildrenUnmodifiable().addListener(childrenObserver);

		LayoutContext layoutContext = getLayoutContext();
		layoutContext.schedulePreLayoutPass(preLayout);
		layoutContext.schedulePostLayoutPass(postLayout);
		getInfiniteCanvas().scrollableBoundsProperty().addListener(viewportBoundsChangeListener);

		// add filter to exclude hidden model elements
		layoutFilter = new ILayoutFilter() {
			Map<Object, IContentPart<? extends Node>> contentPartMap = getHost().getViewer().getContentPartMap();

			@Override
			public boolean isLayoutIrrelevant(Edge edge) {
				if (!contentPartMap.containsKey(edge)) return true;
				if (!contentPartMap.get(edge).isActive()) return true;

				EdgeModel model = (EdgeModel) edge;
				return model.isHidden() || model.getSource().isHidden() || model.getTarget().isHidden();
			}

			@Override
			public boolean isLayoutIrrelevant(org.eclipse.gef.graph.Node node) {
				if (!contentPartMap.containsKey(node)) return true;
				if (!contentPartMap.get(node).isActive()) return true;

				NodeModel model = (NodeModel) node;
				return model.isHidden();
			}
		};

		getLayoutContext().addLayoutFilter(layoutFilter);
	}

	@Override
	protected void doDeactivate() {
		getHost().getChildrenUnmodifiable().removeListener(childrenObserver);

		LayoutContext layoutContext = getLayoutContext();
		layoutContext.unschedulePreLayoutPass(preLayout);
		layoutContext.unschedulePostLayoutPass(postLayout);
		getLayoutContext().removeLayoutFilter(layoutFilter);
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
