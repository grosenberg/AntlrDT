package net.certiv.antlrdt.ui.preferences.page;

import org.eclipse.jface.preference.PreferencePage;

import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.preferences.blocks.AbstractFoldingConfigBlock;

public class FoldingConfigBlock extends AbstractFoldingConfigBlock {

	public FoldingConfigBlock(DslPrefsManagerDelta store, PreferencePage prefPage) {
		super(store, prefPage);
		setPrefBlock(new FoldingPreferenceBlock(store, prefPage));
	}
}
