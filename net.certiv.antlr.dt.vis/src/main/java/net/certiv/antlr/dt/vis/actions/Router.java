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

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.ManhattanConnectionRouter;
import org.eclipse.jface.resource.ImageDescriptor;

import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.vis.layouts.BranchedConnectionRouter;
import net.certiv.dsl.core.util.Reflect;
import net.certiv.dsl.core.util.Strings;

public enum Router {

	BRANCHED("Branched", "IMG_LAYOUT_CALL", new BranchedConnectionRouter()),
	FAN("Fan", "IMG_LAYOUT_GRAPHFLOW", new FanRouter()),
	MANHATTAN("Orthogonal", "IMG_LAYOUT_TREE_ORTHO", new ManhattanConnectionRouter());

	private String routerName;
	private String imageKey;
	private AbstractRouter algorithm;

	Router(String name, String key, AbstractRouter router) {
		routerName = name;
		imageKey = key;
		algorithm = router;
	}

	public String getDisplayName() {
		return routerName;
	}

	public ImageDescriptor getImageDescriptor() {
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		Object key = Reflect.get(imgMgr, imageKey, true);
		if (key == null) return null;
		return imgMgr.getDescriptor((String) key);
	}

	public String getImageUrlname() {
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		Object value = Reflect.get(imgMgr, imageKey, true);
		if (value == null) return Strings.EMPTY;
		return imgMgr.getUrl((String) value).toExternalForm();
	}

	public AbstractRouter getRouter() {
		return algorithm;
	}

	public static Router from(String name) {
		if (name != null) {
			for (Router value : values()) {
				if (value.getDisplayName().toLowerCase().equals(name.toLowerCase())) return value;
			}
		}
		return null;
	}
}
