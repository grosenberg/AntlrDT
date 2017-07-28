package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import net.certiv.antlrdt.core.parser.gen.AntlrDT4Lexer;

public class AntlrDT4Token extends CommonToken {

	private int _mode;
	private boolean hasStyles;
	private boolean hasBody;

	public AntlrDT4Token(int type, String text) {
		super(type, text);
	}

	public AntlrDT4Token(Pair<TokenSource, CharStream> source, int type, int channel, int start, int stop) {
		super(source, type, channel, start, stop);
	}

	public void setMode(int mode) {
		_mode = mode;
	}

	@Override
	public String toString() {
		String chanStr = "chan=" + channel;
		if (channel == 0) chanStr = "chan=Default";
		if (channel == 1) chanStr = "chan=Hidden";
		String modeStr = "mode=" + AntlrDT4Lexer.modeNames[_mode];
		if (_mode == 0) modeStr = "mode=Default";
		String txt = getText();
		if (txt != null) {
			txt = txt.replaceAll("\n", "\\n");
			txt = txt.replaceAll("\r", "\\r");
			txt = txt.replaceAll("\t", "\\t");
		} else {
			txt = "<no text>";
		}
		String tokenName = "<EOF>";
		if (type > -1) tokenName = AntlrDT4Lexer.VOCABULARY.getDisplayName(type);
		return "[@" + getTokenIndex() + ", <" + start + ":" + stop + "> "
				+ tokenName + "(" + type + ")='" + txt + "'"
				+ ", " + chanStr + ", " + modeStr
				+ ", " + line + ":" + getCharPositionInLine() + "]";
	}

	public void styles(boolean hasStyles) {
		this.hasStyles = hasStyles;
	}

	public void body(boolean hasBody) {
		this.hasBody = hasBody;
	}

	public boolean styles() {
		return hasStyles;
	}

	public boolean body() {
		return hasBody;
	}

	public boolean isTag() {
		char c = getText().charAt(0);
		return Character.isUpperCase(c);
	}
}
