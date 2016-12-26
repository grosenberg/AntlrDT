package net.certiv.antlrdt.ui.graph.cst;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.antlrdt.ui.graph.cst.model.CstModel;

public class CstProcessor extends ParseTreeWalker implements ParseTreeListener {

	private CstModel model;

	public CstProcessor(CstModel model) {
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

	public CstModel getModel() {
		return model;
	}
}
