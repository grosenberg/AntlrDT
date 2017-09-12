/*******************************************************************************
 * Copyright 2005-2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph.cst.providers;

import java.util.Arrays;
import java.util.HashSet;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrDTImages;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.graph.EnhTipHelper;
import net.certiv.antlrdt.ui.graph.IDslGraphViewer;
import net.certiv.antlrdt.ui.graph.figures.InfoLabel;
import net.certiv.antlrdt.ui.graph.figures.StdToolTip;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.core.util.Strings;

public class NodeLabelProvider extends AbstractNodeLabelProvider {

	private Image RuleNode;
	private Image TerminalNode;
	private Image ErrorNode;

	private String[] ruleNames;
	private String[] tokenNames;

	public NodeLabelProvider(GraphViewer viewer) {
		super(viewer);
		this.RuleNode = getImageProvider().RULE_NODE.createImage();
		this.TerminalNode = getImageProvider().TERMINAL_NODE.createImage();
		this.ErrorNode = getImageProvider().ERROR_NODE.createImage();
	}

	private AntlrDTImages getImageProvider() {
		return (AntlrDTImages) AntlrDTUI.getDefault().getImageProvider();
	}

	public void setNames(String[] rules, String[] tokens) {
		this.ruleNames = rules;
		this.tokenNames = tokens;
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
		} else if (entity instanceof ErrorNode) {
			return getPrefs().getColor(PrefsKey.PT_NODE_ERROR);
		} else if (entity instanceof TerminalNode) {
			return getPrefs().getColor(PrefsKey.PT_NODE_TERMINAL);
		} else if (entity instanceof RuleNode) {
			return getPrefs().getColor(PrefsKey.PT_NODE_RULE);
		} else {
			return getPrefs().getColor(PrefsKey.PT_NODE_BACKGROUND);
		}
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
		if (element instanceof ParseTree) return null;
		return super.getImage(element);
	}

	@Override
	public String getText(Object element) {
		if (element instanceof TerminalNode) {
			String value = ((TerminalNode) element).getText();
			return Strings.escape(value);
		}
		if (element instanceof RuleNode) {
			RuleContext node = (RuleContext) element;
			int idx = node.getRuleIndex();
			idx = ruleNames != null && idx >= 0 && idx < ruleNames.length ? idx : -1;
			return idx > -1 ? ruleNames[idx] : "<unknown>";
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
		ParseTree element = (ParseTree) entity;
		if (getPrefs().matches(PrefsKey.PT_TOOLTIP_TYPE, PrefsKey.PT_TTT_ENH)) {
			EnhTipHelper helper = ((IDslGraphViewer) viewer).getTipHelper();
			fig = new InfoLabel(helper, getInfoImage(element), getText(element), labelDetail(element),
					contentDetail(element));
		} else {
			fig.setText(summary(element));
		}
		return fig;
	}

	private Image getInfoImage(ParseTree element) {
		if (element instanceof ErrorNode) {
			return ErrorNode;
		} else if (element instanceof TerminalNode) {
			return TerminalNode;
		} else if (element instanceof RuleNode) {
			return RuleNode;
		}
		return null;
	}

	private String labelDetail(ParseTree element) {
		StringBuilder sb = new StringBuilder();
		if (element instanceof TerminalNode) {
			sb.append("Token type" + Strings.EOL);
			sb.append("Token (l:c)" + Strings.EOL);
			sb.append("Token Length" + Strings.EOL);

		} else if (element instanceof RuleNode) {
			sb.append("Rule Index" + Strings.EOL);
			sb.append("Start token (l:c)" + Strings.EOL);
			sb.append("Stop token (l:c)" + Strings.EOL);
			sb.append("Rule Length" + Strings.EOL);
		}
		return sb.toString().trim();
	}

	private String contentDetail(ParseTree element) {
		StringBuilder sb = new StringBuilder();
		if (element instanceof TerminalNode) {
			TerminalNode node = (TerminalNode) element;
			int ttype = node.getSymbol().getType();
			String name;
			if (ttype == Lexer.EOF) {
				name = "EOF";
			} else {
				ttype = tokenNames != null && ttype > 0 && ttype < tokenNames.length ? ttype : -1;
				name = ttype > -1 ? tokenNames[ttype] : "<Unknown>";
			}
			sb.append(name + " (" + ttype + ")" + Strings.EOL);
			sb.append(at(node.getSymbol()) + Strings.EOL);
			sb.append(node.getSourceInterval().length() + Strings.EOL);

		} else if (element instanceof RuleNode) {
			ParserRuleContext node = (ParserRuleContext) element;
			sb.append(node.getRuleIndex() + Strings.EOL);
			sb.append(at(node.getStart()) + Strings.EOL);
			sb.append(at(node.getStop()) + Strings.EOL);
			sb.append(node.getSourceInterval().length() + Strings.EOL);
		}
		return sb.toString().trim();
	}

	private String at(Token start) {
		return start.getLine() + ":" + start.getCharPositionInLine();
	}

	private String summary(ParseTree element) {
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
