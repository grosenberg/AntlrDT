package net.certiv.antlrdt.ui.editor.strategies;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.strategies.AutoEditDocStrategy;

public class AntlrDTAutoEditDocStrategy extends AutoEditDocStrategy {

	public AntlrDTAutoEditDocStrategy(String partitioning) {
		super(partitioning);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}
}
