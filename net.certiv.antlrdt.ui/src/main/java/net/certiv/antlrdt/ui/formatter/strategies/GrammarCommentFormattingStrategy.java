package net.certiv.antlrdt.ui.formatter.strategies;

import org.eclipse.jface.preference.IPreferenceStore;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.dsl.ui.formatter.strategies.CommentFormattingStrategy;

public class GrammarCommentFormattingStrategy extends CommentFormattingStrategy {

	@Override
	public IPreferenceStore getFormatterPrefs() {
		return AntlrDTCore.getDefault().getPrefsManager();
	}
}
