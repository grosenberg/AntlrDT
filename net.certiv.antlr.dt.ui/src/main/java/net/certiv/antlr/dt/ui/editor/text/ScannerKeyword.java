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
package net.certiv.antlr.dt.ui.editor.text;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.rules.IRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.WhitespaceRule;

import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.core.preferences.consts.Editor;
import net.certiv.dsl.ui.editor.scanners.DslRuleBasedScanner;
import net.certiv.dsl.ui.editor.semantic.StylesManager;
import net.certiv.dsl.ui.editor.text.rules.CharSequenceRule;
import net.certiv.dsl.ui.editor.text.rules.CharsRule;
import net.certiv.dsl.ui.editor.text.rules.DslWordRule;

public class ScannerKeyword extends DslRuleBasedScanner {

	public static final String[] KEYWORDS = { //
			"grammar", "parser", "lexer", "options", "tokens", "import", "channels", "EOF", "channel", "HIDDEN", "SKIP",
			"mode", "skip", "more", "pushMode", "popMode", "type", "@header", "@members", "@parser::header",
			"@parser::members", "@lexer::header", "@lexer::members", "@init", "@after", "fragment", "public", "private",
			"protected", "locals", "throws", "catch", "returns", "finally" //
	};

	public static final char[] OPERATORS = { //
			':', ';', ',', '=', '+', '?', '*', '~', '|', '(', ')', '{', '}', '[', ']' //
	};

	public static final String[] SEQUENCES = { "..", "->" };

	private String[] props;

	public ScannerKeyword(IPrefsManager store, StylesManager stylesMgr) {
		super(store, stylesMgr);
		initialize();
	}

	@Override
	protected String[] getTokenProperties() {
		if (props == null) {
			props = new String[] { bind(Editor.EDITOR_KEYWORDS_COLOR), bind(Editor.EDITOR_SYMBOLS_COLOR) };
		}
		return props;
	}

	@Override
	protected List<IRule> createRules() {
		IToken key = getToken(bind(Editor.EDITOR_KEYWORDS_COLOR));
		IToken sym = getToken(bind(Editor.EDITOR_SYMBOLS_COLOR));

		List<IRule> rules = new ArrayList<>();
		rules.add(new WhitespaceRule(new WhitespaceDetector()));

		for (String seq : SEQUENCES) {
			rules.add(new CharSequenceRule(seq, sym));
		}
		rules.add(new CharsRule(OPERATORS, sym));

		DslWordRule keywordRule = new DslWordRule(new WordDetector());
		for (String word : KEYWORDS) {
			keywordRule.addWord(word, key);
		}
		rules.add(keywordRule);
		return rules;
	}
}
