package net.certiv.antlrdt.ui;

import org.antlr.v4.runtime.Token;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.ui.view.tokens.Source;
import net.certiv.dsl.core.util.Log;

public class AntlrDTUtil {

	public static void openSourceFileAtToken(Source source, Token token) {
		ITextEditor editor = openSourceFile(source);
		if (editor != null) { // handle funny way Antlr defines indexes
			int len = token.getStopIndex() - token.getStartIndex() + 1;
			setEditorPosition(editor, token.getLine(), token.getCharPositionInLine(), len);
		}
	}

	private static ITextEditor openSourceFile(Source source) {
		IWorkbenchWindow window = AntlrDTUI.getDefault().getWorkbench().getActiveWorkbenchWindow();
		IWorkbenchPage page = window.getActivePage();
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile file = workspaceRoot.getFileForLocation(new Path(source.getPathname()));
		if (file != null && file.exists() && file.getType() == IResource.FILE) {
			try {
				return (ITextEditor) IDE.openEditor(page, file);
			} catch (PartInitException e) {
				String msg = "Failed to open project editor on: " + file.getName();
				Log.error(AntlrDTUtil.class, msg, e);
			}
		} else {
			IFileStore fileStore = EFS.getLocalFileSystem().getStore(new Path(source.getPathname()));
			if (!fileStore.fetchInfo().isDirectory() && fileStore.fetchInfo().exists()) {
				try {
					return (ITextEditor) IDE.openEditorOnFileStore(page, fileStore);
				} catch (PartInitException e) {
					String msg = "Failed to open file-delta editor on: " + fileStore.getName();
					Log.error(AntlrDTUtil.class, msg, e);
				}
			}
		}
		return null;
	}

	private static void setEditorPosition(ITextEditor editor, int line, int offset, int len) {
		try {
			if (line > 0) { // put the caret at lead point/correct offset
				IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
				int beg = document.getLineOffset(line - 1) + offset + len;
				editor.selectAndReveal(beg, len * -1);
			}
		} catch (BadLocationException e) {
			Log.error(AntlrDTUtil.class, "Reveal failed", e);
		}
	}
}
