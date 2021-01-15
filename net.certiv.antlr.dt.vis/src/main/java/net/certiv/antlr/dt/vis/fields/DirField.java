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
package net.certiv.antlr.dt.vis.fields;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Shell;

import net.certiv.antlr.dt.vis.tokens.Source;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.ui.fields.TextField;

public class DirField extends TextField {

	private Shell shell;
	private Source source;

	/** Gets a directory name. Operates on workspace relative paths */
	public DirField(Shell shell, Composite comp, int style, String id, String labelTxt, int hSpan) {
		super(comp, style, id, labelTxt, hSpan);
		this.shell = shell;
	}

	@Override
	protected Composite createField() {
		Composite comp = super.createField();

		Button button = new Button(comp, SWT.PUSH);
		button.setText(" Select ");
		GridDataFactory.fillDefaults().hint(60, SWT.DEFAULT).applyTo(button);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(shell, SWT.SINGLE);
				IPath dir = new Path(getText());
				dir = CoreUtil.getWorkspaceLocation().append(dir);
				dialog.setFilterPath(dir.toString());

				if (dialog.open() == null) return; 				// cancelled
				if (dialog.getFilterPath().trim().isEmpty()) { 	// entry cleared
					setSource(new Source());					// set to empty source
					fireContentChangedEvent(Strings.EMPTY);

				} else {										// validate
					dir = new Path(dialog.getFilterPath());
					if (dir.toFile().exists()) {				// change only if valid
						dir = dir.makeRelativeTo(CoreUtil.getWorkspaceLocation());
						Source source = new Source(dir.toString(), Strings.EMPTY);
						setSource(source);
						fireContentChangedEvent(dialog.getFilterPath());
					}
				}
			}
		});
		return comp;
	}

	public Source getSource() {
		return source;
	}

	public Source setSource(Source source) {
		Source prior = this.source;
		this.source = source;
		setText(source.getPath());
		return prior;
	}

	@Override
	public int hSpanAdjust() {
		return 2;
	}
}
