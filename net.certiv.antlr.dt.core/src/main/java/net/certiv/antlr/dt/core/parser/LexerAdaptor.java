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
package net.certiv.antlr.dt.core.parser;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.misc.Interval;

import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Lexer;

public abstract class LexerAdaptor extends Lexer {

	public LexerAdaptor(CharStream input) {
		super(input);
	}

	/**
	 * Track whether we are inside of a rule and whether it is lexical parser.
	 * _currentRuleType==Token.INVALID_TYPE means that we are outside of a rule. At
	 * the first sign of a rule name reference and _currentRuleType==invalid, we can
	 * assume that we are starting a parser rule. Similarly, seeing a token
	 * reference when not already in rule means starting a token rule. The
	 * terminating ';' of a rule, flips this back to invalid type.
	 *
	 * The one exception is the '@header' and similar actions that can occur outside
	 * of a lexical rule. To avoid, we track the '@' tokens, and ignore the
	 * immediately following ID.
	 *
	 * This is not perfect logic but works. For example, "grammar T;" means that we
	 * start and stop a lexical rule for the "T;". Dangerous but works.
	 *
	 * The whole point of this state information is to distinguish between [..arg
	 * actions..] and [charsets]. Char sets can only occur in lexical rules and arg
	 * actions cannot occur.
	 */
	private int _currentRuleType = Token.INVALID_TYPE;
	private boolean _atType = false;

	public int getCurrentRuleType() {
		return _currentRuleType;
	}

	public void setCurrentRuleType(int ruleType) {
		this._currentRuleType = ruleType;
	}

	protected void handleBeginArgument() {
		if (inLexerRule()) {
			pushMode(AntlrDT4Lexer.LexerCharSet);
			more();
		} else {
			pushMode(AntlrDT4Lexer.Argument);
		}
	}

	protected void handleEndArgument() {
		popMode();
		if (_modeStack.size() > 0) {
			setType(AntlrDT4Lexer.ARGUMENT_CONTENT);
		}
	}

	protected void handleEndAction() {
		popMode();
		if (_modeStack.size() > 0) {
			setType(AntlrDT4Lexer.ACTION_CONTENT);
		}
	}

	@Override
	public Token emit() {
		switch (_type) {
			case AntlrDT4Lexer.AT:
				_atType = true; // beginning action
				break;
			case AntlrDT4Lexer.ID:
				String firstChar = _input.getText(Interval.of(_tokenStartCharIndex, _tokenStartCharIndex));
				if (Character.isUpperCase(firstChar.charAt(0))) {
					_type = AntlrDT4Lexer.TOKEN_REF;
				} else {
					_type = AntlrDT4Lexer.RULE_REF;
				}

				// if outside of rule def and not the ID in '@header' or '@member'
				if (!_atType && _currentRuleType == Token.INVALID_TYPE) {
					_currentRuleType = _type; // set to inside lexer or parser rule
				}
				_atType = false;
				break;
			case AntlrDT4Lexer.SEMI:				// exit rule def
				_currentRuleType = Token.INVALID_TYPE;
				_atType = false;
				break;
			default:
				_atType = false;
		}
		return super.emit();
	}

	private boolean inLexerRule() {
		return _currentRuleType == AntlrDT4Lexer.TOKEN_REF;
	}
}
