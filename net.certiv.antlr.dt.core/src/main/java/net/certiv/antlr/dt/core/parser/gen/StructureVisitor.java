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
// Generated from D:\DevFiles\Eclipse\Tools\Editors\net.certiv.antlr.dt\net.certiv.antlr.dt.core\src\main\java\net\certiv\antlr.dt\core\parser\Structure.xv
// by XVisitor 4.7
//
package net.certiv.antlr.dt.core.parser.gen;
	import net.certiv.antlr.dt.core.model.StructureBuilder;

import java.util.List;
import org.antlr.v4.runtime.tree.ParseTree;
import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlr.runtime.xvisitor.xpath.EType;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class StructureVisitor extends StructureBuilder {

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

	public StructureVisitor(ParseTree tree) {
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
		mainRule("structure");

					createPathSpec("grammarSpec");
						addElement(EType.Rule, false, false, "grammarSpec", 0);
					completePathSpec();

					createPathSpec("declaration");
						addElement(EType.Rule, false, false, "grammarSpec", 0);
						addElement(EType.Rule, false, false, "declaration", 1);
					completePathSpec();

					createPathSpec("atAction");
						addElement(EType.Rule, false, false, "grammarSpec", 0);
						addElement(EType.Rule, false, false, "prequelConstruct", 3);
						addElement(EType.Rule, false, false, "action", 12);
					completePathSpec();

					createPathSpec("importStatement");
						addElement(EType.Rule, true, false, "delegateGrammars", 7);
						addElement(EType.Rule, true, false, "id", 65);
					completePathSpec();

					createPathSpec("channelsStatement");
						addElement(EType.Rule, true, false, "channelsSpec", 9);
						addElement(EType.Rule, true, false, "id", 65);
					completePathSpec();

					createPathSpec("optionStatement");
						addElement(EType.Rule, true, false, "prequelConstruct", 3);
						addElement(EType.Rule, false, false, "optionsSpec", 4);
						addElement(EType.Rule, false, false, "option", 5);
					completePathSpec();

					createPathSpec("tokenStatement");
						addElement(EType.Rule, true, false, "tokensSpec", 8);
						addElement(EType.Rule, true, false, "id", 65);
					completePathSpec();

					createPathSpec("channelsBlock");
						addElement(EType.Rule, true, false, "channelsSpec", 9);
					completePathSpec();

					createPathSpec("optionsBlock");
						addElement(EType.Rule, true, false, "prequelConstruct", 3);
						addElement(EType.Rule, false, false, "optionsSpec", 4);
					completePathSpec();

					createPathSpec("tokensBlock");
						addElement(EType.Rule, true, false, "tokensSpec", 8);
					completePathSpec();

					createPathSpec("ruleBlock");
						addElement(EType.Rule, true, false, "block", 59);
					completePathSpec();

					createPathSpec("lexerBlock");
						addElement(EType.Rule, true, false, "lexerBlock", 41);
					completePathSpec();

					createPathSpec("parserRule");
						addElement(EType.Rule, true, false, "parserRuleSpec", 18);
					completePathSpec();

					createPathSpec("lexerRule");
						addElement(EType.Rule, true, false, "lexerRuleSpec", 33);
					completePathSpec();

					createPathSpec("fragmentRule");
						addElement(EType.Rule, true, false, "fragmentRuleSpec", 34);
					completePathSpec();

					createPathSpec("modeRule");
						addElement(EType.Rule, true, false, "modeRuleSpec", 32);
					completePathSpec();

					createPathSpec("cmdExpr");
						addElement(EType.Rule, true, false, "lexerCommandExpr", 45);
					completePathSpec();

					createPathSpec("atomRef");
						addElement(EType.Rule, true, false, "atom", 54);
					completePathSpec();

					createPathSpec("lexerAtomRef");
						addElement(EType.Rule, true, false, "lexerAtom", 53);
					completePathSpec();

					createPathSpec("ruleRef");
						addElement(EType.Rule, true, false, "ruleref", 60);
					completePathSpec();

					createPathSpec("labelRef");
						addElement(EType.Rule, true, false, "labeledElement", 49);
						addElement(EType.Rule, false, false, "id", 65);
					completePathSpec();

					createPathSpec("terminalRef");
						addElement(EType.Rule, true, false, "terminal", 62);
					completePathSpec();

					createPathSpec("rangeRef");
						addElement(EType.Rule, true, false, "range", 61);
					completePathSpec();

					createPathSpec("setRef");
						addElement(EType.Rule, true, false, "setElement", 57);
					completePathSpec();
	}


	@Override
	public void executeActionBlock(String name) {
		switch (name) {
					case "grammarSpec":
						grammarSpec();
						break;
					case "declaration":
						declaration();
						break;
					case "atAction":
						atAction();
						break;
					case "importStatement":
						importStatement();
						break;
					case "channelsStatement":
						channelsStatement();
						break;
					case "optionStatement":
						optionStatement();
						break;
					case "tokenStatement":
						tokenStatement();
						break;
					case "channelsBlock":
						channelsBlock();
						break;
					case "optionsBlock":
						optionsBlock();
						break;
					case "tokensBlock":
						tokensBlock();
						break;
					case "ruleBlock":
						ruleBlock();
						break;
					case "lexerBlock":
						lexerBlock();
						break;
					case "parserRule":
						parserRule();
						break;
					case "lexerRule":
						lexerRule();
						break;
					case "fragmentRule":
						fragmentRule();
						break;
					case "modeRule":
						modeRule();
						break;
					case "cmdExpr":
						cmdExpr();
						break;
					case "atomRef":
						atomRef();
						break;
					case "lexerAtomRef":
						lexerAtomRef();
						break;
					case "ruleRef":
						ruleRef();
						break;
					case "labelRef":
						labelRef();
						break;
					case "terminalRef":
						terminalRef();
						break;
					case "rangeRef":
						rangeRef();
						break;
					case "setRef":
						setRef();
						break;
		}
	}

	private void grammarSpec() {
		if (entering()) { doModule(); }
	}

	private void declaration() {
		if (entering()) { doDeclaration(); }
	}

	private void atAction() {
		if (entering()) { doAction(); }
	}

	private void importStatement() {
		if (entering()) { doImportStatement(); }
	}

	private void channelsStatement() {
		if (entering()) { doChannelsStatement(); }
	}

	private void optionStatement() {
		if (entering()) { doOptionStatement(); }
	}

	private void tokenStatement() {
		if (entering()) { doTokenStatement(); }
	}

	private void channelsBlock() {
		if (entering()) { doChannelsBlock(); }
		if (exiting()) {  doEndBlock(); }
	}

	private void optionsBlock() {
		if (entering()) { doOptionsBlock(); }
		if (exiting()) {  doEndBlock(); }
	}

	private void tokensBlock() {
		if (entering()) { doTokensBlock(); }
		if (exiting()) {  doEndBlock(); }
	}

	private void ruleBlock() {
		if (entering()) { doBegBlock(); }
		if (exiting()) {  doEndBlock(); }
	}

	private void lexerBlock() {
		if (entering()) { doBegBlock(); }
		if (exiting()) {  doEndBlock(); }
	}

	private void parserRule() {
		if (entering()) { begParserRule(); }
		if (exiting()) {  endParserRule(); }
	}

	private void lexerRule() {
		if (entering()) { begLexerRule(); }
		if (exiting()) {  endLexerRule(); }
	}

	private void fragmentRule() {
		if (entering()) { begFragmentRule(); }
		if (exiting()) {  endFragmentRule(); }
	}

	private void modeRule() {
		if (entering()) { begModeRule(); }
		if (exiting()) {  endModeRule(); }
	}

	private void cmdExpr() {
		if (entering()) { doCommandExpr(); }
	}

	private void atomRef() {
		if (entering()) { doAtomRef(); }
	}

	private void lexerAtomRef() {
		if (entering()) { doLexerAtomRef(); }
	}

	private void ruleRef() {
		if (entering()) { doRuleRef(); }
	}

	private void labelRef() {
		if (entering()) { doLabelRef(); }
	}

	private void terminalRef() {
		if (entering()) { doTerminalRef(); }
	}

	private void rangeRef() {
		if (entering()) { doRangeRef(); }
	}

	private void setRef() {
		if (entering()) { doSetRef(); }
	}



}
