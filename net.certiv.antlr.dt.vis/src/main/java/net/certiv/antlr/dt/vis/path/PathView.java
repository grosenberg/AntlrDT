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
package net.certiv.antlr.dt.vis.path;

import java.util.List;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TreeSelection;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.antlr.dt.vis.actions.Layout;
import net.certiv.antlr.dt.vis.actions.LayoutControlItem;
import net.certiv.antlr.dt.vis.actions.LinkControlItem;
import net.certiv.antlr.dt.vis.actions.PathsAddSubAction;
import net.certiv.antlr.dt.vis.actions.PathsAddSupAction;
import net.certiv.antlr.dt.vis.actions.PathsRemoveAction;
import net.certiv.antlr.dt.vis.actions.Router;
import net.certiv.antlr.dt.vis.actions.RouterControlItem;
import net.certiv.antlr.dt.vis.actions.ScreenshotControlItem;
import net.certiv.antlr.dt.vis.figures.EnhTipHelper;
import net.certiv.antlr.dt.vis.figures.ZoomControlItem;
import net.certiv.antlr.dt.vis.graph.EnhGraphViewer;
import net.certiv.antlr.dt.vis.graph.IAdjustableViewPart;
import net.certiv.antlr.dt.vis.graph.ViewForm;
import net.certiv.antlr.dt.vis.model.PathModel;
import net.certiv.antlr.dt.vis.model.PathNode;
import net.certiv.antlr.dt.vis.model.providers.PathContentProvider;
import net.certiv.antlr.dt.vis.model.providers.PathLabelProvider;
import net.certiv.common.log.Log;
import net.certiv.dsl.core.model.CodeUnit;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.IModuleStmt;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.core.model.ModelException;
import net.certiv.dsl.core.model.Statement;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.eclipse.PartAdaptor2;
import net.certiv.dsl.core.util.eclipse.WorkbenchAdaptor;
import net.certiv.dsl.ui.editor.DslEditor.DslSelection;

public class PathView extends ViewPart implements IAdjustableViewPart, ISelectionListener {

	public static final String ID = "net.certiv.antlr.dt.vis.views.paths.PathView";

	public final AntlrUI ui;
	public final AntlrCore core;
	public final PrefsManager store;

	private PreferenceListener prefListener;

	private FormToolkit toolKit;
	private ViewForm viewForm;
	private EnhGraphViewer viewer;
	private ScrolledForm form;

	private PathContentProvider contentProvider;
	private PathLabelProvider labelProvider;
	private PathModel pathModel;

	// private LayoutToolbarGroup layoutToolbarGroup;
	// private RouterToolbarGroup routerToolbarGroup;

	private PathsAddSupAction addSupPathsAction;
	private PathsAddSubAction addSubPathsAction;
	private PathsRemoveAction removePathsAction;

	private SurfaceDragAdapter dragAdapter;
	private boolean surfaceDragging;
	private Cursor cursorStore;
	private Point lastLocation;

	private PathNode selectedNode;
	private PathNode focusedNode;
	private List<PathNode> selectedNodes;

	public PathView() {
		super();

		ui = AntlrUI.getDefault();
		core = AntlrCore.getDefault();
		store = core.getPrefsManager();
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public void createPartControl(Composite parent) {
		toolKit = new FormToolkit(parent.getDisplay());
		viewForm = new ViewForm(parent, toolKit);
		viewer = viewForm.getGraphViewer();
		form = viewForm.getForm();

		contentProvider = new PathContentProvider(viewer);
		labelProvider = new PathLabelProvider(viewer);
		pathModel = new PathModel(contentProvider);

		contentProvider.initModel(pathModel);
		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);

		initSettings();
		makeActions();
		createContextMenu();
		createToolbar();
		addListeners(this);
	}

