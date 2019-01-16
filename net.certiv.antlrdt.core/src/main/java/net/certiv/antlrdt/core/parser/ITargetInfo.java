package net.certiv.antlrdt.core.parser;

import org.eclipse.core.resources.IFile;

public interface ITargetInfo {

	IFile getGrammar();

	String getMainRuleName();

	String[] getRuleNames();

	String[] getTokenNames();

	String[] getModeNames();
}
