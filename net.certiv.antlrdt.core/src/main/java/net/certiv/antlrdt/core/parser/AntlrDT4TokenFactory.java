package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

public class AntlrDT4TokenFactory implements TokenFactory<AntlrDT4Token> {

	@Override
	public AntlrDT4Token create(int type, String text) {
		return new AntlrDT4Token(type, text);
	}

	@Override
	public AntlrDT4Token create(Pair<TokenSource, CharStream> source, int type, String text, int channel, int start,
			int stop, int line, int charPos) {

		AntlrDT4Token token = new AntlrDT4Token(source, type, channel, start, stop);
		token.setLine(line);
		token.setCharPositionInLine(charPos);

		Lexer lexer = (Lexer) token.getTokenSource();
		token.setMode(lexer._mode);
		return token;
	}
}
