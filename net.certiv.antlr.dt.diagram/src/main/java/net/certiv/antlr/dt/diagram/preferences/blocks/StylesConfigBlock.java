package net.certiv.antlr.dt.diagram.preferences.blocks;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.dsl.core.color.DslColorManager;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.preferences.bind.DirectorypathValidator;
import net.certiv.dsl.ui.preferences.blocks.AbstractConfigBlock;
import net.certiv.dsl.ui.preferences.pages.IDslPreferencePage;
import net.certiv.dsl.ui.util.SWTFactory;

public class StylesConfigBlock extends AbstractConfigBlock {

	private Combo pCombo;
	private Composite pComp;
	private Combo sCombo;
	private Composite sComp;

	public StylesConfigBlock(IDslPreferencePage page, DslPrefsManagerDelta delta, FormToolkit formkit,
			DslColorManager colorMgr) {
		super(page, delta, formkit, colorMgr);
	}

	@Override
	protected List<String> createDeltaMatchKeys(List<String> keys) {
		keys.add(PrefsKey.EDITOR_SEMANTIC_ENABLED);

		keys.add(PrefsKey.EDITOR_SEMANTIC_FILE);
		keys.add(PrefsKey.EDITOR_SEMANTIC_INTERNAL_DIR);
		keys.add(PrefsKey.EDITOR_SEMANTIC_EXTERNAL_DIR);
		keys.add(PrefsKey.EDITOR_SEMANTIC_EXTERNAL_ENABLE);

		keys.add(PrefsKey.EDITOR_PREVIEW_FILE);
		keys.add(PrefsKey.EDITOR_PREVIEW_INTERNAL_DIR);
		keys.add(PrefsKey.EDITOR_PREVIEW_EXTERNAL_DIR);
		keys.add(PrefsKey.EDITOR_PREVIEW_EXTERNAL_ENABLE);

		return keys;
	}

	@Override
	public Composite createContents(Composite parent) {
		Composite contents = super.createContents(parent);
		Composite comp = SWTFactory.createGroupComposite(contents, 1, 3, "Stylesheet Selection");
		addEditingControls(comp);

		return comp;
	}

	private void addEditingControls(Composite parent) {
		createSemanticStylesGroup(parent);
		createPreviewStylesGroup(parent);
	}

	protected Composite createSemanticStylesGroup(Composite parent) {
		Composite comp = SWTFactory.createGroupComposite(parent, 3, 2, "Semantic Editor Styles");

		addCheckBox(comp, "Enable", PrefsKey.EDITOR_SEMANTIC_ENABLED, 2, 4);
		sCombo = addLabeledCombo(comp, "Select", PrefsKey.EDITOR_SEMANTIC_FILE, semanticFiles());

		addVerticalSpace(comp, 1, 3);

		addCheckBox(comp, "Use external stylesheet", PrefsKey.EDITOR_SEMANTIC_EXTERNAL_ENABLE, 2, 4)
				.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						adjustSemanticSelection(((Button) e.widget).getSelection());
					}
				});

		sComp = SWTFactory.createComposite(comp, 2, 2, 4);
		addDirectoryField(sComp, PrefsKey.EDITOR_SEMANTIC_EXTERNAL_DIR, 2, 2, "Directory", 50,
				new DirectorypathValidator(null, true));

		adjustSemanticSelection(delta.getBoolean(PrefsKey.EDITOR_SEMANTIC_EXTERNAL_ENABLE));
		return comp;
	}

	private void adjustSemanticSelection(boolean external) {
		String choice = delta.getString(PrefsKey.EDITOR_SEMANTIC_FILE);
		File f = new File(choice);
		choice = f.getName();
		enableControls(sComp, external);
		if (external) {
			updateCombo(sCombo, choices(PrefsKey.EDITOR_SEMANTIC_EXTERNAL_DIR));
		} else {
			updateCombo(sCombo, choices(PrefsKey.EDITOR_SEMANTIC_INTERNAL_DIR));
		}
		int idx = sCombo.indexOf(choice);
		if (idx > -1) sCombo.select(idx);
	}

	private Map<String, Object> semanticFiles() {
		if (delta.getBoolean(PrefsKey.EDITOR_SEMANTIC_EXTERNAL_ENABLE)) {
			return choices(PrefsKey.EDITOR_SEMANTIC_EXTERNAL_DIR);
		}
		return choices(PrefsKey.EDITOR_SEMANTIC_INTERNAL_DIR);
	}

	protected Composite createPreviewStylesGroup(Composite parent) {
		Composite comp = SWTFactory.createGroupComposite(parent, 3, 2, "Html Preview/Export Styles");

		pCombo = addLabeledCombo(comp, "Select", PrefsKey.EDITOR_PREVIEW_FILE, previewFiles());

		addVerticalSpace(comp, 1, 3);

		addCheckBox(comp, "Use external stylesheet", PrefsKey.EDITOR_PREVIEW_EXTERNAL_ENABLE, 2, 0)
				.addSelectionListener(new SelectionAdapter() {

					@Override
					public void widgetSelected(SelectionEvent e) {
						adjustPreviewSelection(((Button) e.widget).getSelection());
					}
				});

		pComp = SWTFactory.createComposite(comp, 2, 2, 4);
		addDirectoryField(pComp, PrefsKey.EDITOR_PREVIEW_EXTERNAL_DIR, 2, 2, "Directory", 50,
				new DirectorypathValidator(null, true));

		adjustPreviewSelection(delta.getBoolean(PrefsKey.EDITOR_PREVIEW_EXTERNAL_ENABLE));
		return comp;
	}

	private void adjustPreviewSelection(boolean external) {
		String choice = delta.getString(PrefsKey.EDITOR_PREVIEW_FILE);
		File f = new File(choice);
		choice = f.getName();
		enableControls(pComp, external);
		if (external) {
			updateCombo(pCombo, choices(PrefsKey.EDITOR_PREVIEW_EXTERNAL_DIR));
		} else {
			updateCombo(pCombo, choices(PrefsKey.EDITOR_PREVIEW_INTERNAL_DIR));
		}
		int idx = pCombo.indexOf(choice);
		if (idx > -1) pCombo.select(idx);
	}

	private Map<String, Object> previewFiles() {
		if (delta.getBoolean(PrefsKey.EDITOR_PREVIEW_EXTERNAL_ENABLE)) {
			return choices(PrefsKey.EDITOR_PREVIEW_EXTERNAL_DIR);
		}
		return choices(PrefsKey.EDITOR_PREVIEW_INTERNAL_DIR);
	}

	private void enableControls(Composite comp, boolean enable) {
		for (Control control : comp.getChildren()) {
			if (control instanceof Composite) {
				enableControls((Composite) control, enable);
			} else {
				control.setEnabled(enable);
			}
		}
	}

	private Map<String, Object> choices(String key) {
		Map<String, Object> map = new HashMap<>();
		final File root = dir(delta.bind(key));
		if (root == null || !root.isDirectory()) return map;

		for (String filename : root.list()) {
			if (filename.endsWith(PrefsKey.DOT_CSS) && !filename.endsWith(PrefsKey.DOT_MIN_CSS)) {
				map.put(filename, root.toURI().resolve(filename).toString());
			}
		}
		return map;
	}

	private File dir(String key) {
		String dir = delta.getString(key);
		if (dir.isEmpty()) return null;

		try {
			URI uri = new URI(dir);
			return new File(uri);
		} catch (URISyntaxException e) {
			Log.error(this, "Invalid URI: " + dir);
			return null;
		}
	}
}
