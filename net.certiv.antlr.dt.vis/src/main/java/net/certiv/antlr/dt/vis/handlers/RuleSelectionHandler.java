/*******************************************************************************
 * Copyright 2005-2010, Gerald B. Rosenberg, Certiv Analytics and others.
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
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;

import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.antlr.dt.vis.path.PathOp;
import net.certiv.antlr.dt.vis.path.PathView;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.ui.editor.DslEditor.DslSelection;

public class RuleSelectionHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IEditorPart part = HandlerUtil.getActiveEditor(event);
		if (part == null || !(part instanceof AntlrEditor)) {
			Log.error(this, "Active editor not found or not an Antlr editor");
			return null;
		}

		ISelection sel = HandlerUtil.getCurrentSelection(event);
		if (sel != null && sel instanceof DslSelection) {
			try {
				IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
				IViewPart view = page.showView(PathView.ID);
				if (view instanceof PathView) {
					PathOp op = PathOp.from(event.getParameter(PathOp.PATH_FUNC.text));
					((PathView) view).update(op, (DslSelection) sel);
				}
			} catch (Exception e) {
				throw new ExecutionException(e.getMessage(), e.getCause());
			}

		}
		return null;
	}
}
