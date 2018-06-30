package net.certiv.antlrdt.core.preferences;

import org.eclipse.swt.graphics.RGB;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.DslPrefsInit;

/**
 * Initializer for the preferences unique to this plugin.
 */
public class PrefsInitializer extends DslPrefsInit {

	public PrefsInitializer() {
		super();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	public void initializeDefaultPreferences() {
		super.initializeDefaultPreferences();

		setRGB(PrefsKey.EDITOR_ACTION_COLOR, new RGB(70, 150, 170));
		setRGB(PrefsKey.EDITOR_ACTION_NAMED_COLOR, new RGB(220, 0, 0));

		setBool(PrefsKey.EDITOR_FOLDING_COMMENT_ML, true);
		setBool(PrefsKey.EDITOR_FOLDING_COMMENT_JD, false);
		setBool(PrefsKey.EDITOR_FOLDING_ACTION, false);
		setBool(PrefsKey.EDITOR_FOLDING_TOKENS, false);
		setBool(PrefsKey.EDITOR_FOLDING_OPTIONS, false);
		setBool(PrefsKey.EDITOR_FOLDING_INIT, false);
		setBool(PrefsKey.EDITOR_FOLDING_AFTER, false);
		setBool(PrefsKey.EDITOR_FOLDING_HEADER, false);
		setBool(PrefsKey.EDITOR_FOLDING_MEMBERS, false);
		setBool(PrefsKey.EDITOR_FOLDING_SCOPE, false);
		setBool(PrefsKey.EDITOR_FOLDING_RULECATCH, false);
		setBool(PrefsKey.EDITOR_FOLDING_CATCH, false);
		setBool(PrefsKey.EDITOR_FOLDING_FINALLY, false);

		setString(PrefsKey.BUILDER_USE, PrefsKey.BUILDER_USE_GRAMMAR);
		setString(PrefsKey.BUILDER_REL_PATH, "./gen/");
		setString(PrefsKey.BUILDER_ABS_PATH, "");

		// Initialize formatter preferences

		// header
		setString(PrefsKey.SPACE_SEMI_HDR, PrefsKey.AROUND);

		// options
		setString(PrefsKey.SPACE_EQ_OPT, PrefsKey.AROUND);
		setString(PrefsKey.SPACE_SEMI_OPT, PrefsKey.BEFORE);

		// tokens
		setString(PrefsKey.SPACE_LBRACE_TOK, PrefsKey.BEFORE);
		setString(PrefsKey.SPACE_RBRACE_TOK, PrefsKey.NONE);
		setString(PrefsKey.SPACE_COMMA_TOK, PrefsKey.AFTER);

		setString(PrefsKey.WRAP_LBRACE_TOK, PrefsKey.AFTER);
		setString(PrefsKey.WRAP_RBRACE_TOK, PrefsKey.BEFORE);
		setString(PrefsKey.WRAP_ID_TOK, PrefsKey.BEFORE);

		// channels
		setString(PrefsKey.SPACE_LBRACE_CHAN, PrefsKey.AROUND);
		setString(PrefsKey.SPACE_RBRACE_CHAN, PrefsKey.AROUND);
		setString(PrefsKey.SPACE_COMMA_CHAN, PrefsKey.AFTER);

		setString(PrefsKey.WRAP_LBRACE_CHAN, PrefsKey.NONE);
		setString(PrefsKey.WRAP_RBRACE_CHAN, PrefsKey.NONE);
		setString(PrefsKey.WRAP_ID_CHAN, PrefsKey.NONE);

		// rules

		setString(PrefsKey.WRAP_NAME_RULE, PrefsKey.AFTER);
		setString(PrefsKey.WRAP_COLON_RULE, PrefsKey.BEFORE);
		setString(PrefsKey.WRAP_ALT_OR, PrefsKey.BEFORE);
		setString(PrefsKey.WRAP_SEMI_RULE, PrefsKey.AFTER);

		setString(PrefsKey.SPACE_COLON_RULE, PrefsKey.AFTER);
		setString(PrefsKey.SPACE_ALT_OR, PrefsKey.AROUND);
		setString(PrefsKey.SPACE_SEMI_RULE, PrefsKey.AROUND);
		setString(PrefsKey.SPACE_LBRACKET_RULE, PrefsKey.AFTER);
		setString(PrefsKey.SPACE_RBRACKET_RULE, PrefsKey.BEFORE);

		// rule @id action

		setString(PrefsKey.WRAP_LBRACE_AT, PrefsKey.AFTER);
		setString(PrefsKey.WRAP_RBRACE_AT, PrefsKey.AROUND);

		setString(PrefsKey.SPACE_LBRACE_AT, PrefsKey.AROUND);
		setString(PrefsKey.SPACE_RBRACE_AT, PrefsKey.AROUND);

		// rule action

		setString(PrefsKey.WRAP_LBRACE_ACT, PrefsKey.AFTER);
		setString(PrefsKey.WRAP_RBRACE_ACT, PrefsKey.AROUND);

		setString(PrefsKey.SPACE_LBRACE_ACT, PrefsKey.AROUND);
		setString(PrefsKey.SPACE_RBRACE_ACT, PrefsKey.AROUND);

		// indent

		setBool(PrefsKey.FORMATTER_INDENT_COMMENT_MULTILINE, false);
		setBool(PrefsKey.FORMATTER_INDENT_COMMENT_SINGLELINE, false);

		setBool(PrefsKey.FORMATTER_NATIVE_ENABLE, false);

		// unused

		// setString(PrefsKey.SPACE_COMMA, PrefsKey.AFTER);
		// setString(PrefsKey.SPACE_LBRACE_ACT, PrefsKey.AROUND);
		// setString(PrefsKey.SPACE_RBRACE_ACT, PrefsKey.AFTER);
		// setString(PrefsKey.INDENT_AT, PrefsKey.BEFORE);
		// setString(PrefsKey.LIST_ID, PrefsKey.AFTER);
		// setString(PrefsKey.WRAP_EQ, PrefsKey.NONE);
		// setString(PrefsKey.WRAP_COMMA, PrefsKey.AFTER);
		// setString(PrefsKey.WRAP_SEMI, PrefsKey.AFTER);

		// ------------------------------------------------------------------------------
		// Other

		setString(PrefsKey.SNIPPETTEST_BASEDIR_SOURCE, "");

		// ------------------------------------------------------------------------------
		// Paths View

		setInt(PrefsKey.PT_DEPTH_LIMIT, 2);

		setRGB(PrefsKey.PT_CONN_COLOR, new RGB(128, 128, 128));
		setRGB(PrefsKey.PT_CONN_HIGHLIGHT, new RGB(127, 0, 0));
		setRGB(PrefsKey.PT_NODE_FOREGROUND, new RGB(0, 0, 0));
		setRGB(PrefsKey.PT_NODE_BACKGROUND, new RGB(216, 228, 248));
		setRGB(PrefsKey.PT_NODE_HIGHLIGHT, new RGB(255, 255, 0));
		setRGB(PrefsKey.PT_NODE_ADJACENT, new RGB(255, 196, 0));

		setRGB(PrefsKey.PT_NODE_RULE, new RGB(216, 228, 248));
		setRGB(PrefsKey.PT_NODE_TERMINAL, new RGB(220, 220, 220));
		setRGB(PrefsKey.PT_NODE_ERROR, new RGB(255, 153, 153));

		setString(PrefsKey.PT_TOOLTIP_TYPE, PrefsKey.PT_TTT_ENH);
		setString(PrefsKey.PT_ENH_POSITION, PrefsKey.PT_ENH_FLOAT);

		setInt(PrefsKey.PT_KILL_DELAY, 200);
		setInt(PrefsKey.PT_TIP_DURATION, 5000);
		setBool(PrefsKey.PT_SURFACE_DRAG_ENABLED, true);
		setBool(PrefsKey.PT_DOUBLECLICK_FOCUS_ENABLED, false);
		setBool(PrefsKey.PT_PIN_ENABLED, false);

		setInt(PrefsKey.PT_GAP_FLOAT, 26);
		setInt(PrefsKey.PT_GAP_ENH_X, 10);
		setInt(PrefsKey.PT_GAP_ENH_Y, 5);

		setInt(PrefsKey.PT_VERT_SPACING, 30);
		setInt(PrefsKey.PT_HORZ_SPACING, 60);

		setInt(PrefsKey.PT_SOURCE_LEAD, 16);
		setInt(PrefsKey.PT_TARGET_LEAD, 24);

		setBool(PrefsKey.PT_FIND_IMPL, true);

		setInt(PrefsKey.PT_SIBLING_SPACING, 5);
		setInt(PrefsKey.PT_SUBTREE_SPACING, 25);
		setInt(PrefsKey.PT_DEPTH_SPACING, 50);
		setInt(PrefsKey.PT_ROOT_OFFSET, 50);

		setInt(PrefsKey.PT_ANIMATION_LIMIT, 500);

	}
}
