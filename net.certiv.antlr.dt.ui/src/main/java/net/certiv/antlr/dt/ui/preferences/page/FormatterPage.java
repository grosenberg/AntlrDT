package net.certiv.antlr.dt.ui.preferences.page;

import org.eclipse.jface.preference.IPreferenceStore;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.editor.AntlrSimpleSourceViewerConfiguration;
import net.certiv.antlr.dt.ui.editor.Partitions;
import net.certiv.antlr.dt.ui.preferences.formatter.FormatterFactory;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.DslColorRegistry;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.DslEditor;
import net.certiv.dsl.ui.editor.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.formatter.IDslFormatterFactory;
import net.certiv.dsl.ui.preferences.pages.DslFormatterPreferencePage;

/** Preference page for formatting */
public class FormatterPage extends DslFormatterPreferencePage {

	private FormatterFactory factory;

	public FormatterPage() {
		super();

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
	protected DslSourceViewerConfiguration createSimpleSourceViewerConfiguration(DslColorRegistry colorManager,
			IPreferenceStore store, DslEditor editor, boolean configureFormatter) {

		return new AntlrSimpleSourceViewerConfiguration(colorManager, (IPrefsManager) store, editor,
				Partitions.PARTITIONING, configureFormatter);
	}

	@Override
	protected IDslFormatterFactory getFormatterFactory() {
		if (factory == null) {
			factory = new FormatterFactory();
		}
		return factory;
	}
}
