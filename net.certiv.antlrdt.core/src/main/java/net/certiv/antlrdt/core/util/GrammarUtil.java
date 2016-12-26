package net.certiv.antlrdt.core.util;

import java.util.Scanner;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IFile;

public class GrammarUtil {

	private static final int flags = Pattern.DOTALL | Pattern.MULTILINE;
	private static final Pattern optionsBlockPattern = Pattern.compile("options\\s*\\{.*?\\}", flags);

	private GrammarUtil() {}

	/**
	 * Determine the value assigned to the ASTLabelType key in the options block of a grammar.
	 * 
	 * @param gFile the grammar file
	 * @return the ASTLabelType value
	 */
	@Deprecated
	public static String determineASTLabelType(IFile gFile) {
		String astLabelType = null;
		if (gFile != null) {
			try (Scanner scan = new Scanner(gFile.getContents())) {
				if (scan != null) {
					String block = scan.findWithinHorizon(optionsBlockPattern, 0);
					if (block != null) {
						int beg = block.indexOf("ASTLabelType");
						if (beg != -1) {
							beg = block.indexOf('=', beg);
							if (beg != -1) {
								beg++;
								int end = block.indexOf(';', beg);
								astLabelType = block.substring(beg, end).trim();
							}
						}
					}
				}
			} catch (Exception e) {}
		}
		return astLabelType;
	}

	// ///////////////////////////////////////////////////////////////////////
	// // Active series - operate on the current active editor by default ////

	// public static IDocument getActiveGrammarDocument() {
	// IEditorPart activeEditor = CoreUtil.getActiveEditor();
	// if (activeEditor != null && activeEditor instanceof DslEditor) {
	// IEditorInput input = activeEditor.getEditorInput();
	// if (input != null) {
	// return (((DslEditor) activeEditor).getDocumentProvider()).getDocument(input);
	// }
	// }
	// return null;
	// }

	// /**
	// * Determines the folder that will contain the class binary compiled from the generated files
	// * corresponding to the grammar present in the currently active editor.
	// *
	// * @param editor current active editor containing a grammar file
	// * @param className grammar lexer or parser class name without extension
	// * @return array containing: folder that contains the generated lexer or parser class binary
	// and
	// * the package name of the file found
	// * @throws CoreException
	// */
	// public static String[] determineOutputClassPath(IEditorPart editor, String className)
	// throws CoreException {
	//
	// IFile file = getInputFile(editor);
	// IProject project = file.getProject();
	// IJavaProject jProj = null;
	// if (project instanceof IJavaProject) {
	// jProj = (IJavaProject) project;
	// } else {
	// jProj = JavaCore.create(project);
	// }
	//
	// String absOutputPath = CoreUtil.determineBuildFolder(file);
	// String wsLocation = org.eclipse.core.runtime.Platform.getLocation().toString();
	// String pjLocation = project.getName();
	// String relOutputPath = absOutputPath.substring(wsLocation.length() + 1 + pjLocation.length()
	// + 1);
	// if (!relOutputPath.endsWith("/")) relOutputPath += "/";
	//
	// // default output class path
	// String outputClassPath = wsLocation + jProj.getOutputLocation().toString();
	// String srcPath = wsLocation + jProj.getPath().toString();
	// String packageName = absOutputPath.substring(srcPath.length() + 1);
	// // now match to one specific to the source file
	// String member = relOutputPath + className + ".java";
	// IResource res = jProj.getProject().findMember(member);
	// if (res == null) {
	// String message = "Find failed [member=" + member + "]";
	// IStatus status = new Status(IStatus.ERROR, AntlrCorePlugin.PLUGIN_ID, IStatus.OK, message,
	// null);
	// throw new CoreException(status);
	// }
	//
	// if (jProj.getProject().isOpen()) {
	// IClasspathEntry entries[] = jProj.getRawClasspath();
	// for (IClasspathEntry classpathEntry : entries) {
	// if (classpathEntry.getEntryKind() == IClasspathEntry.CPE_SOURCE) {
	// IPath outputPath = classpathEntry.getOutputLocation();
	// if (outputPath == null) { // using default
	// outputPath = jProj.getOutputLocation();
	// }
	// if (classpathEntry.getPath().isPrefixOf(res.getFullPath())) {
	// outputClassPath = wsLocation + outputPath.toString();
	// srcPath = wsLocation + classpathEntry.getPath().toString();
	// packageName = absOutputPath.substring(srcPath.length() + 1);
	// break;
	// }
	// }
	// }
	// }
	// if (packageName.endsWith("/")) packageName = packageName.substring(0, packageName.length() -
	// 1);
	// packageName = packageName.replace('/', '.');
	// return new String[] { outputClassPath, packageName };
	// }

	// /**
	// * Determine the build folder for a given a resource representing a grammar file.
	// *
	// * @param resource typically the grammar IFile
	// * @return a filesystem absolute path to the build folder
	// */
	// public static String determineBuildFolder(IResource resource) {
	// IProject project = resource.getProject();
	// String choice = Prefs.getString(PrefsKey.BUILDER_USE, project);
	// // default is current
	// String outputPath = resource.getParent().getLocation().toPortableString();
	// if (choice.equals(PrefsKey.BUILDER_USE_GRAMMAR)) {
	// String folderPath = AntlrModuleDeclaration.packagePath;
	// if (folderPath != null && folderPath.length() > 0) {
	// IPath projFolder = project.getLocation();
	// IPath srcFolder = determineSourceFolder(resource);
	// projFolder = projFolder.append(srcFolder);
	// projFolder = projFolder.append(folderPath.replaceAll("\\.", "/"));
	// outputPath = projFolder.toPortableString();
	// }
	// } else if (choice.equals(PrefsKey.BUILDER_USE_RELATIVE)) {
	// String relPath = Prefs.getString(PrefsKey.BUILDER_REL_PATH, project);
	// outputPath = resource.getParent().getLocation().append(relPath).toPortableString();
	// } else if (choice.equals(PrefsKey.BUILDER_USE_ABSOLUTE)) {
	// String absPath = Prefs.getString(PrefsKey.BUILDER_ABS_PATH, project);
	// if (absPath != null && absPath.length() != 0) {
	// outputPath = absPath;
	// }
	// }
	// return outputPath;
	// }
}
