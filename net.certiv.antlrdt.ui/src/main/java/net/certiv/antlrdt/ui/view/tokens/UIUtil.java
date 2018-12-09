package net.certiv.antlrdt.ui.view.tokens;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jdt.core.IMember;
import org.eclipse.jdt.core.IPackageDeclaration;
import org.eclipse.jdt.core.IType;
import org.eclipse.jdt.core.JavaModelException;
import org.eclipse.jdt.core.compiler.ITerminalSymbols;
import org.eclipse.jdt.internal.corext.util.JavaModelUtil;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.ui.DslUI;

@SuppressWarnings("restriction")
public class UIUtil {

	/** sets the platform line separator */
	public static final String eol = System.getProperty("line.separator");
	private static final int START_VALUE = 2048;

	private UIUtil() {
		super();
	}

	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Why not use?:<br />
	 * int lnNum = doc.getLineOfOffset(offset); <br />
	 * int lnBeg = doc.getLineOffset(lnNum); <br />
	 * String prefix = doc.get(lnBeg, offset - lnBeg);
	 *
	 * @param doc
	 * @param offset
	 * @return
	 */
	public static String determinePrefix(IDocument doc, int offset) {
		try {
			IRegion region = doc.getLineInformationOfOffset(offset);
			int beg = region.getOffset();
			int len = region.getLength();
			for (int idx = beg; idx < beg + len; idx++) {
				char c = doc.getChar(idx);
				if (!Character.isWhitespace(c)) {
					String ws = doc.get(beg, idx - beg);
					return ws;
				}
			}
		} catch (BadLocationException e) {
			Log.error(UIUtil.class, "Could not determine prefix", e);
		}
		return "";
	}

	/**
	 * Detect the (apparent) indentation level of the JavaDoc comment body.
	 *
	 * @param prefix the leading text on the first line of the conment
	 * @return the determined indentation level
	 */
	public static int determineIndentLevel(String prefix) {
		int level = 0;
		if (prefix.length() == 0) return level;
		for (int i = 0; i < prefix.length(); i++) {
			if (prefix.charAt(i) == '\t') level++;
		}
		if (level > 0) return level;
		int len = prefix.length();
		if (len % 4 == 0) {
			level = (len / 4);
		} else if (len % 2 == 0) {
			level = (len / 2);
		}
		return level;
	}

	public static String fetch(IDocument cuDocument, int offset, int length) {
		try {
			return cuDocument.get(offset, length);
		} catch (BadLocationException e) {
			Log.error(UIUtil.class, "Failed to locate javadoc");
		}
		return null;
	}

	public static IDocument getActiveDocument() {
		IEditorPart activeEditor = CoreUtil.getActiveEditor();
		if (activeEditor == null || !(activeEditor instanceof ITextEditor)) return null;
		IDocumentProvider docProvider = ((ITextEditor) activeEditor).getDocumentProvider();
		if (docProvider == null) return null;
		IEditorInput editorInput = activeEditor.getEditorInput();
		if (editorInput == null) return null;
		return docProvider.getDocument(editorInput);
	}

	public static ICompilationUnit getCompilationUnit(ITextEditor editor) {
		IEditorInput editorInput = editor.getEditorInput();
		if (editorInput instanceof ICompilationUnit) return (ICompilationUnit) editorInput;
		ICompilationUnit cu = JavaUI.getWorkingCopyManager().getWorkingCopy(editorInput);
		return cu;
	}

	public static IJavaElement getElementAt(ITextEditor cuEditor, int offset) {
		ICompilationUnit unit = getCompilationUnit(cuEditor);
		if (unit != null) {
			try {
				if (!unit.isConsistent()) {
					unit.reconcile(ICompilationUnit.NO_AST, false, null, null);
				}
				if (unit.isConsistent()) {
					IJavaElement element = unit.getElementAt(offset);
					if (element != null) return element;

					element = unit.getChildren()[0];
					if (element instanceof IPackageDeclaration) {
						int start = ((IPackageDeclaration) element).getSourceRange().getOffset();
						if (offset < start) return element;
					}
				} else {
					Log.error(UIUtil.class, "Compilation unit is not consistent; failed to reconcile");
				}
			} catch (JavaModelException jme) {
				if (!jme.isDoesNotExist()) {
					Log.error(UIUtil.class, "Element not found", jme);
				}
			}
		}
		return null;
	}

