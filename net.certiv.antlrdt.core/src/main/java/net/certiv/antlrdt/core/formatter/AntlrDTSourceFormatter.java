package net.certiv.antlrdt.core.formatter;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.parser.AntlrDT4TokenFactory;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Lexer;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlrdt.core.parser.gen.FormatVisitor;
import net.certiv.antlrdt.core.parser.gen.IndentVisitor;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarSpecContext;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.formatter.DslCodeFormatter;
import net.certiv.dsl.core.parser.DslParseErrorListener;
import net.certiv.dsl.core.util.Log;

public class AntlrDTSourceFormatter extends DslCodeFormatter {

	private AntlrDT4Parser parser;

	public AntlrDTSourceFormatter() {
		super();
		setCommentTokens(AntlrDT4Parser.BLOCK_COMMENT, AntlrDT4Parser.DOC_COMMENT, AntlrDT4Parser.LINE_COMMENT);
		setLineCommentTokens(AntlrDT4Parser.LINE_COMMENT);
		setVertSpacingTokens(AntlrDT4Parser.VERT_WS);
		setHorzSpacingTokens(AntlrDT4Parser.HORZ_WS);
		setListSeparatorTokens(AntlrDT4Parser.COMMA);
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	public ParseTree parse(String name, char[] content, DslParseErrorListener errListener) throws RecognitionException {
		// Log.debug(this, "Parse [name=" + name + "]");

		input = new ANTLRInputStream(content, content.length);
		AntlrDT4Lexer lexer = new AntlrDT4Lexer(input);

		// lexer.setLexerHelper(new LexerHelper());
		AntlrDT4TokenFactory factory = new AntlrDT4TokenFactory(input);
		lexer.setTokenFactory(factory);
		tokens = new CommonTokenStream(lexer);

		parser = new AntlrDT4Parser(tokens);
		parser.setTokenFactory(factory);
		parser.removeErrorListeners(); // remove ConsoleErrorListener to reduce the noise
		parser.addErrorListener(errListener); // add listener that feeds the error markers

		GrammarSpecContext parseTree = ((AntlrDT4Parser) parser).grammarSpec();
		return parseTree;
	}

	@Override
	public void formatContent() {
		if (parseTree == null) {
			Log.debug(this, "Parse-tree [root=" + (parseTree != null ? "not null" : "null") + "]");
			return;
		}

		// walkers to format relevant elements of the ParseTree
		// first pass: build indentation structure
		try {
			IndentVisitor indenter = new IndentVisitor(parseTree);
			indenter.setHelper(this);
			indenter.findAll();
		} catch (Exception e) {
			Log.error(this, "Indenter error", e);
		}
		// second pass: format
		try {
			FormatVisitor formatter = new FormatVisitor(parseTree);
			formatter.setHelper(this);
			formatter.findAll();
		} catch (Exception e) {
			Log.error(this, "Formatter error", e);
		}
	}
}
