/*
 * [The "BSD license"]
 *  Copyright (c) 2012-2014 Terence Parr
 *  Copyright (c) 2012-2014 Sam Harwell
 *  Copyright (c) 2013-2020 Gerald Rosenberg
 *  All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions
 *  are met:
 *
 *  1. Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *  2. Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *  3. The name of the author may not be used to endorse or promote products
 *     derived from this software without specific prior written permission.
 *
 *  THIS SOFTWARE IS PROVIDED BY THE AUTHOR ``AS IS'' AND ANY EXPRESS OR
 *  IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 *  OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 *  IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY DIRECT, INDIRECT,
 *  INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 *  NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 *  DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 *  THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 *  (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF
 *  THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/**	A grammar for ANTLR v4 suitable for use in an IDE
 *
 *	Modified 2013.11.21 gbr - modifications to the comment and whitespace handling for AntlrDT4
 *	-- whitespace is characterized separately as horizontal and vertical, to make IDE formatting easier
 *	-- consecutive single line comments including line endings, except for the last, are collected into
 *		a single hidden token, again to simplify IDE formatting operations
 *	-- multi-line comments are handled as hidden tokens - the separate handling of javadoc-style comments,
 *		particularly as constraints causing parser errors, seems to be arbitrary.  But since they
 *		can fail a parse, we must continue to distinguish them separately.
 *	-- will likely need to modify the OPTIONS and TOKENS rules to fully tokenize any included the whitespace interval.
 *	Modified 2013.12.28 gbr
 *	-- separate modes for OPTIONS and TOKENS blocks to make parsing more ideomatic
 *	-- removed WSNLCHAR as unnecessary
 *	-- restructured fragments for consistency
 *	Modified 2014.01.01 gbr
 *	-- corrected ordering of punctuation elements (COLONCOLON before COLON, etc.)
 *	Modified 2014.01.04 gbr
 *	-- updated to be largely consistent with the 'official' v4 grammar
 *	Modified 2014.12.24 gbr
 *	-- updated to be largely consistent with the 'official' v4 grammar
 *	-- moved members to LexerAdaptor
 *	-- removed LexerHelper
 *	Modified 2015.06.14 gbr
 *	-- begin update for compatibility with Antlr v4.5
 */

lexer grammar AntlrDT4Lexer;

options {
	superClass = LexerAdaptor ;
	TokenLabelType = AntlrDT4DTToken ;
}

tokens {
	TOKEN_REF,
	RULE_REF,
	LEXER_CHAR_SET
}


@header {
	package net.certiv.antlr.dt.core.parser.gen;
	import net.certiv.antlr.dt.core.parser.LexerAdaptor;
}


// ======================================================
// Lexer specification
//

// -------------------------
// Comments

DOC_COMMENT
	:	DocComment -> channel(HIDDEN)
	;

BLOCK_COMMENT
	:	BlockComment -> channel(HIDDEN)
	;

LINE_COMMENT
	:	LineCommentExt -> channel(HIDDEN)
	;


// -------------------------
// Integer
//

INT	: DecimalNumeral
	;


// -------------------------
// Literal string
//
// ANTLR makes no distinction between a single character literal and a
// multi-character string. All literals are single quote delimited and
// may contain unicode escape sequences of the form \uxxxx, where x
// is a valid hexadecimal number (per Unicode standard).

STRING_LITERAL
	: SQuoteLiteral
	;

UNTERMINATED_STRING_LITERAL
	: USQuoteLiteral
	;


// -------------------------
// Arguments
//
// Certain argument lists, such as those specifying call parameters
// to a rule invocation, or input parameters to a rule specification
// are contained within square brackets.

BEGIN_ARGUMENT
	:	LBrack		{ handleBeginArgument(); }
	;


// -------------------------
// Actions

BEGIN_ACTION
	:	LBrace		-> pushMode(Action)
	;


