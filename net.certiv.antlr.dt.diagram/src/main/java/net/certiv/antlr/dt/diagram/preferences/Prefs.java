package net.certiv.antlr.dt.diagram.preferences;

import net.certiv.dsl.core.preferences.DslPrefsKey;

public class Prefs extends DslPrefsKey {

	public static final String PREVIEW = "preview";
	public static final String DIAGRAM_CSS = "diagram.css";

	public static final String DIAGRAM_ORIENT = "{DSL_ID}" + ".diagram.orient"; //$NON-NLS-1$

	public static final String DIAGRAM_DOT_PROGRAM = "{DSL_ID}" + ".diagram.dot.exe"; //$NON-NLS-1$
	public static final String DIAGRAM_PANDOC_PROGRAM = "{DSL_ID}" + ".diagram.pandoc.exe"; //$NON-NLS-1$

}
