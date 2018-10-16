package net.certiv.antlrdt.core.builder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.antlr.v4.Tool;
import org.antlr.v4.tool.Grammar;
import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
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
import org.eclipse.jdt.core.IPackageFragmentRoot;
import org.eclipse.jdt.core.ISourceRange;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.ToolFactory;
import org.eclipse.jdt.core.formatter.CodeFormatter;
import org.eclipse.jdt.core.manipulation.OrganizeImportsOperation;
import org.eclipse.jdt.core.manipulation.OrganizeImportsOperation.IChooseImportQuery;
import org.eclipse.jdt.core.search.TypeNameMatch;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.builder.DslBuilder;
import net.certiv.dsl.core.builder.ToolErrorListener;
import net.certiv.dsl.core.model.DslModelManager;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.preferences.consts.Builder;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.core.util.antlr.AntlrUtil;
import net.certiv.dsl.core.util.eclipse.JdtUtil;

public class AntlrDTBuilder extends DslBuilder {

	private final String prefix;

	public AntlrDTBuilder() {
		super();

		prefix = getDslCore().getProblemMakerId(AntlrDTCore.DSL_NAME);
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	public boolean isBuildAllowed(IProject project, IResource res) {
		if (requireCurrentProject() && !inCurrentProject(res)) return false;

		boolean inSrc = requireSourcePath() && inJavaSourceFolder(res);
		boolean inSpc = requireSpecialPath() && inSpecialFolder(res);

		if (inSrc || inSpc) return true;
		return false;
	}

	/** whether in a Java source folder */
	protected boolean inJavaSourceFolder(IResource res) {
		List<IPackageFragmentRoot> folders = JdtUtil.getJavaSourceFolders(res.getProject());
		for (IPackageFragmentRoot folder : folders) {
			if (folder.getPath().isPrefixOf(res.getFullPath())) return true;
		}
		return false;
	}

	// allow non-source path grammars?
	private boolean inSpecialFolder(IResource res) {
		return false;
	}

	@Override
	public IStatus buildSourceModules(IProgressMonitor monitor, int ticks, List<IFile> modules) throws CoreException {
		if (modules.isEmpty()) return Status.OK_STATUS;

		try {
			monitor.beginTask("AntlrDT Build", WORK_BUILD);
			for (IFile module : modules) {
				compileGrammar(module, CoreUtil.subMonitorFor(monitor, WORK_BUILD));
			}
			Log.debug(this, "Build done");
			return Status.OK_STATUS;

		} finally {
			monitor.done();
		}
	}

	private void compileGrammar(IResource resource, IProgressMonitor monitor) {
		if (resource != null && resource instanceof IFile && (resource.getName().endsWith(".g4"))) {
			IFile file = (IFile) resource;

			try {
				String srcFile = file.getLocation().toPortableString();
				String outputDirectory = determineBuildPath(file).toString();
				Log.info(this, "Build  [" + srcFile + "]");
				Log.info(this, "Output [" + outputDirectory + "]");
				monitor.worked(1);

				Tool tool = new Tool(new String[] { "-visitor", "-o", outputDirectory });
				tool.removeListeners();
				tool.addListener(new ToolErrorListener(file, prefix));
				monitor.worked(1);

				// Prep and process the grammar file
				Grammar g = tool.loadGrammar(srcFile);
				tool.process(g, true); // NOTE: can throw execeptions based on grammar eval
				monitor.worked(1);

				postCompileCleanup(file, monitor);
				monitor.worked(1);
			} catch (Exception | Error e) {
				Log.error(this, "Build failed.", e);
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
		if (getPrefs().getBoolean(project, Builder.BUILDER_REFRESH)) {
			boolean markDerived = getPrefs().getBoolean(project, Builder.BUILDER_MARK_DERIVED);
			doBuilderRefresh(file, markDerived, monitor);
		}
		// format all
		if (getPrefs().getBoolean(project, Builder.BUILDER_FORMAT)) {
			doBuilderFormat(file, monitor);
		}
		// organize imports
		if (getPrefs().getBoolean(project, Builder.BUILDER_ORGANIZE)) {
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
							if (res.getFileExtension().equals("g4")) {
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
				TextEdit textEdit = formatter.format(CodeFormatter.K_COMPILATION_UNIT, content, 0, content.length(), 0,
						null);

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

	private IContainer getBuildFolder(IFile file) {
		IPath path = determineBuildPath(file);
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
	private IPath determineBuildPath(IFile file) {
		IPath grammarPath = determineGeneratedSourcePath(file);
		IPath outputPath = file.getProject().getLocation().append(grammarPath);
		return outputPath;
	}

	private IPath determineGeneratedSourcePath(IFile file) {
		IPath workingPath = JdtUtil.determineSourceFolder(file);
		if (workingPath == null) return null;
		String pkg = resolvePackageName(file);
		if (pkg != null && !pkg.isEmpty()) {
			workingPath = workingPath.append(pkg.replaceAll("\\.", "/"));
		}
		return workingPath;
	}

	private String resolvePackageName(IFile file) {
		DslModelManager mgr = getDslCore().getModelManager();
		ICodeUnit unit = mgr.create(file);
		return AntlrUtil.resolvePackageName(unit);
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
}
