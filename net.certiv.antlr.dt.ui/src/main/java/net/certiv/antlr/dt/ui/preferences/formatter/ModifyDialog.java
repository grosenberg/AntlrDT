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
package net.certiv.antlr.dt.ui.preferences.formatter;

import net.certiv.dsl.ui.formatter.FormatterModifyDialog;
import net.certiv.dsl.ui.formatter.IDslFormatterFactory;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialogOwner;

public class ModifyDialog extends FormatterModifyDialog {

	public ModifyDialog(IFormatterModifyDialogOwner dialogOwner, IDslFormatterFactory factory) {
		super(dialogOwner, factory);
	}

	@Override
	protected void addPages() {
		addTabPage("General", new TabGeneral(this, getFormatterFactory()));
		addTabPage("Blank Lines", new TabBlankLines(this));
	}
}
