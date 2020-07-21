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
package net.certiv.antlr.dt.diagram.preferences.blocks;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.antlr.dt.diagram.convert.DotBuilder;
import net.certiv.antlr.dt.diagram.preferences.Prefs;
import net.certiv.dsl.core.color.DslColorRegistry;
import net.certiv.dsl.core.preferences.PrefsDeltaManager;
import net.certiv.dsl.ui.preferences.blocks.AbstractConfigBlock;
import net.certiv.dsl.ui.preferences.pages.IDslPreferencePage;
import net.certiv.dsl.ui.preferences.tabs.AbstractTab;
import net.certiv.dsl.ui.util.SWTFactory;

public class DiagramsConfigBlock extends AbstractConfigBlock {

	private final Map<String, Object> values = new LinkedHashMap<>();

	public DiagramsConfigBlock(IDslPreferencePage page, PrefsDeltaManager delta, FormToolkit formkit,
			DslColorRegistry reg) {
		super(page, delta, formkit, reg);
		values.put("Verical", DotBuilder.TB);
		values.put("Horizontal", DotBuilder.LR);
	}

	@Override
	protected List<String> createDeltaMatchKeys(List<String> keys) {
		keys.add(Prefs.DIAGRAM_ORIENT);

		keys.add(Prefs.DIAGRAM_DOT_PROGRAM);
		keys.add(Prefs.DIAGRAM_PANDOC_PROGRAM);

		return keys;
	}

	@Override
	public Composite createContents(Composite parent) {
		Composite comp = super.createContents(parent);

		Composite progs = SWTFactory.createGroupComposite(comp, 3, 3, "Generators");
		addProgramField(progs, Prefs.DIAGRAM_DOT_PROGRAM, 4, 3, "Dot executable", 50, AbstractTab.validFilePath);
		addProgramField(progs, Prefs.DIAGRAM_PANDOC_PROGRAM, 4, 3, "Pandoc executable", 50, AbstractTab.validFilePath);

		// SWTFactory.createVerticalSpacer(comp, 1);

		Composite options = SWTFactory.createGroupComposite(comp, 3, 3, "Options");
		addLabeledCombo(options, "Rank", Prefs.DIAGRAM_ORIENT, values);

		return comp;
	}
}
