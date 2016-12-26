package net.certiv.antlrdt.ui.editor.text;

import net.certiv.dsl.ui.text.rules.IWordDetector2;

public class WordDetector implements IWordDetector2 {

	@Override
	public boolean isWordStart(char c) {
		return Character.isJavaIdentifierStart(c) || c == '@' || c == '-';
	}

	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c) || c == '_' || c == '>' || c == ':';
	}

	@Override
	public boolean isPriorCharValid(char c) {
		return Character.isWhitespace(c);
	}
}
