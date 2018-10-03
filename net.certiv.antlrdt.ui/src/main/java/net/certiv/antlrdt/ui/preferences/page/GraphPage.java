/*******************************************************************************
 * Copyright 2009, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.antlrdt.ui.preferences.page;

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

		// ///////////////////////////////////////////////////////

		Group genGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(genGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(genGroup);
		genGroup.setText("General Options");

		Composite genComp = new Composite(genGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(genComp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(genComp);

		addField(new BooleanFieldEditor(bind(PrefsKey.PT_FIND_IMPL), "Double-click activates code editor", genComp));
		addField(new BooleanFieldEditor(bind(PrefsKey.PT_SURFACE_DRAG_ENABLED), "Surface drag enabled", genComp));
		addField(
				new SpinnerFieldEditor(bind(PrefsKey.PT_ANIMATION_LIMIT), "Animation limit", genComp, 0, 10, 10000, 1));

		// ///////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////

		Group pvGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(1, 1).applyTo(pvGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(pvGroup);
		pvGroup.setText("Path View Controls");

		Composite pvComp = new Composite(pvGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(pvComp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(pvComp);

		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_DEPTH_LIMIT), "Initial Path Depth:", pvComp, 0, 1, 1000, 1));

		// ///////////////////////////////////////////////////////

		// ///////////////////////////////////////////////////////

		Group alGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).span(2, 1).applyTo(alGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).numColumns(2).applyTo(alGroup);
		alGroup.setText("TreeFlow Controls");

		// ///////////////////////////////////////////////////////

		Group trGroup = new Group(alGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(1, 1).applyTo(trGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(trGroup);
		trGroup.setText("Layout");

		Composite wkComp = new Composite(trGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(wkComp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(wkComp);

		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_SIBLING_SPACING), "Inter-sibling spacing:", wkComp, 0, 0, 100,
				1));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_SUBTREE_SPACING), "Inter-subtree spacing:", wkComp, 0, 0, 100,
				1));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_DEPTH_SPACING), "Inter-level spacing:", wkComp, 0, 0, 200, 1));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_ROOT_OFFSET), "Root node offset:", wkComp, 0, 0, 200, 1));

		// ///////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////

		Group tfGroup = new Group(alGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(1, 1).applyTo(tfGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(tfGroup);
		tfGroup.setText("Algorithm");

		Composite tfComp = new Composite(tfGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(wkComp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(wkComp);

		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_VERT_SPACING), "Vertical spacing:", tfComp, 0, 10, 1000, 1));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_HORZ_SPACING), "Horizontal spacing:", tfComp, 0, 10, 1000, 1));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_SOURCE_LEAD), "Source lead:", tfComp, 0, 0, 100, 1));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_TARGET_LEAD), "Target lead:", tfComp, 0, 0, 100, 1));

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
