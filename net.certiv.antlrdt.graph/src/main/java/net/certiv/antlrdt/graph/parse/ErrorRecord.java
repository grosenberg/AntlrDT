package net.certiv.antlrdt.graph.parse;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

public class ErrorRecord implements Comparable<ErrorRecord> {

	private ErrorSrc src;		// component sourcing the error
	private Token symbol;		// offending token
	private int line;			// Line number - one-based
	private int col;			// Actual char position - zero-based
	private int visCol;			// matches visual display in editor
	private String errTokenTxt;	// Token at error location
	private String expTokens;	// Token(s) expected at error location
	private String message;		// Error description
	private RecognitionException e;

	public ErrorRecord(ErrorSrc src, Token symbol, int line, int charPosInLine, String message,
			RecognitionException e) {

		this.src = src;
		this.symbol = symbol;
		this.line = line;
		this.col = charPosInLine;
		this.visCol = charPosInLine + 1;
		this.message = message;
		this.e = e;
	}

	public void offendingTokenText(String errTokenTxt) {
		this.errTokenTxt = errTokenTxt;
	}

	public void expectedTokens(String expTokens) {
		this.expTokens = expTokens;
	}

	public ErrorSrc errorSource() {
		return src;
	}

	public Token getOffendingSymbol() {
		return symbol;
	}

	public int getOffendingSymbolType() {
		if (symbol == null) return Token.INVALID_TYPE;
		return symbol.getType();
	}

	public int getTokenIndex() {
		if (symbol != null && symbol instanceof CommonToken) {
			return ((CommonToken) symbol).getTokenIndex();
		}
		return -1;
	}

	public int getLine() {
		return line;
	}

	public int getCol() {
		return col;
	}

	public int getVisCol() {
		return visCol;
	}

	public String getMessage() {
		return message;
	}

	public String getErrTokenText() {
		return errTokenTxt;
	}

	public String getExpectedTokens() {
		return expTokens;
	}

	public RecognitionException getException() {
		return e;
	}

	@Override
	public int compareTo(ErrorRecord record) {
		if (this.line > record.line) {
			return 1;
		} else if (this.line < record.line) {
			return -1;
		} else {
			if (this.col > record.col) {
				return 1;
			} else if (this.col < record.col) {
				return -1;
			} else {
				return 0;
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof ErrorRecord)) return false;
		ErrorRecord other = (ErrorRecord) obj;
		if (col != other.col) return false;
		if (line != other.line) return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + line;
		return result;
	}
}