	@Override
	public void setFocus() {
		viewer.getGraphControl().setFocus();
	}

	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}

	@Override
	public void select(ICodeUnit unit, IStatement stmt) {
		update(PathOp.FULL_BUILD, (CodeUnit) unit, stmt);
	}

	public void update(PathOp op, DslSelection sel) {
		try {
			IStatement stmt = sel.resolveElement();
			update(op, sel.resolveUnit(), stmt);

		} catch (ModelException e) {
			Log.warn( "Path update failed: %s", e.getMessage());
		}
	}

	public void update(PathOp op, PathNode node) {
		IStatement stmt = node.getStatement();
		CodeUnit unit = (CodeUnit) stmt.getCodeUnit();
		update(op, unit, stmt);
	}

	public void update(PathOp op, CodeUnit unit, IStatement stmt) {
		switch (op) {
			case ADD_CALLERS:
				pathModel.addCallers(stmt);
				break;

			case ADD_CALLEES:
				pathModel.addCallees(stmt);
				break;

			case REMOVE:
				pathModel.remove(stmt);
				break;

			default:
			case CLEAR:
			case FULL_BUILD:
				pathModel.clear();
				pathModel.add(stmt);
				break;
		}
		viewer.setInput(pathModel);
		refresh();
	}

	private void refresh() {
		viewer.applyLayout();
		setRouter(getRouter());
	}

	@Override
	public Layout getLayout() {
		String name = store.getString(layoutKey());
		Layout layout = Layout.from(name);
		return layout != null ? layout : Layout.HTREE;
	}

	@Override
	public void setLayout(Layout layout) {
		store.setValue(layoutKey(), layout.getDisplayName());
		viewer.setLayoutAlgorithm(layout.getAlgorithm(), true);
	}

	@Override
	public Router getRouter() {
		String name = store.getString(routerKey());
		Router router = Router.from(name);
		return router != null ? router : Router.BRANCHED;
	}

	@Override
	public void setRouter(Router router) {
		store.setValue(routerKey(), router.getDisplayName());
		for (Object obj : viewer.getConnectionElements()) {
			GraphItem item = viewer.findGraphItem(obj);
			if (item instanceof GraphConnection) {
				GraphConnection conn = (GraphConnection) item;
				conn.getConnectionFigure().setConnectionRouter(router.getRouter());
			}
		}
		viewer.update(viewer.getConnectionElements(), null);
	}

	public PathNode getCurrentSelectedNode() {
		return selectedNode;
	}

	public List<PathNode> getCurrentSelectedNodes() {
		return selectedNodes;
	}

	public void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Rule paths", message);
	}

	private void initSettings() {
		Layout layout = getLayout();
		if (layout == Layout.SPRING) {
			int limit = store.getInt(PrefsKey.PT_ANIMATION_LIMIT);
			((SpringLayoutAlgorithm) layout.getAlgorithm()).setIterations(limit);
		}
		viewer.setLayoutAlgorithm(layout.getAlgorithm(), true);
	}

	private void makeActions() {
		addSupPathsAction = new PathsAddSupAction(this);
		addSubPathsAction = new PathsAddSubAction(this);
		removePathsAction = new PathsRemoveAction(this);

		// layoutToolbarGroup = new LayoutToolbarGroup(this);
		// routerToolbarGroup = new RouterToolbarGroup(this);
	}

	@Override
	public boolean isLinked() {
		return store.getBoolean(linkKey());
	}

	@Override
	public void setLinked(boolean state) {
		store.setValue(linkKey(), state);

		if (state) {
			IEditorPart part = CoreUtil.getActiveEditor();
			if (part instanceof AntlrEditor) {
				AntlrEditor editor = (AntlrEditor) part;
				ISelection sel = editor.getSelectionProvider().getSelection();
				if (sel instanceof DslSelection) {
					try {
						CodeUnit unit = ((DslSelection) sel).resolveUnit();
						IStatement stmt = ((DslSelection) sel).resolveElement();
						if (stmt != null) select(unit, stmt);
					} catch (ModelException e) {}
				}
			}
		}
	}

	private void createContextMenu() {
		final MenuManager menuMgr = new MenuManager("#PopupMenu");
		viewer.getControl().addMenuDetectListener(new MenuDetectListener() {

			@Override
			public void menuDetected(MenuDetectEvent e) {
				EnhTipHelper tipHelper = viewer.getEventDispatcher().getEnhTipHelper();
				if (tipHelper.isShowing()) tipHelper.hideTip();

				Point clickAt = new Point(viewer.getControl().toControl(e.x, e.y));
				IFigure fig = viewer.getGraphControl().getViewport().findFigureAt(clickAt.x, clickAt.y);
				if (fig instanceof PolylineConnection) {
					e.doit = false;
					return;
				}

				if (fig instanceof Viewport) fig = null;
				boolean onFigure = fig != null;

				menuMgr.removeAll();
				fillContextMenu(menuMgr, onFigure);
				Menu menu = menuMgr.createContextMenu(viewer.getControl());
				viewer.getControl().setMenu(menu);
			}
		});
	}

	protected void fillContextMenu(MenuManager mgr, boolean onFigure) {
		if (onFigure) {
			mgr.add(addSupPathsAction);
			mgr.add(addSubPathsAction);
			mgr.add(new Separator());
			mgr.add(removePathsAction);
			// } else {
			// manager.add(new Separator(GROUP_LAYOUT_TOOLBAR));
			// layoutToolbarGroup.fillContextMenu(manager);
			// manager.add(new Separator(GROUP_ROUTER_TOOLBAR));
			// routerToolbarGroup.fillContextMenu(manager);
		}
	}

	private void createToolbar() {
		form.getForm().setSeparatorVisible(true);
		IToolBarManager manager = form.getToolBarManager();

		manager.add(new LinkControlItem(this));
		manager.add(new Separator());
		manager.add(new ScreenshotControlItem(this));
		manager.add(new Separator());
		manager.add(new LayoutControlItem(this));
		manager.add(new Separator());
		manager.add(new RouterControlItem(this));
		manager.add(new Separator());
		manager.add(new ZoomControlItem(this));
		manager.update(true);
	}

	@Override
	public void dispose() {
		store.removePropertyChangeListener(prefListener);
		getSite().getService(ISelectionService.class).removeSelectionListener(this);
		super.dispose();
	}

	private void addListeners(final PathView view) {

		final PartAdaptor2 partAdaptor = new PartAdaptor2() {

			private boolean viewVisible = true;

			@Override
			public void partActivated(IWorkbenchPartReference partRef) {
				IWorkbenchPart part = partRef.getPart(false);
				if (part instanceof AntlrEditor && viewVisible) {
					viewer.refresh(true);
				}
			}

			@Override
			public void partVisible(IWorkbenchPartReference partRef) {
				IWorkbenchPart part = partRef.getPart(false);
				if (part == view) viewVisible = true;
			}

			@Override
			public void partHidden(IWorkbenchPartReference partRef) {
				IWorkbenchPart part = partRef.getPart(false);
				if (part == view) viewVisible = false;
			}
		};

		dragAdapter = new SurfaceDragAdapter();
		viewer.getGraphControl().addMouseMoveListener(dragAdapter);
		viewer.getGraphControl().addMouseListener(dragAdapter);

		viewer.addPostSelectionChangedListener(new SelectionChangedListener());
		viewer.addDoubleClickListener(new DoubleClickListener());

		prefListener = new PreferenceListener();
		store.addPropertyChangeListener(prefListener);

		// calls #selectionChanged in response to outline (and other) selections
		getSite().getService(ISelectionService.class).addSelectionListener(this);

		CoreUtil.getPartService().addPartListener(partAdaptor);
		CoreUtil.getActiveWorkbench().addWorkbenchListener(new WorkbenchAdaptor() {

			@Override
			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				if (view != null) {
					workbench.removeWorkbenchListener(this);
					CoreUtil.getPartService().removePartListener(partAdaptor);
				}
				return true;
			}
		});
	}

	protected void dragSurfacePrep(MouseEvent e) {
		if (store.getBoolean(PrefsKey.PT_SURFACE_DRAG_ENABLED)) {
			Point clickAt = new Point(e.x, e.y); // adjust for viewport scroll
			Point vLoc = viewer.getGraphControl().getViewport().getViewLocation();
			clickAt.translate(vLoc);
			Object fig = viewer.getGraphControl().getFigureAt(clickAt.x, clickAt.y);
			if (fig != null) return; 			// only allow drag if if on empty surface

			surfaceDragging = true;
			cursorStore = viewer.getGraphControl().getCursor();
			viewer.getGraphControl().setCursor(Cursors.HAND);
			lastLocation = new Point(e.x, e.y);
		} else {
			surfaceDragging = false;
		}
	}

	/*
	 * Graph#scrollTo(horz - hDelta, vert - vDelta) is the normal way to adjust the view
	 * port. Performance is quite bad where the number of nodes exceeds about 500. Each
	 * call results in a full traversal of the graph connections! Graph#scrollToX and
	 * Graph#scrollToY are optimized for use by the scroll-bars - very big performance
	 * gain with no observable downside.
	 */
	protected void dragSurface(MouseEvent e) {
		if (surfaceDragging && lastLocation != null) {
			int hDelta = e.x - lastLocation.x;
			int vDelta = e.y - lastLocation.y;
			lastLocation = new Point(e.x, e.y);

			Viewport port = viewer.getGraphControl().getViewport();
			int horz = port.getViewLocation().x; // avoid Point definition confusion
			int vert = port.getViewLocation().y;
			// port.setViewLocation(horz - hDelta, vert - vDelta);
			viewer.getGraphControl().scrollToX(horz - hDelta);
			viewer.getGraphControl().scrollToY(vert - vDelta);
		}
	}

	protected void dragSurfaceComplete(MouseEvent e) {
		if (surfaceDragging) {
			if (cursorStore != null) {
				viewer.getGraphControl().setCursor(cursorStore);
				cursorStore = null;
			} else {
				viewer.getGraphControl().setCursor(Cursors.ARROW);
			}
		}
		surfaceDragging = false;
		lastLocation = null;
	}

	/* Listens for selections in the Antlr editor and outline view. */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		if (!isLinked()) return;

		if (part instanceof AntlrEditor && sel instanceof DslSelection) {
			try {
				IStatement stmt = ((DslSelection) sel).resolveElement();
				if (stmt != null) {
					Log.debug( "Editor selection: %s", stmt.getElementName());
					CodeUnit unit = (CodeUnit) stmt.getCodeUnit();
					update(PathOp.FULL_BUILD, unit, stmt);
				}
			} catch (ModelException e) {}

		} else if (part instanceof ContentOutline) {
			if (sel instanceof TreeSelection) {
				TreeSelection selection = (TreeSelection) sel;
				for (Object elem : selection.toList()) {
					if (elem instanceof Statement && !(elem instanceof IModuleStmt)) {
						Log.debug( "Outline selection: %s", ((Statement) elem).getElementName());
						Statement stmt = (Statement) elem;
						CodeUnit unit = (CodeUnit) stmt.getCodeUnit();
						update(PathOp.FULL_BUILD, unit, stmt);
						break;
					}
				}
			}
		}
	}

	/* Listens for selections in the graph viewer */
	private class SelectionChangedListener implements ISelectionChangedListener {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			Object elem = selection.getFirstElement();
			if (elem == null) return;
			if (elem instanceof EntityConnectionData) return;
			handleSelectionChanged(selection, elem);
		}
	}

	/*
	 * Handle selection changes in the viewer. This will update the view whenever a
	 * selection occurs. All selectable nodes in the graph should resolve to PathsNode.
	 */
	@SuppressWarnings("unchecked")
	private void handleSelectionChanged(IStructuredSelection selections, Object selected) {
		if (selected instanceof PathNode) {
			selectedNode = (PathNode) selected;
			selectedNodes = selections.toList();
			labelProvider.setCurrentSelection(focusedNode, selectedNode);
			if (focusedNode != null) {
				viewer.update(contentProvider.getElements(focusedNode), null);
			}
		} else {
			selectedNode = null;
			selectedNodes = null;
		}
	}

	private class DoubleClickListener implements IDoubleClickListener {

		@Override
		public void doubleClick(DoubleClickEvent event) {
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			if (selection.size() < 1) { // on the canvas
				// refresh();
			} else {
				// Object selectedElement = selection.getFirstElement();
				// if (selectedElement instanceof IMember) {
				// openAction.run();
				// }
			}
		}
	}

	private class SurfaceDragAdapter implements MouseListener, MouseMoveListener {

		@Override
		public void mouseDown(MouseEvent e) {
			dragSurfacePrep(e);
		}

		@Override
		public void mouseMove(MouseEvent e) {
			dragSurface(e);
		}

		@Override
		public void mouseUp(MouseEvent e) {
			dragSurfaceComplete(e);
		}

		@Override
		public void mouseDoubleClick(MouseEvent e) {}
	}

	private class PreferenceListener implements IPropertyChangeListener {

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			switch (event.getProperty()) {
				case PrefsKey.PT_HORZ_SPACING:
				case PrefsKey.PT_VERT_SPACING:
				case PrefsKey.PT_TOOLTIP_TYPE:
				case PrefsKey.PT_COLOR_PREFIX:
					viewer.refresh(true);
			}
		}
	}
}
