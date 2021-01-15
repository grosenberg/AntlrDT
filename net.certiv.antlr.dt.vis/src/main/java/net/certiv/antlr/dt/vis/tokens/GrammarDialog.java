/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.vis.tokens;

import static net.certiv.antlr.dt.vis.parse.TargetAssembly.*;

import org.eclipse.jface.dialogs.StatusDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

import net.certiv.antlr.dt.vis.fields.DirField;
import net.certiv.antlr.dt.vis.fields.FileField;
import net.certiv.antlr.dt.vis.parse.TargetAssembly;
import net.certiv.antlr.runtime.xvisitor.util.Strings;
import net.certiv.dsl.ui.fields.ContentChangedEvent;
import net.certiv.dsl.ui.fields.IContentChangedListener;
import net.certiv.dsl.ui.fields.SelectionField;
import net.certiv.dsl.ui.fields.TextField;

public class GrammarDialog extends StatusDialog {

	private TargetAssembly assembly;

	private TextField project;
	private TextField grammar;
	private DirField snippetDir;
	private TextField snippetExt;
	private FileField tokenFactory;
	private FileField customToken;
	private FileField errorStrategy;
	private SelectionField traceParser;

	private ContentChangedListener listener;

	public GrammarDialog(Shell parent, TargetAssembly assembly) {
		super(parent);
		this.assembly = assembly;
		listener = new ContentChangedListener();
	}

	public TargetAssembly getAssembly() {
		return assembly;
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
		project.setText(assembly.getProject().getName());

		grammar = new TextField(grammarComp, SWT.READ_ONLY | SWT.NO_FOCUS, GRAMMAR, "Gen", 3);
		grammar.setText(assembly.getGrammar().getName());

		snippetDir = new DirField(getShell(), grammarComp, SWT.NONE, SNIPPET_DIR, "Snippets Dir", 3);
		snippetDir.setSource(assembly.getSnippetsDir());
		snippetDir.addContentChangedListener(listener);

		snippetExt = new TextField(grammarComp, SWT.NONE, SNIPPET_EXT, "Snippets Ext", 3);
		snippetExt.setText(assembly.getSnippetsExt());
		snippetExt.addContentChangedListener(listener);

		String defaultDir = assembly.getProject().getLocation().toString();

		tokenFactory = new FileField(getShell(), grammarComp, SWT.NONE, TOKEN_FACTORY, "Token factory *", 3,
				assembly.getTokenFactory(), defaultDir);
		tokenFactory.setSource(assembly.getTokenFactory());
		tokenFactory.addContentChangedListener(listener);

		customToken = new FileField(getShell(), grammarComp, SWT.NONE, TOKEN, "Custom token *", 3,
				assembly.getCustomToken(), defaultDir);
		customToken.setSource(assembly.getCustomToken());
		customToken.addContentChangedListener(listener);

		errorStrategy = new FileField(getShell(), grammarComp, SWT.NONE, PARSER_STRATEGY, "Parser error strategy *", 3,
				assembly.getErrorStrategy(), defaultDir);
		errorStrategy.setSource(assembly.getErrorStrategy());
		errorStrategy.addContentChangedListener(listener);

		traceParser = new SelectionField(grammarComp, SWT.CHECK, PARSER_TRACE, "Trace parser execution", 3);
		traceParser.setSelected(assembly.getTraceParser());
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
					assembly.setSnippetsDir(snippetDir.getSource());
					break;
				case SNIPPET_EXT:
					String ext = snippetExt.getText();
					if (ext.trim().isEmpty()) {
						ext = Strings.STAR;
					} else if (ext.startsWith(Strings.DOT)) {
						ext = ext.substring(1);
					}
					assembly.setSnippetsExt(ext);
					break;
				case TOKEN_FACTORY:
					assembly.setTokenFactory(tokenFactory.getSource());
					break;
				case TOKEN:
					assembly.setCustomToken(customToken.getSource());
					break;
				case PARSER_STRATEGY:
					assembly.setErrorStrategy(errorStrategy.getSource());
					break;
				case PARSER_TRACE:
					assembly.setTraceParser(traceParser.isSelected());
				default:
			}
		}
	}
}