// -------------------------
// Keywords
//
// Keywords may not be used as labels for rules or in any other context where
// they would be ambiguous with the keyword vs some other identifier.  OPTIONS,
// TOKENS, & CHANNELS blocks are handled idiomatically in dedicated lexical modes.

OPTIONS		: 'options'		-> pushMode(Options)	;
TOKENS		: 'tokens'		-> pushMode(Tokens)		;
CHANNELS	: 'channels'	-> pushMode(Channels)	;

IMPORT		: 'import'		;
FRAGMENT	: 'fragment'	;
LEXER		: 'lexer'		;
PARSER		: 'parser'		;
XVISITOR	: 'xvisitor'	;
GRAMMAR		: 'grammar'		;
PROTECTED	: 'protected'	;
PUBLIC		: 'public'		;
PRIVATE		: 'private'		;
RETURNS		: 'returns'		;
LOCALS		: 'locals'		;
THROWS		: 'throws'		;
CATCH		: 'catch'		;
FINALLY		: 'finally'		;
MODE		: 'mode'		;


// -------------------------
// Punctuation

COLON		: Colon			;
COLONCOLON	: DColon		;
COMMA		: Comma			;
SEMI		: Semi			;
LPAREN		: LParen		;
RPAREN		: RParen		;
LBRACE		: LBrace		;
RBRACE		: RBrace		;
RARROW		: RArrow		;
LT			: Lt			;
GT			: Gt			;
ASSIGN		: Equal			;
QUESTION	: Question		;
STAR		: Star			;
PLUS_ASSIGN	: PlusAssign	;
PLUS		: Plus			;
OR			: Pipe			;
DOLLAR		: Dollar		;
RANGE		: Range			;
DOT			: Dot			;
AT			: At			;
POUND		: Pound			;
NOT			: Tilde			;


// -------------------------
// Identifiers - allows unicode rule/token names

ID	: Id
	;


// -------------------------
// Whitespace

HORZ_WS
	:	Hws+ -> channel(HIDDEN)
	;

VERT_WS
	:	Vws+ -> channel(HIDDEN)
	;


// -------------------------
// Illegal Characters
//
// This is an illegal character trap which is always the last rule in the
// lexer specification. It matches a single character of any value and being
// the last rule in the file will match when no other rule knows what to do
// about the character. It is reported as an error but is not passed on to the
// parser. This means that the parser to deal with the gramamr file anyway
// but we will not try to analyse or code generate from a file with lexical
// errors.
//
// Comment this rule out to allow the error to be propagated to the parser
/*
ERRCHAR
	:	.	-> channel(HIDDEN)
	;
*/


// ======================================================
// Lexer modes

// -------------------------
// Arguments

mode Argument;			// E.g., [int x, List<String> a[]]

	NESTED_ARGUMENT			: LBrack			-> type(ARGUMENT_CONTENT), pushMode(Argument)	;

	ARGUMENT_ESCAPE			: EscAny			-> type(ARGUMENT_CONTENT)		;

	ARGUMENT_STRING_LITERAL	: DQuoteLiteral	-> type(ARGUMENT_CONTENT)		;
	ARGUMENT_CHAR_LITERAL	: SQuoteLiteral	-> type(ARGUMENT_CONTENT)		;

	END_ARGUMENT			: RBrack	{ handleEndArgument(); }	;

	// added this to return non-EOF token type here. EOF does something weird
	UNTERMINATED_ARGUMENT 	: EOF		-> popMode		;

	ARGUMENT_CONTENT		: .							;


// -------------------------
// Actions
//
// Many language targets use {} as block delimiters and so we
// must recursively match {} delimited blocks to balance the
// braces. Additionally, we must make some assumptions about
// literal string representation in the target language. We assume
// that they are delimited by ' or " and so consume these
// in their own alts so as not to inadvertantly match {}.

