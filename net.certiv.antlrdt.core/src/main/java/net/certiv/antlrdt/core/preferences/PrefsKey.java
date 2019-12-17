package net.certiv.antlrdt.core.preferences;

import net.certiv.dsl.core.preferences.DslPrefsKey;

/**
 * Preference keys that are unique to this Dsl plugin. Prefix with the Dsl
 * plugin ID just to ensure.
 */
public class PrefsKey extends DslPrefsKey {

	// rule-based colors
	public static final String EDITOR_ACTION_COLOR = "{DSL_ID}" + ".action";
	public static final String EDITOR_ACTION_BOLD = EDITOR_ACTION_COLOR + BOLD_SUFFIX;
	public static final String EDITOR_ACTION_ITALIC = EDITOR_ACTION_COLOR + ITALIC_SUFFIX;
	public static final String EDITOR_ACTION_STRIKE = EDITOR_ACTION_COLOR + STRIKE_SUFFIX;
	public static final String EDITOR_ACTION_UNDERLINE = EDITOR_ACTION_COLOR + UNDERLINE_SUFFIX;

	public static final String EDITOR_ACTION_NAME_COLOR = "{DSL_ID}" + ".actionName";
	public static final String EDITOR_ACTION_NAME_BOLD = EDITOR_ACTION_NAME_COLOR + BOLD_SUFFIX;
	public static final String EDITOR_ACTION_NAME_ITALIC = EDITOR_ACTION_NAME_COLOR + ITALIC_SUFFIX;
	public static final String EDITOR_ACTION_NAME_STRIKE = EDITOR_ACTION_NAME_COLOR + STRIKE_SUFFIX;
	public static final String EDITOR_ACTION_NAME_UNDERLINE = EDITOR_ACTION_NAME_COLOR + UNDERLINE_SUFFIX;

	// semantic colors
	public static final String EDITOR_RULE_NAME_COLOR = "{DSL_ID}" + ".rulename";
	public static final String EDITOR_RULE_NAME_BOLD = EDITOR_RULE_NAME_COLOR + BOLD_SUFFIX;
	public static final String EDITOR_RULE_NAME_ITALIC = EDITOR_RULE_NAME_COLOR + ITALIC_SUFFIX;
	public static final String EDITOR_RULE_NAME_STRIKE = EDITOR_RULE_NAME_COLOR + STRIKE_SUFFIX;
	public static final String EDITOR_RULE_NAME_UNDERLINE = EDITOR_RULE_NAME_COLOR + UNDERLINE_SUFFIX;

	public static final String EDITOR_MODE_NAME_COLOR = "{DSL_ID}" + ".modename";
	public static final String EDITOR_MODE_NAME_BOLD = EDITOR_MODE_NAME_COLOR + BOLD_SUFFIX;
	public static final String EDITOR_MODE_NAME_ITALIC = EDITOR_MODE_NAME_COLOR + ITALIC_SUFFIX;
	public static final String EDITOR_MODE_NAME_STRIKE = EDITOR_MODE_NAME_COLOR + STRIKE_SUFFIX;
	public static final String EDITOR_MODE_NAME_UNDERLINE = EDITOR_MODE_NAME_COLOR + UNDERLINE_SUFFIX;

	// folding
	public static final String EDITOR_FOLDING_COMMENT_ML = "{DSL_ID}" + ".editorFoldingCommentML";
	public static final String EDITOR_FOLDING_COMMENT_JD = "{DSL_ID}" + ".editorFoldingCommentJD";
	public static final String EDITOR_FOLDING_ACTION = "{DSL_ID}" + ".editorFoldingActions";
	public static final String EDITOR_FOLDING_TOKENS = "{DSL_ID}" + ".editorFoldingTokens";
	public static final String EDITOR_FOLDING_OPTIONS = "{DSL_ID}" + ".editorFoldingOptions";
	public static final String EDITOR_FOLDING_INIT = "{DSL_ID}" + ".editorFoldingInit";
	public static final String EDITOR_FOLDING_AFTER = "{DSL_ID}" + ".editorFoldingAfter";
	public static final String EDITOR_FOLDING_HEADER = "{DSL_ID}" + ".editorFoldingHeader";
	public static final String EDITOR_FOLDING_MEMBERS = "{DSL_ID}" + ".editorFoldingMembers";
	public static final String EDITOR_FOLDING_SCOPE = "{DSL_ID}" + ".editorFoldingScope";
	public static final String EDITOR_FOLDING_RULECATCH = "{DSL_ID}" + ".editorFoldingRulecatch";
	public static final String EDITOR_FOLDING_CATCH = "{DSL_ID}" + ".editorFoldingCatch";
	public static final String EDITOR_FOLDING_FINALLY = "{DSL_ID}" + ".editorFoldingFinally";

