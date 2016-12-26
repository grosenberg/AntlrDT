grammar BlankLinesPreview;

tokens {
  DECL, 		// function definition
  BLOCK,
  ASSIGN,
}


compilationUnit
    :   (   declaration  |   formalParameters        )+   EOF  ;
declaration : statement* ;
statement
    :   '{' statement* '}'
    |   type ID ASSIGN (ID|INT)
	;
type:   'int'    |   'char'    |   'boolean'    |   'void'
    |   ID    ;

ID  :   LETTER (LETTER | INT )* ;
fragment LETTER
    :   ('a'..'z'|'A'..'Z')
    ;
INT :   '0'..'9'+ ;
WS  :   (' '|'\r'|'\t'|'\n') -> channel(HIDDEN) ;
SL_COMMENT
    :   '//' ~('\r'|'\n')* '\r'? '\n' -> channel(HIDDEN)
    ;
