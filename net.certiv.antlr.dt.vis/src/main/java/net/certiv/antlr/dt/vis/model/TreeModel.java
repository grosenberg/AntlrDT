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
package net.certiv.antlr.dt.vis.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

/** Parse Tree connection graph model. */
public class TreeModel implements IModel {

	// key = unique node key; value = node
	private final Map<ParseTree, TreeNode> nodes = new LinkedHashMap<>();

	// key = node; value = out-bound connectors
	private final LinkedHashMap<TreeNode, List<TreeConnector>> connectors = new LinkedHashMap<>();

	private String[] ruleNames = {};
	private String[] tokenNames = {};

	public TreeModel() {}

	public void setVocab(String[] ruleNames, String[] tokenNames) {
		this.ruleNames = ruleNames;
		this.tokenNames = tokenNames;
	}

	public void add(ParserRuleContext parent, ParserRuleContext child) {
		if (parent != null) addConnector(get(parent), get(child));
	}

	public void add(ParseTree parent, ErrorNode node) {
		if (parent != null) addConnector(get(parent), get(node));
	}

	public void add(ParseTree parent, TerminalNode node) {
		if (parent != null) addConnector(get(parent), get(node));
	}

	private void addConnector(TreeNode parent, TreeNode child) {
		if (parent != null) {
			// Log.info(this, String.format("%s -> %s", parent, child));
			TreeConnector connector = new TreeConnector(parent, child);
			addEndpoint(parent, connector);
			addEndpoint(child, connector);

			parent.addChild(child);
			child.addParent(parent);
		}
	}

	private void addEndpoint(TreeNode node, TreeConnector conn) {
		List<TreeConnector> fanouts = connectors.get(node);
		if (fanouts == null) {
			fanouts = new ArrayList<>();
			connectors.put(node, fanouts);
		}
		fanouts.add(conn);
	}

	private TreeNode get(ParseTree key) {
		TreeNode node = nodes.get(key);
		if (node == null) {
			node = new TreeNode(key, getName(key));
			nodes.put(key, node);
		}
		return node;
	}

	private String getName(ParseTree tree) {
		try {
			if (tree instanceof TerminalNode) {
				int type = ((TerminalNode) tree).getSymbol().getType();
				if (type == Token.EOF) return "EOF";
				return tokenNames[type];
			}

			int idx = ((RuleContext) tree).getRuleIndex();
			return ruleNames[idx];

		} catch (Exception e) {
			return "<Unknown>";
		}
	}

	// ------------------------------------------------
	/** Returns the direct callers of the given target. */
	public List<TreeNode> findCallers(TreeNode target) {
		return new ArrayList<>(target.getParents());
	}

	/** Returns the direct callees of the given target. */
	public List<TreeNode> findCallees(TreeNode target) {
		return new ArrayList<>(target.getChildren());
	}

	public List<TreeNode> getNodeList() {
		return new ArrayList<>(connectors.keySet());
	}

	@Override
	public void clear() {
		connectors.clear();
	}

	@Override
	public void dispose() {
		connectors.clear();
	}

	@Override
	public String toString() {
		return "TreeModel [connectors=" + connectors.size() + "]";
	}
}
