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
package net.certiv.antlr.dt.ui.formatter.strategies;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.dsl.core.formatter.IndentManipulation;
import net.certiv.common.log.Log;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.jdt.ui.formatter.strategies.JavaFormattingStrategy;
import net.certiv.dsl.ui.editor.text.LineRegion;

public class ActionCodeFormattingStrategy extends JavaFormattingStrategy {

	/**
	 * ActionCodeFormattingStrategy indents relative to the beginning of the line if the open
	 * brace follows text. If the open brace has no leading text, then indent is relative to
	 * the indent level of the open brace.
	 */
	@Override
	protected int determineIndentLevel(PrefsManager prefs, IDocument document, int offset) {
		int tabWidth = IndentManipulation.getTabWidth(prefs);
		try {
			int line = document.getLineOfOffset(offset);
			LineRegion region = new LineRegion(document, line);
			String lineText = region.getLineIndent();
			int indentLevel = IndentManipulation.measureIndentUnits(lineText, tabWidth);
			return indentLevel;
		} catch (BadLocationException e) {
			Log.error(this, "Could not determine indent level", e);
		}
		return 0;
	}

	@Override
	public PrefsManager getFormatterPrefs() {
		return AntlrCore.getDefault().getPrefsManager();
	}
}
