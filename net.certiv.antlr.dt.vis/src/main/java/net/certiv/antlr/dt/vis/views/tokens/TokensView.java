package net.certiv.antlr.dt.vis.views.tokens;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.Token;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
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
import org.eclipse.swt.widgets.Display;
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
import net.certiv.antlr.dt.ui.editor.AntlrEditor;
import net.certiv.antlr.dt.vis.parse.ErrorRecord;
import net.certiv.antlr.dt.vis.parse.GrammarRecord;
import net.certiv.antlr.dt.vis.parse.ListProcessor;
import net.certiv.antlr.dt.vis.parse.TargetBuilder;
import net.certiv.antlr.dt.vis.util.VisUtil;
import net.certiv.antlr.dt.vis.views.parse.TreeView;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.log.Log.LogLevel;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.eclipse.PartAdaptor2;
import net.certiv.dsl.ui.util.WorkbenchAdaptor;

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

	public static String dialogFilterMod(String dir) {
		if (DialogFirstOpen) {
			dir = "\\\\?\\" + dir;
			DialogFirstOpen = false;
		}
		return dir;
	}

	// ----

	private Composite mainComp;
	private GrammarBlock gmrBlock;
	private SourceBlock srcBlock;
	private TokensBlock tokBlock;
	private FieldDecoration decoRegistry;

	private IDialogSettings dialogSettings;
	private ArrayList<Source> history;

	private GrammarRecord record;
	private Source source;
	private AntlrEditor lastEditorUsed;

	private boolean linkEditorState;
	private boolean showHidden;

	private Action buildAction;
	private Action traceAction;
	private Action linkAction;
	private Action hiddenAction;

	private TargetBuilder builder;
	protected IJobChangeEvent lastBuildEvent;
	private boolean validControls;

	private ImageManager imgMgr;

	public TokensView() {
		super();
		imgMgr = AntlrUI.getDefault().getImageManager();
		Log.setLevel(this, LogLevel.Debug);
	}

	@Override
	public void init(IViewSite site) throws PartInitException {
		super.init(site);
		addListeners(this);

		String secName = AntlrUI.PT_DIALOG_SEC + "_";
		secName += CoreUtil.getWorkspaceLocation().toString().replaceAll("[:/\\\\]", "_");
		IDialogSettings settings = AntlrUI.getDefault().getDialogSettings();
		dialogSettings = settings.getSection(secName);
		if (dialogSettings == null) {
			dialogSettings = settings.addNewSection(secName);
		}
		history = new ArrayList<>();

		decoRegistry = FieldDecorationRegistry.getDefault().getFieldDecoration(FieldDecorationRegistry.DEC_ERROR);

		linkEditorState = dialogSettings.getBoolean(LINK_EDITOR_STATE);
		showHidden = dialogSettings.getBoolean(SHOW_HIDDEN);
	}

	@Override
	public void createPartControl(Composite parent) {
		GridLayoutFactory.fillDefaults().spacing(1, 1).numColumns(1).applyTo(parent);

		mainComp = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().spacing(1, 1).numColumns(1).applyTo(mainComp);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(mainComp);

		// ------------------------------------------------------------------

		gmrBlock = new GrammarBlock(this, mainComp);
		srcBlock = new SourceBlock(this, mainComp);
		tokBlock = new TokensBlock(this, mainComp);

		// ------------------------------------------------------------------

		makeActions();
		contributeToActionBars();

		validateControls();
	}

	// called on:
	// 1) part creation; 2) source block value change; 3) editor becomes active
	protected boolean validateControls() {
		validControls = checkGrammar();
		validControls &= checkSource();
		return validControls;
	}

	private boolean checkGrammar() {
		boolean valid = false;
		IEditorPart part = CoreUtil.getActiveEditor();
		if (part instanceof AntlrEditor) {
			AntlrEditor editor = (AntlrEditor) part;
			IFile grammarFile = CoreUtil.getInputFile(editor);
			if (grammarFile != null && grammarFile.exists() && !editor.isDirty()) {
				gmrBlock.getDecorator().hide();
				if (gmrBlock.setText(grammarFile.getName())) {
					srcBlock.clear();  // grammarFile name changed
					tokBlock.clear();
				}
				valid = true;
				IProject project = grammarFile.getProject();
				if (record == null) {
					record = new GrammarRecord(project, grammarFile);
					record.load();
					Log.debug(this, "Loaded record");
				}
			}
		} else if (lastEditorUsed != null) {
			valid = true;
		}

		if (!valid) {
			gmrBlock.setText("");
			String msg = "Open a grammar to select";
			srcBlock.getDecorator().setDescriptionText(msg);
			srcBlock.getDecorator().show();
		}
		return valid;
	}

	private boolean checkSource() {
		if (source == null || !source.fileExists()) {
			String msg = "Select a snippet file";
			srcBlock.getDecorator().setDescriptionText(msg);
			srcBlock.getDecorator().show();
			return false;
		} else {
			File snippet = source.getFile();
			if (!snippet.canRead()) {
				String msg = "Snippet file is not readable";
				srcBlock.getDecorator().setDescriptionText(msg);
				srcBlock.getDecorator().show();
				return false;
			}
		}
		srcBlock.getDecorator().hide();
		return true;
	}

	@Override
	public void setFocus() {
		srcBlock.getCombo().setFocus();
	}

	public ControlDecoration createDecoration(Control control) {
		ControlDecoration controlDecoration = new ControlDecoration(control, SWT.LEFT | SWT.TOP);
		controlDecoration.setImage(decoRegistry.getImage());
		controlDecoration.setShowOnlyOnFocus(false);
		controlDecoration.setMarginWidth(DECORATION_SPACING);
		return controlDecoration;
	}

	public void buttonSourceFileSelection(SelectionEvent e) {
		Source current = null;
		try {
			current = history.get(0); // prior UI selection, if any
		} catch (Exception e1) {
			current = new Source(record.getSnippetsDir().getPathname(), "");
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
		validateControls();
		if (validControls) performBuild();
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
		dialog.setFilterExtensions(new String[] { "*." + record.getSnippetsExt() });
		if (dialog.open() != null) {
			if (dialog.getFileName().length() > 0) {
				Source source = new Source(dialog.getFilterPath(), dialog.getFileName());
				if (source.exists()) return source;
			}
		}
		return null;
	}

	public void execGrammarDialogHandler() {
		GrammarDialog dialog = new GrammarDialog(getSite().getShell(), record);
		dialog.setTitle("Modify grammar integration data");
		if (dialog.open() == Window.OK) {
			record = dialog.getRecord();
			record.validate();
			record.save();
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
		} else if (lastEditorUsed != null) {
			performBuild(lastEditorUsed);
		}
	}

	private void performBuild(AntlrEditor editor) {
		if (source == null) return;
		if (!editor.isEditable() || !editor.getEditorInput().exists() || editor.isDirty()) return;
		lastEditorUsed = editor;

		// clear any prior data
		setContentDescription(" ");
		tokBlock.getTokensViewer().setInput(null);

		// compile the grammar and build a ParseTree using the given data file
		builder = new TargetBuilder(editor, record, source);
		builder.addJobChangeListener(new JobChangeAdapter() {

			@Override
			public void done(IJobChangeEvent event) {
				lastBuildEvent = event;

				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						String[] tokenNames = builder.getTokenNames();
						String[] modeNames = builder.getModeNames();
						List<Token> tokens = builder.getTokens();
						List<ErrorRecord> errors = builder.getErrorList();

						if (tokens != null) {
							List<String[]> data = ListProcessor.extract(tokens, tokenNames, modeNames, showHidden);
							tokBlock.getTokensViewer().setInput(data);
						}

						if (errors != null) {
							List<String[]> data = ListProcessor.extract(errors, tokenNames);
							tokBlock.getErrorsViewer().setInput(data);
						}

						if (builder.isValid()) {
							try {
								TreeView treeView = (TreeView) CoreUtil.getActivePage().showView(TreeView.ID);
								treeView.setInput(source, builder);

							} catch (Exception e) {
								Log.error(this, "Failed opening TreeView: " + e.getMessage());
								return;
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
