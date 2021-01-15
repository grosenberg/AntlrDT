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
package net.certiv.antlr.dt.vis.tokens;

import org.eclipse.jface.viewers.ITableColorProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

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
