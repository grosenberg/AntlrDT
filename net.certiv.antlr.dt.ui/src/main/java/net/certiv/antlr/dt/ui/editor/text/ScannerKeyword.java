package net.certiv.antlr.dt.ui.editor.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.WhitespaceRule;

import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.ui.editor.scanners.AbstractBufferedRuleBasedScanner;
import net.certiv.dsl.ui.editor.text.rules.CharSequenceRule;
import net.certiv.dsl.ui.editor.text.rules.CharsRule;
import net.certiv.dsl.ui.editor.text.rules.DslWordRule;

public class ScannerKeyword extends AbstractBufferedRuleBasedScanner {

	public static final String[] KEYWORDS = { "grammar", "parser", "lexer", "options", "tokens", "import", "channels",
			"EOF", "channel", "HIDDEN", "SKIP", "mode", "skip", "more", "pushMode", "popMode", "type", "@header",
			"@members", "@parser::header", "@parser::members", "@lexer::header", "@lexer::members", "@init", "@after",
			"fragment", "public", "private", "protected", "locals", "throws", "catch", "returns", "finally" };

	public static final char[] OPERATORS = { ':', ';', ',', '=', '+', '?', '*', '~', '|', '(', ')', '{', '}', '[',
			']' };

	public static final String[] SEQUENCES = { "..", "->" };

	private String[] tokenProperties;

	public ScannerKeyword(IPrefsManager store) {
		super(store);
		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		if (tokenProperties == null) {
			tokenProperties = new String[] { bind(PrefsKey.EDITOR_KEYWORDS_COLOR) };
		}
		return tokenProperties;
	}

	@Override
	protected List<IRule> createRules() {
		IToken keyToken = getToken(bind(PrefsKey.EDITOR_KEYWORDS_COLOR));

		List<IRule> rules = new ArrayList<IRule>();
		rules.add(new WhitespaceRule(new WhitespaceDetector()));

		for (String seq : SEQUENCES) {
			rules.add(new CharSequenceRule(seq, keyToken));
		}

		rules.add(new CharsRule(OPERATORS, keyToken));

		DslWordRule rule = new DslWordRule(new WordDetector());
		for (String word : KEYWORDS) {
			rule.addWord(word, keyToken);
		}
		rules.add(rule);
		return rules;
	}
}
