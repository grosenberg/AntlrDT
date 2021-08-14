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
package net.certiv.antlr.dt.vis.actions;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.zest.layouts.LayoutAlgorithm;
import org.eclipse.zest.layouts.LayoutStyles;
import org.eclipse.zest.layouts.algorithms.CompositeLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.GridLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.HorizontalShift;
import org.eclipse.zest.layouts.algorithms.RadialLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.SpringLayoutAlgorithm;
import org.eclipse.zest.layouts.algorithms.TreeLayoutAlgorithm;

import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.layouts.Branched;
import net.certiv.antlr.dt.vis.layouts.BranchedLayoutAlgorithm;
import net.certiv.antlr.dt.vis.layouts.LinWalkersLayoutAlgorithm;
import net.certiv.common.stores.Result;
import net.certiv.common.util.Reflect;
import net.certiv.common.util.Strings;

public enum Layout {
	HTREE(
			"Horizontal Tree",
				"IMG_LAYOUT_TREE_HORIZ",
				new LinWalkersLayoutAlgorithm(K.NO_RESIZE),
				Branched.LEFT_RIGHT),
	VTREE(
			"Vertical Tree",
				"IMG_LAYOUT_TREE",
				new LinWalkersLayoutAlgorithm(K.NO_RESIZE, K.TOP_BOTTOM),
				Branched.TOP_BOTTOM),
	HFLOW(
			"Horizontal Flow",
				"IMG_LAYOUT_CALL",
				new BranchedLayoutAlgorithm(K.NO_RESIZE),
				Branched.LEFT_RIGHT),

	CALL(
			"Call Flow",
				"IMG_LAYOUT_GRAPHFLOW",
				new CompositeLayoutAlgorithm(K.NO_RESIZE,
						new LayoutAlgorithm[] { new TreeLayoutAlgorithm(K.NO_RESIZE),
								new HorizontalShift(K.NO_RESIZE) }),
				Branched.LEFT_RIGHT),

	SPRING("Spring", "IMG_LAYOUT_SPRING", new SpringLayoutAlgorithm(K.NO_RESIZE), Branched.MID_POINTS),
	RADIAL("Radial", "IMG_LAYOUT_RADIAL", new RadialLayoutAlgorithm(K.NO_RESIZE), Branched.MID_POINTS),
	GRID(
			"Grid",
				"IMG_LAYOUT_GRID",
				new CompositeLayoutAlgorithm(K.NO_RESIZE,
						new LayoutAlgorithm[] { new GridLayoutAlgorithm(K.NO_RESIZE),
								new HorizontalShift(K.NO_RESIZE) }),
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

	public ImageDescriptor getImageDescriptor() {
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		Result<String> key = Reflect.get(imgMgr, imageKey);
		if (!key.valid()) return null;
		return imgMgr.getDescriptor(key.result);
	}

	public String getImageUrlname() {
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		Result<String> value = Reflect.get(imgMgr, imageKey);
		if (!value.valid()) return Strings.EMPTY;
		return imgMgr.getUrl(value.result).toExternalForm();
	}

	public Branched getRouterStyle() {
		return routerStyle;
	}

	public LayoutAlgorithm getAlgorithm() {
		return layoutAlgorithm;
	}

	public static Layout from(String name) {
		if (name != null) {
			for (Layout value : values()) {
				if (value.getDisplayName().toLowerCase().equals(name.toLowerCase())) return value;
			}
		}
		return null;
	}
}
