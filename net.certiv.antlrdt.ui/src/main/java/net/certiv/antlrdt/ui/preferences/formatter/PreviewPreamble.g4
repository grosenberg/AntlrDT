grammar General;
options{ 	/* Assign custom label type  	 */
superClass=PreviewProcessor; // base class
TokenLabelType=PreviewToken;}
tokens{			METHOD_DECL, // function definition
  		ARG_DECL,    // parameter
  BLOCK,	ASSIGN,
DEREF,           // *p dereference pointer
  ADD,MEMBER}
import Fragments,Unicode;
channels{WS,COMMENTS}
@header {import com.company.product.part;}
@members{protected Object recoverFromMismatchedToken(IntStream input, int ttype, BitSet follow)
throws RecognitionException {throw new MismatchedTokenException(ttype, input);}
}
declaration : ID EOF ;
ID : LETTER* ;
WS : (' '|'\r'|'\t'|'\n') -> channel(HIDDEN) ;
COMMENT : '//' .*? '\n'  -> skip;
fragment LETTER: ('a'..'z' | 'A'..'Z');
