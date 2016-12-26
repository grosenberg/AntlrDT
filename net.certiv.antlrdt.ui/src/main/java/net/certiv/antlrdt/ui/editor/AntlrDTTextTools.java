package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.source.SourceViewerConfiguration;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.dsl.core.IColorManager;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.ui.text.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.text.DslTextTools;

public class AntlrDTTextTools extends DslTextTools {

	// all distinct pairing characters
	public static final char[] PAIRS = new char[] { '{', '}', '(', ')', '[', ']', '<', '>' };

	// used to identify indent blocks
	public static final String[] INDENT_PAIRS = new String[] { "{", "}" };

	// minimum distinctive pairs used to identify string style comments (no "/**", as not min)
	public static final String[] COMMENT_PAIRS = new String[] { "/*", "*/" };

	public static final char[] STRING_DELIMS = new char[] { '"', '\'' };

	private PartitionScanner partitionScanner;

	public AntlrDTTextTools(boolean autoDispose) {
		super(Partitions.ANTLRDT_PARTITIONING, Partitions.getContentTypes(), autoDispose);
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

	@Override
	public char[] getPairs() {
		return PAIRS;
	}

	@Override
	public char[] getStringDelimiters() {
		return STRING_DELIMS;
	}

	@Override
	public String[] getIndentPairs() {
		return INDENT_PAIRS;
	}

	@Override
	public String[] getCommentPairs() {
		return COMMENT_PAIRS;
	}
}
