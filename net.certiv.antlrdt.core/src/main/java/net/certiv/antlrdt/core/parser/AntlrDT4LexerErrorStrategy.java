package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.LexerNoViableAltException;

import net.certiv.antlrdt.core.parser.gen.AntlrDT4Lexer;

/**
 * Base class for the Lexer. Combines functionality for <br>
 * <ul>
 * <li>lexer error strategy</li>
 * <li>various helper routines</li>
 * <ul>
 * 
 * @author Gbr
 * 
 */
public class AntlrDT4LexerErrorStrategy extends AntlrDT4Lexer {

	// ///// Error strategy /////////////////////////////
	public AntlrDT4LexerErrorStrategy(CharStream input) {
		super(input);
	}

	public void recover(LexerNoViableAltException e) {
		// throw new RuntimeException(e); // Bail out
		super.recover(e);
	}

	// ///// Parse stream management ////////////////////

}
