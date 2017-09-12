package net.certiv.antlrdt.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.osgi.framework.Bundle;

import net.certiv.dsl.ui.DslImages;

public class AntlrDTImages extends DslImages {

	private static final Bundle locBundle = AntlrDTUI.getDefault().getBundle();
	private static final String locPrefix = locBundle.getSymbolicName() + '.';

	public final ImageDescriptor DESC_OBJ_MODULE = create(locBundle, OBJ, locPrefix + "grammar_obj.png");
	public final ImageDescriptor DESC_OBJ_STATEMENT = create(locBundle, OBJ, locPrefix + "statement.gif");
	public final ImageDescriptor DESC_OBJ_BLOCK = create(locBundle, OBJ, locPrefix + "block.gif");

	public final ImageDescriptor DESC_OBJ_BUFFER = create(locBundle, OBJ, locPrefix + "buffer.png");
	public final ImageDescriptor DESC_OBJ_MESSAGE = create(locBundle, OBJ, locPrefix + "message_obj.png");
	public final ImageDescriptor DESC_OBJ_REQUIRED = create(locBundle, OBJ, locPrefix + "required_obj.gif");
	public final ImageDescriptor DESC_OBJ_OPTION = create(locBundle, OBJ, locPrefix + "option_obj.gif");

	public final ImageDescriptor DESC_OBJ_IMPORT = create(locBundle, OBJ, locPrefix + "import_obj.gif");
	public final ImageDescriptor DESC_OBJ_PACKAGE = create(locBundle, OBJ, locPrefix + "packageBlue_obj.gif");
	public final ImageDescriptor DESC_OBJ_EXTEND = create(locBundle, OBJ, locPrefix + "extend_obj.gif");
	public final ImageDescriptor DESC_OBJ_SERVICE = create(locBundle, OBJ, locPrefix + "service_obj.gif");
	public final ImageDescriptor DESC_OBJ_OPTIONAL = create(locBundle, OBJ, locPrefix + "optional_obj.gif");
	public final ImageDescriptor DESC_OBJ_OPTION_ELEMENT = create(locBundle, OBJ, locPrefix + "optional_obj.gif");
	public final ImageDescriptor DESC_OBJ_REPEATED = create(locBundle, OBJ, locPrefix + "repeat_obj.gif");
	public final ImageDescriptor DESC_OBJ_EXTENSIONS = create(locBundle, OBJ, locPrefix + "extensions_obj.gif");
	public final ImageDescriptor DESC_OBJ_RPC = create(locBundle, OBJ, locPrefix + "rpc_obj.gif");
	public final ImageDescriptor DESC_OBJ_ENUM = create(locBundle, OBJ, locPrefix + "enum_obj.gif");
	public final ImageDescriptor DESC_OBJ_ENUM_ELEMENT = create(locBundle, OBJ, locPrefix + "enum_element_obj.gif");

	public final ImageDescriptor DESC_OVR_CUSTOM = create(locBundle, OVR, locPrefix + "custom_ovr.png");

	public final ImageDescriptor DESC_OBJ_GRAMMAR = create(locBundle, OBJ, locPrefix + "grammar_obj.png");
	public final ImageDescriptor DESC_OBJ_PARSER = create(locBundle, OBJ, locPrefix + "parser_obj.png");
	public final ImageDescriptor DESC_OBJ_LEXER = create(locBundle, OBJ, locPrefix + "lexer_obj.png");
	public final ImageDescriptor DESC_OBJ_ACTION = create(locBundle, OBJ, locPrefix + "action_obj.png");
	public final ImageDescriptor DESC_OBJ_OPTIONS = create(locBundle, OBJ, locPrefix + "options_obj.png");
	public final ImageDescriptor DESC_OBJ_MODE = create(locBundle, OBJ, locPrefix + "mode_obj.png");
	public final ImageDescriptor DESC_OBJ_LABEL = create(locBundle, OBJ, locPrefix + "label_obj.gif");

	public final ImageDescriptor DESC_OVR_COMBINED = create(locBundle, OVR, locPrefix + "combined_ovr.png");
	public final ImageDescriptor DESC_OVR_PARSER = create(locBundle, OVR, locPrefix + "parser_ovr.png");
	public final ImageDescriptor DESC_OVR_LEXER = create(locBundle, OVR, locPrefix + "lexer_ovr.png");
	public final ImageDescriptor DESC_OVR_TREE = create(locBundle, OVR, locPrefix + "tree_ovr.png");
	public final ImageDescriptor DESC_OVR_FRAGMENT = create(locBundle, OVR, locPrefix + "fragment_ovr.png");
	public final ImageDescriptor DESC_OVR_PROTECTED = create(locBundle, OVR, locPrefix + "protected_ovr.png");
	public final ImageDescriptor DESC_OVR_PRIVATE = create(locBundle, OVR, locPrefix + "private_ovr.png");

	public final ImageDescriptor DESC_OBJ_PARSER_FILTER = create(locBundle, OBJ, locPrefix + "filter_parser_obj.png");
	public final ImageDescriptor DESC_OBJ_LEXER_FILTER = create(locBundle, OBJ, locPrefix + "filter_lexer_obj.png");
	public final ImageDescriptor DESC_OBJ_OPTIONS_FILTER = create(locBundle, OBJ, locPrefix + "filter_options_obj.png");
	public final ImageDescriptor DESC_OBJ_ACTIONS_FILTER = create(locBundle, OBJ, locPrefix + "filter_action_obj.png");

