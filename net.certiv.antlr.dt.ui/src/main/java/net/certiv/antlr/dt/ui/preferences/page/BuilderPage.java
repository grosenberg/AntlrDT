package net.certiv.antlr.dt.ui.preferences.page;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.DslColorManager;
import net.certiv.dsl.core.preferences.PrefsDeltaManager;
import net.certiv.dsl.core.preferences.consts.Builder;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.preferences.blocks.IPreferenceConfigBlock;
import net.certiv.dsl.ui.preferences.editors.BooleanFieldEditor;
import net.certiv.dsl.ui.preferences.pages.AbstractFieldEditorPreferencePage;
import net.certiv.dsl.ui.util.SWTFactory;

public class BuilderPage extends AbstractFieldEditorPreferencePage {

	private Composite buildComp;
	private boolean enabled;
	private BooleanFieldEditor builderEn;
	private BooleanFieldEditor projRestriction;
	private BooleanFieldEditor curpathRestriction;
	private Composite actionsComp;

	public BuilderPage() {
		super(GRID);
		PrefsDeltaManager delta = AntlrCore.getDefault().getPrefsManager().createDeltaManager();
		setPreferenceStore(delta);
	}

	@Override
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();
		GridDataFactory.fillDefaults().grab(true, true).applyTo(parent);

		buildComp = SWTFactory.createGroupComposite(parent, 2, 3, "Builder Options");

		builderEn = new BooleanFieldEditor(bind(Builder.BUILDER_ENABLE), "Enable Builder", buildComp);
		builderEn.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				enabled = ((Button) e.getSource()).getSelection();
				projRestriction.setEnabled(enabled, buildComp);
				curpathRestriction.setEnabled(enabled, buildComp);
				buildComp.redraw();
			}
		});
		addField(builderEn);

		projRestriction = new BooleanFieldEditor(bind(Builder.BUILDER_RESTRICT_TO_ACTIVE_PROJECT),
				"Restrict builds to grammars within the current project", buildComp);
		projRestriction.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean selected = ((Button) e.getSource()).getSelection();
				if (!selected) {
					curpathRestriction.setBooleanValue(selected);
				}
			}
		});
		addField(projRestriction);

		// ----

		actionsComp = SWTFactory.createGroupComposite(parent, 2, 3, "Post-Build Actions");

		addField(new BooleanFieldEditor(bind(Builder.BUILDER_REFRESH), "Refresh project", actionsComp));
		addField(new BooleanFieldEditor(bind(Builder.BUILDER_ORGANIZE), "Organize imports *", actionsComp));
		addField(new BooleanFieldEditor(bind(Builder.BUILDER_FORMAT), "Format generated source *", actionsComp));
		Label notice = new Label(actionsComp, SWT.NONE);
		notice.setText("        * requires the generated files to within a source package directory");
	}

	protected void updateEnables() {
		buildComp.redraw();
	}

	@Override
	public void performDefaults() {
		super.performDefaults();
		updateEnables();
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
	protected IPreferenceConfigBlock createConfigurationBlock(AbstractFieldEditorPreferencePage page, Composite parent,
			PrefsDeltaManager delta, FormToolkit formkit, DslColorManager colorMgr) {
		return null;
	}

	@Override
	protected void adjustSubLayout() {}
}
