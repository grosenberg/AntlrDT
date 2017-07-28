package net.certiv.antlrdt.ui.view;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import net.certiv.antlrdt.ui.graph.cst.ErrorRecord;
import net.certiv.dsl.core.util.Strings;

public class ListProcessor {

	public static ArrayList<String[]> extract(List<Token> tokensList, String[] tokenNames, boolean nodesOnly) {
		ArrayList<String[]> tokens = new ArrayList<String[]>();
		for (Token token : tokensList) {
			if (nodesOnly && token.getChannel() == Lexer.HIDDEN) continue;

			String idx = String.valueOf(token.getTokenIndex());
			String ttype = token.getType() == Token.EOF ? "EOF" : tokenNames[token.getType()];
			String line = String.valueOf(token.getLine() - 1);
			String col = String.valueOf(token.getCharPositionInLine() + 1);
			String text = Strings.escape(token.getText());
			String chan = String.valueOf(token.getChannel());
			tokens.add(new String[] { idx, ttype, line, col, text, chan });
		}
		return tokens;
	}

	public static ArrayList<String[]> extract(List<ErrorRecord> errorList) {
		ArrayList<String[]> errors = new ArrayList<String[]>();
		for (ErrorRecord error : errorList) {
			String idx = String.valueOf(error.getTokenIndex());
			String line = String.valueOf(error.getLine());
			String col = String.valueOf(error.getVisCol());
			String message = Strings.escape(error.getMessage());
			String src = String.valueOf(error.errorSource());
			errors.add(new String[] { idx, line, col, message, src });
		}
		return errors;
	}
}
