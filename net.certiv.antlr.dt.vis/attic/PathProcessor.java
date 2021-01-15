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
package net.certiv.antlr.dt.vis.path;

import java.util.List;

import org.eclipse.core.runtime.CoreException;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.model.Specialization;
import net.certiv.antlr.dt.vis.model.PathModel;
import net.certiv.antlr.dt.vis.model.PathNode;
import net.certiv.antlr.dt.vis.model.providers.PathContentProvider;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.core.model.IStatementVisitor;

public class PathProcessor {

	private AntlrCore core;
	private PathContentProvider provider;

	private final PathModel model;

	public PathProcessor(PathContentProvider provider) {
		this.provider = provider;
		core = AntlrCore.getDefault();
		model = new PathModel();
	}

	public void add(IStatement stmt) {
		add(stmt, null);
	}

	public void add(IStatement src, IStatement dst) {
		PathNode target = model.add(src, dst);
		if (target != null) {
			unhideRootPaths(target);
			provider.updateModel(model);
		}
	}

	public PathModel getModel() {
		return model;
	}

	/** Add the nodes connected as outbound path 'callees' of the given statement. */
	public void addCallees(IStatement stmt) {
		try {
			stmt.getParent().decend(new IStatementVisitor() {

				@Override
				public boolean onEntry(IStatement child) throws CoreException {
					if (stmt != child && child.hasData()) {
						Specialization data = (Specialization) child.getData();
						if (data != null) {
							switch (data.getSpecializedType()) {
								case ParserRuleName:
								case LexerRuleName:
								case FragmentRuleName:
								case RuleRef:
								case Terminal:
								case ParserAtomRef:
								case LexerAtomRef:
									add(stmt, child);
								default:
							}
						}
					}
					return child.hasStatements();
				}
			});
		} catch (CoreException e) {}
	}

	/** Add the nodes connected as inbound path 'callers' of the given statement. */
	public void addCallers(IStatement stmt) {
		if (stmt.isDeclaration()) {
			ICodeUnit unit = stmt.getCodeUnit();
			if (valid(unit)) {
				for (IStatement localref : stmt.getCodeUnit().findStatements(stmt)) {
					add(localref, stmt);
				}
				for (ICodeUnit ancestor : core.getDslModel().getRelatedAncestrals(unit)) {
					if (valid(ancestor)) {
						List<IStatement> stmts = ancestor.findStatements(stmt);
						for (IStatement parent : stmts) {
							add(parent, stmt);
						}
					}
				}
			}

		} else {
			if (stmt.getParent() != null) add(stmt.getParent(), stmt);
		}
	}

	public void remove(IStatement stmt) {
		PathNode target = model.get(stmt);
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

	private boolean valid(ICodeUnit unit) {
		if (unit == null) return false;
		// if (unit.hasErrors()) return false;
		return core.getLangManager().onSourceBuildPath(unit);
	}

	private void unhideRootPaths(PathNode target) {
		target.setHidden(false);
		for (PathNode parent : target.getParents()) {
			if (parent.isHidden()) unhideRootPaths(parent);
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

	public void dispose() {}
}
