package net.certiv.antlr.dt.vis.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;

public class ScreenshotHandler extends AbstractHandler {

	public ScreenshotHandler() {
		super();
	}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		// IWorkbenchWindow window = HandlerUtil.getActiveWorkbenchWindowChecked(event);
		// Shell shell = window.getShell();
		//
		// CallGraphEditor view = CallGraphPlugin.getDefault().getEditor();
		// Graph g = (Graph) view.getZoomableViewer().getControl();
		// Rectangle bounds = g.getContents().getBounds();
		// Point size = new Point(g.getContents().getSize().width,
		// g.getContents().getSize().height);
		// org.eclipse.draw2d.geometry.Point viewLocation =
		// g.getViewport().getViewLocation();
		// Image image = new Image(null, size.x, size.y);
		// GC gc = new GC(image);
		// try {
		// gc.setAdvanced(true);
		// gc.setAntialias(SWT.ON);
		// } catch (Exception e) {}
		// SWTGraphics swtGraphics = new SWTGraphics(gc);
		//
		// swtGraphics.translate(-1 * bounds.x + viewLocation.x, -1 * bounds.y +
		// viewLocation.y);
		// g.getViewport().paint(swtGraphics);
		// gc.copyArea(image, 0, 0);
		// gc.dispose();
		//
		// ImagePreviewPane previewPane = new ImagePreviewPane(shell);
		// previewPane.setText("Image Preview");
		// previewPane.open(image, size);
		//
		// image.dispose();
		return null;
	}
}
