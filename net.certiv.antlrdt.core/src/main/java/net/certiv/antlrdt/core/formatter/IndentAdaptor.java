package net.certiv.antlrdt.core.formatter;

import net.certiv.antlr.runtime.xvisitor.Processor;

import org.antlr.v4.runtime.tree.ParseTree;

public abstract class IndentAdaptor extends Processor {

	protected AntlrDTSourceFormatter helper;

	public IndentAdaptor(ParseTree tree) {
		super(tree);
	}

	public void setHelper(AntlrDTSourceFormatter helper) {
		this.helper = helper;
	}
}
