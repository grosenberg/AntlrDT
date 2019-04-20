package net.certiv.antlrdt.ui.editor.folding;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.folding.AbstractDslFoldingStructureProvider;

public class AntlrDTFoldingStructureProvider extends AbstractDslFoldingStructureProvider {

	public AntlrDTFoldingStructureProvider() {
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
	protected String getPartitioning() {
		return Partitions.PARTITIONING;
	}

	@Override
	protected String getMultiLineCommentPartition() {
		return Partitions.COMMENT_ML;
	}

	@Override
	protected String getSingleLineCommentPartition() {
		return Partitions.COMMENT_SL;
	}
}
