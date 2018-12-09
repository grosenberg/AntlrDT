package net.certiv.antlrdt.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.handlers.HandlerUtil;
import org.eclipse.ui.ide.IDE;

import net.certiv.antlrdt.ui.graph.NullEditorInput;
import net.certiv.antlrdt.ui.graph.paths.PathsEditor;
import net.certiv.dsl.core.log.Log;

public class OpenPathsEditorHandler extends AbstractHandler {

	public OpenPathsEditorHandler() {}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		IWorkbenchPage page = HandlerUtil.getActiveWorkbenchWindow(event).getActivePage();
		openPathsEditor(page);
		return null;
	}

	private PathsEditor openPathsEditor(IWorkbenchPage page) {
		PathsEditor editor = findEditor(page);
		if (editor != null) return editor;
		try {
			return (PathsEditor) IDE.openEditor(page, new NullEditorInput(""), PathsEditor.ID);
		} catch (Exception e) {
			Log.error(this, "Failed to open PathsEditor", e);
		}
		return null;
	}

	private PathsEditor findEditor(IWorkbenchPage page) {
		if (page != null) {
			IEditorReference[] edRefs = page.getEditorReferences();
			for (IEditorReference ref : edRefs) {
				if (ref.getId().equals(PathsEditor.ID)) {
					return (PathsEditor) ref.getEditor(true);
				}
			}
		}
		return null;
	}
}
