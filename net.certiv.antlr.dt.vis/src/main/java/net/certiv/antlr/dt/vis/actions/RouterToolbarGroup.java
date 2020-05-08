/*******************************************************************************
 * Copyright 2005-2009, Gerald B. Rosenberg, Certiv Analytics and others. All rights reserved. This
 * program and the accompanying materials are made available under the terms of the Eclipse Public
 * License v1.0 which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: Certiv Analytics The Chisel Group,
 * University of Victoria IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlr.dt.vis.actions;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.ui.actions.ActionGroup;

import net.certiv.antlr.dt.vis.views.IAdjustableViewPart;
import net.certiv.antlr.dt.vis.views.parse.TreeView;

public class RouterToolbarGroup extends ActionGroup {

	private IAdjustableViewPart view;

	public RouterToolbarGroup(IAdjustableViewPart view) {
		this.view = view;
	}

	@Override
	public void fillContextMenu(IMenuManager menu) {
		MenuManager subMenu = new MenuManager("Routers");
		menu.appendToGroup(TreeView.GROUP_ROUTER_TOOLBAR, subMenu);

		Action action = null;

		action = new ConnectionAction(Router.BRANCHED);
		subMenu.add(action);

		action = new ConnectionAction(Router.FAN);
		subMenu.add(action);

		action = new ConnectionAction(Router.MANHATTAN);
		subMenu.add(action);
	}

	private class ConnectionAction extends Action {

		private Router router;

		public ConnectionAction(Router router) {
			this.router = router;
			setText(router.getDisplayName());
		}

		@Override
		public void run() {
			view.setRouter(router);
		}
	}
}
