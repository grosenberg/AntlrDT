/*******************************************************************************
 * Copyright (c) 2016 - 2020 Certiv Analytics and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package net.certiv.antlr.dt.diagram.views;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.ITextListener;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextEvent;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.IPropertyChangeListener;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.part.ViewPart;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.core.util.eclipse.PartAdaptor;

public class Preview extends ViewPart implements PartAdaptor, ITextListener, IPropertyChangeListener {

	public static final String ID = "net.certiv.antlr.dt.diagram.views.Preview";

	private static Preview viewpart;
	private Browser browser;
	private ViewJob viewjob;

	private final Set<String> WatchProperties = new HashSet<>();

	public Preview() {
		viewpart = this;
	}

	/** Callback to create and initialize the browser. */
	@Override
	public void createPartControl(Composite parent) {
		browser = new Browser(parent, SWT.NONE);
		browser.setRedraw(true);
		browser.setJavascriptEnabled(true);

		viewjob = new ViewJob(viewpart);
		getPreferenceStore().addPropertyChangeListener(this);
		getActivePage().addPartListener(this);
	}

	// -------------------------------------------------------------------------
	// view opened
	@Override
	public void partActivated(IWorkbenchPart part) {
		if (part instanceof AntlrEditor) {
			((AntlrEditor) part).getViewer().addTextListener(this);
			viewjob.update();
		}
	}

	// view closed
	@Override
	public void partClosed(IWorkbenchPart part) {
		if (part instanceof AntlrEditor) {
			try { // exception when workbench close closes the editor
				getActivePage().hideView(viewpart);
			} catch (Exception e) {}
		}
	}

	// on content change in the editor's text viewer
	@Override
	public void textChanged(TextEvent event) {
		if (viewjob != null) viewjob.update();
	}

	// on property store change
	@Override
	public void propertyChange(PropertyChangeEvent event) {
		if (viewjob != null) {
			if (WatchProperties.isEmpty()) {
				PrefsManager mgr = AntlrCore.getDefault().getPrefsManager();
				WatchProperties.add(mgr.bind(PrefsKey.EDITOR_PREVIEW_EXTERNAL_DIR));
				WatchProperties.add(mgr.bind(PrefsKey.EDITOR_PREVIEW_INTERNAL_DIR));
				WatchProperties.add(mgr.bind(PrefsKey.EDITOR_PREVIEW_FILE));
			}

			if (WatchProperties.contains(event.getProperty())) {
				viewjob.load();
			}
		}
	}

	// -------------------------------------------------------------------------

	// called only by refresh view icon
	public void trigger() {
		viewjob.update();
	}

	// called only by firebug view icon
	public void debug() {
		viewjob.load(true);
	}

	// -------------------------------------------------------------------------

	public Browser getBrowser() {
		return browser;
	}

	@Override
	public void setFocus() {
		if (browser != null) browser.setFocus();
	}

	protected IWorkbenchPage getActivePage() {
		return getSite().getWorkbenchWindow().getActivePage();
	}

	protected AntlrEditor getEditor() {
		IEditorPart editor = getActivePage().getActiveEditor();
		if (editor != null && editor instanceof AntlrEditor) {
			return (AntlrEditor) editor;
		}
		return null;
	}

	protected ISourceViewer getSourceViewer() {
		AntlrEditor editor = getEditor();
		if (editor == null) return null;
		return editor.getViewer();
	}

	protected IPreferenceStore getPreferenceStore() {
		return AntlrCore.getDefault().getPrefsManager();
	}

	@Override
	public void dispose() {
		getPreferenceStore().removePropertyChangeListener(this);
		getActivePage().removePartListener(this);
		ITextViewer srcViewer = getSourceViewer();
		if (srcViewer != null) {
			srcViewer.removeTextListener(this);
		}

		if (viewjob != null) {
			viewjob.cancel();
			viewjob.dispose();
			viewjob = null;
		}
		browser = null;
		super.dispose();
	}
}
