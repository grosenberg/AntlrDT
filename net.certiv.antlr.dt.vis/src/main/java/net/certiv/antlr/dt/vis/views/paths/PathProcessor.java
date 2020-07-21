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
package net.certiv.antlr.dt.vis.views.paths;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.builder.AntlrBuilder;
import net.certiv.antlr.dt.core.parser.gen.PathVisitor;
import net.certiv.antlr.dt.vis.model.PathModel;
import net.certiv.antlr.dt.vis.model.PathNode;
import net.certiv.antlr.dt.vis.model.providers.PathContentProvider;
import net.certiv.dsl.core.lang.LanguageManager;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.model.DslModel;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.parser.DslParseRecord;

public class PathProcessor {

	private AntlrCore core;
	private ICodeUnit unit;
	private PathContentProvider provider;

	private final PathModel model = new PathModel();

	public PathProcessor(ICodeUnit unit, PathContentProvider provider) {
		this.unit = unit;
		this.provider = provider;
		core = AntlrCore.getDefault();
	}

	public void build(String rulename) {

		try {
			for (ICodeUnit unit : getRelated(unit)) {
				DslParseRecord rec = unit.getDefaultParseRecord();
				if (rec != null && rec.hasTree()) {
					PathVisitor walker = new PathVisitor(rec.getTree());
					walker.setHelper(model);
					walker.findAll();
				}
			}
		} catch (Exception e) {
			Log.error(this, "Error in paths walk: " + e.getMessage(), e);
		}

		PathNode target = model.get(rulename);
		if (target != null) unhideRootPaths(target);

		provider.updateModel(model);
	}

	public Object getModel() {
		return model;
	}

	// -------------------------------------------

	private List<ICodeUnit> getRelated(ICodeUnit unit) {
		Set<ICodeUnit> units = new HashSet<>();
		units.add(unit);

		DslModel model = core.getDslModel();
		for (ICodeUnit related : model.getRelated(unit)) {
			boolean ok = valid(related);
			if (ok) units.add(related);
		}
		List<ICodeUnit> results = new ArrayList<>(units);
		results.sort(AntlrBuilder.NameComp);
		return results;
	}

	private boolean valid(ICodeUnit unit) {
		if (unit == null) return false;
		if (unit.hasErrors()) return false;

		LanguageManager mgr = core.getLangManager();
		if (!mgr.onSourceBuildPath(unit)) return false;

		return true;
	}

	private void unhideRootPaths(PathNode target) {
		target.setHidden(false);
		for (PathNode parent : target.getParents()) {
			if (parent.isHidden()) unhideRootPaths(parent);
		}
	}

	/**
	 * Unhide the nodes connected as outbound path 'callees' of the given rulename
	 * identified node.
	 */
	public void addCalleePaths(String rulename) {
		PathNode target = model.get(rulename);
		for (PathNode child : target.getChildren()) {
			child.setHidden(false);
		}
	}

	/**
	 * Unhide the nodes connected as inbound path 'callers' of the given rulename
	 * identified node.
	 */
	public void addCallerPaths(String rulename) {
		PathNode target = model.get(rulename);
		for (PathNode parent : target.getParents()) {
			parent.setHidden(false);
		}
	}

	public void removeNode(String rulename) {
		PathNode target = model.get(rulename);
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
