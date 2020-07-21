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

import java.net.URL;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.formatter.AntlrSourceFormatter;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.formatter.IDslCodeFormatter;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.formatter.DslFormatterFactory;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialog;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialogOwner;

public class FormatterFactory extends DslFormatterFactory {

	private IDslCodeFormatter formatter;

	public FormatterFactory() {
		super();
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	@Override
	public IFormatterModifyDialog createDialog(IFormatterModifyDialogOwner owner) {
		return new ModifyDialog(owner, this);
	}

	@Override
	public URL getPreviewContent() {
		return getClass().getResource("FormatPreview.g4");
	}

	@Override
	public IDslCodeFormatter createFormatter() {
		if (formatter == null) {
			formatter = new AntlrSourceFormatter();
		}
		return formatter;
	}
}
