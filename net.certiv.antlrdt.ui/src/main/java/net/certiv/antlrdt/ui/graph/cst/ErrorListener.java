package net.certiv.antlrdt.ui.graph.cst;

import java.util.ArrayList;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import net.certiv.antlrdt.core.parser.gen.AntlrDT4Lexer;

public class ErrorListener extends BaseErrorListener {

	private ErrorSrc src;
	private ArrayList<ErrorRecord> errors;

	public ErrorListener(ErrorSrc src, ArrayList<ErrorRecord> errors) {
		super();
		this.src = src;
		this.errors = errors;
	}

	public ArrayList<ErrorRecord> getErrors() {
		return errors;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {

		Token symbol = offendingSymbol instanceof Token ? (Token) offendingSymbol : null;
		ErrorRecord er = new ErrorRecord(src, symbol, line, charPositionInLine, msg, e);
		er.offendingTokenText(formatToken(symbol));
		if (e != null) er.expectedTokens(formatExpectedTokens(e));
		errors.add(er);
	}

	private String formatToken(Token errToken) {
		if (errToken == null) return "<no token>";
		if (errToken.getType() == Token.EOF) return "<EOF>";
		String str = errToken.getText();
		if (str == null) return "<" + errToken.getType() + ">";
		return str;
	}

	private String formatExpectedTokens(RecognitionException e) {
		return e.getExpectedTokens().toString(AntlrDT4Lexer.VOCABULARY);
	}
}
