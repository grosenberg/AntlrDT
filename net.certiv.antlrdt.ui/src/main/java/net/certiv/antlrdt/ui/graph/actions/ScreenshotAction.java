package net.certiv.antlrdt.ui.graph.actions;

import org.eclipse.draw2d.SWTGraphics;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;
import org.eclipse.zest.core.widgets.Graph;

import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.AntlrImageManager;

public class ScreenshotAction extends Action {

	private IZoomableWorkbenchPart view;

	public ScreenshotAction(IZoomableWorkbenchPart view) {
		this.view = view;
		AntlrImageManager imgMgr = AntlrDTUI.getDefault().getImageManager();

		setText("Graph Screenshot");
		setImageDescriptor(imgMgr.getDescriptor(imgMgr.IMG_SNAPSHOT));
		setToolTipText("Graph screenshot");
		setEnabled(true);
	}

	@Override
	public void run() {
		Shell shell = AntlrDTUI.getDefault().getWorkbench().getActiveWorkbenchWindow().getShell();
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
