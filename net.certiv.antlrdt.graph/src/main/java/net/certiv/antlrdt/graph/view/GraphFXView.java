package net.certiv.antlrdt.graph.view;

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

import com.google.inject.Injector;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.graph.GraphUI;
import net.certiv.antlrdt.graph.actions.Layout;
import net.certiv.antlrdt.graph.actions.LayoutActionGroup;
import net.certiv.antlrdt.graph.actions.Router;
import net.certiv.dsl.core.preferences.DslPrefsManager;

public abstract class GraphFXView extends AbstractFXView implements IShowInTarget {

	public static final int PAD = 10;

	// dialog settings keys
	public static final String NODE_LAYOUT = "node_layout";
	public static final String EDGE_ROUTER = "edge_router";

	public static final String[] EQ = { PrefsKey.PT_HORZ_SPACING, PrefsKey.PT_VERT_SPACING, PrefsKey.PT_TOOLTIP_TYPE };
	public static final String[] SW = { PrefsKey.PT_COLOR_PREFIX };

	private final PrefsListener prefs = new PrefsListener();
	protected DslPrefsManager prefMgr;

	private LayoutActionGroup layoutGroup;
	private ZoomActionGroup zoomGroup;
	private FitToViewportActionGroup fitGroup;
	private ScrollActionGroup scrollGroup;

	public GraphFXView(Injector injector) {
		super(injector);
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		prefMgr = AntlrDTCore.getDefault().getPrefsManager();
		prefMgr.addPropertyChangeListener(prefs);

		IDialogSettings ds = GraphUI.getSettings();
		String name = ds.get(NODE_LAYOUT);
		if (name == null || name.isEmpty() || Layout.getEnum(name) == null) {
			ds.put(NODE_LAYOUT, Layout.SPRING.getDisplayName());
		}
		name = ds.get(EDGE_ROUTER);
		if (name == null || name.isEmpty() || Router.getEnum(name) == null) {
			ds.put(EDGE_ROUTER, Router.FAN.getDisplayName());
		}
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);
		fillActionBars();
	}

	private void fillActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		IToolBarManager mgr = bars.getToolBarManager();

		mgr.add(new Separator());
		layoutGroup = new LayoutActionGroup(this);
		getContentViewer().setAdapter(layoutGroup);
		layoutGroup.fillActionBars(bars);

		mgr.add(new Separator());
		zoomGroup = new ZoomActionGroup();
		getContentViewer().setAdapter(zoomGroup);
		zoomGroup.fillActionBars(bars);

		mgr.add(new Separator());
		fitGroup = new FitToViewportActionGroup();
		getContentViewer().setAdapter(fitGroup);
		fitGroup.fillActionBars(bars);

		mgr.add(new Separator());
		scrollGroup = new ScrollActionGroup();
		getContentViewer().setAdapter(scrollGroup);
		scrollGroup.fillActionBars(bars);
	}

	@Override
	protected void hookViewers() {
		IViewer viewer = getContentViewer();
		Parent parent = viewer.getCanvas();
		getCanvas().setScene(new Scene(parent));
	}

	public DslPrefsManager getPrefMgr() {
		return prefMgr;
	}

	public abstract void refresh();

	/** Adjust viewport scroll to top-left. */
	protected void resetViewport() {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				InfiniteCanvasViewer viewer = (InfiniteCanvasViewer) getContentViewer();
				InfiniteCanvas canvas = viewer.getCanvas();
				Bounds b = canvas.getContentBounds();
				canvas.setHorizontalScrollOffset(canvas.getHorizontalScrollOffset() - b.getMinX() + PAD);
				canvas.setVerticalScrollOffset(canvas.getVerticalScrollOffset() - b.getMinY() + PAD);
			}
		});
	}

	public void setLayoutAlgorithm(Layout layout) {
		IDialogSettings ds = GraphUI.getSettings();
		ds.put(NODE_LAYOUT, layout.getDisplayName());
	}

	public void setRouterAlgorithm(Router router) {
		IDialogSettings ds = GraphUI.getSettings();
		ds.put(EDGE_ROUTER, router.getDisplayName());
	}

	@Override
	public boolean show(ShowInContext context) {
		return false;
	}

	@Override
	public void dispose() {
		prefMgr.removePropertyChangeListener(prefs);
		getContentViewer().contentsProperty().clear();
		if (fitGroup != null) {
			getContentViewer().unsetAdapter(fitGroup);
			fitGroup.dispose();
			fitGroup = null;
		}
		if (zoomGroup != null) {
			getContentViewer().unsetAdapter(zoomGroup);
			zoomGroup.dispose();
			zoomGroup = null;
		}
		if (scrollGroup != null) {
			getContentViewer().unsetAdapter(scrollGroup);
			scrollGroup.dispose();
			scrollGroup = null;
		}
		super.dispose();
	}

	private class PrefsListener implements IPropertyChangeListener {

		public PrefsListener() {}

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			String prop = event.getProperty();
			for (String value : EQ) {
				if (value.equals(prop)) {
					refresh();
					break;
				}
			}
			for (String value : SW) {
				if (value.startsWith(prop)) {
					refresh();
					break;
				}
			}
		}
	}
}
