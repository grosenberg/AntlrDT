package net.certiv.antlr.dt.ui.preferences.page;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.DslColorManager;
import net.certiv.dsl.core.preferences.PrefsDeltaManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.preferences.blocks.IPreferenceConfigBlock;
import net.certiv.dsl.ui.preferences.editors.WorkspaceDirFieldEditor;
import net.certiv.dsl.ui.preferences.pages.AbstractFieldEditorPreferencePage;
import net.certiv.dsl.ui.util.SWTFactory;

public class GeneralPage extends AbstractFieldEditorPreferencePage {

	private Composite dirComp;
	private WorkspaceDirFieldEditor snippetDir;

	public GeneralPage() {
		super(GRID);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	@Override
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		dirComp = SWTFactory.createGroupComposite(parent, 2, 3, "Snippet Test Integration");
		snippetDir = new WorkspaceDirFieldEditor(bind(PrefsKey.SNIPPETTEST_BASEDIR_SOURCE), "Snippets", dirComp);
		snippetDir.setEmptyStringAllowed(true);
		addField(snippetDir);
		updateEnables();
	}

	protected void updateEnables() {
		snippetDir.setEnabled(true, dirComp);
		dirComp.redraw();
	}

	@Override
	public void performDefaults() {
		super.performDefaults();
		updateEnables();
	}

	@Override
	protected IPreferenceConfigBlock createConfigurationBlock(AbstractFieldEditorPreferencePage page, Composite parent,
			PrefsDeltaManager delta, FormToolkit formkit, DslColorManager colorMgr) {
		return null;
	}

	@Override
	protected void adjustSubLayout() {}
}
