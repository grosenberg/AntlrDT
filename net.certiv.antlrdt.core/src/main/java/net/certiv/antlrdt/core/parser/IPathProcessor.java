package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;

public interface IPathProcessor {

	void startRule(ParserRuleContext rule, Path kind);

	void endRule(Path kind);

	void addPathRule(ParserRuleContext rule, Token term, Path kind);

	void addPathChild(Token term);
}
