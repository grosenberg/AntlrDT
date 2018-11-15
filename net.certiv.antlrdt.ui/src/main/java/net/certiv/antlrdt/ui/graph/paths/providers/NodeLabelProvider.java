/*******************************************************************************
 * Copyright 2005-2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.paths.providers;

import java.util.Arrays;
import java.util.HashSet;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.parser.PathsNode;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrDTImages;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.graph.EnhTipHelper;
import net.certiv.antlrdt.ui.graph.IDslGraphViewer;
import net.certiv.antlrdt.ui.graph.cst.providers.AbstractNodeLabelProvider;
import net.certiv.antlrdt.ui.graph.figures.InfoLabel;
import net.certiv.antlrdt.ui.graph.figures.StdToolTip;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.core.util.Strings;

public class NodeLabelProvider extends AbstractNodeLabelProvider {

	private Image RuleNode;
	private Image TerminalNode;
	private Image ErrorNode;

	public NodeLabelProvider(GraphViewer viewer) {
		super(viewer);
		this.RuleNode = getImageProvider().DESC_OBJ_RULE.createImage();
		this.TerminalNode = getImageProvider().DESC_OBJ_TERMINAL.createImage();
		this.ErrorNode = getImageProvider().ERROR_NODE.createImage();
	}

	private AntlrDTImages getImageProvider() {
		return (AntlrDTImages) AntlrDTUI.getDefault().getImageProvider();
	}

	@Override
	public Color getColor(Object rel) {
		if (Arrays.binarySearch(getInterestingRelationships(), rel) != -1) {
			return getHighlightColor(rel);
		}
		return getPrefs().getColor(PrefsKey.PT_CONN_COLOR);
	}

	@Override
	public Color getHighlightColor(Object rel) {
		return getPrefs().getColor(PrefsKey.PT_CONN_HIGHLIGHT);
	}

	@Override
	public Color getBackgroundColour(Object entity) {
		if (entity == this.rootNode) return LIGHT_GREEN;
		if (entity == this.selected || this.pinnedNode == entity) {
			// NOTE: nodes handle their own selection highlighting; highlight color is set
			// on node creation and GraphViewer#update; so, do not manage highlights here.
			// NOTE: doing "return getNodeHighlightColor(entity);" will produce unexpected
			// highlighting
			return getPrefs().getColor(PrefsKey.PT_NODE_BACKGROUND);
		} else if (Arrays.binarySearch(getInterestingRelationships(), entity) != -1) {
			return getPrefs().getColor(PrefsKey.PT_NODE_ADJACENT);
		}

		PathsNode node = (PathsNode) entity;
		if (!node.isTerminal()) {
			if (node.isParserRule()) {
				return getPrefs().getColor(PrefsKey.PT_NODE_RULE);
			} else if (node.isLexerRule()) {
				return getPrefs().getColor(PrefsKey.PT_NODE_TERMINAL);
			} else if (node.isFragment()) {
				return getPrefs().getColor(PrefsKey.PT_NODE_ERROR);
			}
		}
		return getPrefs().getColor(PrefsKey.PT_NODE_BACKGROUND);
	}

	@Override
	public Color getForegroundColour(Object entity) {
		return getPrefs().getColor(PrefsKey.PT_NODE_FOREGROUND);
	}

	@Override
	public Color getNodeHighlightColor(Object entity) {
		return getPrefs().getColor(PrefsKey.PT_NODE_HIGHLIGHT);
	}

	@Override
	public Image getImage(Object element) {
		if (element instanceof PathsNode) return null;
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof PathsNode) {
			PathsNode node = (PathsNode) element;
			return node.getRuleName();
		}
		return "";
	}

	@Override
	public IFigure getTooltip(Object entity) {
		if (entity == null || entity instanceof EntityConnectionData) return null;
		if (getPrefs().matches(PrefsKey.PT_TOOLTIP_TYPE, PrefsKey.PT_TTT_MIN)) {
			return null; // force default content
		}

		Label fig = new StdToolTip();
		PathsNode element = (PathsNode) entity;
		if (getPrefs().matches(PrefsKey.PT_TOOLTIP_TYPE, PrefsKey.PT_TTT_ENH)) {
			EnhTipHelper helper = ((IDslGraphViewer) viewer).getTipHelper();
			fig = new InfoLabel(helper, getInfoImage(element), getText(element), labelDetail(element),
					contentDetail(element));
		} else {
			fig.setText(summary(element));
		}
		return fig;
	}

	private Image getInfoImage(PathsNode entity) {
		PathsNode node = (PathsNode) entity;
		if (!node.isTerminal()) {
			if (node.isParserRule()) {
				return RuleNode;
			} else if (node.isLexerRule()) {
				return TerminalNode;
			} else if (node.isFragment()) {
				return ErrorNode;
			}
		}
		return TerminalNode;
	}

	private String labelDetail(PathsNode entity) {
		StringBuilder sb = new StringBuilder();
		sb.append("Token type" + Strings.EOL);
		sb.append("Token (l:c)" + Strings.EOL);
		sb.append("Token Length" + Strings.EOL);
		return sb.toString().trim();
	}

	private String contentDetail(PathsNode entity) {
		PathsNode node = (PathsNode) entity;
		StringBuilder sb = new StringBuilder();
		int ttype = node.getRef().getType();
		Lexer lexer = (Lexer) node.getRef().getTokenSource();
		String name = lexer.getVocabulary().getDisplayName(ttype);
		sb.append(name + " (" + ttype + ")" + Strings.EOL);
		sb.append(at(node.getRef()) + Strings.EOL);
		sb.append(len(node.getRef()) + Strings.EOL);
		return sb.toString().trim();
	}

	private String at(Token start) {
		return start.getLine() + ":" + start.getCharPositionInLine();
	}

	private String len(Token ref) {
		int len = ref.getStopIndex() - ref.getStartIndex() + 1;
		return String.valueOf(len);
	}

	private String summary(PathsNode element) {
		return null;
	}

	private IDslPrefsManager getPrefs() {
		return AntlrDTCore.getDefault().getPrefsManager();
	}

	@Override
	protected void calculateInterestingDependencies(HashSet<EntityConnectionData> interestingRels,
			HashSet<EntityConnectionData> interestingEntities) {
		if (getSelected() != null) {
			// TODO: compute relations
			// Object[] descriptions = AnalysisUtil.getDependencies(this.getSelected());
			// for (int i = 0; i < descriptions.length; i++) {
			// EntityConnectionData entityConnectionData = new
			// EntityConnectionData(this.getSelected(),
			// descriptions[i]);
			// interestingRels.add(entityConnectionData);
			// interestingEntities.add(descriptions[i]);
			// }
		}
	}

	@Override
	public void dispose() {
		super.dispose();
		this.RuleNode.dispose();
		this.TerminalNode.dispose();
		this.ErrorNode.dispose();
	}
}
