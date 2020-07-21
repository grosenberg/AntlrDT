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
//
// Generated from D:\DevFiles\Eclipse\Tools\Editors\net.certiv.antlr.dt\net.certiv.antlr.dt.core\src\main\java\net\certiv\antlr.dt\core\parser\Path.xv
// by XVisitor 4.7
//
package net.certiv.antlr.dt.core.parser.gen;

	import net.certiv.antlr.dt.core.parser.PathAdaptor;

import java.util.List;
import org.antlr.v4.runtime.tree.ParseTree;
import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlr.runtime.xvisitor.xpath.EType;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class PathVisitor extends PathAdaptor {

	public static final String[] tokenNames = {
		"<INVALID>", "TOKEN_REF", "RULE_REF", "LEXER_CHAR_SET", "DOC_COMMENT",
		"BLOCK_COMMENT", "LINE_COMMENT", "INT", "STRING_LITERAL", "UNTERMINATED_STRING_LITERAL",
		"BEGIN_ARGUMENT", "BEGIN_ACTION", "OPTIONS", "TOKENS", "CHANNELS", "IMPORT",
		"FRAGMENT", "LEXER", "PARSER", "XVISITOR", "GRAMMAR", "PROTECTED", "PUBLIC",
		"PRIVATE", "RETURNS", "LOCALS", "THROWS", "CATCH", "FINALLY", "MODE",
		"COLON", "COLONCOLON", "COMMA", "SEMI", "LPAREN", "RPAREN", "LBRACE",
		"RBRACE", "RARROW", "LT", "GT", "ASSIGN", "QUESTION", "STAR", "PLUS_ASSIGN",
		"PLUS", "OR", "DOLLAR", "RANGE", "DOT", "AT", "POUND", "NOT", "ID", "HORZ_WS",
		"VERT_WS", "END_ARGUMENT", "UNTERMINATED_ARGUMENT", "ARGUMENT_CONTENT",
		"END_ACTION", "UNTERMINATED_ACTION", "ACTION_CONTENT", "UNTERMINATED_CHAR_SET"
	};

	public static final int
		TOKEN_REF = 1, RULE_REF = 2, LEXER_CHAR_SET = 3, DOC_COMMENT = 4, BLOCK_COMMENT = 5,
		LINE_COMMENT = 6, INT = 7, STRING_LITERAL = 8, UNTERMINATED_STRING_LITERAL = 9,
		BEGIN_ARGUMENT = 10, BEGIN_ACTION = 11, OPTIONS = 12, TOKENS = 13, CHANNELS = 14,
		IMPORT = 15, FRAGMENT = 16, LEXER = 17, PARSER = 18, XVISITOR = 19, GRAMMAR = 20,
		PROTECTED = 21, PUBLIC = 22, PRIVATE = 23, RETURNS = 24, LOCALS = 25,
		THROWS = 26, CATCH = 27, FINALLY = 28, MODE = 29, COLON = 30, COLONCOLON = 31,
		COMMA = 32, SEMI = 33, LPAREN = 34, RPAREN = 35, LBRACE = 36, RBRACE = 37,
		RARROW = 38, LT = 39, GT = 40, ASSIGN = 41, QUESTION = 42, STAR = 43,
		PLUS_ASSIGN = 44, PLUS = 45, OR = 46, DOLLAR = 47, RANGE = 48, DOT = 49,
		AT = 50, POUND = 51, NOT = 52, ID = 53, HORZ_WS = 54, VERT_WS = 55, END_ARGUMENT = 56,
		UNTERMINATED_ARGUMENT = 57, ARGUMENT_CONTENT = 58, END_ACTION = 59, UNTERMINATED_ACTION = 60,
		ACTION_CONTENT = 61, UNTERMINATED_CHAR_SET = 62;

	public static final String[] ruleNames = {
		"grammarSpec", "declaration", "grammarType", "prequelConstruct", "optionsSpec",
		"option", "optionValue", "delegateGrammars", "tokensSpec", "channelsSpec",
		"idList", "idListElement", "action", "actionScopeName", "actionBlock",
		"argActionBlock", "rules", "ruleSpec", "parserRuleSpec", "exceptionGroup",
		"exceptionHandler", "finallyClause", "rulePrequel", "ruleReturns", "throwsSpec",
		"localsSpec", "ruleAction", "ruleModifiers", "ruleModifier", "ruleBlock",
		"ruleAltList", "labeledAlt", "modeRuleSpec", "lexerRuleSpec", "fragmentRuleSpec",
		"lexerRuleBlock", "lexerAltList", "lexerAlt", "lexerElements", "lexerElement",
		"labeledLexerElement", "lexerBlock", "lexerCommands", "lexerCommand",
		"lexerCommandName", "lexerCommandExpr", "altList", "alternative", "element",
		"labeledElement", "ebnf", "blockSuffix", "ebnfSuffix", "lexerAtom", "atom",
		"notSet", "blockSet", "setElement", "charSet", "block", "ruleref", "range",
		"terminal", "elementOptions", "elementOption", "id"
	};

	public static final int
		grammarSpec = 1000, declaration = 1001, grammarType = 1002, prequelConstruct = 1003,
		optionsSpec = 1004, option = 1005, optionValue = 1006, delegateGrammars = 1007,
		tokensSpec = 1008, channelsSpec = 1009, idList = 1010, idListElement = 1011,
		action = 1012, actionScopeName = 1013, actionBlock = 1014, argActionBlock = 1015,
		rules = 1016, ruleSpec = 1017, parserRuleSpec = 1018, exceptionGroup = 1019,
		exceptionHandler = 1020, finallyClause = 1021, rulePrequel = 1022, ruleReturns = 1023,
		throwsSpec = 1024, localsSpec = 1025, ruleAction = 1026, ruleModifiers = 1027,
		ruleModifier = 1028, ruleBlock = 1029, ruleAltList = 1030, labeledAlt = 1031,
		modeRuleSpec = 1032, lexerRuleSpec = 1033, fragmentRuleSpec = 1034, lexerRuleBlock = 1035,
		lexerAltList = 1036, lexerAlt = 1037, lexerElements = 1038, lexerElement = 1039,
		labeledLexerElement = 1040, lexerBlock = 1041, lexerCommands = 1042, lexerCommand = 1043,
		lexerCommandName = 1044, lexerCommandExpr = 1045, altList = 1046, alternative = 1047,
		element = 1048, labeledElement = 1049, ebnf = 1050, blockSuffix = 1051,
		ebnfSuffix = 1052, lexerAtom = 1053, atom = 1054, notSet = 1055, blockSet = 1056,
		setElement = 1057, charSet = 1058, block = 1059, ruleref = 1060, range = 1061,
		terminal = 1062, elementOptions = 1063, elementOption = 1064, id = 1065;

	public PathVisitor(ParseTree tree) {
		super(tree);
		init();
	}

	/** Entry point for finding all matches of the defined XPaths in the parse tree */
	@Override
	public void findAll() {
		super.findAll();
	}

	/**
	 * Entry point for finding all matches of a set of one or more named XPaths in the parse tree. The name of an XPath
	 * is the rulename used in the tree grammar to define the XPath.
	 */
	@Override
	public void find(String... names) {
		super.find(names);
	}

	/** Change the parse tree to match against. Implicitly performs a reset. */
	@Override
	public void setNewParseTree(ParseTree tree) {
		super.setNewParseTree(tree);
	}

	/** Clears state information developed in a prior find operation. */
	@Override
	public void reset() {
		super.reset();
	}

	protected String[] getTokenNames() {
		return tokenNames;
	}

	protected String[] getRuleNames() {
		return ruleNames;
	}

	private void init() {
		mainRule("paths");

					createPathSpec("parserRule");
						addElement(EType.Rule, true, false, "parserRuleSpec", 18);
					completePathSpec();

					createPathSpec("parserRuleRef");
						addElement(EType.Rule, true, false, "parserRuleSpec", 18);
						addElement(EType.Rule, true, false, "ruleref", 60);
					completePathSpec();

					createPathSpec("lexerRule");
						addElement(EType.Rule, true, false, "lexerRuleSpec", 33);
					completePathSpec();

					createPathSpec("fragmentRule");
						addElement(EType.Rule, true, false, "fragmentRuleSpec", 34);
					completePathSpec();

					createPathSpec("terminal");
						addElement(EType.Rule, true, false, "terminal", 62);
					completePathSpec();

					createPathSpec("range");
						addElement(EType.Rule, true, false, "range", 61);
					completePathSpec();

					createPathSpec("notset");
						addElement(EType.Rule, true, false, "notSet", 55);
					completePathSpec();

					createPathSpec("charset");
						addElement(EType.Rule, true, false, "charSet", 58);
					completePathSpec();
	}


	@Override
	public void executeActionBlock(String name) {
		switch (name) {
					case "parserRule":
						parserRule();
						break;
					case "parserRuleRef":
						parserRuleRef();
						break;
					case "lexerRule":
						lexerRule();
						break;
					case "fragmentRule":
						fragmentRule();
						break;
					case "terminal":
						terminal();
						break;
					case "range":
						range();
						break;
					case "notset":
						notset();
						break;
					case "charset":
						charset();
						break;
		}
	}

	private void parserRule() {
		if (entering()) { parserRuleName(getToken(AntlrDT4Parser.RULE_REF, 0)); }
		if (exiting()) { ruleDone(); }
	}

	private void parserRuleRef() {
		if (entering()) { ruleRefName(getToken(AntlrDT4Parser.RULE_REF, 0)); }
		if (exiting()) { ruleDone(); }
	}

	private void lexerRule() {
		if (entering()) { lexerRuleName(getToken(AntlrDT4Parser.TOKEN_REF, 0)); }
		if (exiting()) { ruleDone(); }
	}

	private void fragmentRule() {
		if (entering()) { fragmentRuleName(getToken(AntlrDT4Parser.TOKEN_REF, 0)); }
		if (exiting()) { ruleDone(); }
	}

	private void terminal() {
		if (entering()) { terminal(getToken(AntlrDT4Parser.TOKEN_REF, 0), getToken(AntlrDT4Parser.STRING_LITERAL, 0)); }
	}

	private void range() {
		if (entering()) { literal(); }
	}

	private void notset() {
		if (entering()) { literal(); }
	}

	private void charset() {
		if (entering()) { literal(); }
	}



}
