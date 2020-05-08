package net.certiv.antlr.dt.diagram.preferences;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.DslPrefsInit;

public class PrefsInitializer extends DslPrefsInit {

	public PrefsInitializer() {
		super();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	@Override
	public void initializeDefaultPreferences() {
		super.initializeDefaultPreferences();

		setBool(Prefs.DIAGRAM_ORIENT_LR, true);
	}
}
