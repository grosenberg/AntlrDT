package net.certiv.antlrdt.ui.preferences.page;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.AntlrDTSimpleSourceViewerConfiguration;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.IColorManager;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.formatter.DslFormatterPreferencePage;

/** Preference page for formatting */
public class PrefPageFormatter extends DslFormatterPreferencePage {

	public PrefPageFormatter() {
		super();
		DslPrefsManagerDelta delta = (DslPrefsManagerDelta) getDslUI().getFormatterFactory().getPrefsManager();
		setPreferenceStore(delta);
	}

	@Override
	protected DslSourceViewerConfiguration createSimpleSourceViewerConfiguration(IColorManager colorManager,
			IPreferenceStore store, ITextEditor editor, boolean configureFormatter) {

		return new AntlrDTSimpleSourceViewerConfiguration(colorManager, (IDslPrefsManager) store, editor,
				Partitions.ANTLRDT_PARTITIONING, configureFormatter);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}
}
