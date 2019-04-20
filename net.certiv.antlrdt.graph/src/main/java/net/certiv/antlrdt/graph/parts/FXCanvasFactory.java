package net.certiv.antlrdt.graph.parts;

import org.eclipse.gef.fx.swt.canvas.FXCanvasEx;
import org.eclipse.gef.fx.swt.canvas.IFXCanvasFactory;
import org.eclipse.swt.widgets.Composite;

import javafx.embed.swt.FXCanvas;

public class FXCanvasFactory implements IFXCanvasFactory {

	@Override
	public FXCanvas createCanvas(Composite parent, int style) {
		return new FXCanvasEx(parent, 0);
	}
}
