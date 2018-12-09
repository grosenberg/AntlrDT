package net.certiv.antlrdt.ui.view.providers;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import net.certiv.antlrdt.ui.graph.cst.ErrorSrc;

public class ErrorsViewerLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {

	private Color blue;
	private Color green;

	public ErrorsViewerLabelProvider(Display display) {
		super();
		blue = display.getSystemColor(SWT.COLOR_DARK_BLUE);
		green = display.getSystemColor(SWT.COLOR_DARK_GREEN);
	}

	@Override
	public void dispose() {
		if (!blue.isDisposed()) blue.dispose();
		if (!green.isDisposed()) green.dispose();
		super.dispose();
	}

	@Override
	public String getColumnText(Object obj, int index) {
		String[] rec = (String[]) obj;
		switch (index) {
			case 0:
				return rec[0]; // index
			case 1:
				return rec[1]; // line
			case 2:
				return rec[2]; // col
			case 3:			   // source & message
				if (rec[3].equals(ErrorSrc.PARSER.toString())) {
					return String.format("%s is %s", rec[4], rec[5]);
				}
				return rec[5];
		}
		return "<<??>>";
	}

	@Override
	public Image getColumnImage(Object obj, int index) {
		return null;
	}

	@Override
	public Color getBackground(Object element, int columnIndex) {
		return null;
	}

	@Override
	public Color getForeground(Object element, int columnIndex) {
		String[] rec = (String[]) element;
		if (rec[3].equals(ErrorSrc.PARSER.toString())) return blue;
		if (rec[3].equals(ErrorSrc.LEXER.toString())) return green;
		return null;
	}
}
