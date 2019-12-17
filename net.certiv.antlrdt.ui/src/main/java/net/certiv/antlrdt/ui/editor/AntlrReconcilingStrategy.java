package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.core.lang.AntlrLangManager;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.reconcile.ReconcilingStrategy;

public class AntlrReconcilingStrategy extends ReconcilingStrategy {

	public AntlrReconcilingStrategy(ITextEditor editor, ISourceViewer viewer) {
		super(editor, viewer, AntlrLangManager.DSL_NAME);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}
}
