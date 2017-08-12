package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.FragmentRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ParserRuleSpecContext;

/**
 * Implementing functions for grammar path walker.
 * 
 * TODO: handle modes
 */
public abstract class PathAdaptor extends Processor {

	private PathsData helper;

	public PathAdaptor(ParseTree tree) {
		super(tree);
	}

	public void setHelper(PathsData helper) {
		this.helper = helper;
	}

	public void parserRef(Token term) {
		ParserRuleSpecContext ruleSpec = (ParserRuleSpecContext) pathNodes().get(1);
		Token ref = ruleSpec.RULE_REF().getSymbol();
		boolean parserRule = Character.isLowerCase(ref.getText().charAt(0));
		helper.addPathsElement(ref, term, parserRule, false);
	}

	public void lexerRef(Token term) {
		LexerRuleSpecContext ruleSpec = (LexerRuleSpecContext) pathNodes().get(1);
		Token ref = ruleSpec.TOKEN_REF().getSymbol();
		helper.addPathsElement(ref, term, false, false);
	}

	public void fragmentRef(Token term) {
		FragmentRuleSpecContext ruleSpec = (FragmentRuleSpecContext) pathNodes().get(1);
		Token ref = ruleSpec.TOKEN_REF().getSymbol();
		helper.addPathsElement(ref, term, false, true);
	}
}
