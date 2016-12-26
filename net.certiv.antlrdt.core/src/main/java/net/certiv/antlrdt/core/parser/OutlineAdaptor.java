package net.certiv.antlrdt.core.parser;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ActionContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarTypeContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.IdContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.OptionContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.OptionsSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ParserRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RuleModifierContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RuleModifiersContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RulerefContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.TerminalContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.TokensSpecContext;
import net.certiv.antlrdt.core.parser.gen.OutlineVisitor;
import net.certiv.dsl.core.model.IStatement.Form;
import net.certiv.dsl.core.model.IStatement.Realm;
import net.certiv.dsl.core.model.IStatement.Type;
import net.certiv.dsl.core.parser.IModelConstruction;

/**
 * Implementing functions for outline tree walker.
 */
public abstract class OutlineAdaptor extends Processor {

	private IModelConstruction helper;
	private String gTypeName;
	private ParserRuleContext rootNode;

	public OutlineAdaptor(ParseTree tree) {
		super(tree);
	}

	public void setHelper(IModelConstruction helper) {
		this.helper = helper;
	}

	public void grammarModule() {
		GrammarSpecContext ctx = (GrammarSpecContext) lastPathNode();
		rootNode = ctx;
		captureGrammarType(ctx);
		gTypeName += ctx.id().getText();
		ModelData data = new ModelData(ModelType.GrammarType, rootNode, gTypeName);
		helper.module(rootNode, data);
	}

	private void captureGrammarType(GrammarSpecContext ctx) {
		GrammarTypeContext gtCtx = ctx.grammarType();
		if (gtCtx.PARSER() != null) {
			gTypeName = "parser grammar ";
		} else if (gtCtx.LEXER() != null) {
			gTypeName = "lexer grammar ";
		} else {
			gTypeName = "grammar ";
		}
	}

	public void begOptionsBlock() {
		OptionsSpecContext ctx = (OptionsSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Options, ctx, "Options block");
		helper.statement(ctx, ctx.OPTIONS(), data);
		helper.blockBeg();
	}

	public void processOptionStatement() {
		OptionContext ctx = (OptionContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Option, ctx, ctx.id().getText(), ctx.optionValue());
		helper.statement(ctx.id(), ctx.id(), data);
	}

	public void begTokensBlock() {
		TokensSpecContext ctx = (TokensSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Tokens, ctx, "Tokens block");
		helper.statement(ctx, ctx.TOKENS(), data);
		helper.blockBeg();
	}

	public void processTokenStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		if (ctx.TOKEN_REF() != null) {
			ModelData data = new ModelData(ModelType.Token, ctx, ctx.TOKEN_REF().getText());
			helper.statement(ctx, ctx.TOKEN_REF(), data);
		}
	}

	public void begParserRule() {
		ParserRuleSpecContext ctx = (ParserRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.ParserRule, ctx, ctx.RULE_REF().getText());
		RuleModifiersContext mods = ctx.ruleModifiers();
		if (mods != null) {
			List<RuleModifierContext> mod = mods.ruleModifier();
			if (mod != null) {
				for (RuleModifierContext rmc : mod) {
					if (rmc.PROTECTED() != null) {
						data.setDecoration(ModelData.PROTECTED);
					} else if (rmc.PRIVATE() != null) {
						data.setDecoration(ModelData.PRIVATE);
					} else if (rmc.PUBLIC() != null) {
						data.setDecoration(ModelData.PUBLIC);
					}
				}
			}
		}
		helper.statement(ctx, ctx.RULE_REF(), data);
		helper.blockBeg();
	}

	public void begLexerRule() {
		LexerRuleSpecContext ctx = (LexerRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LexerRule, ctx, ctx.TOKEN_REF().getText());
		if (ctx.FRAGMENT() != null) {
			data.setDecoration(ModelData.FRAGMENT);
		}
		helper.statement(ctx, ctx.TOKEN_REF(), data);
		helper.blockBeg();
	}

	public void processAction() {
		ActionContext ctx = (ActionContext) lastPathNode();
		String asn = ctx.actionScopeName() != null ? ctx.actionScopeName().getText() : "";
		ModelData data = new ModelData(ModelType.AtAction, ctx, asn, ctx.id());
		helper.statement(ctx, ctx.id(), data);
	}

	public void endBlock() {
		helper.blockEnd();
	}

	protected void addRuleDecl() {
		ParserRuleSpecContext ctx = (ParserRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.RuleRef, ctx, ctx.RULE_REF().getText());
		data.setType(Type.TYPE);
		data.setForm(Form.DECLARATION);
		data.setScope(Realm.GLOBAL);
		helper.field(ctx, ctx.RULE_REF(), data.type, data.form, data.realm, data);
	}

	protected void addRuleRef() {
		RulerefContext ctx = (RulerefContext) lastPathNode();
		ModelData data = new ModelData(ModelType.RuleRef, ctx, ctx.RULE_REF().getText());
		data.setType(Type.TYPE);
		data.setForm(Form.REFERENCE);
		data.setScope(Realm.GLOBAL);
		helper.field(ctx, ctx.RULE_REF(), data.type, data.form, data.realm, data);
	}

	protected void addTerminalDecl() {
		TerminalContext ctx = (TerminalContext) lastPathNode();
		ModelData data = new ModelData(ModelType.RuleRef, ctx, ctx.TOKEN_REF().getText());
		data.setType(Type.TYPE);
		data.setForm(Form.DECLARATION);
		data.setScope(Realm.GLOBAL);
		helper.field(ctx, ctx.TOKEN_REF(), data.type, data.form, data.realm, data);
	}

	protected void addTerminalRef() {
		TerminalContext ctx = (TerminalContext) lastPathNode();
		if (ctx.TOKEN_REF() != null) {
			ModelData data = new ModelData(ModelType.RuleRef, ctx, ctx.TOKEN_REF().getText());
			data.setType(Type.TYPE);
			data.setForm(Form.REFERENCE);
			data.setScope(Realm.GLOBAL);
			helper.field(ctx, ctx.TOKEN_REF(), data.type, data.form, data.realm, data);
		}
	}

	protected void addLabelId() {
		if (hasAncestor(OutlineVisitor.labeledLexerElement, OutlineVisitor.labeledElement)) {
			IdContext ctx = (IdContext) lastPathNode();
			ModelData data = new ModelData(ModelType.RuleRef, ctx, ctx.TOKEN_REF().getText());
			data.setType(Type.VARIABLE);
			data.setForm(Form.INFERRED);
			data.setScope(Realm.LOCAL);
			helper.field(ctx, ctx.TOKEN_REF(), data.type, data.form, data.realm, data);
		}
	}
}
