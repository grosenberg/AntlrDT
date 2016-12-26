package net.certiv.antlrdt.ui.preferences.page;

import java.util.List;

import org.eclipse.jface.preference.PreferencePage;
import org.eclipse.swt.widgets.Composite;

import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.preferences.blocks.AbstractFoldingPreferenceBlock;

public class FoldingPreferenceBlock extends AbstractFoldingPreferenceBlock {

	public FoldingPreferenceBlock(DslPrefsManagerDelta store, PreferencePage basePage) {
		super(store, basePage);
	}

	@Override
	public List<String> getFoldingKeys(List<String> keys) {

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
	public void addCustomFoldingControls(Composite initialFolding) {
		addCheckBox(initialFolding, "Options", PrefsKey.EDITOR_FOLDING_OPTIONS, 0);
		addCheckBox(initialFolding, "Realm", PrefsKey.EDITOR_FOLDING_SCOPE, 0);
		addCheckBox(initialFolding, "Tokens", PrefsKey.EDITOR_FOLDING_TOKENS, 0);
		addCheckBox(initialFolding, "Init", PrefsKey.EDITOR_FOLDING_INIT, 0);

		addCheckBox(initialFolding, "Header", PrefsKey.EDITOR_FOLDING_HEADER, 0);
		addCheckBox(initialFolding, "After", PrefsKey.EDITOR_FOLDING_AFTER, 0);
		addCheckBox(initialFolding, "Members", PrefsKey.EDITOR_FOLDING_MEMBERS, 0);
		addCheckBox(initialFolding, "Action", PrefsKey.EDITOR_FOLDING_ACTION, 0);

		addCheckBox(initialFolding, "RuleCatch", PrefsKey.EDITOR_FOLDING_RULECATCH, 0);

		addCheckBox(initialFolding, "Catch", PrefsKey.EDITOR_FOLDING_CATCH, 0);
		addCheckBox(initialFolding, "JavaDoc Comments", PrefsKey.EDITOR_FOLDING_COMMENT_JD, 0);
		addCheckBox(initialFolding, "Finally", PrefsKey.EDITOR_FOLDING_FINALLY, 0);
		addCheckBox(initialFolding, "Multi-Line Comments", PrefsKey.EDITOR_FOLDING_COMMENT_ML, 0);
	}
}
