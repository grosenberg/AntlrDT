package net.certiv.antlrdt.graph.parts;

import javafx.embed.swt.FXCanvas;

import org.eclipse.gef.fx.swt.canvas.FXCanvasEx;
import org.eclipse.gef.fx.swt.canvas.IFXCanvasFactory;
import org.eclipse.swt.widgets.Composite;

public class FXCanvasFactory implements IFXCanvasFactory {

	@Override
	public FXCanvas createCanvas(Composite parent, int style) {
		return new FXCanvasEx(parent, 0);
	}
}
