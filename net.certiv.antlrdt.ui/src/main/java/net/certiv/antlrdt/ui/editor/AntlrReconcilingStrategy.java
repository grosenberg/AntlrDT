package net.certiv.antlrdt.ui.editor;

import org.eclipse.core.resources.IResource;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.reconcile.DslReconcilingStrategy;

public class AntlrReconcilingStrategy extends DslReconcilingStrategy {

	public AntlrReconcilingStrategy(ITextEditor editor, ISourceViewer viewer) {
		super(editor, viewer, AntlrDTCore.DSL_NAME);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
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
