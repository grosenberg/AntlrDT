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

import net.certiv.dsl.ui.editor.text.rules.IWordDetector2;

public class WordDetector implements IWordDetector2 {

	@Override
	public boolean isWordStart(char c) {
		return Character.isJavaIdentifierStart(c) || c == '@' || c == '-';
	}

	@Override
	public boolean isWordPart(char c) {
		return Character.isJavaIdentifierPart(c) || c == '_' || c == '>' || c == ':';
	}

	@Override
	public boolean isPriorCharValid(char c) {
		return Character.isWhitespace(c);
	}
}
