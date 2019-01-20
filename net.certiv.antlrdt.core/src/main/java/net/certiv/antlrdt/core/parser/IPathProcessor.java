package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public interface IPathProcessor {

	void addRuleSpec(ParserRuleContext rule, Token term, Target kind);

	void addRuleAlt(ParserRuleContext rule, Token term);

	void endRule();
}
