package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.dsl.core.color.IColorManager;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.ui.editor.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.editor.text.DslTextTools;

public class AntlrDTTextTools extends DslTextTools {

	private PartitionScanner partitionScanner;

	public AntlrDTTextTools(boolean autoDispose) {
		super(Partitions.PARTITIONING, Partitions.getContentTypes(), autoDispose);
	}

	@Override
	public void createAutoClosePairs() {
		for (String contentType : Partitions.getAllContentTypes()) {
			addAutoClosePair(contentType, '{', '}');
			addAutoClosePair(contentType, '(', ')');
			addAutoClosePair(contentType, '[', ']');
			addAutoClosePair(contentType, '<', '>');
		}
	}

	@Override
	public void createAutoIndentPairs() {
		String[] contentTypes = { IDocument.DEFAULT_CONTENT_TYPE, Partitions.ACTION };
		for (String contentType : contentTypes) {
			addAutoIndentPair(contentType, '{', '}');
		}
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
	public DslSourceViewerConfiguration createSourceViewerConfiguraton(IDslPrefsManager store, ITextEditor editor,
			String partitioning) {
		return new AntlrDTSourceViewerConfiguration(getColorManager(), store, editor, partitioning);
	}

	@Override
	public SourceViewerConfiguration createSimpleSourceViewerConfiguration(IDslPrefsManager store,
			String partitioning) {
		return new AntlrDTSimpleSourceViewerConfiguration(store, null, partitioning);
	}

	private IColorManager getColorManager() {
		return AntlrDTCore.getDefault().getColorManager();
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
