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

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class GrammarBlock {

	private Text text;
	private ControlDecoration decorator;

	public GrammarBlock(TokensView view, Composite comp) {
		createGrammarBlock(view, comp);
	}

	private void createGrammarBlock(TokensView view, Composite parent) {
		Group group = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(1, 1).applyTo(group);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(4, 4).spacing(TokensView.hSp, TokensView.vSp)
				.applyTo(group);
		group.setText("Grammar");

		text = new Text(group, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		decorator = view.createDecoration(text);
		GridDataFactory.fillDefaults().indent(6, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(text);

		Button buttonSelect = new Button(group, SWT.PUSH);
		buttonSelect.setText(" Integration ");
		GridDataFactory.fillDefaults().hint(80, SWT.DEFAULT).applyTo(buttonSelect);
		buttonSelect.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				view.execGrammarDialogHandler();
			}
		});
	}

	public Text getTextControl() {
		return text;
	}

	/** Set text; return true if content changed */
	public boolean setText(String value) {
		String prior = text.getText();
		text.setText(value);
		return !prior.equals(value);
	}

	public ControlDecoration getDecorator() {
		return decorator;
	}

	public void hideDecoration() {
		decorator.hide();
	}

	public void showDecoration(String msg) {
		decorator.setDescriptionText(msg);
		decorator.show();
	}
}
