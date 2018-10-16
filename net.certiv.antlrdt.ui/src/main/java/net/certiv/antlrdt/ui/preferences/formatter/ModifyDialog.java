package net.certiv.antlrdt.ui.preferences.formatter;

import net.certiv.dsl.ui.formatter.FormatterModifyDialog;
import net.certiv.dsl.ui.formatter.IDslFormatterFactory;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialogOwner;

public class ModifyDialog extends FormatterModifyDialog {

	public ModifyDialog(IFormatterModifyDialogOwner dialogOwner, IDslFormatterFactory factory) {
		super(dialogOwner, factory);
	}

	@Override
	protected void addPages() {
		addTabPage("General", new TabGeneral(this, getFormatterFactory()));
		addTabPage("Blank Lines", new TabBlankLines(this));
	}
}
