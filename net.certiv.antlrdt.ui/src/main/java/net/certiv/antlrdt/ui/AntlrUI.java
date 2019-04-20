package net.certiv.antlrdt.ui;

import org.osgi.framework.BundleContext;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.editor.AntlrDTEditor;
import net.certiv.antlrdt.ui.editor.AntlrDTTextTools;
import net.certiv.antlrdt.ui.templates.AntlrContextType;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.log.Log.LogLevel;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.DslTextTools;

public class AntlrUI extends DslUI {

	public static final String PT_DIALOG_SEC = "secAntlrDT";
	public static final String PA_DIALOG_SEC = "secAntlrDT_PA";

	private static AntlrUI plugin;

	private ImageManager imageMgr;
	private DslTextTools textTools;

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
	public String getDslLanguageName() {
		return AntlrCore.DSL_NAME;
	}

	@Override
	public ImageManager getImageManager() {
		if (imageMgr == null) {
			imageMgr = new ImageManager();
		}
		return imageMgr;
	}

	/** Returns the text tools */
	@Override
	public DslTextTools getTextTools() {
		if (textTools == null) {
			textTools = new AntlrDTTextTools(true);
		}
		return textTools;
	}

	@Override
	protected String getEditorId() {
		return AntlrDTEditor.EDITOR_ID;
	}

	@Deprecated
	@Override
	protected String[] getDslContextTypes() {
		return new String[] { AntlrContextType.GRAMMAR_CONTEXT_TYPE_ID, AntlrContextType.OPTIONS_CONTEXT_TYPE_ID, //
				AntlrContextType.ACTIONS_CONTEXT_TYPE_ID, //
				AntlrContextType.JAVADOC_CONTEXT_TYPE_ID //
		};
	}
}
