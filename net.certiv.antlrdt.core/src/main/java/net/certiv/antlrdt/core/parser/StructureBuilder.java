package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.antlr.runtime.xvisitor.Processor;
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
import net.certiv.dsl.core.model.IStatement.Form;
import net.certiv.dsl.core.model.IStatement.Realm;
import net.certiv.dsl.core.model.IStatement.Type;
import net.certiv.dsl.core.model.ImportStmt;
import net.certiv.dsl.core.model.ModuleStmt;
import net.certiv.dsl.core.model.Statement;
import net.certiv.dsl.core.model.builder.DslModelMaker;

/** Implementing functions for model tree walker. */
public abstract class StructureBuilder extends Processor {

	private DslModelMaker maker;
	private String name = "<Undefined>"; // typically, the source file name

	public StructureBuilder(ParseTree tree) {
		super(tree);
	}

	public void setMaker(DslModelMaker maker) {
		this.maker = maker;
	}

	public void setSourceName(String name) {
		this.name = name;
	}

	/** Called on a GrammarSpecContext node. */
	public void doModule() {
		GrammarSpecContext ctx = (GrammarSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.GrammarRoot, ctx, name);
		ModuleStmt module = maker.module(ctx, name, data);
		maker.pushParent(module);
	}

	/** Called on a grammar declaration context node. */
	public void doDeclaration() {
		DeclarationContext ctx = (DeclarationContext) lastPathNode();
		GrammarTypeContext gtx = ctx.grammarType();
		int type;
		String name = ctx.id().getText();
		if (gtx.PARSER() != null) {
			name += " [parser grammar]";
			type = ModelData.PARSER;
		} else if (gtx.LEXER() != null) {
			name += " [lexer grammar]";
			type = ModelData.LEXER;
		} else {
			name += " [combined grammar]";
			type = ModelData.COMBINED;
		}

		ModelData data = new ModelData(ModelType.Definition, ctx, name);
		data.setDecoration(type);
		DeclarationStmt stmt = maker.declaration(ctx, ctx.id(), data);
		maker.pushParent(stmt);
		addField(ctx.id(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);

		// parser grammar has an implied import of the lexer grammar
		if (type == ModelData.PARSER) {
			String lexName = ctx.id().getText().replace("Parser", "Lexer");
			ModelData implied = new ModelData(ModelType.Import, ctx, lexName);
			maker.importStmt(ctx.id(), lexName, implied);
		}

		maker.popParent();
	}

	/**
	 * Called for action blocks. Block content is handled as an aggregate of native
	 * code.
	 */
	public void doAction() {
		ActionContext ctx = (ActionContext) lastPathNode();
		String scope = ctx.actionScopeName() != null ? ctx.actionScopeName().getText() : "";
		ModelData data = new ModelData(ModelType.AtAction, ctx, scope, ctx.id());
		Statement stmt = maker.statement(ctx, ctx.id(), data);
		maker.pushParent(stmt);
		addField(ctx.id(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.BOUND);
		maker.popParent();
	}

	/** Called for each import statement; one import name per statement. */
	public void doImportStatement() {
		DelegateGrammarsContext ctx = (DelegateGrammarsContext) pathNodes().get(1);
		IdContext ctxId = (IdContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Import, ctx, ctxId.getText());
		ImportStmt stmt = maker.importStmt(ctx, ctxId, data);
		maker.pushParent(stmt);
		addField(ctxId, ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);
		maker.popParent();
	}

	/** Called to begin the channnels block. */
	public void doChannelsBlock() {
		ChannelsSpecContext ctx = (ChannelsSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Channel, ctx, "Channels block");
		Statement stmt = maker.statement(ctx, ctx, data);
		maker.pushParent(stmt);
	}

	/** Called for each channel identifier within the block. */
	public void doChannelsStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Value, ctx, ctx.getText());
		maker.field(ctx, ctx, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL, data);
	}

	/** Called to begin the options block. */
	public void doOptionsBlock() {
		OptionsSpecContext ctx = (OptionsSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Options, ctx, "Options block");
		Statement stmt = maker.statement(ctx, ctx.OPTIONS(), data);
		maker.pushParent(stmt);
	}

