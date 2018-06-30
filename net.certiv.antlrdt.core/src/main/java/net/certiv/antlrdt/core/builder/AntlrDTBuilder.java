package net.certiv.antlrdt.core.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.Tool;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.tool.ANTLRMessage;
import org.antlr.v4.tool.Grammar;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.OperationCanceledException;
import org.eclipse.core.runtime.Status;
import org.eclipse.jdt.core.ICompilationUnit;
import org.eclipse.jdt.core.IJavaProject;
import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.manipulation.OrganizeImportsOperation;
import org.eclipse.jdt.core.manipulation.OrganizeImportsOperation.IChooseImportQuery;
import org.eclipse.jdt.core.search.TypeNameMatch;
import org.eclipse.jdt.internal.formatter.DefaultCodeFormatter;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.builder.DslBuilder;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.util.ErrorListener;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Log;
import net.certiv.v4.runtime.dsl.MsgUtil;

@SuppressWarnings("restriction")
public class AntlrDTBuilder extends DslBuilder {

	public static final String BUILDER_ID = "net.certiv.antlrdt.core.builder";
	public static final String MARKER_TYPE = "net.certiv.antlrdt.core.problemMarker";

	private static final int WORK_BUILD = 100;

	private IFile file; // the current .g4 file being edited, or null
	private IPath filepath;
	private IPath projpath;

