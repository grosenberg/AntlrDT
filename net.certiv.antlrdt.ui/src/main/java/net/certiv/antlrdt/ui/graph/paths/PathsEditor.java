/*******************************************************************************
 * Copyright 2005-2017, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.paths;

import java.util.List;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.parser.PathsData;
import net.certiv.antlrdt.core.parser.PathsNode;
import net.certiv.antlrdt.core.parser.gen.PathVisitor;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.graph.DslGraphViewer;
import net.certiv.antlrdt.ui.graph.EnhTipHelper;
import net.certiv.antlrdt.ui.graph.IZoomableEditor;
import net.certiv.antlrdt.ui.graph.ViewForm;
import net.certiv.antlrdt.ui.graph.actions.Layout;
import net.certiv.antlrdt.ui.graph.actions.LayoutToolbarGroup;
import net.certiv.antlrdt.ui.graph.actions.PathsAddSubAction;
import net.certiv.antlrdt.ui.graph.actions.PathsAddSupAction;
import net.certiv.antlrdt.ui.graph.actions.PathsRemoveAction;
import net.certiv.antlrdt.ui.graph.actions.Router;
import net.certiv.antlrdt.ui.graph.actions.RouterToolbarGroup;
import net.certiv.antlrdt.ui.graph.actions.ScreenshotAction;
import net.certiv.antlrdt.ui.graph.cst.providers.INodeLabelProvider;
import net.certiv.antlrdt.ui.graph.figures.ZoomContributionViewItemFix;
import net.certiv.antlrdt.ui.graph.paths.model.PathsModel;
import net.certiv.antlrdt.ui.graph.paths.providers.NodeContentProvider;
import net.certiv.antlrdt.ui.graph.paths.providers.NodeLabelProvider;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.IModuleStmt;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.core.model.Statement;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.preferences.DslPrefsManager;
import net.certiv.dsl.core.util.Log;

public class PathsEditor extends EditorPart implements IZoomableEditor, ISelectionListener {

	public static final String ID = "net.certiv.antlrdt.ui.graph.paths.PathsEditor";

	public static final String GROUP_ROUTER_TOOLBAR = "groupRouterToolbar";
	public static final String GROUP_LAYOUT_TOOLBAR = "groupLayoutToolbar";

	private static final String KEY_LAYOUT = "keyLayout";
	private static final String KEY_ROUTER = "keyRouter";

	public static final String ADD_SUP_PATHS = "addSupPaths";
	public static final String ADD_SUB_PATHS = "addSubPaths";
	public static final String REMOVE_PATHS = "removePaths";

	private AntlrDTUI ui;
	private AntlrDTCore core;

	private DslGraphViewer viewer;
	private PathHelper helper;
	private PreferenceListener prefListener;
	private NodeContentProvider contentProvider;
	private ViewForm viewForm;
	private INodeLabelProvider nodeLabelProvider;
	private FormToolkit toolKit;
	private ScrolledForm form;

	private PathsNode[] nodes = new PathsNode[] {};
	private PathsNode selectedNode;
	private List<PathsNode> selectedNodes;
	private PathsNode focusedNode;

	private LayoutToolbarGroup layoutToolbarGroup;
	private RouterToolbarGroup routerToolbarGroup;
	private Router router;

	private Action screenshotAction;
	private Action addSupPathsAction;
	private Action addSubPathsAction;
	private Action removePathsAction;

	private SurfaceDragAdapter dragAdapter;
	private boolean surfaceDragging;
	public Cursor cursorStore;
	private Point lastLocation;

	private IDialogSettings dialogSettings;

	public PathsEditor() {
		super();

		ui = AntlrDTUI.getDefault();
		core = AntlrDTCore.getDefault();
		helper = new PathHelper(this);
		prefListener = new PreferenceListener();
		ui.setPathsEditor(this);
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);

		IDialogSettings settings = ui.getDialogSettings();
		dialogSettings = settings.getSection(AntlrDTUI.PA_DIALOG_SEC);
		if (dialogSettings == null) {
			dialogSettings = settings.addNewSection(AntlrDTUI.PA_DIALOG_SEC);
		}
	}

	public DslPrefsManager getPrefs() {
		return core.getPrefsManager();
	}

	/** Create the viewer and initialize it. */
	@Override
	public void createPartControl(Composite parent) {
		toolKit = new FormToolkit(parent.getDisplay());
		viewForm = new ViewForm(parent, toolKit);
		viewer = (DslGraphViewer) viewForm.getGraphViewer();
		form = viewForm.getForm();

		contentProvider = new NodeContentProvider(helper);
		nodeLabelProvider = new NodeLabelProvider(viewer);

		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(nodeLabelProvider);
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);

		//////////////////////////////

		String layoutName = dialogSettings.get(KEY_LAYOUT);
		Layout layout = Layout.getEnum(layoutName);
		if (layout == null) layout = Layout.HTREE;
		if (layout == Layout.SPRING) {
			int limit = getPrefs().getInt(PrefsKey.PT_ANIMATION_LIMIT);
			((SpringLayoutAlgorithm) layout.getAlgorithm()).setIterations(limit);
		}

		dialogSettings.put(KEY_LAYOUT, layout.getName());
		viewer.setLayoutAlgorithm(layout.getAlgorithm(), true);

		String routerName = dialogSettings.get(KEY_ROUTER);
		router = Router.getEnum(routerName);
		if (router == null) router = Router.BRANCHED;
		dialogSettings.put(KEY_ROUTER, router.getName());

		//////////////////////////////

		viewer.addPostSelectionChangedListener(new SelectionChangedListener());
		viewer.addDoubleClickListener(new DoubleClickListener());

		getPrefs().addPropertyChangeListener(prefListener);
		AntlrDTUI.getDefault().getWorkbench().addWorkbenchListener(new IWorkbenchListener() {

			@Override
			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				getEditorSite().getPage().closeEditor(PathsEditor.this, false);
				return true;
			}

			@Override
			public void postShutdown(IWorkbench workbench) {}
		});

		// calls #selectionChanged in response to outline (and other) selections
		getSelectionService().addSelectionListener(this);

		makeActions();
		setupContextMenu();
		contributeToActionBars();
		addSurfaceDragger();
	}

	private ISelectionService getSelectionService() {
		return getSite().getService(ISelectionService.class);
	}

	/** Retrieve the current GraphViewer */
	@Override
	public AbstractZoomableViewer getZoomableViewer() {
		return viewer;
	}

	@Override
	public void setFocus() {
		form.setFocus();
	}

	@Override
	public boolean isDirty() {
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		return false;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {}

	@Override
	public void doSaveAs() {}

	@Override
	public void dispose() {
		getPrefs().removePropertyChangeListener(prefListener);
		form.dispose();
		nodeLabelProvider.dispose();
		helper.dispose();
		ui.setPathsEditor(null);
		super.dispose();
	}

	/**
	 * React to selection in the outline view. This will generate a complete root to selected element
	 * path graph.
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		if (part instanceof ContentOutline) {
			if (sel instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) sel;
				for (Object elem : selection.toList()) {
					if (elem instanceof Statement && !(elem instanceof IModuleStmt)) {
						IStatement statement = (IStatement) elem;
						IResource res = statement.getResource();
						String name = statement.getElementName();
						setInput(res, name, Kind.FULL);
					}
				}
			}
		}
	}

	/**
	 * Trigger graph update with new input. The {@code kind} parameter specifies whether to (1) generate
	 * and install a complete root to selected element model; or (2) add or remove the sub path segments
	 * of the {@code ruleName} identified path node in the existing model.
	 */
	private void setInput(IResource res, String ruleName, Kind kind) {
		if (ruleName != null) {
			PathsModel model;
			switch (kind) {
				case ADD:
					model = helper.addSubPaths(ruleName);
					break;
				case REMOVE:
					model = helper.removeNode(ruleName);
					break;

				default: // FULL
					ICodeUnit unit = core.getModelManager().create((IFile) res);
					DslParseRecord record = unit.getParseRecord();
					PathsData data = buildPathsData(record);
					model = new PathsModel(this, data, ruleName);
			}

			helper.setModel(model);
			PathsNode selected = helper.getPathsNode(ruleName);
			nodeLabelProvider.setCurrentSelection(selected, selected);
			refresh();
		}
	}

	private PathsData buildPathsData(DslParseRecord record) {
		PathsData data = new PathsData();
		if (record != null && record.hasTree()) {
			try {
				PathVisitor walker = new PathVisitor(record.tree);
				walker.setHelper(data);
				walker.findAll();
			} catch (IllegalArgumentException e) {
				Log.error(this, "Paths: " + e.getMessage());
			}
		}
		return data;
	}

	public void refresh() {
		if (helper.getModel() != null) {
			viewer.setInput(helper.getModel());
			setConnectionRouter(router);
		}
	}

	@Override
	public void setConnectionRouter(Router router) {
		this.router = router;
		dialogSettings.put(KEY_ROUTER, router.getName());
		for (Object obj : viewer.getConnectionElements()) {
			GraphItem item = viewer.findGraphItem(obj);
			if (item instanceof GraphConnection) {
				GraphConnection conn = (GraphConnection) item;
				conn.getConnectionFigure().setConnectionRouter(router.getRouter());
			}
		}
		viewer.update(helper.getModel(), null);
	}

	@Override
	public void setLayoutAlgorithm(Layout layout) {
		dialogSettings.put(KEY_LAYOUT, layout.getName());
	}

	public PathsNode[] getInputElements() {
		return nodes;
	}

	public PathsNode getCurrentSelectedNode() {
		return selectedNode;
	}

	public List<PathsNode> getCurrentSelectedNodes() {
		return selectedNodes;
	}

	public void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Rule paths", message);
	}

	private void makeActions() {
		makeGroupActions();
		makeScreenshotActions();
		makePathsActions();
	}

	private void setupContextMenu() {
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

	protected void fillContextMenu(MenuManager manager, boolean onFigure) {
		if (onFigure) {
			manager.add(addSupPathsAction);
			manager.add(addSubPathsAction);
			manager.add(new Separator());
			manager.add(removePathsAction);
		} else {
			manager.add(new Separator(GROUP_LAYOUT_TOOLBAR));
			layoutToolbarGroup.fillContextMenu(manager);
			manager.add(new Separator(GROUP_ROUTER_TOOLBAR));
			routerToolbarGroup.fillContextMenu(manager);
		}
	}

	private void contributeToActionBars() {
		IActionBars bars = getEditorSite().getActionBars();
		makeToolBarContributions(bars.getToolBarManager());
	}

	private void makeToolBarContributions(IToolBarManager manager) {
		manager.add(new Separator());
		manager.add(new ZoomContributionViewItemFix(this));
		manager.add(screenshotAction);
	}

	private void makeScreenshotActions() {
		screenshotAction = new ScreenshotAction(this);
	}

	private void makeGroupActions() {
		layoutToolbarGroup = new LayoutToolbarGroup(this);
		routerToolbarGroup = new RouterToolbarGroup(this);
	}

	private void makePathsActions() {
		addSupPathsAction = new PathsAddSupAction(this, helper);
		addSubPathsAction = new PathsAddSubAction(this, helper);
		removePathsAction = new PathsRemoveAction(this, helper);
	}

	private void addSurfaceDragger() {
		dragAdapter = new SurfaceDragAdapter();
		viewer.getGraphControl().addMouseMoveListener(dragAdapter);
		viewer.getGraphControl().addMouseListener(dragAdapter);
	}

	protected void dragSurfacePrep(MouseEvent e) {
		if (getPrefs().getBoolean(PrefsKey.PT_SURFACE_DRAG_ENABLED)) {
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
	 * Graph#scrollTo(horz - hDelta, vert - vDelta) is the normal way to adjust the view port.
	 * Performance is quite bad where the number of nodes exceeds about 500. Each call results in a full
	 * traversal of the graph connections! Graph#scrollToX and Graph#scrollToY are optimized for use by
	 * the scroll-bars - very big performance gain with no observable downside.
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

	/* Listens for selections in the graph viewer */
	private class SelectionChangedListener implements ISelectionChangedListener {

		@Override
		public void selectionChanged(SelectionChangedEvent event) {
			IStructuredSelection selections = (IStructuredSelection) event.getSelection();
			Object selectedElement = selections.getFirstElement();
			if (selectedElement == null) return;
			if (selectedElement instanceof EntityConnectionData) return;
			handleSelectionChanged(selectedElement, selections);
		}
	}

	/*
	 * Handle selection changes in the viewer. This will update the view whenever a selection occurs.
	 * All selectable nodes in the graph should resolve to PathsNode.
	 */
	@SuppressWarnings("unchecked")
	private void handleSelectionChanged(Object selectedElement, IStructuredSelection selections) {
		if (selectedElement instanceof PathsNode) {
			selectedNode = (PathsNode) selectedElement;
			selectedNodes = selections.toList();
			nodeLabelProvider.setCurrentSelection(focusedNode, selectedNode);
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
				refresh();
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
					refresh();
			}
		}
	}
}
