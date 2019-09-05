package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.antlrdt.core.model.Target;

public interface IPathProcessor {

	void addNode(ParserRuleContext rule, AntlrToken term, Target kind);

	void end();
}