	public AntlrDTBuilder() {
		super();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	public String getBuilderID() {
		return BUILDER_ID;
	}

	@Override
	public IStatus buildSourceModules(IProgressMonitor monitor, int ticks, List<IFile> srcModules)
			throws CoreException {

		if (!builderEnabled() || srcModules.isEmpty()) return null;

		try {
			monitor.beginTask(CoreUtil.EMPTY_STRING, WORK_BUILD);
			file = CoreUtil.getActiveDslFile(getDslCore().getDslFileExtensions());
			filepath = file != null ? file.getFullPath() : null;
			projpath = getProject().getFullPath();

			for (IFile module : srcModules) {
				IPath modpath = module.getFullPath();
				if (restrictToActiveProject() && !projpath.isPrefixOf(modpath)) continue;
				if (restrictToActiveProjectPath() && !modpath.equals(filepath)) continue;
				if (excludeIgnoredPaths(module)) continue;

				clearMarkers(module);
				try {
					compileGrammar(module, CoreUtil.subMonitorFor(monitor, WORK_BUILD));
				} catch (Exception e) {
					Log.error(this, "Failed to resolve build element", e);
				}
			}
			Log.debug(this, "Build done");
			return Status.OK_STATUS;
		} finally {
			monitor.done();
		}
	}

	private boolean excludeIgnoredPaths(IFile module) {
		if (resolvePackageName(module) == null) return true;
		IPath path = determineGeneratedSourcePath(module);
		if (path == null) return true;
		for (String segment : path.segments()) {
			if (segment.equals("ignore")) return true;
		}
		return false;
	}

	private void compileGrammar(IResource resource, IProgressMonitor monitor) {
		if (resource != null && resource instanceof IFile && (resource.getName().endsWith(".g4"))) {
			IFile file = (IFile) resource;

			try {
				String srcFile = file.getLocation().toPortableString();
				String outputDirectory = determineBuildFolder(file).toString();
				Log.info(this, "Build  [" + srcFile + "]");
				Log.info(this, "Output [" + outputDirectory + "]");
				monitor.worked(1);

				Tool tool = new Tool(new String[] { "-visitor", "-o", outputDirectory });
				tool.removeListeners();
				ErrorListener toolErrs = new ErrorListener();
				tool.addListener(toolErrs);
				monitor.worked(1);

				// Prep and process the grammar file
				Grammar g = tool.loadGrammar(srcFile);
				tool.process(g, true); // NOTE: can throw execeptions based on grammar eval
				monitor.worked(1);

				publishErrors(resource, toolErrs);
				postCompileCleanup(file, monitor);
				monitor.worked(1);
			} catch (Exception | Error e) {
				Log.error(this, "Build failed.", e);
			}
		}
	}

	private void publishErrors(IResource resource, ErrorListener toolErrs) {
		if (toolErrs.hasErrors()) {
			for (ANTLRMessage err : toolErrs.getErrList()) {
				Token token = MsgUtil.offendingToken(err);
				String msg = MsgUtil.displayMessage(err, token);
				createProblemMarker(resource, token, msg, IMarker.SEVERITY_ERROR);
				Log.error(this, err.toString());
			}
		}
		if (toolErrs.hasWarnings()) {
			for (ANTLRMessage err : toolErrs.getWarnList()) {
				Token token = MsgUtil.offendingToken(err);
				String msg = MsgUtil.displayMessage(err, token);
				createProblemMarker(resource, token, msg, IMarker.SEVERITY_WARNING);
				Log.warn(this, err.toString());
			}
		}
	}

	private void postCompileCleanup(IFile file, IProgressMonitor monitor) {
		if (!file.exists()) {
			Log.info(this, "Compile produced no file[file=" + file.getFullPath() + "]");
			return;
		}
		IProject project = file.getProject();

		// refresh directory
		if (getPrefs().getBoolean(project, PrefsKey.BUILDER_REFRESH)) {
			boolean markDerived = getPrefs().getBoolean(project, PrefsKey.BUILDER_MARK_DERIVED);
			doBuilderRefresh(file, markDerived, monitor);
		}
		// format all
		if (getPrefs().getBoolean(project, PrefsKey.BUILDER_FORMAT)) {
			doBuilderFormat(file, monitor);
		}
		// organize imports
		if (getPrefs().getBoolean(project, PrefsKey.BUILDER_ORGANIZE)) {
			doBuilderOrganizeImports(file, monitor);
		}
	}

	// ///////////////////////////////////////////////////////////////////////////////

	private void doBuilderRefresh(IFile file, boolean markDerived, IProgressMonitor monitor) {
		Log.info(this, "Refreshing...");
		IContainer folder = getBuildFolder(file);
		try {
			if (folder != null) {
				// refresh files
				folder.refreshLocal(IResource.DEPTH_ONE, monitor);
				if (markDerived) {
					// and set generated files as derived resources
					String name = file.getName();
					int dot = name.lastIndexOf('.');
					name = name.substring(0, dot);
					String ext = name.substring(dot + 1);
					for (IResource res : folder.members()) {
						if (res.getType() == IResource.FILE) {
							if (res.getFileExtension().startsWith("g4")) {
								continue;
							} else if (res.getName().equals(name + ".tokens")) {
								res.setDerived(true, monitor);
							} else if (res.getName().startsWith(name + "Parser")) {
								res.setDerived(true, monitor);
							} else if (res.getName().startsWith(name + "Lexer")) {
								res.setDerived(true, monitor);
							} else if (res.getName().equals(name + "." + ext)) {
								res.setDerived(true, monitor);
							}
						}
					}
				}
			} else {
				Log.error(this, "Failed to determine build folder; refreshing all");
				IProject project = file.getProject();
				project.refreshLocal(IResource.DEPTH_INFINITE, monitor);
			}
		} catch (CoreException e) {
			Log.error(this, "Failed to refresh");
		}
		monitor.worked(1);
	}

	private void doBuilderFormat(IFile file, IProgressMonitor monitor) {
		Log.info(this, "Formatting...");
		IContainer folder = getBuildFolder(file);
		IProject project = file.getProject();
		IJavaProject javaProject = JavaCore.create(project);
		Map<String, String> options = javaProject.getOptions(true);
		CodeFormatter formatter = ToolFactory.createCodeFormatter(options);

		try {
			ICompilationUnit[] cus = getCompilationUnits(file, folder);
			for (ICompilationUnit cu : cus) {
				monitor.worked(1);
				String content = cu.getSource();
				TextEdit textEdit = formatter.format(DefaultCodeFormatter.K_COMPILATION_UNIT, content, 0,
						content.length(), 0, null);

				if (textEdit != null) {
					IDocument document = new Document();
					document.set(content);
					textEdit.apply(document);
					IPackageFragment pack = (IPackageFragment) cu.getParent();
					String name = cu.getElementName();
					ICompilationUnit cuFormatted = pack.createCompilationUnit(name, document.get(), true, monitor);
					cuFormatted.save(monitor, true);
				}
			}
		} catch (Exception e) {
			Log.error(this, "Failed to Format");
		}
		monitor.worked(1);
	}

	private void doBuilderOrganizeImports(IFile file, IProgressMonitor monitor) {
		IContainer folder = getBuildFolder(file);
		IChooseImportQuery query = new IChooseImportQuery() {

			@Override
			public TypeNameMatch[] chooseImports(TypeNameMatch[][] openChoices, ISourceRange[] ranges) {
				return null;
			}
		};

		try {
			ICompilationUnit[] cus = getCompilationUnits(file, folder);
			for (ICompilationUnit cu : cus) {
				OrganizeImportsOperation op = new OrganizeImportsOperation(cu, null, true, true, true, query);
				op.run(monitor);
			}
		} catch (OperationCanceledException e) {
			Log.warn(this, "Ambiguous imports, organization cancelled");
		} catch (Exception e) {
			Log.warn(this, "Failed to Organize imports");
		}
		monitor.worked(1);
	}

	@SuppressWarnings("unused")
	private IPath getProjectPath(IResource res) {
		IPath workspacePath = res.getProject().getWorkspace().getRoot().getLocation();
		IPath projectPath = res.getProject().getFullPath();
		return workspacePath.append(projectPath);
	}

	private IContainer getBuildFolder(IResource resource) {
		IPath path = determineBuildFolder(resource);
		return containerOfPath(path);
	}

	private IContainer containerOfPath(IPath path) {
		return ResourcesPlugin.getWorkspace().getRoot().getContainerForLocation(path);
	}

	/**
	 * Determine the build folder for a given a resource representing a grammar file.
	 *
	 * @param resource typically the grammar IFile
	 * @return a filesystem absolute path to the build folder
	 */
	private IPath determineBuildFolder(IResource resource) {
		IPath grammarPath = determineGeneratedSourcePath(resource);
		IPath outputPath = resource.getProject().getLocation().append(grammarPath);
		return outputPath;
	}

	private IPath determineGeneratedSourcePath(IResource resource) {
		IPath workingPath = CoreUtil.determineSourceFolder(resource);
		if (workingPath == null) return null;
		String pkg = resolvePackageName(resource);
		if (pkg != null && !pkg.isEmpty()) {
			workingPath = workingPath.append(pkg.replaceAll("\\.", "/"));
		}
		return workingPath;
	}

	private String resolvePackageName(IResource resource) {
		ICodeUnit unit = getDslCore().getModelManager().create((IFile) resource);
		return unit.resolveParserPackageName();
	}

	private ICompilationUnit[] getCompilationUnits(IResource resource, IContainer folder) {
		ArrayList<ICompilationUnit> cuList = new ArrayList<>();
		IResource[] children = null;
		try {
			children = folder.members();
		} catch (CoreException e) {
			e.printStackTrace();
		}

		for (IResource child : children) {
			try {
				if (child instanceof IFile) {
					ICompilationUnit cu = JavaCore.createCompilationUnitFrom((IFile) child);
					if (cu != null) cuList.add(cu);
				}
			} catch (IllegalArgumentException e) {}
		}
		return cuList.toArray(new ICompilationUnit[cuList.size()]);
	}

	// private String buildDescription(IFile file) {
	// String name = file != null ? "proj=" + file.getProject().getName() + ", " : "";
	// return "[" + name + "time=" + new Date(System.currentTimeMillis()) + "]";
	// }
}
