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
package net.certiv.antlr.dt.ui.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.lang.LanguageManager;
import net.certiv.dsl.core.model.IDslProject;
import net.certiv.dsl.core.preferences.PrefsDeltaManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.preferences.blocks.IPreferenceConfigBlock;
import net.certiv.dsl.ui.preferences.blocks.RootConfigBlock;
import net.certiv.dsl.ui.preferences.pages.AbstractPropertyPage;

public class RootFoldersPropertyPage extends AbstractPropertyPage {

	public RootFoldersPropertyPage() {
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
	protected IPreferenceConfigBlock createConfigurationBlock(PrefsDeltaManager delta) {
		LanguageManager langMgr = getDslCore().getLangManager();
		return new RootConfigBlock(this, delta, getFormkit(), getColorRegistry(), langMgr, getProject());
	}

	private IDslProject getProject() {
		IAdaptable adaptable = getElement();
		if (adaptable == null) return null;

		IProject project = adaptable.getAdapter(IProject.class);
		if (project == null) return null;

		return getDslCore().getDslModel().create(project);
	}
}
