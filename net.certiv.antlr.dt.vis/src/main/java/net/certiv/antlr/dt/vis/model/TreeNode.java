package net.certiv.antlr.dt.vis.model;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

public class TreeNode {

	static int ident = 0;

	// key = node; value = in-bound connections
	private final List<TreeNode> parents = new ArrayList<>();
	// key = node; value = out-bound connections
	private final List<TreeNode> children = new ArrayList<>();

	private boolean hidden = true;

	public final int id;
	public final ParseTree tree;
	public final String name;

	public TreeNode(ParseTree tree, String name) {
		this.id = ident++;
		this.tree = tree;
		this.name = name;
	}

	public void addParent(TreeNode node) {
		parents.add(node);
	}

	public void addChild(TreeNode node) {
		children.add(node);
	}

	public List<TreeNode> getParents() {
		return parents;
	}

	public List<TreeNode> getChildren() {
		return children;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public String getName() {
		return name;
	}

	public boolean isTerminalNode() {
		return tree instanceof TerminalNode && !isErrorNode();
	}

	public boolean isErrorNode() {
		return tree instanceof ErrorNode;
	}

	public boolean isRuleNode() {
		return tree instanceof RuleContext;
	}

	public Token getSymbol() {
		if (tree instanceof TerminalNode) {
			return ((TerminalNode) tree).getSymbol();
		}

		return ((ParserRuleContext) tree).start;
	}

	@Override
	public String toString() {
		return String.format("%s [%s]", name, id);
	}
}
