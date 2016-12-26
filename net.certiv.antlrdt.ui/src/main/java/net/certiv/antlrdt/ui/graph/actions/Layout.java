package net.certiv.antlrdt.ui.graph.actions;

import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import net.certiv.antlrdt.ui.graph.layouts.BranchedLayoutAlgorithm;
import net.certiv.antlrdt.ui.graph.layouts.LinWalkersLayoutAlgorithm;

public enum Layout {
	HTREE("Horizontal Tree", new LinWalkersLayoutAlgorithm(K.NO_RESIZE)), //
	VTREE("Vertical Tree", new LinWalkersLayoutAlgorithm(K.NO_RESIZE, K.TOP_BOTTOM)), //
	HFLOW("Horizontal Flow", new BranchedLayoutAlgorithm(K.NO_RESIZE)),
	CALL("Call Flow", new CompositeLayoutAlgorithm(K.NO_RESIZE, //
			new LayoutAlgorithm[] { new TreeLayoutAlgorithm(K.NO_RESIZE), new HorizontalShift(K.NO_RESIZE) })), //

	SPRING("Spring", new SpringLayoutAlgorithm(K.NO_RESIZE)), //
	RADIAL("Radial", new RadialLayoutAlgorithm(K.NO_RESIZE)), //
	GRID("Grid", new CompositeLayoutAlgorithm(K.NO_RESIZE, //
			new LayoutAlgorithm[] { new GridLayoutAlgorithm(K.NO_RESIZE), new HorizontalShift(K.NO_RESIZE) }));

	private static final class K {
		public static final int TOP_BOTTOM = LinWalkersLayoutAlgorithm.ORIENT_TOP_BOTTOM;
		public static final int NO_RESIZE = LayoutStyles.NO_LAYOUT_NODE_RESIZING;
	}

	private String layoutName;
	private LayoutAlgorithm layoutAlgorithm;

	Layout(String name, LayoutAlgorithm algorithm) {
		layoutName = name;
		layoutAlgorithm = algorithm;
	}

	public String getName() {
		return layoutName;
	}

	public LayoutAlgorithm getAlgorithm() {
		return layoutAlgorithm;
	}

	public static Layout getEnum(String name) {
		if (name != null) {
			for (Layout value : values()) {
				if (value.getName().toLowerCase().equals(name.toLowerCase())) return value;
			}
		}
		return null;
	}
}
