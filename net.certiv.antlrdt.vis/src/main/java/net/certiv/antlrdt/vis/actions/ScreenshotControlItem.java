package net.certiv.antlrdt.vis.actions;

import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.action.ControlContribution;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.widgets.Graph;

import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.antlrdt.vis.views.IAdjustableViewPart;
import net.certiv.dsl.core.util.CoreUtil;

public class ScreenshotControlItem extends ControlContribution {

	public static final String ID = "net.certiv.antlrdt.vis.actions.SnapshotControlItem";
	private IAdjustableViewPart view;

	public ScreenshotControlItem(IAdjustableViewPart view) {
		super(ID);
		this.view = view;
	}

	@Override
	protected Control createControl(Composite parent) {
		Button snap = new Button(parent, SWT.PUSH);
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		snap.setImage(imgMgr.get(imgMgr.IMG_SNAPSHOT));
		snap.setToolTipText("Graph screenshot");
		snap.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				capture();
			}
		});

		return snap;
	}

	public void capture() {
		Shell shell = CoreUtil.getActiveWorkbenchWindow().getShell();
		Graph g = (Graph) view.getZoomableViewer().getControl();
		Rectangle bounds = g.getContents().getBounds();
		Point size = new Point(g.getContents().getSize().width, g.getContents().getSize().height);
		org.eclipse.draw2d.geometry.Point viewLocation = g.getViewport().getViewLocation();
		final Image image = new Image(null, size.x, size.y);
		GC gc = new GC(image);
		SWTGraphics swtGraphics = new SWTGraphics(gc);

		swtGraphics.translate(-1 * bounds.x + viewLocation.x, -1 * bounds.y + viewLocation.y);
		g.getViewport().paint(swtGraphics);
		gc.copyArea(image, 0, 0);
		gc.dispose();

		ScreenShotPreviewPane previewPane = new ScreenShotPreviewPane(shell);
		previewPane.setText("Image Preview");
		previewPane.open(image, size);
	}
}
