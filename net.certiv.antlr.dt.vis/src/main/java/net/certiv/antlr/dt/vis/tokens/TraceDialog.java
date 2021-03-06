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

import java.util.List;

import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableColumn;

public class TraceDialog extends StatusDialog {

	private static final int flags = SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;
	private static final String[] Titles = { "Idx", "Description" };
	private static final int[] Widths = { 40, 600 };

	private List<String[]> traces;
	private TableViewer traceViewer;

	public TraceDialog(Shell parent, List<String[]> traces) {
		super(parent);
		this.traces = traces;
		setTitle("Raw parser execution trace");
	}

	@Override
	public void create() {
		super.create();
		traceViewer.setInput(traces);
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		Group traceGroup = new Group(composite, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(traceGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(traceGroup);
		traceGroup.setText("Parser trace");

		traceViewer = new TableViewer(traceGroup, flags);
		traceViewer.setContentProvider(new TokensViewContentProvider());
		traceViewer.setLabelProvider(new TraceViewerLabelProvider(parent.getDisplay()));
		traceViewer.getTable().setHeaderVisible(true);
		traceViewer.getTable().setLinesVisible(true);
		GridDataFactory.fillDefaults().grab(true, true).minSize(SWT.DEFAULT, 600).applyTo(traceViewer.getControl());

		for (int idx = 0; idx < Titles.length; idx++) {
			TableColumn col = new TableColumn(traceViewer.getTable(), SWT.NONE);
			col.setText(Titles[idx]);
			col.setMoveable(false);
			col.setWidth(Widths[idx]);
		}

		return composite;
	}

	@Override
	protected boolean isResizable() {
		return true;
	}
}
