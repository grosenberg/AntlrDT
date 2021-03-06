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

import java.util.Objects;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonToken;
import org.antlr.v4.runtime.TokenSource;
import org.antlr.v4.runtime.misc.Pair;

import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Lexer;
import net.certiv.dsl.core.parser.IDslToken;
import net.certiv.common.util.Strings;

public class AntlrToken extends CommonToken implements IDslToken {

	private int _mode;

	public AntlrToken(int type, String text) {
		super(type, text);
	}

	public AntlrToken(Pair<TokenSource, CharStream> source, int type, int channel, int start, int stop) {
		super(source, type, channel, start, stop);
	}

	@Override
	public int getMode() {
		return _mode;
	}

	public void setMode(int mode) {
		_mode = mode;
	}

	public String getTextEncoded() {
		return Strings.encode(getText());
	}

	public String getTypeName() {
		return AntlrDT4Lexer.VOCABULARY.getDisplayName(type);
	}

	@Override
	public int hashCode() {
		return Objects.hash(channel, charPositionInLine, index, line, source, start, stop, text, type, _mode);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof AntlrToken)) return false;
		AntlrToken o = (AntlrToken) obj;
		return channel == o.channel && charPositionInLine == o.charPositionInLine && index == o.index && line == o.line
				&& Objects.equals(source, o.source) && start == o.start && stop == o.stop
				&& Objects.equals(text, o.text) && type == o.type && _mode == o._mode;
	}

	@Override
	public String toString() {
		String text = getTextEncoded();
		String tname = getTypeName();
		String mname = _mode == 0 ? "default" : AntlrDT4Lexer.modeNames[_mode];
		String chan = channel == 0 ? "DEFAULT" : AntlrDT4Lexer.channelNames[channel];

		String msg = "%-2d %2d:%-2d %s (%s:%s:%s) '%s'";
		return String.format(msg, index, line, charPositionInLine, tname, type, mname, chan, text);
	}
}
