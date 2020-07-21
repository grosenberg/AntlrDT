/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.core.model;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.ActionContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.AtomContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.BlockContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.ChannelsSpecContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.DeclarationContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.DelegateGrammarsContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.FragmentRuleSpecContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.GrammarSpecContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.GrammarTypeContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.IdContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.LexerAtomContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.LexerBlockContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.LexerCommandExprContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.LexerRuleSpecContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.ModeRuleSpecContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.OptionContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.OptionsSpecContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.ParserRuleSpecContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.RangeContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.RuleModifierContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.RuleModifiersContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.RulerefContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.SetElementContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.TerminalContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.TokensSpecContext;
import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.dsl.core.model.Block;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.core.model.ImportStmt;
import net.certiv.dsl.core.model.ModelType;
import net.certiv.dsl.core.model.Statement;
import net.certiv.dsl.core.model.builder.ModelBuilder;
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.core.util.antlr.GrammarUtil;

/** Implementing functions for model tree walker. */
public abstract class StructureBuilder extends Processor {

	private ModelBuilder builder;

	public StructureBuilder(ParseTree tree) {
		super(tree);
	}

	public void setBuilder(ModelBuilder builder) {
		this.builder = builder;
	}

	/** Called on a GrammarSpecContext node. */
	public void doModule() {
		GrammarSpecContext ctx = (GrammarSpecContext) lastPathNode();

		DeclarationContext dtx = ctx.declaration();
		GrammarTypeContext gtx = dtx.grammarType();

		String grammarName = dtx.id().getText();
		int qualifier;
		if (gtx.PARSER() != null) {
			grammarName += " [parser grammar]";
			qualifier = Specialization.PARSER;
		} else if (gtx.LEXER() != null) {
			grammarName += " [lexer grammar]";
			qualifier = Specialization.LEXER;
		} else {
			grammarName += " [combined grammar]";
			qualifier = Specialization.COMBINED;
		}

		Specialization data = new Specialization(SpecializedType.GrammarRoot, ruleName(ctx), ctx, grammarName);
		data.setDecoration(qualifier);
		builder.module(ctx, grammarName, data);

		// parser grammar has an implied import of the lexer grammar;
		// add a synthetic import as a child of the grammar statement
		if (qualifier == Specialization.PARSER) {
			String lexName = dtx.id().getText().replace("Parser", "Lexer");
			data = new Specialization(SpecializedType.Import, ruleName(ctx), ctx, lexName);
			data.setDecoration(Specialization.SYNTHETIC);
			ImportStmt stmt = builder.importStmt(ctx, lexName, data);
			stmt.setFlags(IStatement.SYNTHETIC);
		}
	}

	/** Called on a grammar declaration context node. */
	public void doDeclaration() {}

	/**
	 * Called for named or anonymous action statements. The block body content is handled
	 * as an aggregate of native code.
	 * <p>
	 * Declaration{AtAction} -> Native{Value}
	 */
	public void doAction() {
		ActionContext ctx = (ActionContext) lastPathNode();

		String rulename = ruleName(ctx);
		String displayname = ctx.id().getText();
		if (ctx.COLONCOLON() != null) {
			displayname = ctx.actionScopeName().getText() + Strings.COLON2 + displayname;
		}

		Specialization data = new Specialization(SpecializedType.AtAction, rulename, ctx, displayname);
		Statement stmt = builder.statement(ModelType.NATIVE, ctx, ctx.id(), data);

		builder.pushParent(stmt);
		String actionContent = GrammarUtil.getText(ctx.actionBlock().ACTION_CONTENT()).trim();
		actionContent = Strings.ellipsize(actionContent, 32);
		addField(ModelType.NATIVE, SpecializedType.Value, rulename, ctx, actionContent);
		builder.popParent();
	}

	/** Called for each import statement; one import name per statement. */
	public void doImportStatement() {
		DelegateGrammarsContext ctx = (DelegateGrammarsContext) pathNodes().get(1);
		IdContext ctxId = (IdContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.Import, ruleName(ctx), ctx, ctxId.getText());
		ImportStmt stmt = builder.importStmt(ctx, ctxId, data);

		builder.pushParent(stmt);
		addField(ModelType.IMPORT, SpecializedType.Import, ruleName(ctx), ctxId);
		builder.popParent();
	}

	/** Called to begin the channnels block. */
	public void doChannelsBlock() {
		ChannelsSpecContext ctx = (ChannelsSpecContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.Channel, ruleName(ctx), ctx, "Channels Block");
		Statement stmt = builder.statement(ModelType.BLOCK, ctx, ctx, data);
		builder.pushParent(stmt);
	}

