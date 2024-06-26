/*
 * [The "BSD license"]
 *  Copyright (c) 2012-2018 Antlr Grammar Authors
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
parser grammar AntlrDT4Parser;

options {
	tokenVocab	= AntlrDT4Lexer ;
}

@header {
	package net.certiv.antlr.dt.core.parser.gen;
}

// The main entry point for parsing a v4 grammar.
grammarSpec
	:	declaration
		prequelConstruct*
		rules
		EOF
	;

declaration
	: grammarType id SEMI
	;

grammarType
	:	LEXER GRAMMAR
	|	PARSER GRAMMAR
	|	GRAMMAR
	;

// This is the list of all constructs that can be declared before
// the set of rules that compose the grammar, and is invoked 0..n
// times by the grammarPrequel rule.
prequelConstruct
	:	optionsSpec
	|	delegateGrammars
	|	tokensSpec
	|	channelsSpec
	|	action
	;


// ------------
// Options - things that affect analysis and/or code generation

optionsSpec
	:	OPTIONS LBRACE option* RBRACE
	;

option
	:	id ASSIGN optionValue SEMI
	;

optionValue
	:	id ( DOT id )*
	|	STRING_LITERAL
	|	actionBlock
	|	INT
	;

// ------------
// Tokens, Imports & Channels

delegateGrammars
	:	IMPORT idList? SEMI
	;

tokensSpec
	:	TOKENS LBRACE idList? RBRACE
	;

channelsSpec
	:	CHANNELS LBRACE idList? RBRACE
	;

idList
	: id idListElement* COMMA?
	;

idListElement
	: COMMA id
	;

// Match stuff like @parser::members {int i;}
action
	:	AT (actionScopeName COLONCOLON)? id actionBlock
	;

// Scope names could collide with keywords; allow them as ids for action scopes
actionScopeName
	:	id
	|	LEXER
	|	PARSER
	;

actionBlock
	:	BEGIN_ACTION ACTION_CONTENT* END_ACTION
	;

argActionBlock
	:	BEGIN_ARGUMENT ARGUMENT_CONTENT* END_ARGUMENT
	;

rules
	:	ruleSpec*
	;

ruleSpec
	:	parserRuleSpec
	|	lexerRuleSpec
	|	modeRuleSpec
	|	fragmentRuleSpec
	;

parserRuleSpec
	:	ruleModifiers? RULE_REF argActionBlock? ruleReturns? throwsSpec?
		localsSpec?
		rulePrequel*
		COLON
		ruleBlock
		SEMI
		exceptionGroup
	;

exceptionGroup
	:	exceptionHandler* finallyClause?
	;

exceptionHandler
	:	CATCH argActionBlock actionBlock
	;

finallyClause
	:	FINALLY actionBlock
	;

rulePrequel
	:	optionsSpec
	|	ruleAction
	;

ruleReturns
	:	RETURNS argActionBlock
	;

// --------------
// Exception spec

throwsSpec
	:	THROWS id (COMMA id)*
	;

localsSpec
	:	LOCALS argActionBlock
	;

/** Match stuff like @init {int i;} */
ruleAction
	:	AT id actionBlock
	;

ruleModifiers
	:	ruleModifier+
	;

// An individual access modifier for a rule. These modifiers are passed
// to the code generation templates and may be ignored by the template
// if they are of no use in that language.
ruleModifier
	:	PUBLIC
	|	PRIVATE
	|	PROTECTED
	;

ruleBlock
	:	ruleAltList
	;

ruleAltList
	:	labeledAlt (OR labeledAlt)*
	;

labeledAlt
	:	alternative (POUND id)?
	;

// --------------------
// Lexer rules

modeRuleSpec
	:	MODE id SEMI
		lexerRuleSpec+
	;

lexerRuleSpec
	:	TOKEN_REF COLON lexerRuleBlock SEMI
	;

fragmentRuleSpec
	:	FRAGMENT TOKEN_REF COLON lexerRuleBlock SEMI
	;

lexerRuleBlock
	:	lexerAltList
	;

lexerAltList
	:	lexerAlt (OR lexerAlt)*
	;

lexerAlt
	:	lexerElements lexerCommands?
	|	// explicitly allow empty alts
	;

lexerElements
	:	lexerElement+
	;

lexerElement
	:	labeledLexerElement ebnfSuffix?
	|	lexerAtom ebnfSuffix?
	|	lexerBlock ebnfSuffix?
	|	actionBlock QUESTION?	// actions only allowed on outer alt; preds can be anywhere
	;

labeledLexerElement
	:	id ( ASSIGN | PLUS_ASSIGN )
		(	lexerAtom
		|	lexerBlock
		)
	;

lexerBlock
	:	LPAREN lexerAltList RPAREN
	;

/** E.g., channel(HIDDEN), skip, more, mode(INSIDE), push(INSIDE), pop */
lexerCommands
	:	RARROW lexerCommand ( COMMA lexerCommand )*
	;

lexerCommand
	:	lexerCommandName ( LPAREN lexerCommandExpr RPAREN )?
	;

lexerCommandName
	:	id
	|	MODE
	;

lexerCommandExpr
	:	id
	|	INT
	;

// --------------------
// Rule Alts

altList
	:	alternative (OR alternative)*
	;

alternative
	:	elementOptions? element+
	|								// explicitly allow empty alts
	;

element
	:	labeledElement
		(	ebnfSuffix
		|
		)
	|	atom
		(	ebnfSuffix
		|
		)
	|	ebnf
	|	actionBlock QUESTION?		// SEMPRED is actionBlock followed by QUESTION
	;

labeledElement
	:	id ( ASSIGN | PLUS_ASSIGN )
		(	atom
		|	block
		)
	;

// --------------------
// EBNF and blocks

ebnf
	:	block blockSuffix?
	;


blockSuffix
	:	ebnfSuffix 		// Standard EBNF
	;

ebnfSuffix
	:	QUESTION QUESTION?
	|	STAR QUESTION?
	|	PLUS QUESTION?
	;

lexerAtom
	:	range
	|	terminal
	|	notSet
	|	charSet
	|	DOT elementOptions?
	;

atom
	:	terminal
	|	ruleref
	|	notSet
	|	DOT elementOptions?
	;

// --------------------
// Inverted element set

notSet
	:	NOT setElement
	|	NOT blockSet
	;

blockSet
	:	LPAREN setElement (OR setElement)* RPAREN
	;

setElement
	:	TOKEN_REF elementOptions?
	|	STRING_LITERAL elementOptions?
	|	range
	|	charSet
	;

charSet
	: LEXER_CHAR_SET
	;

// -------------
// Grammar Block

block
	:	LPAREN
		( optionsSpec? ruleAction* COLON )?
		altList
		RPAREN
	;

// ----------------
// Parser rule ref

ruleref
	:	RULE_REF argActionBlock? elementOptions?
	;

// ---------------
// Character Range

range
	: STRING_LITERAL RANGE STRING_LITERAL
	;

terminal
	:   TOKEN_REF elementOptions?
	|   STRING_LITERAL elementOptions?
	;

// Terminals may be adorned with certain options when
// referenced in the grammar: TOK<,,,>
elementOptions
	:	LT elementOption (COMMA elementOption)* GT
	;

elementOption
	:	id 									// default node option
	|	id ASSIGN (id | STRING_LITERAL)		// option assignment
	;

id	:	RULE_REF
	|	TOKEN_REF
	;
