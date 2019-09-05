package net.certiv.antlrdt.vis.model;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.antlrdt.core.model.Target;
import net.certiv.antlrdt.core.parser.AntlrToken;

public class PathNode {

	static int ident = 0;
	public final int id;

	// key = node; value = in-bound connections
	private final List<PathNode> parents = new ArrayList<>();
	// key = node; value = out-bound connections
	private final List<PathNode> children = new ArrayList<>();

	private boolean hidden = true;

	public final ParserRuleContext ctx;
	public final AntlrToken term;
	public final Target kind;

	public PathNode(ParserRuleContext ctx, AntlrToken term, Target kind) {
		this.ctx = ctx;
		this.term = term;
		this.kind = kind;
		this.id = ident++;
	}

	public void addParent(PathNode node) {
		parents.add(node);
	}

	public void addChild(PathNode node) {
		children.add(node);
	}

	public List<PathNode> getParents() {
		return parents;
	}

	public List<PathNode> getChildren() {
		return children;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public ParserRuleContext getContext() {
		return ctx;
	}

	public AntlrToken getSymbol() {
		return term;
	}

	public String getNodeName() {
		return term.getText();
	}

	public boolean isParserRule() {
		return kind == Target.PARSER;
	}

	public boolean isLexerRule() {
		return kind == Target.LEXER;
	}

	public boolean isTerminal() {
		return kind == Target.TERMINAL;
	}

	public boolean isFragment() {
		return kind == Target.FRAGMENT;
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", term.getText(), id);
	}
}
