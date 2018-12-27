package net.certiv.antlrdt.graph.actions;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import org.eclipse.gef.mvc.fx.ui.actions.AbstractViewerContributionItem;
import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.nebula.widgets.tablecombo.TableCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import net.certiv.antlrdt.graph.GraphUI;
import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.view.tree.TreeView;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.log.Log;

public class RouterContributionItem extends AbstractViewerContributionItem {

	public static final String ITEM_ID = "RouterContributionItem";

	private ToolItem item;
	private TableCombo combo;
	protected DiagramModel diagModel;

	private ListChangeListener<Object> routerListener;

	public RouterContributionItem() {
		setId(ITEM_ID);
	}

	@Override
	public void fill(ToolBar bar, int index) {
		item = new ToolItem(bar, SWT.SEPARATOR, index);

		combo = new TableCombo(bar, SWT.READ_ONLY | SWT.BORDER | SWT.SINGLE);

		Table table = combo.getTable();
		table.setLayoutData(new GridData(240, SWT.DEFAULT));
		ImageManager imgMgr = AntlrDTUI.getDefault().getImageManager();
		for (Router router : Router.values()) {
			TableItem ti = new TableItem(table, SWT.NONE);
			ti.setImage(0, imgMgr.get(router.getImageDescriptor()));
			ti.setText(router.getDisplayName());
		}

		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (diagModel != null) {
					Router router = Router.getEnum(e.text);
					if (router == null) router = Router.FAN;

					IDialogSettings ds = GraphUI.getDefault().getDialogSettings();
					ds.put(TreeView.NODE_ROUTER, router.getDisplayName());
					diagModel.setRouter(router);
				}
			}
		});

		item.setControl(combo);
		item.setWidth(combo.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}

	@Override
	protected void register() {
		if (routerListener != null) {
			Log.warn(this, "Router combo listener is already registered.");
			return;
		}

		routerListener = new ListChangeListener<Object>() {

			@Override
			public void onChanged(Change<? extends Object> c) {
				diagModel = getDiagramModel();
				if (diagModel != null) {
					combo.setText(diagModel.getRouter().getDisplayName());
				}
			}
		};

		getViewer().getContents().addListener(routerListener);
	}

	@Override
	protected void unregister() {
		if (routerListener != null) {
			getViewer().getContents().removeListener(routerListener);
			routerListener = null;
		}
	}

	private DiagramModel getDiagramModel() {
		InfiniteCanvasViewer viewer = (InfiniteCanvasViewer) getViewer();
		ObservableList<Object> contents = viewer.getContents();
		if (contents.isEmpty()) return null;
		return (DiagramModel) contents.get(0);
	}

	@Override
	public void dispose() {
		if (item != null && !item.isDisposed()) item.dispose();
	}
}
