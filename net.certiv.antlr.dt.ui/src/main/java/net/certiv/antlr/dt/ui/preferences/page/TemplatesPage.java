package net.certiv.antlr.dt.ui.preferences.page;

import org.eclipse.jface.text.IDocument;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.editor.AntlrSimpleSourceViewerConfiguration;
import net.certiv.antlr.dt.ui.editor.AntlrSourceViewerConfiguration;
import net.certiv.antlr.dt.ui.editor.Partitions;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.IColorManager;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.core.preferences.PrefsDeltaManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.DslTextTools;
import net.certiv.dsl.ui.preferences.pages.DslTemplatePreferencePage;
import net.certiv.dsl.ui.templates.CompletionManager;

public class TemplatesPage extends DslTemplatePreferencePage {

	public TemplatesPage() {
		super();
		PrefsDeltaManager delta = AntlrCore.getDefault().getPrefsManager().createDeltaManager();
		setPreferenceStore(delta);
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
	protected AntlrSourceViewerConfiguration createSourceViewerConfiguration() {
		return new AntlrSimpleSourceViewerConfiguration(getColorManager(), (IPrefsManager) getPreferenceStore(), null,
				Partitions.PARTITIONING, false);
	}

	@Override
	protected void setDocumentPartitioner(IDocument document) {
		getTextTools().setupDocumentPartitioner(document, Partitions.PARTITIONING);
	}

	@Override
	protected CompletionManager getCompletionMgr() {
		return AntlrUI.getDefault().getCompletionMgr();
	}

	private IColorManager getColorManager() {
		return AntlrCore.getDefault().getColorManager();
	}

	private DslTextTools getTextTools() {
		return AntlrUI.getDefault().getTextTools();
	}
}
