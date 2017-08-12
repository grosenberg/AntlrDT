package net.certiv.antlrdt.ui.preferences.page;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.ui.preferences.BooleanFieldEditor2;
import net.certiv.dsl.ui.preferences.page.AbstractFieldEditorPreferencePage;

public class PrefPageBuilder extends AbstractFieldEditorPreferencePage {

	private Composite buf;
	private Combo projCombo;
	private boolean enabled;
	private BooleanFieldEditor2 builderEn;
	private BooleanFieldEditor2 projRestriction;
	private BooleanFieldEditor2 curpathRestriction;

	private LinkedHashMap<String, IProject> projects;	// all open projects with antlrdt nature
	private IProject active;							// current has antlrdt nature or null

	public PrefPageBuilder() {
		super(GRID);
		DslPrefsManagerDelta delta = AntlrDTCore.getDefault().getPrefsManager().createDeltaManager();
		delta.setDefaultProject(null);
		setPreferenceStore(delta);

		String natureId = AntlrDTCore.getDefault().getNatureId();
		projects = CoreUtil.getActiveProjects(natureId);
		active = CoreUtil.getActiveProject(natureId);
	}

	/** Creates the field editors. */
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();

		// ///////////////////////////////////////////////////////

		Group projGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(projGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(projGroup);
		projGroup.setText("Project Selection");

		Composite projComp = new Composite(projGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(projComp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(projComp);

		new Label(projComp, SWT.NONE).setText("Builder options for project");
		projCombo = new Combo(projComp, SWT.DROP_DOWN | SWT.READ_ONLY);
		projCombo.setItems(projects.keySet().toArray(new String[projects.size()]));

		if (active != null) {
			int current = projCombo.indexOf(active.getName());
			projCombo.select(current);
		} else {
			projCombo.select(0);
		}

		projCombo.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				int sel = projCombo.getSelectionIndex();
				if (sel > 0) {
					active = projects.get(projCombo.getItem(sel));
				} else {
					active = null;
				}
				getDeltaMgr().setDefaultProject(active);
				initialize();
				checkState();
				updateEnables();
			}
		});

		// ///////////////////////////////////////////////////////

		Group group = new Group(parent, SWT.NONE);
		GridData gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		gridData.verticalIndent = 10;
		gridData.horizontalSpan = 2;
		group.setLayoutData(gridData);

		GridLayout layout = new GridLayout(1, false);
		group.setLayout(layout);
		group.setText("Builder Options");

		buf = new Composite(group, SWT.NONE);
		gridData = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		buf.setLayoutData(gridData);

		layout = new GridLayout(1, false);
		buf.setLayout(layout);

		builderEn = new BooleanFieldEditor2(bind(PrefsKey.BUILDER_ENABLE), "Enable Builder", buf);
		enabled = getDeltaMgr().getBoolean(active, PrefsKey.BUILDER_ENABLE);
		builderEn.addSelectionListener(new SelectionAdapter() {

			public void widgetSelected(SelectionEvent e) {
				enabled = ((Button) e.getSource()).getSelection();
				projRestriction.setEnabled(enabled, buf);
				curpathRestriction.setEnabled(enabled, buf);
				buf.redraw();
			}
		});
		addField(builderEn);

		projRestriction = new BooleanFieldEditor2(bind(PrefsKey.BUILDER_RESTRICT_TO_PROJECT),
				"Restrict builds to current project", buf);
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

		curpathRestriction = new BooleanFieldEditor2(bind(PrefsKey.BUILDER_RESTRICT_TO_PATH),
				"Restrict builds to active path", buf);
		curpathRestriction.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean selected = ((Button) e.getSource()).getSelection();
				if (selected) {
					projRestriction.setBooleanValue(selected);
				}
			}
		});
		addField(curpathRestriction);

		// ///////////////////////////////////////////////////////

		Group postGroup = new Group(parent, SWT.NONE);
		GridData gdGroup = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		gdGroup.verticalIndent = 6;
		gdGroup.horizontalSpan = 2;
		postGroup.setLayoutData(gdGroup);

		GridLayout glGroup = new GridLayout(1, false);
		postGroup.setLayout(glGroup);
		postGroup.setText("Post-Build Actions");

		Composite inComp = new Composite(postGroup, SWT.NONE);
		GridData gdComp = new GridData(GridData.FILL_HORIZONTAL | GridData.GRAB_HORIZONTAL);
		// gdComp.horizontalIndent = 4;
		inComp.setLayoutData(gdComp);

		GridLayout glComp = new GridLayout(1, false);
		inComp.setLayout(glComp);

		addField(new BooleanFieldEditor(bind(PrefsKey.BUILDER_REFRESH), "Refresh project", inComp));
		addField(new BooleanFieldEditor(bind(PrefsKey.BUILDER_ORGANIZE), "Organize imports *", inComp));
		addField(new BooleanFieldEditor(bind(PrefsKey.BUILDER_FORMAT), "Format generated source *", inComp));
		Label notice = new Label(inComp, SWT.NONE);
		notice.setText("* requires the generated files to be on the classpath");
	}

	protected void updateEnables() {
		enabled = getDeltaMgr().getBoolean(active, bind(PrefsKey.BUILDER_ENABLE));
		curpathRestriction.setEnabled(enabled, buf);
		buf.redraw();
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		updateEnables();
	}
}
