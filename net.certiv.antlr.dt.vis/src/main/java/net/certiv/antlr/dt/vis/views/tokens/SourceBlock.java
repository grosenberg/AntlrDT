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

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

public class SourceBlock {

	private CCombo combo;
	private ControlDecoration decorator;

	public SourceBlock(TokensView view, Composite comp) {
		createSourceBlock(view, comp);
	}

	private void createSourceBlock(TokensView view, Composite parent) {

		Group srcGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(1, 1).applyTo(srcGroup);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(4, 4).spacing(TokensView.hSp, TokensView.vSp)
				.applyTo(srcGroup);
		srcGroup.setText("Source");

		combo = new CCombo(srcGroup, SWT.DROP_DOWN | SWT.READ_ONLY | SWT.BORDER);
		decorator = view.createDecoration(combo);
		GridDataFactory.fillDefaults().indent(6, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(combo);
		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				view.dropDownSourceFileSelection(combo.getSelectionIndex());
			}
		});
		Button buttonSelect = new Button(srcGroup, SWT.PUSH);
		buttonSelect.setText(" Select ");
		GridDataFactory.fillDefaults().hint(60, SWT.DEFAULT).applyTo(buttonSelect);
		buttonSelect.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				view.buttonSourceFileSelection(e);
			}
		});
	}

	public CCombo getCombo() {
		return combo;
	}

	public ControlDecoration getDecorator() {
		return decorator;
	}

	public void clear() {
		combo.clearSelection();
	}

	public void hideDecoration() {
		getDecorator().hide();
	}

	public void showDecoration(String msg) {
		getDecorator().setDescriptionText(msg);
		getDecorator().show();
	}
}
