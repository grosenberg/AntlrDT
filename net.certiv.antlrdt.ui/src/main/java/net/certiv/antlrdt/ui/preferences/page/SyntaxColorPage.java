package net.certiv.antlrdt.ui.preferences.page;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.preferences.PrefsMessages;
import net.certiv.antlrdt.ui.preferences.blocks.SyntaxColorConfigBlock;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.preferences.blocks.IPreferenceConfigBlock;
import net.certiv.dsl.ui.preferences.pages.AbstractPreferencePage;

public class SyntaxColorPage extends AbstractPreferencePage {

	public SyntaxColorPage() {
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
	protected IPreferenceConfigBlock createConfigurationBlock(DslPrefsManagerDelta delta) {
		return new SyntaxColorConfigBlock(this, delta, getFormkit(), getColorMgr());
	}

	@Override
	protected void setDescription() {
		setDescription(PrefsMessages.EditorPreferencePage_general);
	}
}
