package net.certiv.antlrdt.vis.views.tokens;

import static net.certiv.antlrdt.vis.parse.GrammarRecord.*;

import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import net.certiv.antlrdt.vis.fields.DirField;
import net.certiv.antlrdt.vis.fields.FileField;
import net.certiv.antlrdt.vis.parse.GrammarRecord;
import net.certiv.dsl.ui.fields.ContentChangedEvent;
import net.certiv.dsl.ui.fields.IContentChangedListener;
import net.certiv.dsl.ui.fields.SelectionField;
import net.certiv.dsl.ui.fields.TextField;

public class GrammarDialog extends StatusDialog {

	private GrammarRecord rec;

	private TextField project;
	private TextField grammar;
	private DirField snippetDir;
	private TextField snippetExt;
	private FileField tokenFactory;
	private FileField customToken;
	private FileField errorStrategy;
	private SelectionField traceParser;

	private ContentChangedListener listener;

	public GrammarDialog(Shell parent, GrammarRecord rec) {
		super(parent);
		this.rec = rec;
		this.listener = new ContentChangedListener();
	}

	public GrammarRecord getRecord() {
		return rec;
	}

	@Override
	protected Control createDialogArea(Composite parent) {
		Composite composite = (Composite) super.createDialogArea(parent);

		Group grammarGroup = new Group(composite, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(2, 1).applyTo(grammarGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(grammarGroup);
		grammarGroup.setText("Gen Integration");

		Composite grammarComp = new Composite(grammarGroup, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(grammarComp);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(grammarComp);

		project = new TextField(grammarComp, SWT.READ_ONLY | SWT.NO_FOCUS, PROJECT, "Project", 3);
		project.setText(rec.getProject().getName());

		grammar = new TextField(grammarComp, SWT.READ_ONLY | SWT.NO_FOCUS, GRAMMAR, "Gen", 3);
		grammar.setText(rec.getGrammar().getName());

		snippetDir = new DirField(getShell(), grammarComp, SWT.NONE, SNIPPET_DIR, "Snippets Dir", 3);
		snippetDir.setSource(rec.getSnippetsDir());
		snippetDir.addContentChangedListener(listener);

		snippetExt = new TextField(grammarComp, SWT.NONE, SNIPPET_EXT, "Snippets Ext", 3);
		snippetExt.setText(rec.getSnippetsExt());
		snippetExt.addContentChangedListener(listener);

		String defaultDir = rec.getProject().getLocation().toString();

		tokenFactory = new FileField(getShell(), grammarComp, SWT.NONE, TOKEN_FACTORY, "Token factory *", 3,
				rec.getTokenFactory(), defaultDir);
		tokenFactory.setSource(rec.getTokenFactory());
		tokenFactory.addContentChangedListener(listener);

		customToken = new FileField(getShell(), grammarComp, SWT.NONE, TOKEN, "Custom token *", 3, rec.getCustomToken(),
				defaultDir);
		customToken.setSource(rec.getCustomToken());
		customToken.addContentChangedListener(listener);

		errorStrategy = new FileField(getShell(), grammarComp, SWT.NONE, PARSER_STRATEGY, "Parser error strategy *", 3,
				rec.getErrorStrategy(), defaultDir);
		errorStrategy.setSource(rec.getErrorStrategy());
		errorStrategy.addContentChangedListener(listener);

		traceParser = new SelectionField(grammarComp, SWT.CHECK, PARSER_TRACE, "Trace parser execution", 3);
		traceParser.setSelected(rec.getTraceParser());
		traceParser.addContentChangedListener(listener);

		Label note = new Label(grammarComp, SWT.NONE);
		note.setText("* Required only when custom classes have been defined for the grammar.");
		GridDataFactory.fillDefaults().grab(true, false).span(3, 1).applyTo(note);

		return composite;
	}

	class ContentChangedListener implements IContentChangedListener {

		@Override
		public void contentChanged(ContentChangedEvent event) {
			switch (event.getId()) {
				case SNIPPET_DIR:
					rec.setSnippetsDir(snippetDir.getSource());
					break;
				case SNIPPET_EXT:
					String ext = snippetExt.getText();
					if (ext.trim().isEmpty()) {
						ext = "*";
					} else if (ext.startsWith(".")) {
						ext = ext.substring(1);
					}
					rec.setSnippetsExt(ext);
					break;
				case TOKEN_FACTORY:
					rec.setTokenFactory(tokenFactory.getSource());
					break;
				case TOKEN:
					rec.setCustomToken(customToken.getSource());
					break;
				case PARSER_STRATEGY:
					rec.setErrorStrategy(errorStrategy.getSource());
					break;
				case PARSER_TRACE:
					rec.setTraceParser(traceParser.isSelected());
				default:
			}
		}
	}
}