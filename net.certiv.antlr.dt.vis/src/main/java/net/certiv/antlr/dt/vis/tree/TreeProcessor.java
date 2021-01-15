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
package net.certiv.antlr.dt.vis.tree;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.TerminalNode;

import net.certiv.antlr.dt.vis.model.TreeModel;

public class TreeProcessor implements ParseTreeListener {

	private TreeModel model;

	public TreeProcessor(TreeModel model) {
		super();
		this.model = model;
	}

	@Override
	public void enterEveryRule(ParserRuleContext ctx) {
		model.add(ctx.getParent(), ctx);
	}

	@Override
	public void exitEveryRule(ParserRuleContext ctx) {}

	@Override
	public void visitTerminal(TerminalNode node) {
		model.add(node.getParent(), node);
	}

	@Override
	public void visitErrorNode(ErrorNode node) {
		model.add(node.getParent(), node);
	}
}
