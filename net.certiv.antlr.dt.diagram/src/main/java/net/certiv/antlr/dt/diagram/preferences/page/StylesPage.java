/*******************************************************************************
 * Copyright (c) 2016 - 2017 Certiv Analytics and others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package net.certiv.antlr.dt.diagram.preferences.page;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.diagram.preferences.blocks.StylesConfigBlock;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.preferences.blocks.IPreferenceConfigBlock;
import net.certiv.dsl.ui.preferences.pages.AbstractPreferencePage;

public class StylesPage extends AbstractPreferencePage {

	public StylesPage() {
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
	protected IPreferenceConfigBlock createConfigurationBlock(DslPrefsManagerDelta delta) {
		return new StylesConfigBlock(this, delta, getFormkit(), getColorMgr());
	}

	@Override
	protected void setDescription() {
		setDescription("Stylesheets");
	}
}