	// formatter prefixes
	private static final String FORMAT_SPACE = "{DSL_ID}" + ".formatSpace";
	private static final String FORMAT_WRAP = "{DSL_ID}" + ".formatWrap";

	// header
	public static final String SPACE_SEMI_HDR = FORMAT_SPACE + "SemiHdr";

	// options
	public static final String SPACE_EQ_OPT = FORMAT_SPACE + "Eq";
	public static final String SPACE_SEMI_OPT = FORMAT_SPACE + "SemiOptions";

	// tokens
	public static final String SPACE_LBRACE_TOK = FORMAT_SPACE + "LBraceTok";
	public static final String SPACE_RBRACE_TOK = FORMAT_SPACE + "RBraceTok";
	public static final String SPACE_COMMA_TOK = FORMAT_SPACE + "CommaTok";

	public static final String WRAP_LBRACE_TOK = FORMAT_WRAP + "LBraceTok";
	public static final String WRAP_RBRACE_TOK = FORMAT_WRAP + "RBraceTok";
	public static final String WRAP_ID_TOK = FORMAT_WRAP + "IdTok";

	// channels
	public static final String SPACE_LBRACE_CHAN = FORMAT_SPACE + "LBraceChan";
	public static final String SPACE_RBRACE_CHAN = FORMAT_SPACE + "RBraceChan";
	public static final String SPACE_COMMA_CHAN = FORMAT_SPACE + "CommaChan";

	public static final String WRAP_LBRACE_CHAN = FORMAT_WRAP + "LBraceChan";
	public static final String WRAP_RBRACE_CHAN = FORMAT_WRAP + "RBraceChan";
	public static final String WRAP_ID_CHAN = FORMAT_WRAP + "IdChan";

	// rules

	public static final String WRAP_NAME_RULE = FORMAT_WRAP + "NameRule";
	public static final String WRAP_COLON_RULE = FORMAT_WRAP + "ColonRule";
	public static final String WRAP_ALT_OR = FORMAT_WRAP + "AltOR";
	public static final String WRAP_SEMI_RULE = FORMAT_WRAP + "SemiRule";

	public static final String SPACE_COLON_RULE = FORMAT_SPACE + "Colon";
	public static final String SPACE_ALT_OR = FORMAT_SPACE + "AltOr";
	public static final String SPACE_SEMI_RULE = FORMAT_SPACE + "SemiRule";

	public static final String SPACE_LBRACKET_RULE = FORMAT_SPACE + "LBracket";
	public static final String SPACE_RBRACKET_RULE = FORMAT_SPACE + "RBracket";

	// rule @id action

	// public static final String WRAP_AT_RULE = FORMAT_WRAP + "AtRule";
	public static final String WRAP_LBRACE_AT = FORMAT_WRAP + "LBraceAt";
	public static final String WRAP_RBRACE_AT = FORMAT_WRAP + "RBraceAt";

	public static final String SPACE_LBRACE_AT = FORMAT_SPACE + "LBraceAt";
	public static final String SPACE_RBRACE_AT = FORMAT_SPACE + "RBraceAt";

	// rule action

	public static final String WRAP_LBRACE_ACT = FORMAT_WRAP + "LBrace";
	public static final String WRAP_RBRACE_ACT = FORMAT_WRAP + "RBrace";
	public static final String SPACE_LBRACE_ACT = FORMAT_SPACE + "LBrace";
	public static final String SPACE_RBRACE_ACT = FORMAT_SPACE + "RBrace";

	// unused

	// public static final String SPACE_COMMA = FORMAT_SPACE + "Comma";
	// public static final String INDENT_AT = FORMAT_INDENT + "At";

	// public static final String WRAP_EQ = FORMAT_WRAP + "Eq";
	// public static final String WRAP_COMMA = FORMAT_WRAP + "Comma";
	// public static final String WRAP_SEMI = FORMAT_WRAP + "Semi";
	//
	// public static final String LIST_ID = FORMAT_LIST + "Id";
	//
	// public static final String STYLE_RULE_LEAD = FORMAT_STYLE + "RuleLead";
	// public static final String STYLE_RULE_TRAIL = FORMAT_STYLE + "RuleTrail";

