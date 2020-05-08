grammar Lucene ;

query   : expr+ EOF ;
expr	: LPAREN orExpr RPAREN	/* grouping */
		| expr ANDTOKEN expr	/* and exp */
		| expr NOTTOKEN expr	/* not exp */
		| expr ORTOKEN expr 	/* or exp */
		| required
		| prohibited
		| proximity
		| fuzzy
		| boosted
		| phrase
		| term
		;

proximity   : PHRASE TILDE INT ;
fuzzy		: term TILDE FLOAT? ;
boosted     : (term | PHRASE) ACCENT (FLOAT | INT) ;
required    : PLUSTOKEN term ;
prohibited  : MINUSTOKEN term ;
term        : alphanum+ ( STAR | QMARK )? alphanum* ;
alphanum    : CHARACTER | NUM ;

ANDTOKEN    : 'AND' ;
NOTTOKEN    : 'NOT' | BANG ;
ORTOKEN     : 'OR' ;
FLOAT       : NUM* '.' NUM+ ;
INT         : NUM+ ;
PHRASE      : '"' .*? '"' ;

WHITESPACE  : [ \t\r\n]+ -> skip;

CHARACTER   : 'a'..'z' | 'A'..'Z' ;
NUM         : '0'..'9' ;
LPAREN      : '(' ;
RPAREN      : ')' ;
PLUSTOKEN   : '+' ;
MINUSTOKEN  : '-' ;
STAR		: '*' ;
QMARK		: '?' ;
BANG		: '!' ;
TILDE       : '~' ;
ACCENT      : '^' ;
