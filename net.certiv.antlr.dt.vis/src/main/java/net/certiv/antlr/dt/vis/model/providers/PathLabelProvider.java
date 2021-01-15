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
package net.certiv.antlr.dt.vis.model.providers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.parser.AntlrToken;
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.figures.StdToolTip;
import net.certiv.antlr.dt.vis.model.PathConnector;
import net.certiv.antlr.dt.vis.model.PathNode;
import net.certiv.dsl.core.model.builder.Descriptor;
import net.certiv.dsl.core.preferences.PrefsManager;

public class PathLabelProvider implements INodeLabelProvider, IConnectionStyleProvider, IEntityStyleProvider {

	private final Color LIGHT_GRAY = new Color(Display.getDefault(), 220, 220, 220);
	private final Color DARK_BLUE = new Color(Display.getDefault(), 1, 70, 122);
	private final Color BLACK = new Color(Display.getDefault(), 0, 0, 0);

	private GraphViewer viewer;
	private PrefsManager store;
	private ImageManager imgr;

	private Image parserNodeImg;
	private Image lexerNodeImg;
	private Image fragmentNodeImg;
	private Image rangeNodeImg;
	private Image setNodeImg;
	private Image terminalNodeImg;
	private Image unknownNodeImg;

	private Object rootNode;
	private Object selected;

	private final Set<EntityConnectionData> related = new HashSet<>();
	private final Set<EntityConnectionData> dependent = new HashSet<>();

	public PathLabelProvider(GraphViewer viewer) {
		super();
		this.viewer = viewer;

		store = AntlrCore.getDefault().getPrefsManager();
		imgr = AntlrUI.getDefault().getImageManager();

		parserNodeImg = imgr.get(imgr.IMG_OBJ_RULE);
		lexerNodeImg = imgr.get(imgr.IMG_OBJ_LEXER);
		fragmentNodeImg = imgr.get(imgr.IMG_OBJ_FRAGMENT);
		rangeNodeImg = imgr.get(imgr.IMG_OBJ_RANGE);
		setNodeImg = imgr.get(imgr.IMG_OBJ_SET);
		terminalNodeImg = imgr.get(imgr.IMG_OBJ_TERMINAL);
		unknownNodeImg = imgr.get(imgr.IMG_OBJ_UNKNOWN);
	}

	@Override
	public Color getColor(Object relation) {
		if (interesting(relation)) return getHighlightColor(relation);
		return store.getColor(PrefsKey.PT_CONN_COLOR);
	}

	@Override
	public int getLineWidth(Object relation) {
		if (related.contains(relation)) store.getInt(PrefsKey.PT_CONN_WEIGHT);
		return -1;
	}

	@Override
	public Color getBackgroundColour(Object entity) {
		if (entity == rootNode) return store.getColor(PrefsKey.PT_NODE_BACKGROUND);
		if (entity == selected) return store.getColor(PrefsKey.PT_NODE_HIGHLIGHT);

		if (interesting(entity)) return store.getColor(PrefsKey.PT_NODE_ADJACENT);

		if (entity instanceof RuleNode) return store.getColor(PrefsKey.PT_NODE_RULE);
		if (entity instanceof ErrorNode) return store.getColor(PrefsKey.PT_NODE_ERROR);
		if (entity instanceof TerminalNode) return store.getColor(PrefsKey.PT_NODE_TERMINAL);

		return store.getColor(PrefsKey.PT_NODE_BACKGROUND);
	}

	@Override
	public Color getForegroundColour(Object entity) {
		return store.getColor(PrefsKey.PT_NODE_FOREGROUND);
	}

	@Override
	public Color getNodeHighlightColor(Object entity) {
		return store.getColor(PrefsKey.PT_NODE_HIGHLIGHT);
	}

	@Override
	public Color getHighlightColor(Object rel) {
		return store.getColor(PrefsKey.PT_CONN_HIGHLIGHT);
	}

	@Override
	public Color getBorderColor(Object entity) {
		if (selected != null) {
			if (entity == selected) return BLACK;
			if (dependent.contains(entity)) return BLACK;
			return LIGHT_GRAY;
		}
		return BLACK;
	}

	@Override
	public Color getBorderHighlightColor(Object entity) {
		return DARK_BLUE;
	}

