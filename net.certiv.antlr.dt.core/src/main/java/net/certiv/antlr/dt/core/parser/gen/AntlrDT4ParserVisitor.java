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
// Generated from D:/DevFiles/Eclipse/Tools/Editors/net.certiv.antlr.dt/net.certiv.antlr.dt.core/src/main/java/net/certiv/antlr/dt/core/parser/AntlrDT4Parser.g4 by ANTLR 4.8

	package net.certiv.antlr.dt.core.parser.gen;

import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link AntlrDT4Parser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface AntlrDT4ParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#grammarSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammarSpec(AntlrDT4Parser.GrammarSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#declaration}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDeclaration(AntlrDT4Parser.DeclarationContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#grammarType}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitGrammarType(AntlrDT4Parser.GrammarTypeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#prequelConstruct}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPrequelConstruct(AntlrDT4Parser.PrequelConstructContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#optionsSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionsSpec(AntlrDT4Parser.OptionsSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#option}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOption(AntlrDT4Parser.OptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#optionValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitOptionValue(AntlrDT4Parser.OptionValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#delegateGrammars}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDelegateGrammars(AntlrDT4Parser.DelegateGrammarsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#tokensSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTokensSpec(AntlrDT4Parser.TokensSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#channelsSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitChannelsSpec(AntlrDT4Parser.ChannelsSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#idList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdList(AntlrDT4Parser.IdListContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#idListElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIdListElement(AntlrDT4Parser.IdListElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#action}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAction(AntlrDT4Parser.ActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#actionScopeName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitActionScopeName(AntlrDT4Parser.ActionScopeNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#actionBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitActionBlock(AntlrDT4Parser.ActionBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#argActionBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgActionBlock(AntlrDT4Parser.ArgActionBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#rules}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRules(AntlrDT4Parser.RulesContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ruleSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleSpec(AntlrDT4Parser.RuleSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#parserRuleSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParserRuleSpec(AntlrDT4Parser.ParserRuleSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#exceptionGroup}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionGroup(AntlrDT4Parser.ExceptionGroupContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#exceptionHandler}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExceptionHandler(AntlrDT4Parser.ExceptionHandlerContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#finallyClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFinallyClause(AntlrDT4Parser.FinallyClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#rulePrequel}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRulePrequel(AntlrDT4Parser.RulePrequelContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ruleReturns}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleReturns(AntlrDT4Parser.RuleReturnsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#throwsSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitThrowsSpec(AntlrDT4Parser.ThrowsSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#localsSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLocalsSpec(AntlrDT4Parser.LocalsSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ruleAction}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleAction(AntlrDT4Parser.RuleActionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ruleModifiers}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleModifiers(AntlrDT4Parser.RuleModifiersContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ruleModifier}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleModifier(AntlrDT4Parser.RuleModifierContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ruleBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleBlock(AntlrDT4Parser.RuleBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ruleAltList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleAltList(AntlrDT4Parser.RuleAltListContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#labeledAlt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledAlt(AntlrDT4Parser.LabeledAltContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#modeRuleSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitModeRuleSpec(AntlrDT4Parser.ModeRuleSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerRuleSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerRuleSpec(AntlrDT4Parser.LexerRuleSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#fragmentRuleSpec}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFragmentRuleSpec(AntlrDT4Parser.FragmentRuleSpecContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerRuleBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerRuleBlock(AntlrDT4Parser.LexerRuleBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerAltList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerAltList(AntlrDT4Parser.LexerAltListContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerAlt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerAlt(AntlrDT4Parser.LexerAltContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerElements}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerElements(AntlrDT4Parser.LexerElementsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerElement(AntlrDT4Parser.LexerElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#labeledLexerElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledLexerElement(AntlrDT4Parser.LabeledLexerElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerBlock(AntlrDT4Parser.LexerBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerCommands}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerCommands(AntlrDT4Parser.LexerCommandsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerCommand}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerCommand(AntlrDT4Parser.LexerCommandContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerCommandName}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerCommandName(AntlrDT4Parser.LexerCommandNameContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerCommandExpr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerCommandExpr(AntlrDT4Parser.LexerCommandExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#altList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAltList(AntlrDT4Parser.AltListContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#alternative}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAlternative(AntlrDT4Parser.AlternativeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#element}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElement(AntlrDT4Parser.ElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#labeledElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLabeledElement(AntlrDT4Parser.LabeledElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ebnf}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEbnf(AntlrDT4Parser.EbnfContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#blockSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockSuffix(AntlrDT4Parser.BlockSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ebnfSuffix}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEbnfSuffix(AntlrDT4Parser.EbnfSuffixContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#lexerAtom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLexerAtom(AntlrDT4Parser.LexerAtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(AntlrDT4Parser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#notSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotSet(AntlrDT4Parser.NotSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#blockSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlockSet(AntlrDT4Parser.BlockSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#setElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSetElement(AntlrDT4Parser.SetElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#charSet}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCharSet(AntlrDT4Parser.CharSetContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(AntlrDT4Parser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#ruleref}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleref(AntlrDT4Parser.RulerefContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#range}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRange(AntlrDT4Parser.RangeContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#terminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal(AntlrDT4Parser.TerminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#elementOptions}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementOptions(AntlrDT4Parser.ElementOptionsContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#elementOption}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElementOption(AntlrDT4Parser.ElementOptionContext ctx);
	/**
	 * Visit a parse tree produced by {@link AntlrDT4Parser#id}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitId(AntlrDT4Parser.IdContext ctx);
}