mode Action;

	NESTED_ACTION			: LBrace			-> type(ACTION_CONTENT), pushMode(Action)	;

	ACTION_ESCAPE			: EscAny			-> type(ACTION_CONTENT)		;

	ACTION_STRING_LITERAL	: DQuoteLiteral		-> type(ACTION_CONTENT)		;
	ACTION_CHAR_LITERAL		: SQuoteLiteral		-> type(ACTION_CONTENT)		;

	ACTION_DOC_COMMENT		: DocComment		-> type(ACTION_CONTENT)		;
	ACTION_BLOCK_COMMENT	: BlockComment 		-> type(ACTION_CONTENT)		;
	ACTION_LINE_COMMENT		: LineCommentExt 	-> type(ACTION_CONTENT)		;

	END_ACTION				: RBrace				{ handleEndAction(); }	;

	UNTERMINATED_ACTION		: EOF		-> popMode		;

    ACTION_CONTENT			: .							;


// -------------------------

mode Options;

	OPT_DOC_COMMENT		: DocComment		-> type(DOC_COMMENT), channel(HIDDEN)		;
	OPT_BLOCK_COMMENT	: BlockComment 		-> type(BLOCK_COMMENT), channel(HIDDEN)		;
	OPT_LINE_COMMENT	: LineCommentExt 	-> type(LINE_COMMENT), channel(HIDDEN)		;

	OPT_LBRACE			: LBrace			-> type(LBRACE)				;
	OPT_RBRACE			: RBrace			-> type(RBRACE), popMode	;

	OPT_ID				: Id				-> type(ID)					;
	OPT_DOT				: Dot				-> type(DOT)				;
	OPT_ASSIGN			: Equal				-> type(ASSIGN)				;
	OPT_STRING_LITERAL	: SQuoteLiteral		-> type(STRING_LITERAL)		;
	OPT_INT				: Int				-> type(INT)				;
	OPT_STAR			: Star				-> type(STAR)				;
	OPT_SEMI			: Semi				-> type(SEMI)				;

	OPT_HORZ_WS			: Hws+ 				-> type(HORZ_WS), channel(HIDDEN) 	;
	OPT_VERT_WS			: Vws+ 				-> type(VERT_WS), channel(HIDDEN)	;


// -------------------------

mode Tokens;

	TOK_DOC_COMMENT		: DocComment		-> type(DOC_COMMENT), channel(HIDDEN)		;
	TOK_BLOCK_COMMENT	: BlockComment 		-> type(BLOCK_COMMENT), channel(HIDDEN)		;
	TOK_LINE_COMMENT	: LineCommentExt 	-> type(LINE_COMMENT), channel(HIDDEN)		;

	TOK_LBRACE			: LBrace			-> type(LBRACE)				;
	TOK_RBRACE			: RBrace			-> type(RBRACE), popMode	;

	TOK_ID				: Id				-> type(ID)					;
	TOK_DOT				: Dot				-> type(DOT)				;
	TOK_COMMA			: Comma				-> type(COMMA)				;

	TOK_HORZ_WS			: Hws+ 				-> type(HORZ_WS), channel(HIDDEN) 	;
	TOK_VERT_WS			: Vws+ 				-> type(VERT_WS), channel(HIDDEN)	;


// -------------------------

mode Channels;	// currently same as Tokens mode; distinguished by keyword

	CHN_DOC_COMMENT		: DocComment		-> type(DOC_COMMENT), channel(HIDDEN)	;
	CHN_BLOCK_COMMENT	: BlockComment 		-> type(BLOCK_COMMENT), channel(HIDDEN)	;
	CHN_LINE_COMMENT	: LineCommentExt 	-> type(LINE_COMMENT), channel(HIDDEN)	;

	CHN_LBRACE			: LBrace			-> type(LBRACE)				;
	CHN_RBRACE			: RBrace			-> type(RBRACE), popMode	;

	CHN_ID				: Id				-> type(ID)					;
	CHN_DOT				: Dot				-> type(DOT)				;
	CHN_COMMA			: Comma				-> type(COMMA)				;

	CHN_HORZ_WS			: Hws+ 				-> type(HORZ_WS), channel(HIDDEN) 	;
	CHN_VERT_WS			: Vws+ 				-> type(VERT_WS), channel(HIDDEN)	;


