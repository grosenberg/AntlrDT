package net.certiv.antlrdt.graph.actions;

import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;

import org.eclipse.gef.mvc.fx.ui.actions.AbstractViewerContributionItem;
import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;
import org.eclipse.nebula.widgets.tablecombo.TableCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.view.GraphFXView;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.log.Log;

public class LayoutContributionItem extends AbstractViewerContributionItem {

	public static final String ITEM_ID = "LayoutContributionItem";

	private ToolItem item;
	private TableCombo combo;
	protected DiagramModel diagModel;
	private GraphFXView view;

	private ListChangeListener<Object> changeListener;

	public LayoutContributionItem(GraphFXView view) {
		setId(ITEM_ID);
		this.view = view;
	}

	@Override
	public void fill(ToolBar bar, int index) {
		item = new ToolItem(bar, SWT.SEPARATOR, index);

		combo = new TableCombo(bar, SWT.READ_ONLY | SWT.BORDER | SWT.SINGLE);
		Table table = combo.getTable();
		table.setLayoutData(new GridData(240, SWT.DEFAULT));
		ImageManager imgMgr = AntlrDTUI.getDefault().getImageManager();
		for (Layout layout : Layout.values()) {
			TableItem row = new TableItem(table, SWT.NONE);
			row.setImage(0, imgMgr.get(layout.getImageDescriptor()));
			row.setText(layout.getDisplayName());
		}

		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				if (diagModel != null) {
					int idx = combo.getSelectionIndex();
					TableItem row = combo.getTable().getItem(idx);
					Layout layout = Layout.getEnum(row.getText());
					if (layout == null) layout = Layout.SPRING;
					diagModel.setLayout(layout);
					view.refresh();
				}
			}
		});

		item.setControl(combo);
		item.setWidth(combo.computeSize(SWT.DEFAULT, SWT.DEFAULT, true).x);
	}

	@Override
	protected void register() {
		if (changeListener != null) {
			Log.warn(this, "Layout combo listener is already registered.");
			return;
		}

		changeListener = new ListChangeListener<Object>() {

			@Override
			public void onChanged(Change<? extends Object> c) {
				diagModel = getDiagramModel();
				if (diagModel != null) {
					combo.setText(diagModel.getLayout().getDisplayName());
				}
			}
		};

		getViewer().getContents().addListener(changeListener);
	}

	@Override
	protected void unregister() {
		if (changeListener != null) {
			getViewer().getContents().removeListener(changeListener);
			changeListener = null;
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
