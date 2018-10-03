package net.certiv.antlrdt.ui.preferences.page;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.preferences.blocks.IPreferenceConfigBlock;
import net.certiv.dsl.ui.preferences.pages.AbstractPreferencePage;

public class MarkOccurrencesPage extends AbstractPreferencePage {

	public MarkOccurrencesPage() {
		super();
	}

	@Override
	protected IPreferenceConfigBlock createConfigurationBlock(DslPrefsManagerDelta delta) {
		return new MarkOccurrencesConfigBlock(this, delta, getFormkit(), getColorMgr());
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
