package net.certiv.antlr.dt.vis.parse;

import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.antlr.dt.vis.model.TreeModel;
import net.certiv.antlr.dt.vis.views.tokens.Source;
import net.certiv.dsl.core.console.ConsoleRecordFactory.ConsoleRecord;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.ui.DslUI;

public class TargetBuilder {

	private final TargetJob job = new TargetJob("Parse Tree Builder");

	private AntlrEditor editor;
	private TargetAssembly assembly;
	private Source source;

	private long timeout;	// ms for parser execution and tree walk
	private TargetUnit target;

	public TargetBuilder(AntlrEditor editor, TargetAssembly assembly, Source source) {
		this.editor = editor;
		this.assembly = assembly;
		this.source = source;

		timeout = editor.getPrefsMgr().getLong(PrefsKey.PARSE_TIMEOUT);
		job.setPriority(Job.SHORT);
		job.setSystem(true);
	}

	public AntlrEditor getEditor() {
		return editor;
	}

	public boolean isValid() {
		return target != null ? target.isValid() : false;
	}

	public TreeModel getModel() {
		return target != null ? target.getModel() : null;
	}

	public ParseTree getTree() {
		return target != null ? target.getTree() : null;
	}

	public List<Token> getTokens() {
		return target != null ? target.getTokens() : null;
	}

	public List<ProblemRecord> getProblems() {
		return target != null ? target.getProblems() : null;
	}

	public List<ConsoleRecord> getErrors() {
		return target != null ? target.getErrors() : null;
	}

	public List<String[]> getTrace() {
		return target != null ? target.getTrace() : Collections.emptyList();
	}

	public String getMainRuleName() {
		return target != null ? target.getMainRuleName() : Strings.EMPTY;
	}

	public String[] getRuleNames() {
		return target != null ? target.getRuleNames() : null;
	}

	public String[] getTokenNames() {
		return target != null ? target.getTokenNames() : null;
	}

	public String[] getModeNames() {
		return target != null ? target.getModeNames() : null;
	}

	public void addJobChangeListener(JobChangeAdapter adapter) {
		job.addJobChangeListener(adapter);
	}

	public void schedule() {
		job.schedule();
	}

	private class TargetJob extends Job {

		public TargetJob(String name) {
			super(name);
		}

		private Timer startTimer(final IProgressMonitor monitor, final Thread thread) {
			Timer timer = new Timer();
			timer.schedule(new TimerTask() {

				@Override
				public void run() {
					Log.warn(this, "Target parser generation timer expired " + (target != null));
					if (target != null) target.terminate();
				}
			}, timeout);
			return timer;
		}

		@Override
		protected IStatus run(IProgressMonitor monitor) {
			if (monitor.isCanceled()) return Status.CANCEL_STATUS;

			Timer timer = null;
			try {
				timer = startTimer(monitor, getThread());
				IFile grammar = CoreUtil.getInputFile(editor);
				target = new TargetUnit(assembly, grammar, source.getContent());
				target.exec();

			} finally {
				if (timer != null) timer.cancel();
			}

			if (getTree() == null) {
				IStatus status = new Status(IStatus.CANCEL, DslUI.PLUGIN_ID, "No tree generated");
				return status;
			}
			return Status.OK_STATUS;
		}
	}
}
