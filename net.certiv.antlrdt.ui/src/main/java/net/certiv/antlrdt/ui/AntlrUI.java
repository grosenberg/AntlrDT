package net.certiv.antlrdt.ui;

import org.osgi.framework.BundleContext;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.editor.AntlrCompletionManager;
import net.certiv.antlrdt.ui.editor.AntlrDTEditor;
import net.certiv.antlrdt.ui.editor.AntlrDTTextTools;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.log.Log.LogLevel;
import net.certiv.dsl.ui.DslUI;
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
	public void start(BundleContext context) throws Exception {
		assert (AntlrCore.getDefault().getNatureId() != null);
		plugin = this;
		super.start(context);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		plugin = null;
	}

	@Override
	public String getPluginId() {
		return plugin.getBundle().getSymbolicName();
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
			textTools = new AntlrDTTextTools(true);
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

	@Override
	public String getEditorId() {
		return AntlrDTEditor.EDITOR_ID;
	}
}
