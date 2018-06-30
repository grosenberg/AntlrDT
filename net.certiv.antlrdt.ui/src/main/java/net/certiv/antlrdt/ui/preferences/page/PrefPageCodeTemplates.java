package net.certiv.antlrdt.ui.preferences.page;

import org.eclipse.jface.text.IDocument;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.AntlrDTSimpleSourceViewerConfiguration;
import net.certiv.antlrdt.ui.editor.AntlrDTSourceViewerConfiguration;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.antlrdt.ui.templates.AntlrDTTemplateAccess;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.IColorManager;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.DslTextTools;
import net.certiv.dsl.ui.templates.DslTemplateAccess;
import net.certiv.dsl.ui.templates.DslTemplatePreferencePage;

public class PrefPageCodeTemplates extends DslTemplatePreferencePage {

	public PrefPageCodeTemplates() {
		super();
		DslPrefsManagerDelta delta = AntlrDTCore.getDefault().getPrefsManager().createDeltaManager();
		delta.setDefaultProject(null);
		setPreferenceStore(delta);
	}

	protected AntlrDTSourceViewerConfiguration createSourceViewerConfiguration() {
		return new AntlrDTSimpleSourceViewerConfiguration(getColorManager(), (IDslPrefsManager) getPreferenceStore(),
				null, Partitions.ANTLRDT_PARTITIONING, false);
	}

	protected void setDocumentPartitioner(IDocument document) {
		getTextTools().setupDocumentPartitioner(document, Partitions.ANTLRDT_PARTITIONING);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	private IColorManager getColorManager() {
		return AntlrDTCore.getDefault().getColorManager();
	}

	private DslTextTools getTextTools() {
		return AntlrDTUI.getDefault().getTextTools();
	}

	@Override
	protected DslTemplateAccess getTemplateAccess() {
		return AntlrDTTemplateAccess.getInstance();
	}
}
