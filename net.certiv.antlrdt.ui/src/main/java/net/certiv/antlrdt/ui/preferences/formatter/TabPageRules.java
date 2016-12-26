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

public class TabPageRules extends FormatterModifyTabPage implements IFormatterFieldLabels {

	public TabPageRules(IFormatterModifyDialog dialog) {
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

		Composite rule = createOptionGroup(parent, "Rules", 2);

		manager.createLabel(rule, "--- Wrap  ---", 10, 2);

		manager.createCombo(rule, bind(WRAP_NAME_RULE), "Modifiers ", AllValues, AllLabels);
		manager.createCombo(rule, bind(WRAP_COLON_RULE), LBL_COLON, AllValues, AllLabels);
		manager.createCombo(rule, bind(WRAP_ALT_OR), LBL_ALT, AllValues, AllLabels);
		manager.createCombo(rule, bind(WRAP_SEMI_RULE), LBL_SEMI, AllValues, AllLabels);

		manager.createLabel(rule, "--- Space ---", 10, 2);

		manager.createCombo(rule, bind(SPACE_COLON_RULE), LBL_COLON, AllValues, AllLabels);
		manager.createCombo(rule, bind(SPACE_ALT_OR), LBL_ALT, AllValues, AllLabels);
		manager.createCombo(rule, bind(SPACE_SEMI_RULE), LBL_SEMI, AllValues, AllLabels);

		manager.createCombo(rule, bind(SPACE_LBRACKET_RULE), LBL_BRK_OPEN, AllValues, AllLabels);
		manager.createCombo(rule, bind(SPACE_RBRACKET_RULE), LBL_BRK_CLOSE, AllValues, AllLabels);

		// //////////////////////////////////////////////////////////////////////////////

		Composite ruleAt = createOptionGroup(parent, "Rule @Actions", 2);

		manager.createLabel(ruleAt, "--- Wrap  ---", 10, 2);

		// manager.createCombo(ruleAt, bind(WRAP_AT_RULE), LBL_AT, BfNoValues, AllLabels);
		manager.createCombo(ruleAt, bind(WRAP_LBRACE_AT), LBL_BRC_OPEN, AllValues, AllLabels);
		manager.createCombo(ruleAt, bind(WRAP_RBRACE_AT), LBL_BRC_CLOSE, AllValues, AllLabels);

		manager.createLabel(ruleAt, "--- Space ---", 10, 2);

		manager.createCombo(ruleAt, bind(SPACE_LBRACE_AT), LBL_BRC_OPEN, AllValues, AllLabels);
		manager.createCombo(ruleAt, bind(SPACE_RBRACE_AT), LBL_BRC_CLOSE, AllValues, AllLabels);

	}

	protected URL getPreviewContent() {
		return getClass().getResource("PreviewRules.g4"); //$NON-NLS-1$
	}
}
