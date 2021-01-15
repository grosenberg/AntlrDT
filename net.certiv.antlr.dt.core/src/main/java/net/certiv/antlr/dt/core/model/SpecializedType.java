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

	RuleRef("Rule Ref", "ruleref"),
	ParserAtomRef("Parser atom ref", "parser_atomref"),
	LexerAtomRef("Lexer atom ref", "lexer_atomref"),
	LexerCmdExpr("Lexer command expr", "lexer_cmdexpr"),

	ParserRuleName("Parser rulename", "parser_rulename"),
	LexerRuleName("Lexer rulename", "lexer_rulename"),
	FragmentRuleName("Fragment rulename", "fragment_rulename"),

	Range("Range", "range"),
	Set("Set", "set"),
	Terminal("Terminal", "terminal"),

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
