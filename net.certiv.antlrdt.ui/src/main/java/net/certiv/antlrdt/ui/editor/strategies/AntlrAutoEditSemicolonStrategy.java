package net.certiv.antlrdt.ui.editor.strategies;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.AbstractSemicolonEditStrategy;

public class AntlrAutoEditSemicolonStrategy extends AbstractSemicolonEditStrategy {

	public AntlrAutoEditSemicolonStrategy(String partitioning) {
		super(partitioning);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}
}
