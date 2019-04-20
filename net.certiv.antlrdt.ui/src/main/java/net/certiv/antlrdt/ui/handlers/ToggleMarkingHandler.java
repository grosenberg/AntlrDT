package net.certiv.antlrdt.ui.handlers;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.handlers.ToggleMarkOccurrencesHandler;

public class ToggleMarkingHandler extends ToggleMarkOccurrencesHandler {

	public ToggleMarkingHandler() {}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}
}
