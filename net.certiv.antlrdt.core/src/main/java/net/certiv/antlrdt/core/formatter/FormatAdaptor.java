package net.certiv.antlrdt.core.formatter;

import net.certiv.antlr.runtime.xvisitor.Processor;

import org.antlr.v4.runtime.tree.ParseTree;

public abstract class FormatAdaptor extends Processor {

	protected AntlrDTSourceFormatter helper;

	public FormatAdaptor(ParseTree tree) {
		super(tree);
	}

	public void setHelper(AntlrDTSourceFormatter helper) {
		this.helper = helper;
	}
}
