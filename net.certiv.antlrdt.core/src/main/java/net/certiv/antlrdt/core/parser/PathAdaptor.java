package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.FragmentRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ModeRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ParserRuleSpecContext;

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
		ParserRuleSpecContext rule = (ParserRuleSpecContext) pathNodes().get(0);
		helper.startRule(rule, Path.PARSER);
		helper.addPathRule(rule, term, Path.PARSER);
	}

	public void ruleAltName(Token term) {
		helper.addPathChild(term);
	}

	public void parserRuleDone() {
		helper.endRule(Path.PARSER);
	}

	public void lexerRuleName(Token term) {
		LexerRuleSpecContext rule = (LexerRuleSpecContext) pathNodes().get(0);
		helper.startRule(rule, Path.PARSER);
		helper.addPathRule(rule, term, Path.LEXER);
	}

	public void lexerRuleDone() {
		helper.endRule(Path.LEXER);
	}

	public void fragmentRuleName(Token term) {
		FragmentRuleSpecContext rule = (FragmentRuleSpecContext) pathNodes().get(0);
		helper.startRule(rule, Path.PARSER);
		helper.addPathRule(rule, term, Path.FRAGMENT);
	}

	public void fragmentRuleDone() {
		helper.endRule(Path.FRAGMENT);
	}

	public void modeName(Token parse, Token lex) {
		Token term = parse != null ? parse : lex;
		ModeRuleSpecContext rule = (ModeRuleSpecContext) pathNodes().get(1);
		helper.startRule(rule, Path.PARSER);
		helper.addPathRule(rule, term, Path.MODE);
	}
}