	// /**
	// * Installs a comment block into the JDT editor. Will replace an existing comment if
	// * length is greater than zero.
	// *
	// * @param bufText
	// * @param cuEditor
	// * @param doc
	// * @param offset
	// * @param length
	// */
	// public static void restore(String bufText, ITextEditor cuEditor, IDocument doc, int
	// offset, int length) {
	// String prefix = determinePrefix(doc, offset);
	// int indentLevel = determineIndentLevel(prefix);
	// String docText = format(bufText.trim(), indentLevel);
	// try {
	// int len = length;
	// if (len > 0) len = doc.get(offset, length).lastIndexOf('/') + 1;
	// doc.replace(offset, len, docText);
	// } catch (BadLocationException e) {
	// Log.error(UIUtil.class, "Could not restore JavaDoc content", e);
	// }
	//
	// ICompilationUnit cuUnit = getCompilationUnit(cuEditor);
	// try {
	// cuUnit.reconcile(ICompilationUnit.NO_AST, false, null, null);
	// } catch (JavaModelException e) {
	// Log.error(UIUtil.class, "Failed to reconcile on restore to Editor", e);
	// }
	// }

	// ///////////////////////////////////////////////////////////////////////

	/**
	 * Determines the fully qualified name for a given type signature.
	 *
	 * @param javaSel the java element context
	 * @param refTypeSig an unqualified type signature
	 * @return the fully qualified name for the given type signature
	 */
	public static String resolveSelectedType(IJavaElement javaSel, String refTypeSig) {
		if (javaSel instanceof IMember) {
			IMember member = (IMember) javaSel;
			IType declaringType = member.getDeclaringType();
			if (javaSel instanceof IType) declaringType = (IType) javaSel;
			try {
				return JavaModelUtil.getResolvedTypeName(refTypeSig, declaringType);
			} catch (JavaModelException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	/**
	 * Determine the element selected in the Outline View.
	 *
	 * @param selection a structured selection
	 * @return the IJavaElement selected
	 */
	public static IJavaElement getSelectedElement(ISelection selection) {
		if (selection instanceof IStructuredSelection) {
			Object element = ((IStructuredSelection) selection).getFirstElement();
			if (element instanceof IJavaElement) {
				return (IJavaElement) element;
			}
		}
		Log.error(UIUtil.class, "Unstructured selection");
		return null;
	}

	// ///////////////////////////////////////////////////////////////////////

	public static boolean isComment(int token) {
		return isCommentJavadoc(token) || isCommentMultiLine(token) || isCommentSingleLine(token);
	}

	public static boolean isCommentSingleLine(int token) {
		return token == ITerminalSymbols.TokenNameCOMMENT_LINE;
	}

	public static boolean isCommentMultiLine(int token) {
		return token == ITerminalSymbols.TokenNameCOMMENT_BLOCK;
	}

	public static boolean isCommentJavadoc(int token) {
		return token == ITerminalSymbols.TokenNameCOMMENT_JAVADOC;
	}

	public static String[] linesFromFile(File file) throws CoreException {
		ArrayList<String> lines = new ArrayList<>();
		try (BufferedReader in = new BufferedReader(new FileReader(file))) {
			for (String str = in.readLine(); str != null; str = in.readLine()) {
				lines.add(str);
			}
		} catch (Exception e) {
			Status status = new Status(IStatus.ERROR, DslUI.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
			throw new CoreException(status);
		}
		return lines.toArray(new String[lines.size()]);
	}

	/**
	 * Retrieve the contents of a flat file.
	 *
	 * @param file
	 * @return file contents or and empty string
	 */
	public static String stringFromFile(IFile file) throws CoreException {
		try (InputStream is = file.getContents()) {
			return stringFromInputStream(is, null);
		} catch (CoreException | IOException e) {}
		return "";
	}

	/**
	 * Retrieve the contents of a flat file.
	 *
	 * @param file
	 * @return
	 * @throws CoreException
	 */
	public static String stringFromFile(File file) throws CoreException {
		try (FileInputStream is = new FileInputStream(file)) {
			return stringFromInputStream(is, null);
		} catch (Exception e) {}
		return "";
	}

	/**
	 * Returns a String from an InputStream that is aware of its encoding. If the encoding is
	 * {@code null} it sets the platforms default encoding ( {@code ResourcesPlugin.getEncoding()}).
	 *
	 * @param stream
	 * @return the content
	 */
	public static String stringFromInputStream(InputStream stream, String encoding) throws CoreException {
		StringBuilder sb = new StringBuilder(2048);
		try (BufferedInputStream is = new BufferedInputStream(stream);
				InputStreamReader in = new InputStreamReader(is,
						encoding == null ? ResourcesPlugin.getEncoding() : encoding);) {
			char[] readBuffer = new char[START_VALUE];
			int n = in.read(readBuffer);
			while (n > 0) {
				sb.append(readBuffer, 0, n);
				n = in.read(readBuffer);
			}
		} catch (Exception e) {
			Status status = new Status(IStatus.ERROR, DslUI.PLUGIN_ID, IStatus.OK, e.getMessage(), e);
			throw new CoreException(status);
		}
		return sb.toString();
	}
}
