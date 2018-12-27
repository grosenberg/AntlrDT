package net.certiv.antlrdt.graph.actions;

import javafx.collections.ObservableList;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.image.WritableImage;

import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.mvc.fx.ui.actions.AbstractViewerContributionItem;
import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;

import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.util.CoreUtil;

public class SnapshotContributionItem extends AbstractViewerContributionItem {

	public static final String ITEM_ID = "SnapshotContributionItem";

	private ToolItem item;
	private Button button;

	public SnapshotContributionItem() {
		setId(ITEM_ID);
	}

	@Override
	public void fill(ToolBar tb, int index) {
		item = new ToolItem(tb, SWT.SEPARATOR, index);
		button = new Button(tb, SWT.PUSH);
		item.setControl(button);

		ImageManager imgMgr = AntlrDTUI.getDefault().getImageManager();
		button.setImage(imgMgr.get(imgMgr.IMG_SNAPSHOT));
		button.addSelectionListener(new SelectionListener() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				openWizard();
			}

			@Override
			public void widgetDefaultSelected(SelectionEvent e) {
				widgetSelected(e);
			}
		});
	}

	protected void openWizard() {
		if (getGraph() != null) {
			InfiniteCanvasViewer viewer = (InfiniteCanvasViewer) getViewer();
			Group contents = viewer.getCanvas().getContentGroup();
			WritableImage image = contents.snapshot(null, null); // use defaults
			Bounds bounds = contents.getBoundsInLocal();

			SnapshotWizard wizard = new SnapshotWizard(image, (int) bounds.getWidth(), (int) bounds.getHeight());
			WizardDialog dialog = new WizardDialog(CoreUtil.getActiveWorkbenchShell(), wizard);
			dialog.open();
		}
	}

	private Graph getGraph() {
		InfiniteCanvasViewer viewer = (InfiniteCanvasViewer) getViewer();
		ObservableList<Object> contents = viewer.getContents();
		if (contents.isEmpty()) return null;
		return (Graph) contents.get(0);
	}
}
