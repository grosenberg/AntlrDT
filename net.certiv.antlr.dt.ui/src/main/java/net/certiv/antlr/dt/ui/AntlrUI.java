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
package net.certiv.antlr.dt.ui;

import org.osgi.framework.BundleContext;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.console.AntlrConsoleFactory;
import net.certiv.antlr.dt.ui.editor.AntlrCompletionManager;
import net.certiv.antlr.dt.ui.editor.AntlrTextTools;
import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.log.Log.LogLevel;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.console.StyledConsole;
import net.certiv.dsl.ui.editor.text.DslTextTools;
import net.certiv.dsl.ui.templates.CompletionManager;

public class AntlrUI extends DslUI {

	public static final String PT_DIALOG_SEC = "secAntlrDT";
	public static final String PA_DIALOG_SEC = "secAntlrDT_PA";

	private static AntlrUI plugin;

	private ImageManager imageMgr;
	private DslTextTools textTools;
	private CompletionManager compMgr;

	public AntlrUI() {
		super();
		Log.defLevel(LogLevel.Debug);
	}

	public static AntlrUI getDefault() {
		return plugin;
	}

	@Override
	public DslUI getDslUI() {
		return plugin;
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	@Override
	public void start(BundleContext ctx) throws Exception {
		assert (AntlrCore.getDefault().getNatureId() != null);
		plugin = this;
		super.start(ctx);
	}

	@Override
	public void stop(BundleContext ctx) throws Exception {
		super.stop(ctx);
		plugin = null;
	}

	@Override
	public String getPluginId() {
		return plugin.getBundle().getSymbolicName();
	}

	@Override
	public String getEditorId() {
		return AntlrEditor.EDITOR_ID;
	}

	@Override
	protected StyledConsole getConsole() {
		return AntlrConsoleFactory.getFactory().getConsole();
	}

	@Override
	public ImageManager getImageManager() {
		if (imageMgr == null) {
			imageMgr = new ImageManager();
		}
		return imageMgr;
	}

	@Override
	public DslTextTools getTextTools() {
		if (textTools == null) {
			textTools = new AntlrTextTools(true);
		}
		return textTools;
	}

	@Override
	public CompletionManager getCompletionMgr() {
		if (compMgr == null) {
			compMgr = new AntlrCompletionManager(this, getEditorId());
		}
		return compMgr;
	}
}
