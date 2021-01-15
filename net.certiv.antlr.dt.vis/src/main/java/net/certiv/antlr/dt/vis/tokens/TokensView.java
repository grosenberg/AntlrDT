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

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.fieldassist.FieldDecoration;
import org.eclipse.jface.fieldassist.FieldDecorationRegistry;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.layout.LayoutConstants;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IViewSite;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.part.ViewPart;

import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.antlr.dt.ui.console.AntlrConsoleFactory;
import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.antlr.dt.vis.parse.FieldProcessor;
import net.certiv.antlr.dt.vis.parse.ProblemRecord;
import net.certiv.antlr.dt.vis.parse.TargetAssembly;
import net.certiv.antlr.dt.vis.parse.TargetBuilder;
import net.certiv.antlr.dt.vis.tree.TreeView;
import net.certiv.antlr.dt.vis.util.VisUtil;
import net.certiv.dsl.core.console.ConsoleRecordFactory.ConsoleRecord;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.ModelException;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.core.util.eclipse.PartAdaptor2;
import net.certiv.dsl.core.util.eclipse.WorkbenchAdaptor;

public class TokensView extends ViewPart {

	public static final String VIEW_ID = "net.certiv.antlr.dt.vis.views.tokens.TokensView";
	public static final String GraphTopic = "TreeView/Graph";

	static final int hSp = 16;
	static final int vSp = LayoutConstants.getSpacing().y;

	private static final int DECORATION_SPACING = 3;

	private static final String LINK_EDITOR_STATE = "linkEditorState";
	private static final String SHOW_HIDDEN = "showHidden";

	private static final String KEY_NAME = "keyName";
	private static final String KEY_PATH = "keyPath";
	private static final String KEY_HIST = "keyHistory";

	// ----

	private static boolean DialogFirstOpen = true;

	private static String dialogFilterMod(String dir) {
		if (DialogFirstOpen) {
			dir = "\\\\?\\" + dir;
			DialogFirstOpen = false;
		}
		return dir;
	}

	// ----

	private Composite comp;
	private GrammarBlock gmrBlock;
	private SourceBlock srcBlock;
	private TokensBlock tokBlock;
	private FieldDecoration registry;

	private IDialogSettings dialogSettings;
	private ArrayList<Source> history;

	private TargetAssembly assembly;
	private Source source;

	private AntlrEditor priorEditor;
	private ICodeUnit priorUnit;

	private boolean linkEditorState;
	private boolean showHidden;

	private Action buildAction;
	private Action traceAction;
	private Action linkAction;
	private Action hiddenAction;

	private TargetBuilder builder;
	protected IJobChangeEvent lastBuildEvent;

	private ImageManager imgMgr;

	public TokensView() {
		super();
		imgMgr = AntlrUI.getDefault().getImageManager();
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);

