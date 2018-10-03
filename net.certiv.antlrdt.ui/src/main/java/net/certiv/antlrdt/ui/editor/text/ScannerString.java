package net.certiv.antlrdt.ui.editor.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.SingleLineRule;
import org.eclipse.jface.text.rules.WhitespaceRule;

import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.ui.editor.scanners.AbstractBufferedRuleBasedScanner;

public class ScannerString extends AbstractBufferedRuleBasedScanner {

	private String[] tokenProperties;

	public ScannerString(IDslPrefsManager store) {
		super(store);
		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		if (tokenProperties == null) {
			tokenProperties = new String[] { bind(PrefsKey.EDITOR_STRING_COLOR) };
		}
		return tokenProperties;
	}

	// Have to evaluate bracketed strings before single quoted strings to avoid a single quote
	// character being recognized within a bracket string
	@Override
	protected List<IRule> createRules() {
		IToken token = getToken(bind(PrefsKey.EDITOR_STRING_COLOR));
		setDefaultReturnToken(token);

		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		// rules.add(new NestingDelimiterRule("[", "]", token, '\\'));
		// rules.add(new SingleLineRule("[", "]", token, '\\')); // non-nested
		rules.add(new SingleLineRule("'", "'", token, '\\', true));
		return rules;
	}
}
