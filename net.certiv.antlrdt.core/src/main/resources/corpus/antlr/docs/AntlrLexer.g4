lexer grammar AntlrLexer ;

options {
	superClass = LexerErrorStrategy ;
}

@header {
	package net.certiv.adept.lang.antlr.parser.gen;
	import net.certiv.adept.model.parser.LexerErrorStrategy;
}

COMMENT
 	: (DocComment
 	|  BlockComment
	|  LineComment ) -> channel(HIDDEN)
	;


INT	: DecimalNumeral ;

STRING
	: SQuoteLiteral
	| DQuoteLiteral
	;

OPTIONS		: 'options'		;
TOKENS		: 'tokens'		;
IMPORT		: 'import'		;
CHANNELS	: 'channels'	;
CHANNEL		: 'channel'		;

LSKIP		: 'skip'		;
LMORE		: 'more'		;
LEOF 		: 'EOF'			;

HEADER		: 'header'		;
MEMBERS		: 'members'		;
INIT		: 'init'		;
AFTER		: 'after'		;

MODE		: 'mode'		;
PUSHMODE	: 'pushMode'	;
POPMODE		: 'popMode'		;
TYPE		: 'type'		;

FRAGMENT	: 'fragment'	;
LEXER		: 'lexer'		;
PARSER		: 'parser'		;
GRAMMAR		: 'grammar'		;
PROTECTED	: 'protected'	;
PUBLIC		: 'public'		;
PRIVATE		: 'private'		;
RETURNS		: 'returns'		;
LOCALS		: 'locals'		;
THROWS		: 'throws'		;
CATCH		: 'catch'		;
FINALLY		: 'finally'		;

AT			: '@'			;
COLON		: ':'			;
COLONCOLON	: '::'			;
COMMA		: ','			;
SEMI		: ';'			;
LPAREN		: '('			;
RPAREN		: ')'			;
LBRACE		: '{'			;
RBRACE		: '}'			;
LBRACK		: '['			;
RBRACK		: ']'			;
RARROW		: '->'			;
EQ			: '='			;
QMARK		: '?'			;
STAR		: '*'			;
PLUS		: '+'			;
PLUSEQ		: '+='			;
NOT			: '~'			;
ALT			: '|'			;
DOT			: '.'			;
RANGE		: '..'			;
DOLLAR		: '$'			;
POUND		: '#'			;

ESC			: Esc			;
SQUOTE		: SQuote		;
DQUOTE		: DQuote		;


ID	: NameStartChar NameChar* ;

HWS	:	Hws+	-> channel(HIDDEN)	;
VWS	:	Vws+	-> channel(HIDDEN)	;

ERRCHAR
	:	.	-> channel(HIDDEN)
	;

// =======================

fragment Esc			: '\\'	;
fragment SQuote			: '\''	;
fragment DQuote			: '"'	;

fragment Hws			: [ \t]			;
fragment Vws			: '\r'? [\n\f]	;

fragment DocComment		: '/**' .*? ('*/' | EOF)	;
fragment BlockComment	: '/*'  .*? ('*/' | EOF)	;

fragment LineComment	: '//' ~[\r\n]* 							;
fragment LineCommentExt	: '//' ~[\r\n]* ( '\r'? '\n' Hws* '//' ~[\r\n]* )*	;

fragment EscSeq
	:	Esc
		( [btnfr"'\\]	// The standard escaped character set such as tab, newline, etc.
		| UnicodeEsc	// A Unicode escape sequence
		| .				// Invalid escape character
		| EOF			// Incomplete at EOF
		)
	;

fragment UnicodeEsc
	:	'u' (HexDigit (HexDigit (HexDigit HexDigit?)?)?)?
	;

fragment DecimalNumeral
	:	'0'
	|	[1-9] DecDigit*
	;

fragment DecDigit		: [0-9]			;
fragment HexDigit		: [0-9a-fA-F]	;

fragment CharLiteral	: SQuote ( EscSeq | ~['\r\n\\] )  SQuote	;
fragment SQuoteLiteral	: SQuote ( EscSeq | ~['\r\n\\] )* SQuote	;
fragment DQuoteLiteral	: DQuote ( EscSeq | ~["\r\n\\] )* DQuote	;


fragment NameChar
	:	NameStartChar
	|	'0'..'9'
	|	'_'
	|	'\u00B7'
	|	'\u0300'..'\u036F'
	|	'\u203F'..'\u2040'
	;

fragment NameStartChar
	:	'A'..'Z'
	|	'a'..'z'
	|	'\u00C0'..'\u00D6'
	|	'\u00D8'..'\u00F6'
	|	'\u00F8'..'\u02FF'
	|	'\u0370'..'\u037D'
	|	'\u037F'..'\u1FFF'
	|	'\u200C'..'\u200D'
	|	'\u2070'..'\u218F'
	|	'\u2C00'..'\u2FEF'
	|	'\u3001'..'\uD7FF'
	|	'\uF900'..'\uFDCF'
	|	'\uFDF0'..'\uFFFD'
	;	// ignores | ['\u10000-'\uEFFFF] ;