	/** Called for each channel identifier within the block. */
	public void doChannelsStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.Value, ruleName(ctx), ctx, ctx.getText());
		builder.field(ctx, ctx, ModelType.LITERAL, data);
	}

	/** Called to begin the options block. */
	public void doOptionsBlock() {
		OptionsSpecContext ctx = (OptionsSpecContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.Options, ruleName(ctx), ctx, "Options Block");
		Statement stmt = builder.statement(ModelType.BLOCK, ctx, ctx.OPTIONS(), data);
		builder.pushParent(stmt);
	}

	/** Called for each option within the options block. */
	public void doOptionStatement() {
		OptionContext ctx = (OptionContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.Option, ruleName(ctx), ctx, ctx.id().getText(),
				ctx.optionValue());
		builder.statement(ModelType.EXPRESSION, ctx.id(), ctx.id(), data);
	}

	/** Called to begin the tokens block. */
	public void doTokensBlock() {
		TokensSpecContext ctx = (TokensSpecContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.Tokens, ruleName(ctx), ctx, "Tokens Block");
		Statement stmt = builder.statement(ModelType.BLOCK, ctx, ctx.TOKENS(), data);
		builder.pushParent(stmt);
	}

	/** Called for each token within the tokens block. */
	public void doTokenStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		String ref = ctx.TOKEN_REF() != null ? ctx.TOKEN_REF().getText() : ctx.getText();
		Specialization data = new Specialization(SpecializedType.Token, ruleName(ctx), ctx, ref);
		builder.statement(ModelType.EXPRESSION, ctx, ctx.TOKEN_REF(), data);
	}

	/** Called for a parser rule specification. */
	public void begParserRule() {
		ParserRuleSpecContext ctx = (ParserRuleSpecContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.ParserRule, ruleName(ctx), ctx,
				ctx.RULE_REF().getText());
		RuleModifiersContext mods = ctx.ruleModifiers();
		if (mods != null) {
			for (RuleModifierContext mod : mods.ruleModifier()) {
				if (mod.PROTECTED() != null) {
					data.setDecoration(Specialization.PROTECTED);
				} else if (mod.PRIVATE() != null) {
					data.setDecoration(Specialization.PRIVATE);
				} else if (mod.PUBLIC() != null) {
					data.setDecoration(Specialization.PUBLIC);
				}
			}
		}
		Statement stmt = builder.statement(ModelType.FUNC, ctx, ctx.RULE_REF(), data);
		builder.pushParent(stmt);
		addField(ModelType.LITERAL, SpecializedType.ParserRuleName, ruleName(ctx), ctx.RULE_REF());
		addBlock(ModelType.BEG_BLOCK, ctx.COLON(), ctx.SEMI());
	}

	/** Called for each lexer rule specification. */
	public void begLexerRule() {
		LexerRuleSpecContext ctx = (LexerRuleSpecContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.LexerRule, ruleName(ctx), ctx,
				ctx.TOKEN_REF().getText());
		Statement stmt = builder.statement(ModelType.FUNC, ctx, ctx.TOKEN_REF(), data);
		builder.pushParent(stmt);
		addField(ModelType.LITERAL, SpecializedType.LexerRuleName, ruleName(ctx), ctx.TOKEN_REF());
		addBlock(ModelType.BEG_BLOCK, ctx.COLON(), ctx.SEMI());
	}

	/** Called for each fragment lexer rule specification. */
	public void begFragmentRule() {
		FragmentRuleSpecContext ctx = (FragmentRuleSpecContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.LexerRule, ruleName(ctx), ctx,
				ctx.TOKEN_REF().getText());
		data.setDecoration(Specialization.FRAGMENT);
		Statement stmt = builder.statement(ModelType.FUNC, ctx, ctx.TOKEN_REF(), data);
		builder.pushParent(stmt);
		addField(ModelType.LITERAL, SpecializedType.FragmentRuleName, ruleName(ctx), ctx.TOKEN_REF());
		addBlock(ModelType.BEG_BLOCK, ctx.COLON(), ctx.SEMI());
	}

	/** Called to begin a mode block. */
	public void begModeRule() {
		ModeRuleSpecContext ctx = (ModeRuleSpecContext) lastPathNode();
		String modeName = ctx.id().getText() + " [mode]";
		Specialization data = new Specialization(SpecializedType.Mode, ruleName(ctx), ctx, modeName);
		Statement stmt = builder.statement(ModelType.BLOCK, ctx, ctx.id(), data);
		builder.pushParent(stmt);
		addField(ModelType.LITERAL, SpecializedType.ModeName, ctx.id().getText(), ctx.id());
	}

	public void endParserRule() {
		if (builder.peekParent() instanceof Block) {
			builder.popParent();
			ParserRuleSpecContext ctx = (ParserRuleSpecContext) lastPathNode();
			builder.block(ModelType.END_BLOCK, ctx.COLON(), ctx.SEMI(), null);
		}
		builder.popParent();
	}

	public void endLexerRule() {
		if (builder.peekParent() instanceof Block) {
			builder.popParent();
			LexerRuleSpecContext ctx = (LexerRuleSpecContext) lastPathNode();
			builder.block(ModelType.END_BLOCK, ctx.COLON(), ctx.SEMI(), null);
		}
		builder.popParent();
	}

	public void endFragmentRule() {
		if (builder.peekParent() instanceof Block) {
			builder.popParent();
			FragmentRuleSpecContext ctx = (FragmentRuleSpecContext) lastPathNode();
			builder.block(ModelType.END_BLOCK, ctx.COLON(), ctx.SEMI(), null);
		}
		builder.popParent();
	}

	public void endModeRule() {
		builder.popParent();
	}

	public void doCommandExpr() {
		LexerCommandExprContext ctx = (LexerCommandExprContext) lastPathNode();
		addField(ModelType.LITERAL, SpecializedType.ModeName, ruleName(ctx), ctx);
	}

	public void doAtomRef() {
		AtomContext ctx = (AtomContext) lastPathNode();
		if (ctx.DOT() != null) {
			Specialization data = new Specialization(SpecializedType.Value, ruleName(ctx), ctx, ctx.DOT().getText());
			builder.field(ctx, ctx.DOT(), ModelType.TYPE, data);
		}
	}

	public void doLexerAtomRef() {
		LexerAtomContext ctx = (LexerAtomContext) lastPathNode();
		TerminalNode atom = ctx.charSet() != null ? ctx.charSet().LEXER_CHAR_SET() : ctx.DOT();
		if (atom != null) {
			Specialization data = new Specialization(SpecializedType.Value, ruleName(ctx), ctx, atom.getText());
			builder.field(ctx, atom, ModelType.TYPE, data);
		}
	}

	/** Called for each parser rule referenced in a rule specification. */
	public void doRuleRef() {
		RulerefContext ctx = (RulerefContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.Value, ruleName(ctx), ctx, ctx.RULE_REF().getText());
		builder.field(ctx, ctx.RULE_REF(), ModelType.TYPE, data);
	}

	/** Called for each label reference within a parser rule specification. */
	public void doLabelRef() {
		IdContext ctx = (IdContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.Label, ruleName(ctx), ctx, ctx.RULE_REF().getText());
		builder.field(ctx, ctx.RULE_REF(), ModelType.VARIABLE, data);
	}

	/**
	 * Called for each terminal referenced in a parser or lexer rule specification.
	 */
	public void doTerminalRef() {
		TerminalContext ctx = (TerminalContext) lastPathNode();
		TerminalNode terminal = ctx.TOKEN_REF() != null ? ctx.TOKEN_REF() : ctx.STRING_LITERAL();
		Specialization data = new Specialization(SpecializedType.Value, ruleName(ctx), ctx, terminal.getText());
		builder.field(ctx, terminal, ModelType.TYPE, data);
	}

	public void doRangeRef() {
		RangeContext ctx = (RangeContext) lastPathNode();
		Specialization data = new Specialization(SpecializedType.Value, ruleName(ctx), ctx, ctx.getText());
		builder.field(ctx, ctx.STRING_LITERAL().get(0), ModelType.TYPE, data);
	}

	public void doSetRef() {
		SetElementContext ctx = (SetElementContext) lastPathNode();
		TerminalNode elem = null;
		if (ctx.TOKEN_REF() != null) {
			elem = ctx.TOKEN_REF();
		} else if (ctx.STRING_LITERAL() != null) {
			elem = ctx.STRING_LITERAL();
		} else if (ctx.charSet() != null) {
			elem = ctx.charSet().LEXER_CHAR_SET();
		}
		if (elem != null) {
			Specialization data = new Specialization(SpecializedType.LexerRule, ruleName(ctx), ctx, elem.getText());
			builder.field(ctx, elem, ModelType.TYPE, data);
		}
	}

	/** Begins an alt term list. */
	public void doBegBlock() {
		Statement stmt = null;
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		if (ctx instanceof BlockContext) {
			BlockContext cty = (BlockContext) lastPathNode();
			stmt = builder.block(ModelType.BEG_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		} else if (ctx instanceof LexerBlockContext) {
			LexerBlockContext cty = (LexerBlockContext) lastPathNode();
			stmt = builder.block(ModelType.BEG_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		}
		if (stmt != null) builder.pushParent(stmt);
	}

	/** Called to close a currently open block of statements. */
	public void doEndBlock() {
		builder.popParent();
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();

		if (ctx instanceof BlockContext) {
			BlockContext cty = (BlockContext) ctx;
			builder.block(ModelType.END_BLOCK, cty.LPAREN(), cty.RPAREN(), null);
		} else if (ctx instanceof LexerBlockContext) {
			LexerBlockContext cty = (LexerBlockContext) ctx;
			builder.block(ModelType.END_BLOCK, cty.LPAREN(), cty.RPAREN(), null);
		}
	}

	private void addBlock(ModelType kind, TerminalNode beg, TerminalNode end) {
		Block block = builder.block(kind, beg, end, null);
		if (block != null) builder.pushParent(block);
	}

	private void addField(ModelType mType, SpecializedType sType, String rulename, ParseTree ctx) {
		if (ctx != null) {
			builder.field(ctx, ctx, mType, new Specialization(sType, rulename, ctx, ctx.getText()));
		}
	}

	private void addField(ModelType mType, SpecializedType sType, String rulename, ParseTree ctx, String text) {
		if (ctx != null) {
			builder.field(ctx, ctx, mType, new Specialization(sType, rulename, ctx, text));
		}
	}

	private String ruleName(ParserRuleContext ctx) {
		return AntlrDT4Parser.ruleNames[ctx.getRuleIndex()];
	}
}
