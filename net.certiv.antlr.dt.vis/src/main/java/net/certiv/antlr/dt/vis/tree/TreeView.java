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
package net.certiv.antlr.dt.vis.tree;

import static org.eclipse.ui.IWorkbenchCommandConstants.NAVIGATE_TOGGLE_LINK_WITH_EDITOR;

import org.eclipse.draw2d.Cursors;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.PolylineConnection;
import org.eclipse.draw2d.Viewport;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.commands.ActionHandler;
import org.eclipse.jface.resource.ColorRegistry;
import org.eclipse.jface.resource.JFaceResources;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.ISelectionProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MenuDetectEvent;
import org.eclipse.swt.events.MenuDetectListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Cursor;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.handlers.IHandlerService;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.AbstractTextEditor;
import org.eclipse.ui.texteditor.ITextEditorActionConstants;
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
import net.certiv.antlr.dt.vis.actions.LinkAction;
import net.certiv.antlr.dt.vis.actions.LinkControlItem;
import net.certiv.antlr.dt.vis.actions.Router;
import net.certiv.antlr.dt.vis.actions.RouterControlItem;
import net.certiv.antlr.dt.vis.actions.ScreenshotControlItem;
import net.certiv.antlr.dt.vis.figures.EnhTipHelper;
import net.certiv.antlr.dt.vis.figures.ZoomControlItem;
import net.certiv.antlr.dt.vis.graph.EnhGraphViewer;
import net.certiv.antlr.dt.vis.graph.IAdjustableViewPart;
import net.certiv.antlr.dt.vis.graph.ViewForm;
import net.certiv.antlr.dt.vis.model.PathNode;
import net.certiv.antlr.dt.vis.model.providers.NodeContentProvider;
import net.certiv.antlr.dt.vis.model.providers.NodeLabelProvider;
import net.certiv.antlr.dt.vis.parse.TargetBuilder;
import net.certiv.antlr.dt.vis.tokens.Source;
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
import net.certiv.dsl.ui.IContextMenuConstants;
import net.certiv.dsl.ui.actions.GotoAction;
import net.certiv.dsl.ui.editor.DslEditor.DslSelection;

public class TreeView extends ViewPart implements IAdjustableViewPart, ISelectionListener {

	public static final String ID = "net.certiv.antlr.dt.vis.views.tree.TreeView";

	public final AntlrUI ui;
	public final AntlrCore core;
	private PrefsManager store;

	private PreferenceListener prefListener;

	private FormToolkit toolKit;
	private ViewForm viewForm;
	private EnhGraphViewer viewer;
	private ScrolledForm form;

	private NodeContentProvider contentProvider;
	private NodeLabelProvider labelProvider;

	private RGB fgRGB;
	private RGB bgRGB;
	private Color fgColor;
	private Color bgColor;

	private GotoAction gotoAction;
	private LinkAction linkAction;

	private Action showAllAction;

	private SurfaceDragAdapter dragAdapter;
	private boolean surfaceDragging;
	private Cursor cursorStore;
	private Point lastLocation;

	private PathNode focusedNode;
	private PathNode selectedNode;
	// private List<PathNode> selectedNodes;

	public TreeView() {
		super();
		ui = AntlrUI.getDefault();
		core = AntlrCore.getDefault();
		store = AntlrCore.getDefault().getPrefsManager();
	}

	@Override
	public String getId() {
		return ID;
	}

