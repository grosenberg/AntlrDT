package net.certiv.antlrdt.ui;

import org.osgi.framework.BundleContext;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.editor.AntlrDTTextTools;
import net.certiv.antlrdt.ui.graph.cst.CstEditor;
import net.certiv.antlrdt.ui.graph.paths.PathsEditor;
import net.certiv.antlrdt.ui.preferences.formatter.FormatterFactory;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.core.util.Log.LogLevel;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.formatter.IDslFormatterFactory;
import net.certiv.dsl.ui.text.DslTextTools;

/**
 * The activator class controls the plug-in life cycle
 */
public class AntlrDTUI extends DslUI {

	// Should be unique, lower case, single word
	private static final String DSL_NAME = "antlrdt";

	public static final String PT_DIALOG_SEC = "secAntlrDT";
	public static final String PA_DIALOG_SEC = "secAntlrDT_PA";

	private static AntlrDTUI plugin;

	private AntlrDTImages imageProvider;
	private DslTextTools textTools;
	private IDslFormatterFactory factory;

	private CstEditor cstEditor;
	private PathsEditor pathsEditor;

	public AntlrDTUI() {
		super();
		Log.defLevel(LogLevel.Debug);
	}

	public static AntlrDTUI getDefault() {
		return plugin;
	}

	@Override
	public DslUI getDslUI() {
		return getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		assert (AntlrDTCore.getDefault().getNatureId() != null);
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
		return getDefault().getBundle().getSymbolicName();
	}

	@Override
	public String getDslLanguageName() {
		return DSL_NAME;
	}

	/** Returns the image provider */
	@Override
	public AntlrDTImages getImageProvider() {
		if (imageProvider == null) {
			imageProvider = new AntlrDTImages();
		}
		return imageProvider;
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
	public IDslFormatterFactory getFormatterFactory() {
		if (factory == null) {
			factory = new FormatterFactory();
		}
		return factory;
	}

	public CstEditor getCstEditor() {
		return cstEditor;
	}

	public void setCstEditor(CstEditor editor) {
		this.cstEditor = editor;
	}

	public PathsEditor getPathsEditor() {
		return pathsEditor;
	}

	public void setPathsEditor(PathsEditor editor) {
		this.pathsEditor = editor;
	}
}
