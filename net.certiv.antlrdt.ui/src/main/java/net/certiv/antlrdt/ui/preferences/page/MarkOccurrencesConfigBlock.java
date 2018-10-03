package net.certiv.antlrdt.ui.preferences.page;

import java.util.List;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.dsl.core.color.DslColorManager;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.preferences.blocks.AbstractMarkOccurrencesConfigBlock;
import net.certiv.dsl.ui.preferences.pages.IDslPreferencePage;

public class MarkOccurrencesConfigBlock extends AbstractMarkOccurrencesConfigBlock {

	public MarkOccurrencesConfigBlock(IDslPreferencePage page, DslPrefsManagerDelta delta, FormToolkit formkit,
			DslColorManager colorMgr) {
		super(page, delta, formkit, colorMgr);
	}

	@Override
	public List<String> getMarkingKeys(List<String> keys) {
		return keys;
	}

	@Override
	protected void addCustomFoldingControls(Composite composite) {}

}
