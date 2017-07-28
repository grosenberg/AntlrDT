//
// Generated from D:/DevFiles/Eclipse/Dsl/antlrdt/net.certiv.antlrdt.core/src/main/java/net/certiv/antlrdt/core/parser/Paths.xv
// by XVisitor 4.5.3
//
package net.certiv.antlrdt.core.parser.gen;

	import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlr.runtime.xvisitor.xpath.EType;
import net.certiv.antlrdt.core.parser.PathAdaptor;

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
		"grammarSpec", "grammarType", "prequelConstruct", "optionsSpec", "option", 
		"optionValue", "delegateGrammars", "delegateElement", "delegate", "tokensSpec", 
		"channelsSpec", "idList", "idListElement", "action", "actionScopeName", 
		"actionBlock", "argActionBlock", "modeSpec", "rules", "ruleSpec", "parserRuleSpec", 
		"exceptionGroup", "exceptionHandler", "finallyClause", "rulePrequel", 
		"ruleReturns", "throwsSpec", "localsSpec", "ruleAction", "ruleModifiers", 
		"ruleModifier", "ruleBlock", "ruleAltList", "labeledAlt", "lexerRuleSpec", 
		"lexerRuleBlock", "lexerAltList", "lexerAlt", "lexerElements", "lexerElement", 
		"labeledLexerElement", "lexerBlock", "lexerCommands", "lexerCommand", 
		"lexerCommandName", "lexerCommandExpr", "altList", "alternative", "element", 
		"labeledElement", "ebnf", "blockSuffix", "ebnfSuffix", "lexerAtom", "atom", 
		"notSet", "blockSet", "setElement", "block", "ruleref", "range", "terminal", 
		"elementOptions", "elementOption", "id"
	};

	public static final int
		grammarSpec = 1000, grammarType = 1001, prequelConstruct = 1002, optionsSpec = 1003, 
		option = 1004, optionValue = 1005, delegateGrammars = 1006, delegateElement = 1007, 
		delegate = 1008, tokensSpec = 1009, channelsSpec = 1010, idList = 1011, 
		idListElement = 1012, action = 1013, actionScopeName = 1014, actionBlock = 1015, 
		argActionBlock = 1016, modeSpec = 1017, rules = 1018, ruleSpec = 1019, 
		parserRuleSpec = 1020, exceptionGroup = 1021, exceptionHandler = 1022, 
		finallyClause = 1023, rulePrequel = 1024, ruleReturns = 1025, throwsSpec = 1026, 
		localsSpec = 1027, ruleAction = 1028, ruleModifiers = 1029, ruleModifier = 1030, 
		ruleBlock = 1031, ruleAltList = 1032, labeledAlt = 1033, lexerRuleSpec = 1034, 
		lexerRuleBlock = 1035, lexerAltList = 1036, lexerAlt = 1037, lexerElements = 1038, 
		lexerElement = 1039, labeledLexerElement = 1040, lexerBlock = 1041, lexerCommands = 1042, 
		lexerCommand = 1043, lexerCommandName = 1044, lexerCommandExpr = 1045, 
		altList = 1046, alternative = 1047, element = 1048, labeledElement = 1049, 
		ebnf = 1050, blockSuffix = 1051, ebnfSuffix = 1052, lexerAtom = 1053, 
		atom = 1054, notSet = 1055, blockSet = 1056, setElement = 1057, block = 1058, 
		ruleref = 1059, range = 1060, terminal = 1061, elementOptions = 1062, 
		elementOption = 1063, id = 1064;

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

					createPathSpec("parserRuleRef");
						addElement(EType.Rule, true, false, "parserRuleSpec", 20); 
						addElement(EType.Rule, true, false, "ruleref", 59); 
					completePathSpec(); 

					createPathSpec("parserTokenRef");
						addElement(EType.Rule, true, false, "parserRuleSpec", 20); 
						addElement(EType.Rule, true, false, "terminal", 61); 
					completePathSpec(); 

					createPathSpec("lexerTokenRef");
						addElement(EType.Rule, true, false, "lexerRuleSpec", 34); 
						addElement(EType.Rule, true, false, "terminal", 61); 
					completePathSpec(); 
	}


	@Override
	public void executeActionBlock(String name) {
		switch (name) {
					case "parserRuleRef":
						parserRuleRef();
						break;
					case "parserTokenRef":
						parserTokenRef();
						break;
					case "lexerTokenRef":
						lexerTokenRef();
						break;
		}
	}

	private void parserRuleRef() {
		if (entering()) { parserRef(getToken(AntlrDT4Parser.RULE_REF, 0)); }
	}

	private void parserTokenRef() {
		if (entering()) { parserRef(getToken(AntlrDT4Parser.TOKEN_REF, 0)); }
	}

	private void lexerTokenRef() {
		if (entering()) { lexerRef(getToken(AntlrDT4Parser.TOKEN_REF, 0)); }
	}



}