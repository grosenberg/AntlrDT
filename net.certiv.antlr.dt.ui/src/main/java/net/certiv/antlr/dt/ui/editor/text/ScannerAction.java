package net.certiv.antlr.dt.ui.editor.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.WhitespaceRule;

import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.ui.editor.Partitions;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.ui.editor.scanners.AbstractBufferedRuleBasedScanner;
import net.certiv.dsl.ui.editor.text.rules.BalancedBraceRule;

public class ScannerAction extends AbstractBufferedRuleBasedScanner {

	private String[] tokenProperties;

	public ScannerAction(IPrefsManager store) {
		super(store);
		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		if (tokenProperties == null) {
			tokenProperties = new String[] { bind(PrefsKey.EDITOR_ACTION_COLOR) };
		}
		return tokenProperties;
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
