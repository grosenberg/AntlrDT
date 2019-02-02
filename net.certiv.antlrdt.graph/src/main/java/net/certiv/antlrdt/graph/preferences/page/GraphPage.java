/*******************************************************************************
 * Copyright 2009, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.antlrdt.graph.preferences.page;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.DslColorManager;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.preferences.blocks.IPreferenceConfigBlock;
import net.certiv.dsl.ui.preferences.editors.BooleanFieldEditor;
import net.certiv.dsl.ui.preferences.editors.SpinnerFieldEditor;
import net.certiv.dsl.ui.preferences.pages.AbstractFieldEditorPreferencePage;

public class GraphPage extends AbstractFieldEditorPreferencePage {

	public GraphPage() {
		super(GRID);
	}

	/** Creates the field editors. */
	@Override
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();

		Group group = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(group);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(group);
		group.setText("General Options");

		Composite comp = new Composite(group, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(comp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(comp);

		addField(new BooleanFieldEditor(bind(PrefsKey.PT_FIND_IMPL), "Double-click activates code editor", comp));
		addField(new BooleanFieldEditor(bind(PrefsKey.PT_SURFACE_DRAG_ENABLED), "Surface drag enabled", comp));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_ANIMATION_LIMIT), "Animation limit", comp, 0, 10, 1000, 10));

		// ///////////////////////////////////////////////////////

		Group pvGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(1, 1).applyTo(pvGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(pvGroup);
		pvGroup.setText("Target View Controls");

		Composite pvComp = new Composite(pvGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(pvComp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(pvComp);

		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_DEPTH_LIMIT), "Initial Target Depth:", pvComp, 0, 1, 100, 1));

		// ///////////////////////////////////////////////////////

		Group alGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(alGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).numColumns(2).applyTo(alGroup);
		alGroup.setText("TreeFlow Controls");

		// ///////////////////////////////////////////////////////

		Group lgrp = new Group(alGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(1, 1).applyTo(lgrp);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(lgrp);
		lgrp.setText("Layout");

		Composite lcomp = new Composite(lgrp, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(lcomp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(lcomp);

		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_SIBLING_SPACING), "Inter-sibling spacing:", lcomp, 0, 16, 1000,
				8));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_SUBTREE_SPACING), "Inter-subtree spacing:", lcomp, 0, 16, 1000,
				8));
		addField(
				new SpinnerFieldEditor(bind(PrefsKey.PT_DEPTH_SPACING), "Inter-level spacing:", lcomp, 0, 16, 1000, 8));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_ROOT_OFFSET), "Root node offset:", lcomp, 0, 16, 1000, 8));

		// ///////////////////////////////////////////////////////

		Group agrp = new Group(alGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(1, 1).applyTo(agrp);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(agrp);
		agrp.setText("Algorithm");

		Composite acomp = new Composite(agrp, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(lcomp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(lcomp);

		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_VERT_SPACING), "Vertical spacing:", acomp, 0, 16, 1000, 8));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_HORZ_SPACING), "Horizontal spacing:", acomp, 0, 16, 1000, 8));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_SOURCE_LEAD), "Source lead:", acomp, 0, 8, 100, 8));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_TARGET_LEAD), "Target lead:", acomp, 0, 8, 100, 8));

		// ///////////////////////////////////////////////////////
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	protected IPreferenceConfigBlock createConfigurationBlock(AbstractFieldEditorPreferencePage page, Composite parent,
			DslPrefsManagerDelta delta, FormToolkit formkit, DslColorManager colorMgr) {
		return null;
	}

	@Override
	protected void adjustSubLayout() {}
}