	@Override
	public void createPartControl(Composite parent) {
		setPartName("Parse tree display");
		toolKit = new FormToolkit(parent.getDisplay());
		viewForm = new ViewForm(parent, toolKit);
		viewer = viewForm.getGraphViewer();
		form = viewForm.getForm();

		contentProvider = new NodeContentProvider(viewer);
		labelProvider = new NodeLabelProvider(viewer);

		viewer.setContentProvider(contentProvider);
		viewer.setLabelProvider(labelProvider);
		viewer.setConnectionStyle(ZestStyles.CONNECTIONS_DIRECTED);

		initSettings();
		initColors();
		createActions();
		createMenu();
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

	// @Override
	// public boolean show(ShowInContext context) {
	// ISelection selection = context.getSelection();
	// if (selection instanceof IStructuredSelection) {
	// Object selected = ((IStructuredSelection) selection).getFirstElement();
	//
	// if (selected instanceof IDslElement) {
	// IDslElement element = (IDslElement) selected;
	// IOpenable openable = element.getOpenable();
	// if (openable instanceof ICodeUnit) {
	// return select((ICodeUnit) openable, element);
	// }
	// }
	//
	// } else {
	// IEditorPart ed = CoreUtil.getActiveEditor();
	// if (ed instanceof AntlrEditor) {
	// AntlrEditor editor = (AntlrEditor) ed;
	// return select(editor.getInputDslElement(), null);
	// }
	// }
	// return false;
	// }

	// @Override
	// public void select(ICodeUnit unit, IStatement statement) {
	// // String name = statement != null ? statement.getElementName() : null;
	// // update(PathOp.FULL_BUILD, unit, name);
	// }

	// public void refresh() {
	// viewer.setInput(helper.getModel());
	// setConnectionRouter(router);
	// }

	public void setInput(Source source, TargetBuilder builder) {
		contentProvider.updateModel(builder.getModel());
		viewer.setInput(builder.getModel());
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

	private void initSettings() {
		Layout layout = getLayout();
		if (layout == Layout.SPRING) {
			int limit = store.getInt(PrefsKey.PT_ANIMATION_LIMIT);
			((SpringLayoutAlgorithm) layout.getAlgorithm()).setIterations(limit);
		}
		viewer.setLayoutAlgorithm(layout.getAlgorithm(), true);
	}

	private void initColors() {
		if (getSite().getShell().isDisposed()) return;

		Display display = getSite().getShell().getDisplay();
		if (display == null || display.isDisposed()) return;

		ColorRegistry registry = JFaceResources.getColorRegistry();
		prefListener = new PreferenceListener();
		registry.addListener(prefListener);
		store.addPropertyChangeListener(prefListener);

		fgRGB = registry.getRGB(AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND);
		if (fgRGB == null) {
			fgColor = display.getSystemColor(SWT.COLOR_LIST_FOREGROUND);
			fgRGB = fgColor.getRGB();
		} else {
			fgColor = new Color(display, fgRGB);
		}
		viewer.getGraphControl().setForeground(fgColor);

		bgRGB = registry.getRGB(AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND);
		if (bgRGB == null) {
			bgColor = display.getSystemColor(SWT.COLOR_LIST_BACKGROUND);
			bgRGB = bgColor.getRGB();
		} else {
			bgColor = new Color(display, bgRGB);
		}
		viewer.getGraphControl().setBackground(bgColor);
	}

	/** Start to listen for selection changes. */
	protected void startListeningForSelectionChanges() {
		getSite().getPage().addPostSelectionListener(this);
	}

	/** Stop to listen for selection changes. */
	protected void stopListeningForSelectionChanges() {
		getSite().getPage().removePostSelectionListener(this);
	}

	ISelectionProvider getSelectionProvider() {
		return getViewSite().getSelectionProvider();
	}

	private void createActions() {
		showAllAction = new Action("Show All") {

			@Override
			public void run() {
				viewer.resetFilters();
				viewer.refresh();
				viewer.applyLayout();
				setRouter(getRouter());
			}
		};

		gotoAction = new GotoAction(ui, this, viewer);
		gotoAction.setEnabled(false);

		linkAction = new LinkAction(this);
	}

	private void createMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		menuMgr.setRemoveAllWhenShown(true);
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

	protected void fillContextMenu(MenuManager menuMgr, boolean onFigure) {
		if (onFigure) {
			menuMgr.add(new Separator(IContextMenuConstants.GROUP_GOTO));
			menuMgr.add(new Separator(IContextMenuConstants.GROUP_OPEN));
			menuMgr.add(new Separator(ITextEditorActionConstants.GROUP_EDIT));
			menuMgr.add(new Separator(IContextMenuConstants.GROUP_ADDITIONS));

			// menuMgr.appendToGroup(IContextMenuConstants.GROUP_OPEN, layoutAction);
			menuMgr.appendToGroup(IContextMenuConstants.GROUP_OPEN, showAllAction);
			menuMgr.appendToGroup(IContextMenuConstants.GROUP_OPEN, gotoAction);
		}
	}

	private void createToolbar() {
		form.getForm().setSeparatorVisible(true);
		IToolBarManager manager = form.getToolBarManager();

		manager.add(new Separator());
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

		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		toolBar.add(linkAction);
		toolBar.add(gotoAction);

		IHandlerService handlerService = getSite().getService(IHandlerService.class);
		handlerService.activateHandler(NAVIGATE_TOGGLE_LINK_WITH_EDITOR, new ActionHandler(linkAction));
	}

	@Override
	public void dispose() {
		store.removePropertyChangeListener(prefListener);
		getSite().getService(ISelectionService.class).removeSelectionListener(this);

		JFaceResources.getColorRegistry().removeListener(prefListener);
		bgRGB = null;
		if (bgColor != null) {
			bgColor.dispose();
			bgColor = null;
		}

		fgRGB = null;
		if (fgColor != null) {
			fgColor.dispose();
			fgColor = null;
		}

		super.dispose();
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

	private void addListeners(final TreeView view) {

		final PartAdaptor2 partAdaptor = new PartAdaptor2() {

			private boolean viewVisible = true;

			@Override
			public void partActivated(IWorkbenchPartReference partRef) {
				IWorkbenchPart part = partRef.getPart(false);
				if (part instanceof AntlrEditor && viewVisible) {
					viewer.refresh(false);
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

	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection selection) {
		if (!isLinked()) return;

		if (part instanceof AntlrEditor) {
			ICodeUnit unit = ((AntlrEditor) part).getInputDslElement();
			IStatement stmt = null;
			if (selection instanceof StructuredSelection) {
				Object element = ((StructuredSelection) selection).getFirstElement();
				if (element instanceof IStatement) {
					stmt = (IStatement) element;
				}
			}
			select(unit, stmt);

		} else if (part instanceof ContentOutline) {
			if (selection instanceof IStructuredSelection) {
				for (Object elem : ((IStructuredSelection) selection).toList()) {
					if (elem instanceof Statement && !(elem instanceof IModuleStmt)) {
						IStatement statement = (IStatement) elem;
						ICodeUnit unit = statement.getCodeUnit();
						select(unit, statement);
						break;
					}
				}
			}
		}
	}

	@Override
	public void select(ICodeUnit unit, IStatement stmt) {
		if (unit != null) {
			if (stmt != null) Log.info( "Selecting " + stmt.toString());
			viewer.setInput(unit);
		}
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
	 * Handle selection changes in the viewer. This will update the view whenever a
	 * selection occurs. All selectable nodes in the graph should resolve to PathsNode.
	 */
	private void handleSelectionChanged(Object selectedElement, IStructuredSelection selections) {
		if (selectedElement instanceof PathNode) {
			selectedNode = (PathNode) selectedElement;
			// selectedNodes = selections.toList();
			labelProvider.setCurrentSelection(focusedNode, selectedNode);
			if (focusedNode != null) {
				viewer.update(contentProvider.getElements(focusedNode), null);
			}
		} else {
			selectedNode = null;
			// selectedNodes = null;
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

				case AbstractTextEditor.PREFERENCE_COLOR_BACKGROUND:
				case AbstractTextEditor.PREFERENCE_COLOR_FOREGROUND:

					initColors();
					viewer.refresh(true);
			}
		}
	}
}
