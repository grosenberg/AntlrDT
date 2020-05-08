package net.certiv.antlr.dt.ui.editor.text;

import org.eclipse.jface.text.rules.IWhitespaceDetector;

public class WhitespaceDetector implements IWhitespaceDetector {

	public boolean isWhitespace(char c) {
		return Character.isWhitespace(c);
	}
}
