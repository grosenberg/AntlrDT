/*******************************************************************************
 * Copyright 2005-2020, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors: Certiv Analytics
 *******************************************************************************/
package net.certiv.antlr.dt.vis.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.antlr.dt.vis.path.PathOp;
import net.certiv.antlr.dt.vis.path.PathView;
import net.certiv.dsl.core.model.CodeUnit;

public class ClearPathsHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart part = HandlerUtil.getActiveEditor(event);
		if (part instanceof AntlrEditor) {
			AntlrEditor editor = (AntlrEditor) part;
			CodeUnit unit = (CodeUnit) editor.getInputDslElement();

			IWorkbenchPage page = HandlerUtil.getActiveSite(event).getPage();
			IViewReference ref = page.findViewReference(PathView.ID);
			if (ref != null) {
				PathView view = (PathView) ref.getView(false);
				if (view != null) view.update(PathOp.CLEAR, unit, null);
			}
		}
		return null;
	}
}
