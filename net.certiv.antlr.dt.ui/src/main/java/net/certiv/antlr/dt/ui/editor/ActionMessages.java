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
package net.certiv.antlr.dt.ui.editor;

import org.eclipse.osgi.util.NLS;

public final class ActionMessages extends NLS {

	private static final String BUNDLE_NAME = "net.certiv.antlr.dt.ui.editor.ActionMessages";

	private ActionMessages() {}

	public static String MemberFilterActionGroup_hide_rules_label;
	public static String MemberFilterActionGroup_hide_rules_tooltip;
	public static String MemberFilterActionGroup_hide_rules_description;
	public static String MemberFilterActionGroup_hide_tokens_label;
	public static String MemberFilterActionGroup_hide_tokens_tooltip;
	public static String MemberFilterActionGroup_hide_tokens_description;
	public static String MemberFilterActionGroup_hide_options_label;
	public static String MemberFilterActionGroup_hide_options_tooltip;
	public static String MemberFilterActionGroup_hide_options_description;
	public static String MemberFilterActionGroup_hide_at_label;
	public static String MemberFilterActionGroup_hide_at_tooltip;
	public static String MemberFilterActionGroup_hide_at_description;

	static {
		NLS.initializeMessages(BUNDLE_NAME, ActionMessages.class);
	}
}
