package net.certiv.antlrdt.core.parser;

import org.eclipse.core.resources.IFile;

public interface ITargetInfo {

	/** Returns the definitional type of this target info. */
	Target getTargetType();

	/** Returns the file containing the grammar corresponding to this target info. */
	IFile getGrammar();

	/** Returns the name of the main rule in the grammar corresponding to this target info. */
	String getMainRuleName();

	/** Returns the grammar rule names defined for the grammar corresponding to this target info. */
	String[] getRuleNames();

	/** Returns the grammar token names defined for the grammar corresponding to this target info. */
	String[] getTokenNames();

	/** Returns the grammar mode names defined for the grammar corresponding to this target info. */
	String[] getModeNames();
}
