package net.certiv.antlrdt.ui.preferences.formatter;

import static net.certiv.antlrdt.core.preferences.PrefsKey.*;
import static net.certiv.dsl.core.preferences.consts.Formatter.AFTER;
import static net.certiv.dsl.core.preferences.consts.Formatter.AROUND;
import static net.certiv.dsl.core.preferences.consts.Formatter.BEFORE;
import static net.certiv.dsl.core.preferences.consts.Formatter.NONE;

import java.net.URL;

import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.widgets.Composite;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.formatter.FormatterModifyTabPage;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialog;
import net.certiv.dsl.ui.preferences.IControlCreationManager;

public class TabPagePreamble extends FormatterModifyTabPage implements IFormatterFieldLabels {

	public TabPagePreamble(IFormatterModifyDialog dialog) {
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

		Composite header = createOptionGroup(parent, "Grammar header", 2);
		manager.createCombo(header, bind(SPACE_SEMI_HDR), LBL_SEMI + LBL_SP, AllValues, AllLabels);

		// //////////////////////////////////////////////////////////////////////////////

		Composite options = createOptionGroup(parent, "Options", 2);
		manager.createCombo(options, bind(SPACE_EQ_OPT), LBL_ASSIGN + LBL_SP, AllValues, AllLabels);
		manager.createCombo(options, bind(SPACE_SEMI_OPT), LBL_SEMI + LBL_SP, AllValues, AllLabels);

		// //////////////////////////////////////////////////////////////////////////////

		Composite tokens = createOptionGroup(parent, "Tokens", 2);
		manager.createCombo(tokens, bind(SPACE_LBRACE_TOK), LBL_BRC_OPEN + LBL_SP, AllValues, AllLabels);

		manager.createCombo(tokens, bind(WRAP_ID_TOK), LBL_IDENT + LBL_LW, BfNoLabels, BfNoLabels);
		manager.createCombo(tokens, bind(SPACE_COMMA_TOK), LBL_COMMA + LBL_SP, AllValues, AllLabels);

		manager.createCombo(tokens, bind(SPACE_RBRACE_TOK), LBL_BRC_CLOSE + LBL_SP, AllValues, AllLabels);
		manager.createCombo(tokens, bind(WRAP_RBRACE_TOK), LBL_BRC_CLOSE + LBL_LW, BfNoValues, BfNoLabels);

		// //////////////////////////////////////////////////////////////////////////////

		Composite chans = createOptionGroup(parent, "Channels", 2);
		manager.createCombo(chans, bind(SPACE_LBRACE_CHAN), LBL_BRC_OPEN + LBL_SP, AllValues, AllLabels);

		manager.createCombo(chans, bind(WRAP_ID_CHAN), LBL_IDENT + LBL_LW, BfNoLabels, BfNoLabels);
		manager.createCombo(chans, bind(SPACE_COMMA_CHAN), LBL_COMMA + LBL_SP, AllValues, AllLabels);

		manager.createCombo(chans, bind(SPACE_RBRACE_CHAN), LBL_BRC_CLOSE + LBL_SP, AllValues, AllLabels);
		manager.createCombo(chans, bind(WRAP_RBRACE_CHAN), LBL_BRC_CLOSE + LBL_LW, BfNoValues, BfNoLabels);

		// //////////////////////////////////////////////////////////////////////////////

		getPrefs().addPropertyChangeListener(new IPropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent event) {
				boolean tokn = event.getProperty().equals(bind(WRAP_RBRACE_TOK));
				boolean chan = event.getProperty().equals(bind(WRAP_RBRACE_CHAN));
				if (tokn || chan) {
					String value = (String) event.getNewValue();
					switch (value) {
						case AFTER:
							value = BEFORE;
							break;
						case BEFORE:
							value = AFTER;
							break;
						case AROUND:
						case NONE:
					}
					if (tokn) {
						getPrefs().setValue(WRAP_LBRACE_TOK, value);
						Log.debug(this, "WRAP_LBRACE_TOK " + value);
					} else {
						getPrefs().setValue(WRAP_LBRACE_CHAN, value);
						Log.debug(this, "WRAP_LBRACE_CHAN " + value);
					}
				}
			}
		});

		// force property change events
		getPrefs().setValue(WRAP_RBRACE_TOK, getPrefs().getString(WRAP_RBRACE_TOK));
		getPrefs().setValue(WRAP_RBRACE_CHAN, getPrefs().getString(WRAP_RBRACE_CHAN));
	}

	private IDslPrefsManager getPrefs() {
		return getDslUI().getFormatterFactory().getPrefsManager();
	}

	protected URL getPreviewContent() {
		return getClass().getResource("PreviewPreamble.g4"); //$NON-NLS-1$
	}
}
