package net.certiv.antlrdt.core.model;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ActionContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.AtomContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.BlockContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ChannelsSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.DeclarationContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.DelegateGrammarsContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.FragmentRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarTypeContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.IdContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerAtomContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerBlockContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerCommandExprContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ModeRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.OptionContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.OptionsSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ParserRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RangeContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RuleModifierContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RuleModifiersContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RulerefContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.SetElementContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.TerminalContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.TokensSpecContext;
import net.certiv.dsl.core.model.Block;
import net.certiv.dsl.core.model.DeclarationStmt;
import net.certiv.dsl.core.model.IDslElement;
import net.certiv.dsl.core.model.IStatement.BaseType;
import net.certiv.dsl.core.model.ImportStmt;
import net.certiv.dsl.core.model.ModuleStmt;
import net.certiv.dsl.core.model.Statement;
import net.certiv.dsl.core.model.builder.ModelBuilder;

/** Implementing functions for model tree walker. */
public abstract class StructureBuilder extends Processor {

	private ModelBuilder builder;
	private String name = "<Undefined>"; // typically, the source file name

	public StructureBuilder(ParseTree tree) {
		super(tree);
	}

	public void setMaker(ModelBuilder builder) {
		this.builder = builder;
	}

	public void setSourceName(String name) {
		this.name = name;
	}

	/** Called on a GrammarSpecContext node. */
	public void doModule() {
		GrammarSpecContext ctx = (GrammarSpecContext) lastPathNode();
		SpecData data = new SpecData(BaseType.UNIT, SpecType.GrammarRoot, ruleName(ctx), ctx, name);
		ModuleStmt module = builder.module(ctx, name, data);
		builder.pushParent(module);
	}

	/** Called on a grammar declaration context node. */
	public void doDeclaration() {
		DeclarationContext ctx = (DeclarationContext) lastPathNode();
		GrammarTypeContext gtx = ctx.grammarType();
		int qualifier;
		String name = ctx.id().getText();
		if (gtx.PARSER() != null) {
			name += " [parser grammar]";
			qualifier = SpecData.PARSER;
		} else if (gtx.LEXER() != null) {
			name += " [lexer grammar]";
			qualifier = SpecData.LEXER;
		} else {
			name += " [combined grammar]";
			qualifier = SpecData.COMBINED;
		}

		SpecData data = new SpecData(BaseType.DECLARATION, SpecType.Definition, ruleName(ctx), ctx, name);
		data.setDecoration(qualifier);
		DeclarationStmt stmt = builder.declaration(ctx, ctx.id(), data);
		builder.pushParent(stmt);
		addField(BaseType.LITERAL, SpecType.Value, ruleName(ctx), ctx.id());

		// parser grammar has an implied import of the lexer grammar;
		// add the implied import as a field of the grammar statement
		if (qualifier == SpecData.PARSER) {
			String lexName = ctx.id().getText().replace("Parser", "Lexer");
			data = new SpecData(BaseType.IMPORT_IMPLIED, SpecType.Import, ruleName(ctx), ctx, lexName);
			builder.field(ctx, lexName, BaseType.IMPORT_IMPLIED, data);
		}

		builder.popParent();
	}

	/**
	 * Called for action blocks. Block content is handled as an aggregate of native
	 * code.
	 */
	public void doAction() {
		ActionContext ctx = (ActionContext) lastPathNode();
		String scope = ctx.actionScopeName() != null ? ctx.actionScopeName().getText() : "";
		SpecData data = new SpecData(BaseType.NATIVE, SpecType.AtAction, ruleName(ctx), ctx, scope, ctx.id());
		Statement stmt = builder.statement(ctx, ctx.id(), data);
		builder.pushParent(stmt);
		addField(BaseType.LITERAL, SpecType.Value, ruleName(ctx), ctx.id());
		builder.popParent();
	}

