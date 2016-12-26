package net.certiv.antlrdt.ui.view;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;

import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.AntlrDTEditor;
import net.certiv.antlrdt.ui.graph.cst.ErrorRecord;
import net.certiv.antlrdt.ui.graph.cst.model.CstModel;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Log;

public class TargetBuilder {

	private AntlrDTEditor editor;
	private GrammarRecord record;
	private Source source;

	private TargetUnit target;
	private Job buildJob;

	public TargetBuilder(AntlrDTEditor editor, GrammarRecord record, Source source) {
		this.editor = editor;
		this.record = record;
		this.source = source;
		createJob();
	}

	public AntlrDTEditor getEditor() {
		return editor;
	}

	public CstModel getModel() {
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

	public ANTLRInputStream getInput() {
		return target != null ? target.getInput() : null;
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

	protected void addJobChangeListener(JobChangeAdapter jobChangeAdapter) {
		buildJob.addJobChangeListener(jobChangeAdapter);
	}

	protected void schedule() {
		buildJob.schedule();
	}

	private void createJob() {
		buildJob = new Job("Parse Tree Builder") {

			private Timer timer;

			@Override
			protected IStatus run(IProgressMonitor monitor) {
				try {
					timer = startTimer(monitor, getThread());
					buildTarget();
				} finally {
					timer.cancel();
				}
				if (getTree() == null) {
					IStatus status = new Status(IStatus.CANCEL, AntlrDTUI.PLUGIN_ID, "No tree generated");
					return status;
				}
				return Status.OK_STATUS;
			}
		};
		buildJob.setPriority(Job.SHORT);
		buildJob.setSystem(true);
	}

	private Timer startTimer(final IProgressMonitor monitor, final Thread thread) {
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {

			@Override
			public void run() {
				Log.error(this, "Target parser generation timer expired");
				if (monitor != null) {
					monitor.setCanceled(true);
					monitor.done();
				}
				if (thread != null) thread.interrupt();
			}
		}, 2000);
		return timer;
	}

	private void buildTarget() {
		IFile srcGrammar = CoreUtil.getInputFile(editor);
		String sourceText = source.getContent();
		target = new TargetUnit(record, srcGrammar, sourceText);
	}

}
