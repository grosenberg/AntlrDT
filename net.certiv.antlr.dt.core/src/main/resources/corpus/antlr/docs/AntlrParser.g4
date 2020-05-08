parser grammar AntlrParser ;

options {
	tokenVocab = AntlrLexer;
}

@header {
	package net.certiv.adept.lang.antlr.parser.gen;
}

adept
	:	( statement
		| grammarRule
		| other
		)*
		EOF
	;

statement
	: simple
	| block
	| atBlock
	;

simple
	: keyword+ id+ SEMI
	;

block
	: keyword+ body
	;

body
	: LBRACE ( listStmt | assignStmt+ )? RBRACE
	;

atBlock
	: AT keyword+ action
	;

listStmt
	: id ( COMMA id )* COMMA?
	;

assignStmt
	: id EQ ( dottedID | STRING | INT ) SEMI
	;

grammarRule
	: keyword* id COLON elementList action? function? SEMI
	;

elementList
	: element+ ( ALT element+ )*
	;

element
	: ( label op )? id
	| ( label op )? elementSublist
	| pred
	;

elementSublist
	: LPAREN elementList RPAREN
	;

pred
	: action QMARK
	;

action
	: LBRACE ( action | ~RBRACE )* RBRACE
	;

function
	: RARROW attribute attrValue? ( COMMA attribute attrValue? )*
	;

attrValue
	: LPAREN ID RPAREN
	;

dottedID
	: id ( DOT id )*
	;

label
	: ID
	;

id
	: ID
	;

keyword
	: GRAMMAR | LEXER | PARSER
	| OPTIONS | TOKENS | IMPORT | CHANNELS
	| COLONCOLON | HEADER | MEMBERS | INIT | AFTER
	| FRAGMENT | PROTECTED | PUBLIC | PRIVATE
	| RETURNS | LOCALS | THROWS | CATCH | FINALLY
	| MODE | LEOF
	;

attribute
	: CHANNEL | LSKIP | LMORE
	| PUSHMODE | POPMODE | TYPE
	;

op
	: EQ | PLUSEQ
	;

other
	: .
	;
