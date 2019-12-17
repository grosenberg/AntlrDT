package net.certiv.antlrdt.core.model;

import net.certiv.dsl.core.model.builder.ISpecType;

public enum SpecType implements ISpecType {
	GrammarRoot("Grammar", "*"),
	Definition("Definition", ""),

	Options("Options block", ""),
	Option("Option", ""),
	Key("Key", "key"),
	Value("Value", "value"),

	Tokens("Tokens block", ""),
	Token("Token", "token"),

	Import("Import", ""),
	Channel("Channel", ""),

	AtAction("AtAction", ""),
	Block("Block", ""),

	ParserRule("Parser rule", "rule"),
	LexerRule("Lexer rule", "rule"),
	RuleName("Rule name", "rulename"),

	Mode("Mode statement", "mode"),
	ModeName("Mode name", "modename"),

	Label("Label", "label"),

	ActionBlock("Code", "code"),

	Unknown("Unknown", ""),

	;

	public final String name;
	public final String css;

	SpecType(String name, String css) {
		this.name = name;
		this.css = css;
	}

	@Override
	public String toString() {
		return name;
	}
}
