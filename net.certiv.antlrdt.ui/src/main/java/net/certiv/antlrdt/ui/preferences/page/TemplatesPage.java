package net.certiv.antlrdt.ui.preferences.page;

import org.eclipse.jface.text.IDocument;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.editor.AntlrSimpleSourceViewerConfiguration;
import net.certiv.antlrdt.ui.editor.AntlrSourceViewerConfiguration;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.IColorManager;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.DslTextTools;
import net.certiv.dsl.ui.preferences.pages.DslTemplatePreferencePage;
import net.certiv.dsl.ui.templates.CompletionManager;

public class TemplatesPage extends DslTemplatePreferencePage {

	public TemplatesPage() {
		super();
		DslPrefsManagerDelta delta = AntlrCore.getDefault().getPrefsManager().createDeltaManager();
		delta.setDefaultProject(null);
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
		return new AntlrSimpleSourceViewerConfiguration(getColorManager(), (IDslPrefsManager) getPreferenceStore(),
				null, Partitions.PARTITIONING, false);
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
