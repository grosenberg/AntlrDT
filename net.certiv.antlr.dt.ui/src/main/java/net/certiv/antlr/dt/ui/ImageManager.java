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

import org.osgi.framework.Bundle;

import org.eclipse.jface.resource.ImageRegistry;

import net.certiv.dsl.core.util.Chars;
import net.certiv.dsl.ui.DslImageManager;

public class ImageManager extends DslImageManager {

	private static final Bundle locBundle = AntlrUI.getDefault().getBundle();
	private static final String locPrefix = locBundle.getSymbolicName() + Chars.DOT;

	public final String IMG_OBJ_BUFFER = create(locBundle, OBJ, locPrefix + "buffer.png");
	public final String IMG_OBJ_MESSAGE = create(locBundle, OBJ, locPrefix + "message_obj.png");
	public final String IMG_OBJ_REQUIRED = create(locBundle, OBJ, locPrefix + "required_obj.png");
	public final String IMG_OBJ_OPTION = create(locBundle, OBJ, locPrefix + "option_obj.png");

	public final String IMG_OBJ_IMPORT = create(locBundle, OBJ, locPrefix + "import_obj.png");
	public final String IMG_OBJ_PACKAGE = create(locBundle, OBJ, locPrefix + "packageBlue_obj.png");
	public final String IMG_OBJ_EXTEND = create(locBundle, OBJ, locPrefix + "extend_obj.png");
	public final String IMG_OBJ_SERVICE = create(locBundle, OBJ, locPrefix + "service_obj.png");
	public final String IMG_OBJ_OPTIONAL = create(locBundle, OBJ, locPrefix + "optional_obj.png");
	public final String IMG_OBJ_OPTION_ELEMENT = create(locBundle, OBJ, locPrefix + "optional_obj.png");
	public final String IMG_OBJ_REPEATED = create(locBundle, OBJ, locPrefix + "repeat_obj.png");
	public final String IMG_OBJ_EXTENSIONS = create(locBundle, OBJ, locPrefix + "extensions_obj.png");
	public final String IMG_OBJ_RPC = create(locBundle, OBJ, locPrefix + "rpc_obj.png");
	public final String IMG_OBJ_ENUM = create(locBundle, OBJ, locPrefix + "enum_obj.png");
	public final String IMG_OBJ_ENUM_ELEMENT = create(locBundle, OBJ, locPrefix + "enum_element_obj.png");

	public final String IMG_OVR_CUSTOM = create(locBundle, OVR, locPrefix + "custom_ovr.png");

	public final String IMG_OBJ_GRAMMAR = create(locBundle, OBJ, locPrefix + "module.png");
	public final String IMG_OBJ_PARSER = create(locBundle, OBJ, locPrefix + "node_parser.png");
	public final String IMG_OBJ_LEXER = create(locBundle, OBJ, locPrefix + "node_lexer.png");
	public final String IMG_OBJ_FRAGMENT = create(locBundle, OBJ, locPrefix + "node_fragment.png");

	public final String IMG_OBJ_ACTION = create(locBundle, OBJ, locPrefix + "action_obj.png");
	public final String IMG_OBJ_ERROR = create(locBundle, OBJ, locPrefix + "node_error.png");
	public final String IMG_OBJ_OPTIONS = create(locBundle, OBJ, locPrefix + "options_obj.png");
	public final String IMG_OBJ_MODE = create(locBundle, OBJ, locPrefix + "mode_obj.png");
	public final String IMG_OBJ_LABEL = create(locBundle, OBJ, locPrefix + "label_obj.png");
	public final String IMG_OBJ_RULE_COLON = create(locBundle, OBJ, locPrefix + "ruleColon.png");
	public final String IMG_OBJ_RULE = create(locBundle, OBJ, locPrefix + "node_rule.png");
	public final String IMG_OBJ_RANGE = create(locBundle, OBJ, locPrefix + "node_range.png");
	public final String IMG_OBJ_SET = create(locBundle, OBJ, locPrefix + "node_set.png");
	public final String IMG_OBJ_TERMINAL = create(locBundle, OBJ, locPrefix + "node_terminal.png");
	public final String IMG_OBJ_VALUE = create(locBundle, OBJ, locPrefix + "genericvariable_obj.png");
	public final String IMG_OBJ_UNKNOWN = create(locBundle, OBJ, locPrefix + "node_unknown.png");

	public final String IMG_OVR_COMBINED = create(locBundle, OVR, locPrefix + "combined_ovr.png");
	public final String IMG_OVR_PARSER = create(locBundle, OVR, locPrefix + "parser_ovr.png");
	public final String IMG_OVR_LEXER = create(locBundle, OVR, locPrefix + "lexer_ovr.png");
	public final String IMG_OVR_TREE = create(locBundle, OVR, locPrefix + "tree_ovr.png");
	public final String IMG_OVR_FRAGMENT = create(locBundle, OVR, locPrefix + "fragment_ovr.png");
	public final String IMG_OVR_PROTECTED = create(locBundle, OVR, locPrefix + "protected_ovr.png");
	public final String IMG_OVR_PRIVATE = create(locBundle, OVR, locPrefix + "private_ovr.png");

