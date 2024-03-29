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
package net.certiv.antlr.dt.diagram.views;

import static guru.nidi.graphviz.model.Factory.*;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;

import org.antlr.v4.runtime.ParserRuleContext;
import org.apache.commons.text.StringEscapeUtils;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.browser.ProgressListener;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.IPathEditorInput;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.diagram.convert.DotBuilder;
import net.certiv.antlr.dt.diagram.convert.Kind;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.core.preferences.consts.Editor;
import net.certiv.dsl.core.util.FileUtils;
import net.certiv.dsl.core.util.Strings;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.LinkAttr;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

public class ViewJob extends Job {

	private static final String Render = "Diagram.set('%s');";

	private enum State {
		NONE,
		LOAD,
		READY;
	}

	private ProgressListener watcher = new ProgressAdapter() {

		@Override
		public void completed(ProgressEvent event) {
			done();
		}
	};

	private Preview view;
	private Browser browser;
	private PrefsManager store;
	private DoneFunction func;

	private State state = State.NONE;
	private long timer;

	private boolean mathjax;

	public ViewJob(Preview view) {
		super("Preview (dot)");
		this.view = view;
		browser = view.getBrowser();
		store = AntlrCore.getDefault().getPrefsManager();

		load();
	}

	public boolean load() {
		return load(false);
	}

	public boolean load(DslParseRecord record) {
		if (record.hasTree()) {
			DotBuilder gen = new DotBuilder(store);
			String result = gen.generate(record);

		}
		return true;
	}

	Node main = node("main").with(Label.html("<b>main</b><br/>start"), Color.rgb("1020d0").font());
	Node init = node(Label.markdown("**_init_**")), execute = node("execute");
	Node compare = node("compare").with(Shape.RECTANGLE, Style.FILLED, Color.hsv(.7, .3, 1.0));
	Node mkString = node("mkString").with(Label.lines("left", "make", "a", "multi-line"));
	Node printf = node("printf");

	public boolean load(ParserRuleContext root) throws IOException {
		Graph g = graph("example2").directed().with( //
				main.link( //
						to(node("parse").link(execute)).with(LinkAttr.weight(8)), //
						to(init).with(Style.DOTTED), //
						node("cleanup"), //
						to(printf).with(Style.BOLD, Label.of("100 times"), Color.RED)),
				execute.link( //
						graph().with(mkString, printf), //
						to(compare).with(Color.RED)), //
				init.link(mkString) //
		);
		Graphviz.fromGraph(g).width(900).render(Format.PNG).toFile(new File("example/ex2.png"));

		return true;
	}

	public boolean load(boolean firebug) {
		AntlrEditor editor = view.getEditor();
		if (editor == null) return false;

		IPathEditorInput input = (IPathEditorInput) editor.getEditorInput();
		if (input == null) return false;

		state = State.LOAD;
		browser.addProgressListener(watcher);
		timer = System.nanoTime();
		String pluginId = AntlrUI.getDefault().getPluginId();
		String script = FileUtils.fromBundle(pluginId, Editor.HTML + "/firebug.html") + Strings.EOL;
		String content = editor.getHtml(Kind.VIEW);
		if (firebug) {
			content = content.replaceFirst("</head>", script + "</head>");
		}
		browser.setText(content);
		return true;
	}

	public void update() {
		if (state != State.READY) return;
		switch (getState()) {
			case Job.WAITING:
			case Job.RUNNING:
				schedule(store.getInt(PrefsKey.VIEW_UPDATE_DELAY));
				break;
			default:
				schedule(SHORT);
				break;
		}
	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {
		AntlrEditor editor = view.getEditor();
		if (editor == null || view == null || browser == null || browser.isDisposed()) {
			return Status.CANCEL_STATUS;
		}
		timer = System.nanoTime();

		String html = editor.getHtml(Kind.UPDATE);
		if (html.isEmpty()) return Status.CANCEL_STATUS;

		if (mathjax) state = State.READY;

		// execute script on UI thread
		Display.getDefault().asyncExec(new Runnable() {

			@Override
			public void run() {
				if (browser != null && !browser.isDisposed()) {
					String script = String.format(Render, StringEscapeUtils.escapeEcmaScript(html));
					boolean ok = browser.execute(script);
					if (!ok) Log.error( "Script execute failed.");
				}
			}
		});

		return Status.OK_STATUS;
	}

	protected void done() {
		switch (state) {
			case LOAD:
				result("ViewJob ready");
				state = State.READY;
				update();
				break;
			default:
				state = State.READY;
				break;
		}
	}

	protected void dispose() {
		if (func != null) {
			func.dispose();
			func = null;
		}
		if (browser != null && !browser.isDisposed()) {
			browser.removeProgressListener(watcher);
		}
	}

	private void result(String msg) {
		if (timer == 0) {
			Log.info( msg);
		} else {
			long elapsed = System.nanoTime() - timer;
			String value = BigDecimal.valueOf(elapsed, 6).setScale(2, RoundingMode.HALF_UP).toString();
			if (value.indexOf('.') > 3) {
				Log.info( String.format("%s %s (ms): ", msg, value));
			}
		}
		timer = 0;
	}

	// called on completion of MathJax typesetting
	class DoneFunction extends BrowserFunction {

		DoneFunction(Browser browser, String name) {
			super(browser, name);
		}

		@Override
		public Object function(Object[] args) {
			Log.info( String.format("typeset(%s", args));
			if (args.length > 0 && "End".equals(args[0])) {
				state = State.READY;
				done();
			}
			return null;
		}
	}
}
