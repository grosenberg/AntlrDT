package net.certiv.antlrdt.core.parser;

import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ActionContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.BlockContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ChannelsSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.DelegateGrammarsContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.FragmentRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarTypeContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.IdContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerBlockContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ModeRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.OptionContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.OptionsSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ParserRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RuleModifierContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RuleModifiersContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.RulerefContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.TerminalContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.TokensSpecContext;
import net.certiv.dsl.core.model.IDslElement;
import net.certiv.dsl.core.model.IStatement.Form;
import net.certiv.dsl.core.model.IStatement.Realm;
import net.certiv.dsl.core.model.IStatement.Type;
import net.certiv.dsl.core.model.builder.DslModelMaker;

/** Implementing functions for model tree walker. */
public abstract class StructureBuilder extends Processor {

	private DslModelMaker maker;

	public StructureBuilder(ParseTree tree) {
		super(tree);
	}

	public void setMaker(DslModelMaker maker) {
		this.maker = maker;
	}

	/** Called on a GrammarSpecContext node. */
	public void grammarModule() {
		GrammarSpecContext ctx = (GrammarSpecContext) lastPathNode();
		GrammarTypeContext gtx = ctx.grammarType();
		int type;
		String name = ctx.id().getText();
		if (gtx.PARSER() != null) {
			name += " [parser]";
			type = ModelData.PARSER;
		} else if (gtx.LEXER() != null) {
			name += " [lexer]";
			type = ModelData.LEXER;
		} else {
			name += " [combined]";
			type = ModelData.COMBINED;
		}

		ModelData data = new ModelData(ModelType.GrammarDef, ctx, name);
		data.setDecoration(type);
		maker.module(ctx, ctx.id(), data);
		addDeclField(ctx.id(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);
	}

	/** Called for action blocks. Block content is handled as an aggregate of native code. */
	public void processAction() {
		ActionContext ctx = (ActionContext) lastPathNode();
		String scope = ctx.actionScopeName() != null ? ctx.actionScopeName().getText() : "";
		ModelData data = new ModelData(ModelType.AtAction, ctx, scope, ctx.id());
		maker.statement(ctx, ctx.id(), data);
		addDeclField(ctx.id(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.BOUND);
	}

	/** Called for each import statement; one import name per statement. */
	public void processImportStatement() {
		DelegateGrammarsContext ctx = (DelegateGrammarsContext) pathNodes().get(1);
		IdContext ctxId = (IdContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Import, ctx, ctxId.getText());
		maker.importStmt(ctx, ctxId, data);
		addDeclField(ctxId, ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);
	}

	/** Called to begin the channnels block. */
	public void begChannelsBlock() {
		ChannelsSpecContext ctx = (ChannelsSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Channel, ctx, "Channels block");
		maker.statement(ctx, ctx, data);
		maker.block(IDslElement.BEG_BLOCK, ctx.LBRACE(), ctx.RBRACE(), null);
	}

	/** Called for each channel identifier within the block. */
	public void processChannelsStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Value, ctx, ctx.getText());
		maker.field(ctx, ctx, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL, data);
	}

	/** Called to begin the options block. */
	public void begOptionsBlock() {
		OptionsSpecContext ctx = (OptionsSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Options, ctx, "Options block");
		maker.statement(ctx, ctx.OPTIONS(), data);
		maker.block(IDslElement.BEG_BLOCK, ctx.LBRACE(), ctx.RBRACE(), null);
	}

	/** Called for each option within the options block. */
	public void processOptionStatement() {
		OptionContext ctx = (OptionContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Option, ctx, ctx.id().getText(), ctx.optionValue());
		maker.statement(ctx.id(), ctx.id(), data);
	}

	/** Called to begin the tokens block. */
	public void begTokensBlock() {
		TokensSpecContext ctx = (TokensSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Tokens, ctx, "Tokens block");
		maker.statement(ctx, ctx.TOKENS(), data);
		maker.block(IDslElement.BEG_BLOCK, ctx.LBRACE(), ctx.RBRACE(), null);
	}

