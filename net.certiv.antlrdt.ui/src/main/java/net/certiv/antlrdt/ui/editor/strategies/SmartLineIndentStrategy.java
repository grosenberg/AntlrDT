package net.certiv.antlrdt.ui.editor.strategies;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.AbstractLineIndentStrategy;

public class SmartLineIndentStrategy extends AbstractLineIndentStrategy {

	private static final String[] ALIGNTERMS = { "{" };
	private static final String[] CLOSETERMS = { ")", "}" };

	public SmartLineIndentStrategy(String partitioning, String contentType) {
		super(partitioning, contentType);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	protected String[] getAlignTerms() {
		return ALIGNTERMS;
	}

	@Override
	protected String[] getCloseTerms() {
		return CLOSETERMS;
	}

	@Override
	protected String[] getStringAndCommentPartitionTypes() {
		return Partitions.STRING_AND_COMMENT_TYPES;
	}
}