	/** Called for each import statement; one import name per statement. */
	public void doImportStatement() {
		DelegateGrammarsContext ctx = (DelegateGrammarsContext) pathNodes().get(1);
		IdContext ctxId = (IdContext) lastPathNode();
		SpecData data = new SpecData(BaseType.IMPORT, SpecType.Import, ruleName(ctx), ctx, ctxId.getText());
		ImportStmt stmt = builder.importStmt(ctx, ctxId, data);
		builder.pushParent(stmt);
		addField(BaseType.IMPORT, SpecType.Import, ruleName(ctx), ctxId);
		builder.popParent();
	}

	/** Called to begin the channnels block. */
	public void doChannelsBlock() {
		ChannelsSpecContext ctx = (ChannelsSpecContext) lastPathNode();
		SpecData data = new SpecData(BaseType.BLOCK, SpecType.Channel, ruleName(ctx), ctx, "Channels block");
		Statement stmt = builder.statement(ctx, ctx, data);
		builder.pushParent(stmt);
	}

	/** Called for each channel identifier within the block. */
	public void doChannelsStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		SpecData data = new SpecData(BaseType.LITERAL, SpecType.Value, ruleName(ctx), ctx, ctx.getText());
		builder.field(ctx, ctx, BaseType.LITERAL, data);
	}

	/** Called to begin the options block. */
	public void doOptionsBlock() {
		OptionsSpecContext ctx = (OptionsSpecContext) lastPathNode();
		SpecData data = new SpecData(BaseType.BLOCK, SpecType.Options, ruleName(ctx), ctx, "Options block");
		Statement stmt = builder.statement(ctx, ctx.OPTIONS(), data);
		builder.pushParent(stmt);
	}

	/** Called for each option within the options block. */
	public void doOptionStatement() {
		OptionContext ctx = (OptionContext) lastPathNode();
		SpecData data = new SpecData(BaseType.LITERAL, SpecType.Option, ruleName(ctx), ctx, ctx.id().getText(),
				ctx.optionValue());
		builder.statement(ctx.id(), ctx.id(), data);
	}

	/** Called to begin the tokens block. */
	public void doTokensBlock() {
		TokensSpecContext ctx = (TokensSpecContext) lastPathNode();
		SpecData data = new SpecData(BaseType.BLOCK, SpecType.Tokens, ruleName(ctx), ctx, "Tokens block");
		Statement stmt = builder.statement(ctx, ctx.TOKENS(), data);
		builder.pushParent(stmt);
	}

	/** Called for each token within the tokens block. */
	public void doTokenStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		String ref = ctx.TOKEN_REF() != null ? ctx.TOKEN_REF().getText() : ctx.getText();
		SpecData data = new SpecData(BaseType.LITERAL, SpecType.Token, ruleName(ctx), ctx, ref);
		builder.statement(ctx, ctx.TOKEN_REF(), data);
	}

	/** Called for a parser rule specification. */
	public void begParserRule() {
		ParserRuleSpecContext ctx = (ParserRuleSpecContext) lastPathNode();
		SpecData data = new SpecData(BaseType.TYPE, SpecType.ParserRule, ruleName(ctx), ctx, ctx.RULE_REF().getText());
		RuleModifiersContext mods = ctx.ruleModifiers();
		if (mods != null) {
			for (RuleModifierContext mod : mods.ruleModifier()) {
				if (mod.PROTECTED() != null) {
					data.setDecoration(SpecData.PROTECTED);
				} else if (mod.PRIVATE() != null) {
					data.setDecoration(SpecData.PRIVATE);
				} else if (mod.PUBLIC() != null) {
					data.setDecoration(SpecData.PUBLIC);
				}
			}
		}
		Statement stmt = builder.statement(ctx, ctx.RULE_REF(), data);
		builder.pushParent(stmt);
		addField(BaseType.LITERAL, SpecType.RuleName, ruleName(ctx), ctx.RULE_REF());
		addBlock(IDslElement.BEG_BLOCK, ctx.COLON(), ctx.SEMI());
	}

	/** Called for each lexer rule specification. */
	public void begLexerRule() {
		LexerRuleSpecContext ctx = (LexerRuleSpecContext) lastPathNode();
		SpecData data = new SpecData(BaseType.TYPE, SpecType.LexerRule, ruleName(ctx), ctx, ctx.TOKEN_REF().getText());
		Statement stmt = builder.statement(ctx, ctx.TOKEN_REF(), data);
		builder.pushParent(stmt);
		addField(BaseType.LITERAL, SpecType.RuleName, ruleName(ctx), ctx.TOKEN_REF());
		addBlock(IDslElement.BEG_BLOCK, ctx.COLON(), ctx.SEMI());
	}

	/** Called for each fragment lexer rule specification. */
	public void begFragmentRule() {
		FragmentRuleSpecContext ctx = (FragmentRuleSpecContext) lastPathNode();
		SpecData data = new SpecData(BaseType.TYPE, SpecType.LexerRule, ruleName(ctx), ctx, ctx.TOKEN_REF().getText());
		data.setDecoration(SpecData.FRAGMENT);
		Statement stmt = builder.statement(ctx, ctx.TOKEN_REF(), data);
		builder.pushParent(stmt);
		addField(BaseType.LITERAL, SpecType.RuleName, ruleName(ctx), ctx.TOKEN_REF());
		addBlock(IDslElement.BEG_BLOCK, ctx.COLON(), ctx.SEMI());
	}

	/** Called to begin a mode block. */
	public void begModeRule() {
		ModeRuleSpecContext ctx = (ModeRuleSpecContext) lastPathNode();
		String modeName = ctx.id().getText() + " [mode]";
		SpecData data = new SpecData(BaseType.DECLARATION, SpecType.Mode, ruleName(ctx), ctx, modeName);
		Statement stmt = builder.statement(ctx, ctx.id(), data);
		builder.pushParent(stmt);
		addField(BaseType.LITERAL, SpecType.ModeName, ctx.id().getText(), ctx.id());
	}

	public void endParserRule() {
		if (builder.peekParent() instanceof Block) {
			builder.popParent();
			ParserRuleSpecContext ctx = (ParserRuleSpecContext) lastPathNode();
			builder.block(IDslElement.END_BLOCK, ctx.COLON(), ctx.SEMI(), null);
		}
		builder.popParent();
	}

	public void endLexerRule() {
		if (builder.peekParent() instanceof Block) {
			builder.popParent();
			LexerRuleSpecContext ctx = (LexerRuleSpecContext) lastPathNode();
			builder.block(IDslElement.END_BLOCK, ctx.COLON(), ctx.SEMI(), null);
		}
		builder.popParent();
	}

	public void endFragmentRule() {
		if (builder.peekParent() instanceof Block) {
			builder.popParent();
			FragmentRuleSpecContext ctx = (FragmentRuleSpecContext) lastPathNode();
			builder.block(IDslElement.END_BLOCK, ctx.COLON(), ctx.SEMI(), null);
		}
		builder.popParent();
	}

	public void endModeRule() {
		builder.popParent();
	}

	public void doCommandExpr() {
		LexerCommandExprContext ctx = (LexerCommandExprContext) lastPathNode();
		addField(BaseType.LITERAL, SpecType.ModeName, ruleName(ctx), ctx);
	}

	public void doAtomRef() {
		AtomContext ctx = (AtomContext) lastPathNode();
		if (ctx.DOT() != null) {
			SpecData data = new SpecData(BaseType.TYPE, SpecType.Value, ruleName(ctx), ctx, ctx.DOT().getText());
			builder.field(ctx, ctx.DOT(), BaseType.TYPE, data);
		}
	}

	public void doLexerAtomRef() {
		LexerAtomContext ctx = (LexerAtomContext) lastPathNode();
		TerminalNode atom = ctx.charSet() != null ? ctx.charSet().LEXER_CHAR_SET() : ctx.DOT();
		if (atom != null) {
			SpecData data = new SpecData(BaseType.TYPE, SpecType.Value, ruleName(ctx), ctx, atom.getText());
			builder.field(ctx, atom, BaseType.TYPE, data);
		}
	}

	/** Called for each parser rule referenced in a rule specification. */
	public void doRuleRef() {
		RulerefContext ctx = (RulerefContext) lastPathNode();
		SpecData data = new SpecData(BaseType.TYPE, SpecType.Value, ruleName(ctx), ctx, ctx.RULE_REF().getText());
		builder.field(ctx, ctx.RULE_REF(), BaseType.TYPE, data);
	}

	/** Called for each label reference within a parser rule specification. */
	public void doLabelRef() {
		IdContext ctx = (IdContext) lastPathNode();
		SpecData data = new SpecData(BaseType.VARIABLE, SpecType.Label, ruleName(ctx), ctx, ctx.RULE_REF().getText());
		builder.field(ctx, ctx.RULE_REF(), BaseType.VARIABLE, data);
	}

	/**
	 * Called for each terminal referenced in a parser or lexer rule specification.
	 */
	public void doTerminalRef() {
		TerminalContext ctx = (TerminalContext) lastPathNode();
		TerminalNode terminal = ctx.TOKEN_REF() != null ? ctx.TOKEN_REF() : ctx.STRING_LITERAL();
		SpecData data = new SpecData(BaseType.TYPE, SpecType.Value, ruleName(ctx), ctx, terminal.getText());
		builder.field(ctx, terminal, BaseType.TYPE, data);
	}

	public void doRangeRef() {
		RangeContext ctx = (RangeContext) lastPathNode();
		SpecData data = new SpecData(BaseType.TYPE, SpecType.Value, ruleName(ctx), ctx, ctx.getText());
		builder.field(ctx, ctx.STRING_LITERAL().get(0), BaseType.TYPE, data);
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
			SpecData data = new SpecData(BaseType.TYPE, SpecType.LexerRule, ruleName(ctx), ctx, elem.getText());
			builder.field(ctx, elem, BaseType.TYPE, data);
		}
	}

	/** Begins an alt term list. */
	public void doBegBlock() {
		Statement stmt = null;
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		if (ctx instanceof BlockContext) {
			BlockContext cty = (BlockContext) lastPathNode();
			stmt = builder.block(IDslElement.BEG_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		} else if (ctx instanceof LexerBlockContext) {
			LexerBlockContext cty = (LexerBlockContext) lastPathNode();
			stmt = builder.block(IDslElement.BEG_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		}
		if (stmt != null) builder.pushParent(stmt);
	}

	/** Called to close a currently open block of statements. */
	public void doEndBlock() {
		builder.popParent();
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();

		if (ctx instanceof BlockContext) {
			BlockContext cty = (BlockContext) ctx;
			builder.block(IDslElement.END_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		} else if (ctx instanceof LexerBlockContext) {
			LexerBlockContext cty = (LexerBlockContext) ctx;
			builder.block(IDslElement.END_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		} else if (ctx instanceof ChannelsSpecContext) {

		} else if (ctx instanceof OptionsSpecContext) {

		} else if (ctx instanceof TokensSpecContext) {

		}
	}

	private void addField(BaseType baseType, SpecType specType, String rulename, ParseTree ctx) {
		if (ctx != null) {
			builder.field(ctx, ctx, baseType, new SpecData(baseType, specType, rulename, ctx, ctx.getText()));
		}
	}

	private void addBlock(int blockType, TerminalNode beg, TerminalNode end) {
		Block block = builder.block(blockType, beg, end, null);
		if (block != null) builder.pushParent(block);
	}

	private String ruleName(ParserRuleContext ctx) {
		return AntlrDT4Parser.ruleNames[ctx.getRuleIndex()];
	}
}
