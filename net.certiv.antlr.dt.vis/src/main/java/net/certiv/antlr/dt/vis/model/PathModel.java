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
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.runtime.CoreException;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.model.Specialization;
import net.certiv.antlr.dt.core.model.SpecializedType;
import net.certiv.antlr.dt.core.model.Target;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.TerminalContext;
import net.certiv.antlr.dt.vis.model.providers.PathContentProvider;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.IField;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.core.model.IStatementVisitor;
import net.certiv.dsl.core.model.ModelType;
import net.certiv.dsl.core.model.builder.Descriptor;

public class PathModel implements IModel {

	// key = unique node key; value = node
	private final Map<String, PathNode> nodes = new LinkedHashMap<>();
	// key = node; value = out-bound connectors
	private final Map<PathNode, List<PathConnector>> connectors = new LinkedHashMap<>();

	private PathContentProvider provider;
	private AntlrCore core;

	public PathModel(PathContentProvider provider) {
		this.provider = provider;
		core = AntlrCore.getDefault();
	}

	/** Add the given statement. */
	public void add(IStatement stmt) {
		addCallees(stmt);
	}

	public void addCallers(IStatement stmt) {
		for (IStatement caller : findCallers(stmt)) {
			add(caller, stmt);
		}
		unhideRootPaths(nodes.get(stmt.getElementName()));
		provider.updateModel(this);
	}

	public void addCallees(IStatement stmt) {
		for (IStatement callee : findCallees(stmt)) {
			add(stmt, callee);
		}
		unhideRootPaths(nodes.get(stmt.getElementName()));
		provider.updateModel(this);
	}

	public void remove(IStatement stmt) {
		PathNode target = nodes.get(stmt.getElementName());
		if (target != null) {
			target.setHidden(true);
			for (PathNode source : target.getParents()) {
				if (!source.isHidden()) {
					if (orphan(source)) source.setHidden(true);
				}
			}
			for (PathNode child : target.getChildren()) {
				if (!child.isHidden()) {
					if (orphan(child)) child.setHidden(true);
				}
			}
		}
	}

	private boolean orphan(PathNode target) {
		for (PathNode parent : target.getParents()) {
			if (!parent.isHidden()) return false;
		}
		for (PathNode child : target.getChildren()) {
			if (!child.isHidden()) return false;
		}
		return true;
	}

	private void unhideRootPaths(PathNode target) {
		if (target != null) {
			target.setHidden(false);
			for (PathNode parent : target.getParents()) {
				if (parent.isHidden()) unhideRootPaths(parent);
			}
		}
	}

	private PathNode add(IStatement src, IStatement dst) {
		PathNode srcNode = addNode(src);
		PathNode dstNode = addNode(dst);
		addConnector(srcNode, dstNode);
		return dstNode != null ? dstNode : srcNode;
	}

	private PathNode addNode(IStatement stmt) {
		if (stmt == null || !stmt.hasData()) return null;

		Specialization data = (Specialization) stmt.getData();
		switch (data.getSpecializedType()) {
			case ParserRuleName:
				return addNode(stmt, Target.PARSER);

			case LexerRuleName:
				return addNode(stmt, Target.LEXER);

			case FragmentRuleName:
				return addNode(stmt, Target.FRAGMENT);

			case Range:
				return addNode(stmt, Target.RANGE);
			case Set:
				return addNode(stmt, Target.SET);
			case Terminal:
				TerminalContext ctx = (TerminalContext) data.getStmtNode();
				Target tgt = ctx.TOKEN_REF() != null ? Target.LEXER : Target.TERMINAL;
				return addNode(stmt, tgt);

			case RuleRef:
				int ch = stmt.getElementName().charAt(0);
				Target target = Character.isLowerCase(ch) ? Target.PARSER : Target.LEXER;
				return addNode(stmt, target);

			case ParserRule:
			case LexerRule:
				IField field = getNameField(stmt);
				if (field != null) return addNode(field);

			default:
				Log.info(this, "Not adding '%s'", data.getSpecializedType());
				return null;
		}
	}

	private IField getNameField(IStatement stmt) {
		for (IField field : stmt.getFields()) {
			Specialization data = (Specialization) field.getData();
			if (data != null) {
				switch (data.getSpecializedType()) {
					case ParserRuleName:
					case LexerRuleName:
					case FragmentRuleName:
						return field;
					default:
				}
			}
		}
		return null;
	}

	private PathNode addNode(IStatement stmt, Target kind) {
		String nodename = stmt.getElementName();
		PathNode node = nodes.get(nodename);
		if (node == null) {
			node = new PathNode(stmt, kind);
			nodes.put(nodename, node);
		} else {
			node.addStatement(stmt, kind);
			node.setHidden(false);
		}
		return node;

	}

