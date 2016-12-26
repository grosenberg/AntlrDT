/**
 * Heading JavaDoc block
 */
grammar FormatPreview;
options {
  superClass = PreviewProcessor ; // base class
}

tokens {
  METHOD_DECL, // function definition
  ARG_DECL,    // parameter
	MEMBER
}

@members {
	@Override
	protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
		throws RecognitionException {
			throw new MismatchedTokenException(ttype, input);
	}
}

parameter
    :   varType ID
    |   varType ID '[]'
    |   varType '*' ID
    ;

varType:   primitiveType
    |   ID
    ;

primitiveType
    :   'int'
    |   'char'
    |   'boolean'
    |   'void'
    ;

block
    :   '{' statement* '}' 
    ;

statement : block | expr ;

expr
	: expr PLUS expr
	| primary
	;

primary
    :   ID
    |   INT
    |   CHAR
    |   'true'
    |   'false'
    |   '(' expr ')' 
    ;

// LEXER RULES
EQ:'=';
PLUS:'+';

ID  :   LETTER (LETTER | INT )*
    ;

fragment
LETTER
    :   ('a'..'z' | 'A'..'Z')
    ;

CHAR:   '\'' . '\'' ;

INT :   '0'..'9'+ ;
    
WS  :   (' '|'\r'|'\t'|'\n') -> channel(HIDDEN) ;

SL_COMMENT
    :   '//' ~('\r'|'\n')* '\r'? '\n' -> skip
    ;
