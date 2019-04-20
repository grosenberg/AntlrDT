/*******************************************************************************
 * Copyright 2009, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.antlrdt.vis.preferences.page;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.ColorFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.DslColorManager;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.preferences.blocks.IPreferenceConfigBlock;
import net.certiv.dsl.ui.preferences.pages.AbstractFieldEditorPreferencePage;

public class GraphColoringPage extends AbstractFieldEditorPreferencePage {

	public GraphColoringPage() {
		super(GRID);
		DslPrefsManagerDelta delta = AntlrCore.getDefault().getPrefsManager().createDeltaManager();
		delta.setDefaultProject(null);
		setPreferenceStore(delta);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	/** Creates the field editors. */
	@Override
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();

		// ///////////////////////////////////////////////////////

		Group colGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(colGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(colGroup);
		colGroup.setText("Graph Element Colors");
		{
			Group nodeGroup = new Group(colGroup, SWT.NONE);
			GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(nodeGroup);
			GridLayoutFactory.fillDefaults().margins(6, 3).applyTo(nodeGroup);
			nodeGroup.setText("Nodes");
			{
				Composite colComp = new Composite(nodeGroup, SWT.NONE);
				GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(colComp);
				GridLayoutFactory.swtDefaults().numColumns(2).margins(0, 3).applyTo(colComp);

				addField(new ColorFieldEditor(bind(PrefsKey.PT_NODE_RULE), "Rule nodes", colComp));
				addField(new ColorFieldEditor(bind(PrefsKey.PT_NODE_TERMINAL), "Token nodes", colComp));
				addField(new ColorFieldEditor(bind(PrefsKey.PT_NODE_ERROR), "Error nodes", colComp));
				addField(new ColorFieldEditor(bind(PrefsKey.PT_NODE_FOREGROUND), "Default foreground", colComp));
				addField(new ColorFieldEditor(bind(PrefsKey.PT_NODE_BACKGROUND), "Default background", colComp));
				addField(new ColorFieldEditor(bind(PrefsKey.PT_NODE_HIGHLIGHT), "Selected highlight", colComp));
				addField(new ColorFieldEditor(bind(PrefsKey.PT_NODE_ADJACENT), "Selected adjacent highlight", colComp));
			}
			// ///////////////////////////////////////////////////////

			Group conGroup = new Group(colGroup, SWT.NONE);
			GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(conGroup);
			GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(conGroup);
			conGroup.setText("Elements");
			{
				Composite conComp = new Composite(conGroup, SWT.NONE);
				GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(conComp);
				GridLayoutFactory.swtDefaults().numColumns(2).applyTo(conComp);

				addField(new ColorFieldEditor(bind(PrefsKey.PT_CONN_COLOR), "Connection", conComp));
				addField(new ColorFieldEditor(bind(PrefsKey.PT_CONN_HIGHLIGHT), "Connection highlighted", conComp));
			}
		}
	}

	@Override
	protected IPreferenceConfigBlock createConfigurationBlock(AbstractFieldEditorPreferencePage page, Composite parent,
			DslPrefsManagerDelta delta, FormToolkit formkit, DslColorManager colorMgr) {
		return null;
	}

	@Override
	protected void adjustSubLayout() {}
}
