package net.certiv.antlr.dt.core.builder;

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

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.console.Aspect;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.builder.Cause;
import net.certiv.dsl.core.builder.DslBuilder;
import net.certiv.dsl.core.builder.ToolErrorListener;
import net.certiv.dsl.core.console.CS;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.ModelException;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.parser.problems.ProblemCollector;
import net.certiv.dsl.core.preferences.consts.Builder;
import net.certiv.dsl.core.util.Chars;
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
			if (!unit.isConsistent()) {
				changed.add(unit);
				changed.addAll(findRelated(unit, null));
			}
		}
		return new ArrayList<>(changed);
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

	private Throwable compileGrammar(ICodeUnit unit, IProgressMonitor monitor) throws ModelException {
		if (!lock(unit)) return null;

		try {
			IPath pathname = unit.getPath();
			String location = unit.getLocation().toString();

			DslParseRecord record = unit.getDefaultParseRecord();

			// check status of reconciler; proceed regardless
			if (!record.hasTree()) {
				report(CS.WARN, Cause.UNIT_ERR, srcName(unit, false), " no Reconciler tree");
			}

			IPath output = null;
			try {
				output = BuildUtil.resolveOutputPath(record);
			} catch (ModelException ex) {
				String msg = ex.getMessage();
				Throwable c = ex.getCause();
				if (c != null) msg = c.getMessage();
				report(CS.ERROR, Cause.SRC_ERRS, msg, srcName(unit, true));
			}
			monitor.worked(1);

			if (output == null) {
				report(CS.ERROR, Cause.PATH_ERR, srcName(unit, false), pathname);
				CoreUtil.showStatusLineMessage("No output path for  %s", pathname);
				return null;
			}

			IPath dest = output.makeRelativeTo(pathname);
			Tool tool = new Tool(new String[] { "-visitor", "-o", output.toString() });
			tool.removeListeners();
			tool.addListener(new ToolErrorListener(record));

			ProblemCollector collector = record.getCollector();
			monitor.worked(1);

			Throwable err = null;
			try {
				collector.beginCollecting(unit.getResource(), record.markerId);
				Grammar g = tool.loadGrammar(location); // pre-process grammar
				tool.process(g, true); 					// can throw on eval

			} catch (Exception | Error e) {
				err = e;
				report(CS.ERROR, Cause.BUILD_ERR, e.getMessage(), srcName(unit, false), dest);

			} finally {
				record.getCollector().endCollecting();
			}

			if (err != null || record.hasProblems()) {
				String failMsg = "Build error " + pathname.toString();
				CoreUtil.showStatusLineMessage(failMsg, true);
				int cnt = record.getErrorCnt() + record.getWarningCnt();
				if (cnt > 0) report(CS.ERROR, Cause.SRC_PRBM, cnt, srcName(unit, false));
				if (err != null) report(CS.ERROR, Cause.SRC_ERRS, err.getMessage(), srcName(unit, false));

			} else {
				String msg = "Built " + pathname.toString();
				CoreUtil.showStatusLineMessage(msg, false);
				report(CS.INFO, Cause.BUILT, srcName(unit, false), dest);
				postCompileCleanup(unit, output, monitor);
			}

			monitor.worked(1);
			return err;

		} finally {
			unit.unlock();
		}
	}

	@Override
	protected void report(CS kind, Cause cause, Object... args) {
		getDslCore().consoleAppend(Aspect.BUILDER, kind, cause.toString(), args);
	}

	@Override
	protected String destPackage(ICodeUnit unit) {
		String pkg = BuildUtil.grammarDefinedPackage(unit.getDefaultParseRecord());
		return pkg != null ? pkg : Strings.UNKNOWN;
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
					int dot = name.lastIndexOf(Chars.DOT);
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
							} else if (res.getName().equals(name + Strings.DOT + ext)) {
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
			Log.debug(this, "Ambiguous imports, organization skipped");
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
