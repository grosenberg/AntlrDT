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
// Generated from D:/DevFiles/Eclipse/Tools/Editors/net.certiv.antlr.dt/net.certiv.antlr.dt.core/src/main/java/net/certiv/antlr/dt/core/parser/AntlrDT4Parser.g4 by ANTLR 4.8

	package net.certiv.antlr.dt.core.parser.gen;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AntlrDT4Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.8", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		TOKEN_REF=1, RULE_REF=2, LEXER_CHAR_SET=3, DOC_COMMENT=4, BLOCK_COMMENT=5, 
		LINE_COMMENT=6, INT=7, STRING_LITERAL=8, UNTERMINATED_STRING_LITERAL=9, 
		BEGIN_ARGUMENT=10, BEGIN_ACTION=11, OPTIONS=12, TOKENS=13, CHANNELS=14, 
		IMPORT=15, FRAGMENT=16, LEXER=17, PARSER=18, XVISITOR=19, GRAMMAR=20, 
		PROTECTED=21, PUBLIC=22, PRIVATE=23, RETURNS=24, LOCALS=25, THROWS=26, 
		CATCH=27, FINALLY=28, MODE=29, COLON=30, COLONCOLON=31, COMMA=32, SEMI=33, 
		LPAREN=34, RPAREN=35, LBRACE=36, RBRACE=37, RARROW=38, LT=39, GT=40, ASSIGN=41, 
		QUESTION=42, STAR=43, PLUS_ASSIGN=44, PLUS=45, OR=46, DOLLAR=47, RANGE=48, 
		DOT=49, AT=50, POUND=51, NOT=52, ID=53, HORZ_WS=54, VERT_WS=55, END_ARGUMENT=56, 
		UNTERMINATED_ARGUMENT=57, ARGUMENT_CONTENT=58, END_ACTION=59, UNTERMINATED_ACTION=60, 
		ACTION_CONTENT=61, UNTERMINATED_CHAR_SET=62;
	public static final int
		RULE_grammarSpec = 0, RULE_declaration = 1, RULE_grammarType = 2, RULE_prequelConstruct = 3, 
		RULE_optionsSpec = 4, RULE_option = 5, RULE_optionValue = 6, RULE_delegateGrammars = 7, 
		RULE_tokensSpec = 8, RULE_channelsSpec = 9, RULE_idList = 10, RULE_idListElement = 11, 
		RULE_action = 12, RULE_actionScopeName = 13, RULE_actionBlock = 14, RULE_argActionBlock = 15, 
		RULE_rules = 16, RULE_ruleSpec = 17, RULE_parserRuleSpec = 18, RULE_exceptionGroup = 19, 
		RULE_exceptionHandler = 20, RULE_finallyClause = 21, RULE_rulePrequel = 22, 
		RULE_ruleReturns = 23, RULE_throwsSpec = 24, RULE_localsSpec = 25, RULE_ruleAction = 26, 
		RULE_ruleModifiers = 27, RULE_ruleModifier = 28, RULE_ruleBlock = 29, 
		RULE_ruleAltList = 30, RULE_labeledAlt = 31, RULE_modeRuleSpec = 32, RULE_lexerRuleSpec = 33, 
		RULE_fragmentRuleSpec = 34, RULE_lexerRuleBlock = 35, RULE_lexerAltList = 36, 
		RULE_lexerAlt = 37, RULE_lexerElements = 38, RULE_lexerElement = 39, RULE_labeledLexerElement = 40, 
		RULE_lexerBlock = 41, RULE_lexerCommands = 42, RULE_lexerCommand = 43, 
		RULE_lexerCommandName = 44, RULE_lexerCommandExpr = 45, RULE_altList = 46, 
		RULE_alternative = 47, RULE_element = 48, RULE_labeledElement = 49, RULE_ebnf = 50, 
		RULE_blockSuffix = 51, RULE_ebnfSuffix = 52, RULE_lexerAtom = 53, RULE_atom = 54, 
		RULE_notSet = 55, RULE_blockSet = 56, RULE_setElement = 57, RULE_charSet = 58, 
		RULE_block = 59, RULE_ruleref = 60, RULE_range = 61, RULE_terminal = 62, 
		RULE_elementOptions = 63, RULE_elementOption = 64, RULE_id = 65;
	private static String[] makeRuleNames() {
		return new String[] {
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
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "TOKEN_REF", "RULE_REF", "LEXER_CHAR_SET", "DOC_COMMENT", "BLOCK_COMMENT", 
			"LINE_COMMENT", "INT", "STRING_LITERAL", "UNTERMINATED_STRING_LITERAL", 
			"BEGIN_ARGUMENT", "BEGIN_ACTION", "OPTIONS", "TOKENS", "CHANNELS", "IMPORT", 
			"FRAGMENT", "LEXER", "PARSER", "XVISITOR", "GRAMMAR", "PROTECTED", "PUBLIC", 
			"PRIVATE", "RETURNS", "LOCALS", "THROWS", "CATCH", "FINALLY", "MODE", 
			"COLON", "COLONCOLON", "COMMA", "SEMI", "LPAREN", "RPAREN", "LBRACE", 
			"RBRACE", "RARROW", "LT", "GT", "ASSIGN", "QUESTION", "STAR", "PLUS_ASSIGN", 
			"PLUS", "OR", "DOLLAR", "RANGE", "DOT", "AT", "POUND", "NOT", "ID", "HORZ_WS", 
			"VERT_WS", "END_ARGUMENT", "UNTERMINATED_ARGUMENT", "ARGUMENT_CONTENT", 
			"END_ACTION", "UNTERMINATED_ACTION", "ACTION_CONTENT", "UNTERMINATED_CHAR_SET"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "AntlrDT4Parser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public AntlrDT4Parser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	public static class GrammarSpecContext extends ParserRuleContext {
		public DeclarationContext declaration() {
			return getRuleContext(DeclarationContext.class,0);
		}
		public RulesContext rules() {
			return getRuleContext(RulesContext.class,0);
		}
		public TerminalNode EOF() { return getToken(AntlrDT4Parser.EOF, 0); }
		public List<PrequelConstructContext> prequelConstruct() {
			return getRuleContexts(PrequelConstructContext.class);
		}
		public PrequelConstructContext prequelConstruct(int i) {
			return getRuleContext(PrequelConstructContext.class,i);
		}
		public GrammarSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterGrammarSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitGrammarSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitGrammarSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarSpecContext grammarSpec() throws RecognitionException {
		GrammarSpecContext _localctx = new GrammarSpecContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_grammarSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(132);
			declaration();
			setState(136);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << AT))) != 0)) {
				{
				{
				setState(133);
				prequelConstruct();
				}
				}
				setState(138);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(139);
			rules();
			setState(140);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DeclarationContext extends ParserRuleContext {
		public GrammarTypeContext grammarType() {
			return getRuleContext(GrammarTypeContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public DeclarationContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_declaration; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterDeclaration(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitDeclaration(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitDeclaration(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DeclarationContext declaration() throws RecognitionException {
		DeclarationContext _localctx = new DeclarationContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_declaration);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(142);
			grammarType();
			setState(143);
			id();
			setState(144);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GrammarTypeContext extends ParserRuleContext {
		public TerminalNode LEXER() { return getToken(AntlrDT4Parser.LEXER, 0); }
		public TerminalNode GRAMMAR() { return getToken(AntlrDT4Parser.GRAMMAR, 0); }
		public TerminalNode PARSER() { return getToken(AntlrDT4Parser.PARSER, 0); }
		public GrammarTypeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_grammarType; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterGrammarType(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitGrammarType(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitGrammarType(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GrammarTypeContext grammarType() throws RecognitionException {
		GrammarTypeContext _localctx = new GrammarTypeContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_grammarType);
		try {
			setState(151);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case LEXER:
				enterOuterAlt(_localctx, 1);
				{
				setState(146);
				match(LEXER);
				setState(147);
				match(GRAMMAR);
				}
				break;
			case PARSER:
				enterOuterAlt(_localctx, 2);
				{
				setState(148);
				match(PARSER);
				setState(149);
				match(GRAMMAR);
				}
				break;
			case GRAMMAR:
				enterOuterAlt(_localctx, 3);
				{
				setState(150);
				match(GRAMMAR);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PrequelConstructContext extends ParserRuleContext {
		public OptionsSpecContext optionsSpec() {
			return getRuleContext(OptionsSpecContext.class,0);
		}
		public DelegateGrammarsContext delegateGrammars() {
			return getRuleContext(DelegateGrammarsContext.class,0);
		}
		public TokensSpecContext tokensSpec() {
			return getRuleContext(TokensSpecContext.class,0);
		}
		public ChannelsSpecContext channelsSpec() {
			return getRuleContext(ChannelsSpecContext.class,0);
		}
		public ActionContext action() {
			return getRuleContext(ActionContext.class,0);
		}
		public PrequelConstructContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_prequelConstruct; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterPrequelConstruct(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitPrequelConstruct(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitPrequelConstruct(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrequelConstructContext prequelConstruct() throws RecognitionException {
		PrequelConstructContext _localctx = new PrequelConstructContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_prequelConstruct);
		try {
			setState(158);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OPTIONS:
				enterOuterAlt(_localctx, 1);
				{
				setState(153);
				optionsSpec();
				}
				break;
			case IMPORT:
				enterOuterAlt(_localctx, 2);
				{
				setState(154);
				delegateGrammars();
				}
				break;
			case TOKENS:
				enterOuterAlt(_localctx, 3);
				{
				setState(155);
				tokensSpec();
				}
				break;
			case CHANNELS:
				enterOuterAlt(_localctx, 4);
				{
				setState(156);
				channelsSpec();
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 5);
				{
				setState(157);
				action();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionsSpecContext extends ParserRuleContext {
		public TerminalNode OPTIONS() { return getToken(AntlrDT4Parser.OPTIONS, 0); }
		public TerminalNode LBRACE() { return getToken(AntlrDT4Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(AntlrDT4Parser.RBRACE, 0); }
		public List<OptionContext> option() {
			return getRuleContexts(OptionContext.class);
		}
		public OptionContext option(int i) {
			return getRuleContext(OptionContext.class,i);
		}
		public OptionsSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optionsSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterOptionsSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitOptionsSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitOptionsSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionsSpecContext optionsSpec() throws RecognitionException {
		OptionsSpecContext _localctx = new OptionsSpecContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_optionsSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(160);
			match(OPTIONS);
			setState(161);
			match(LBRACE);
			setState(165);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TOKEN_REF || _la==RULE_REF) {
				{
				{
				setState(162);
				option();
				}
				}
				setState(167);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(168);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(AntlrDT4Parser.ASSIGN, 0); }
		public OptionValueContext optionValue() {
			return getRuleContext(OptionValueContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public OptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_option; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionContext option() throws RecognitionException {
		OptionContext _localctx = new OptionContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_option);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			id();
			setState(171);
			match(ASSIGN);
			setState(172);
			optionValue();
			setState(173);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OptionValueContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TerminalNode> DOT() { return getTokens(AntlrDT4Parser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(AntlrDT4Parser.DOT, i);
		}
		public TerminalNode STRING_LITERAL() { return getToken(AntlrDT4Parser.STRING_LITERAL, 0); }
		public ActionBlockContext actionBlock() {
			return getRuleContext(ActionBlockContext.class,0);
		}
		public TerminalNode INT() { return getToken(AntlrDT4Parser.INT, 0); }
		public OptionValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_optionValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterOptionValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitOptionValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitOptionValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final OptionValueContext optionValue() throws RecognitionException {
		OptionValueContext _localctx = new OptionValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_optionValue);
		int _la;
		try {
			setState(186);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(175);
				id();
				setState(180);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(176);
					match(DOT);
					setState(177);
					id();
					}
					}
					setState(182);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(183);
				match(STRING_LITERAL);
				}
				break;
			case BEGIN_ACTION:
				enterOuterAlt(_localctx, 3);
				{
				setState(184);
				actionBlock();
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 4);
				{
				setState(185);
				match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class DelegateGrammarsContext extends ParserRuleContext {
		public TerminalNode IMPORT() { return getToken(AntlrDT4Parser.IMPORT, 0); }
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public IdListContext idList() {
			return getRuleContext(IdListContext.class,0);
		}
		public DelegateGrammarsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delegateGrammars; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterDelegateGrammars(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitDelegateGrammars(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitDelegateGrammars(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DelegateGrammarsContext delegateGrammars() throws RecognitionException {
		DelegateGrammarsContext _localctx = new DelegateGrammarsContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_delegateGrammars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(188);
			match(IMPORT);
			setState(190);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TOKEN_REF || _la==RULE_REF) {
				{
				setState(189);
				idList();
				}
			}

			setState(192);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TokensSpecContext extends ParserRuleContext {
		public TerminalNode TOKENS() { return getToken(AntlrDT4Parser.TOKENS, 0); }
		public TerminalNode LBRACE() { return getToken(AntlrDT4Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(AntlrDT4Parser.RBRACE, 0); }
		public IdListContext idList() {
			return getRuleContext(IdListContext.class,0);
		}
		public TokensSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_tokensSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterTokensSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitTokensSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitTokensSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TokensSpecContext tokensSpec() throws RecognitionException {
		TokensSpecContext _localctx = new TokensSpecContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_tokensSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(194);
			match(TOKENS);
			setState(195);
			match(LBRACE);
			setState(197);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TOKEN_REF || _la==RULE_REF) {
				{
				setState(196);
				idList();
				}
			}

			setState(199);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ChannelsSpecContext extends ParserRuleContext {
		public TerminalNode CHANNELS() { return getToken(AntlrDT4Parser.CHANNELS, 0); }
		public TerminalNode LBRACE() { return getToken(AntlrDT4Parser.LBRACE, 0); }
		public TerminalNode RBRACE() { return getToken(AntlrDT4Parser.RBRACE, 0); }
		public IdListContext idList() {
			return getRuleContext(IdListContext.class,0);
		}
		public ChannelsSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_channelsSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterChannelsSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitChannelsSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitChannelsSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ChannelsSpecContext channelsSpec() throws RecognitionException {
		ChannelsSpecContext _localctx = new ChannelsSpecContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_channelsSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201);
			match(CHANNELS);
			setState(202);
			match(LBRACE);
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TOKEN_REF || _la==RULE_REF) {
				{
				setState(203);
				idList();
				}
			}

			setState(206);
			match(RBRACE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdListContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public List<IdListElementContext> idListElement() {
			return getRuleContexts(IdListElementContext.class);
		}
		public IdListElementContext idListElement(int i) {
			return getRuleContext(IdListElementContext.class,i);
		}
		public TerminalNode COMMA() { return getToken(AntlrDT4Parser.COMMA, 0); }
		public IdListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterIdList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitIdList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitIdList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdListContext idList() throws RecognitionException {
		IdListContext _localctx = new IdListContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_idList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(208);
			id();
			setState(212);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(209);
					idListElement();
					}
					} 
				}
				setState(214);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,9,_ctx);
			}
			setState(216);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(215);
				match(COMMA);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdListElementContext extends ParserRuleContext {
		public TerminalNode COMMA() { return getToken(AntlrDT4Parser.COMMA, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public IdListElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_idListElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterIdListElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitIdListElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitIdListElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdListElementContext idListElement() throws RecognitionException {
		IdListElementContext _localctx = new IdListElementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_idListElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(218);
			match(COMMA);
			setState(219);
			id();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(AntlrDT4Parser.AT, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ActionBlockContext actionBlock() {
			return getRuleContext(ActionBlockContext.class,0);
		}
		public ActionScopeNameContext actionScopeName() {
			return getRuleContext(ActionScopeNameContext.class,0);
		}
		public TerminalNode COLONCOLON() { return getToken(AntlrDT4Parser.COLONCOLON, 0); }
		public ActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_action; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionContext action() throws RecognitionException {
		ActionContext _localctx = new ActionContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_action);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(221);
			match(AT);
			setState(225);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,11,_ctx) ) {
			case 1:
				{
				setState(222);
				actionScopeName();
				setState(223);
				match(COLONCOLON);
				}
				break;
			}
			setState(227);
			id();
			setState(228);
			actionBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionScopeNameContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode LEXER() { return getToken(AntlrDT4Parser.LEXER, 0); }
		public TerminalNode PARSER() { return getToken(AntlrDT4Parser.PARSER, 0); }
		public ActionScopeNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionScopeName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterActionScopeName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitActionScopeName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitActionScopeName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionScopeNameContext actionScopeName() throws RecognitionException {
		ActionScopeNameContext _localctx = new ActionScopeNameContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_actionScopeName);
		try {
			setState(233);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(230);
				id();
				}
				break;
			case LEXER:
				enterOuterAlt(_localctx, 2);
				{
				setState(231);
				match(LEXER);
				}
				break;
			case PARSER:
				enterOuterAlt(_localctx, 3);
				{
				setState(232);
				match(PARSER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionBlockContext extends ParserRuleContext {
		public TerminalNode BEGIN_ACTION() { return getToken(AntlrDT4Parser.BEGIN_ACTION, 0); }
		public TerminalNode END_ACTION() { return getToken(AntlrDT4Parser.END_ACTION, 0); }
		public List<TerminalNode> ACTION_CONTENT() { return getTokens(AntlrDT4Parser.ACTION_CONTENT); }
		public TerminalNode ACTION_CONTENT(int i) {
			return getToken(AntlrDT4Parser.ACTION_CONTENT, i);
		}
		public ActionBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterActionBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitActionBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitActionBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ActionBlockContext actionBlock() throws RecognitionException {
		ActionBlockContext _localctx = new ActionBlockContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_actionBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(235);
			match(BEGIN_ACTION);
			setState(239);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ACTION_CONTENT) {
				{
				{
				setState(236);
				match(ACTION_CONTENT);
				}
				}
				setState(241);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(242);
			match(END_ACTION);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ArgActionBlockContext extends ParserRuleContext {
		public TerminalNode BEGIN_ARGUMENT() { return getToken(AntlrDT4Parser.BEGIN_ARGUMENT, 0); }
		public TerminalNode END_ARGUMENT() { return getToken(AntlrDT4Parser.END_ARGUMENT, 0); }
		public List<TerminalNode> ARGUMENT_CONTENT() { return getTokens(AntlrDT4Parser.ARGUMENT_CONTENT); }
		public TerminalNode ARGUMENT_CONTENT(int i) {
			return getToken(AntlrDT4Parser.ARGUMENT_CONTENT, i);
		}
		public ArgActionBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_argActionBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterArgActionBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitArgActionBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitArgActionBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgActionBlockContext argActionBlock() throws RecognitionException {
		ArgActionBlockContext _localctx = new ArgActionBlockContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_argActionBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(244);
			match(BEGIN_ARGUMENT);
			setState(248);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ARGUMENT_CONTENT) {
				{
				{
				setState(245);
				match(ARGUMENT_CONTENT);
				}
				}
				setState(250);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(251);
			match(END_ARGUMENT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RulesContext extends ParserRuleContext {
		public List<RuleSpecContext> ruleSpec() {
			return getRuleContexts(RuleSpecContext.class);
		}
		public RuleSpecContext ruleSpec(int i) {
			return getRuleContext(RuleSpecContext.class,i);
		}
		public RulesContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rules; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRules(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRules(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRules(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulesContext rules() throws RecognitionException {
		RulesContext _localctx = new RulesContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_rules);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TOKEN_REF) | (1L << RULE_REF) | (1L << FRAGMENT) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE) | (1L << MODE))) != 0)) {
				{
				{
				setState(253);
				ruleSpec();
				}
				}
				setState(258);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleSpecContext extends ParserRuleContext {
		public ParserRuleSpecContext parserRuleSpec() {
			return getRuleContext(ParserRuleSpecContext.class,0);
		}
		public LexerRuleSpecContext lexerRuleSpec() {
			return getRuleContext(LexerRuleSpecContext.class,0);
		}
		public ModeRuleSpecContext modeRuleSpec() {
			return getRuleContext(ModeRuleSpecContext.class,0);
		}
		public FragmentRuleSpecContext fragmentRuleSpec() {
			return getRuleContext(FragmentRuleSpecContext.class,0);
		}
		public RuleSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRuleSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRuleSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRuleSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleSpecContext ruleSpec() throws RecognitionException {
		RuleSpecContext _localctx = new RuleSpecContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_ruleSpec);
		try {
			setState(263);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case RULE_REF:
			case PROTECTED:
			case PUBLIC:
			case PRIVATE:
				enterOuterAlt(_localctx, 1);
				{
				setState(259);
				parserRuleSpec();
				}
				break;
			case TOKEN_REF:
				enterOuterAlt(_localctx, 2);
				{
				setState(260);
				lexerRuleSpec();
				}
				break;
			case MODE:
				enterOuterAlt(_localctx, 3);
				{
				setState(261);
				modeRuleSpec();
				}
				break;
			case FRAGMENT:
				enterOuterAlt(_localctx, 4);
				{
				setState(262);
				fragmentRuleSpec();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ParserRuleSpecContext extends ParserRuleContext {
		public TerminalNode RULE_REF() { return getToken(AntlrDT4Parser.RULE_REF, 0); }
		public TerminalNode COLON() { return getToken(AntlrDT4Parser.COLON, 0); }
		public RuleBlockContext ruleBlock() {
			return getRuleContext(RuleBlockContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public ExceptionGroupContext exceptionGroup() {
			return getRuleContext(ExceptionGroupContext.class,0);
		}
		public RuleModifiersContext ruleModifiers() {
			return getRuleContext(RuleModifiersContext.class,0);
		}
		public ArgActionBlockContext argActionBlock() {
			return getRuleContext(ArgActionBlockContext.class,0);
		}
		public RuleReturnsContext ruleReturns() {
			return getRuleContext(RuleReturnsContext.class,0);
		}
		public ThrowsSpecContext throwsSpec() {
			return getRuleContext(ThrowsSpecContext.class,0);
		}
		public LocalsSpecContext localsSpec() {
			return getRuleContext(LocalsSpecContext.class,0);
		}
		public List<RulePrequelContext> rulePrequel() {
			return getRuleContexts(RulePrequelContext.class);
		}
		public RulePrequelContext rulePrequel(int i) {
			return getRuleContext(RulePrequelContext.class,i);
		}
		public ParserRuleSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parserRuleSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterParserRuleSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitParserRuleSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitParserRuleSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParserRuleSpecContext parserRuleSpec() throws RecognitionException {
		ParserRuleSpecContext _localctx = new ParserRuleSpecContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_parserRuleSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(266);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				setState(265);
				ruleModifiers();
				}
			}

			setState(268);
			match(RULE_REF);
			setState(270);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BEGIN_ARGUMENT) {
				{
				setState(269);
				argActionBlock();
				}
			}

			setState(273);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==RETURNS) {
				{
				setState(272);
				ruleReturns();
				}
			}

			setState(276);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==THROWS) {
				{
				setState(275);
				throwsSpec();
				}
			}

			setState(279);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LOCALS) {
				{
				setState(278);
				localsSpec();
				}
			}

			setState(284);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OPTIONS || _la==AT) {
				{
				{
				setState(281);
				rulePrequel();
				}
				}
				setState(286);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(287);
			match(COLON);
			setState(288);
			ruleBlock();
			setState(289);
			match(SEMI);
			setState(290);
			exceptionGroup();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExceptionGroupContext extends ParserRuleContext {
		public List<ExceptionHandlerContext> exceptionHandler() {
			return getRuleContexts(ExceptionHandlerContext.class);
		}
		public ExceptionHandlerContext exceptionHandler(int i) {
			return getRuleContext(ExceptionHandlerContext.class,i);
		}
		public FinallyClauseContext finallyClause() {
			return getRuleContext(FinallyClauseContext.class,0);
		}
		public ExceptionGroupContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exceptionGroup; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterExceptionGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitExceptionGroup(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitExceptionGroup(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExceptionGroupContext exceptionGroup() throws RecognitionException {
		ExceptionGroupContext _localctx = new ExceptionGroupContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_exceptionGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(295);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CATCH) {
				{
				{
				setState(292);
				exceptionHandler();
				}
				}
				setState(297);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(299);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==FINALLY) {
				{
				setState(298);
				finallyClause();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ExceptionHandlerContext extends ParserRuleContext {
		public TerminalNode CATCH() { return getToken(AntlrDT4Parser.CATCH, 0); }
		public ArgActionBlockContext argActionBlock() {
			return getRuleContext(ArgActionBlockContext.class,0);
		}
		public ActionBlockContext actionBlock() {
			return getRuleContext(ActionBlockContext.class,0);
		}
		public ExceptionHandlerContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_exceptionHandler; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterExceptionHandler(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitExceptionHandler(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitExceptionHandler(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExceptionHandlerContext exceptionHandler() throws RecognitionException {
		ExceptionHandlerContext _localctx = new ExceptionHandlerContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_exceptionHandler);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(301);
			match(CATCH);
			setState(302);
			argActionBlock();
			setState(303);
			actionBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FinallyClauseContext extends ParserRuleContext {
		public TerminalNode FINALLY() { return getToken(AntlrDT4Parser.FINALLY, 0); }
		public ActionBlockContext actionBlock() {
			return getRuleContext(ActionBlockContext.class,0);
		}
		public FinallyClauseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_finallyClause; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterFinallyClause(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitFinallyClause(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitFinallyClause(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FinallyClauseContext finallyClause() throws RecognitionException {
		FinallyClauseContext _localctx = new FinallyClauseContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_finallyClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(305);
			match(FINALLY);
			setState(306);
			actionBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RulePrequelContext extends ParserRuleContext {
		public OptionsSpecContext optionsSpec() {
			return getRuleContext(OptionsSpecContext.class,0);
		}
		public RuleActionContext ruleAction() {
			return getRuleContext(RuleActionContext.class,0);
		}
		public RulePrequelContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rulePrequel; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRulePrequel(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRulePrequel(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRulePrequel(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulePrequelContext rulePrequel() throws RecognitionException {
		RulePrequelContext _localctx = new RulePrequelContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_rulePrequel);
		try {
			setState(310);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case OPTIONS:
				enterOuterAlt(_localctx, 1);
				{
				setState(308);
				optionsSpec();
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(309);
				ruleAction();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleReturnsContext extends ParserRuleContext {
		public TerminalNode RETURNS() { return getToken(AntlrDT4Parser.RETURNS, 0); }
		public ArgActionBlockContext argActionBlock() {
			return getRuleContext(ArgActionBlockContext.class,0);
		}
		public RuleReturnsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleReturns; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRuleReturns(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRuleReturns(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRuleReturns(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleReturnsContext ruleReturns() throws RecognitionException {
		RuleReturnsContext _localctx = new RuleReturnsContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_ruleReturns);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(312);
			match(RETURNS);
			setState(313);
			argActionBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ThrowsSpecContext extends ParserRuleContext {
		public TerminalNode THROWS() { return getToken(AntlrDT4Parser.THROWS, 0); }
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AntlrDT4Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AntlrDT4Parser.COMMA, i);
		}
		public ThrowsSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_throwsSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterThrowsSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitThrowsSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitThrowsSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThrowsSpecContext throwsSpec() throws RecognitionException {
		ThrowsSpecContext _localctx = new ThrowsSpecContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_throwsSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(315);
			match(THROWS);
			setState(316);
			id();
			setState(321);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(317);
				match(COMMA);
				setState(318);
				id();
				}
				}
				setState(323);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LocalsSpecContext extends ParserRuleContext {
		public TerminalNode LOCALS() { return getToken(AntlrDT4Parser.LOCALS, 0); }
		public ArgActionBlockContext argActionBlock() {
			return getRuleContext(ArgActionBlockContext.class,0);
		}
		public LocalsSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_localsSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLocalsSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLocalsSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLocalsSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LocalsSpecContext localsSpec() throws RecognitionException {
		LocalsSpecContext _localctx = new LocalsSpecContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_localsSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(324);
			match(LOCALS);
			setState(325);
			argActionBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleActionContext extends ParserRuleContext {
		public TerminalNode AT() { return getToken(AntlrDT4Parser.AT, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public ActionBlockContext actionBlock() {
			return getRuleContext(ActionBlockContext.class,0);
		}
		public RuleActionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleAction; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRuleAction(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRuleAction(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRuleAction(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleActionContext ruleAction() throws RecognitionException {
		RuleActionContext _localctx = new RuleActionContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_ruleAction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(327);
			match(AT);
			setState(328);
			id();
			setState(329);
			actionBlock();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleModifiersContext extends ParserRuleContext {
		public List<RuleModifierContext> ruleModifier() {
			return getRuleContexts(RuleModifierContext.class);
		}
		public RuleModifierContext ruleModifier(int i) {
			return getRuleContext(RuleModifierContext.class,i);
		}
		public RuleModifiersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleModifiers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRuleModifiers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRuleModifiers(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRuleModifiers(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleModifiersContext ruleModifiers() throws RecognitionException {
		RuleModifiersContext _localctx = new RuleModifiersContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_ruleModifiers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(331);
				ruleModifier();
				}
				}
				setState(334); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleModifierContext extends ParserRuleContext {
		public TerminalNode PUBLIC() { return getToken(AntlrDT4Parser.PUBLIC, 0); }
		public TerminalNode PRIVATE() { return getToken(AntlrDT4Parser.PRIVATE, 0); }
		public TerminalNode PROTECTED() { return getToken(AntlrDT4Parser.PROTECTED, 0); }
		public RuleModifierContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleModifier; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRuleModifier(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRuleModifier(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRuleModifier(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleModifierContext ruleModifier() throws RecognitionException {
		RuleModifierContext _localctx = new RuleModifierContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_ruleModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleBlockContext extends ParserRuleContext {
		public RuleAltListContext ruleAltList() {
			return getRuleContext(RuleAltListContext.class,0);
		}
		public RuleBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRuleBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRuleBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRuleBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleBlockContext ruleBlock() throws RecognitionException {
		RuleBlockContext _localctx = new RuleBlockContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_ruleBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(338);
			ruleAltList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RuleAltListContext extends ParserRuleContext {
		public List<LabeledAltContext> labeledAlt() {
			return getRuleContexts(LabeledAltContext.class);
		}
		public LabeledAltContext labeledAlt(int i) {
			return getRuleContext(LabeledAltContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(AntlrDT4Parser.OR); }
		public TerminalNode OR(int i) {
			return getToken(AntlrDT4Parser.OR, i);
		}
		public RuleAltListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleAltList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRuleAltList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRuleAltList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRuleAltList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RuleAltListContext ruleAltList() throws RecognitionException {
		RuleAltListContext _localctx = new RuleAltListContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_ruleAltList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(340);
			labeledAlt();
			setState(345);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(341);
				match(OR);
				setState(342);
				labeledAlt();
				}
				}
				setState(347);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabeledAltContext extends ParserRuleContext {
		public AlternativeContext alternative() {
			return getRuleContext(AlternativeContext.class,0);
		}
		public TerminalNode POUND() { return getToken(AntlrDT4Parser.POUND, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public LabeledAltContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labeledAlt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLabeledAlt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLabeledAlt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLabeledAlt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabeledAltContext labeledAlt() throws RecognitionException {
		LabeledAltContext _localctx = new LabeledAltContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_labeledAlt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(348);
			alternative();
			setState(351);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==POUND) {
				{
				setState(349);
				match(POUND);
				setState(350);
				id();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ModeRuleSpecContext extends ParserRuleContext {
		public TerminalNode MODE() { return getToken(AntlrDT4Parser.MODE, 0); }
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public List<LexerRuleSpecContext> lexerRuleSpec() {
			return getRuleContexts(LexerRuleSpecContext.class);
		}
		public LexerRuleSpecContext lexerRuleSpec(int i) {
			return getRuleContext(LexerRuleSpecContext.class,i);
		}
		public ModeRuleSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modeRuleSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterModeRuleSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitModeRuleSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitModeRuleSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModeRuleSpecContext modeRuleSpec() throws RecognitionException {
		ModeRuleSpecContext _localctx = new ModeRuleSpecContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_modeRuleSpec);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(353);
			match(MODE);
			setState(354);
			id();
			setState(355);
			match(SEMI);
			setState(357); 
			_errHandler.sync(this);
			_alt = 1;
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(356);
					lexerRuleSpec();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(359); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,30,_ctx);
			} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerRuleSpecContext extends ParserRuleContext {
		public TerminalNode TOKEN_REF() { return getToken(AntlrDT4Parser.TOKEN_REF, 0); }
		public TerminalNode COLON() { return getToken(AntlrDT4Parser.COLON, 0); }
		public LexerRuleBlockContext lexerRuleBlock() {
			return getRuleContext(LexerRuleBlockContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public LexerRuleSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerRuleSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerRuleSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerRuleSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerRuleSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerRuleSpecContext lexerRuleSpec() throws RecognitionException {
		LexerRuleSpecContext _localctx = new LexerRuleSpecContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_lexerRuleSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(361);
			match(TOKEN_REF);
			setState(362);
			match(COLON);
			setState(363);
			lexerRuleBlock();
			setState(364);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FragmentRuleSpecContext extends ParserRuleContext {
		public TerminalNode FRAGMENT() { return getToken(AntlrDT4Parser.FRAGMENT, 0); }
		public TerminalNode TOKEN_REF() { return getToken(AntlrDT4Parser.TOKEN_REF, 0); }
		public TerminalNode COLON() { return getToken(AntlrDT4Parser.COLON, 0); }
		public LexerRuleBlockContext lexerRuleBlock() {
			return getRuleContext(LexerRuleBlockContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public FragmentRuleSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fragmentRuleSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterFragmentRuleSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitFragmentRuleSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitFragmentRuleSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FragmentRuleSpecContext fragmentRuleSpec() throws RecognitionException {
		FragmentRuleSpecContext _localctx = new FragmentRuleSpecContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_fragmentRuleSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(366);
			match(FRAGMENT);
			setState(367);
			match(TOKEN_REF);
			setState(368);
			match(COLON);
			setState(369);
			lexerRuleBlock();
			setState(370);
			match(SEMI);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerRuleBlockContext extends ParserRuleContext {
		public LexerAltListContext lexerAltList() {
			return getRuleContext(LexerAltListContext.class,0);
		}
		public LexerRuleBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerRuleBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerRuleBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerRuleBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerRuleBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerRuleBlockContext lexerRuleBlock() throws RecognitionException {
		LexerRuleBlockContext _localctx = new LexerRuleBlockContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_lexerRuleBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(372);
			lexerAltList();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerAltListContext extends ParserRuleContext {
		public List<LexerAltContext> lexerAlt() {
			return getRuleContexts(LexerAltContext.class);
		}
		public LexerAltContext lexerAlt(int i) {
			return getRuleContext(LexerAltContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(AntlrDT4Parser.OR); }
		public TerminalNode OR(int i) {
			return getToken(AntlrDT4Parser.OR, i);
		}
		public LexerAltListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerAltList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerAltList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerAltList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerAltList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerAltListContext lexerAltList() throws RecognitionException {
		LexerAltListContext _localctx = new LexerAltListContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_lexerAltList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(374);
			lexerAlt();
			setState(379);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(375);
				match(OR);
				setState(376);
				lexerAlt();
				}
				}
				setState(381);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerAltContext extends ParserRuleContext {
		public LexerElementsContext lexerElements() {
			return getRuleContext(LexerElementsContext.class,0);
		}
		public LexerCommandsContext lexerCommands() {
			return getRuleContext(LexerCommandsContext.class,0);
		}
		public LexerAltContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerAlt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerAlt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerAlt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerAlt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerAltContext lexerAlt() throws RecognitionException {
		LexerAltContext _localctx = new LexerAltContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_lexerAlt);
		int _la;
		try {
			setState(387);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
			case LEXER_CHAR_SET:
			case STRING_LITERAL:
			case BEGIN_ACTION:
			case LPAREN:
			case DOT:
			case NOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(382);
				lexerElements();
				setState(384);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==RARROW) {
					{
					setState(383);
					lexerCommands();
					}
				}

				}
				break;
			case SEMI:
			case RPAREN:
			case OR:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerElementsContext extends ParserRuleContext {
		public List<LexerElementContext> lexerElement() {
			return getRuleContexts(LexerElementContext.class);
		}
		public LexerElementContext lexerElement(int i) {
			return getRuleContext(LexerElementContext.class,i);
		}
		public LexerElementsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerElements; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerElements(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerElements(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerElements(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerElementsContext lexerElements() throws RecognitionException {
		LexerElementsContext _localctx = new LexerElementsContext(_ctx, getState());
		enterRule(_localctx, 76, RULE_lexerElements);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(390); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(389);
				lexerElement();
				}
				}
				setState(392); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TOKEN_REF) | (1L << RULE_REF) | (1L << LEXER_CHAR_SET) | (1L << STRING_LITERAL) | (1L << BEGIN_ACTION) | (1L << LPAREN) | (1L << DOT) | (1L << NOT))) != 0) );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerElementContext extends ParserRuleContext {
		public LabeledLexerElementContext labeledLexerElement() {
			return getRuleContext(LabeledLexerElementContext.class,0);
		}
		public EbnfSuffixContext ebnfSuffix() {
			return getRuleContext(EbnfSuffixContext.class,0);
		}
		public LexerAtomContext lexerAtom() {
			return getRuleContext(LexerAtomContext.class,0);
		}
		public LexerBlockContext lexerBlock() {
			return getRuleContext(LexerBlockContext.class,0);
		}
		public ActionBlockContext actionBlock() {
			return getRuleContext(ActionBlockContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(AntlrDT4Parser.QUESTION, 0); }
		public LexerElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerElementContext lexerElement() throws RecognitionException {
		LexerElementContext _localctx = new LexerElementContext(_ctx, getState());
		enterRule(_localctx, 78, RULE_lexerElement);
		int _la;
		try {
			setState(410);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,39,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(394);
				labeledLexerElement();
				setState(396);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUESTION) | (1L << STAR) | (1L << PLUS))) != 0)) {
					{
					setState(395);
					ebnfSuffix();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(398);
				lexerAtom();
				setState(400);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUESTION) | (1L << STAR) | (1L << PLUS))) != 0)) {
					{
					setState(399);
					ebnfSuffix();
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(402);
				lexerBlock();
				setState(404);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUESTION) | (1L << STAR) | (1L << PLUS))) != 0)) {
					{
					setState(403);
					ebnfSuffix();
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(406);
				actionBlock();
				setState(408);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(407);
					match(QUESTION);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabeledLexerElementContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(AntlrDT4Parser.ASSIGN, 0); }
		public TerminalNode PLUS_ASSIGN() { return getToken(AntlrDT4Parser.PLUS_ASSIGN, 0); }
		public LexerAtomContext lexerAtom() {
			return getRuleContext(LexerAtomContext.class,0);
		}
		public LexerBlockContext lexerBlock() {
			return getRuleContext(LexerBlockContext.class,0);
		}
		public LabeledLexerElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labeledLexerElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLabeledLexerElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLabeledLexerElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLabeledLexerElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabeledLexerElementContext labeledLexerElement() throws RecognitionException {
		LabeledLexerElementContext _localctx = new LabeledLexerElementContext(_ctx, getState());
		enterRule(_localctx, 80, RULE_labeledLexerElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(412);
			id();
			setState(413);
			_la = _input.LA(1);
			if ( !(_la==ASSIGN || _la==PLUS_ASSIGN) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(416);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case LEXER_CHAR_SET:
			case STRING_LITERAL:
			case DOT:
			case NOT:
				{
				setState(414);
				lexerAtom();
				}
				break;
			case LPAREN:
				{
				setState(415);
				lexerBlock();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerBlockContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(AntlrDT4Parser.LPAREN, 0); }
		public LexerAltListContext lexerAltList() {
			return getRuleContext(LexerAltListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(AntlrDT4Parser.RPAREN, 0); }
		public LexerBlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerBlock; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerBlockContext lexerBlock() throws RecognitionException {
		LexerBlockContext _localctx = new LexerBlockContext(_ctx, getState());
		enterRule(_localctx, 82, RULE_lexerBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418);
			match(LPAREN);
			setState(419);
			lexerAltList();
			setState(420);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerCommandsContext extends ParserRuleContext {
		public TerminalNode RARROW() { return getToken(AntlrDT4Parser.RARROW, 0); }
		public List<LexerCommandContext> lexerCommand() {
			return getRuleContexts(LexerCommandContext.class);
		}
		public LexerCommandContext lexerCommand(int i) {
			return getRuleContext(LexerCommandContext.class,i);
		}
		public List<TerminalNode> COMMA() { return getTokens(AntlrDT4Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AntlrDT4Parser.COMMA, i);
		}
		public LexerCommandsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerCommands; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerCommands(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerCommands(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerCommands(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerCommandsContext lexerCommands() throws RecognitionException {
		LexerCommandsContext _localctx = new LexerCommandsContext(_ctx, getState());
		enterRule(_localctx, 84, RULE_lexerCommands);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(422);
			match(RARROW);
			setState(423);
			lexerCommand();
			setState(428);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(424);
				match(COMMA);
				setState(425);
				lexerCommand();
				}
				}
				setState(430);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerCommandContext extends ParserRuleContext {
		public LexerCommandNameContext lexerCommandName() {
			return getRuleContext(LexerCommandNameContext.class,0);
		}
		public TerminalNode LPAREN() { return getToken(AntlrDT4Parser.LPAREN, 0); }
		public LexerCommandExprContext lexerCommandExpr() {
			return getRuleContext(LexerCommandExprContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(AntlrDT4Parser.RPAREN, 0); }
		public LexerCommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerCommand; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerCommand(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerCommand(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerCommandContext lexerCommand() throws RecognitionException {
		LexerCommandContext _localctx = new LexerCommandContext(_ctx, getState());
		enterRule(_localctx, 86, RULE_lexerCommand);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(431);
			lexerCommandName();
			setState(436);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LPAREN) {
				{
				setState(432);
				match(LPAREN);
				setState(433);
				lexerCommandExpr();
				setState(434);
				match(RPAREN);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerCommandNameContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode MODE() { return getToken(AntlrDT4Parser.MODE, 0); }
		public LexerCommandNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerCommandName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerCommandName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerCommandName(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerCommandName(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerCommandNameContext lexerCommandName() throws RecognitionException {
		LexerCommandNameContext _localctx = new LexerCommandNameContext(_ctx, getState());
		enterRule(_localctx, 88, RULE_lexerCommandName);
		try {
			setState(440);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(438);
				id();
				}
				break;
			case MODE:
				enterOuterAlt(_localctx, 2);
				{
				setState(439);
				match(MODE);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerCommandExprContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode INT() { return getToken(AntlrDT4Parser.INT, 0); }
		public LexerCommandExprContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerCommandExpr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerCommandExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerCommandExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerCommandExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerCommandExprContext lexerCommandExpr() throws RecognitionException {
		LexerCommandExprContext _localctx = new LexerCommandExprContext(_ctx, getState());
		enterRule(_localctx, 90, RULE_lexerCommandExpr);
		try {
			setState(444);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(442);
				id();
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(443);
				match(INT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AltListContext extends ParserRuleContext {
		public List<AlternativeContext> alternative() {
			return getRuleContexts(AlternativeContext.class);
		}
		public AlternativeContext alternative(int i) {
			return getRuleContext(AlternativeContext.class,i);
		}
		public List<TerminalNode> OR() { return getTokens(AntlrDT4Parser.OR); }
		public TerminalNode OR(int i) {
			return getToken(AntlrDT4Parser.OR, i);
		}
		public AltListContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_altList; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterAltList(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitAltList(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitAltList(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AltListContext altList() throws RecognitionException {
		AltListContext _localctx = new AltListContext(_ctx, getState());
		enterRule(_localctx, 92, RULE_altList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(446);
			alternative();
			setState(451);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(447);
				match(OR);
				setState(448);
				alternative();
				}
				}
				setState(453);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AlternativeContext extends ParserRuleContext {
		public ElementOptionsContext elementOptions() {
			return getRuleContext(ElementOptionsContext.class,0);
		}
		public List<ElementContext> element() {
			return getRuleContexts(ElementContext.class);
		}
		public ElementContext element(int i) {
			return getRuleContext(ElementContext.class,i);
		}
		public AlternativeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_alternative; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterAlternative(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitAlternative(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitAlternative(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AlternativeContext alternative() throws RecognitionException {
		AlternativeContext _localctx = new AlternativeContext(_ctx, getState());
		enterRule(_localctx, 94, RULE_alternative);
		int _la;
		try {
			setState(463);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
			case STRING_LITERAL:
			case BEGIN_ACTION:
			case LPAREN:
			case LT:
			case DOT:
			case NOT:
				enterOuterAlt(_localctx, 1);
				{
				setState(455);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(454);
					elementOptions();
					}
				}

				setState(458); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(457);
					element();
					}
					}
					setState(460); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TOKEN_REF) | (1L << RULE_REF) | (1L << STRING_LITERAL) | (1L << BEGIN_ACTION) | (1L << LPAREN) | (1L << DOT) | (1L << NOT))) != 0) );
				}
				break;
			case SEMI:
			case RPAREN:
			case OR:
			case POUND:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementContext extends ParserRuleContext {
		public LabeledElementContext labeledElement() {
			return getRuleContext(LabeledElementContext.class,0);
		}
		public EbnfSuffixContext ebnfSuffix() {
			return getRuleContext(EbnfSuffixContext.class,0);
		}
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public EbnfContext ebnf() {
			return getRuleContext(EbnfContext.class,0);
		}
		public ActionBlockContext actionBlock() {
			return getRuleContext(ActionBlockContext.class,0);
		}
		public TerminalNode QUESTION() { return getToken(AntlrDT4Parser.QUESTION, 0); }
		public ElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_element; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementContext element() throws RecognitionException {
		ElementContext _localctx = new ElementContext(_ctx, getState());
		enterRule(_localctx, 96, RULE_element);
		int _la;
		try {
			setState(480);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,52,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(465);
				labeledElement();
				setState(468);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case QUESTION:
				case STAR:
				case PLUS:
					{
					setState(466);
					ebnfSuffix();
					}
					break;
				case TOKEN_REF:
				case RULE_REF:
				case STRING_LITERAL:
				case BEGIN_ACTION:
				case SEMI:
				case LPAREN:
				case RPAREN:
				case OR:
				case DOT:
				case POUND:
				case NOT:
					{
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(470);
				atom();
				setState(473);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case QUESTION:
				case STAR:
				case PLUS:
					{
					setState(471);
					ebnfSuffix();
					}
					break;
				case TOKEN_REF:
				case RULE_REF:
				case STRING_LITERAL:
				case BEGIN_ACTION:
				case SEMI:
				case LPAREN:
				case RPAREN:
				case OR:
				case DOT:
				case POUND:
				case NOT:
					{
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(475);
				ebnf();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(476);
				actionBlock();
				setState(478);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(477);
					match(QUESTION);
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LabeledElementContext extends ParserRuleContext {
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode ASSIGN() { return getToken(AntlrDT4Parser.ASSIGN, 0); }
		public TerminalNode PLUS_ASSIGN() { return getToken(AntlrDT4Parser.PLUS_ASSIGN, 0); }
		public AtomContext atom() {
			return getRuleContext(AtomContext.class,0);
		}
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public LabeledElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_labeledElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLabeledElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLabeledElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLabeledElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LabeledElementContext labeledElement() throws RecognitionException {
		LabeledElementContext _localctx = new LabeledElementContext(_ctx, getState());
		enterRule(_localctx, 98, RULE_labeledElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(482);
			id();
			setState(483);
			_la = _input.LA(1);
			if ( !(_la==ASSIGN || _la==PLUS_ASSIGN) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			setState(486);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
			case STRING_LITERAL:
			case DOT:
			case NOT:
				{
				setState(484);
				atom();
				}
				break;
			case LPAREN:
				{
				setState(485);
				block();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EbnfContext extends ParserRuleContext {
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
		}
		public BlockSuffixContext blockSuffix() {
			return getRuleContext(BlockSuffixContext.class,0);
		}
		public EbnfContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ebnf; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterEbnf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitEbnf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitEbnf(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EbnfContext ebnf() throws RecognitionException {
		EbnfContext _localctx = new EbnfContext(_ctx, getState());
		enterRule(_localctx, 100, RULE_ebnf);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(488);
			block();
			setState(490);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUESTION) | (1L << STAR) | (1L << PLUS))) != 0)) {
				{
				setState(489);
				blockSuffix();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockSuffixContext extends ParserRuleContext {
		public EbnfSuffixContext ebnfSuffix() {
			return getRuleContext(EbnfSuffixContext.class,0);
		}
		public BlockSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockSuffix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterBlockSuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitBlockSuffix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitBlockSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockSuffixContext blockSuffix() throws RecognitionException {
		BlockSuffixContext _localctx = new BlockSuffixContext(_ctx, getState());
		enterRule(_localctx, 102, RULE_blockSuffix);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(492);
			ebnfSuffix();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class EbnfSuffixContext extends ParserRuleContext {
		public List<TerminalNode> QUESTION() { return getTokens(AntlrDT4Parser.QUESTION); }
		public TerminalNode QUESTION(int i) {
			return getToken(AntlrDT4Parser.QUESTION, i);
		}
		public TerminalNode STAR() { return getToken(AntlrDT4Parser.STAR, 0); }
		public TerminalNode PLUS() { return getToken(AntlrDT4Parser.PLUS, 0); }
		public EbnfSuffixContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ebnfSuffix; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterEbnfSuffix(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitEbnfSuffix(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitEbnfSuffix(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EbnfSuffixContext ebnfSuffix() throws RecognitionException {
		EbnfSuffixContext _localctx = new EbnfSuffixContext(_ctx, getState());
		enterRule(_localctx, 104, RULE_ebnfSuffix);
		int _la;
		try {
			setState(506);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case QUESTION:
				enterOuterAlt(_localctx, 1);
				{
				setState(494);
				match(QUESTION);
				setState(496);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(495);
					match(QUESTION);
					}
				}

				}
				break;
			case STAR:
				enterOuterAlt(_localctx, 2);
				{
				setState(498);
				match(STAR);
				setState(500);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(499);
					match(QUESTION);
					}
				}

				}
				break;
			case PLUS:
				enterOuterAlt(_localctx, 3);
				{
				setState(502);
				match(PLUS);
				setState(504);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(503);
					match(QUESTION);
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LexerAtomContext extends ParserRuleContext {
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
		public TerminalContext terminal() {
			return getRuleContext(TerminalContext.class,0);
		}
		public NotSetContext notSet() {
			return getRuleContext(NotSetContext.class,0);
		}
		public CharSetContext charSet() {
			return getRuleContext(CharSetContext.class,0);
		}
		public TerminalNode DOT() { return getToken(AntlrDT4Parser.DOT, 0); }
		public ElementOptionsContext elementOptions() {
			return getRuleContext(ElementOptionsContext.class,0);
		}
		public LexerAtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_lexerAtom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterLexerAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitLexerAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitLexerAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LexerAtomContext lexerAtom() throws RecognitionException {
		LexerAtomContext _localctx = new LexerAtomContext(_ctx, getState());
		enterRule(_localctx, 106, RULE_lexerAtom);
		int _la;
		try {
			setState(516);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,60,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(508);
				range();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(509);
				terminal();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(510);
				notSet();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(511);
				charSet();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(512);
				match(DOT);
				setState(514);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(513);
					elementOptions();
					}
				}

				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AtomContext extends ParserRuleContext {
		public TerminalContext terminal() {
			return getRuleContext(TerminalContext.class,0);
		}
		public RulerefContext ruleref() {
			return getRuleContext(RulerefContext.class,0);
		}
		public NotSetContext notSet() {
			return getRuleContext(NotSetContext.class,0);
		}
		public TerminalNode DOT() { return getToken(AntlrDT4Parser.DOT, 0); }
		public ElementOptionsContext elementOptions() {
			return getRuleContext(ElementOptionsContext.class,0);
		}
		public AtomContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_atom; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterAtom(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitAtom(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitAtom(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AtomContext atom() throws RecognitionException {
		AtomContext _localctx = new AtomContext(_ctx, getState());
		enterRule(_localctx, 108, RULE_atom);
		int _la;
		try {
			setState(525);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 1);
				{
				setState(518);
				terminal();
				}
				break;
			case RULE_REF:
				enterOuterAlt(_localctx, 2);
				{
				setState(519);
				ruleref();
				}
				break;
			case NOT:
				enterOuterAlt(_localctx, 3);
				{
				setState(520);
				notSet();
				}
				break;
			case DOT:
				enterOuterAlt(_localctx, 4);
				{
				setState(521);
				match(DOT);
				setState(523);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(522);
					elementOptions();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NotSetContext extends ParserRuleContext {
		public TerminalNode NOT() { return getToken(AntlrDT4Parser.NOT, 0); }
		public SetElementContext setElement() {
			return getRuleContext(SetElementContext.class,0);
		}
		public BlockSetContext blockSet() {
			return getRuleContext(BlockSetContext.class,0);
		}
		public NotSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_notSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterNotSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitNotSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitNotSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NotSetContext notSet() throws RecognitionException {
		NotSetContext _localctx = new NotSetContext(_ctx, getState());
		enterRule(_localctx, 110, RULE_notSet);
		try {
			setState(531);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,63,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(527);
				match(NOT);
				setState(528);
				setElement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(529);
				match(NOT);
				setState(530);
				blockSet();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockSetContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(AntlrDT4Parser.LPAREN, 0); }
		public List<SetElementContext> setElement() {
			return getRuleContexts(SetElementContext.class);
		}
		public SetElementContext setElement(int i) {
			return getRuleContext(SetElementContext.class,i);
		}
		public TerminalNode RPAREN() { return getToken(AntlrDT4Parser.RPAREN, 0); }
		public List<TerminalNode> OR() { return getTokens(AntlrDT4Parser.OR); }
		public TerminalNode OR(int i) {
			return getToken(AntlrDT4Parser.OR, i);
		}
		public BlockSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_blockSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterBlockSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitBlockSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitBlockSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockSetContext blockSet() throws RecognitionException {
		BlockSetContext _localctx = new BlockSetContext(_ctx, getState());
		enterRule(_localctx, 112, RULE_blockSet);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(533);
			match(LPAREN);
			setState(534);
			setElement();
			setState(539);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(535);
				match(OR);
				setState(536);
				setElement();
				}
				}
				setState(541);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(542);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SetElementContext extends ParserRuleContext {
		public TerminalNode TOKEN_REF() { return getToken(AntlrDT4Parser.TOKEN_REF, 0); }
		public ElementOptionsContext elementOptions() {
			return getRuleContext(ElementOptionsContext.class,0);
		}
		public TerminalNode STRING_LITERAL() { return getToken(AntlrDT4Parser.STRING_LITERAL, 0); }
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
		public CharSetContext charSet() {
			return getRuleContext(CharSetContext.class,0);
		}
		public SetElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_setElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterSetElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitSetElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitSetElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SetElementContext setElement() throws RecognitionException {
		SetElementContext _localctx = new SetElementContext(_ctx, getState());
		enterRule(_localctx, 114, RULE_setElement);
		int _la;
		try {
			setState(554);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,67,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(544);
				match(TOKEN_REF);
				setState(546);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(545);
					elementOptions();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(548);
				match(STRING_LITERAL);
				setState(550);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(549);
					elementOptions();
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(552);
				range();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(553);
				charSet();
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CharSetContext extends ParserRuleContext {
		public TerminalNode LEXER_CHAR_SET() { return getToken(AntlrDT4Parser.LEXER_CHAR_SET, 0); }
		public CharSetContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_charSet; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterCharSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitCharSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitCharSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CharSetContext charSet() throws RecognitionException {
		CharSetContext _localctx = new CharSetContext(_ctx, getState());
		enterRule(_localctx, 116, RULE_charSet);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(556);
			match(LEXER_CHAR_SET);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BlockContext extends ParserRuleContext {
		public TerminalNode LPAREN() { return getToken(AntlrDT4Parser.LPAREN, 0); }
		public AltListContext altList() {
			return getRuleContext(AltListContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(AntlrDT4Parser.RPAREN, 0); }
		public TerminalNode COLON() { return getToken(AntlrDT4Parser.COLON, 0); }
		public OptionsSpecContext optionsSpec() {
			return getRuleContext(OptionsSpecContext.class,0);
		}
		public List<RuleActionContext> ruleAction() {
			return getRuleContexts(RuleActionContext.class);
		}
		public RuleActionContext ruleAction(int i) {
			return getRuleContext(RuleActionContext.class,i);
		}
		public BlockContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_block; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterBlock(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitBlock(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitBlock(this);
			else return visitor.visitChildren(this);
		}
	}

	public final BlockContext block() throws RecognitionException {
		BlockContext _localctx = new BlockContext(_ctx, getState());
		enterRule(_localctx, 118, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(558);
			match(LPAREN);
			setState(569);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << COLON) | (1L << AT))) != 0)) {
				{
				setState(560);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==OPTIONS) {
					{
					setState(559);
					optionsSpec();
					}
				}

				setState(565);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(562);
					ruleAction();
					}
					}
					setState(567);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(568);
				match(COLON);
				}
			}

			setState(571);
			altList();
			setState(572);
			match(RPAREN);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RulerefContext extends ParserRuleContext {
		public TerminalNode RULE_REF() { return getToken(AntlrDT4Parser.RULE_REF, 0); }
		public ArgActionBlockContext argActionBlock() {
			return getRuleContext(ArgActionBlockContext.class,0);
		}
		public ElementOptionsContext elementOptions() {
			return getRuleContext(ElementOptionsContext.class,0);
		}
		public RulerefContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ruleref; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRuleref(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRuleref(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRuleref(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RulerefContext ruleref() throws RecognitionException {
		RulerefContext _localctx = new RulerefContext(_ctx, getState());
		enterRule(_localctx, 120, RULE_ruleref);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(574);
			match(RULE_REF);
			setState(576);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==BEGIN_ARGUMENT) {
				{
				setState(575);
				argActionBlock();
				}
			}

			setState(579);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(578);
				elementOptions();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RangeContext extends ParserRuleContext {
		public List<TerminalNode> STRING_LITERAL() { return getTokens(AntlrDT4Parser.STRING_LITERAL); }
		public TerminalNode STRING_LITERAL(int i) {
			return getToken(AntlrDT4Parser.STRING_LITERAL, i);
		}
		public TerminalNode RANGE() { return getToken(AntlrDT4Parser.RANGE, 0); }
		public RangeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_range; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterRange(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitRange(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitRange(this);
			else return visitor.visitChildren(this);
		}
	}

	public final RangeContext range() throws RecognitionException {
		RangeContext _localctx = new RangeContext(_ctx, getState());
		enterRule(_localctx, 122, RULE_range);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(581);
			match(STRING_LITERAL);
			setState(582);
			match(RANGE);
			setState(583);
			match(STRING_LITERAL);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TerminalContext extends ParserRuleContext {
		public TerminalNode TOKEN_REF() { return getToken(AntlrDT4Parser.TOKEN_REF, 0); }
		public ElementOptionsContext elementOptions() {
			return getRuleContext(ElementOptionsContext.class,0);
		}
		public TerminalNode STRING_LITERAL() { return getToken(AntlrDT4Parser.STRING_LITERAL, 0); }
		public TerminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitTerminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminalContext terminal() throws RecognitionException {
		TerminalContext _localctx = new TerminalContext(_ctx, getState());
		enterRule(_localctx, 124, RULE_terminal);
		int _la;
		try {
			setState(593);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case TOKEN_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(585);
				match(TOKEN_REF);
				setState(587);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(586);
					elementOptions();
					}
				}

				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(589);
				match(STRING_LITERAL);
				setState(591);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(590);
					elementOptions();
					}
				}

				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementOptionsContext extends ParserRuleContext {
		public TerminalNode LT() { return getToken(AntlrDT4Parser.LT, 0); }
		public List<ElementOptionContext> elementOption() {
			return getRuleContexts(ElementOptionContext.class);
		}
		public ElementOptionContext elementOption(int i) {
			return getRuleContext(ElementOptionContext.class,i);
		}
		public TerminalNode GT() { return getToken(AntlrDT4Parser.GT, 0); }
		public List<TerminalNode> COMMA() { return getTokens(AntlrDT4Parser.COMMA); }
		public TerminalNode COMMA(int i) {
			return getToken(AntlrDT4Parser.COMMA, i);
		}
		public ElementOptionsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementOptions; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterElementOptions(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitElementOptions(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitElementOptions(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementOptionsContext elementOptions() throws RecognitionException {
		ElementOptionsContext _localctx = new ElementOptionsContext(_ctx, getState());
		enterRule(_localctx, 126, RULE_elementOptions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(595);
			match(LT);
			setState(596);
			elementOption();
			setState(601);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(597);
				match(COMMA);
				setState(598);
				elementOption();
				}
				}
				setState(603);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(604);
			match(GT);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ElementOptionContext extends ParserRuleContext {
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public TerminalNode ASSIGN() { return getToken(AntlrDT4Parser.ASSIGN, 0); }
		public TerminalNode STRING_LITERAL() { return getToken(AntlrDT4Parser.STRING_LITERAL, 0); }
		public ElementOptionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_elementOption; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterElementOption(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitElementOption(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitElementOption(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ElementOptionContext elementOption() throws RecognitionException {
		ElementOptionContext _localctx = new ElementOptionContext(_ctx, getState());
		enterRule(_localctx, 128, RULE_elementOption);
		try {
			setState(613);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,78,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(606);
				id();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(607);
				id();
				setState(608);
				match(ASSIGN);
				setState(611);
				_errHandler.sync(this);
				switch (_input.LA(1)) {
				case TOKEN_REF:
				case RULE_REF:
					{
					setState(609);
					id();
					}
					break;
				case STRING_LITERAL:
					{
					setState(610);
					match(STRING_LITERAL);
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class IdContext extends ParserRuleContext {
		public TerminalNode RULE_REF() { return getToken(AntlrDT4Parser.RULE_REF, 0); }
		public TerminalNode TOKEN_REF() { return getToken(AntlrDT4Parser.TOKEN_REF, 0); }
		public IdContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_id; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterId(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitId(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitId(this);
			else return visitor.visitChildren(this);
		}
	}

	public final IdContext id() throws RecognitionException {
		IdContext _localctx = new IdContext(_ctx, getState());
		enterRule(_localctx, 130, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(615);
			_la = _input.LA(1);
			if ( !(_la==TOKEN_REF || _la==RULE_REF) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3@\u026c\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\4C\tC\3\2\3\2\7\2\u0089\n\2\f\2\16\2\u008c"+
		"\13\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\5\4\u009a\n\4\3"+
		"\5\3\5\3\5\3\5\3\5\5\5\u00a1\n\5\3\6\3\6\3\6\7\6\u00a6\n\6\f\6\16\6\u00a9"+
		"\13\6\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\7\b\u00b5\n\b\f\b\16\b\u00b8"+
		"\13\b\3\b\3\b\3\b\5\b\u00bd\n\b\3\t\3\t\5\t\u00c1\n\t\3\t\3\t\3\n\3\n"+
		"\3\n\5\n\u00c8\n\n\3\n\3\n\3\13\3\13\3\13\5\13\u00cf\n\13\3\13\3\13\3"+
		"\f\3\f\7\f\u00d5\n\f\f\f\16\f\u00d8\13\f\3\f\5\f\u00db\n\f\3\r\3\r\3\r"+
		"\3\16\3\16\3\16\3\16\5\16\u00e4\n\16\3\16\3\16\3\16\3\17\3\17\3\17\5\17"+
		"\u00ec\n\17\3\20\3\20\7\20\u00f0\n\20\f\20\16\20\u00f3\13\20\3\20\3\20"+
		"\3\21\3\21\7\21\u00f9\n\21\f\21\16\21\u00fc\13\21\3\21\3\21\3\22\7\22"+
		"\u0101\n\22\f\22\16\22\u0104\13\22\3\23\3\23\3\23\3\23\5\23\u010a\n\23"+
		"\3\24\5\24\u010d\n\24\3\24\3\24\5\24\u0111\n\24\3\24\5\24\u0114\n\24\3"+
		"\24\5\24\u0117\n\24\3\24\5\24\u011a\n\24\3\24\7\24\u011d\n\24\f\24\16"+
		"\24\u0120\13\24\3\24\3\24\3\24\3\24\3\24\3\25\7\25\u0128\n\25\f\25\16"+
		"\25\u012b\13\25\3\25\5\25\u012e\n\25\3\26\3\26\3\26\3\26\3\27\3\27\3\27"+
		"\3\30\3\30\5\30\u0139\n\30\3\31\3\31\3\31\3\32\3\32\3\32\3\32\7\32\u0142"+
		"\n\32\f\32\16\32\u0145\13\32\3\33\3\33\3\33\3\34\3\34\3\34\3\34\3\35\6"+
		"\35\u014f\n\35\r\35\16\35\u0150\3\36\3\36\3\37\3\37\3 \3 \3 \7 \u015a"+
		"\n \f \16 \u015d\13 \3!\3!\3!\5!\u0162\n!\3\"\3\"\3\"\3\"\6\"\u0168\n"+
		"\"\r\"\16\"\u0169\3#\3#\3#\3#\3#\3$\3$\3$\3$\3$\3$\3%\3%\3&\3&\3&\7&\u017c"+
		"\n&\f&\16&\u017f\13&\3\'\3\'\5\'\u0183\n\'\3\'\5\'\u0186\n\'\3(\6(\u0189"+
		"\n(\r(\16(\u018a\3)\3)\5)\u018f\n)\3)\3)\5)\u0193\n)\3)\3)\5)\u0197\n"+
		")\3)\3)\5)\u019b\n)\5)\u019d\n)\3*\3*\3*\3*\5*\u01a3\n*\3+\3+\3+\3+\3"+
		",\3,\3,\3,\7,\u01ad\n,\f,\16,\u01b0\13,\3-\3-\3-\3-\3-\5-\u01b7\n-\3."+
		"\3.\5.\u01bb\n.\3/\3/\5/\u01bf\n/\3\60\3\60\3\60\7\60\u01c4\n\60\f\60"+
		"\16\60\u01c7\13\60\3\61\5\61\u01ca\n\61\3\61\6\61\u01cd\n\61\r\61\16\61"+
		"\u01ce\3\61\5\61\u01d2\n\61\3\62\3\62\3\62\5\62\u01d7\n\62\3\62\3\62\3"+
		"\62\5\62\u01dc\n\62\3\62\3\62\3\62\5\62\u01e1\n\62\5\62\u01e3\n\62\3\63"+
		"\3\63\3\63\3\63\5\63\u01e9\n\63\3\64\3\64\5\64\u01ed\n\64\3\65\3\65\3"+
		"\66\3\66\5\66\u01f3\n\66\3\66\3\66\5\66\u01f7\n\66\3\66\3\66\5\66\u01fb"+
		"\n\66\5\66\u01fd\n\66\3\67\3\67\3\67\3\67\3\67\3\67\5\67\u0205\n\67\5"+
		"\67\u0207\n\67\38\38\38\38\38\58\u020e\n8\58\u0210\n8\39\39\39\39\59\u0216"+
		"\n9\3:\3:\3:\3:\7:\u021c\n:\f:\16:\u021f\13:\3:\3:\3;\3;\5;\u0225\n;\3"+
		";\3;\5;\u0229\n;\3;\3;\5;\u022d\n;\3<\3<\3=\3=\5=\u0233\n=\3=\7=\u0236"+
		"\n=\f=\16=\u0239\13=\3=\5=\u023c\n=\3=\3=\3=\3>\3>\5>\u0243\n>\3>\5>\u0246"+
		"\n>\3?\3?\3?\3?\3@\3@\5@\u024e\n@\3@\3@\5@\u0252\n@\5@\u0254\n@\3A\3A"+
		"\3A\3A\7A\u025a\nA\fA\16A\u025d\13A\3A\3A\3B\3B\3B\3B\3B\5B\u0266\nB\5"+
		"B\u0268\nB\3C\3C\3C\2\2D\2\4\6\b\n\f\16\20\22\24\26\30\32\34\36 \"$&("+
		"*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\^`bdfhjlnprtvxz|~\u0080\u0082\u0084"+
		"\2\5\3\2\27\31\4\2++..\3\2\3\4\2\u028d\2\u0086\3\2\2\2\4\u0090\3\2\2\2"+
		"\6\u0099\3\2\2\2\b\u00a0\3\2\2\2\n\u00a2\3\2\2\2\f\u00ac\3\2\2\2\16\u00bc"+
		"\3\2\2\2\20\u00be\3\2\2\2\22\u00c4\3\2\2\2\24\u00cb\3\2\2\2\26\u00d2\3"+
		"\2\2\2\30\u00dc\3\2\2\2\32\u00df\3\2\2\2\34\u00eb\3\2\2\2\36\u00ed\3\2"+
		"\2\2 \u00f6\3\2\2\2\"\u0102\3\2\2\2$\u0109\3\2\2\2&\u010c\3\2\2\2(\u0129"+
		"\3\2\2\2*\u012f\3\2\2\2,\u0133\3\2\2\2.\u0138\3\2\2\2\60\u013a\3\2\2\2"+
		"\62\u013d\3\2\2\2\64\u0146\3\2\2\2\66\u0149\3\2\2\28\u014e\3\2\2\2:\u0152"+
		"\3\2\2\2<\u0154\3\2\2\2>\u0156\3\2\2\2@\u015e\3\2\2\2B\u0163\3\2\2\2D"+
		"\u016b\3\2\2\2F\u0170\3\2\2\2H\u0176\3\2\2\2J\u0178\3\2\2\2L\u0185\3\2"+
		"\2\2N\u0188\3\2\2\2P\u019c\3\2\2\2R\u019e\3\2\2\2T\u01a4\3\2\2\2V\u01a8"+
		"\3\2\2\2X\u01b1\3\2\2\2Z\u01ba\3\2\2\2\\\u01be\3\2\2\2^\u01c0\3\2\2\2"+
		"`\u01d1\3\2\2\2b\u01e2\3\2\2\2d\u01e4\3\2\2\2f\u01ea\3\2\2\2h\u01ee\3"+
		"\2\2\2j\u01fc\3\2\2\2l\u0206\3\2\2\2n\u020f\3\2\2\2p\u0215\3\2\2\2r\u0217"+
		"\3\2\2\2t\u022c\3\2\2\2v\u022e\3\2\2\2x\u0230\3\2\2\2z\u0240\3\2\2\2|"+
		"\u0247\3\2\2\2~\u0253\3\2\2\2\u0080\u0255\3\2\2\2\u0082\u0267\3\2\2\2"+
		"\u0084\u0269\3\2\2\2\u0086\u008a\5\4\3\2\u0087\u0089\5\b\5\2\u0088\u0087"+
		"\3\2\2\2\u0089\u008c\3\2\2\2\u008a\u0088\3\2\2\2\u008a\u008b\3\2\2\2\u008b"+
		"\u008d\3\2\2\2\u008c\u008a\3\2\2\2\u008d\u008e\5\"\22\2\u008e\u008f\7"+
		"\2\2\3\u008f\3\3\2\2\2\u0090\u0091\5\6\4\2\u0091\u0092\5\u0084C\2\u0092"+
		"\u0093\7#\2\2\u0093\5\3\2\2\2\u0094\u0095\7\23\2\2\u0095\u009a\7\26\2"+
		"\2\u0096\u0097\7\24\2\2\u0097\u009a\7\26\2\2\u0098\u009a\7\26\2\2\u0099"+
		"\u0094\3\2\2\2\u0099\u0096\3\2\2\2\u0099\u0098\3\2\2\2\u009a\7\3\2\2\2"+
		"\u009b\u00a1\5\n\6\2\u009c\u00a1\5\20\t\2\u009d\u00a1\5\22\n\2\u009e\u00a1"+
		"\5\24\13\2\u009f\u00a1\5\32\16\2\u00a0\u009b\3\2\2\2\u00a0\u009c\3\2\2"+
		"\2\u00a0\u009d\3\2\2\2\u00a0\u009e\3\2\2\2\u00a0\u009f\3\2\2\2\u00a1\t"+
		"\3\2\2\2\u00a2\u00a3\7\16\2\2\u00a3\u00a7\7&\2\2\u00a4\u00a6\5\f\7\2\u00a5"+
		"\u00a4\3\2\2\2\u00a6\u00a9\3\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a8\3\2"+
		"\2\2\u00a8\u00aa\3\2\2\2\u00a9\u00a7\3\2\2\2\u00aa\u00ab\7\'\2\2\u00ab"+
		"\13\3\2\2\2\u00ac\u00ad\5\u0084C\2\u00ad\u00ae\7+\2\2\u00ae\u00af\5\16"+
		"\b\2\u00af\u00b0\7#\2\2\u00b0\r\3\2\2\2\u00b1\u00b6\5\u0084C\2\u00b2\u00b3"+
		"\7\63\2\2\u00b3\u00b5\5\u0084C\2\u00b4\u00b2\3\2\2\2\u00b5\u00b8\3\2\2"+
		"\2\u00b6\u00b4\3\2\2\2\u00b6\u00b7\3\2\2\2\u00b7\u00bd\3\2\2\2\u00b8\u00b6"+
		"\3\2\2\2\u00b9\u00bd\7\n\2\2\u00ba\u00bd\5\36\20\2\u00bb\u00bd\7\t\2\2"+
		"\u00bc\u00b1\3\2\2\2\u00bc\u00b9\3\2\2\2\u00bc\u00ba\3\2\2\2\u00bc\u00bb"+
		"\3\2\2\2\u00bd\17\3\2\2\2\u00be\u00c0\7\21\2\2\u00bf\u00c1\5\26\f\2\u00c0"+
		"\u00bf\3\2\2\2\u00c0\u00c1\3\2\2\2\u00c1\u00c2\3\2\2\2\u00c2\u00c3\7#"+
		"\2\2\u00c3\21\3\2\2\2\u00c4\u00c5\7\17\2\2\u00c5\u00c7\7&\2\2\u00c6\u00c8"+
		"\5\26\f\2\u00c7\u00c6\3\2\2\2\u00c7\u00c8\3\2\2\2\u00c8\u00c9\3\2\2\2"+
		"\u00c9\u00ca\7\'\2\2\u00ca\23\3\2\2\2\u00cb\u00cc\7\20\2\2\u00cc\u00ce"+
		"\7&\2\2\u00cd\u00cf\5\26\f\2\u00ce\u00cd\3\2\2\2\u00ce\u00cf\3\2\2\2\u00cf"+
		"\u00d0\3\2\2\2\u00d0\u00d1\7\'\2\2\u00d1\25\3\2\2\2\u00d2\u00d6\5\u0084"+
		"C\2\u00d3\u00d5\5\30\r\2\u00d4\u00d3\3\2\2\2\u00d5\u00d8\3\2\2\2\u00d6"+
		"\u00d4\3\2\2\2\u00d6\u00d7\3\2\2\2\u00d7\u00da\3\2\2\2\u00d8\u00d6\3\2"+
		"\2\2\u00d9\u00db\7\"\2\2\u00da\u00d9\3\2\2\2\u00da\u00db\3\2\2\2\u00db"+
		"\27\3\2\2\2\u00dc\u00dd\7\"\2\2\u00dd\u00de\5\u0084C\2\u00de\31\3\2\2"+
		"\2\u00df\u00e3\7\64\2\2\u00e0\u00e1\5\34\17\2\u00e1\u00e2\7!\2\2\u00e2"+
		"\u00e4\3\2\2\2\u00e3\u00e0\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4\u00e5\3\2"+
		"\2\2\u00e5\u00e6\5\u0084C\2\u00e6\u00e7\5\36\20\2\u00e7\33\3\2\2\2\u00e8"+
		"\u00ec\5\u0084C\2\u00e9\u00ec\7\23\2\2\u00ea\u00ec\7\24\2\2\u00eb\u00e8"+
		"\3\2\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ea\3\2\2\2\u00ec\35\3\2\2\2\u00ed"+
		"\u00f1\7\r\2\2\u00ee\u00f0\7?\2\2\u00ef\u00ee\3\2\2\2\u00f0\u00f3\3\2"+
		"\2\2\u00f1\u00ef\3\2\2\2\u00f1\u00f2\3\2\2\2\u00f2\u00f4\3\2\2\2\u00f3"+
		"\u00f1\3\2\2\2\u00f4\u00f5\7=\2\2\u00f5\37\3\2\2\2\u00f6\u00fa\7\f\2\2"+
		"\u00f7\u00f9\7<\2\2\u00f8\u00f7\3\2\2\2\u00f9\u00fc\3\2\2\2\u00fa\u00f8"+
		"\3\2\2\2\u00fa\u00fb\3\2\2\2\u00fb\u00fd\3\2\2\2\u00fc\u00fa\3\2\2\2\u00fd"+
		"\u00fe\7:\2\2\u00fe!\3\2\2\2\u00ff\u0101\5$\23\2\u0100\u00ff\3\2\2\2\u0101"+
		"\u0104\3\2\2\2\u0102\u0100\3\2\2\2\u0102\u0103\3\2\2\2\u0103#\3\2\2\2"+
		"\u0104\u0102\3\2\2\2\u0105\u010a\5&\24\2\u0106\u010a\5D#\2\u0107\u010a"+
		"\5B\"\2\u0108\u010a\5F$\2\u0109\u0105\3\2\2\2\u0109\u0106\3\2\2\2\u0109"+
		"\u0107\3\2\2\2\u0109\u0108\3\2\2\2\u010a%\3\2\2\2\u010b\u010d\58\35\2"+
		"\u010c\u010b\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010e\3\2\2\2\u010e\u0110"+
		"\7\4\2\2\u010f\u0111\5 \21\2\u0110\u010f\3\2\2\2\u0110\u0111\3\2\2\2\u0111"+
		"\u0113\3\2\2\2\u0112\u0114\5\60\31\2\u0113\u0112\3\2\2\2\u0113\u0114\3"+
		"\2\2\2\u0114\u0116\3\2\2\2\u0115\u0117\5\62\32\2\u0116\u0115\3\2\2\2\u0116"+
		"\u0117\3\2\2\2\u0117\u0119\3\2\2\2\u0118\u011a\5\64\33\2\u0119\u0118\3"+
		"\2\2\2\u0119\u011a\3\2\2\2\u011a\u011e\3\2\2\2\u011b\u011d\5.\30\2\u011c"+
		"\u011b\3\2\2\2\u011d\u0120\3\2\2\2\u011e\u011c\3\2\2\2\u011e\u011f\3\2"+
		"\2\2\u011f\u0121\3\2\2\2\u0120\u011e\3\2\2\2\u0121\u0122\7 \2\2\u0122"+
		"\u0123\5<\37\2\u0123\u0124\7#\2\2\u0124\u0125\5(\25\2\u0125\'\3\2\2\2"+
		"\u0126\u0128\5*\26\2\u0127\u0126\3\2\2\2\u0128\u012b\3\2\2\2\u0129\u0127"+
		"\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012d\3\2\2\2\u012b\u0129\3\2\2\2\u012c"+
		"\u012e\5,\27\2\u012d\u012c\3\2\2\2\u012d\u012e\3\2\2\2\u012e)\3\2\2\2"+
		"\u012f\u0130\7\35\2\2\u0130\u0131\5 \21\2\u0131\u0132\5\36\20\2\u0132"+
		"+\3\2\2\2\u0133\u0134\7\36\2\2\u0134\u0135\5\36\20\2\u0135-\3\2\2\2\u0136"+
		"\u0139\5\n\6\2\u0137\u0139\5\66\34\2\u0138\u0136\3\2\2\2\u0138\u0137\3"+
		"\2\2\2\u0139/\3\2\2\2\u013a\u013b\7\32\2\2\u013b\u013c\5 \21\2\u013c\61"+
		"\3\2\2\2\u013d\u013e\7\34\2\2\u013e\u0143\5\u0084C\2\u013f\u0140\7\"\2"+
		"\2\u0140\u0142\5\u0084C\2\u0141\u013f\3\2\2\2\u0142\u0145\3\2\2\2\u0143"+
		"\u0141\3\2\2\2\u0143\u0144\3\2\2\2\u0144\63\3\2\2\2\u0145\u0143\3\2\2"+
		"\2\u0146\u0147\7\33\2\2\u0147\u0148\5 \21\2\u0148\65\3\2\2\2\u0149\u014a"+
		"\7\64\2\2\u014a\u014b\5\u0084C\2\u014b\u014c\5\36\20\2\u014c\67\3\2\2"+
		"\2\u014d\u014f\5:\36\2\u014e\u014d\3\2\2\2\u014f\u0150\3\2\2\2\u0150\u014e"+
		"\3\2\2\2\u0150\u0151\3\2\2\2\u01519\3\2\2\2\u0152\u0153\t\2\2\2\u0153"+
		";\3\2\2\2\u0154\u0155\5> \2\u0155=\3\2\2\2\u0156\u015b\5@!\2\u0157\u0158"+
		"\7\60\2\2\u0158\u015a\5@!\2\u0159\u0157\3\2\2\2\u015a\u015d\3\2\2\2\u015b"+
		"\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c?\3\2\2\2\u015d\u015b\3\2\2\2"+
		"\u015e\u0161\5`\61\2\u015f\u0160\7\65\2\2\u0160\u0162\5\u0084C\2\u0161"+
		"\u015f\3\2\2\2\u0161\u0162\3\2\2\2\u0162A\3\2\2\2\u0163\u0164\7\37\2\2"+
		"\u0164\u0165\5\u0084C\2\u0165\u0167\7#\2\2\u0166\u0168\5D#\2\u0167\u0166"+
		"\3\2\2\2\u0168\u0169\3\2\2\2\u0169\u0167\3\2\2\2\u0169\u016a\3\2\2\2\u016a"+
		"C\3\2\2\2\u016b\u016c\7\3\2\2\u016c\u016d\7 \2\2\u016d\u016e\5H%\2\u016e"+
		"\u016f\7#\2\2\u016fE\3\2\2\2\u0170\u0171\7\22\2\2\u0171\u0172\7\3\2\2"+
		"\u0172\u0173\7 \2\2\u0173\u0174\5H%\2\u0174\u0175\7#\2\2\u0175G\3\2\2"+
		"\2\u0176\u0177\5J&\2\u0177I\3\2\2\2\u0178\u017d\5L\'\2\u0179\u017a\7\60"+
		"\2\2\u017a\u017c\5L\'\2\u017b\u0179\3\2\2\2\u017c\u017f\3\2\2\2\u017d"+
		"\u017b\3\2\2\2\u017d\u017e\3\2\2\2\u017eK\3\2\2\2\u017f\u017d\3\2\2\2"+
		"\u0180\u0182\5N(\2\u0181\u0183\5V,\2\u0182\u0181\3\2\2\2\u0182\u0183\3"+
		"\2\2\2\u0183\u0186\3\2\2\2\u0184\u0186\3\2\2\2\u0185\u0180\3\2\2\2\u0185"+
		"\u0184\3\2\2\2\u0186M\3\2\2\2\u0187\u0189\5P)\2\u0188\u0187\3\2\2\2\u0189"+
		"\u018a\3\2\2\2\u018a\u0188\3\2\2\2\u018a\u018b\3\2\2\2\u018bO\3\2\2\2"+
		"\u018c\u018e\5R*\2\u018d\u018f\5j\66\2\u018e\u018d\3\2\2\2\u018e\u018f"+
		"\3\2\2\2\u018f\u019d\3\2\2\2\u0190\u0192\5l\67\2\u0191\u0193\5j\66\2\u0192"+
		"\u0191\3\2\2\2\u0192\u0193\3\2\2\2\u0193\u019d\3\2\2\2\u0194\u0196\5T"+
		"+\2\u0195\u0197\5j\66\2\u0196\u0195\3\2\2\2\u0196\u0197\3\2\2\2\u0197"+
		"\u019d\3\2\2\2\u0198\u019a\5\36\20\2\u0199\u019b\7,\2\2\u019a\u0199\3"+
		"\2\2\2\u019a\u019b\3\2\2\2\u019b\u019d\3\2\2\2\u019c\u018c\3\2\2\2\u019c"+
		"\u0190\3\2\2\2\u019c\u0194\3\2\2\2\u019c\u0198\3\2\2\2\u019dQ\3\2\2\2"+
		"\u019e\u019f\5\u0084C\2\u019f\u01a2\t\3\2\2\u01a0\u01a3\5l\67\2\u01a1"+
		"\u01a3\5T+\2\u01a2\u01a0\3\2\2\2\u01a2\u01a1\3\2\2\2\u01a3S\3\2\2\2\u01a4"+
		"\u01a5\7$\2\2\u01a5\u01a6\5J&\2\u01a6\u01a7\7%\2\2\u01a7U\3\2\2\2\u01a8"+
		"\u01a9\7(\2\2\u01a9\u01ae\5X-\2\u01aa\u01ab\7\"\2\2\u01ab\u01ad\5X-\2"+
		"\u01ac\u01aa\3\2\2\2\u01ad\u01b0\3\2\2\2\u01ae\u01ac\3\2\2\2\u01ae\u01af"+
		"\3\2\2\2\u01afW\3\2\2\2\u01b0\u01ae\3\2\2\2\u01b1\u01b6\5Z.\2\u01b2\u01b3"+
		"\7$\2\2\u01b3\u01b4\5\\/\2\u01b4\u01b5\7%\2\2\u01b5\u01b7\3\2\2\2\u01b6"+
		"\u01b2\3\2\2\2\u01b6\u01b7\3\2\2\2\u01b7Y\3\2\2\2\u01b8\u01bb\5\u0084"+
		"C\2\u01b9\u01bb\7\37\2\2\u01ba\u01b8\3\2\2\2\u01ba\u01b9\3\2\2\2\u01bb"+
		"[\3\2\2\2\u01bc\u01bf\5\u0084C\2\u01bd\u01bf\7\t\2\2\u01be\u01bc\3\2\2"+
		"\2\u01be\u01bd\3\2\2\2\u01bf]\3\2\2\2\u01c0\u01c5\5`\61\2\u01c1\u01c2"+
		"\7\60\2\2\u01c2\u01c4\5`\61\2\u01c3\u01c1\3\2\2\2\u01c4\u01c7\3\2\2\2"+
		"\u01c5\u01c3\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c6_\3\2\2\2\u01c7\u01c5\3"+
		"\2\2\2\u01c8\u01ca\5\u0080A\2\u01c9\u01c8\3\2\2\2\u01c9\u01ca\3\2\2\2"+
		"\u01ca\u01cc\3\2\2\2\u01cb\u01cd\5b\62\2\u01cc\u01cb\3\2\2\2\u01cd\u01ce"+
		"\3\2\2\2\u01ce\u01cc\3\2\2\2\u01ce\u01cf\3\2\2\2\u01cf\u01d2\3\2\2\2\u01d0"+
		"\u01d2\3\2\2\2\u01d1\u01c9\3\2\2\2\u01d1\u01d0\3\2\2\2\u01d2a\3\2\2\2"+
		"\u01d3\u01d6\5d\63\2\u01d4\u01d7\5j\66\2\u01d5\u01d7\3\2\2\2\u01d6\u01d4"+
		"\3\2\2\2\u01d6\u01d5\3\2\2\2\u01d7\u01e3\3\2\2\2\u01d8\u01db\5n8\2\u01d9"+
		"\u01dc\5j\66\2\u01da\u01dc\3\2\2\2\u01db\u01d9\3\2\2\2\u01db\u01da\3\2"+
		"\2\2\u01dc\u01e3\3\2\2\2\u01dd\u01e3\5f\64\2\u01de\u01e0\5\36\20\2\u01df"+
		"\u01e1\7,\2\2\u01e0\u01df\3\2\2\2\u01e0\u01e1\3\2\2\2\u01e1\u01e3\3\2"+
		"\2\2\u01e2\u01d3\3\2\2\2\u01e2\u01d8\3\2\2\2\u01e2\u01dd\3\2\2\2\u01e2"+
		"\u01de\3\2\2\2\u01e3c\3\2\2\2\u01e4\u01e5\5\u0084C\2\u01e5\u01e8\t\3\2"+
		"\2\u01e6\u01e9\5n8\2\u01e7\u01e9\5x=\2\u01e8\u01e6\3\2\2\2\u01e8\u01e7"+
		"\3\2\2\2\u01e9e\3\2\2\2\u01ea\u01ec\5x=\2\u01eb\u01ed\5h\65\2\u01ec\u01eb"+
		"\3\2\2\2\u01ec\u01ed\3\2\2\2\u01edg\3\2\2\2\u01ee\u01ef\5j\66\2\u01ef"+
		"i\3\2\2\2\u01f0\u01f2\7,\2\2\u01f1\u01f3\7,\2\2\u01f2\u01f1\3\2\2\2\u01f2"+
		"\u01f3\3\2\2\2\u01f3\u01fd\3\2\2\2\u01f4\u01f6\7-\2\2\u01f5\u01f7\7,\2"+
		"\2\u01f6\u01f5\3\2\2\2\u01f6\u01f7\3\2\2\2\u01f7\u01fd\3\2\2\2\u01f8\u01fa"+
		"\7/\2\2\u01f9\u01fb\7,\2\2\u01fa\u01f9\3\2\2\2\u01fa\u01fb\3\2\2\2\u01fb"+
		"\u01fd\3\2\2\2\u01fc\u01f0\3\2\2\2\u01fc\u01f4\3\2\2\2\u01fc\u01f8\3\2"+
		"\2\2\u01fdk\3\2\2\2\u01fe\u0207\5|?\2\u01ff\u0207\5~@\2\u0200\u0207\5"+
		"p9\2\u0201\u0207\5v<\2\u0202\u0204\7\63\2\2\u0203\u0205\5\u0080A\2\u0204"+
		"\u0203\3\2\2\2\u0204\u0205\3\2\2\2\u0205\u0207\3\2\2\2\u0206\u01fe\3\2"+
		"\2\2\u0206\u01ff\3\2\2\2\u0206\u0200\3\2\2\2\u0206\u0201\3\2\2\2\u0206"+
		"\u0202\3\2\2\2\u0207m\3\2\2\2\u0208\u0210\5~@\2\u0209\u0210\5z>\2\u020a"+
		"\u0210\5p9\2\u020b\u020d\7\63\2\2\u020c\u020e\5\u0080A\2\u020d\u020c\3"+
		"\2\2\2\u020d\u020e\3\2\2\2\u020e\u0210\3\2\2\2\u020f\u0208\3\2\2\2\u020f"+
		"\u0209\3\2\2\2\u020f\u020a\3\2\2\2\u020f\u020b\3\2\2\2\u0210o\3\2\2\2"+
		"\u0211\u0212\7\66\2\2\u0212\u0216\5t;\2\u0213\u0214\7\66\2\2\u0214\u0216"+
		"\5r:\2\u0215\u0211\3\2\2\2\u0215\u0213\3\2\2\2\u0216q\3\2\2\2\u0217\u0218"+
		"\7$\2\2\u0218\u021d\5t;\2\u0219\u021a\7\60\2\2\u021a\u021c\5t;\2\u021b"+
		"\u0219\3\2\2\2\u021c\u021f\3\2\2\2\u021d\u021b\3\2\2\2\u021d\u021e\3\2"+
		"\2\2\u021e\u0220\3\2\2\2\u021f\u021d\3\2\2\2\u0220\u0221\7%\2\2\u0221"+
		"s\3\2\2\2\u0222\u0224\7\3\2\2\u0223\u0225\5\u0080A\2\u0224\u0223\3\2\2"+
		"\2\u0224\u0225\3\2\2\2\u0225\u022d\3\2\2\2\u0226\u0228\7\n\2\2\u0227\u0229"+
		"\5\u0080A\2\u0228\u0227\3\2\2\2\u0228\u0229\3\2\2\2\u0229\u022d\3\2\2"+
		"\2\u022a\u022d\5|?\2\u022b\u022d\5v<\2\u022c\u0222\3\2\2\2\u022c\u0226"+
		"\3\2\2\2\u022c\u022a\3\2\2\2\u022c\u022b\3\2\2\2\u022du\3\2\2\2\u022e"+
		"\u022f\7\5\2\2\u022fw\3\2\2\2\u0230\u023b\7$\2\2\u0231\u0233\5\n\6\2\u0232"+
		"\u0231\3\2\2\2\u0232\u0233\3\2\2\2\u0233\u0237\3\2\2\2\u0234\u0236\5\66"+
		"\34\2\u0235\u0234\3\2\2\2\u0236\u0239\3\2\2\2\u0237\u0235\3\2\2\2\u0237"+
		"\u0238\3\2\2\2\u0238\u023a\3\2\2\2\u0239\u0237\3\2\2\2\u023a\u023c\7 "+
		"\2\2\u023b\u0232\3\2\2\2\u023b\u023c\3\2\2\2\u023c\u023d\3\2\2\2\u023d"+
		"\u023e\5^\60\2\u023e\u023f\7%\2\2\u023fy\3\2\2\2\u0240\u0242\7\4\2\2\u0241"+
		"\u0243\5 \21\2\u0242\u0241\3\2\2\2\u0242\u0243\3\2\2\2\u0243\u0245\3\2"+
		"\2\2\u0244\u0246\5\u0080A\2\u0245\u0244\3\2\2\2\u0245\u0246\3\2\2\2\u0246"+
		"{\3\2\2\2\u0247\u0248\7\n\2\2\u0248\u0249\7\62\2\2\u0249\u024a\7\n\2\2"+
		"\u024a}\3\2\2\2\u024b\u024d\7\3\2\2\u024c\u024e\5\u0080A\2\u024d\u024c"+
		"\3\2\2\2\u024d\u024e\3\2\2\2\u024e\u0254\3\2\2\2\u024f\u0251\7\n\2\2\u0250"+
		"\u0252\5\u0080A\2\u0251\u0250\3\2\2\2\u0251\u0252\3\2\2\2\u0252\u0254"+
		"\3\2\2\2\u0253\u024b\3\2\2\2\u0253\u024f\3\2\2\2\u0254\177\3\2\2\2\u0255"+
		"\u0256\7)\2\2\u0256\u025b\5\u0082B\2\u0257\u0258\7\"\2\2\u0258\u025a\5"+
		"\u0082B\2\u0259\u0257\3\2\2\2\u025a\u025d\3\2\2\2\u025b\u0259\3\2\2\2"+
		"\u025b\u025c\3\2\2\2\u025c\u025e\3\2\2\2\u025d\u025b\3\2\2\2\u025e\u025f"+
		"\7*\2\2\u025f\u0081\3\2\2\2\u0260\u0268\5\u0084C\2\u0261\u0262\5\u0084"+
		"C\2\u0262\u0265\7+\2\2\u0263\u0266\5\u0084C\2\u0264\u0266\7\n\2\2\u0265"+
		"\u0263\3\2\2\2\u0265\u0264\3\2\2\2\u0266\u0268\3\2\2\2\u0267\u0260\3\2"+
		"\2\2\u0267\u0261\3\2\2\2\u0268\u0083\3\2\2\2\u0269\u026a\t\4\2\2\u026a"+
		"\u0085\3\2\2\2Q\u008a\u0099\u00a0\u00a7\u00b6\u00bc\u00c0\u00c7\u00ce"+
		"\u00d6\u00da\u00e3\u00eb\u00f1\u00fa\u0102\u0109\u010c\u0110\u0113\u0116"+
		"\u0119\u011e\u0129\u012d\u0138\u0143\u0150\u015b\u0161\u0169\u017d\u0182"+
		"\u0185\u018a\u018e\u0192\u0196\u019a\u019c\u01a2\u01ae\u01b6\u01ba\u01be"+
		"\u01c5\u01c9\u01ce\u01d1\u01d6\u01db\u01e0\u01e2\u01e8\u01ec\u01f2\u01f6"+
		"\u01fa\u01fc\u0204\u0206\u020d\u020f\u0215\u021d\u0224\u0228\u022c\u0232"+
		"\u0237\u023b\u0242\u0245\u024d\u0251\u0253\u025b\u0265\u0267";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}