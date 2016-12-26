/**
 Heading JavaDoc block */
grammar IndentsPreview;

options { 
	superClass= IndentProcessor ;
	/* Assign custom label type	 */
	TokenLabelType = IndentToken;
}

tokens {
  METHOD_DECL, // function definition
  ARG_DECL,    // parameter
  BLOCK,
  ASSIGN,
  DEREF,           // *p dereference pointer
  ADD,
	MEMBER
}



@members {
@Override protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
throws RecognitionException {
throw new MismatchedTokenException(ttype, input);
}}

compilationUnit
@after{setUnknownTokenBoundaries() ;}
	:  ( classDeclaration| 		methodDeclaration 		)+
	;
classDeclaration
	: 'class' ID  '{' classMember+ '}' ';'
	;
classMember
	: varDeclaration
    | methodDeclaration
	;

varDeclaration : varType ID EQ primary;
// START: method
methodDeclaration
	: varType ID '(' formalParameters? ')' block
	;
// END: method
formalParameters
	: parameter ( ',' parameter )* 
	;

parameter
	: varType ID
    | type ID '[]' 
    | type '*' ID  
	;

type
	: primitiveType
    | ID
	;
primitiveType
	: 'int'
    | 'char'
    | 'boolean'
    | 'void'
	;

// START: block
block		: '{' statement* '}' ;
// END: block

statement
	: block
	;
primary
	: 	ID
    | 		INT
    | CHAR
    | 			'true'
    | 'false'
    | '(' expr ')' 
	;

expr
	: expr PLUS expr
	| primary
	;

// LEXER RULES
EQ:'=';
PLUS:'+';
ID 	: LETTER ( LETTER | INT )* 	;
fragment LETTER 	:  ( 'a'..'z' | 'A'..'Z' ) 	;
CHAR 	: '\'' . '\'' 	;
INT 	: '0'..'9'+ 	;
WS	:  ( ' ' | '\r' | '\t' | '\n' ) -> channel(HIDDEN) 	;
SL_COMMENT	: '//' ~( '\r' | '\n' )* '\r'? '\n' -> channel(HIDDEN)	;
