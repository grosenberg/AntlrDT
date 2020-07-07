package net.certiv.antlr.dt.vis.views.tokens;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import net.certiv.dsl.core.util.Strings;

public class TokensLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {

	private final Color gray;

	public TokensLabelProvider(Display display) {
		super();
		gray = display.getSystemColor(SWT.COLOR_GRAY);
	}

	@Override
	public String getColumnText(Object obj, int index) {
		String[] rec = (String[]) obj;
		switch (index) {
			case 0:
				return rec[0]; // idx
			case 1:
				return rec[1]; // token name
			case 2:
				return rec[2]; // line
			case 3:
				return rec[3]; // col
			case 4:
				return rec[6]; // mode
			case 5:
				return Strings.encode(rec[4]); // text
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
		if (!rec[5].equals("0")) return gray; // hidden
		return null;
	}
}
