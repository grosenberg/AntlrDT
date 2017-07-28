// Generated from D:/DevFiles/Eclipse/Dsl Editors/net.certiv.antlrdt/net.certiv.antlrdt.core/src/main/java/net/certiv/antlrdt/core/parser/AntlrDT4Parser.g4 by ANTLR 4.5.3

	package net.certiv.antlrdt.core.parser.gen;


import java.util.List;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;
import org.antlr.v4.runtime.tree.TerminalNode;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class AntlrDT4Parser extends Parser {
	static { RuntimeMetaData.checkVersion("4.5.3", RuntimeMetaData.VERSION); }

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
		RULE_grammarSpec = 0, RULE_grammarType = 1, RULE_prequelConstruct = 2, 
		RULE_optionsSpec = 3, RULE_option = 4, RULE_optionValue = 5, RULE_delegateGrammars = 6, 
		RULE_delegateElement = 7, RULE_delegate = 8, RULE_tokensSpec = 9, RULE_channelsSpec = 10, 
		RULE_idList = 11, RULE_idListElement = 12, RULE_action = 13, RULE_actionScopeName = 14, 
		RULE_actionBlock = 15, RULE_argActionBlock = 16, RULE_modeSpec = 17, RULE_rules = 18, 
		RULE_ruleSpec = 19, RULE_parserRuleSpec = 20, RULE_exceptionGroup = 21, 
		RULE_exceptionHandler = 22, RULE_finallyClause = 23, RULE_rulePrequel = 24, 
		RULE_ruleReturns = 25, RULE_throwsSpec = 26, RULE_localsSpec = 27, RULE_ruleAction = 28, 
		RULE_ruleModifiers = 29, RULE_ruleModifier = 30, RULE_ruleBlock = 31, 
		RULE_ruleAltList = 32, RULE_labeledAlt = 33, RULE_lexerRuleSpec = 34, 
		RULE_lexerRuleBlock = 35, RULE_lexerAltList = 36, RULE_lexerAlt = 37, 
		RULE_lexerElements = 38, RULE_lexerElement = 39, RULE_labeledLexerElement = 40, 
		RULE_lexerBlock = 41, RULE_lexerCommands = 42, RULE_lexerCommand = 43, 
		RULE_lexerCommandName = 44, RULE_lexerCommandExpr = 45, RULE_altList = 46, 
		RULE_alternative = 47, RULE_element = 48, RULE_labeledElement = 49, RULE_ebnf = 50, 
		RULE_blockSuffix = 51, RULE_ebnfSuffix = 52, RULE_lexerAtom = 53, RULE_atom = 54, 
		RULE_notSet = 55, RULE_blockSet = 56, RULE_setElement = 57, RULE_block = 58, 
		RULE_ruleref = 59, RULE_range = 60, RULE_terminal = 61, RULE_elementOptions = 62, 
		RULE_elementOption = 63, RULE_id = 64;
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

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
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
		public GrammarTypeContext grammarType() {
			return getRuleContext(GrammarTypeContext.class,0);
		}
		public IdContext id() {
			return getRuleContext(IdContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public RulesContext rules() {
			return getRuleContext(RulesContext.class,0);
		}
		public TerminalNode EOF() { return getToken(AntlrDT4Parser.EOF, 0); }
		public TerminalNode DOC_COMMENT() { return getToken(AntlrDT4Parser.DOC_COMMENT, 0); }
		public List<PrequelConstructContext> prequelConstruct() {
			return getRuleContexts(PrequelConstructContext.class);
		}
		public PrequelConstructContext prequelConstruct(int i) {
			return getRuleContext(PrequelConstructContext.class,i);
		}
		public List<ModeSpecContext> modeSpec() {
			return getRuleContexts(ModeSpecContext.class);
		}
		public ModeSpecContext modeSpec(int i) {
			return getRuleContext(ModeSpecContext.class,i);
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
			setState(131);
			_la = _input.LA(1);
			if (_la==DOC_COMMENT) {
				{
				setState(130);
				match(DOC_COMMENT);
				}
			}

			setState(133);
			grammarType();
			setState(134);
			id();
			setState(135);
			match(SEMI);
			setState(139);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << TOKENS) | (1L << CHANNELS) | (1L << IMPORT) | (1L << AT))) != 0)) {
				{
				{
				setState(136);
				prequelConstruct();
				}
				}
				setState(141);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(142);
			rules();
			setState(146);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==MODE) {
				{
				{
				setState(143);
				modeSpec();
				}
				}
				setState(148);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(149);
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

	public static class GrammarTypeContext extends ParserRuleContext {
		public TerminalNode LEXER() { return getToken(AntlrDT4Parser.LEXER, 0); }
		public TerminalNode GRAMMAR() { return getToken(AntlrDT4Parser.GRAMMAR, 0); }
		public TerminalNode PARSER() { return getToken(AntlrDT4Parser.PARSER, 0); }
		public TerminalNode XVISITOR() { return getToken(AntlrDT4Parser.XVISITOR, 0); }
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
		enterRule(_localctx, 2, RULE_grammarType);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(158);
			switch (_input.LA(1)) {
			case LEXER:
				{
				setState(151);
				match(LEXER);
				setState(152);
				match(GRAMMAR);
				}
				break;
			case PARSER:
				{
				setState(153);
				match(PARSER);
				setState(154);
				match(GRAMMAR);
				}
				break;
			case XVISITOR:
				{
				setState(155);
				match(XVISITOR);
				setState(156);
				match(GRAMMAR);
				}
				break;
			case GRAMMAR:
				{
				setState(157);
				match(GRAMMAR);
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
		enterRule(_localctx, 4, RULE_prequelConstruct);
		try {
			setState(165);
			switch (_input.LA(1)) {
			case OPTIONS:
				enterOuterAlt(_localctx, 1);
				{
				setState(160);
				optionsSpec();
				}
				break;
			case IMPORT:
				enterOuterAlt(_localctx, 2);
				{
				setState(161);
				delegateGrammars();
				}
				break;
			case TOKENS:
				enterOuterAlt(_localctx, 3);
				{
				setState(162);
				tokensSpec();
				}
				break;
			case CHANNELS:
				enterOuterAlt(_localctx, 4);
				{
				setState(163);
				channelsSpec();
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 5);
				{
				setState(164);
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
		enterRule(_localctx, 6, RULE_optionsSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(167);
			match(OPTIONS);
			setState(168);
			match(LBRACE);
			setState(172);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==TOKEN_REF || _la==RULE_REF) {
				{
				{
				setState(169);
				option();
				}
				}
				setState(174);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(175);
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
		enterRule(_localctx, 8, RULE_option);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(177);
			id();
			setState(178);
			match(ASSIGN);
			setState(179);
			optionValue();
			setState(180);
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
		enterRule(_localctx, 10, RULE_optionValue);
		int _la;
		try {
			setState(193);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(182);
				id();
				setState(187);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==DOT) {
					{
					{
					setState(183);
					match(DOT);
					setState(184);
					id();
					}
					}
					setState(189);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(190);
				match(STRING_LITERAL);
				}
				break;
			case BEGIN_ACTION:
				enterOuterAlt(_localctx, 3);
				{
				setState(191);
				actionBlock();
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 4);
				{
				setState(192);
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
		public DelegateContext delegate() {
			return getRuleContext(DelegateContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public List<DelegateElementContext> delegateElement() {
			return getRuleContexts(DelegateElementContext.class);
		}
		public DelegateElementContext delegateElement(int i) {
			return getRuleContext(DelegateElementContext.class,i);
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
		enterRule(_localctx, 12, RULE_delegateGrammars);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(195);
			match(IMPORT);
			setState(196);
			delegate();
			setState(200);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(197);
				delegateElement();
				}
				}
				setState(202);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(203);
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

	public static class DelegateElementContext extends ParserRuleContext {
		public TerminalNode COMMA() { return getToken(AntlrDT4Parser.COMMA, 0); }
		public DelegateContext delegate() {
			return getRuleContext(DelegateContext.class,0);
		}
		public DelegateElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delegateElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterDelegateElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitDelegateElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitDelegateElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DelegateElementContext delegateElement() throws RecognitionException {
		DelegateElementContext _localctx = new DelegateElementContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_delegateElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205);
			match(COMMA);
			setState(206);
			delegate();
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

	public static class DelegateContext extends ParserRuleContext {
		public IdContext lbl;
		public IdContext gram;
		public TerminalNode ASSIGN() { return getToken(AntlrDT4Parser.ASSIGN, 0); }
		public List<IdContext> id() {
			return getRuleContexts(IdContext.class);
		}
		public IdContext id(int i) {
			return getRuleContext(IdContext.class,i);
		}
		public DelegateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delegate; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterDelegate(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitDelegate(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitDelegate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final DelegateContext delegate() throws RecognitionException {
		DelegateContext _localctx = new DelegateContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_delegate);
		try {
			setState(213);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(208);
				((DelegateContext)_localctx).lbl = id();
				setState(209);
				match(ASSIGN);
				setState(210);
				((DelegateContext)_localctx).gram = id();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(212);
				((DelegateContext)_localctx).gram = id();
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
		enterRule(_localctx, 18, RULE_tokensSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(215);
			match(TOKENS);
			setState(216);
			match(LBRACE);
			setState(218);
			_la = _input.LA(1);
			if (_la==TOKEN_REF || _la==RULE_REF) {
				{
				setState(217);
				idList();
				}
			}

			setState(220);
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
		enterRule(_localctx, 20, RULE_channelsSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(222);
			match(CHANNELS);
			setState(223);
			match(LBRACE);
			setState(225);
			_la = _input.LA(1);
			if (_la==TOKEN_REF || _la==RULE_REF) {
				{
				setState(224);
				idList();
				}
			}

			setState(227);
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
		enterRule(_localctx, 22, RULE_idList);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(229);
			id();
			setState(233);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					{
					setState(230);
					idListElement();
					}
					} 
				}
				setState(235);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
			}
			setState(237);
			_la = _input.LA(1);
			if (_la==COMMA) {
				{
				setState(236);
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
		enterRule(_localctx, 24, RULE_idListElement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(239);
			match(COMMA);
			setState(240);
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
		enterRule(_localctx, 26, RULE_action);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(242);
			match(AT);
			setState(246);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,14,_ctx) ) {
			case 1:
				{
				setState(243);
				actionScopeName();
				setState(244);
				match(COLONCOLON);
				}
				break;
			}
			setState(248);
			id();
			setState(249);
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
		enterRule(_localctx, 28, RULE_actionScopeName);
		try {
			setState(254);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(251);
				id();
				}
				break;
			case LEXER:
				enterOuterAlt(_localctx, 2);
				{
				setState(252);
				match(LEXER);
				}
				break;
			case PARSER:
				enterOuterAlt(_localctx, 3);
				{
				setState(253);
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
		enterRule(_localctx, 30, RULE_actionBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256);
			match(BEGIN_ACTION);
			setState(260);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ACTION_CONTENT) {
				{
				{
				setState(257);
				match(ACTION_CONTENT);
				}
				}
				setState(262);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(263);
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
		enterRule(_localctx, 32, RULE_argActionBlock);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(265);
			match(BEGIN_ARGUMENT);
			setState(269);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ARGUMENT_CONTENT) {
				{
				{
				setState(266);
				match(ARGUMENT_CONTENT);
				}
				}
				setState(271);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(272);
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

	public static class ModeSpecContext extends ParserRuleContext {
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
		public ModeSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_modeSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).enterModeSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof AntlrDT4ParserListener ) ((AntlrDT4ParserListener)listener).exitModeSpec(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof AntlrDT4ParserVisitor ) return ((AntlrDT4ParserVisitor<? extends T>)visitor).visitModeSpec(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ModeSpecContext modeSpec() throws RecognitionException {
		ModeSpecContext _localctx = new ModeSpecContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_modeSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(274);
			match(MODE);
			setState(275);
			id();
			setState(276);
			match(SEMI);
			setState(280);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TOKEN_REF) | (1L << DOC_COMMENT) | (1L << FRAGMENT))) != 0)) {
				{
				{
				setState(277);
				lexerRuleSpec();
				}
				}
				setState(282);
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
		enterRule(_localctx, 36, RULE_rules);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(286);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << TOKEN_REF) | (1L << RULE_REF) | (1L << DOC_COMMENT) | (1L << FRAGMENT) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				{
				setState(283);
				ruleSpec();
				}
				}
				setState(288);
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
		enterRule(_localctx, 38, RULE_ruleSpec);
		try {
			setState(291);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,20,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(289);
				parserRuleSpec();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(290);
				lexerRuleSpec();
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
		public TerminalNode DOC_COMMENT() { return getToken(AntlrDT4Parser.DOC_COMMENT, 0); }
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
		enterRule(_localctx, 40, RULE_parserRuleSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(294);
			_la = _input.LA(1);
			if (_la==DOC_COMMENT) {
				{
				setState(293);
				match(DOC_COMMENT);
				}
			}

			setState(297);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FRAGMENT) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) {
				{
				setState(296);
				ruleModifiers();
				}
			}

			setState(299);
			match(RULE_REF);
			setState(301);
			_la = _input.LA(1);
			if (_la==BEGIN_ARGUMENT) {
				{
				setState(300);
				argActionBlock();
				}
			}

			setState(304);
			_la = _input.LA(1);
			if (_la==RETURNS) {
				{
				setState(303);
				ruleReturns();
				}
			}

			setState(307);
			_la = _input.LA(1);
			if (_la==THROWS) {
				{
				setState(306);
				throwsSpec();
				}
			}

			setState(310);
			_la = _input.LA(1);
			if (_la==LOCALS) {
				{
				setState(309);
				localsSpec();
				}
			}

			setState(315);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OPTIONS || _la==AT) {
				{
				{
				setState(312);
				rulePrequel();
				}
				}
				setState(317);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(318);
			match(COLON);
			setState(319);
			ruleBlock();
			setState(320);
			match(SEMI);
			setState(321);
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
		enterRule(_localctx, 42, RULE_exceptionGroup);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(326);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==CATCH) {
				{
				{
				setState(323);
				exceptionHandler();
				}
				}
				setState(328);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(330);
			_la = _input.LA(1);
			if (_la==FINALLY) {
				{
				setState(329);
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
		enterRule(_localctx, 44, RULE_exceptionHandler);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(332);
			match(CATCH);
			setState(333);
			argActionBlock();
			setState(334);
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
		enterRule(_localctx, 46, RULE_finallyClause);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(336);
			match(FINALLY);
			setState(337);
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
		enterRule(_localctx, 48, RULE_rulePrequel);
		try {
			setState(341);
			switch (_input.LA(1)) {
			case OPTIONS:
				enterOuterAlt(_localctx, 1);
				{
				setState(339);
				optionsSpec();
				}
				break;
			case AT:
				enterOuterAlt(_localctx, 2);
				{
				setState(340);
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
		enterRule(_localctx, 50, RULE_ruleReturns);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(343);
			match(RETURNS);
			setState(344);
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
		enterRule(_localctx, 52, RULE_throwsSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(346);
			match(THROWS);
			setState(347);
			id();
			setState(352);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(348);
				match(COMMA);
				setState(349);
				id();
				}
				}
				setState(354);
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
		enterRule(_localctx, 54, RULE_localsSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(355);
			match(LOCALS);
			setState(356);
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
		enterRule(_localctx, 56, RULE_ruleAction);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(358);
			match(AT);
			setState(359);
			id();
			setState(360);
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
		enterRule(_localctx, 58, RULE_ruleModifiers);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(363); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(362);
				ruleModifier();
				}
				}
				setState(365); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( (((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FRAGMENT) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE))) != 0) );
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
		public TerminalNode FRAGMENT() { return getToken(AntlrDT4Parser.FRAGMENT, 0); }
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
		enterRule(_localctx, 60, RULE_ruleModifier);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(367);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << FRAGMENT) | (1L << PROTECTED) | (1L << PUBLIC) | (1L << PRIVATE))) != 0)) ) {
			_errHandler.recoverInline(this);
			} else {
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
		enterRule(_localctx, 62, RULE_ruleBlock);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(369);
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
		enterRule(_localctx, 64, RULE_ruleAltList);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(371);
			labeledAlt();
			setState(376);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(372);
				match(OR);
				setState(373);
				labeledAlt();
				}
				}
				setState(378);
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
		enterRule(_localctx, 66, RULE_labeledAlt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(379);
			alternative();
			setState(382);
			_la = _input.LA(1);
			if (_la==POUND) {
				{
				setState(380);
				match(POUND);
				setState(381);
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

	public static class LexerRuleSpecContext extends ParserRuleContext {
		public TerminalNode TOKEN_REF() { return getToken(AntlrDT4Parser.TOKEN_REF, 0); }
		public TerminalNode COLON() { return getToken(AntlrDT4Parser.COLON, 0); }
		public LexerRuleBlockContext lexerRuleBlock() {
			return getRuleContext(LexerRuleBlockContext.class,0);
		}
		public TerminalNode SEMI() { return getToken(AntlrDT4Parser.SEMI, 0); }
		public TerminalNode DOC_COMMENT() { return getToken(AntlrDT4Parser.DOC_COMMENT, 0); }
		public TerminalNode FRAGMENT() { return getToken(AntlrDT4Parser.FRAGMENT, 0); }
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
		enterRule(_localctx, 68, RULE_lexerRuleSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(385);
			_la = _input.LA(1);
			if (_la==DOC_COMMENT) {
				{
				setState(384);
				match(DOC_COMMENT);
				}
			}

			setState(388);
			_la = _input.LA(1);
			if (_la==FRAGMENT) {
				{
				setState(387);
				match(FRAGMENT);
				}
			}

			setState(390);
			match(TOKEN_REF);
			setState(391);
			match(COLON);
			setState(392);
			lexerRuleBlock();
			setState(393);
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
			setState(395);
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
			setState(397);
			lexerAlt();
			setState(402);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(398);
				match(OR);
				setState(399);
				lexerAlt();
				}
				}
				setState(404);
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
			setState(410);
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
				setState(405);
				lexerElements();
				setState(407);
				_la = _input.LA(1);
				if (_la==RARROW) {
					{
					setState(406);
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
			setState(413); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(412);
				lexerElement();
				}
				}
				setState(415); 
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
			setState(433);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,45,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(417);
				labeledLexerElement();
				setState(419);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUESTION) | (1L << STAR) | (1L << PLUS))) != 0)) {
					{
					setState(418);
					ebnfSuffix();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(421);
				lexerAtom();
				setState(423);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUESTION) | (1L << STAR) | (1L << PLUS))) != 0)) {
					{
					setState(422);
					ebnfSuffix();
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(425);
				lexerBlock();
				setState(427);
				_la = _input.LA(1);
				if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUESTION) | (1L << STAR) | (1L << PLUS))) != 0)) {
					{
					setState(426);
					ebnfSuffix();
					}
				}

				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(429);
				actionBlock();
				setState(431);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(430);
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
		public BlockContext block() {
			return getRuleContext(BlockContext.class,0);
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
			setState(435);
			id();
			setState(436);
			_la = _input.LA(1);
			if ( !(_la==ASSIGN || _la==PLUS_ASSIGN) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(439);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case LEXER_CHAR_SET:
			case STRING_LITERAL:
			case DOT:
			case NOT:
				{
				setState(437);
				lexerAtom();
				}
				break;
			case LPAREN:
				{
				setState(438);
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
			setState(441);
			match(LPAREN);
			setState(442);
			lexerAltList();
			setState(443);
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
			setState(445);
			match(RARROW);
			setState(446);
			lexerCommand();
			setState(451);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(447);
				match(COMMA);
				setState(448);
				lexerCommand();
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
		try {
			setState(460);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,48,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(454);
				lexerCommandName();
				setState(455);
				match(LPAREN);
				setState(456);
				lexerCommandExpr();
				setState(457);
				match(RPAREN);
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(459);
				lexerCommandName();
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
			setState(464);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(462);
				id();
				}
				break;
			case MODE:
				enterOuterAlt(_localctx, 2);
				{
				setState(463);
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
			setState(468);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(466);
				id();
				}
				break;
			case INT:
				enterOuterAlt(_localctx, 2);
				{
				setState(467);
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
			setState(470);
			alternative();
			setState(475);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(471);
				match(OR);
				setState(472);
				alternative();
				}
				}
				setState(477);
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
			setState(487);
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
				setState(479);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(478);
					elementOptions();
					}
				}

				setState(482); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(481);
					element();
					}
					}
					setState(484); 
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
			setState(504);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,58,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(489);
				labeledElement();
				setState(492);
				switch (_input.LA(1)) {
				case QUESTION:
				case STAR:
				case PLUS:
					{
					setState(490);
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
				setState(494);
				atom();
				setState(497);
				switch (_input.LA(1)) {
				case QUESTION:
				case STAR:
				case PLUS:
					{
					setState(495);
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
				setState(499);
				ebnf();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(500);
				actionBlock();
				setState(502);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(501);
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
			setState(506);
			id();
			setState(507);
			_la = _input.LA(1);
			if ( !(_la==ASSIGN || _la==PLUS_ASSIGN) ) {
			_errHandler.recoverInline(this);
			} else {
				consume();
			}
			setState(510);
			switch (_input.LA(1)) {
			case TOKEN_REF:
			case RULE_REF:
			case STRING_LITERAL:
			case DOT:
			case NOT:
				{
				setState(508);
				atom();
				}
				break;
			case LPAREN:
				{
				setState(509);
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
			setState(512);
			block();
			setState(514);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << QUESTION) | (1L << STAR) | (1L << PLUS))) != 0)) {
				{
				setState(513);
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
			setState(516);
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
			setState(530);
			switch (_input.LA(1)) {
			case QUESTION:
				enterOuterAlt(_localctx, 1);
				{
				setState(518);
				match(QUESTION);
				setState(520);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(519);
					match(QUESTION);
					}
				}

				}
				break;
			case STAR:
				enterOuterAlt(_localctx, 2);
				{
				setState(522);
				match(STAR);
				setState(524);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(523);
					match(QUESTION);
					}
				}

				}
				break;
			case PLUS:
				enterOuterAlt(_localctx, 3);
				{
				setState(526);
				match(PLUS);
				setState(528);
				_la = _input.LA(1);
				if (_la==QUESTION) {
					{
					setState(527);
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
		public TerminalNode LEXER_CHAR_SET() { return getToken(AntlrDT4Parser.LEXER_CHAR_SET, 0); }
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
			setState(540);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,66,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(532);
				range();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(533);
				terminal();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(534);
				notSet();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(535);
				match(LEXER_CHAR_SET);
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(536);
				match(DOT);
				setState(538);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(537);
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
		public RangeContext range() {
			return getRuleContext(RangeContext.class,0);
		}
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
			setState(550);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,68,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(542);
				range();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(543);
				terminal();
				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(544);
				ruleref();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(545);
				notSet();
				}
				break;
			case 5:
				enterOuterAlt(_localctx, 5);
				{
				setState(546);
				match(DOT);
				setState(548);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(547);
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
			setState(556);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,69,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(552);
				match(NOT);
				setState(553);
				setElement();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(554);
				match(NOT);
				setState(555);
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
			setState(558);
			match(LPAREN);
			setState(559);
			setElement();
			setState(564);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==OR) {
				{
				{
				setState(560);
				match(OR);
				setState(561);
				setElement();
				}
				}
				setState(566);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(567);
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
		public TerminalNode LEXER_CHAR_SET() { return getToken(AntlrDT4Parser.LEXER_CHAR_SET, 0); }
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
			setState(579);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,73,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(569);
				match(TOKEN_REF);
				setState(571);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(570);
					elementOptions();
					}
				}

				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(573);
				match(STRING_LITERAL);
				setState(575);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(574);
					elementOptions();
					}
				}

				}
				break;
			case 3:
				enterOuterAlt(_localctx, 3);
				{
				setState(577);
				range();
				}
				break;
			case 4:
				enterOuterAlt(_localctx, 4);
				{
				setState(578);
				match(LEXER_CHAR_SET);
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
		enterRule(_localctx, 116, RULE_block);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(581);
			match(LPAREN);
			setState(592);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << OPTIONS) | (1L << COLON) | (1L << AT))) != 0)) {
				{
				setState(583);
				_la = _input.LA(1);
				if (_la==OPTIONS) {
					{
					setState(582);
					optionsSpec();
					}
				}

				setState(588);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==AT) {
					{
					{
					setState(585);
					ruleAction();
					}
					}
					setState(590);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(591);
				match(COLON);
				}
			}

			setState(594);
			altList();
			setState(595);
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
		enterRule(_localctx, 118, RULE_ruleref);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(597);
			match(RULE_REF);
			setState(599);
			_la = _input.LA(1);
			if (_la==BEGIN_ARGUMENT) {
				{
				setState(598);
				argActionBlock();
				}
			}

			setState(602);
			_la = _input.LA(1);
			if (_la==LT) {
				{
				setState(601);
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
		enterRule(_localctx, 120, RULE_range);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(604);
			match(STRING_LITERAL);
			setState(605);
			match(RANGE);
			setState(606);
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
		enterRule(_localctx, 122, RULE_terminal);
		int _la;
		try {
			setState(616);
			switch (_input.LA(1)) {
			case TOKEN_REF:
				enterOuterAlt(_localctx, 1);
				{
				setState(608);
				match(TOKEN_REF);
				setState(610);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(609);
					elementOptions();
					}
				}

				}
				break;
			case STRING_LITERAL:
				enterOuterAlt(_localctx, 2);
				{
				setState(612);
				match(STRING_LITERAL);
				setState(614);
				_la = _input.LA(1);
				if (_la==LT) {
					{
					setState(613);
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
		enterRule(_localctx, 124, RULE_elementOptions);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(618);
			match(LT);
			setState(619);
			elementOption();
			setState(624);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==COMMA) {
				{
				{
				setState(620);
				match(COMMA);
				setState(621);
				elementOption();
				}
				}
				setState(626);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(627);
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
		enterRule(_localctx, 126, RULE_elementOption);
		try {
			setState(636);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,84,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(629);
				id();
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(630);
				id();
				setState(631);
				match(ASSIGN);
				setState(634);
				switch (_input.LA(1)) {
				case TOKEN_REF:
				case RULE_REF:
					{
					setState(632);
					id();
					}
					break;
				case STRING_LITERAL:
					{
					setState(633);
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
		enterRule(_localctx, 128, RULE_id);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(638);
			_la = _input.LA(1);
			if ( !(_la==TOKEN_REF || _la==RULE_REF) ) {
			_errHandler.recoverInline(this);
			} else {
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3@\u0283\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4\13\t"+
		"\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22\t\22"+
		"\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27\4\30\t\30\4\31\t\31"+
		"\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36\4\37\t\37\4 \t \4!"+
		"\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4(\t(\4)\t)\4*\t*\4+\t+\4"+
		",\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62\t\62\4\63\t\63\4\64\t"+
		"\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4:\t:\4;\t;\4<\t<\4=\t="+
		"\4>\t>\4?\t?\4@\t@\4A\tA\4B\tB\3\2\5\2\u0086\n\2\3\2\3\2\3\2\3\2\7\2\u008c"+
		"\n\2\f\2\16\2\u008f\13\2\3\2\3\2\7\2\u0093\n\2\f\2\16\2\u0096\13\2\3\2"+
		"\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3\3\5\3\u00a1\n\3\3\4\3\4\3\4\3\4\3\4\5\4"+
		"\u00a8\n\4\3\5\3\5\3\5\7\5\u00ad\n\5\f\5\16\5\u00b0\13\5\3\5\3\5\3\6\3"+
		"\6\3\6\3\6\3\6\3\7\3\7\3\7\7\7\u00bc\n\7\f\7\16\7\u00bf\13\7\3\7\3\7\3"+
		"\7\5\7\u00c4\n\7\3\b\3\b\3\b\7\b\u00c9\n\b\f\b\16\b\u00cc\13\b\3\b\3\b"+
		"\3\t\3\t\3\t\3\n\3\n\3\n\3\n\3\n\5\n\u00d8\n\n\3\13\3\13\3\13\5\13\u00dd"+
		"\n\13\3\13\3\13\3\f\3\f\3\f\5\f\u00e4\n\f\3\f\3\f\3\r\3\r\7\r\u00ea\n"+
		"\r\f\r\16\r\u00ed\13\r\3\r\5\r\u00f0\n\r\3\16\3\16\3\16\3\17\3\17\3\17"+
		"\3\17\5\17\u00f9\n\17\3\17\3\17\3\17\3\20\3\20\3\20\5\20\u0101\n\20\3"+
		"\21\3\21\7\21\u0105\n\21\f\21\16\21\u0108\13\21\3\21\3\21\3\22\3\22\7"+
		"\22\u010e\n\22\f\22\16\22\u0111\13\22\3\22\3\22\3\23\3\23\3\23\3\23\7"+
		"\23\u0119\n\23\f\23\16\23\u011c\13\23\3\24\7\24\u011f\n\24\f\24\16\24"+
		"\u0122\13\24\3\25\3\25\5\25\u0126\n\25\3\26\5\26\u0129\n\26\3\26\5\26"+
		"\u012c\n\26\3\26\3\26\5\26\u0130\n\26\3\26\5\26\u0133\n\26\3\26\5\26\u0136"+
		"\n\26\3\26\5\26\u0139\n\26\3\26\7\26\u013c\n\26\f\26\16\26\u013f\13\26"+
		"\3\26\3\26\3\26\3\26\3\26\3\27\7\27\u0147\n\27\f\27\16\27\u014a\13\27"+
		"\3\27\5\27\u014d\n\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\32\3\32\5\32"+
		"\u0158\n\32\3\33\3\33\3\33\3\34\3\34\3\34\3\34\7\34\u0161\n\34\f\34\16"+
		"\34\u0164\13\34\3\35\3\35\3\35\3\36\3\36\3\36\3\36\3\37\6\37\u016e\n\37"+
		"\r\37\16\37\u016f\3 \3 \3!\3!\3\"\3\"\3\"\7\"\u0179\n\"\f\"\16\"\u017c"+
		"\13\"\3#\3#\3#\5#\u0181\n#\3$\5$\u0184\n$\3$\5$\u0187\n$\3$\3$\3$\3$\3"+
		"$\3%\3%\3&\3&\3&\7&\u0193\n&\f&\16&\u0196\13&\3\'\3\'\5\'\u019a\n\'\3"+
		"\'\5\'\u019d\n\'\3(\6(\u01a0\n(\r(\16(\u01a1\3)\3)\5)\u01a6\n)\3)\3)\5"+
		")\u01aa\n)\3)\3)\5)\u01ae\n)\3)\3)\5)\u01b2\n)\5)\u01b4\n)\3*\3*\3*\3"+
		"*\5*\u01ba\n*\3+\3+\3+\3+\3,\3,\3,\3,\7,\u01c4\n,\f,\16,\u01c7\13,\3-"+
		"\3-\3-\3-\3-\3-\5-\u01cf\n-\3.\3.\5.\u01d3\n.\3/\3/\5/\u01d7\n/\3\60\3"+
		"\60\3\60\7\60\u01dc\n\60\f\60\16\60\u01df\13\60\3\61\5\61\u01e2\n\61\3"+
		"\61\6\61\u01e5\n\61\r\61\16\61\u01e6\3\61\5\61\u01ea\n\61\3\62\3\62\3"+
		"\62\5\62\u01ef\n\62\3\62\3\62\3\62\5\62\u01f4\n\62\3\62\3\62\3\62\5\62"+
		"\u01f9\n\62\5\62\u01fb\n\62\3\63\3\63\3\63\3\63\5\63\u0201\n\63\3\64\3"+
		"\64\5\64\u0205\n\64\3\65\3\65\3\66\3\66\5\66\u020b\n\66\3\66\3\66\5\66"+
		"\u020f\n\66\3\66\3\66\5\66\u0213\n\66\5\66\u0215\n\66\3\67\3\67\3\67\3"+
		"\67\3\67\3\67\5\67\u021d\n\67\5\67\u021f\n\67\38\38\38\38\38\38\58\u0227"+
		"\n8\58\u0229\n8\39\39\39\39\59\u022f\n9\3:\3:\3:\3:\7:\u0235\n:\f:\16"+
		":\u0238\13:\3:\3:\3;\3;\5;\u023e\n;\3;\3;\5;\u0242\n;\3;\3;\5;\u0246\n"+
		";\3<\3<\5<\u024a\n<\3<\7<\u024d\n<\f<\16<\u0250\13<\3<\5<\u0253\n<\3<"+
		"\3<\3<\3=\3=\5=\u025a\n=\3=\5=\u025d\n=\3>\3>\3>\3>\3?\3?\5?\u0265\n?"+
		"\3?\3?\5?\u0269\n?\5?\u026b\n?\3@\3@\3@\3@\7@\u0271\n@\f@\16@\u0274\13"+
		"@\3@\3@\3A\3A\3A\3A\3A\5A\u027d\nA\5A\u027f\nA\3B\3B\3B\2\2C\2\4\6\b\n"+
		"\f\16\20\22\24\26\30\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJLNPRTVXZ\\"+
		"^`bdfhjlnprtvxz|~\u0080\u0082\2\5\4\2\22\22\27\31\4\2++..\3\2\3\4\u02ab"+
		"\2\u0085\3\2\2\2\4\u00a0\3\2\2\2\6\u00a7\3\2\2\2\b\u00a9\3\2\2\2\n\u00b3"+
		"\3\2\2\2\f\u00c3\3\2\2\2\16\u00c5\3\2\2\2\20\u00cf\3\2\2\2\22\u00d7\3"+
		"\2\2\2\24\u00d9\3\2\2\2\26\u00e0\3\2\2\2\30\u00e7\3\2\2\2\32\u00f1\3\2"+
		"\2\2\34\u00f4\3\2\2\2\36\u0100\3\2\2\2 \u0102\3\2\2\2\"\u010b\3\2\2\2"+
		"$\u0114\3\2\2\2&\u0120\3\2\2\2(\u0125\3\2\2\2*\u0128\3\2\2\2,\u0148\3"+
		"\2\2\2.\u014e\3\2\2\2\60\u0152\3\2\2\2\62\u0157\3\2\2\2\64\u0159\3\2\2"+
		"\2\66\u015c\3\2\2\28\u0165\3\2\2\2:\u0168\3\2\2\2<\u016d\3\2\2\2>\u0171"+
		"\3\2\2\2@\u0173\3\2\2\2B\u0175\3\2\2\2D\u017d\3\2\2\2F\u0183\3\2\2\2H"+
		"\u018d\3\2\2\2J\u018f\3\2\2\2L\u019c\3\2\2\2N\u019f\3\2\2\2P\u01b3\3\2"+
		"\2\2R\u01b5\3\2\2\2T\u01bb\3\2\2\2V\u01bf\3\2\2\2X\u01ce\3\2\2\2Z\u01d2"+
		"\3\2\2\2\\\u01d6\3\2\2\2^\u01d8\3\2\2\2`\u01e9\3\2\2\2b\u01fa\3\2\2\2"+
		"d\u01fc\3\2\2\2f\u0202\3\2\2\2h\u0206\3\2\2\2j\u0214\3\2\2\2l\u021e\3"+
		"\2\2\2n\u0228\3\2\2\2p\u022e\3\2\2\2r\u0230\3\2\2\2t\u0245\3\2\2\2v\u0247"+
		"\3\2\2\2x\u0257\3\2\2\2z\u025e\3\2\2\2|\u026a\3\2\2\2~\u026c\3\2\2\2\u0080"+
		"\u027e\3\2\2\2\u0082\u0280\3\2\2\2\u0084\u0086\7\6\2\2\u0085\u0084\3\2"+
		"\2\2\u0085\u0086\3\2\2\2\u0086\u0087\3\2\2\2\u0087\u0088\5\4\3\2\u0088"+
		"\u0089\5\u0082B\2\u0089\u008d\7#\2\2\u008a\u008c\5\6\4\2\u008b\u008a\3"+
		"\2\2\2\u008c\u008f\3\2\2\2\u008d\u008b\3\2\2\2\u008d\u008e\3\2\2\2\u008e"+
		"\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0094\5&\24\2\u0091\u0093\5$"+
		"\23\2\u0092\u0091\3\2\2\2\u0093\u0096\3\2\2\2\u0094\u0092\3\2\2\2\u0094"+
		"\u0095\3\2\2\2\u0095\u0097\3\2\2\2\u0096\u0094\3\2\2\2\u0097\u0098\7\2"+
		"\2\3\u0098\3\3\2\2\2\u0099\u009a\7\23\2\2\u009a\u00a1\7\26\2\2\u009b\u009c"+
		"\7\24\2\2\u009c\u00a1\7\26\2\2\u009d\u009e\7\25\2\2\u009e\u00a1\7\26\2"+
		"\2\u009f\u00a1\7\26\2\2\u00a0\u0099\3\2\2\2\u00a0\u009b\3\2\2\2\u00a0"+
		"\u009d\3\2\2\2\u00a0\u009f\3\2\2\2\u00a1\5\3\2\2\2\u00a2\u00a8\5\b\5\2"+
		"\u00a3\u00a8\5\16\b\2\u00a4\u00a8\5\24\13\2\u00a5\u00a8\5\26\f\2\u00a6"+
		"\u00a8\5\34\17\2\u00a7\u00a2\3\2\2\2\u00a7\u00a3\3\2\2\2\u00a7\u00a4\3"+
		"\2\2\2\u00a7\u00a5\3\2\2\2\u00a7\u00a6\3\2\2\2\u00a8\7\3\2\2\2\u00a9\u00aa"+
		"\7\16\2\2\u00aa\u00ae\7&\2\2\u00ab\u00ad\5\n\6\2\u00ac\u00ab\3\2\2\2\u00ad"+
		"\u00b0\3\2\2\2\u00ae\u00ac\3\2\2\2\u00ae\u00af\3\2\2\2\u00af\u00b1\3\2"+
		"\2\2\u00b0\u00ae\3\2\2\2\u00b1\u00b2\7\'\2\2\u00b2\t\3\2\2\2\u00b3\u00b4"+
		"\5\u0082B\2\u00b4\u00b5\7+\2\2\u00b5\u00b6\5\f\7\2\u00b6\u00b7\7#\2\2"+
		"\u00b7\13\3\2\2\2\u00b8\u00bd\5\u0082B\2\u00b9\u00ba\7\63\2\2\u00ba\u00bc"+
		"\5\u0082B\2\u00bb\u00b9\3\2\2\2\u00bc\u00bf\3\2\2\2\u00bd\u00bb\3\2\2"+
		"\2\u00bd\u00be\3\2\2\2\u00be\u00c4\3\2\2\2\u00bf\u00bd\3\2\2\2\u00c0\u00c4"+
		"\7\n\2\2\u00c1\u00c4\5 \21\2\u00c2\u00c4\7\t\2\2\u00c3\u00b8\3\2\2\2\u00c3"+
		"\u00c0\3\2\2\2\u00c3\u00c1\3\2\2\2\u00c3\u00c2\3\2\2\2\u00c4\r\3\2\2\2"+
		"\u00c5\u00c6\7\21\2\2\u00c6\u00ca\5\22\n\2\u00c7\u00c9\5\20\t\2\u00c8"+
		"\u00c7\3\2\2\2\u00c9\u00cc\3\2\2\2\u00ca\u00c8\3\2\2\2\u00ca\u00cb\3\2"+
		"\2\2\u00cb\u00cd\3\2\2\2\u00cc\u00ca\3\2\2\2\u00cd\u00ce\7#\2\2\u00ce"+
		"\17\3\2\2\2\u00cf\u00d0\7\"\2\2\u00d0\u00d1\5\22\n\2\u00d1\21\3\2\2\2"+
		"\u00d2\u00d3\5\u0082B\2\u00d3\u00d4\7+\2\2\u00d4\u00d5\5\u0082B\2\u00d5"+
		"\u00d8\3\2\2\2\u00d6\u00d8\5\u0082B\2\u00d7\u00d2\3\2\2\2\u00d7\u00d6"+
		"\3\2\2\2\u00d8\23\3\2\2\2\u00d9\u00da\7\17\2\2\u00da\u00dc\7&\2\2\u00db"+
		"\u00dd\5\30\r\2\u00dc\u00db\3\2\2\2\u00dc\u00dd\3\2\2\2\u00dd\u00de\3"+
		"\2\2\2\u00de\u00df\7\'\2\2\u00df\25\3\2\2\2\u00e0\u00e1\7\20\2\2\u00e1"+
		"\u00e3\7&\2\2\u00e2\u00e4\5\30\r\2\u00e3\u00e2\3\2\2\2\u00e3\u00e4\3\2"+
		"\2\2\u00e4\u00e5\3\2\2\2\u00e5\u00e6\7\'\2\2\u00e6\27\3\2\2\2\u00e7\u00eb"+
		"\5\u0082B\2\u00e8\u00ea\5\32\16\2\u00e9\u00e8\3\2\2\2\u00ea\u00ed\3\2"+
		"\2\2\u00eb\u00e9\3\2\2\2\u00eb\u00ec\3\2\2\2\u00ec\u00ef\3\2\2\2\u00ed"+
		"\u00eb\3\2\2\2\u00ee\u00f0\7\"\2\2\u00ef\u00ee\3\2\2\2\u00ef\u00f0\3\2"+
		"\2\2\u00f0\31\3\2\2\2\u00f1\u00f2\7\"\2\2\u00f2\u00f3\5\u0082B\2\u00f3"+
		"\33\3\2\2\2\u00f4\u00f8\7\64\2\2\u00f5\u00f6\5\36\20\2\u00f6\u00f7\7!"+
		"\2\2\u00f7\u00f9\3\2\2\2\u00f8\u00f5\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9"+
		"\u00fa\3\2\2\2\u00fa\u00fb\5\u0082B\2\u00fb\u00fc\5 \21\2\u00fc\35\3\2"+
		"\2\2\u00fd\u0101\5\u0082B\2\u00fe\u0101\7\23\2\2\u00ff\u0101\7\24\2\2"+
		"\u0100\u00fd\3\2\2\2\u0100\u00fe\3\2\2\2\u0100\u00ff\3\2\2\2\u0101\37"+
		"\3\2\2\2\u0102\u0106\7\r\2\2\u0103\u0105\7?\2\2\u0104\u0103\3\2\2\2\u0105"+
		"\u0108\3\2\2\2\u0106\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0109\3\2"+
		"\2\2\u0108\u0106\3\2\2\2\u0109\u010a\7=\2\2\u010a!\3\2\2\2\u010b\u010f"+
		"\7\f\2\2\u010c\u010e\7<\2\2\u010d\u010c\3\2\2\2\u010e\u0111\3\2\2\2\u010f"+
		"\u010d\3\2\2\2\u010f\u0110\3\2\2\2\u0110\u0112\3\2\2\2\u0111\u010f\3\2"+
		"\2\2\u0112\u0113\7:\2\2\u0113#\3\2\2\2\u0114\u0115\7\37\2\2\u0115\u0116"+
		"\5\u0082B\2\u0116\u011a\7#\2\2\u0117\u0119\5F$\2\u0118\u0117\3\2\2\2\u0119"+
		"\u011c\3\2\2\2\u011a\u0118\3\2\2\2\u011a\u011b\3\2\2\2\u011b%\3\2\2\2"+
		"\u011c\u011a\3\2\2\2\u011d\u011f\5(\25\2\u011e\u011d\3\2\2\2\u011f\u0122"+
		"\3\2\2\2\u0120\u011e\3\2\2\2\u0120\u0121\3\2\2\2\u0121\'\3\2\2\2\u0122"+
		"\u0120\3\2\2\2\u0123\u0126\5*\26\2\u0124\u0126\5F$\2\u0125\u0123\3\2\2"+
		"\2\u0125\u0124\3\2\2\2\u0126)\3\2\2\2\u0127\u0129\7\6\2\2\u0128\u0127"+
		"\3\2\2\2\u0128\u0129\3\2\2\2\u0129\u012b\3\2\2\2\u012a\u012c\5<\37\2\u012b"+
		"\u012a\3\2\2\2\u012b\u012c\3\2\2\2\u012c\u012d\3\2\2\2\u012d\u012f\7\4"+
		"\2\2\u012e\u0130\5\"\22\2\u012f\u012e\3\2\2\2\u012f\u0130\3\2\2\2\u0130"+
		"\u0132\3\2\2\2\u0131\u0133\5\64\33\2\u0132\u0131\3\2\2\2\u0132\u0133\3"+
		"\2\2\2\u0133\u0135\3\2\2\2\u0134\u0136\5\66\34\2\u0135\u0134\3\2\2\2\u0135"+
		"\u0136\3\2\2\2\u0136\u0138\3\2\2\2\u0137\u0139\58\35\2\u0138\u0137\3\2"+
		"\2\2\u0138\u0139\3\2\2\2\u0139\u013d\3\2\2\2\u013a\u013c\5\62\32\2\u013b"+
		"\u013a\3\2\2\2\u013c\u013f\3\2\2\2\u013d\u013b\3\2\2\2\u013d\u013e\3\2"+
		"\2\2\u013e\u0140\3\2\2\2\u013f\u013d\3\2\2\2\u0140\u0141\7 \2\2\u0141"+
		"\u0142\5@!\2\u0142\u0143\7#\2\2\u0143\u0144\5,\27\2\u0144+\3\2\2\2\u0145"+
		"\u0147\5.\30\2\u0146\u0145\3\2\2\2\u0147\u014a\3\2\2\2\u0148\u0146\3\2"+
		"\2\2\u0148\u0149\3\2\2\2\u0149\u014c\3\2\2\2\u014a\u0148\3\2\2\2\u014b"+
		"\u014d\5\60\31\2\u014c\u014b\3\2\2\2\u014c\u014d\3\2\2\2\u014d-\3\2\2"+
		"\2\u014e\u014f\7\35\2\2\u014f\u0150\5\"\22\2\u0150\u0151\5 \21\2\u0151"+
		"/\3\2\2\2\u0152\u0153\7\36\2\2\u0153\u0154\5 \21\2\u0154\61\3\2\2\2\u0155"+
		"\u0158\5\b\5\2\u0156\u0158\5:\36\2\u0157\u0155\3\2\2\2\u0157\u0156\3\2"+
		"\2\2\u0158\63\3\2\2\2\u0159\u015a\7\32\2\2\u015a\u015b\5\"\22\2\u015b"+
		"\65\3\2\2\2\u015c\u015d\7\34\2\2\u015d\u0162\5\u0082B\2\u015e\u015f\7"+
		"\"\2\2\u015f\u0161\5\u0082B\2\u0160\u015e\3\2\2\2\u0161\u0164\3\2\2\2"+
		"\u0162\u0160\3\2\2\2\u0162\u0163\3\2\2\2\u0163\67\3\2\2\2\u0164\u0162"+
		"\3\2\2\2\u0165\u0166\7\33\2\2\u0166\u0167\5\"\22\2\u01679\3\2\2\2\u0168"+
		"\u0169\7\64\2\2\u0169\u016a\5\u0082B\2\u016a\u016b\5 \21\2\u016b;\3\2"+
		"\2\2\u016c\u016e\5> \2\u016d\u016c\3\2\2\2\u016e\u016f\3\2\2\2\u016f\u016d"+
		"\3\2\2\2\u016f\u0170\3\2\2\2\u0170=\3\2\2\2\u0171\u0172\t\2\2\2\u0172"+
		"?\3\2\2\2\u0173\u0174\5B\"\2\u0174A\3\2\2\2\u0175\u017a\5D#\2\u0176\u0177"+
		"\7\60\2\2\u0177\u0179\5D#\2\u0178\u0176\3\2\2\2\u0179\u017c\3\2\2\2\u017a"+
		"\u0178\3\2\2\2\u017a\u017b\3\2\2\2\u017bC\3\2\2\2\u017c\u017a\3\2\2\2"+
		"\u017d\u0180\5`\61\2\u017e\u017f\7\65\2\2\u017f\u0181\5\u0082B\2\u0180"+
		"\u017e\3\2\2\2\u0180\u0181\3\2\2\2\u0181E\3\2\2\2\u0182\u0184\7\6\2\2"+
		"\u0183\u0182\3\2\2\2\u0183\u0184\3\2\2\2\u0184\u0186\3\2\2\2\u0185\u0187"+
		"\7\22\2\2\u0186\u0185\3\2\2\2\u0186\u0187\3\2\2\2\u0187\u0188\3\2\2\2"+
		"\u0188\u0189\7\3\2\2\u0189\u018a\7 \2\2\u018a\u018b\5H%\2\u018b\u018c"+
		"\7#\2\2\u018cG\3\2\2\2\u018d\u018e\5J&\2\u018eI\3\2\2\2\u018f\u0194\5"+
		"L\'\2\u0190\u0191\7\60\2\2\u0191\u0193\5L\'\2\u0192\u0190\3\2\2\2\u0193"+
		"\u0196\3\2\2\2\u0194\u0192\3\2\2\2\u0194\u0195\3\2\2\2\u0195K\3\2\2\2"+
		"\u0196\u0194\3\2\2\2\u0197\u0199\5N(\2\u0198\u019a\5V,\2\u0199\u0198\3"+
		"\2\2\2\u0199\u019a\3\2\2\2\u019a\u019d\3\2\2\2\u019b\u019d\3\2\2\2\u019c"+
		"\u0197\3\2\2\2\u019c\u019b\3\2\2\2\u019dM\3\2\2\2\u019e\u01a0\5P)\2\u019f"+
		"\u019e\3\2\2\2\u01a0\u01a1\3\2\2\2\u01a1\u019f\3\2\2\2\u01a1\u01a2\3\2"+
		"\2\2\u01a2O\3\2\2\2\u01a3\u01a5\5R*\2\u01a4\u01a6\5j\66\2\u01a5\u01a4"+
		"\3\2\2\2\u01a5\u01a6\3\2\2\2\u01a6\u01b4\3\2\2\2\u01a7\u01a9\5l\67\2\u01a8"+
		"\u01aa\5j\66\2\u01a9\u01a8\3\2\2\2\u01a9\u01aa\3\2\2\2\u01aa\u01b4\3\2"+
		"\2\2\u01ab\u01ad\5T+\2\u01ac\u01ae\5j\66\2\u01ad\u01ac\3\2\2\2\u01ad\u01ae"+
		"\3\2\2\2\u01ae\u01b4\3\2\2\2\u01af\u01b1\5 \21\2\u01b0\u01b2\7,\2\2\u01b1"+
		"\u01b0\3\2\2\2\u01b1\u01b2\3\2\2\2\u01b2\u01b4\3\2\2\2\u01b3\u01a3\3\2"+
		"\2\2\u01b3\u01a7\3\2\2\2\u01b3\u01ab\3\2\2\2\u01b3\u01af\3\2\2\2\u01b4"+
		"Q\3\2\2\2\u01b5\u01b6\5\u0082B\2\u01b6\u01b9\t\3\2\2\u01b7\u01ba\5l\67"+
		"\2\u01b8\u01ba\5v<\2\u01b9\u01b7\3\2\2\2\u01b9\u01b8\3\2\2\2\u01baS\3"+
		"\2\2\2\u01bb\u01bc\7$\2\2\u01bc\u01bd\5J&\2\u01bd\u01be\7%\2\2\u01beU"+
		"\3\2\2\2\u01bf\u01c0\7(\2\2\u01c0\u01c5\5X-\2\u01c1\u01c2\7\"\2\2\u01c2"+
		"\u01c4\5X-\2\u01c3\u01c1\3\2\2\2\u01c4\u01c7\3\2\2\2\u01c5\u01c3\3\2\2"+
		"\2\u01c5\u01c6\3\2\2\2\u01c6W\3\2\2\2\u01c7\u01c5\3\2\2\2\u01c8\u01c9"+
		"\5Z.\2\u01c9\u01ca\7$\2\2\u01ca\u01cb\5\\/\2\u01cb\u01cc\7%\2\2\u01cc"+
		"\u01cf\3\2\2\2\u01cd\u01cf\5Z.\2\u01ce\u01c8\3\2\2\2\u01ce\u01cd\3\2\2"+
		"\2\u01cfY\3\2\2\2\u01d0\u01d3\5\u0082B\2\u01d1\u01d3\7\37\2\2\u01d2\u01d0"+
		"\3\2\2\2\u01d2\u01d1\3\2\2\2\u01d3[\3\2\2\2\u01d4\u01d7\5\u0082B\2\u01d5"+
		"\u01d7\7\t\2\2\u01d6\u01d4\3\2\2\2\u01d6\u01d5\3\2\2\2\u01d7]\3\2\2\2"+
		"\u01d8\u01dd\5`\61\2\u01d9\u01da\7\60\2\2\u01da\u01dc\5`\61\2\u01db\u01d9"+
		"\3\2\2\2\u01dc\u01df\3\2\2\2\u01dd\u01db\3\2\2\2\u01dd\u01de\3\2\2\2\u01de"+
		"_\3\2\2\2\u01df\u01dd\3\2\2\2\u01e0\u01e2\5~@\2\u01e1\u01e0\3\2\2\2\u01e1"+
		"\u01e2\3\2\2\2\u01e2\u01e4\3\2\2\2\u01e3\u01e5\5b\62\2\u01e4\u01e3\3\2"+
		"\2\2\u01e5\u01e6\3\2\2\2\u01e6\u01e4\3\2\2\2\u01e6\u01e7\3\2\2\2\u01e7"+
		"\u01ea\3\2\2\2\u01e8\u01ea\3\2\2\2\u01e9\u01e1\3\2\2\2\u01e9\u01e8\3\2"+
		"\2\2\u01eaa\3\2\2\2\u01eb\u01ee\5d\63\2\u01ec\u01ef\5j\66\2\u01ed\u01ef"+
		"\3\2\2\2\u01ee\u01ec\3\2\2\2\u01ee\u01ed\3\2\2\2\u01ef\u01fb\3\2\2\2\u01f0"+
		"\u01f3\5n8\2\u01f1\u01f4\5j\66\2\u01f2\u01f4\3\2\2\2\u01f3\u01f1\3\2\2"+
		"\2\u01f3\u01f2\3\2\2\2\u01f4\u01fb\3\2\2\2\u01f5\u01fb\5f\64\2\u01f6\u01f8"+
		"\5 \21\2\u01f7\u01f9\7,\2\2\u01f8\u01f7\3\2\2\2\u01f8\u01f9\3\2\2\2\u01f9"+
		"\u01fb\3\2\2\2\u01fa\u01eb\3\2\2\2\u01fa\u01f0\3\2\2\2\u01fa\u01f5\3\2"+
		"\2\2\u01fa\u01f6\3\2\2\2\u01fbc\3\2\2\2\u01fc\u01fd\5\u0082B\2\u01fd\u0200"+
		"\t\3\2\2\u01fe\u0201\5n8\2\u01ff\u0201\5v<\2\u0200\u01fe\3\2\2\2\u0200"+
		"\u01ff\3\2\2\2\u0201e\3\2\2\2\u0202\u0204\5v<\2\u0203\u0205\5h\65\2\u0204"+
		"\u0203\3\2\2\2\u0204\u0205\3\2\2\2\u0205g\3\2\2\2\u0206\u0207\5j\66\2"+
		"\u0207i\3\2\2\2\u0208\u020a\7,\2\2\u0209\u020b\7,\2\2\u020a\u0209\3\2"+
		"\2\2\u020a\u020b\3\2\2\2\u020b\u0215\3\2\2\2\u020c\u020e\7-\2\2\u020d"+
		"\u020f\7,\2\2\u020e\u020d\3\2\2\2\u020e\u020f\3\2\2\2\u020f\u0215\3\2"+
		"\2\2\u0210\u0212\7/\2\2\u0211\u0213\7,\2\2\u0212\u0211\3\2\2\2\u0212\u0213"+
		"\3\2\2\2\u0213\u0215\3\2\2\2\u0214\u0208\3\2\2\2\u0214\u020c\3\2\2\2\u0214"+
		"\u0210\3\2\2\2\u0215k\3\2\2\2\u0216\u021f\5z>\2\u0217\u021f\5|?\2\u0218"+
		"\u021f\5p9\2\u0219\u021f\7\5\2\2\u021a\u021c\7\63\2\2\u021b\u021d\5~@"+
		"\2\u021c\u021b\3\2\2\2\u021c\u021d\3\2\2\2\u021d\u021f\3\2\2\2\u021e\u0216"+
		"\3\2\2\2\u021e\u0217\3\2\2\2\u021e\u0218\3\2\2\2\u021e\u0219\3\2\2\2\u021e"+
		"\u021a\3\2\2\2\u021fm\3\2\2\2\u0220\u0229\5z>\2\u0221\u0229\5|?\2\u0222"+
		"\u0229\5x=\2\u0223\u0229\5p9\2\u0224\u0226\7\63\2\2\u0225\u0227\5~@\2"+
		"\u0226\u0225\3\2\2\2\u0226\u0227\3\2\2\2\u0227\u0229\3\2\2\2\u0228\u0220"+
		"\3\2\2\2\u0228\u0221\3\2\2\2\u0228\u0222\3\2\2\2\u0228\u0223\3\2\2\2\u0228"+
		"\u0224\3\2\2\2\u0229o\3\2\2\2\u022a\u022b\7\66\2\2\u022b\u022f\5t;\2\u022c"+
		"\u022d\7\66\2\2\u022d\u022f\5r:\2\u022e\u022a\3\2\2\2\u022e\u022c\3\2"+
		"\2\2\u022fq\3\2\2\2\u0230\u0231\7$\2\2\u0231\u0236\5t;\2\u0232\u0233\7"+
		"\60\2\2\u0233\u0235\5t;\2\u0234\u0232\3\2\2\2\u0235\u0238\3\2\2\2\u0236"+
		"\u0234\3\2\2\2\u0236\u0237\3\2\2\2\u0237\u0239\3\2\2\2\u0238\u0236\3\2"+
		"\2\2\u0239\u023a\7%\2\2\u023as\3\2\2\2\u023b\u023d\7\3\2\2\u023c\u023e"+
		"\5~@\2\u023d\u023c\3\2\2\2\u023d\u023e\3\2\2\2\u023e\u0246\3\2\2\2\u023f"+
		"\u0241\7\n\2\2\u0240\u0242\5~@\2\u0241\u0240\3\2\2\2\u0241\u0242\3\2\2"+
		"\2\u0242\u0246\3\2\2\2\u0243\u0246\5z>\2\u0244\u0246\7\5\2\2\u0245\u023b"+
		"\3\2\2\2\u0245\u023f\3\2\2\2\u0245\u0243\3\2\2\2\u0245\u0244\3\2\2\2\u0246"+
		"u\3\2\2\2\u0247\u0252\7$\2\2\u0248\u024a\5\b\5\2\u0249\u0248\3\2\2\2\u0249"+
		"\u024a\3\2\2\2\u024a\u024e\3\2\2\2\u024b\u024d\5:\36\2\u024c\u024b\3\2"+
		"\2\2\u024d\u0250\3\2\2\2\u024e\u024c\3\2\2\2\u024e\u024f\3\2\2\2\u024f"+
		"\u0251\3\2\2\2\u0250\u024e\3\2\2\2\u0251\u0253\7 \2\2\u0252\u0249\3\2"+
		"\2\2\u0252\u0253\3\2\2\2\u0253\u0254\3\2\2\2\u0254\u0255\5^\60\2\u0255"+
		"\u0256\7%\2\2\u0256w\3\2\2\2\u0257\u0259\7\4\2\2\u0258\u025a\5\"\22\2"+
		"\u0259\u0258\3\2\2\2\u0259\u025a\3\2\2\2\u025a\u025c\3\2\2\2\u025b\u025d"+
		"\5~@\2\u025c\u025b\3\2\2\2\u025c\u025d\3\2\2\2\u025dy\3\2\2\2\u025e\u025f"+
		"\7\n\2\2\u025f\u0260\7\62\2\2\u0260\u0261\7\n\2\2\u0261{\3\2\2\2\u0262"+
		"\u0264\7\3\2\2\u0263\u0265\5~@\2\u0264\u0263\3\2\2\2\u0264\u0265\3\2\2"+
		"\2\u0265\u026b\3\2\2\2\u0266\u0268\7\n\2\2\u0267\u0269\5~@\2\u0268\u0267"+
		"\3\2\2\2\u0268\u0269\3\2\2\2\u0269\u026b\3\2\2\2\u026a\u0262\3\2\2\2\u026a"+
		"\u0266\3\2\2\2\u026b}\3\2\2\2\u026c\u026d\7)\2\2\u026d\u0272\5\u0080A"+
		"\2\u026e\u026f\7\"\2\2\u026f\u0271\5\u0080A\2\u0270\u026e\3\2\2\2\u0271"+
		"\u0274\3\2\2\2\u0272\u0270\3\2\2\2\u0272\u0273\3\2\2\2\u0273\u0275\3\2"+
		"\2\2\u0274\u0272\3\2\2\2\u0275\u0276\7*\2\2\u0276\177\3\2\2\2\u0277\u027f"+
		"\5\u0082B\2\u0278\u0279\5\u0082B\2\u0279\u027c\7+\2\2\u027a\u027d\5\u0082"+
		"B\2\u027b\u027d\7\n\2\2\u027c\u027a\3\2\2\2\u027c\u027b\3\2\2\2\u027d"+
		"\u027f\3\2\2\2\u027e\u0277\3\2\2\2\u027e\u0278\3\2\2\2\u027f\u0081\3\2"+
		"\2\2\u0280\u0281\t\4\2\2\u0281\u0083\3\2\2\2W\u0085\u008d\u0094\u00a0"+
		"\u00a7\u00ae\u00bd\u00c3\u00ca\u00d7\u00dc\u00e3\u00eb\u00ef\u00f8\u0100"+
		"\u0106\u010f\u011a\u0120\u0125\u0128\u012b\u012f\u0132\u0135\u0138\u013d"+
		"\u0148\u014c\u0157\u0162\u016f\u017a\u0180\u0183\u0186\u0194\u0199\u019c"+
		"\u01a1\u01a5\u01a9\u01ad\u01b1\u01b3\u01b9\u01c5\u01ce\u01d2\u01d6\u01dd"+
		"\u01e1\u01e6\u01e9\u01ee\u01f3\u01f8\u01fa\u0200\u0204\u020a\u020e\u0212"+
		"\u0214\u021c\u021e\u0226\u0228\u022e\u0236\u023d\u0241\u0245\u0249\u024e"+
		"\u0252\u0259\u025c\u0264\u0268\u026a\u0272\u027c\u027e";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}