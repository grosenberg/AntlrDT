package net.certiv.antlr.dt.core.model;

import net.certiv.dsl.core.model.builder.ISpecializedType;
import net.certiv.dsl.core.util.Strings;

public enum SpecializedType implements ISpecializedType {
	GrammarRoot("Grammar", "grammar"),
	Definition("Definition", Strings.EMPTY),

	Options("Options block", Strings.EMPTY),
	Option("Option", Strings.EMPTY),
	Key("Key", "key"),
	Value("Value", "value"),

	Tokens("Tokens block", Strings.EMPTY),
	Token("Token", "token"),

	Import("Import", Strings.EMPTY),
	Channel("Channel", Strings.EMPTY),

	AtAction("At action", Strings.EMPTY),
	Block("Block", Strings.EMPTY),

	ParserRule("Parser rule", "rule"),
	LexerRule("Lexer rule", "rule"),
	RuleName("Rule name", "rulename"),

	Mode("Mode statement", "mode"),
	ModeName("Mode name", "modename"),

	Label("Label", "label"),

	ActionBlock("Code", "code"),

	Unknown("Unknown", Strings.EMPTY),

	;

	public final String name;
	public final String css;

	SpecializedType(String name, String css) {
		this.name = name;
		this.css = css;
	}

	@Override
	public String getStyle() {
		return css;
	}

	@Override
	public String toString() {
		return name;
	}
}
