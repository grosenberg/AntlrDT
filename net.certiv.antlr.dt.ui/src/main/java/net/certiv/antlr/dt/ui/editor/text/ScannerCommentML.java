package net.certiv.antlr.dt.ui.editor.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.MultiLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.core.preferences.consts.Editor;
import net.certiv.dsl.ui.editor.scanners.DslRuleBasedScanner;
import net.certiv.dsl.ui.editor.semantic.StylesManager;

public class ScannerCommentML extends DslRuleBasedScanner {

	private String[] fgTokenProperties;

	public ScannerCommentML(IPrefsManager store, StylesManager stylesMgr) {
		super(store, stylesMgr);
		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		if (fgTokenProperties == null) {
			fgTokenProperties = new String[] { bind(Editor.EDITOR_COMMENT_BL_COLOR) };
		}
		return fgTokenProperties;
	}

	@Override
	protected List<IRule> createRules() {
		List<IRule> rules = new ArrayList<>();
		IToken token = getToken(bind(Editor.EDITOR_COMMENT_BL_COLOR));
		setDefaultReturnToken(token);

		rules.add(new MultiLineRule("/*", "*/", token, '\\'));
		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		return rules;
	}
}
