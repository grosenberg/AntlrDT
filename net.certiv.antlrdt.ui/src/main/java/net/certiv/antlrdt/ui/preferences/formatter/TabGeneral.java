package net.certiv.antlrdt.ui.preferences.formatter;

import static net.certiv.dsl.ui.preferences.IEditorConfig.TabPolicyLabels;
import static net.certiv.dsl.ui.preferences.IEditorConfig.TabPolicyValues;

import java.net.URL;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.consts.Formatter;
import net.certiv.dsl.core.util.eclipse.TabStyle;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.formatter.FormatterModifyTabPage;
import net.certiv.dsl.ui.formatter.IDslFormatterFactory;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialog;
import net.certiv.dsl.ui.preferences.FormatterMessages;
import net.certiv.dsl.ui.preferences.bind.IControlCreationManager;
import net.certiv.dsl.ui.util.SWTFactory;

public class TabGeneral extends FormatterModifyTabPage {

	private Combo tabPolicy;
	private Text indentSize;
	private Text tabSize;
	private TabPolicyListener tabPolicyListener;
	private IDslFormatterFactory factory;

	public TabGeneral(IFormatterModifyDialog dialog, IDslFormatterFactory factory) {
		super(dialog);
		this.factory = factory;
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	private class TabPolicyListener extends SelectionAdapter implements IControlCreationManager.IInitializeListener {

		private final IControlCreationManager manager;

		public TabPolicyListener(IControlCreationManager manager) {
			this.manager = manager;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			int index = tabPolicy.getSelectionIndex();
			if (index >= 0) {
				final boolean tabMode = Formatter.TAB.equals(TabPolicyValues[index]);
				manager.enableControl(indentSize, !tabMode);
			}
		}

		@Override
		public void initialize() {
			final boolean tabMode = factory.getPrefsManager().getTabStyle() == TabStyle.TAB;
			manager.enableControl(indentSize, !tabMode);
		}
	}

	@Override
	protected void createOptions(final IControlCreationManager manager, Composite parent) {

		Group tabPolicyGroup = SWTFactory.createGroup(parent, "General Settings", 2, 1, GridData.FILL_HORIZONTAL);
		tabPolicy = manager.createCombo(tabPolicyGroup, bind(Formatter.FORMATTER_TAB_POLICY),
				FormatterMessages.IndentationTabPage_general_group_option_tab_policy, TabPolicyValues, TabPolicyLabels);

		tabPolicyListener = new TabPolicyListener(manager);
		tabPolicy.addSelectionListener(tabPolicyListener);
		manager.addInitializeListener(tabPolicyListener);
		indentSize = manager.createNumber(tabPolicyGroup, bind(Formatter.FORMATTER_INDENT_SIZE),
				FormatterMessages.IndentationTabPage_general_group_option_indent_size);
		tabSize = manager.createNumber(tabPolicyGroup, bind(Formatter.FORMATTER_TAB_SIZE),
				FormatterMessages.IndentationTabPage_general_group_option_tab_size);
		tabSize.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				int index = tabPolicy.getSelectionIndex();
				if (index >= 0) {
					final boolean tabMode = Formatter.TAB.equals(TabPolicyValues[index]);
					if (tabMode) {
						indentSize.setText(tabSize.getText());
					}
				}
			}
		});

		// ///////////////////////////////////////////////////////

		Group dirGroup = SWTFactory.createGroup(parent, "Corpus", 1, 1, GridData.FILL_HORIZONTAL);

		Composite dirComp = new Composite(dirGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(dirComp);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(dirComp);

		manager.createDirSelect(dirComp, bind(Formatter.FORMATTER_CORPUS_BASEDIR), "Corpus root directory",
				getDslCore().getEnvManager());
		manager.createCombo(dirComp, bind(Formatter.FORMATTER_CORPUS_LANGUAGE), "Language", Formatter.ADEPT_LANGS);

		// ///////////////////////////////////////////////////////

		Group fmtGroup = SWTFactory.createGroup(parent, "Formatting Options", 1, 1, GridData.FILL_HORIZONTAL);

		Composite fmtComp = new Composite(fmtGroup, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(fmtComp);
		GridLayoutFactory.fillDefaults().margins(6, 6).numColumns(1).applyTo(fmtComp);

		manager.createCheckbox(fmtComp, bind(Formatter.FORMATTER_CODE_FORMAT), "Enable code formatting");
		manager.createCheckbox(fmtComp, bind(Formatter.FORMATTER_COMMENT_FORMAT), "Enable comment formatting");
		manager.createCheckbox(fmtComp, bind(Formatter.FORMATTER_HEADER_FORMAT), "Enable comment header formatting");
		manager.createCheckbox(fmtComp, bind(Formatter.FORMATTER_ALIGN_FIELD), "Enable code field alignment");
		manager.createCheckbox(fmtComp, bind(Formatter.FORMATTER_ALIGN_COMMENT), "Enable comment alignment");
		manager.createCheckbox(fmtComp, bind(Formatter.FORMATTER_SPLIT_WRAP), "Enable line split/wrapping");
		manager.createCheckbox(fmtComp, bind(Formatter.FORMATTER_NATIVE_ENABLE), "Enable native code formatting");

	}

	@Override
	protected URL getPreviewContent() {
		return getClass().getResource("PreviewGeneral.g4"); //$NON-NLS-1$
	}
}
