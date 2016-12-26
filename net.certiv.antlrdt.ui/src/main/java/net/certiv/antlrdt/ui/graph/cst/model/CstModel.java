/*******************************************************************************
 * Copyright 2005-2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.cst.model;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Set;

import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.antlrdt.ui.graph.IGraphModel;

/** Parse Tree connection graph model. */
public class CstModel implements IGraphModel {

	private LinkedHashMap<ParseTree, ArrayList<CstConnector>> connectors;

	public CstModel() {
		clear();
	}

	public void clear() {
		dispose();
		connectors = new LinkedHashMap<>();
	}

	public void add(ParseTree parent, ParseTree child) {
		if (parent != null) {
			CstConnector connector = new CstConnector(parent, child);
			addEndpoint(parent, connector);
			addEndpoint(child, connector);
		}
	}

	private void addEndpoint(ParseTree node, CstConnector conn) {
		ArrayList<CstConnector> list = connectors.get(node);
		if (list == null) {
			list = new ArrayList<CstConnector>();
			connectors.put(node, list);
		}
		list.add(conn);
	}

	public ArrayList<ParseTree> findCallers(ParseTree target) {
		ArrayList<ParseTree> sources = new ArrayList<>();
		ArrayList<CstConnector> conns = connectors.get(target);
		if (conns == null) return sources;
		for (CstConnector conn : conns) {
			if (conn.getTarget().equals(target)) {
				sources.add(conn.getSource());
			}
		}
		return sources;
	}

	public ArrayList<ParseTree> findCallees(ParseTree source) {
		ArrayList<ParseTree> targets = new ArrayList<>();
		ArrayList<CstConnector> conns = connectors.get(source);
		if (conns == null) return targets;
		for (CstConnector conn : conns) {
			if (conn.getSource() != null && conn.getSource().equals(source)) {
				targets.add(conn.getTarget());
			}
		}
		return targets;
	}

	public ArrayList<ParseTree> getNodeList() {
		Set<ParseTree> nodes = connectors.keySet();
		return new ArrayList<ParseTree>(nodes);
	}

	public void dispose() {
		if (connectors != null) {
			connectors.clear();
			connectors = null;
		}
	}

	@Override
	public String toString() {
		return "CstModel [connectors=" + connectors.size() + "]";
	}
}
