package net.certiv.antlrdt.graph.actions;

import org.eclipse.gef.layout.ILayoutAlgorithm;
import org.eclipse.gef.layout.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.gef.layout.algorithms.GridLayoutAlgorithm;
import org.eclipse.gef.layout.algorithms.HorizontalShiftAlgorithm;
import org.eclipse.gef.layout.algorithms.RadialLayoutAlgorithm;
import org.eclipse.gef.layout.algorithms.SpringLayoutAlgorithm;
import org.eclipse.jface.resource.ImageDescriptor;

import net.certiv.antlrdt.graph.layouts.LinWalkersLayoutAlgorithm;
import net.certiv.antlrdt.graph.layouts.LinWalkersLayoutAlgorithm.ORIENT;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.util.Reflect;
import net.certiv.dsl.core.util.Strings;

public enum Layout {
	// HTREE("Horizontal Tree", "IMG_LAYOUT_TREE_HORIZ", new
	// LinWalkersLayoutAlgorithm(ORIENT.LEFT_RIGHT)),
	// VTREE("Vertical Tree", "IMG_LAYOUT_TREE", new LinWalkersLayoutAlgorithm(ORIENT.TOP_BOTTOM)),

	// HFLOW("Horizontal Flow", "IMG_LAYOUT_CALL", new BranchedLayoutAlgorithm(0)),
	// CALL("Call Flow", "IMG_LAYOUT_GRAPHFLOW",
	// new CompositeLayoutAlgorithm(
	// new ILayoutAlgorithm[] { new TreeLayoutAlgorithm(), new HorizontalShiftAlgorithm() })),

	HTREE("Hoizontal Tree", "IMG_LAYOUT_TREE_HORIZ", new LinWalkersLayoutAlgorithm(ORIENT.LEFT_RIGHT)),
	VTREE("Vertical Tree", "IMG_LAYOUT_TREE", new LinWalkersLayoutAlgorithm(ORIENT.TOP_BOTTOM)),
	SPRING("Spring", "IMG_LAYOUT_SPRING", new SpringLayoutAlgorithm()),
	RADIAL("Radial", "IMG_LAYOUT_RADIAL", new RadialLayoutAlgorithm()),
	GRID("Grid", "IMG_LAYOUT_GRID", new CompositeLayoutAlgorithm(
			new ILayoutAlgorithm[] { new GridLayoutAlgorithm(), new HorizontalShiftAlgorithm() })),

	;

	private String layoutName;
	private String imageKey;
	private ILayoutAlgorithm layoutAlgorithm;

	Layout(String name, String key, ILayoutAlgorithm algorithm) {
		layoutName = name;
		imageKey = key;
		layoutAlgorithm = algorithm;
	}

	public String getDisplayName() {
		return layoutName;
	}

	public String getImageUrlname() {
		ImageManager imgMgr = AntlrDTUI.getDefault().getImageManager();
		Object value = Reflect.get(imgMgr, imageKey, true);
		if (value == null) return Strings.EMPTY_STRING;
		return imgMgr.getUrl((String) value).toExternalForm();
	}

	public ImageDescriptor getImageDescriptor() {
		ImageManager imgMgr = AntlrDTUI.getDefault().getImageManager();
		Object key = Reflect.get(imgMgr, imageKey, true);
		if (key == null) return null;
		return imgMgr.getDescriptor((String) key);
	}

	public ILayoutAlgorithm getAlgorithm() {
		return layoutAlgorithm;
	}

	/** Returns an enum given a display name. */
	public static Layout getEnum(String name) {
		if (name != null) {
			for (Layout value : values()) {
				if (value.getDisplayName().toLowerCase().equals(name.toLowerCase())) return value;
			}
		}
		return null;
	}
}
