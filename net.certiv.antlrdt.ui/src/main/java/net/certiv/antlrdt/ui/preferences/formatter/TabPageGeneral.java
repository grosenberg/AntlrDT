package net.certiv.antlrdt.ui.preferences.formatter;

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

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.util.TabStyle;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.formatter.FormatterModifyTabPage;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialog;
import net.certiv.dsl.ui.preferences.FormatterMessages;
import net.certiv.dsl.ui.preferences.IControlCreationManager;
import net.certiv.dsl.ui.util.SWTFactory;

public class TabPageGeneral extends FormatterModifyTabPage {

	private Combo tabPolicy;
	private Text indentSize;
	private Text tabSize;
	private TabPolicyListener tabPolicyListener;

	String[] tabItems = new String[] { PrefsKey.SPACE, PrefsKey.TAB, PrefsKey.MIXED };
	String[] tabNames = new String[] { FormatterMessages.IndentationTabPage_general_group_option_tab_policy_SPACE,
			FormatterMessages.IndentationTabPage_general_group_option_tab_policy_TAB,
			FormatterMessages.IndentationTabPage_general_group_option_tab_policy_MIXED };

	public TabPageGeneral(IFormatterModifyDialog dialog) {
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

	private class TabPolicyListener extends SelectionAdapter implements IControlCreationManager.IInitializeListener {

		private final IControlCreationManager manager;

		public TabPolicyListener(IControlCreationManager manager) {
			this.manager = manager;
		}

		@Override
		public void widgetSelected(SelectionEvent e) {
			int index = tabPolicy.getSelectionIndex();
			if (index >= 0) {
				final boolean tabMode = PrefsKey.TAB.equals(tabItems[index]);
				manager.enableControl(indentSize, !tabMode);
			}
		}

		@Override
		public void initialize() {
			final boolean tabMode = getDslUI().getFormatterFactory().getPrefsManager().getTabStyle() == TabStyle.TAB;
			manager.enableControl(indentSize, !tabMode);
		}
	}

	@Override
	protected void createOptions(final IControlCreationManager manager, Composite parent) {

		Group tabPolicyGroup = SWTFactory.createGroup(parent, "General Settings", 2, 1, GridData.FILL_HORIZONTAL);
		tabPolicy = manager.createCombo(tabPolicyGroup, bind(PrefsKey.FORMATTER_TAB_POLICY),
				FormatterMessages.IndentationTabPage_general_group_option_tab_policy, tabItems, tabNames);

		tabPolicyListener = new TabPolicyListener(manager);
		tabPolicy.addSelectionListener(tabPolicyListener);
		manager.addInitializeListener(tabPolicyListener);
		indentSize = manager.createNumber(tabPolicyGroup, bind(PrefsKey.FORMATTER_INDENT_SIZE),
				FormatterMessages.IndentationTabPage_general_group_option_indent_size);
		tabSize = manager.createNumber(tabPolicyGroup, bind(PrefsKey.FORMATTER_TAB_SIZE),
				FormatterMessages.IndentationTabPage_general_group_option_tab_size);
		tabSize.addModifyListener(new ModifyListener() {

			@Override
			public void modifyText(ModifyEvent e) {
				int index = tabPolicy.getSelectionIndex();
				if (index >= 0) {
					final boolean tabMode = PrefsKey.TAB.equals(tabItems[index]);
					if (tabMode) {
						indentSize.setText(tabSize.getText());
					}
				}
			}
		});

		// // ///////////////////////////////////////////////////////

		// Group group = SWTFactory.createGroup(parent, "Indentation", 1, 1,
		// GridData.FILL_HORIZONTAL);
		//
		// Composite comp = new Composite(group, SWT.NONE);
		// GridDataFactory.fillDefaults().applyTo(comp);
		// GridLayoutFactory.fillDefaults().margins(6, 6).numColumns(2).applyTo(comp);
		//
		// manager.createCheckbox(comp, bind(PrefsKey.FORMATTER_INDENT_BLOCK_STATEMENTS),
		// "Statements within blocks", 2);
		// manager.createCheckbox(comp, bind(PrefsKey.FORMATTER_INDENT_BLOCK), "Blocks starting on
		// new lines", 2);

		// // ///////////////////////////////////////////////////////

		Group jdtGroup = SWTFactory.createGroup(parent, "Comment/Java Formatting Integration", 1, 1,
				GridData.FILL_HORIZONTAL);

		Composite jdtComp = new Composite(jdtGroup, SWT.NONE);
		GridDataFactory.fillDefaults().applyTo(jdtComp);
		GridLayoutFactory.fillDefaults().margins(6, 6).numColumns(1).applyTo(jdtComp);

		manager.createCheckbox(jdtComp, bind(PrefsKey.FORMATTER_COMMENT_FORMAT), "Enable formatting of comments");
		manager.createCheckbox(jdtComp, bind(PrefsKey.FORMATTER_NATIVE_ENABLE),
				"Enable formatting of native code blocks");
	}

	protected URL getPreviewContent() {
		return getClass().getResource("PreviewGeneral.g4"); //$NON-NLS-1$
	}
}
