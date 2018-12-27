package net.certiv.antlrdt.graph.view.tree;

import java.util.Collections;

import javafx.application.Platform;
import javafx.geometry.Bounds;
import javafx.scene.Parent;
import javafx.scene.Scene;

import org.eclipse.gef.fx.nodes.InfiniteCanvas;
import org.eclipse.gef.mvc.fx.ui.actions.FitToViewportActionGroup;
import org.eclipse.gef.mvc.fx.ui.actions.ScrollActionGroup;
import org.eclipse.gef.mvc.fx.ui.actions.ZoomActionGroup;
import org.eclipse.gef.mvc.fx.ui.parts.AbstractFXView;
import org.eclipse.gef.mvc.fx.viewer.IViewer;
import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.IShowInTarget;
import org.eclipse.ui.part.ShowInContext;

import com.google.inject.Guice;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.graph.GraphUI;
import net.certiv.antlrdt.graph.actions.GraphActionGroup;
import net.certiv.antlrdt.graph.actions.Layout;
import net.certiv.antlrdt.graph.actions.Router;
import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.parse.TargetBuilder;
import net.certiv.dsl.core.preferences.DslPrefsManager;

public class TreeView extends AbstractFXView implements IShowInTarget {

	public static final String VIEW_ID = "net.certiv.antlrdt.graph.view.tree.TreeView";

	public static final int PAD = 10;

	public static final String GRAPH_LAYOUT = "graph_layout";
	public static final String NODE_ROUTER = "node_router";

	public static final String StylesCss = TreeView.class.getResource("styles.css").toExternalForm(); //$NON-NLS-1$

	private static final String[] EQ = { PrefsKey.PT_HORZ_SPACING, PrefsKey.PT_VERT_SPACING, PrefsKey.PT_TOOLTIP_TYPE };
	private static final String[] SW = { PrefsKey.PT_COLOR_PREFIX };

	private final PrefsListener prefs = new PrefsListener();
	private DslPrefsManager prefsMgr;

	private GraphActionGroup graphActionGroup;
	private ZoomActionGroup zoomActionGroup;
	private FitToViewportActionGroup fitActionGroup;
	private ScrollActionGroup scrollActionGroup;

	private TargetBuilder builder;
	private DiagramModel diagModel;

	public TreeView() {
		super(Guice.createInjector(new TreeViewModule()));
		prefsMgr = AntlrDTCore.getDefault().getPrefsManager();
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		prefsMgr.addPropertyChangeListener(prefs);

		IDialogSettings ds = GraphUI.getDefault().getDialogSettings();
		String displayName = ds.get(GRAPH_LAYOUT);
		if (displayName == null || displayName.isEmpty() || Layout.getEnum(displayName) == null) {
			ds.put(GRAPH_LAYOUT, Layout.SPRING.getDisplayName());
		}
		displayName = ds.get(NODE_ROUTER);
		if (displayName == null || displayName.isEmpty() || Router.getEnum(displayName) == null) {
			ds.put(NODE_ROUTER, Router.FAN.getDisplayName());
		}
	}

	@Override
	protected void hookViewers() {
		IViewer viewer = getContentViewer();
		Parent parent = viewer.getCanvas();
		getCanvas().setScene(new Scene(parent));
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);

		IActionBars actionBars = getViewSite().getActionBars();
		IToolBarManager mgr = actionBars.getToolBarManager();

		// actions

		mgr.add(new Separator());
		graphActionGroup = new GraphActionGroup();
		getContentViewer().setAdapter(graphActionGroup);
		graphActionGroup.fillActionBars(actionBars);

		mgr.add(new Separator());
		zoomActionGroup = new ZoomActionGroup();
		getContentViewer().setAdapter(zoomActionGroup);
		zoomActionGroup.fillActionBars(actionBars);

		mgr.add(new Separator());
		fitActionGroup = new FitToViewportActionGroup();
		getContentViewer().setAdapter(fitActionGroup);
		fitActionGroup.fillActionBars(actionBars);

		mgr.add(new Separator());
		scrollActionGroup = new ScrollActionGroup();
		getContentViewer().setAdapter(scrollActionGroup);
		scrollActionGroup.fillActionBars(actionBars);

		// scene styles
		Scene scene = getContentViewer().getCanvas().getScene();
		scene.getStylesheets().add(StylesCss);
	}

	/** Refreshes the constructed graph */
	public void refresh() {
		getContentViewer().reveal(getContentViewer().getRootPart());
	}

	/** Refreshes the constructed graph using the graph model from the given builder. */
	public void refresh(TargetBuilder builder) {
		this.builder = builder;
		refresh(true);
	}

	/**
	 * Refreshes the constructed graph. If {@code rebuild} is set, the graph model fetched from the
	 * existing parse-tree model.
	 */
	public void refresh(boolean rebuild) {
		if (rebuild) {
			diagModel = null;
			if (builder != null && builder.isValid()) {
				diagModel = builder.getModel();

				IDialogSettings ds = GraphUI.getDefault().getDialogSettings();
				diagModel.setLayout(Layout.getEnum(ds.get(GRAPH_LAYOUT)));
				diagModel.setRouter(Router.getEnum(ds.get(NODE_ROUTER)));
			}
		}
		setGraph(diagModel);
	}

	public void setGraph(DiagramModel model) {
		IViewer viewer = getContentViewer();
		if (viewer == null) {
			throw new IllegalStateException("Invalid configuration: Content viewer could not be retrieved.");
		}

		viewer.getContents().clear();
		viewer.getContents().setAll(Collections.singleton(model));

		// adjust viewport to scroll to top-left
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				InfiniteCanvas canvas = ((InfiniteCanvasViewer) getContentViewer()).getCanvas();
				Bounds b = canvas.getContentBounds();
				canvas.setHorizontalScrollOffset(canvas.getHorizontalScrollOffset() - b.getMinX() + PAD);
				canvas.setVerticalScrollOffset(canvas.getVerticalScrollOffset() - b.getMinY() + PAD);
			}
		});
	}

	public void setLayoutAlgorithm(Layout layout) {
		IDialogSettings ds = GraphUI.getDefault().getDialogSettings();
		ds.put(GRAPH_LAYOUT, layout.getDisplayName());
	}

	public void setRouterAlgorithm(Router router) {
		IDialogSettings ds = GraphUI.getDefault().getDialogSettings();
		ds.put(NODE_ROUTER, router.getDisplayName());
	}

	@Override
	public boolean show(ShowInContext context) {
		return false;
	}

	@Override
	public void dispose() {
		prefsMgr.removePropertyChangeListener(prefs);

		if (fitActionGroup != null) {
			getContentViewer().unsetAdapter(fitActionGroup);
			fitActionGroup.dispose();
			fitActionGroup = null;
		}
		if (zoomActionGroup != null) {
			getContentViewer().unsetAdapter(zoomActionGroup);
			zoomActionGroup.dispose();
			zoomActionGroup = null;
		}
		if (scrollActionGroup != null) {
			getContentViewer().unsetAdapter(scrollActionGroup);
			scrollActionGroup.dispose();
			scrollActionGroup = null;
		}

		getContentViewer().contentsProperty().clear();
		super.dispose();
	}

	private class PrefsListener implements IPropertyChangeListener {

		public PrefsListener() {}

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			String prop = event.getProperty();
			for (String value : EQ) {
				if (value.equals(prop)) {
					refresh(false);
					break;
				}
			}
			for (String value : SW) {
				if (value.startsWith(prop)) {
					refresh(false);
					break;
				}
			}
		}
	}
}