	@Override
	public int getBorderWidth(Object entity) {
		if (entity instanceof RuleNode) return 1;
		return 0;
	}

	@Override
	public Image getImage(Object entity) {
		if (entity instanceof EntityConnectionData) return null;
		if (entity instanceof PathConnector) return null;
		if (entity instanceof PathNode) {
			PathNode element = (PathNode) entity;
			if (element.isParserRule()) return parserNodeImg;
			if (element.isLexerRule()) return lexerNodeImg;
			if (element.isFragment()) return fragmentNodeImg;
			if (element.isRange()) return rangeNodeImg;
			if (element.isSet()) return setNodeImg;
			if (element.isTerminal()) return terminalNodeImg;
		}
		return unknownNodeImg;
	}

	@Override
	public String getText(Object entity) {
		if (entity instanceof EntityConnectionData) return null;
		if (entity instanceof PathConnector) return null;
		if (entity instanceof PathNode) {
			return ((PathNode) entity).getNodeName();
		}
		return "Unknown";
	}

	@Override
	public IFigure getTooltip(Object entity) {
		if (entity == null || entity instanceof EntityConnectionData) return null;
		if (store.matches(PrefsKey.PT_TTT_MIN, PrefsKey.PT_TOOLTIP_TYPE)) {
			return null; // force default content
		}

		PathNode element = (PathNode) entity;
		Label fig = new StdToolTip();
		if (element.isParserRule()) fig.setIcon(parserNodeImg);
		if (element.isLexerRule()) fig.setIcon(lexerNodeImg);
		if (element.isFragment()) fig.setIcon(fragmentNodeImg);
		if (element.isRange()) fig.setIcon(rangeNodeImg);
		if (element.isSet()) fig.setIcon(setNodeImg);
		if (element.isTerminal()) fig.setIcon(terminalNodeImg);
		fig.setText(summary(element));

		return fig;
	}

	private String summary(PathNode node) {
		String name = node.getNodeName();
		if (node.isTerminal()) {
			AntlrToken token = node.getNameToken();
			String ttype = token.getTypeName();
			int line = token.getLine() + 1;
			int col = token.getCharPositionInLine();
			return String.format("%s (%s) @%s:%s", name, ttype, line, col);
		}

		Descriptor desc = node.getStatement().getDescriptor();
		int line = desc.begLine + 1;
		int col = desc.begOffsetName;
		return String.format("%s @%s:%s", name, line, col);
	}

	private boolean interesting(Object relation) {
		return Arrays.binarySearch(related.toArray(), relation) != -1;
	}

	protected void calculateInterestingDependencies() {
		// Object[] descriptions = AnalysisUtil.getDependencies(getSelected());
		// for (int i = 0; i < descriptions.length; i++) {
		// EntityConnectionData entityConnectionData = new
		// EntityConnectionData(getSelected(), descriptions[i]);
		// interestingRels.add(entityConnectionData);
		// interestingEntities.add(descriptions[i]);
		// }
	}

	@Override
	public void dispose() {
		dispose(BLACK);
		dispose(DARK_BLUE);
		dispose(LIGHT_GRAY);
	}

	private void dispose(Color color) {
		if (!color.isDisposed()) color.dispose();
	}

	@Override
	public void addListener(ILabelProviderListener listener) {}

	@Override
	public boolean isLabelProperty(Object element, String property) {
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener listener) {}

	@Override
	public boolean fisheyeNode(Object entity) {
		return false;
	}

	@Override
	public int getConnectionStyle(Object rel) {
		if (related.contains(rel)) {
			return ZestStyles.CONNECTIONS_DASH | ZestStyles.CONNECTIONS_DIRECTED;
		}
		return ZestStyles.CONNECTIONS_DIRECTED;
	}

	@Override
	public void setCurrentSelection(Object root, Object selection) {
		this.rootNode = root;
		this.selected = null;
		for (EntityConnectionData entityConnectionData : related) {
			viewer.unReveal(entityConnectionData);
		}

		this.selected = selection;
		related.clear();
		dependent.clear();
		if (selected != null) calculateInterestingDependencies();

		for (EntityConnectionData entityConnectionData : related) {
			viewer.reveal(entityConnectionData);
		}
		for (Object connection : viewer.getConnectionElements()) {
			viewer.update(connection, null);
		}
	}
}
