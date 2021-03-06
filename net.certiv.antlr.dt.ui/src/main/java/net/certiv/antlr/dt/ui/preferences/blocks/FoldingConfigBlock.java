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
package net.certiv.antlr.dt.ui.preferences.blocks;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.dsl.core.color.DslColorRegistry;
import net.certiv.dsl.core.preferences.PrefsDeltaManager;
import net.certiv.dsl.ui.preferences.blocks.AbstractFoldingConfigBlock;
import net.certiv.dsl.ui.preferences.pages.IDslPreferencePage;

public class FoldingConfigBlock extends AbstractFoldingConfigBlock {

	public FoldingConfigBlock(IDslPreferencePage page, PrefsDeltaManager delta, FormToolkit formkit,
			DslColorRegistry reg) {
		super(page, delta, formkit, reg);
	}

	@Override
	public List<String> getDeltaFoldingKeys(List<String> keys) {
		keys.add(PrefsKey.EDITOR_FOLDING_COMMENT_ML);
		keys.add(PrefsKey.EDITOR_FOLDING_COMMENT_JD);

		keys.add(PrefsKey.EDITOR_FOLDING_OPTIONS);
		keys.add(PrefsKey.EDITOR_FOLDING_TOKENS);
		keys.add(PrefsKey.EDITOR_FOLDING_HEADER);
		keys.add(PrefsKey.EDITOR_FOLDING_MEMBERS);
		keys.add(PrefsKey.EDITOR_FOLDING_RULECATCH);
		keys.add(PrefsKey.EDITOR_FOLDING_SCOPE);
		keys.add(PrefsKey.EDITOR_FOLDING_INIT);
		keys.add(PrefsKey.EDITOR_FOLDING_AFTER);
		keys.add(PrefsKey.EDITOR_FOLDING_ACTION);
		keys.add(PrefsKey.EDITOR_FOLDING_CATCH);
		keys.add(PrefsKey.EDITOR_FOLDING_FINALLY);

		return keys;
	}

	@Override
	public void addCustomControls(Composite initialFolding) {
		addCheckBox(initialFolding, "JavaDoc Comments", PrefsKey.EDITOR_FOLDING_COMMENT_JD, 0);
		addCheckBox(initialFolding, "Block Comments", PrefsKey.EDITOR_FOLDING_COMMENT_ML, 0);

		addCheckBox(initialFolding, "Options", PrefsKey.EDITOR_FOLDING_OPTIONS, 0);
		addCheckBox(initialFolding, "Tokens", PrefsKey.EDITOR_FOLDING_TOKENS, 0);
		addCheckBox(initialFolding, "Header", PrefsKey.EDITOR_FOLDING_HEADER, 0);
		addCheckBox(initialFolding, "Members", PrefsKey.EDITOR_FOLDING_MEMBERS, 0);

		addCheckBox(initialFolding, "Action", PrefsKey.EDITOR_FOLDING_ACTION, 0);

		addCheckBox(initialFolding, "Scope", PrefsKey.EDITOR_FOLDING_SCOPE, 0);
		addCheckBox(initialFolding, "Init", PrefsKey.EDITOR_FOLDING_INIT, 0);
		addCheckBox(initialFolding, "Finally", PrefsKey.EDITOR_FOLDING_FINALLY, 0);
		addCheckBox(initialFolding, "After", PrefsKey.EDITOR_FOLDING_AFTER, 0);
		addCheckBox(initialFolding, "Catch", PrefsKey.EDITOR_FOLDING_CATCH, 0);
		addCheckBox(initialFolding, "RuleCatch", PrefsKey.EDITOR_FOLDING_RULECATCH, 0);
	}
}
