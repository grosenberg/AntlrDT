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
package net.certiv.antlr.dt.ui.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.source.SourceViewerConfiguration;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.dsl.core.color.DslColorRegistry;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.ui.editor.DslEditor;
import net.certiv.dsl.ui.editor.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.editor.text.DslTextTools;

public class AntlrTextTools extends DslTextTools {

	private PartitionScanner partitionScanner;

	public AntlrTextTools(boolean autoDispose) {
		super(Partitions.PARTITIONING, Partitions.getContentTypes(), autoDispose);
	}

	@Override
	public void createAutoClosePairs() {
		for (String contentType : Partitions.getAllContentTypes()) {
			addAutoClosePair(contentType, "{", "}");
			addAutoClosePair(contentType, "(", ")");
			addAutoClosePair(contentType, "[", "]");
			addAutoClosePair(contentType, "<", ">");
		}
	}

	@Override
	public void createAutoIndentPairs() {
		String[] contentTypes = { IDocument.DEFAULT_CONTENT_TYPE, Partitions.ACTION };
		for (String contentType : contentTypes) {
			addAutoIndentPair(contentType, "{", "}");
		}
		addAutoIndentPair(IDocument.DEFAULT_CONTENT_TYPE, "(", ")");
		addAutoIndentPair(IDocument.DEFAULT_CONTENT_TYPE, ":", ";");
	}

	@Override
	public void createAutoCommentPairs() {
		String[] contentTypes = { IDocument.DEFAULT_CONTENT_TYPE, Partitions.ACTION };
		for (String contentType : contentTypes) {
			addAutoCommentPair(contentType, "/**", "*/");
			addAutoCommentPair(contentType, "/*", "*/");
		}
	}

	@Override
	public void createStringDelimPairs() {
		for (String contentType : Partitions.getAllContentTypes()) {
			addStringDelimPairs(contentType, '\'');
			addStringDelimPairs(contentType, '"');
		}
	}

	@Override
	public DslSourceViewerConfiguration createSourceViewerConfiguraton(IPrefsManager store, DslEditor editor,
			String partitioning) {
		return new AntlrSourceViewerConfiguration(getColorRegistry(), store, editor, partitioning);
	}

	@Override
	public SourceViewerConfiguration createSimpleSourceViewerConfiguration(IPrefsManager store, String partitioning) {
		return new AntlrSimpleSourceViewerConfiguration(store, null, partitioning);
	}

	private DslColorRegistry getColorRegistry() {
		return AntlrCore.getDefault().getColorRegistry();
	}

	@Override
	public IPartitionTokenScanner createPartitionScanner() {
		if (partitionScanner == null) {
			partitionScanner = new PartitionScanner();
		}
		return partitionScanner;
	}

	@Override
	public String[] getStringContentPartitions() {
		return Partitions.STRING_TYPES;
	}

	@Override
	public String[] getCommentContentPartitions() {
		return Partitions.COMMENT_TYPES;
	}

	@Override
	public String[] getStringAndCommentContentPartitions() {
		return Partitions.STRING_AND_COMMENT_TYPES;
	}

}
