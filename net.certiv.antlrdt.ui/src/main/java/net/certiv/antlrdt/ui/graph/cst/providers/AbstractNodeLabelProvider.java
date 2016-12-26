package net.certiv.antlrdt.ui.graph.cst.providers;

import java.util.HashSet;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

import net.certiv.antlrdt.ui.AntlrDTImages;
import net.certiv.antlrdt.ui.AntlrDTUI;

/** Colors for labels and connections. */
public abstract class AbstractNodeLabelProvider
		implements INodeLabelProvider, IConnectionStyleProvider, IEntityStyleProvider {

	public Color LIGHT_BLUE = new Color(Display.getDefault(), 216, 228, 248);
	public Color DARK_BLUE = new Color(Display.getDefault(), 1, 70, 122);
	public Color GREY_BLUE = new Color(Display.getDefault(), 139, 150, 171);
	public Color LIGHT_BLUE_CYAN = new Color(Display.getDefault(), 213, 243, 255);
	public Color LIGHT_YELLOW = new Color(Display.getDefault(), 255, 255, 206);
	public Color GRAY = new Color(Display.getDefault(), 128, 128, 128);
	public Color LIGHT_GRAY = new Color(Display.getDefault(), 220, 220, 220);
	public Color BLACK = new Color(Display.getDefault(), 0, 0, 0);
	public Color RED = new Color(Display.getDefault(), 255, 0, 0);
	public Color DARK_RED = new Color(Display.getDefault(), 127, 0, 0);
	public Color ORANGE = new Color(Display.getDefault(), 255, 196, 0);
	public Color YELLOW = new Color(Display.getDefault(), 255, 255, 0);
	public Color GREEN = new Color(Display.getDefault(), 0, 255, 0);
	public Color DARK_GREEN = new Color(Display.getDefault(), 0, 127, 0);
	public Color LIGHT_GREEN = new Color(Display.getDefault(), 96, 255, 96);
	public Color CYAN = new Color(Display.getDefault(), 0, 255, 255);
	public Color BLUE = new Color(Display.getDefault(), 0, 0, 255);
	public Color WHITE = new Color(Display.getDefault(), 255, 255, 255);
	public Color EDGE_WEIGHT_0 = new Color(Display.getDefault(), 192, 192, 255);
	public Color EDGE_WEIGHT_01 = new Color(Display.getDefault(), 64, 128, 225);
	public Color EDGE_WEIGHT_02 = new Color(Display.getDefault(), 32, 32, 128);
	public Color EDGE_WEIGHT_03 = new Color(Display.getDefault(), 0, 0, 128);
	public Color EDGE_DEFAULT = new Color(Display.getDefault(), 64, 64, 128);
	public Color EDGE_HIGHLIGHT = new Color(Display.getDefault(), 192, 32, 32);
	public Color DISABLED = new Color(Display.getDefault(), 230, 240, 250);

	protected GraphViewer viewer;
	protected Object rootNode = null;
	protected Object selected = null;
	protected Object pinnedNode = null;

	private Image UnknownNode;

	private HashSet<EntityConnectionData> interestingRelationships = new HashSet<EntityConnectionData>();
	private HashSet<EntityConnectionData> interestingDependencies = new HashSet<EntityConnectionData>();

	/** Create a new Abstract Visualization Label Provider */
	public AbstractNodeLabelProvider(GraphViewer viewer) {
		this.viewer = viewer;
		this.UnknownNode = getImageProvider().UNKNOWN_NODE.createImage();
	}

	private AntlrDTImages getImageProvider() {
		return (AntlrDTImages) AntlrDTUI.getDefault().getImageProvider();
	}

	@Override
	public Color getColor(Object rel) {
		if (interestingRelationships.contains(rel)) return DARK_RED;
		return LIGHT_GRAY;
	}

	@Override
	public int getConnectionStyle(Object rel) {
		if (interestingRelationships.contains(rel)) {
			return ZestStyles.CONNECTIONS_DASH | ZestStyles.CONNECTIONS_DIRECTED;
		}
		return ZestStyles.CONNECTIONS_DIRECTED;
	}

	@Override
	public Color getHighlightColor(Object rel) {
		return DARK_RED;
	}

	@Override
	public int getLineWidth(Object rel) {
		if (interestingRelationships.contains(rel)) return 1;
		return 1;
	}

	@Override
	public IFigure getTooltip(Object entity) {
		return null;
	}

	/** If the return value is null, the color is set to Graph.HIGHLIGHT_COLOR */
	@Override
	public Color getNodeHighlightColor(Object entity) {
		return null;
	}

	@Override
	public Color getBorderColor(Object entity) {
		if (this.selected != null || this.pinnedNode != null) {
			if (entity == this.selected || entity == this.pinnedNode) {
				return BLACK;
			} else if (interestingDependencies.contains(entity)) {
				return BLACK;
			} else {
				return LIGHT_GRAY;
			}
		}
		return BLACK;
	}

	@Override
	public Color getBorderHighlightColor(Object entity) {
		return null;
	}

	@Override
	public int getBorderWidth(Object entity) {
		return 0;
	}

	@Override
	public Color getBackgroundColour(Object entity) {
		if (entity == this.rootNode) return LIGHT_GREEN;
		if (entity == this.selected || this.pinnedNode == entity) {
			return viewer.getGraphControl().DEFAULT_NODE_COLOR;
		} else if (interestingDependencies.contains(entity)) {
			return viewer.getGraphControl().HIGHLIGHT_ADJACENT_COLOR;
		} else {
			return LIGHT_GRAY;
		}
	}

	@Override
	public Color getForegroundColour(Object entity) {
		if (this.selected != null || this.pinnedNode != null) {
			if (entity == this.selected || this.pinnedNode == entity) {
				return BLACK;
			} else if (interestingDependencies.contains(entity)) {
				return BLACK;
			} else {
				return GRAY;
			}
		}
		return BLACK;
	}

	@Override
	public boolean fisheyeNode(Object entity) {
		return false;
	}

	@Override
	public Image getImage(Object element) {
		if (element != null) {
			if (element.getClass() == EntityConnectionData.class) return null;
		}
		return UnknownNode;
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
	public void setPinnedNode(Object pinnedNode) {
		this.pinnedNode = pinnedNode;
	}

	protected Object getSelected() {
		if (pinnedNode != null) return pinnedNode;
		return selected;
	}

	/** Sets the current selection */
	@Override
	public void setCurrentSelection(Object root, Object selection) {
		this.rootNode = root;
		this.selected = null;
		for (EntityConnectionData entityConnectionData : interestingRelationships) {
			viewer.unReveal(entityConnectionData);
		}

		this.selected = selection;
		interestingRelationships.clear();
		interestingDependencies.clear();
		if (this.selected != null || this.pinnedNode != null) {
			calculateInterestingDependencies(interestingRelationships, interestingDependencies);
		}
		for (EntityConnectionData entityConnectionData : interestingRelationships) {
			viewer.reveal(entityConnectionData);
		}
		for (Object connection : viewer.getConnectionElements()) {
			viewer.update(connection, null);
		}
	}

	/** Calculate all the interesting dependencies. */
	protected abstract void calculateInterestingDependencies(HashSet<EntityConnectionData> relations,
			HashSet<EntityConnectionData> entities);

	@Override
	public Object[] getInterestingRelationships() {
		return interestingRelationships.toArray();
	}

	@Override
	public void dispose() {
		UnknownNode.dispose();
	}
}
