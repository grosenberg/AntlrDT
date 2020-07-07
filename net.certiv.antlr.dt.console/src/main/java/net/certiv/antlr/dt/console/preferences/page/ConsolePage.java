package net.certiv.antlr.dt.console.preferences.page;

import net.certiv.antlr.dt.console.ConsoleUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.preferences.pages.AbstractConsolePage;
import net.certiv.dsl.ui.DslUI;

public class ConsolePage extends AbstractConsolePage {

	public ConsolePage() {
		super();
	}

	@Override
	public DslUI getDslUI() {
		return ConsoleUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return ConsoleUI.getDefault().getDslCore();
	}
}
