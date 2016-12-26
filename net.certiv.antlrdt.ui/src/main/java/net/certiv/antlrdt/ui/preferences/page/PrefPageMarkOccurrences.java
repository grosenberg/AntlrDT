package net.certiv.antlrdt.ui.preferences.page;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.preference.BooleanFieldEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.preferences.page.AbstractFieldEditorPreferencePage;

public class PrefPageMarkOccurrences extends AbstractFieldEditorPreferencePage {

	public PrefPageMarkOccurrences() {
		super(GRID);
		DslPrefsManagerDelta delta = AntlrDTCore.getDefault().getPrefsManager().createDeltaManager();
		delta.setDefaultProject(null);
		setPreferenceStore(delta);
	}

	/**Creates the field editors.	 */
	public void createFieldEditors() {
		Composite parent = getFieldEditorParent();

		Group postGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(postGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(postGroup);
		postGroup.setText("General Options");

		Composite inComp = new Composite(postGroup, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 4).grab(true, false).applyTo(inComp);
		GridLayoutFactory.fillDefaults().applyTo(inComp);

		addField(new BooleanFieldEditor(bind(PrefsKey.EDITOR_MARK_OCCURRENCES), "Mark occurrences", inComp));
	}
}
