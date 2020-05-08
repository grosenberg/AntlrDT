package net.certiv.antlr.dt.core.parser;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.antlr.dt.core.model.Target;

public interface IPathProcessor {

	void addNode(ParserRuleContext rule, AntlrToken term, Target kind);

	void end();
}
