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
package net.certiv.antlr.dt.ui.preferences.blocks;

import java.io.InputStream;
import java.util.List;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.source.IOverviewRuler;
import org.eclipse.jface.text.source.IVerticalRuler;
import org.eclipse.jface.text.source.projection.ProjectionViewer;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.editor.AntlrSimpleSourceViewerConfiguration;
import net.certiv.antlr.dt.ui.editor.Partitions;
import net.certiv.antlr.dt.ui.preferences.page.SyntaxColorPage;
import net.certiv.dsl.core.color.DslColorRegistry;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.core.preferences.PrefsDeltaManager;
import net.certiv.dsl.core.preferences.consts.Editor;
import net.certiv.dsl.ui.editor.DslEditor;
import net.certiv.dsl.ui.editor.DslSourceViewer;
import net.certiv.dsl.ui.editor.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.preferences.blocks.AbstractSyntaxColorConfigBlock;

public class SyntaxColorConfigBlock extends AbstractSyntaxColorConfigBlock {

	private static final String PREVIEW_FILE_NAME = "ColorPreview.g4";

	public SyntaxColorConfigBlock(SyntaxColorPage page, PrefsDeltaManager delta, FormToolkit formkit,
			DslColorRegistry reg) {
		super(page, delta, formkit, reg);
	}

	@Override
	protected List<String> createDeltaMatchKeys(List<String> keys) {
		keys.add(Editor.EDITOR_COMMENT_DC_COLOR);
		keys.add(Editor.EDITOR_COMMENT_BL_COLOR);
		keys.add(Editor.EDITOR_COMMENT_LN_COLOR);

		keys.add(Editor.EDITOR_KEYWORDS_COLOR);
		keys.add(PrefsKey.EDITOR_MODE_NAME_COLOR);
		keys.add(PrefsKey.EDITOR_RULE_NAME_COLOR);
		keys.add(Editor.EDITOR_STRING_COLOR);
		keys.add(Editor.EDITOR_SYMBOLS_COLOR);

		keys.add(PrefsKey.EDITOR_ACTION_COLOR);
		keys.add(PrefsKey.EDITOR_ACTION_NAME_COLOR);

		return keys;
	}

	@Override
	protected void initCatPrefsModel() {
		addColorPreference("Comments", "JavaDoc", Editor.EDITOR_COMMENT_DC_COLOR);
		addColorPreference("Comments", "Block", Editor.EDITOR_COMMENT_BL_COLOR);
		addColorPreference("Comments", "Single line", Editor.EDITOR_COMMENT_LN_COLOR);
		addColorPreference("Grammar", "Keywords", Editor.EDITOR_KEYWORDS_COLOR);
		addColorPreference("Grammar", "Mode Names", PrefsKey.EDITOR_MODE_NAME_COLOR);
		addColorPreference("Grammar", "Rule Names", PrefsKey.EDITOR_RULE_NAME_COLOR);
		addColorPreference("Grammar", "Strings", Editor.EDITOR_STRING_COLOR);
		addColorPreference("Grammar", "Symbols", Editor.EDITOR_SYMBOLS_COLOR);
		addColorPreference("Action", "Action Blocks", PrefsKey.EDITOR_ACTION_COLOR);
		addColorPreference("Action", "Action Names", PrefsKey.EDITOR_ACTION_NAME_COLOR);
	}

	@Override
	protected ProjectionViewer createPreviewViewer(Composite parent, IVerticalRuler verticalRuler,
			IOverviewRuler overviewRuler, boolean showAnnotationsOverview, int styles, IPrefsManager store) {

		return new DslSourceViewer(page.getDslUI(), parent, verticalRuler, overviewRuler, showAnnotationsOverview,
				styles, store);
	}

	@Override
	protected DslSourceViewerConfiguration createSimpleSourceViewerConfiguration(DslColorRegistry colorMgr,
			IPrefsManager store, DslEditor editor, boolean configFormatter) {
		return new AntlrSimpleSourceViewerConfiguration(colorMgr, store, editor, Partitions.PARTITIONING,
				configFormatter);
	}

	@Override
	protected void setDocumentPartitioning(IDocument document) {
		AntlrUI.getDefault().getTextTools().setupDocument(document);
	}

	@Override
	protected InputStream getPreviewContentStream() {
		return getClass().getResourceAsStream(PREVIEW_FILE_NAME);
	}
}
