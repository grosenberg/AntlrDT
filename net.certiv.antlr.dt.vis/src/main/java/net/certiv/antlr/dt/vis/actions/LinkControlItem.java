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

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.antlr.dt.vis.graph.IAdjustableViewPart;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.core.model.ModelException;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.ui.editor.DslEditor.DslSelection;

public class LinkControlItem extends ControlContribution {

	public static final String ID = "net.certiv.antlr.dt.vis.actions.LinkControlItem";

	private final IAdjustableViewPart view;
	private final String key;

	public LinkControlItem(IAdjustableViewPart view) {
		super(ID);
		this.view = view;

		key = view.getId() + IAdjustableViewPart.LINK;
	}

	@Override
	protected Control createControl(Composite parent) {
		final PrefsManager store = AntlrCore.getDefault().getPrefsManager();
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();

		Button link = new Button(parent, SWT.TOGGLE);
		link.setImage(imgMgr.get(imgMgr.IMG_LCL_SYNC));
		link.setToolTipText("Link with editor");
		link.setSelection(store.getBoolean(key));
		link.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent evt) {
				boolean linking = link.getSelection();
				store.setValue(key, linking);

				if (linking) {
					IEditorPart part = CoreUtil.getActiveEditor();
					if (part instanceof AntlrEditor) {
						AntlrEditor editor = (AntlrEditor) part;
						ISelection selection = editor.getSelectionProvider().getSelection();
						if (selection instanceof DslSelection) {
							try {
								IStatement stmt = ((DslSelection) selection).resolveElement();
								ICodeUnit unit = stmt.getCodeUnit();
								view.select(unit, stmt);
							} catch (ModelException e) {}
							return;
						}
					}
				}
			}
		});

		return link;
	}
}