	private void addConnector(PathNode parent, PathNode child) {
		if (parent != null && child != null) {
			PathConnector connector = new PathConnector(parent, child);
			addEndpoint(parent, connector);
			addEndpoint(child, connector);

			parent.addChild(child);
			child.addParent(parent);
		}
	}

	private void addEndpoint(PathNode node, PathConnector conn) {
		List<PathConnector> fanouts = connectors.get(node);
		if (fanouts == null) {
			fanouts = new ArrayList<>();
			connectors.put(node, fanouts);
		}
		fanouts.add(conn);
	}

	private Set<IStatement> findCallers(IStatement stmt) {
		Set<IStatement> callers = new LinkedHashSet<>();

		// all instances of this ruleref in the local unit
		collectCallers(callers, stmt.getCodeUnit(), stmt);

		// all instances of this ruleref in ancestor units
		Set<ICodeUnit> ancestrals = core.getDslModel().getRelatedAncestrals(stmt.getCodeUnit());
		for (ICodeUnit ancestral : ancestrals) {
			collectCallers(callers, ancestral, stmt);
		}

		return callers;
	}

	private void collectCallers(Set<IStatement> callers, ICodeUnit unit, IStatement stmt) {
		List<IStatement> refs = unit.findStatements(stmt);
		for (IStatement ref : refs) {
			IStatement caller = ref.getAncestor(ModelType.STATEMENT);
			IField name = caller.getNameField();
			if (name != null && name != stmt) {
				callers.add(caller);
			}
		}
	}

	private Set<IStatement> findCallees(IStatement stmt) {
		Descriptor desc = stmt.getDescriptor();
		ModelType ptype = desc.type;
		ModelType stype = desc.subtype;
		SpecializedType spec = (SpecializedType) desc.getSpecializedType();

		if (ptype == ModelType.FIELD && stype == ModelType.LITERAL) {
			switch (spec) {
				case FragmentRuleName:
				case LexerRuleName:
				case ParserRuleName:
					return ruleRefs(stmt.getParent());	// direct callees

				default:
			}

		} else if (ptype == ModelType.FIELD && stype == ModelType.TYPE) {
			switch (spec) {
				case RuleRef:
				case Terminal:
					return descendantRefs(stmt);	// decendant callees

				default:
			}
		}
		return Collections.emptySet();
	}

	private Set<IStatement> descendantRefs(IStatement stmt) {
		Set<IStatement> refs = new LinkedHashSet<>();

		// check current unit
		List<IStatement> locals = findRule(stmt.getCodeUnit(), stmt);
		int size = locals.size();
		if (size > 1) {
			Log.error(this, "Unexpected refs: %s", locals);

		} else if (size == 1) {
			refs.addAll(ruleRefs(locals.get(0)));

		} else if (size == 0) {	// check related descendants
			for (ICodeUnit descendant : core.getDslModel().getRelatedDescendants(stmt.getCodeUnit())) {
				List<IStatement> rules = findRule(descendant, stmt);
				for (IStatement rule : rules) {
					refs.addAll(ruleRefs(rule));
				}
			}
		}

		return refs;
	}

	private List<IStatement> findRule(ICodeUnit unit, IStatement stmt) {
		return unit.findStatements(ModelType.STATEMENT, ModelType.FUNC, stmt.getElementName());
	}

	private Set<IStatement> ruleRefs(IStatement stmt) {
		Set<IStatement> refs = new LinkedHashSet<>();

		try {
			stmt.decend(new IStatementVisitor() {

				@Override
				public boolean onEntry(IStatement child) throws CoreException {
					Descriptor desc = child.getDescriptor();
					SpecializedType spec = (SpecializedType) desc.getSpecializedType();
					if (spec != null) {
						switch (spec) {
							case RuleRef:
							case Terminal:
							case ParserAtomRef:
							case LexerAtomRef:
							case Range:
							case Set:
								refs.add(child);
							default:
						}
					}
					return child.hasStatements();
				}
			});
		} catch (CoreException e) {
			Log.error(this, "Error extracting rule refs from %s", stmt);
		}

		return refs;
	}

	public List<PathNode> getNodes() {
		return new ArrayList<>(nodes.values());
	}

	public PathNode get(IStatement stmt) {
		return nodes.get(stmt.getElementName());
	}

	public boolean contains(IStatement stmt) {
		return nodes.containsKey(stmt.getElementName());
	}

	@Override
	public void clear() {
		for (PathNode node : nodes.values()) {
			node.clear();
		}
		nodes.clear();
		connectors.clear();
	}

	@Override
	public void dispose() {
		clear();
	}

	@Override
	public String toString() {
		return "PathsModel [connectors=" + connectors.size() + "]";
	}
}
