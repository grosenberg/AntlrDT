package net.certiv.antlrdt.core.builder;

import java.io.File;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import net.certiv.dsl.core.parser.problems.DslProblemCollector;
import net.certiv.dsl.core.preferences.consts.Builder;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Strings;

public class AntlrBuilder extends DslBuilder {

	private static final String TASK = "Antlr build";
	private static final String TOKENS = ".tokens";

	public static final Comparator<ICodeUnit> NameComp = new Comparator<ICodeUnit>() {

		@Override
		public int compare(ICodeUnit u1, ICodeUnit u2) {
			return u1.getElementName().compareTo(u2.getElementName());
		}
	};

	public AntlrBuilder() {
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
	protected List<ICodeUnit> filterBuildableUnits(List<ICodeUnit> units) {
		Set<ICodeUnit> changed = new HashSet<>();
		for (ICodeUnit unit : units) {
			if (isChanged(unit)) {
				changed.add(unit);
				changed.addAll(related(unit, null));
			}
		}
		return new ArrayList<>(changed);
	}

	private boolean isChanged(ICodeUnit unit) {
		IPath unitLoc = unit.getLocation();
		String ext = unitLoc.getFileExtension();
		String tokensName = unitLoc.lastSegment().replace(Strings.DOT + ext, TOKENS);

		IPath outPath = mgr.getBuildOutputPath(unit);
		IPath loc = unit.getDslProject().getLocation();
		File tokensFile = loc.append(outPath).append(tokensName).toFile();
		if (!tokensFile.exists()) return true;

		long unitTime = unitLoc.toFile().lastModified();
		long tokensTime = tokensFile.lastModified();
		boolean stale = unitTime > tokensTime;
		return stale;
	}

	@Override
	protected IStatus buildUnits(List<ICodeUnit> units, IProgressMonitor monitor, int ticks) throws CoreException {
		if (units.isEmpty()) return Status.OK_STATUS;

		try {
			monitor.beginTask(TASK, WORK_BUILD);
			units.sort(NameComp);
			for (ICodeUnit unit : units) {
				compileGrammar(unit, CoreUtil.subMonitorFor(monitor, WORK_BUILD));
			}
			return Status.OK_STATUS;

		} finally {
			monitor.done();
		}
	}

	private void compileGrammar(ICodeUnit unit, IProgressMonitor monitor) {
		IPath unitPath = unit.getPath();
		String pathname = unit.getLocation().toString();
		IPath output = determineBuildPath(unit);
		monitor.worked(1);

		if (output == null) {
			explain(Cause.PATH, unitPath.toString());
			CoreUtil.showStatusLineMessage("Skipped  " + unitPath.toString(), false);
			return;
		}

		IPath dest = output.makeRelativeTo(unitPath);
		Log.info(this, "Building %s -> %s", unitPath.lastSegment(), dest);

		DslParseRecord record = unit.getDefaultParseRecord();
		DslProblemCollector collector = record.getCollector();
		collector.beginCollecting(unit.getResource(), record.markerId);

		Tool tool = new Tool(new String[] { "-visitor", "-o", output.toString() });
		tool.removeListeners();
		tool.addListener(new ToolErrorListener(record));
		monitor.worked(1);

		Throwable err = null;
		try {
			Grammar g = tool.loadGrammar(pathname); // pre-process the grammar file
			tool.process(g, true); // can throw execeptions based on grammar eval
			record.getCollector().endCollecting();

		} catch (Exception | Error e) {
			record.getCollector().endCollecting();
			err = e;
		}

		if (err != null || record.hasProblems()) {
			String failMsg = "Build error " + unitPath.toString();
			CoreUtil.showStatusLineMessage(failMsg, true);
			if (err != null) Log.error(this, failMsg, err);
			if (record.hasProblems()) explain(Cause.ERRORS, unitPath.toString());
			monitor.worked(1);

		} else {
			String msg = "Built " + unitPath.toString();
			CoreUtil.showStatusLineMessage(msg, false);
			monitor.worked(1);

			IPath folder = unit.getProject().getLocation().append(unit.getSourceRoot()).append(output);
			postCompileCleanup(unit, folder, monitor);
			monitor.worked(1);
		}
	}

	/**
	 * Returns an output build path for the given grammar code unit. The build path
	 * will be a filesystem absolute path or {@code null}. A {@code null} return
	 * indicates that the given unit is not on a valid build path and, therefore, no
	 * the unit should not be built.
	 *
	 * @param file the grammar IFile
	 * @return a filesystem absolute path to the build folder or {@code null}
	 */
	private IPath determineBuildPath(ICodeUnit unit) {
		if (!mgr.onSourceBuildPath(unit)) return null;

		IPath buildPath = mgr.getGrammarInternalPackagePath(unit);
		if (buildPath != null) {
			buildPath = unit.getSourceRoot().append(buildPath);
		} else {
			buildPath = mgr.getBuildOutputPath(unit);
		}

		buildPath = unit.getProject().getLocation().append(buildPath);
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
							} else if (res.getName().equals(name + TOKENS)) {
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