	public final ImageDescriptor IMG_LAYOUT_CALL = create(locBundle, OBJ, locPrefix + "icon_call_layout.gif");
	public final ImageDescriptor IMG_LAYOUT_TREE_HORIZ = create(locBundle, OBJ,
			locPrefix + "icon_tree_layout_horizontal.gif");
	public final ImageDescriptor IMG_LAYOUT_TREE = create(locBundle, OBJ, locPrefix + "icon_tree_layout.gif");
	public final ImageDescriptor IMG_LAYOUT_RADIAL = create(locBundle, OBJ, locPrefix + "icon_radial_layout.gif");
	public final ImageDescriptor IMG_LAYOUT_SPRING = create(locBundle, OBJ, locPrefix + "icon_spring_layout.gif");
	public final ImageDescriptor IMG_LAYOUT_GRID = create(locBundle, OBJ, locPrefix + "icon_grid_layout.gif");
	public final ImageDescriptor IMG_LAYOUT_GRAPHFLOW = create(locBundle, OBJ, locPrefix + "icon_graphflow_layout.gif");
	public final ImageDescriptor IMG_LAYOUT_HORIZONTAL = create(locBundle, OBJ,
			locPrefix + "icon_horizontal_layout.gif");

	public final ImageDescriptor IMG_ZOOM_IN = create(locBundle, OBJ, locPrefix + "zoomin_nav.gif");
	public final ImageDescriptor IMG_ZOOM_OUT = create(locBundle, OBJ, locPrefix + "zoomout_nav.gif");
	public final ImageDescriptor IMG_ZOOM_FULL = create(locBundle, OBJ, locPrefix + "fit_diagram.png");
	public final ImageDescriptor IMG_ZOOM_NORM = create(locBundle, OBJ, locPrefix + "fit_normal.png");
	public final ImageDescriptor IMG_SNAPSHOT = create(locBundle, OBJ, locPrefix + "snapshot3.png");
	public final ImageDescriptor IMG_SAVEEDIT = create(locBundle, OBJ, locPrefix + "save_edit.gif");

	public final ImageDescriptor IMG_FORWARD_ENABLED = create(locBundle, OBJ, locPrefix + "forward_enabled.gif");
	public final ImageDescriptor IMG_BACKWARD_ENABLED = create(locBundle, OBJ, locPrefix + "backward_enabled.gif");
	public final ImageDescriptor IMG_REQ_PLUGIN_OBJ = create(locBundle, OBJ, locPrefix + "req_plugins_obj.gif");
	public final ImageDescriptor IMG_SEARCH_CANCEL = create(locBundle, OBJ, locPrefix + "progress_rem.gif");
	public final ImageDescriptor IMG_CLEAR = create(locBundle, OBJ, locPrefix + "clear_co.gif");
	public final ImageDescriptor IMG_FOCUS = create(locBundle, OBJ, locPrefix + "focus_obj.gif");
	public final ImageDescriptor IMG_PIN = create(locBundle, OBJ, locPrefix + "pin.gif");

	public final ImageDescriptor IMG_CALLEES = create(locBundle, OBJ, locPrefix + "ch_callees.gif");
	public final ImageDescriptor IMG_CALLERS = create(locBundle, OBJ, locPrefix + "ch_callers.gif");
	public final ImageDescriptor IMG_SUPERTYPES = create(locBundle, OBJ, locPrefix + "super_co.gif");
	public final ImageDescriptor IMG_SUBTYPES = create(locBundle, OBJ, locPrefix + "sub_co.gif");
	public final ImageDescriptor IMG_REMOVE = create(locBundle, OBJ, locPrefix + "interest-filtering.gif");
	public final ImageDescriptor IMG_GOTO = create(locBundle, OBJ, locPrefix + "goto_obj.gif");

	public final ImageDescriptor IMG_ACTION_ADD_PATHS = create(locBundle, OBJ, locPrefix + "ch_callees.gif");
	public final ImageDescriptor IMG_ACTION_REMOVE_PATHS = create(locBundle, OBJ, locPrefix + "remove_obj.gif");

	public final ImageDescriptor COLLAPSE = create(locBundle, OBJ, locPrefix + "collapseall.gif");
	public final ImageDescriptor EXPAND = create(locBundle, OBJ, locPrefix + "expandall.gif");
	public final ImageDescriptor LINK = create(locBundle, OBJ, locPrefix + "link_editor.gif");
	public final ImageDescriptor BUILD = create(locBundle, OBJ, locPrefix + "invocation.gif");
	public final ImageDescriptor LOAD = create(locBundle, OBJ, locPrefix + "loadGrammar.gif");
	public final ImageDescriptor CLEAR = create(locBundle, OBJ, locPrefix + "clearHistory.gif");
	public final ImageDescriptor HIDDEN = create(locBundle, OBJ, locPrefix + "annotationOutlined.gif");

	public final ImageDescriptor RULE_NODE = create(locBundle, OBJ, locPrefix + "ruleNode.png");
	public final ImageDescriptor TERMINAL_NODE = create(locBundle, OBJ, locPrefix + "terminalNode.png");
	public final ImageDescriptor ERROR_NODE = create(locBundle, OBJ, locPrefix + "errorNode.png");
	public final ImageDescriptor UNKNOWN_NODE = create(locBundle, OBJ, locPrefix + "unknown_obj.gif");

	public AntlrDTImages() {
		super();
		IMG_OBJS_TUNIT = locPrefix + "antlrFile.png"; //$NON-NLS-1$
		DESC_OBJS_TUNIT = create(locBundle, OBJ, IMG_OBJS_TUNIT);
	}

	@Override
	public ImageRegistry getImageRegistry() {
		return AntlrDTUI.getDefault().getImageRegistry();
	}
}
