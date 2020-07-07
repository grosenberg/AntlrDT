package net.certiv.antlr.dt.ui.editor.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.core.preferences.consts.Editor;
import net.certiv.dsl.ui.editor.scanners.DslRuleBasedScanner;
import net.certiv.dsl.ui.editor.semantic.StylesManager;

public class ScannerString extends DslRuleBasedScanner {

	private String[] props;

	public ScannerString(IPrefsManager store, StylesManager stylesMgr) {
		super(store, stylesMgr);
		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		if (props == null) {
			props = new String[] { bind(Editor.EDITOR_STRING_COLOR) };
		}
		return props;
	}

	// Have to evaluate bracketed strings before single quoted strings to avoid a single quote
	// character being recognized within a bracket string
	@Override
	protected List<IRule> createRules() {
		IToken token = getToken(bind(Editor.EDITOR_STRING_COLOR));
		setDefaultReturnToken(token);

		List<IRule> rules = new ArrayList<>();
		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		// rules.add(new NestingDelimiterRule("[", "]", token, '\\'));
		// rules.add(new SingleLineRule("[", "]", token, '\\')); // non-nested
		rules.add(new SingleLineRule("'", "'", token, '\\', true));
		return rules;
	}
}
