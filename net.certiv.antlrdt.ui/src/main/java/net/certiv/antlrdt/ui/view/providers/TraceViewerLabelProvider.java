package net.certiv.antlrdt.ui.view.providers;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import net.certiv.antlrdt.ui.view.tokens.Trace;

public class TraceViewerLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {

	private final Color blue;
	private final Color green;
	private final Color red;

	public TraceViewerLabelProvider(Display display) {
		super();
		blue = display.getSystemColor(SWT.COLOR_DARK_BLUE);
		green = display.getSystemColor(SWT.COLOR_DARK_GREEN);
		red = display.getSystemColor(SWT.COLOR_DARK_RED);
	}

	@Override
	public void dispose() {
		if (!blue.isDisposed()) blue.dispose();
		if (!green.isDisposed()) green.dispose();
		if (!red.isDisposed()) red.dispose();
		super.dispose();
	}

	@Override
	public String getColumnText(Object obj, int index) {
		String[] rec = (String[]) obj;
		switch (index) {
			case 0:
				return rec[0]; // idx
			case 1:
				return rec[1]; // trace step description
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
		if (rec[1].startsWith(" - " + Trace.TERMINAL.toString())) return green;
		if (rec[1].startsWith(" - " + Trace.ERROR.toString())) return red;
		return blue;
	}
}
