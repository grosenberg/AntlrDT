package net.certiv.antlrdt.ui.preferences.formatter;

import static net.certiv.antlrdt.core.preferences.PrefsKey.*;

import java.net.URL;

import org.eclipse.swt.widgets.Composite;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.formatter.FormatterModifyTabPage;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialog;
import net.certiv.dsl.ui.preferences.IControlCreationManager;

public class TabPageActions extends FormatterModifyTabPage implements IFormatterFieldLabels {

	public TabPageActions(IFormatterModifyDialog dialog) {
		super(dialog);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	protected void createOptions(final IControlCreationManager manager, Composite parent) {

		Composite action = createOptionGroup(parent, "Rule Actions", 2);

		manager.createCombo(action, bind(SPACE_LBRACE_ACT), "Action " + LBL_BRC_OPEN + LBL_SP, AllValues, AllLabels);
		manager.createCombo(action, bind(SPACE_RBRACE_ACT), "Action " + LBL_BRC_CLOSE + LBL_SP, AllValues, AllLabels);

		manager.createCombo(action, bind(WRAP_LBRACE_ACT), "Action " + LBL_BRC_OPEN + LBL_LW, AllValues, AllLabels);
		manager.createCombo(action, bind(WRAP_RBRACE_ACT), "Action " + LBL_BRC_CLOSE + LBL_LW, AllValues, AllLabels);

	}

	protected URL getPreviewContent() {
		return getClass().getResource("PreviewActions.g4"); //$NON-NLS-1$
	}
}
