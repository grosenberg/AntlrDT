grammar Rules;
options{ 	superClass = FormatProcessor ;// place for stuff
/* Assign custom label type */
	TokenLabelType = FormatterToken ;}
tokens {
  METHOD_DECL, // function definition
  ARG_DECL,    // parameter
	MEMBER}
declaration
: statement* EOF ;
statement
	@init { Stack s = new Stack(); }
	@after { s == null; }
: '{' statement* '}'
	| parameter ASSIGN (ID|INT);

parameter
: type ID '[]'
	| type ID ;

type
: 'int'
	| 'char'
	| 'boolean'
	| 'void'
	| ID ;

ID
: LETTER (LETTER | INT  )* ;

fragment
 LETTER
: ('a'..'z' | 'A'..'Z');

ASSIGN
: '=' ;

INT
: '0'..'9'+ ;

WS
: (' '|'\r'|'\t'|'\n') -> channel(HIDDEN) ;

COMMENT
: (ML_COMMENT
		| SL_COMMENT)	-> channel(HIDDEN);

fragment
 ML_COMMENT
: '/*' .*? '*/' ;

fragment
SL_COMMENT
: '//' ~('\r'|'\n')* '\r'? '\n' ;

