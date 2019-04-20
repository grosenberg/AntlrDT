package net.certiv.antlrdt.vis.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.antlrdt.vis.layouts.Branched;
import net.certiv.antlrdt.vis.layouts.BranchedLayoutAlgorithm;
import net.certiv.antlrdt.vis.layouts.LinWalkersLayoutAlgorithm;
import net.certiv.dsl.core.util.Reflect;
import net.certiv.dsl.core.util.Strings;

public enum Layout {
	HTREE("Horizontal Tree", "IMG_LAYOUT_TREE_HORIZ", new LinWalkersLayoutAlgorithm(K.NO_RESIZE), Branched.LEFT_RIGHT),
	VTREE("Vertical Tree", "IMG_LAYOUT_TREE", new LinWalkersLayoutAlgorithm(K.NO_RESIZE, K.TOP_BOTTOM),
			Branched.TOP_BOTTOM),
	HFLOW("Horizontal Flow", "IMG_LAYOUT_CALL", new BranchedLayoutAlgorithm(K.NO_RESIZE), Branched.LEFT_RIGHT),

	CALL("Call Flow", "IMG_LAYOUT_GRAPHFLOW",
			new CompositeLayoutAlgorithm(K.NO_RESIZE,
					new LayoutAlgorithm[] { new TreeLayoutAlgorithm(K.NO_RESIZE), new HorizontalShift(K.NO_RESIZE) }),
			Branched.LEFT_RIGHT),

	SPRING("Spring", "IMG_LAYOUT_SPRING", new SpringLayoutAlgorithm(K.NO_RESIZE), Branched.MID_POINTS),
	RADIAL("Radial", "IMG_LAYOUT_RADIAL", new RadialLayoutAlgorithm(K.NO_RESIZE), Branched.MID_POINTS),
	GRID("Grid", "IMG_LAYOUT_GRID",
			new CompositeLayoutAlgorithm(K.NO_RESIZE,
					new LayoutAlgorithm[] { new GridLayoutAlgorithm(K.NO_RESIZE), new HorizontalShift(K.NO_RESIZE) }),
			Branched.MID_POINTS),

	;

	private static final class K {
		public static final int TOP_BOTTOM = LinWalkersLayoutAlgorithm.ORIENT_TOP_BOTTOM;
		public static final int NO_RESIZE = LayoutStyles.NO_LAYOUT_NODE_RESIZING;
	}

	private String layoutName;
	private String imageKey;
	private LayoutAlgorithm layoutAlgorithm;
	private Branched routerStyle;

	Layout(String name, String key, LayoutAlgorithm algorithm, Branched style) {
		layoutName = name;
		imageKey = key;
		layoutAlgorithm = algorithm;
		routerStyle = style;
	}

	public String getDisplayName() {
		return layoutName;
	}

	public String getImageUrlname() {
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		Object value = Reflect.get(imgMgr, imageKey, true);
		if (value == null) return Strings.EMPTY_STRING;
		return imgMgr.getUrl((String) value).toExternalForm();
	}

	public ImageDescriptor getImageDescriptor() {
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		Object key = Reflect.get(imgMgr, imageKey, true);
		if (key == null) return null;
		return imgMgr.getDescriptor((String) key);
	}

	public Branched getRouterStyle() {
		return routerStyle;
	}

	public LayoutAlgorithm getAlgorithm() {
		return layoutAlgorithm;
	}

	public static Layout getEnum(String name) {
		if (name != null) {
			for (Layout value : values()) {
				if (value.getDisplayName().toLowerCase().equals(name.toLowerCase())) return value;
			}
		}
		return null;
	}
}
