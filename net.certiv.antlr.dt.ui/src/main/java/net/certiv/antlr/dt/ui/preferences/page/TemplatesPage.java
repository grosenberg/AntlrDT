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
package net.certiv.antlr.dt.ui.preferences.page;

import org.eclipse.jface.text.IDocument;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.editor.AntlrSimpleSourceViewerConfiguration;
import net.certiv.antlr.dt.ui.editor.AntlrSourceViewerConfiguration;
import net.certiv.antlr.dt.ui.editor.Partitions;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.DslColorRegistry;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.core.preferences.PrefsDeltaManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.DslTextTools;
import net.certiv.dsl.ui.preferences.pages.DslTemplatePreferencePage;
import net.certiv.dsl.ui.templates.CompletionManager;

public class TemplatesPage extends DslTemplatePreferencePage {

	public TemplatesPage() {
		super();
		PrefsDeltaManager delta = AntlrCore.getDefault().getPrefsManager().createDeltaManager();
		setPreferenceStore(delta);
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
	protected AntlrSourceViewerConfiguration createSourceViewerConfiguration() {
		return new AntlrSimpleSourceViewerConfiguration(getColorManager(), (IPrefsManager) getPreferenceStore(), null,
				Partitions.PARTITIONING, false);
	}

	@Override
	protected void setDocumentPartitioner(IDocument document) {
		getTextTools().setupDocumentPartitioner(document, Partitions.PARTITIONING);
	}

	@Override
	protected CompletionManager getCompletionMgr() {
		return AntlrUI.getDefault().getCompletionMgr();
	}

	private DslColorRegistry getColorManager() {
		return AntlrCore.getDefault().getColorRegistry();
	}

	private DslTextTools getTextTools() {
		return AntlrUI.getDefault().getTextTools();
	}
}
