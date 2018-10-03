package net.certiv.antlrdt.ui.formatter.strategies;

import java.util.Map;

import org.eclipse.jdt.core.formatter.IndentManipulation;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.ui.editor.text.LineRegion;
import net.certiv.dsl.ui.formatter.strategies.JavaFormattingStrategy;

public class ActionCodeFormattingStrategy extends JavaFormattingStrategy {

	/**
	 * ActionCodeFormattingStrategy indents relative to the beginning of the line if the open brace
	 * follows text. If the open brace has no leading text, then indent is relative to the indent
	 * level of the open brace.
	 */
	@Override
	protected int determineIndentLevel(IDocument document, int offset, Map<String, String> preferences) {
		int indentWidth = IndentManipulation.getIndentWidth(preferences);
		int tabWidth = IndentManipulation.getTabWidth(preferences);
		try {
			int line = document.getLineOfOffset(offset);
			LineRegion region = new LineRegion(document, line);
			String lineText = region.getLineIndent();
			int indentLevel = IndentManipulation.measureIndentUnits(lineText, tabWidth, indentWidth);
			return indentLevel;
		} catch (BadLocationException e) {
			Log.error(this, "Could not determine indent level", e);
		}
		return 0;
	}

	@Override
	public IPreferenceStore getFormatterPrefs() {
		return AntlrDTCore.getDefault().getPrefsManager();
	}
}
