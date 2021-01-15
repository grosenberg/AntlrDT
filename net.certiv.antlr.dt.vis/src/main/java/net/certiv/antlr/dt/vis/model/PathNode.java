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
import java.util.LinkedHashSet;
import java.util.List;

import net.certiv.antlr.dt.core.model.Target;
import net.certiv.antlr.dt.core.parser.AntlrToken;
import net.certiv.dsl.core.model.IStatement;

public class PathNode {

	// in-bound connections
	private final List<PathNode> parents = new ArrayList<>();
	// out-bound connections
	private final List<PathNode> children = new ArrayList<>();
	// equivalent statements
	private final LinkedHashSet<IStatement> stmts = new LinkedHashSet<>();

	private Target target;
	private boolean hidden;

	public PathNode(IStatement stmt, Target target) {
		addStatement(stmt, target);
	}

	public void addStatement(IStatement stmt, Target target) {
		stmts.add(stmt);
		if (this.target != Target.FRAGMENT) {
			this.target = target;
		}
	}

	public void addParent(PathNode node) {
		parents.add(node);
	}

	public void addChild(PathNode node) {
		children.add(node);
	}

	public List<PathNode> getParents() {
		return parents;
	}

	public List<PathNode> getChildren() {
		return children;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	public AntlrToken getNameToken() {
		return (AntlrToken) getStatement().getDescriptor().getNameToken();
	}

	public String getNodeName() {
		return getStatement().getElementName();
	}

	public IStatement getStatement() {
		return stmts.iterator().next();
	}

	public boolean isParserRule() {
		return Target.PARSER == target;
	}

	public boolean isLexerRule() {
		return Target.LEXER == target;
	}

	public boolean isFragment() {
		return Target.FRAGMENT == target;
	}

	public boolean isRange() {
		return Target.RANGE == target;
	}

	public boolean isSet() {
		return Target.SET == target;
	}

	public boolean isTerminal() {
		return Target.TERMINAL == target;
	}

	public void clear() {
		parents.clear();
		children.clear();
		stmts.clear();
	}

	@Override
	public String toString() {
		return getNodeName();
	}
}
