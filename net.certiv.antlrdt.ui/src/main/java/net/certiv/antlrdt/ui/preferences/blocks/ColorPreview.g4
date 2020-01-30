/**
 * JavaDoc-style Comment
 */
grammar PrefsColorSample;

options {
	superClass = LexerAdaptor ;
}

// single line comment
import baseGrammar;

tokens {
	AST,
    EOL
}

@members {
    protected String printType(int type) {
        return tokenNames[type];
    }
}

/*
 *  multi-line comment
 */
rule: ( sub )+ 	EOF ;
	catch [Exception e] {  e.printStackTrace(); }
	finally {  System.out.println("Done");
}

sub 
	@init{ init();  }
	@after{ complete();  } 	: TEST ';' ;
	TEST: 'string' { System.out.println("Handling rule");  };
