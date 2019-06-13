package net.certiv.antlrdt.vis.parse;

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

import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.editor.AntlrDTEditor;
import net.certiv.antlrdt.vis.model.TreeModel;
import net.certiv.antlrdt.vis.views.tokens.Source;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.ui.DslUI;

public class TargetBuilder {

	private final BuildJob buildJob = new BuildJob("Parse Tree Builder");

	private AntlrDTEditor editor;
	private GrammarRecord record;
	private Source source;

	private long timeout;	// ms for parser execution and tree walk
	private TargetUnit target;

	public TargetBuilder(AntlrDTEditor editor, GrammarRecord record, Source source) {
		this.editor = editor;
		this.record = record;
		this.source = source;

		timeout = editor.getPrefsMgr().getLong(PrefsKey.PARSE_TIMEOUT);

		buildJob.setPriority(Job.BUILD);
		buildJob.setSystem(true);
	}

	public AntlrDTEditor getEditor() {
		return editor;
	}

	public boolean isValid() {
		return getTree() != null;
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

	public List<ErrorRecord> getErrorList() {
		return target != null ? target.getErrors() : null;
	}

	public List<String[]> getTrace() {
		return target != null ? target.getTrace() : Collections.emptyList();
	}

	public String getMainRuleName() {
		return target != null ? target.getMainRuleName() : "";
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

	public void addJobChangeListener(JobChangeAdapter jobChangeAdapter) {
		buildJob.addJobChangeListener(jobChangeAdapter);
	}

	public void schedule() {
		buildJob.schedule();
	}

	private class BuildJob extends Job {

		public BuildJob(String name) {
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
				String sourceText = source.getContent();
				target = new TargetUnit(record, grammar, sourceText);
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