	/** Called for each token within the tokens block. */
	public void processTokenStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		if (ctx.TOKEN_REF() != null) {
			ModelData data = new ModelData(ModelType.Token, ctx, ctx.TOKEN_REF().getText());
			maker.statement(ctx, ctx.TOKEN_REF(), data);
		}
	}

	/** Called for a parser rule specification. */
	public void begParserRule() {
		ParserRuleSpecContext ctx = (ParserRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.ParserRule, ctx, ctx.RULE_REF().getText());
		data.setAspects(Type.TYPE, Form.DECLARATION, Realm.GLOBAL);
		RuleModifiersContext mods = ctx.ruleModifiers();
		if (mods != null) {
			for (RuleModifierContext mod : mods.ruleModifier()) {
				if (mod.PROTECTED() != null) {
					data.setDecoration(ModelData.PROTECTED);
				} else if (mod.PRIVATE() != null) {
					data.setDecoration(ModelData.PRIVATE);
				} else if (mod.PUBLIC() != null) {
					data.setDecoration(ModelData.PUBLIC);
				}
			}
		}
		maker.statement(ctx, ctx.RULE_REF(), data);
		addDeclField(ctx.RULE_REF(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);
		maker.block(IDslElement.BEG_BLOCK, ctx.COLON(), ctx.SEMI(), null);
	}

	/** Called to begin a parser alt list block. */
	public void begParserBlock() {
		BlockContext ctx = (BlockContext) lastPathNode();
		maker.block(IDslElement.BEG_BLOCK, ctx.LPAREN(), ctx.RPAREN(), null);
	}

	/** Called for each label reference within a parser rule specification. */
	public void addLabelRef() {
		IdContext ctx = (IdContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LabelId, ctx, ctx.RULE_REF().getText());
		data.setAspects(Type.VARIABLE, Form.INFERRED, Realm.LOCAL);
		maker.field(ctx, ctx.RULE_REF(), data.type, data.form, data.realm, data);
	}

	/** Called for each parser rule referenced in a rule specification. */
	public void addRuleRef() {
		RulerefContext ctx = (RulerefContext) lastPathNode();
		ModelData data = new ModelData(ModelType.ParserRule, ctx, ctx.RULE_REF().getText());
		data.setAspects(Type.TYPE, Form.REFERENCE, Realm.GLOBAL);
		maker.field(ctx, ctx.RULE_REF(), data.type, data.form, data.realm, data);
	}

	/** Called for each lexer rule specification. */
	public void begLexerRule() {
		LexerRuleSpecContext ctx = (LexerRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LexerRule, ctx, ctx.TOKEN_REF().getText());
		data.setAspects(Type.TYPE, Form.DECLARATION, Realm.GLOBAL);
		maker.statement(ctx, ctx.TOKEN_REF(), data);
		addDeclField(ctx.TOKEN_REF(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);
		maker.block(IDslElement.BEG_BLOCK, ctx.COLON(), ctx.SEMI(), null);
	}

	/** Called to begin a lexer mode block. */
	public void begModeBlock() {
		ModeRuleSpecContext ctx = (ModeRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Mode, ctx, ctx.id().getText() + " [mode]");
		maker.statement(ctx, ctx.id(), data);
		maker.block(IDslElement.BEG_BLOCK, ctx.MODE(), ctx.SEMI(), null);
	}

	/** Called to begin a lexer alt list block. */
	public void begLexerBlock() {
		LexerBlockContext ctx = (LexerBlockContext) lastPathNode();
		maker.block(IDslElement.BEG_BLOCK, ctx.LPAREN(), ctx.RPAREN(), null);
	}

	/** Called for each lexer rule specification. */
	public void begFragmentRule() {
		FragmentRuleSpecContext ctx = (FragmentRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LexerRule, ctx, ctx.TOKEN_REF().getText());
		data.setAspects(Type.TYPE, Form.DECLARATION, Realm.GLOBAL);
		data.setDecoration(ModelData.FRAGMENT);
		maker.statement(ctx, ctx.TOKEN_REF(), data);
		addDeclField(ctx.TOKEN_REF(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);
		maker.block(IDslElement.BEG_BLOCK, ctx.COLON(), ctx.SEMI(), null);
	}

	/** Called for each terminal referenced in a parser or lexer rule specification. */
	public void addTerminalRef() {
		TerminalContext ctx = (TerminalContext) lastPathNode();
		if (ctx.TOKEN_REF() != null) {
			ModelData data = new ModelData(ModelType.LexerRule, ctx, ctx.TOKEN_REF().getText());
			data.setAspects(Type.TYPE, Form.REFERENCE, Realm.GLOBAL);
			maker.field(ctx, ctx.TOKEN_REF(), data.type, data.form, data.realm, data);
		}
	}

	/** Called to close a currently open block of statements. */
	public void endBlock() {
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		if (ctx instanceof ChannelsSpecContext) {
			ChannelsSpecContext cty = (ChannelsSpecContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.LBRACE(), cty.RBRACE(), null);

		} else if (ctx instanceof OptionsSpecContext) {
			OptionsSpecContext cty = (OptionsSpecContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.LBRACE(), cty.RBRACE(), null);

		} else if (ctx instanceof TokensSpecContext) {
			TokensSpecContext cty = (TokensSpecContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.LBRACE(), cty.RBRACE(), null);

		} else if (ctx instanceof ParserRuleSpecContext) {
			ParserRuleSpecContext cty = (ParserRuleSpecContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.COLON(), cty.SEMI(), null);

		} else if (ctx instanceof BlockContext) {
			BlockContext cty = (BlockContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		} else if (ctx instanceof LexerRuleSpecContext) {
			LexerRuleSpecContext cty = (LexerRuleSpecContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.COLON(), cty.SEMI(), null);

		} else if (ctx instanceof ModeRuleSpecContext) {
			ModeRuleSpecContext cty = (ModeRuleSpecContext) ctx;
			List<LexerRuleSpecContext> ctzs = cty.lexerRuleSpec();
			LexerRuleSpecContext ctz = ctzs.get(ctzs.size() - 1);
			maker.block(IDslElement.END_BLOCK, cty.SEMI(), ctz.SEMI(), "mode end", null);

		} else if (ctx instanceof FragmentRuleSpecContext) {
			FragmentRuleSpecContext cty = (FragmentRuleSpecContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.COLON(), cty.SEMI(), null);

		} else if (ctx instanceof LexerBlockContext) {
			LexerBlockContext cty = (LexerBlockContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.LPAREN(), cty.RPAREN(), null);
		}
	}

	private void addDeclField(ParseTree ctx, ModelType mType, Type type, Form form, Realm realm) {
		maker.field(ctx, ctx, type, form, realm, new ModelData(mType, ctx, ctx.getText()));
	}
}
