package net.certiv.antlrdt.ui.editor.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.WhitespaceRule;

import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.ui.text.AbstractBufferedRuleBasedScanner;
import net.certiv.dsl.ui.text.rules.BalancedBraceRule;

public class ScannerAction extends AbstractBufferedRuleBasedScanner {

	private String[] fgTokenProperties;

	public ScannerAction(IDslPrefsManager store) {
		super(store);
		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		if (fgTokenProperties == null) {
			fgTokenProperties = new String[] { bind(PrefsKey.EDITOR_ACTION_COLOR) };
		}
		return fgTokenProperties;
	}

	@Override
	protected List<IRule> createRules() {
		IToken token = getToken(bind(PrefsKey.EDITOR_ACTION_COLOR));
		setDefaultReturnToken(token);

		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new BalancedBraceRule(Partitions.PREFIXES, Partitions.PREDICATES, token));
		rules.add(new WhitespaceRule(new WhitespaceDetector()));
		return rules;
	}
}
