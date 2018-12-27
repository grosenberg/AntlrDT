package net.certiv.antlrdt.graph.util;

import org.antlr.v4.runtime.Token;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.antlrdt.graph.view.tokens.Source;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.util.CoreUtil;

public class EditorUtil {

	public static void openSourceFileAtRule(NodeModel model) {
		IFile file = model.getGrammar();
		ITextEditor editor = openSourceFile(file);
		if (editor != null) {
			int line = model.getNodeSourceLine();
			int offset = model.getNodeSourceOffsetInLine();
			int len = model.getNodeSourceLength();
			setEditorPosition(editor, line, offset, len);
		}
	}

	public static void openSourceFileAtToken(Source source, Token token) {
		ITextEditor editor = openSourceFile(source);
		if (editor != null) {
			int len = token.getStopIndex() - token.getStartIndex() + 1;
			setEditorPosition(editor, token.getLine(), token.getCharPositionInLine(), len);
		}
	}

	private static ITextEditor openSourceFile(Source source) {
		IFile file = CoreUtil.getWorkspaceRoot().getFileForLocation(new Path(source.getPathname()));
		return openSourceFile(file);
	}

	private static ITextEditor openSourceFile(IFile file) {
		if (file != null && file.exists() && file.getType() == IResource.FILE) {
			try {
				return (ITextEditor) IDE.openEditor(CoreUtil.getActivePage(), file);
			} catch (PartInitException e) {
				String msg = "Failed to open project editor on: " + file.getName();
				Log.error(EditorUtil.class, msg, e);
			}
		}
		return null;
	}

	private static void setEditorPosition(ITextEditor editor, int line, int offset, int len) {
		if (line < 1) return;
		try {
			IDocument document = editor.getDocumentProvider().getDocument(editor.getEditorInput());
			int beg = document.getLineOffset(line - 1) + offset + len;
			editor.selectAndReveal(beg, len * -1);
		} catch (BadLocationException e) {
			Log.error(EditorUtil.class, "Reveal failed", e);
		}
	}
}