	// --------------

	public static final String SNIPPETTEST_BASEDIR_SOURCE = "{DSL_ID}" + ".SnippetTest.BaseDir.Source";

	public static final String PARSE_TIMEOUT = "{DSL_ID}" + ".ParseTimeout";

	public static final String PT_VERT_SPACING = "{DSL_ID}" + ".ptVerticalSpacing";
	public static final String PT_HORZ_SPACING = "{DSL_ID}" + ".ptHorizontalSpacing";

	public static final String PT_TOOLTIP_TYPE = "{DSL_ID}" + ".ptToolTipType";
	public static final String PT_TTT_MIN = "ptMinimal";
	public static final String PT_TTT_STD = "ptStandard";
	public static final String PT_TTT_ENH = "ptEnhanced";

	public static final String PT_KILL_DELAY = "{DSL_ID}" + ".ptKillDelay";
	public static final String PT_TIP_DURATION = "{DSL_ID}" + ".ptTipDuration";
	public static final String PT_SURFACE_DRAG_ENABLED = "{DSL_ID}" + ".ptSurfaceDragEnabled";

	public static final String PT_ENH_POSITION = "{DSL_ID}" + ".ptEnhancedPosition";
	public static final String PT_ENH_ALIGNED = "ptEnhancedAligned";
	public static final String PT_ENH_FLOAT = "ptEnhancedFloat";

	public static final String PT_GAP_FLOAT = "{DSL_ID}" + ".ptFloatGap";
	public static final String PT_GAP_ENH_X = "{DSL_ID}" + ".ptEnhGapX";
	public static final String PT_GAP_ENH_Y = "{DSL_ID}" + ".ptEnhGapY";
	public static final String PT_DOUBLECLICK_FOCUS_ENABLED = "{DSL_ID}" + ".ptFocusEnabled";
	public static final String PT_PIN_ENABLED = "{DSL_ID}" + ".ptPinEnabled";
	public static final String PT_SOURCE_LEAD = "{DSL_ID}" + ".ptSourceLead";
	public static final String PT_TARGET_LEAD = "{DSL_ID}" + ".ptTargetLead";
	public static final String PT_LAYER_MAX_ITEMS = "{DSL_ID}" + ".ptLayerMaxItems";

	public static final String PT_FIND_IMPL = "{DSL_ID}" + ".ptFindImplementor";

	// for Improved Walker's Tree Layout Algorithm
	public static final String PT_SIBLING_SPACING = "{DSL_ID}" + ".ptSiblingSpacing";
	public static final String PT_SUBTREE_SPACING = "{DSL_ID}" + ".ptSubtreeSpacing";
	public static final String PT_DEPTH_SPACING = "{DSL_ID}" + ".ptDepthSpacing";
	public static final String PT_ROOT_OFFSET = "{DSL_ID}" + ".ptRootOffset";

	public static final String PT_TOOLTIP_COLOR = "{DSL_ID}" + ".ptTooltipColor";

	public static final String PT_COLOR_PREFIX = "{DSL_ID}" + ".ptColor";
	public static final String PT_CONN_COLOR = PT_COLOR_PREFIX + "Conn";
	public static final String PT_CONN_HIGHLIGHT = PT_COLOR_PREFIX + "ConnHighlight";
	public static final String PT_CONN_WEIGHT = PT_COLOR_PREFIX + "ConnWeight";

	public static final String PT_NODE_FOREGROUND = PT_COLOR_PREFIX + "NodeForeground";
	public static final String PT_NODE_BACKGROUND = PT_COLOR_PREFIX + "NodeBackground";
	public static final String PT_NODE_HIGHLIGHT = PT_COLOR_PREFIX + "NodeHighlight";
	public static final String PT_NODE_ADJACENT = PT_COLOR_PREFIX + "NodeAdjacent";

	public static final String PT_NODE_ERROR = PT_COLOR_PREFIX + "NodeError";
	public static final String PT_NODE_TERMINAL = PT_COLOR_PREFIX + "NodeTerminal";
	public static final String PT_NODE_RULE = PT_COLOR_PREFIX + "NodeRule";

	public static final String PT_ANIMATION_LIMIT = "{DSL_ID}" + ".ptAnimationLimit";

	public static final String PT_DEPTH_LIMIT = "{DSL_ID}" + ".ptDepthLimit";

	public static final String PT_SHOW_GRID = "{DSL_ID}" + ".ptShowGrid";

}