// -------------------------

mode LexerCharSet;

	LEXER_CHAR_SET_BODY
		:	(	~[\]\\]
			|	EscAny
			)+				-> more
		;

	LEXER_CHAR_SET
		:	RBrack		-> popMode
		;

	UNTERMINATED_CHAR_SET
		:	EOF				-> popMode
		;

// ------------------------------------------------------------------------------
// Grammar specific Keywords, Punctuation, etc.

fragment Id	: NameStartChar NameChar*	;


// -----------------------------------
// Whitespace & Comments

fragment Ws				: Hws | Vws	;
fragment Hws			: [ \t] ;
fragment Vws			: [\r\n\f] ;

fragment DocComment		: '/**' .*? ('*/' | EOF) ;
fragment BlockComment	: '/*'  .*? ('*/' | EOF) ;
fragment LineComment	: '//' ~[\r\n]*			 ;
fragment LineCommentExt	: '//' ~[\r\n]* ( '\r'? '\n' Hws* '//' ~[\r\n]* )*	;


// -----------------------------------
// Escapes

// Any kind of escaped character that we can embed within ANTLR literal strings.
fragment EscSeq
	:	Esc
		( [btnfr"'\\]	// The standard escaped character set such as tab, newline, etc.
		| UnicodeEsc	// A Unicode escape sequence
		| .				// Invalid escape character
		| EOF			// Incomplete at EOF
		)
	;

fragment EscAny
	:	Esc .
	;

fragment UnicodeEsc
	:	'u' (HexDigit (HexDigit (HexDigit HexDigit?)?)?)?
	;

fragment OctalEscape
	:	OctalDigit
	|	OctalDigit OctalDigit
	|	[0-3] OctalDigit OctalDigit
	;


// -----------------------------------
// Numerals

fragment HexNumeral
	:	'0' [xX] HexDigits
	;

fragment OctalNumeral
	:	'0' '_' OctalDigits
	;

fragment DecimalNumeral
	:	'0'
	|	[1-9] DecDigit*
	;

fragment BinaryNumeral
	:	'0' [bB] BinaryDigits
	;


// -----------------------------------
// Digits

fragment HexDigits		: HexDigit+		;
fragment DecDigits		: DecDigit+		;
fragment OctalDigits	: OctalDigit+	;
fragment BinaryDigits	: BinaryDigit+	;

fragment HexDigit		: [0-9a-fA-F]	;
fragment DecDigit		: [0-9]			;
fragment OctalDigit		: [0-7]			;
fragment BinaryDigit	: [01]			;


// -----------------------------------
// Literals

fragment BoolLiteral	: True | False								;

fragment CharLiteral	: SQuote ( EscSeq | ~['\r\n\\] )  SQuote	;
fragment SQuoteLiteral	: SQuote ( EscSeq | ~['\r\n\\] )* SQuote	;
fragment DQuoteLiteral	: DQuote ( EscSeq | ~["\r\n\\] )* DQuote	;
fragment USQuoteLiteral	: SQuote ( EscSeq | ~['\r\n\\] )* 			;

fragment DecimalFloatingPointLiteral
	:   DecDigits DOT DecDigits? ExponentPart? FloatTypeSuffix?
	|   DOT DecDigits ExponentPart? FloatTypeSuffix?
	|	DecDigits ExponentPart FloatTypeSuffix?
	|	DecDigits FloatTypeSuffix
	;

fragment ExponentPart
	:	[eE] [+-]? DecDigits
	;

fragment FloatTypeSuffix
	:	[fFdD]
	;

fragment HexadecimalFloatingPointLiteral
	:	HexSignificand BinaryExponent FloatTypeSuffix?
	;

fragment HexSignificand
	:   HexNumeral DOT?
	|   '0' [xX] HexDigits? DOT HexDigits
	;

fragment BinaryExponent
	:	[pP] [+-]? DecDigits
	;


// -----------------------------------
// Character ranges

fragment NameChar
	:	NameStartChar
	|	'0'..'9'
	|	Underscore
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


fragment JavaLetter
	:   [a-zA-Z$_] // "java letters" below 0xFF
	|	JavaUnicodeChars
	;

fragment JavaLetterOrDigit
	:   [a-zA-Z0-9$_] // "java letters or digits" below 0xFF
	|	JavaUnicodeChars
	;

// covers all characters above 0xFF which are not a surrogate
// and UTF-16 surrogate pairs encodings for U+10000 to U+10FFFF
fragment JavaUnicodeChars
	: ~[\u0000-\u00FF\uD800-\uDBFF]		{Character.isJavaIdentifierPart(_input.LA(-1))}?
	|  [\uD800-\uDBFF] [\uDC00-\uDFFF]	{Character.isJavaIdentifierPart(Character.toCodePoint((char)_input.LA(-2), (char)_input.LA(-1)))}?
	;


// -----------------------------------
// Types

fragment Boolean		: 'boolean'	;
fragment Byte			: 'byte'	;
fragment Short			: 'short'	;
fragment Int			: 'int'		;
fragment Long			: 'long'	;
fragment Char			: 'char'	;
fragment Float			: 'float'	;
fragment Double 		: 'double'	;

fragment True		 	: 'true'	;
fragment False			: 'false'	;


// -----------------------------------
// Symbols

fragment Esc			: '\\'	;
fragment Colon			: ':'	;
fragment DColon			: '::'	;
fragment SQuote			: '\''	;
fragment DQuote			: '"'	;
fragment BQuote			: '`'	;
fragment LParen			: '('	;
fragment RParen			: ')'	;
fragment LBrace			: '{'	;
fragment RBrace			: '}'	;
fragment LBrack			: '['	;
fragment RBrack			: ']'	;
fragment RArrow			: '->'	;
fragment Lt				: '<'	;
fragment Gt				: '>'	;
fragment Lte			: '<='	;
fragment Gte			: '>='	;
fragment Equal			: '='	;
fragment NotEqual		: '!='	;
fragment Question		: '?'	;
fragment Bang			: '!'	;
fragment Star			: '*'	;
fragment Slash			: '/'	;
fragment Percent		: '%'	;
fragment Caret			: '^'	;
fragment Plus			: '+'	;
fragment Minus			: '-'	;
fragment PlusAssign		: '+='	;
fragment MinusAssign	: '-='	;
fragment MulAssign		: '*='	;
fragment DivAssign		: '/='	;
fragment AndAssign		: '&='	;
fragment OrAssign		: '|='	;
fragment XOrAssign		: '^='	;
fragment ModAssign		: '%='	;
fragment LShiftAssign	: '<<='	;
fragment RShiftAssign	: '>>='	;
fragment URShiftAssign	: '>>>=';
fragment Underscore		: '_'	;
fragment Pipe			: '|'	;
fragment Amp			: '&'	;
fragment And			: '&&'	;
fragment Or				: '||'	;
fragment Inc			: '++'	;
fragment Dec			: '--'	;
fragment LShift			: '<<'	;
fragment RShift			: '>>'	;
fragment Dollar			: '$'	;
fragment Comma			: ','	;
fragment Semi			: ';'	;
fragment Dot			: '.'	;
fragment Range			: '..'	;
fragment Ellipsis		: '...'	;
fragment At				: '@'	;
fragment Pound			: '#'	;
fragment Tilde			: '~'	;
