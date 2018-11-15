/*******************************************************************************
 * Copyright 2005-2010, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.cst;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;
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
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchListener;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.part.EditorPart;
import org.eclipse.zest.core.viewers.AbstractZoomableViewer;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.widgets.GraphConnection;
import org.eclipse.zest.core.widgets.GraphItem;
import org.eclipse.zest.core.widgets.ZestStyles;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.AntlrDTUtil;
import net.certiv.antlrdt.ui.graph.DslGraphViewer;
import net.certiv.antlrdt.ui.graph.IZoomableEditor;
import net.certiv.antlrdt.ui.graph.ViewForm;
import net.certiv.antlrdt.ui.graph.actions.Layout;
import net.certiv.antlrdt.ui.graph.actions.LayoutToolbarGroup;
import net.certiv.antlrdt.ui.graph.actions.Router;
import net.certiv.antlrdt.ui.graph.actions.RouterToolbarGroup;
import net.certiv.antlrdt.ui.graph.actions.ScreenshotAction;
import net.certiv.antlrdt.ui.graph.cst.providers.INodeLabelProvider;
import net.certiv.antlrdt.ui.graph.cst.providers.NodeContentProvider;
import net.certiv.antlrdt.ui.graph.cst.providers.NodeLabelProvider;
import net.certiv.antlrdt.ui.graph.figures.ZoomContributionViewItemFix;
import net.certiv.antlrdt.ui.view.tokens.Source;
import net.certiv.antlrdt.ui.view.tokens.TargetBuilder;
import net.certiv.dsl.core.preferences.DslPrefsManager;

public class CstEditor extends EditorPart implements IZoomableEditor {

	public static final String ID = "net.certiv.antlrdt.ui.graph.cst.CstEditor";

	public static final String GROUP_ROUTER_TOOLBAR = "groupRouterToolbar";
	public static final String GROUP_LAYOUT_TOOLBAR = "groupLayoutToolbar";

	private static final String KEY_LAYOUT = "keyLayout";
	private static final String KEY_ROUTER = "keyRouter";

	private DslGraphViewer viewer;
	private CstHelper helper;
	private PreferenceListener prefListener;
	private NodeContentProvider contentProvider;
	private ViewForm viewForm;
	private INodeLabelProvider nodeLabelProvider;
	private FormToolkit toolKit = null;
	private ScrolledForm form = null;

	private ParseTree[] nodes = new ParseTree[] {};
	private ParseTree selectedNode = null;
	private List<ParseTree> selectedNodes;
	private ParseTree focusedNode = null;

	private LayoutToolbarGroup layoutToolbarGroup;
	private RouterToolbarGroup routerToolbarGroup;
	private Router router;

	private Action screenshotAction;

	private SurfaceDragAdapter dragAdapter;
	private boolean surfaceDragging;
	public Cursor cursorStore;
	private Point lastLocation;

	private IDialogSettings dialogSettings;

	private Source source;

	/** The constructor. */
	public CstEditor() {
		super();
		AntlrDTUI.getDefault().setCstEditor(this);
		helper = new CstHelper(this);
		prefListener = new PreferenceListener();
	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);

		IDialogSettings settings = AntlrDTUI.getDefault().getDialogSettings();
		dialogSettings = settings.getSection(AntlrDTUI.PT_DIALOG_SEC);
		if (dialogSettings == null) {
			dialogSettings = settings.addNewSection(AntlrDTUI.PT_DIALOG_SEC);
		}
	}

	private DslPrefsManager getPrefs() {
		return AntlrDTCore.getDefault().getPrefsManager();
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
		viewer.setInput(null);
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

		viewer.addSelectionChangedListener(new SelectionChangedListener());
		viewer.addDoubleClickListener(new DoubleClickListener());

		getPrefs().addPropertyChangeListener(prefListener);
		AntlrDTUI.getDefault().getWorkbench().addWorkbenchListener(new IWorkbenchListener() {

			@Override
			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				try {
					getEditorSite().getPage().closeEditor(CstEditor.this, false);
				} catch (Exception e) {}
				return true;
			}

			@Override
			public void postShutdown(IWorkbench workbench) {}
		});

		makeActions();
		setupContextMenu();
		contributeToActionBars();
		addSurfaceDragger();
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
		helper.getModel().dispose();
		AntlrDTUI.getDefault().setCstEditor(null);
		super.dispose();
	}

	public void setInput(Source source, TargetBuilder target) {
		this.source = source;
		((NodeLabelProvider) nodeLabelProvider).setNames(target.getRuleNames(), target.getTokenNames());
		helper.setModel(target.getModel());
		refresh();

	}

	public void refresh() {
		viewer.setInput(helper.getModel());
		setConnectionRouter(router);
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

	public ParseTree[] getInputElements() {
		return nodes;
	}

	public ParseTree getCurrentSelectedNode() {
		return selectedNode;
	}

	public List<ParseTree> getCurrentSelectedNodes() {
		return selectedNodes;
	}

	public void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "AntlrDT ParseTree View", message);
	}

	private void makeActions() {
		makeGroupActions();
		makeScreenshotActions();
	}

	private void setupContextMenu() {
		final MenuManager menuMgr = new MenuManager("#PopupMenu");
		viewer.getControl().addMenuDetectListener(new MenuDetectListener() {

			@Override
			public void menuDetected(MenuDetectEvent e) {
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
			// manager.add(openAction);
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

	/**
	 * Handle selection changes in the viewer. This will update the view whenever a selection occurs.
	 * All selectable nodes in the graph should resolve to ParseTree.
	 */
	@SuppressWarnings("unchecked")
	private void handleSelectionChanged(Object selectedElement, IStructuredSelection selections) {
		if (selectedElement instanceof ParseTree) {
			selectedNode = (ParseTree) selectedElement;
			selectedNodes = selections.toList();
			nodeLabelProvider.setCurrentSelection(focusedNode, selectedNode);
			// TODO: need to correctly initialize focusedNode
			if (focusedNode != null) {
				viewer.update(contentProvider.getElements(focusedNode), null);
			}
		} else {
			selectedNode = null;
			selectedNodes = null;
		}
	}

	private void addSurfaceDragger() {
		dragAdapter = new SurfaceDragAdapter();
		viewer.getGraphControl().addMouseMoveListener(dragAdapter);
		viewer.getGraphControl().addMouseListener(dragAdapter);
	}

	protected void dragSurfacePrep(MouseEvent e) {
		if (getPrefs().getBoolean(PrefsKey.PT_SURFACE_DRAG_ENABLED)) {
			// adjust click location for viewport scroll
			Point clickAt = new Point(e.x, e.y);
			Point vLoc = viewer.getGraphControl().getViewport().getViewLocation();
			clickAt.translate(vLoc);
			// only allow drag if if on empty surface
			Object fig = viewer.getGraphControl().getFigureAt(clickAt.x, clickAt.y);
			if (fig != null) return;
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

	private class DoubleClickListener implements IDoubleClickListener {

		@Override
		public void doubleClick(DoubleClickEvent event) {
			// On double click focus on that node: note bug 172627
			IStructuredSelection selection = (IStructuredSelection) event.getSelection();
			if (selection.size() < 1) {	// on the canvas
				refresh();
				return;
			}

			Object selectedElement = selection.getFirstElement();
			if (selectedElement instanceof ParserRuleContext) {
				ParserRuleContext node = (ParserRuleContext) selectedElement;
				AntlrDTUtil.openSourceFileAtToken(source, node.getStart());
			} else if (selectedElement instanceof TerminalNode) {
				TerminalNode node = (TerminalNode) selectedElement;
				AntlrDTUtil.openSourceFileAtToken(source, node.getSymbol());
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

		public PreferenceListener() {}

		@Override
		public void propertyChange(PropertyChangeEvent event) {
			String prop = event.getProperty();
			if (prop.equals(PrefsKey.PT_HORZ_SPACING) || prop.equals(PrefsKey.PT_VERT_SPACING)) {
				refresh();
			} else if (prop.equals(PrefsKey.PT_TOOLTIP_TYPE)) {
				refresh();
			} else if (prop.startsWith(PrefsKey.PT_COLOR_PREFIX)) {
				refresh();
			}
		}
	}
}