	public final String IMG_OBJ_PARSER_FILTER = create(locBundle, OBJ, locPrefix + "filter_parser_obj.png");
	public final String IMG_OBJ_LEXER_FILTER = create(locBundle, OBJ, locPrefix + "filter_lexer_obj.png");
	public final String IMG_OBJ_OPTIONS_FILTER = create(locBundle, OBJ, locPrefix + "filter_options_obj.png");
	public final String IMG_OBJ_ACTIONS_FILTER = create(locBundle, OBJ, locPrefix + "filter_action_obj.png");

	public final String IMG_LAYOUT_CALL = create(locBundle, OBJ, locPrefix + "icon_call_layout.png");
	public final String IMG_LAYOUT_TREE_HORIZ = create(locBundle, OBJ, locPrefix + "icon_tree_layout_horizontal.png");
	public final String IMG_LAYOUT_TREE = create(locBundle, OBJ, locPrefix + "icon_tree_layout.png");
	public final String IMG_LAYOUT_RADIAL = create(locBundle, OBJ, locPrefix + "icon_radial_layout.png");
	public final String IMG_LAYOUT_SPRING = create(locBundle, OBJ, locPrefix + "icon_spring_layout.png");
	public final String IMG_LAYOUT_GRID = create(locBundle, OBJ, locPrefix + "icon_grid_layout.png");
	public final String IMG_LAYOUT_GRAPHFLOW = create(locBundle, OBJ, locPrefix + "icon_graphflow_layout.png");
	public final String IMG_LAYOUT_HORIZONTAL = create(locBundle, OBJ, locPrefix + "icon_horizontal_layout.png");
	public final String IMG_LAYOUT_TREE_ORTHO = create(locBundle, OBJ, locPrefix + "icon_tree_ortho_layout.png");

	public final String IMG_ZOOM_IN = create(locBundle, OBJ, locPrefix + "zoomin_nav.png");
	public final String IMG_ZOOM_OUT = create(locBundle, OBJ, locPrefix + "zoomout_nav.png");
	public final String IMG_ZOOM_FULL = create(locBundle, OBJ, locPrefix + "fit_diagram.png");
	public final String IMG_ZOOM_NORM = create(locBundle, OBJ, locPrefix + "fit_normal.png");
	public final String IMG_SNAPSHOT = create(locBundle, OBJ, locPrefix + "snapshot3.png");
	public final String IMG_SAVEEDIT = create(locBundle, OBJ, locPrefix + "save_edit.png");

	public final String IMG_FORWARD_ENABLED = create(locBundle, OBJ, locPrefix + "forward_enabled.png");
	public final String IMG_BACKWARD_ENABLED = create(locBundle, OBJ, locPrefix + "backward_enabled.png");
	public final String IMG_REQ_PLUGIN_OBJ = create(locBundle, OBJ, locPrefix + "req_plugins_obj.png");
	public final String IMG_SEARCH_CANCEL = create(locBundle, OBJ, locPrefix + "progress_rem.png");
	public final String IMG_CLEAR = create(locBundle, OBJ, locPrefix + "clear_co.png");
	public final String IMG_FOCUS = create(locBundle, OBJ, locPrefix + "focus_obj.png");
	public final String IMG_PIN = create(locBundle, OBJ, locPrefix + "pin.png");

	public final String IMG_CALLEES = create(locBundle, OBJ, locPrefix + "ch_callees.png");
	public final String IMG_CALLERS = create(locBundle, OBJ, locPrefix + "ch_callers.png");
	public final String IMG_SUPERTYPES = create(locBundle, OBJ, locPrefix + "super_co.png");
	public final String IMG_SUBTYPES = create(locBundle, OBJ, locPrefix + "sub_co.png");
	public final String IMG_REMOVE = create(locBundle, OBJ, locPrefix + "interest-filtering.png");
	public final String IMG_GOTO = create(locBundle, OBJ, locPrefix + "goto_obj.png");

	public final String IMG_ACTION_ADD_CALLERS = create(locBundle, OBJ, locPrefix + "ch_callers.png");
	public final String IMG_ACTION_ADD_PATHS = create(locBundle, OBJ, locPrefix + "ch_callees.png");
	public final String IMG_ACTION_REMOVE_PATHS = create(locBundle, OBJ, locPrefix + "remove_obj.png");

	public final String COLLAPSE = create(locBundle, OBJ, locPrefix + "collapseall.png");
	public final String EXPAND = create(locBundle, OBJ, locPrefix + "expandall.png");
	public final String LINK = create(locBundle, OBJ, locPrefix + "link_editor.png");
	public final String BUILD = create(locBundle, OBJ, locPrefix + "invocation.png");
	public final String LOAD = create(locBundle, OBJ, locPrefix + "loadGrammar.png");
	public final String CLEAR = create(locBundle, OBJ, locPrefix + "clearHistory.png");
	public final String HIDDEN = create(locBundle, OBJ, locPrefix + "annotationOutlined.png");
	public final String HISTORY = create(locBundle, OBJ, locPrefix + "history_list.png");

	public ImageManager() {
		super();
		IMG_OBJS_UNIT = create(locBundle, OBJ, locPrefix + "antlrFile.png"); //$NON-NLS-1$
	}

	@Override
	public ImageRegistry getRegistry() {
		return AntlrUI.getDefault().getImageRegistry();
	}
}
