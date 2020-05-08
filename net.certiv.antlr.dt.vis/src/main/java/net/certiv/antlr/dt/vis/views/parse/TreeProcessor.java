package net.certiv.antlr.dt.vis.views.parse;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.antlr.dt.vis.model.TreeModel;

public class TreeProcessor implements ParseTreeListener {

	private TreeModel model;

	public TreeProcessor(TreeModel model) {
		super();
		this.model = model;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		model.add(ctx.getParent(), ctx);
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {}

	@Override
	public void visitTerminal(TerminalNode node) {
		model.add(node.getParent(), node);
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		model.add(node.getParent(), node);
	}
}
