package net.certiv.antlrdt.ui.graph.cst.providers;

import java.util.HashSet;

import org.eclipse.draw2d.IFigure;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.zest.core.viewers.EntityConnectionData;
import org.eclipse.zest.core.viewers.GraphViewer;
import org.eclipse.zest.core.viewers.IConnectionStyleProvider;
import org.eclipse.zest.core.viewers.IEntityStyleProvider;
import org.eclipse.zest.core.widgets.ZestStyles;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.AntlrImageManager;
import net.certiv.dsl.core.color.DslColorManager;

/** Colors for labels and connections. */
public abstract class AbstractNodeLabelProvider
		implements INodeLabelProvider, IConnectionStyleProvider, IEntityStyleProvider {

	protected GraphViewer viewer;
	protected DslColorManager colorMgr;

	protected Object rootNode = null;
	protected Object selected = null;
	protected Object pinnedNode = null;
	private Image UnknownNode;

	private final HashSet<EntityConnectionData> interestingRelationships = new HashSet<>();
	private final HashSet<EntityConnectionData> interestingDependencies = new HashSet<>();

	/** Create a new Abstract Visualization Label Provider */
	public AbstractNodeLabelProvider(GraphViewer viewer) {
		this.viewer = viewer;
		AntlrImageManager imgMgr = AntlrDTUI.getDefault().getImageManager();
		this.UnknownNode = imgMgr.get(imgMgr.UNKNOWN_NODE);

		colorMgr = AntlrDTCore.getDefault().getColorManager();
		for (NC nc : NC.values()) {
			colorMgr.bindColor(nc.name(), nc.rgb());
		}
	}

	@Override
	public Color getColor(Object rel) {
		if (interestingRelationships.contains(rel)) return colorMgr.getColor(NC.CST_DARK_RED.name());
		return colorMgr.getColor(NC.CST_LIGHT_GRAY.name());
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
		return colorMgr.getColor(NC.CST_DARK_RED.name());
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
				return colorMgr.getColor(NC.CST_BLACK.name());
			} else if (interestingDependencies.contains(entity)) {
				return colorMgr.getColor(NC.CST_BLACK.name());
			} else {
				return colorMgr.getColor(NC.CST_LIGHT_GRAY.name());
			}
		}
		return colorMgr.getColor(NC.CST_BLACK.name());
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
		if (entity == this.rootNode) return colorMgr.getColor(NC.CST_LIGHT_GREEN.name());
		if (entity == this.selected || this.pinnedNode == entity) {
			return viewer.getGraphControl().DEFAULT_NODE_COLOR;
		} else if (interestingDependencies.contains(entity)) {
			return viewer.getGraphControl().HIGHLIGHT_ADJACENT_COLOR;
		} else {
			return colorMgr.getColor(NC.CST_LIGHT_GRAY.name());
		}
	}

	@Override
	public Color getForegroundColour(Object entity) {
		if (this.selected != null || this.pinnedNode != null) {
			if (entity == this.selected || this.pinnedNode == entity) {
				return colorMgr.getColor(NC.CST_BLACK.name());
			} else if (interestingDependencies.contains(entity)) {
				return colorMgr.getColor(NC.CST_BLACK.name());
			} else {
				return colorMgr.getColor(NC.CST_GRAY.name());
			}
		}
		return colorMgr.getColor(NC.CST_BLACK.name());
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
