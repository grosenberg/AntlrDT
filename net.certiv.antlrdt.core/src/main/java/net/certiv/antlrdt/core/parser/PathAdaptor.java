package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ModeRuleSpecContext;

/** Implementing functions for grammar path walker. */
public abstract class PathAdaptor extends Processor {

	private IPathProcessor helper;

	public PathAdaptor(ParseTree tree) {
		super(tree);
	}

	public void setHelper(IPathProcessor helper) {
		this.helper = helper;
	}

	public void parserRuleName(Token term) {
		ParserRuleContext rule = (ParserRuleContext) lastPathNode();
		helper.addRuleSpec(rule, term, Target.PARSER);
	}

	public void ruleAltName(Token term1, Token term2) {
		ParserRuleContext rule = (ParserRuleContext) lastPathNode();
		Token term = term1 != null ? term1 : term2;
		helper.addRuleAlt(rule, term);
	}

	public void lexerRuleName(Token term) {
		ParserRuleContext rule = (ParserRuleContext) lastPathNode();
		helper.addRuleSpec(rule, term, Target.LEXER);
	}

	public void fragmentRuleName(Token term) {
		ParserRuleContext rule = (ParserRuleContext) lastPathNode();
		helper.addRuleSpec(rule, term, Target.FRAGMENT);
	}

	public void ruleDone() {
		helper.endRule();
	}

	public void modeName(Token parse, Token lex) {
		Token term = parse != null ? parse : lex;
		ModeRuleSpecContext rule = (ModeRuleSpecContext) pathNodes().get(1);
		helper.addRuleSpec(rule, term, Target.MODE);
	}
}
