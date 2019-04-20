package net.certiv.antlrdt.ui.editor;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.reconcile.DslReconcilingStrategy;

public class AntlrReconcilingStrategy extends DslReconcilingStrategy {

	public AntlrReconcilingStrategy(ITextEditor editor, ISourceViewer viewer) {
		super(editor, viewer, AntlrCore.DSL_NAME);
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
	public void initialReconcile() {
		deleteMarkers(true, IResource.DEPTH_INFINITE);
		reconcile(new Region(0, getRecord().doc.getLength()));
	}

	@Override
	public boolean incremental() {
		return false;
	}
}
