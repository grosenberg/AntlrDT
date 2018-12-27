package net.certiv.antlrdt.graph.parse;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.antlrdt.graph.models.DiagramModel;

public class TreeProcessor extends ParseTreeWalker implements ParseTreeListener {

	private DiagramModel model;

	public TreeProcessor(DiagramModel model) {
		super();
		this.model = model;
	}

	public DiagramModel getModel() {
		return model;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		model.addElements(ctx.getParent(), ctx);
	}

	@Override
	public void visitTerminal(TerminalNode node) {
		model.addElements(node.getParent(), node);
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		model.addElements(node.getParent(), node);
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {}
}