	/** Called for each option within the options block. */
	public void doOptionStatement() {
		OptionContext ctx = (OptionContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Option, ctx, ctx.id().getText(), ctx.optionValue());
		maker.statement(ctx.id(), ctx.id(), data);
	}

	/** Called to begin the tokens block. */
	public void doTokensBlock() {
		TokensSpecContext ctx = (TokensSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Tokens, ctx, "Tokens block");
		Statement stmt = maker.statement(ctx, ctx.TOKENS(), data);
		maker.pushParent(stmt);
	}

	/** Called for each token within the tokens block. */
	public void doTokenStatement() {
		IdContext ctx = (IdContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Token, ctx, ctx.TOKEN_REF().getText());
		maker.statement(ctx, ctx.TOKEN_REF(), data);
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
		Statement stmt = maker.statement(ctx, ctx.RULE_REF(), data);
		maker.pushParent(stmt);
		addField(ctx.RULE_REF(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);
		addBlock(IDslElement.BEG_BLOCK, ctx.COLON(), ctx.SEMI());
	}

	/** Called for each lexer rule specification. */
	public void begLexerRule() {
		LexerRuleSpecContext ctx = (LexerRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LexerRule, ctx, ctx.TOKEN_REF().getText());
		data.setAspects(Type.TYPE, Form.DECLARATION, Realm.GLOBAL);
		Statement stmt = maker.statement(ctx, ctx.TOKEN_REF(), data);
		maker.pushParent(stmt);
		addField(ctx.TOKEN_REF(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);
		addBlock(IDslElement.BEG_BLOCK, ctx.COLON(), ctx.SEMI());
	}

	/** Called for each lexer rule specification. */
	public void begFragmentRule() {
		FragmentRuleSpecContext ctx = (FragmentRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LexerRule, ctx, ctx.TOKEN_REF().getText());
		data.setAspects(Type.TYPE, Form.DECLARATION, Realm.GLOBAL);
		data.setDecoration(ModelData.FRAGMENT);
		Statement stmt = maker.statement(ctx, ctx.TOKEN_REF(), data);
		maker.pushParent(stmt);
		addField(ctx.TOKEN_REF(), ModelType.Value, Type.LITERAL, Form.DECLARATION, Realm.GLOBAL);
		addBlock(IDslElement.BEG_BLOCK, ctx.COLON(), ctx.SEMI());
	}

	/** Called to begin a mode block. */
	public void begModeRule() {
		ModeRuleSpecContext ctx = (ModeRuleSpecContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Mode, ctx, ctx.id().getText() + " [mode]");
		Statement stmt = maker.statement(ctx, ctx.id(), data);
		maker.pushParent(stmt);
	}

	public void endParserRule() {
		if (maker.peekParent() instanceof Block) {
			maker.popParent();
			ParserRuleSpecContext ctx = (ParserRuleSpecContext) lastPathNode();
			maker.block(IDslElement.END_BLOCK, ctx.COLON(), ctx.SEMI(), null);
		}
		maker.popParent();
	}

	public void endLexerRule() {
		if (maker.peekParent() instanceof Block) {
			maker.popParent();
			LexerRuleSpecContext ctx = (LexerRuleSpecContext) lastPathNode();
			maker.block(IDslElement.END_BLOCK, ctx.COLON(), ctx.SEMI(), null);
		}
		maker.popParent();
	}

	public void endFragmentRule() {
		if (maker.peekParent() instanceof Block) {
			maker.popParent();
			FragmentRuleSpecContext ctx = (FragmentRuleSpecContext) lastPathNode();
			maker.block(IDslElement.END_BLOCK, ctx.COLON(), ctx.SEMI(), null);
		}
		maker.popParent();
	}

	public void endModeRule() {
		maker.popParent();
	}

	public void doAtomRef() {
		AtomContext ctx = (AtomContext) lastPathNode();
		if (ctx.DOT() != null) {
			ModelData data = new ModelData(ModelType.Value, ctx, ctx.DOT().getText());
			data.setAspects(Type.TYPE, Form.REFERENCE, Realm.GLOBAL);
			maker.field(ctx, ctx.DOT(), data.type, data.form, data.realm, data);
		}
	}

	public void doLexerAtomRef() {
		LexerAtomContext ctx = (LexerAtomContext) lastPathNode();
		TerminalNode atom = ctx.charSet() != null ? ctx.charSet().LEXER_CHAR_SET() : ctx.DOT();
		if (atom != null) {
			ModelData data = new ModelData(ModelType.Value, ctx, atom.getText());
			data.setAspects(Type.TYPE, Form.REFERENCE, Realm.GLOBAL);
			maker.field(ctx, atom, data.type, data.form, data.realm, data);
		}
	}

	/** Called for each parser rule referenced in a rule specification. */
	public void doRuleRef() {
		RulerefContext ctx = (RulerefContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Value, ctx, ctx.RULE_REF().getText());
		data.setAspects(Type.TYPE, Form.REFERENCE, Realm.GLOBAL);
		maker.field(ctx, ctx.RULE_REF(), data.type, data.form, data.realm, data);
	}

	/** Called for each label reference within a parser rule specification. */
	public void doLabelRef() {
		IdContext ctx = (IdContext) lastPathNode();
		ModelData data = new ModelData(ModelType.LabelId, ctx, ctx.RULE_REF().getText());
		data.setAspects(Type.VARIABLE, Form.INFERRED, Realm.LOCAL);
		maker.field(ctx, ctx.RULE_REF(), data.type, data.form, data.realm, data);
	}

	/**
	 * Called for each terminal referenced in a parser or lexer rule specification.
	 */
	public void doTerminalRef() {
		TerminalContext ctx = (TerminalContext) lastPathNode();
		TerminalNode terminal = ctx.TOKEN_REF() != null ? ctx.TOKEN_REF() : ctx.STRING_LITERAL();
		ModelData data = new ModelData(ModelType.Value, ctx, terminal.getText());
		data.setAspects(Type.TYPE, Form.REFERENCE, Realm.GLOBAL);
		maker.field(ctx, terminal, data.type, data.form, data.realm, data);
	}

	public void doRangeRef() {
		RangeContext ctx = (RangeContext) lastPathNode();
		ModelData data = new ModelData(ModelType.Value, ctx, ctx.getText());
		data.setAspects(Type.TYPE, Form.REFERENCE, Realm.GLOBAL);
		maker.field(ctx, ctx.STRING_LITERAL().get(0), data.type, data.form, data.realm, data);
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
			ModelData data = new ModelData(ModelType.LexerRule, ctx, elem.getText());
			data.setAspects(Type.TYPE, Form.REFERENCE, Realm.GLOBAL);
			maker.field(ctx, elem, data.type, data.form, data.realm, data);
		}
	}

	/** Begins an alt term list. */
	public void doBegBlock() {
		Statement stmt = null;
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();
		if (ctx instanceof BlockContext) {
			BlockContext cty = (BlockContext) lastPathNode();
			stmt = maker.block(IDslElement.BEG_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		} else if (ctx instanceof LexerBlockContext) {
			LexerBlockContext cty = (LexerBlockContext) lastPathNode();
			stmt = maker.block(IDslElement.BEG_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		}
		if (stmt != null) maker.pushParent(stmt);
	}

	/** Called to close a currently open block of statements. */
	public void doEndBlock() {
		maker.popParent();
		ParserRuleContext ctx = (ParserRuleContext) lastPathNode();

		if (ctx instanceof BlockContext) {
			BlockContext cty = (BlockContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		} else if (ctx instanceof LexerBlockContext) {
			LexerBlockContext cty = (LexerBlockContext) ctx;
			maker.block(IDslElement.END_BLOCK, cty.LPAREN(), cty.RPAREN(), null);

		} else if (ctx instanceof ChannelsSpecContext) {
			// do nothing more

		} else if (ctx instanceof OptionsSpecContext) {
			// do nothing more

		} else if (ctx instanceof TokensSpecContext) {
			// do nothing more
		}
	}

	private void addField(ParseTree ctx, ModelType mType, Type type, Form form, Realm realm) {
		if (ctx != null) {
			maker.field(ctx, ctx, type, form, realm, new ModelData(mType, ctx, ctx.getText()));
		}
	}

	private void addBlock(int blockType, TerminalNode beg, TerminalNode end) {
		Block block = maker.block(blockType, beg, end, null);
		if (block != null) maker.pushParent(block);
	}
}
