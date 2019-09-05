package net.certiv.antlrdt.core.builder;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.antlr.v4.Tool;
import org.antlr.v4.tool.Grammar;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
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
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.text.edits.TextEdit;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.builder.Cause;
import net.certiv.dsl.core.builder.DslBuilder;
import net.certiv.dsl.core.builder.ToolErrorListener;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.preferences.consts.Builder;
import net.certiv.dsl.core.util.CoreUtil;

public class AntlrDTBuilder extends DslBuilder {

	private static final String TASK = "Antlr build";

	public static final Comparator<ICodeUnit> NameComp = new Comparator<ICodeUnit>() {

		@Override
		public int compare(ICodeUnit u1, ICodeUnit u2) {
			return u1.getElementName().compareTo(u2.getElementName());
		}
	};

	public AntlrDTBuilder() {
		super();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	@Override
	public String taskId() {
		return TASK;
	}

	@Override
	protected IStatus buildUnits(List<ICodeUnit> units, IProgressMonitor monitor, int ticks) throws CoreException {
		if (units.isEmpty()) return Status.OK_STATUS;

		try {
			monitor.beginTask(TASK, WORK_BUILD);
			units.sort(NameComp);
			for (ICodeUnit unit : units) {
				DslParseRecord record = unit.getParseRecord();
				record.getCollector().beginCollecting(unit.getResource(), record.markerId);
				compileGrammar(unit, CoreUtil.subMonitorFor(monitor, WORK_BUILD));
				record.getCollector().endCollecting();
			}
			return Status.OK_STATUS;

		} finally {
			monitor.done();
		}
	}

	private void compileGrammar(ICodeUnit unit, IProgressMonitor monitor) {
		try {
			String pathname = unit.getLocation().toString();
			IPath output = determineBuildPath(unit);
			if (output == null) {
				explain(Cause.PATH, unit.getPath().toString());
				CoreUtil.showStatusLineMessage("No build  " + unit.getPath().toString(), false);
				return;
			}

			monitor.worked(1);

			Tool tool = new Tool(new String[] { "-visitor", "-o", output.toString() });
			tool.removeListeners();
			tool.addListener(new ToolErrorListener(unit.getParseRecord()));
			monitor.worked(1);

			// Prep and process the grammar file
			Grammar g = tool.loadGrammar(pathname);
			tool.process(g, true); // NOTE: can throw execeptions based on grammar eval
			CoreUtil.showStatusLineMessage("Built " + unit.getPath().toString(), false);
			monitor.worked(1);

			IPath folder = unit.getProject().getLocation().append(unit.getSourceRoot()).append(output);
			postCompileCleanup(unit, folder, monitor);
			monitor.worked(1);

		} catch (Exception | Error e) {
			explain(Cause.UNIT, unit.getPath().toString());
			CoreUtil.showStatusLineMessage("Build failed " + unit.getPath().toString(), false);
		}
	}

	/**
	 * Returns an output build path for the given grammar code unit. The build path
	 * will be a filesystem absolute path or {@code null}. A {@code null} return
	 * indicates that no build will occur.
	 *
	 * @param file the grammar IFile
	 * @return a filesystem absolute path to the build folder or {@code null}
	 */
	private IPath determineBuildPath(ICodeUnit unit) {
		IPath buildPath = null;
		if (mgr.onSourceBuildPath(unit)) {
			buildPath = mgr.resolveGrammarPackagePath(unit);
			if (buildPath == null) {
				buildPath = mgr.getSourceBuildOutputPath(unit);
			}
			if (buildPath != null) {
				buildPath = unit.getProject().getLocation().append(unit.getSourceRoot()).append(buildPath);
				Log.info(this, String.format("Build path '%s' -> '%s'", unit.getProjectRelativePath(), buildPath));
			}
		}
		return buildPath;
	}

	// ----

	private void postCompileCleanup(ICodeUnit unit, IPath output, IProgressMonitor monitor) {
		if (!unit.exists()) {
			Log.error(this, "Compile produced no file[file=" + unit.getPath() + "]");
			return;
		}
		IProject project = unit.getProject();
		IContainer folder = CoreUtil.getWorkspaceRoot().getContainerForLocation(output);

		// refresh directory
		if (store.getBoolean(project, Builder.BUILDER_REFRESH)) {
			boolean markDerived = store.getBoolean(project, Builder.BUILDER_MARK_DERIVED);
			doBuilderRefresh(unit, folder, markDerived, monitor);
		}
		// format all
		if (store.getBoolean(project, Builder.BUILDER_FORMAT)) {
			doBuilderFormat(unit, folder, monitor);
		}
		// organize imports
		if (store.getBoolean(project, Builder.BUILDER_ORGANIZE)) {
			doBuilderOrganizeImports(unit, folder, monitor);
		}
	}

	private void doBuilderRefresh(ICodeUnit unit, IContainer folder, boolean markDerived, IProgressMonitor monitor) {
		try {
			if (folder != null) {
				folder.refreshLocal(IResource.DEPTH_ONE, monitor);
				if (markDerived) {
					String name = unit.getElementName();
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
			}
		} catch (CoreException e) {
			Log.error(this, "Refresh error: " + e.getMessage());
		}
		monitor.worked(1);
	}

	private void doBuilderFormat(ICodeUnit unit, IContainer folder, IProgressMonitor monitor) {
		IProject project = unit.getProject();
		IJavaProject javaProject = JavaCore.create(project);
		Map<String, String> options = javaProject.getOptions(true);
		CodeFormatter formatter = ToolFactory.createCodeFormatter(options);

		try {
			for (ICompilationUnit cu : getCompilationUnits(unit.getResource(), folder)) {
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

	private void doBuilderOrganizeImports(ICodeUnit unit, IContainer folder, IProgressMonitor monitor) {
		IChooseImportQuery query = new IChooseImportQuery() {

			@Override
			public TypeNameMatch[] chooseImports(TypeNameMatch[][] openChoices, ISourceRange[] ranges) {
				return null;
			}
		};

		try {
			for (ICompilationUnit cu : getCompilationUnits(unit.getResource(), folder)) {
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

	private List<ICompilationUnit> getCompilationUnits(IResource resource, IContainer folder) {
		List<ICompilationUnit> units = new ArrayList<>();
		IResource[] resources = new IResource[0];
		try {
			resources = folder.members();
		} catch (CoreException e) {}

		for (IResource res : resources) {
			try {
				if (res instanceof IFile) {
					ICompilationUnit cu = JavaCore.createCompilationUnitFrom((IFile) res);
					if (cu != null) units.add(cu);
				}
			} catch (IllegalArgumentException e) {}
		}
		return units;
	}
}
