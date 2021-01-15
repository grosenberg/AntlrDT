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
package net.certiv.antlr.dt.ui.wizards;

import org.eclipse.jface.resource.ImageDescriptor;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslImageManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.wizard.DslFileWizard;

/** Create a new grammar resource in the indicated container. */
public class AntlrNewWizard extends DslFileWizard {

	private AntlrNewWizardPage page;

	public AntlrNewWizard() {
		super("AntlrNewWizard");
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
	public ImageDescriptor getPageImageDescriptor() {
		DslImageManager mgr = getDslUI().getImageManager();
		return mgr.getDescriptor(mgr.IMG_WIZBAN_NEW_FILE);
	}

	@Override
	public String getWindowShellTitle() {
		return "New Antlr grammar";
	}

	@Override
	protected AntlrNewWizardPage getMainPage() {
		if (page == null) {
			page = new AntlrNewWizardPage(this, getSelection());
		}
		return page;
	}
}
