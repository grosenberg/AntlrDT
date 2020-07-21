/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.diagram.preferences;

import net.certiv.dsl.core.preferences.DslPrefsKey;

public class Prefs extends DslPrefsKey {

	public static final String PREVIEW = "preview";
	public static final String DIAGRAM_CSS = "diagram.css";

	public static final String DIAGRAM_ORIENT = "{DSL_ID}" + ".diagram.orient"; //$NON-NLS-1$

	public static final String DIAGRAM_DOT_PROGRAM = "{DSL_ID}" + ".diagram.dot.exe"; //$NON-NLS-1$
	public static final String DIAGRAM_PANDOC_PROGRAM = "{DSL_ID}" + ".diagram.pandoc.exe"; //$NON-NLS-1$

}
