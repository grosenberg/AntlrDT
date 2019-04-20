package net.certiv.antlr.dt.vis.views;

import javax.inject.Inject;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.DrillDownAdapter;
import org.eclipse.ui.part.ViewPart;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.editor.AntlrDTEditor;
import net.certiv.dsl.core.util.eclipse.PartListener;
import net.certiv.fluent.dt.core.preferences.Prefs;

public class ParseTreeView extends ViewPart implements PartListener, ITextListener, IPropertyChangeListener {

	public static final String ID = "net.certiv.antlr.dt.vis.views.ParseTreeView";

	@Inject
	IWorkbench workbench;

	private Browser browser;
	private ViewJob viewjob;

	private TreeViewer viewer;
	private DrillDownAdapter drillDownAdapter;
	private Action action1;
	private Action action2;
	private Action doubleClickAction;

	/** Callback to create and initialize the browser. */
	@Override
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		browser.setRedraw(true);
		browser.setJavascriptEnabled(true);

		viewjob = new ViewJob(this);
		getPreferenceStore().addPropertyChangeListener(this);
		getActivePage().addPartListener(this);

		makeActions();
		hookContextMenu();
		hookDoubleClickAction();
		contributeToActionBars();
	}

	// -------------------------------------------------------------------------
	// view opened
	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof AntlrDTEditor) {
			((AntlrDTEditor) part).getViewer().addTextListener(this);
			viewjob.update();
		}
	}

	// view closed
	@Override
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof AntlrDTEditor) {
			try { // exception when workbench close closes the editor
				getActivePage().hideView(this);
			} catch (Exception e) {
			}
		}
	}

	// on content change in the editor's text viewer
	@Override
	public void textChanged(TextEvent event) {
		if (viewjob != null)
			viewjob.update();
	}

	// on property store change
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		switch (event.getProperty()) {
		case Prefs.EDITOR_CSS_EXTERNAL_DIR:
		case Prefs.EDITOR_CSS_INTERNAL_DIR:
			if (viewjob != null)
				viewjob.load();
		}
	}

	// -------------------------------------------------------------------------

	// called only by refresh view icon
	public void trigger() {
		viewjob.update();
	}

	// called only by firebug view icon
	public void debug() {
		viewjob.load(true);
	}

	// -------------------------------------------------------------------------

	public Browser getBrowser() {
		return browser;
	}

	@Override
	public void setFocus() {
		if (browser != null)
			browser.setFocus();
	}

	protected IWorkbenchPage getActivePage() {
		return getSite().getWorkbenchWindow().getActivePage();
	}

	protected AntlrDTEditor getEditor() {
		IEditorPart editor = getActivePage().getActiveEditor();
		if (editor != null && editor instanceof AntlrDTEditor) {
			return (AntlrDTEditor) editor;
		}
		return null;
	}

	protected ISourceViewer getSourceViewer() {
		AntlrDTEditor editor = getEditor();
		if (editor == null)
			return null;
		return editor.getViewer();
	}

	protected IPreferenceStore getPreferenceStore() {
		return AntlrCore.getDefault().getPrefsManager();
	}

	@Override
	public void dispose() {
		getPreferenceStore().removePropertyChangeListener(this);
		getActivePage().removePartListener(this);
		ITextViewer srcViewer = getSourceViewer();
		if (srcViewer != null) {
			srcViewer.removeTextListener(this);
		}

		if (viewjob != null) {
			viewjob.cancel();
			viewjob.dispose();
			viewjob = null;
		}
		browser = null;
		super.dispose();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				ParseTreeView.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewer.getControl());
		viewer.getControl().setMenu(menu);
		getSite().registerContextMenu(menuMgr, viewer);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
		manager.add(new Separator());
		drillDownAdapter.addNavigationActions(manager);
	}

	private void makeActions() {
		action1 = new Action() {
			@Override
			public void run() {
				showMessage("Action 1 executed");
			}
		};
		action1.setText("Action 1");
		action1.setToolTipText("Action 1 tooltip");
		action1.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));

		action2 = new Action() {
			@Override
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(workbench.getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
		doubleClickAction = new Action() {
			@Override
			public void run() {
				IStructuredSelection selection = viewer.getStructuredSelection();
				Object obj = selection.getFirstElement();
				showMessage("Double-click detected on " + obj.toString());
			}
		};
	}

	private void hookDoubleClickAction() {
		viewer.addDoubleClickListener(new IDoubleClickListener() {
			@Override
			public void doubleClick(DoubleClickEvent event) {
				doubleClickAction.run();
			}
		});
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewer.getControl().getShell(), "Sample View", message);
	}
}
