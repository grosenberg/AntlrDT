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

	private static final Color blue = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE);

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
			case 3:
				// source & message
				if (rec[4].equals(ErrorSrc.PARSER.toString())) {
					return String.format("%s: %s is %s", rec[3], rec[4], rec[5]);
				}
				return rec[3] + ": " + rec[5];
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
		if (rec[3].equals(ErrorSrc.PARSER.toString())) return blue; // parser
		return null;
	}
}
