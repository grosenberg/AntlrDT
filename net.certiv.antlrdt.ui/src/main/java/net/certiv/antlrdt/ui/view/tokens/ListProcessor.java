package net.certiv.antlrdt.ui.view.tokens;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;

import net.certiv.antlrdt.ui.graph.cst.ErrorRecord;
import net.certiv.dsl.core.util.Strings;

public class ListProcessor {

	public static ArrayList<String[]> extract(List<Token> tokensList, String[] tokenNames, boolean nodesOnly) {
		ArrayList<String[]> tokens = new ArrayList<>();
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

	public static ArrayList<String[]> extract(List<ErrorRecord> errorList, String[] tokenNames) {
		ArrayList<String[]> errors = new ArrayList<>();
		for (ErrorRecord error : errorList) {
			int ttype = error.getOffendingSymbol().getType();

			String idx = String.valueOf(error.getTokenIndex());
			String line = String.valueOf(error.getLine());
			String col = String.valueOf(error.getVisCol());
			String src = error.errorSource().toString();
			String tname = ttype == Token.EOF ? "EOF" : tokenNames[ttype];
			String message = Strings.escape(error.getMessage());

			errors.add(new String[] { idx, line, col, src, tname, message });
		}
		return errors;
	}
}
