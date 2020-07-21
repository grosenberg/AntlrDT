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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import net.certiv.dsl.core.parser.IDslToken;
import net.certiv.dsl.core.util.Strings;

public class FieldProcessor {

	public static final String INFO = "Info";
	public static final String ERROR = "Error";
	public static final String EOF = "EOF";

	public static List<String[]> extract(List<Token> tokens, String[] tokenNames, String[] modeNames,
			boolean nodesOnly) {

		List<String[]> fields = new ArrayList<>();
		for (Token token : tokens) {
			if (nodesOnly && token.getChannel() == Lexer.HIDDEN) continue;

			String idx = String.valueOf(token.getTokenIndex());
			String ttype = token.getType() == Token.EOF ? EOF : tokenNames[token.getType()];
			String line = String.valueOf(token.getLine());
			String col = String.valueOf(token.getCharPositionInLine() + 1);
			String text = Strings.escape(token.getText());
			String chan = String.valueOf(token.getChannel());
			String mode = modeName(token, modeNames);
			fields.add(new String[] { idx, ttype, line, col, text, chan, mode });
		}
		return fields;
	}

	private static String modeName(Token token, String[] modeNames) {
		if (token instanceof IDslToken) {
			int mode = ((IDslToken) token).getMode();
			if (mode > 0) return modeNames[mode];
		}
		return Strings.EMPTY;
	}

	public static List<String[]> extract(List<ProblemRecord> problems, String[] tokenNames) {
		List<String[]> fields = new ArrayList<>();
		for (ProblemRecord problem : problems) {
			int ttype = problem.getOffendingSymbolType();
			String idx = String.valueOf(problem.getTokenIndex());
			String line = String.valueOf(problem.getLine());
			String col = String.valueOf(problem.getVisCol());
			String src = problem.errorSource().toString();
			String tname = ttype == Token.EOF ? EOF : tokenNames[ttype];
			String message = Strings.escape(problem.getMessage());

			fields.add(new String[] { idx, line, col, src, tname, message });
		}
		return fields;
	}

	// public static List<String[]> extract(List<ErrorRecord> errors) {
	// List<String[]> fields = new ArrayList<>();
	// for (ErrorRecord error : errors) {
	// String kind = error.isError() ? ERROR : INFO;
	// String aspect = error.aspect().toString();
	// String message = error.getMessage();
	// fields.add(new String[] { kind, aspect, message });
	// }
	// return fields;
	// }
}
