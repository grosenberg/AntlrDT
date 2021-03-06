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
package net.certiv.antlr.dt.vis.graph;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;

/** Create the form view */
public class ViewForm {

	private ScrolledForm form;
	private FormToolkit toolkit;
	private ManagedForm managedForm;
	private EnhGraphViewer viewer;

	public ViewForm(Composite parent, FormToolkit toolkit) {
		this.toolkit = toolkit;
		form = this.toolkit.createScrolledForm(parent);
		managedForm = new ManagedForm(toolkit, form);

		FillLayout layout = new FillLayout();
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		form.getBody().setLayout(layout);
		createGraphSection(form.getBody());
	}

	private void createGraphSection(Composite parent) {
		Section section = toolkit.createSection(parent, ExpandableComposite.NO_TITLE);
		viewer = new EnhGraphViewer(section, SWT.NONE);
		section.setClient(viewer.getControl());
	}

	public EnhGraphViewer getGraphViewer() {
		return viewer;
	}

	public ScrolledForm getForm() {
		return form;
	}

	public ManagedForm getManagedForm() {
		return managedForm;
	}
}
