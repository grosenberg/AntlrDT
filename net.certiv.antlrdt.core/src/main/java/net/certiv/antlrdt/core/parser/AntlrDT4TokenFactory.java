package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import net.certiv.antlrdt.core.parser.gen.AntlrDT4Lexer;

public class AntlrDT4TokenFactory implements TokenFactory<AntlrDT4Token> {

	public CharStream input;

	public AntlrDT4TokenFactory(CharStream input) {
		this.input = input;
	}

	@Override
	public AntlrDT4Token create(int type, String text) {
		return new AntlrDT4Token(type, text);
	}

	@Override
	public AntlrDT4Token create(Pair<TokenSource, CharStream> source, int type, String text, 
			int channel, int start,	int stop, int line, int charPositionInLine) {
		AntlrDT4Token token = new AntlrDT4Token(source, type, channel, start, stop);
		token.setLine(line);
		token.setCharPositionInLine(charPositionInLine);
		TokenSource tsrc = token.getTokenSource();
		token.setMode(((AntlrDT4Lexer) tsrc)._mode);
		return token;
	}
}
