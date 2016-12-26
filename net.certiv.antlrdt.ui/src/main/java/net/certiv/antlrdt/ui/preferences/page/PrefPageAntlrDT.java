package net.certiv.antlrdt.ui.preferences.page;

import java.util.LinkedHashMap;

import org.eclipse.core.resources.IProject;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.ui.preferences.page.AbstractFieldEditorPreferencePage;
import net.certiv.dsl.ui.preferences.page.WorkspaceDirFieldEditor;

public class PrefPageAntlrDT extends AbstractFieldEditorPreferencePage {

	private LinkedHashMap<String, IProject> projects; 	// all open projects with antlrdt nature
	private IProject active; 							// current has antlrdt nature or null

	private Composite projComp;
	private Combo projCombo;
	private Composite dirComp;
	private WorkspaceDirFieldEditor testBase;
	private boolean enabled;

	public PrefPageAntlrDT() {
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
		projGroup.setText("Snippet Test Integration");

		projComp = new Composite(projGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(projComp);
		GridLayoutFactory.swtDefaults().numColumns(2).applyTo(projComp);

		new Label(projComp, SWT.NONE).setText("Project");
		projCombo = new Combo(projComp, SWT.DROP_DOWN | SWT.READ_ONLY);
		projCombo.setItems(projects.keySet().toArray(new String[projects.size()]));

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
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(group);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(group);
		group.setText("Test Base Directories");

		dirComp = new Composite(group, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(dirComp);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(dirComp);

		testBase = new WorkspaceDirFieldEditor(bind(PrefsKey.SNIPPETTEST_BASEDIR_SOURCE), "Snippets", dirComp);
		testBase.setEmptyStringAllowed(true);
		addField(testBase);

		// ///////////////////////////////////////////////////////

		if (active != null) {
			int current = projCombo.indexOf(active.getName());
			projCombo.select(current);
		} else {
			projCombo.select(0);
			testBase.setStringValue(null);
		}
		initialize();
		checkState();
		updateEnables();
	}

	protected void updateEnables() {
		enabled = projCombo.getSelectionIndex() != 0 ? true : false;
		testBase.setEnabled(enabled, dirComp);
		dirComp.redraw();
	}

	@Override
	protected void performDefaults() {
		super.performDefaults();
		updateEnables();
	}
}
