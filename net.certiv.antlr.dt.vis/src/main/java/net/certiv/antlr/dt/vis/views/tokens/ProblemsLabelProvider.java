/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.vis.views.tokens;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import net.certiv.antlr.dt.core.console.Aspect;

public class ProblemsLabelProvider extends LabelProvider implements ITableLabelProvider, ITableColorProvider {

	private final Color blue;
	private final Color green;

	public ProblemsLabelProvider(Display display) {
		super();
		blue = display.getSystemColor(SWT.COLOR_DARK_BLUE);
		green = display.getSystemColor(SWT.COLOR_DARK_GREEN);
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
				if (rec[3].equals(Aspect.PARSER.toString())) {
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
		if (rec[3].equals(Aspect.PARSER.toString())) return blue;
		if (rec[3].equals(Aspect.LEXER.toString())) return green;
		return null;
	}
}
