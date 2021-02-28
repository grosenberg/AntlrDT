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

import java.util.List;

import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.Token;

import net.certiv.antlr.dt.core.console.Aspect;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Lexer;
import net.certiv.common.util.Strings;

public class ErrorListener extends BaseErrorListener {

	private Aspect src;
	private List<ProblemRecord> errors;

	public ErrorListener(Aspect src, List<ProblemRecord> errors) {
		super();
		this.src = src;
		this.errors = errors;
	}

	public List<ProblemRecord> getErrors() {
		return errors;
	}

	@Override
	public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine,
			String msg, RecognitionException e) {

		Token symbol = offendingSymbol instanceof Token ? (Token) offendingSymbol : null;
		ProblemRecord rec = new ProblemRecord(src, symbol, line, charPositionInLine, msg, e);
		rec.offendingTokenText(formatToken(symbol));
		if (e != null) rec.expectedTokens(formatExpectedTokens(e));
		errors.add(rec);
	}

	private String formatToken(Token errToken) {
		if (errToken == null) return "<no token>";
		if (errToken.getType() == Token.EOF) return "<EOF>";
		String str = errToken.getText();
		if (str == null) return Strings.LANGLE + errToken.getType() + Strings.RANGLE;
		return str;
	}

	private String formatExpectedTokens(RecognitionException e) {
		if (e.getOffendingState() < 0) return "<<nothing!>>";
		return e.getExpectedTokens().toString(AntlrDT4Lexer.VOCABULARY);
	}
}
