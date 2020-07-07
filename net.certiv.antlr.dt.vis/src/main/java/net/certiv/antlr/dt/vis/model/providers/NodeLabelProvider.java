package net.certiv.antlr.dt.vis.model.providers;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.antlr.v4.runtime.Token;
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
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.figures.StdToolTip;
import net.certiv.antlr.dt.vis.model.TreeNode;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.core.util.Strings;

public class NodeLabelProvider implements INodeLabelProvider, IConnectionStyleProvider, IEntityStyleProvider {

	private final Color LIGHT_GRAY = new Color(Display.getDefault(), 220, 220, 220);
	private final Color DARK_BLUE = new Color(Display.getDefault(), 1, 70, 122);
	private final Color BLACK = new Color(Display.getDefault(), 0, 0, 0);

	private GraphViewer viewer;
	private PrefsManager store;
	private ImageManager imgr;

	private Image ruleNode;
	private Image terminalNode;
	private Image errorNode;
	private Image unknownNode;

	private Object rootNode;
	private Object selected;

	private final Set<EntityConnectionData> related = new HashSet<>();
	private final Set<EntityConnectionData> dependent = new HashSet<>();

	public NodeLabelProvider(GraphViewer viewer) {
		super();
		this.viewer = viewer;

		store = AntlrCore.getDefault().getPrefsManager();
		imgr = AntlrUI.getDefault().getImageManager();

		ruleNode = imgr.get(imgr.IMG_OBJ_RULE);
		terminalNode = imgr.get(imgr.IMG_OBJ_TERMINAL);
		errorNode = imgr.get(imgr.ERROR_NODE);
		unknownNode = imgr.get(imgr.UNKNOWN_NODE);
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
	public Image getImage(Object element) {
		if (element instanceof EntityConnectionData) return null;
		if (element instanceof TreeNode) {
			TreeNode node = (TreeNode) element;
			if (node.isRuleNode()) return ruleNode;
			if (node.isErrorNode()) return errorNode;
			if (node.isTerminalNode()) return terminalNode;
		}
		return unknownNode;
	}

	@Override
	public String getText(Object element) {
		if (element instanceof TreeNode) {
			TreeNode node = (TreeNode) element;
			String name = node.getName();
			if (node.isTerminalNode() || node.isErrorNode()) {
				String text = node.getSymbol().getText();
				text = Strings.encode(Strings.ellipsize(text, 16));
				return String.format("%s « %s »", name, text);
			}
			return name;
		}
		return null;
	}

	@Override
	public IFigure getTooltip(Object entity) {
		if (entity == null || entity instanceof EntityConnectionData) return null;
		if (store.matches(PrefsKey.PT_TTT_MIN, PrefsKey.PT_TOOLTIP_TYPE)) {
			return null; // force default content
		}

		TreeNode node = (TreeNode) entity;
		Label fig = new StdToolTip();
		if (node.isRuleNode()) {
			fig.setIcon(ruleNode);
		} else if (node.isErrorNode()) {
			fig.setIcon(errorNode);
		} else if (node.isTerminalNode()) {
			fig.setIcon(terminalNode);
		}
		fig.setText(summary(node));

		return fig;
	}

	private String summary(TreeNode node) {
		String name = node.getName();
		Token token = node.getSymbol();
		int line = token.getLine() + 1;
		int col = token.getCharPositionInLine();

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
