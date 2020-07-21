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

import org.eclipse.osgi.util.NLS;

public class PrefsMessages extends NLS {

	private static final String BUNDLE_NAME = "net.certiv.fluent.dt.ui.preferences.PrefsMessages";//$NON-NLS-1$

	private PrefsMessages() {}

	static {
		NLS.initializeMessages(BUNDLE_NAME, net.certiv.antlr.dt.diagram.preferences.PrefsMessages.class);
	}

	public static String GlobalPreferencePage_description;

	public static String EditorPreferencePage_general;

	public static String SmartTypingConfigurationBlock_smartPaste;
	public static String SmartTypingConfigurationBlock_typing_smartTab;
	public static String SmartTypingConfigurationBlock_closeBrackets;
	public static String SmartTypingConfigurationBlock_closeStrings;
	public static String SmartTypingConfigurationBlock_typing_tabTitle;
}
