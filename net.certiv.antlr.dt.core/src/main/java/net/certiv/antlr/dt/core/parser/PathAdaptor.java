package net.certiv.antlr.dt.core.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlr.dt.core.model.Target;

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
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		helper.addNode(ctx, (AntlrToken) term, Target.PARSER);
	}

	public void ruleRefName(Token term) {
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		helper.addNode(ctx, (AntlrToken) term, Target.PARSER);
	}

	public void lexerRuleName(Token term) {
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		helper.addNode(ctx, (AntlrToken) term, Target.LEXER);
	}

	public void fragmentRuleName(Token term) {
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		helper.addNode(ctx, (AntlrToken) term, Target.FRAGMENT);
	}

	public void terminal(Token term1, Token term2) {
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		Token term = term1 != null ? term1 : term2;
		helper.addNode(ctx, (AntlrToken) term, Target.TERMINAL);
	}

	public void literal() {
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		AntlrToken token = (AntlrToken) ctx.start;
		Pair<TokenSource, CharStream> source = new Pair<>(token.getTokenSource(), token.getInputStream());
		AntlrToken term = new AntlrToken(source, token.getType(), token.getChannel(), token.getStartIndex(),
				token.getStopIndex());
		term.setText(ctx.getText());
		term.setLine(token.getLine());
		term.setCharPositionInLine(token.getCharPositionInLine());
		term.setMode(token.getMode());
		helper.addNode(ctx, term, Target.TERMINAL);
	}

	public void ruleDone() {
		helper.end();
	}
}
