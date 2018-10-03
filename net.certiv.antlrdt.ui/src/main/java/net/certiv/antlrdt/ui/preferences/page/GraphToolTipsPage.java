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
import net.certiv.dsl.ui.preferences.editors.RadioGroupFieldEditor;
import net.certiv.dsl.ui.preferences.editors.SpinnerFieldEditor;
import net.certiv.dsl.ui.preferences.pages.AbstractFieldEditorPreferencePage;

public class GraphToolTipsPage extends AbstractFieldEditorPreferencePage {

	private static final String[][] TTYPES = new String[][] { //
			{ "Enhanced", PrefsKey.PT_TTT_ENH }, //
			{ "Standard", PrefsKey.PT_TTT_STD },//
			{ "Minimal", PrefsKey.PT_TTT_MIN } //
	};
	private static final String[][] ALIGNS = new String[][] { //
			{ "Aligned to node", PrefsKey.PT_ENH_ALIGNED },//
			{ "Float relative to pointer", PrefsKey.PT_ENH_FLOAT } //
	};

	public GraphToolTipsPage() {
		super(GRID);
		DslPrefsManagerDelta delta = AntlrDTCore.getDefault().getPrefsManager().createDeltaManager();
		delta.setDefaultProject(null);
		setPreferenceStore(delta);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	/** Creates the field editors. */
	@Override
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();

		// ///////////////////////////////////////////////////////

		Group genGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(genGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(genGroup);
		genGroup.setText("Tooltip Options");

		Composite genComp = new Composite(genGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(genComp);
		GridLayoutFactory.swtDefaults().numColumns(3).applyTo(genComp);

		addField(new RadioGroupFieldEditor(bind(PrefsKey.PT_TOOLTIP_TYPE), "Tooltip type:", 3, TTYPES, genComp));

		// ///////////////////////////////////////////////////////
		// ///////////////////////////////////////////////////////

		Group tipGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(tipGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(tipGroup);
		tipGroup.setText("EnhancedToolTip Controls");

		Composite tipComp = new Composite(tipGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(tipComp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(tipComp);

		addField(new RadioGroupFieldEditor(bind(PrefsKey.PT_ENH_POSITION), "Positioning:", 1, ALIGNS, tipComp));

		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_GAP_ENH_X), "Fixed Gap X distance (pixels):", tipComp, 0, 0,
				100, 1));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_GAP_ENH_Y), "Fixed Gap Y distance (pixels):", tipComp, 0, 0,
				100, 1));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_KILL_DELAY), "Fixed Gap transit grace period (ms):", tipComp,
				0, 20, 2000, 10));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_GAP_FLOAT), "Floating gap distance (pixels):", tipComp, 0, 0,
				100, 1));
		addField(new SpinnerFieldEditor(bind(PrefsKey.PT_TIP_DURATION), "Floating display duration (ms):", tipComp, 0,
				20, 10000, 10));

	}

	@Override
	protected IPreferenceConfigBlock createConfigurationBlock(AbstractFieldEditorPreferencePage page, Composite parent,
			DslPrefsManagerDelta delta, FormToolkit formkit, DslColorManager colorMgr) {
		return null;
	}

	@Override
	protected void adjustSubLayout() {}
}
