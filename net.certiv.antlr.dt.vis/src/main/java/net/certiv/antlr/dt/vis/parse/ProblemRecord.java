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
package net.certiv.antlr.dt.vis.parse;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;

import net.certiv.antlr.dt.core.console.Aspect;

public class ProblemRecord implements Comparable<ProblemRecord> {

	private Aspect src;			// component sourcing the error
	private Token symbol;		// offending token
	private int line;			// Line number - one-based
	private int col;			// Actual char position - zero-based
	private int visCol;			// matches visual display in editor
	private String errTokenTxt;	// Token at error location
	private String expTokens;	// Token(s) expected at error location
	private String message;		// Error description
	private RecognitionException ex;

	public ProblemRecord(Aspect src, Token symbol, int line, int charPosInLine, String message,
			RecognitionException ex) {

		this.src = src;
		this.symbol = symbol;
		this.line = line;
		col = charPosInLine;
		visCol = charPosInLine + 1;
		this.message = message;
		this.ex = ex;
	}

	public void offendingTokenText(String errTokenTxt) {
		this.errTokenTxt = errTokenTxt;
	}

	public void expectedTokens(String expTokens) {
		this.expTokens = expTokens;
	}

	public Aspect errorSource() {
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
		return ex;
	}

	@Override
	public int compareTo(ProblemRecord record) {
		if (line > record.line) {
			return 1;
		} else if (line < record.line) {
			return -1;
		} else {
			if (col > record.col) {
				return 1;
			} else if (col < record.col) {
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
		if (!(obj instanceof ProblemRecord)) return false;
		ProblemRecord other = (ProblemRecord) obj;
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