		String secName = AntlrUI.PT_DIALOG_SEC + Strings.LOWDASH;
		secName += CoreUtil.getWorkspaceLocation().toString().replaceAll("[:/\\\\]", Strings.LOWDASH);
		IDialogSettings settings = AntlrUI.getDefault().getDialogSettings();
		dialogSettings = settings.getSection(secName);
		if (dialogSettings == null) {
			dialogSettings = settings.addNewSection(secName);
		}
		history = new ArrayList<>();
		registry = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);
		linkEditorState = dialogSettings.getBoolean(LINK_EDITOR_STATE);
		showHidden = dialogSettings.getBoolean(SHOW_HIDDEN);

		addListeners(this);
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayoutFactory.fillDefaults().spacing(1, 1).numColumns(1).applyTo(parent);

		comp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(1, 1).numColumns(1).applyTo(comp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(comp);

		gmrBlock = new GrammarBlock(this, comp);	// grammar name
		srcBlock = new SourceBlock(this, comp);		// source name
		tokBlock = new TokensBlock(this, comp);		// token stream & errors

		makeActions();
		contributeToActionBars();
		validateControls();
	}

	// called on:
	// 1) part creation
	// 2) source block value change
	// 3) editor becomes active
	protected boolean validateControls() {
		boolean valid = true;
		valid &= checkGrammar();
		valid &= checkSource();
		return valid;
	}

	private boolean checkGrammar() {
		boolean valid = true;
		AntlrEditor editor = null;
		ICodeUnit unit = null;

		gmrBlock.hideDecoration();

		// if current editor is not AntlrEditor; use prior if valid
		IEditorPart ed = CoreUtil.getActiveEditor();
		if (ed instanceof AntlrEditor) {
			editor = (AntlrEditor) ed;
			unit = editor.getInputDslElement();
			priorEditor = editor;
			priorUnit = unit;

		} else if (priorEditor != null) {
			editor = priorEditor;
			unit = priorUnit;

		} else {
			gmrBlock.showDecoration("Open grammar in AntlrDT editor");
			valid = false;
		}

		if (valid) {
			if (!unit.exists()) {
				gmrBlock.showDecoration("Grammar file not found!");
				valid = false;

			} else if (editor.isDirty()) {
				gmrBlock.showDecoration("Grammar editor is dirty!");
				valid = false;
			}
		}

		if (valid) {
			boolean changed = gmrBlock.setText(unit.getElementName());
			if (changed) srcBlock.clear(); // grammar name changed

			try {
				assembly = new TargetAssembly(unit); // always recreate
				assembly.prepare();
				assembly.load();

			} catch (ModelException e) {
				gmrBlock.showDecoration(e.getMessage());
				valid = false;
			}
		}

		return valid;
	}

	private boolean checkSource() {
		srcBlock.getDecorator().hide();

		if (source == null || !source.exists()) {
			srcBlock.showDecoration("Must select an input data file");
			return false;
		}

		if (!source.getFile().canRead()) {
			srcBlock.showDecoration("Input data file is not readable");
			return false;
		}

		return true;
	}

	@Override
	public void setFocus() {
		srcBlock.getCombo().setFocus();
	}

	public ControlDecoration createDecoration(Control control) {
		ControlDecoration controlDecoration = new ControlDecoration(control, SWT.LEFT | SWT.TOP);
		controlDecoration.setImage(registry.getImage());
		controlDecoration.setShowOnlyOnFocus(false);
		controlDecoration.setMarginWidth(DECORATION_SPACING);
		return controlDecoration;
	}

	public void buttonSourceFileSelection(SelectionEvent e) {
		Source current = null;
		try {
			current = history.get(0); // prior UI selection, if any
		} catch (Exception e1) {
			current = new Source(assembly.getSnippetsDir().getPathname(), Strings.EMPTY);
		}

		current = handleFileSelectionDialog(current);
		if (current != null) {
			source = current;
			processFileSelection();
		}
	}

	public void dropDownSourceFileSelection(int selectionIndex) {
		if (selectionIndex > -1) {
			source = history.get(selectionIndex);
			processFileSelection();
		}
	}

	private void processFileSelection() {
		updateComboDropDown();

		history.remove(source);
		history.add(0, source);

		updateSettings();
		if (validateControls()) {
			performBuild();
		}
	}

	private void updateComboDropDown() {
		for (int idx = 0; idx < history.size(); idx++) {
			if (source.equals(history.get(idx))) {
				try {
					srcBlock.getCombo().remove(idx);
				} catch (IllegalArgumentException e) {}
				break;
			}
		}
		srcBlock.getCombo().add(source.name, 0);
		srcBlock.getCombo().setText(source.name);
		srcBlock.getCombo().setToolTipText(source.getPathname());
		if (srcBlock.getCombo().getItemCount() > 10) {
			srcBlock.getCombo().remove(10);
			history.remove(10);
		}
	}

	private void updateSettings() {
		IEditorPart editor = CoreUtil.getActiveEditor();
		if (editor instanceof AntlrEditor) {
			updateSettings((AntlrEditor) editor);
		}
	}

	private void updateSettings(AntlrEditor editor) {
		String grammarName = CoreUtil.getInputFile(editor).getName();
		dialogSettings.put(grammarName + KEY_NAME, source.name);
		dialogSettings.put(grammarName + KEY_PATH, source.path);

		ArrayList<String> tmp = new ArrayList<>();
		for (Source h : history) {
			tmp.add(h.name);
			tmp.add(h.path);
		}
		String[] data = tmp.toArray(new String[tmp.size()]);
		dialogSettings.put(grammarName + KEY_HIST, data);
	}

	// ////////////////////////////////////////////////////////////////////////

	private Source handleFileSelectionDialog(Source prior) {
		IPath dir = new Path(prior.path);
		if (!dir.toFile().exists()) {
			dir = CoreUtil.getWorkspaceLocation().append(dir);
		}
		FileDialog dialog = new FileDialog(getSite().getShell(), SWT.SINGLE);
		dialog.setFilterPath(dialogFilterMod(dir.toString()));
		dialog.setFileName(prior.name);
		dialog.setFilterExtensions(new String[] { "*." + assembly.getSnippetsExt() });
		if (dialog.open() != null) {
			if (dialog.getFileName().length() > 0) {
				Source source = new Source(dialog.getFilterPath(), dialog.getFileName());
				if (source.exists()) return source;
			}
		}
		return null;
	}

	public void execGrammarDialogHandler() {
		if (assembly != null) {
			GrammarDialog dialog = new GrammarDialog(getSite().getShell(), assembly);
			dialog.setTitle("Modify grammar integration data");
			if (dialog.open() == Window.OK) {
				assembly = dialog.getAssembly();
				assembly.save();
			}
		}
	}

	private void makeActions() {
		buildAction = new Action() {

			@Override
			public void run() {
				performBuild();
			}
		};

		buildAction.setText("Build");
		buildAction.setToolTipText("Build Model");
		buildAction.setEnabled(true);
		buildAction.setImageDescriptor(imgMgr.getDescriptor(imgMgr.BUILD));

		traceAction = new Action() {

			@Override
			public void run() {
				showTrace();
			}
		};

		traceAction.setText("Show Trace");
		traceAction.setToolTipText("Show Trace");
		traceAction.setEnabled(true);
		traceAction.setImageDescriptor(imgMgr.getDescriptor(imgMgr.HISTORY));

		hiddenAction = new Action() {

			@Override
			public void run() {
				performShowHidden();
			}
		};

		hiddenAction.setText("Toggle Hidden");
		hiddenAction.setToolTipText("Toggle display of hidden tokens");
		hiddenAction.setChecked(showHidden);
		hiddenAction.setImageDescriptor(imgMgr.getDescriptor(imgMgr.HIDDEN));

		linkAction = new Action() {

			@Override
			public void run() {
				performLink();
			}
		};

		linkAction.setText("Link with Editor");
		linkAction.setToolTipText("Link with Editor");
		linkAction.setChecked(linkEditorState);
		linkAction.setImageDescriptor(imgMgr.getDescriptor(imgMgr.LINK));
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(buildAction);
		manager.add(linkAction);
		manager.add(hiddenAction);
		manager.add(traceAction);
		manager.add(new Separator());
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(buildAction);
		manager.add(linkAction);
		manager.add(hiddenAction);
		manager.add(traceAction);
	}

	// ////////////////////////////////////////////////////////////////////////

	protected void openSourceFileAtToken(int idx) {
		List<Token> tokens = builder.getTokens();
		if (tokens != null && tokens.size() > idx) {
			CommonToken token = (CommonToken) tokens.get(idx);
			VisUtil.openSourceFileAtToken(source, token);
		}
	}

	// ////////////////////////////////////////////////////////////////////////

	protected void showTrace() {

		TraceDialog traceDialog = new TraceDialog(getSite().getShell(), builder.getTrace());
		traceDialog.open();
	}

	// update link state and conditionally populate
	protected void performLink() {
		linkEditorState = linkAction.isChecked();
		dialogSettings.put(LINK_EDITOR_STATE, linkEditorState);
	}

	protected void performShowHidden() {
		showHidden = hiddenAction.isChecked();
		dialogSettings.put(SHOW_HIDDEN, showHidden);
		if (lastBuildEvent != null && lastBuildEvent.getResult().isOK()) {
			tokBlock.getTokensViewer().refresh();
		}
	}

	protected void performBuild() {
		IEditorPart part = CoreUtil.getActiveEditor();
		if (part instanceof AntlrEditor) {
			performBuild((AntlrEditor) part);

		} else if (priorEditor != null) {
			performBuild(priorEditor);
		}
	}

	private void performBuild(AntlrEditor editor) {
		if (source == null) return;

		// clear any prior data
		setContentDescription(Strings.SPACE);
		tokBlock.clear();

		// compile the grammar and build a ParseTree using the given data file
		builder = new TargetBuilder(editor, assembly, source);
		builder.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				lastBuildEvent = event;

				String[] tokenNames = builder.getTokenNames();
				String[] modeNames = builder.getModeNames();
				List<Token> tokens = builder.getTokens();
				List<ProblemRecord> problems = builder.getProblems();
				List<ConsoleRecord> errors = builder.getErrors();

				CoreUtil.getStandardDisplay().syncExec(new Runnable() {

					@Override
					public void run() {
						if (tokens != null) {
							List<String[]> data = FieldProcessor.extract(tokens, tokenNames, modeNames, showHidden);
							tokBlock.setTokensInput(data);
						}

						if (problems != null) {
							List<String[]> data = FieldProcessor.extract(problems, tokenNames);
							tokBlock.setProblemsInput(data);
						}

						if (errors != null) {
							AntlrConsoleFactory.getFactory().getConsole().append(errors, false);
							for (ConsoleRecord error : errors) {
								Log.error(TokensView.class, error.toString());
							}
						}

						if (builder.isValid()) {
							try {
								TreeView treeView = (TreeView) CoreUtil.getActivePage().showView(TreeView.ID);
								treeView.setInput(source, builder);

							} catch (Exception e) {
								Log.error(this, "Failed opening TreeView: " + e.getMessage());
							}
						}
					}
				});
			}
		});

		builder.schedule();
	}

	private void addListeners(final TokensView view) {
		final PartAdaptor2 partAdaptor = new PartAdaptor2() {

			private boolean viewVisible = true;

			@Override
			public void partActivated(IWorkbenchPartReference partRef) {
				IWorkbenchPart part = partRef.getPart(false);
				if (part instanceof AntlrEditor && viewVisible) {
					validateControls();
				}
			}

			@Override
			public void partVisible(IWorkbenchPartReference partRef) {
				IWorkbenchPart part = partRef.getPart(false);
				if (part == view) viewVisible = true;
			}

			@Override
			public void partHidden(IWorkbenchPartReference partRef) {
				IWorkbenchPart part = partRef.getPart(false);
				if (part == view) viewVisible = false;
			}
		};

		CoreUtil.getPartService().addPartListener(partAdaptor);
		CoreUtil.getActiveWorkbench().addWorkbenchListener(new WorkbenchAdaptor() {

			@Override
			public boolean preShutdown(IWorkbench workbench, boolean forced) {
				if (view != null) {
					workbench.removeWorkbenchListener(this);
					CoreUtil.getPartService().removePartListener(partAdaptor);
				}
				return true;
			}
		});
	}
}
