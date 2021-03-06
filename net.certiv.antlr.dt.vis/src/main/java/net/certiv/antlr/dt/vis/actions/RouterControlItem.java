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
package net.certiv.antlr.dt.vis.actions;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.nebula.widgets.tablecombo.TableCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.graph.IAdjustableViewPart;

public class RouterControlItem extends ControlContribution {

	public static final String ID = "net.certiv.antlr.dt.vis.actions.RouterControlItem";

	private IAdjustableViewPart view;
	private TableCombo combo;

	public RouterControlItem(IAdjustableViewPart view) {
		super(ID);
		this.view = view;
	}

	@Override
	protected Control createControl(Composite parent) {
		combo = new TableCombo(parent, SWT.READ_ONLY | SWT.BORDER | SWT.SINGLE);
		Table table = combo.getTable();
		table.setLayoutData(new GridData(240, SWT.DEFAULT));
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		for (Router layout : Router.values()) {
			TableItem row = new TableItem(table, SWT.NONE);
			row.setImage(0, imgMgr.get(layout.getImageDescriptor()));
			row.setText(layout.getDisplayName());
		}

		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				int idx = combo.getSelectionIndex();
				TableItem row = combo.getTable().getItem(idx);
				Router router = Router.from(row.getText());
				if (router == null) router = Router.FAN;
				view.setRouter(router);
			}
		});

		Router router = view.getRouter();
		combo.setText(router.getDisplayName());

		return combo;
	}
}
