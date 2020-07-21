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
package net.certiv.antlr.dt.vis.views;

import org.eclipse.zest.core.viewers.IZoomableWorkbenchPart;

import net.certiv.antlr.dt.vis.actions.Layout;
import net.certiv.antlr.dt.vis.actions.Router;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.IStatement;

public interface IAdjustableViewPart extends IZoomableWorkbenchPart {

	String KEY_LAYOUT = "keyLayout";
	String KEY_ROUTER = "keyRouter";

	String GROUP_ROUTER_TOOLBAR = "groupRouterToolbar";
	String GROUP_LAYOUT_TOOLBAR = "groupLayoutToolbar";

	void setLayout(Layout layout);

	void setRouter(Router router);

	String getId();

	void select(ICodeUnit unit, IStatement statement);
}
