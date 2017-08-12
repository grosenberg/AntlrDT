package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlr.runtime.xvisitor.Processor;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ActionContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.FragmentRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarTypeContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.IdContext;
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
import net.certiv.dsl.core.model.IStatement.Form;
import net.certiv.dsl.core.model.IStatement.Realm;
import net.certiv.dsl.core.model.IStatement.Type;
import net.certiv.dsl.core.parser.IModelConstruction;

/**
 * Implementing functions for outline tree walker.
 */
public abstract class OutlineAdaptor extends Processor {

	private IModelConstruction helper;

	public OutlineAdaptor(ParseTree tree) {
		super(tree);
	}

	public void setHelper(IModelConstruction helper) {
		this.helper = helper;
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
		helper.module(ctx, data);
		// Log.debug(this, "Module: " + name);
	}

	/** Called for action blocks. */
	public void processAction() {
		ActionContext ctx = (ActionContext) lastPathNode();
		String asn = ctx.actionScopeName() != null ? ctx.actionScopeName().getText() : "";
		ModelData data = new ModelData(ModelType.AtAction, ctx, asn, ctx.id());
		helper.statement(ctx, ctx.id(), data);
		// Log.debug(this, "Action: " + asn + "::" + ctx.id().getText());
	}

	/** Called to begin an options block. */
	public void begOptionsBlock() {
		OptionsSpecContext ctx = (OptionsSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Options, ctx, "Options block");
		helper.statement(ctx, ctx.OPTIONS(), data);
		helper.blockBeg();
		// Log.debug(this, "Options:");
	}

	/** Called for each option within an options block. */
	public void processOptionStatement() {
		OptionContext ctx = (OptionContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Option, ctx, ctx.id().getText(), ctx.optionValue());
		helper.statement(ctx.id(), ctx.id(), data);
		// Log.debug(this, "\t" + ctx.id().getText() + "=" + ctx.optionValue().getText());
	}

	/** Called to begin a tokens block. */
	public void begTokensBlock() {
		TokensSpecContext ctx = (TokensSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Tokens, ctx, "Tokens block");
		helper.statement(ctx, ctx.TOKENS(), data);
		helper.blockBeg();
		// Log.debug(this, "Tokens:");
	}

	/** Called for each token within a tokens block. */
	public void processTokenStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		if (ctx.TOKEN_REF() != null) {
			ModelData data = new ModelData(ModelType.Token, ctx, ctx.TOKEN_REF().getText());
			helper.statement(ctx, ctx.TOKEN_REF(), data);
			// Log.debug(this, "\t" + ctx.TOKEN_REF().getText());
		}
	}

	/** Called for each parser rule specification. */
	public void begParserRule() {
		ParserRuleSpecContext ctx = (ParserRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.ParserRule, ctx, ctx.RULE_REF().getText());
		data.setType(Type.TYPE);
		data.setForm(Form.DECLARATION);
		data.setScope(Realm.GLOBAL);
		RuleModifiersContext mods = ctx.ruleModifiers();
		if (mods != null) {
			for (RuleModifierContext rmc : mods.ruleModifier()) {
				if (rmc.PROTECTED() != null) {
					data.setDecoration(ModelData.PROTECTED);
				} else if (rmc.PRIVATE() != null) {
					data.setDecoration(ModelData.PRIVATE);
				} else if (rmc.PUBLIC() != null) {
					data.setDecoration(ModelData.PUBLIC);
				}
			}
		}
		helper.statement(ctx, ctx.RULE_REF(), data);
		helper.blockBeg();
		// Log.debug(this, "Parser rule: " + ctx.RULE_REF().getText());
	}

	/** Called for each parser rule referenced in a rule specification. */
	public void addRuleRef() {
		RulerefContext ctx = (RulerefContext) lastPathNode();
		ModelData data = new ModelData(ModelType.ParserRule, ctx, ctx.RULE_REF().getText());
		data.setType(Type.TYPE);
		data.setForm(Form.REFERENCE);
		data.setScope(Realm.GLOBAL);
		helper.field(ctx, ctx.RULE_REF(), data.type, data.form, data.realm, data);
		// Log.debug(this, "Parser ref: " + ctx.RULE_REF().getText());
	}

	/** Called for each label reference within a parser rule specification. */
	public void addLabelRef() {
		IdContext ctx = (IdContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LableId, ctx, ctx.RULE_REF().getText());
		data.setType(Type.VARIABLE);
		data.setForm(Form.INFERRED);
		data.setScope(Realm.LOCAL);
		helper.field(ctx, ctx.RULE_REF(), data.type, data.form, data.realm, data);
		// Log.debug(this, "Label ref: " + ctx.RULE_REF().getText());
	}

	/** Called for each lexer rule specification. */
	public void begLexerRule() {
		LexerRuleSpecContext ctx = (LexerRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LexerRule, ctx, ctx.TOKEN_REF().getText());
		data.setType(Type.TYPE);
		data.setForm(Form.DECLARATION);
		data.setScope(Realm.GLOBAL);
		helper.statement(ctx, ctx.TOKEN_REF(), data);
		helper.blockBeg();
		// Log.debug(this, "Lexer rule: " + ctx.TOKEN_REF().getText());
	}

	/** Called for each lexer rule specification. */
	public void begFragmentRule() {
		FragmentRuleSpecContext ctx = (FragmentRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LexerRule, ctx, ctx.TOKEN_REF().getText());
		data.setType(Type.TYPE);
		data.setForm(Form.DECLARATION);
		data.setScope(Realm.GLOBAL);
		if (ctx.FRAGMENT() != null) {
			data.setDecoration(ModelData.FRAGMENT);
		}
		helper.statement(ctx, ctx.TOKEN_REF(), data);
		helper.blockBeg();
		// Log.debug(this, "Fragment rule: " + ctx.TOKEN_REF().getText());
	}

	/**
	 * Called for each terminal referenced in a parser or lexer rule specification.
	 */
	public void addTerminalRef() {
		TerminalContext ctx = (TerminalContext) lastPathNode();
		if (ctx.TOKEN_REF() != null) {
			ModelData data = new ModelData(ModelType.LexerRule, ctx, ctx.TOKEN_REF().getText());
			data.setType(Type.TYPE);
			data.setForm(Form.REFERENCE);
			data.setScope(Realm.GLOBAL);
			helper.field(ctx, ctx.TOKEN_REF(), data.type, data.form, data.realm, data);
			// Log.debug(this, "Terminal ref: " + ctx.TOKEN_REF().getText());

		} else if (ctx.STRING_LITERAL() != null) {}
	}

	/** Called to begin a lexer mode block. */
	public void begModeBlock() {
		ModeRuleSpecContext ctx = (ModeRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Mode, ctx, ctx.id().getText()+ " [mode]");
		helper.statement(ctx, ctx.id(), data);
		helper.blockBeg();
		// Log.debug(this, "Mode: " + ctx.id().getText());
	}

	/** Closes any currently open block of statements. */
	public void endBlock() {
		helper.blockEnd();
	}
}
