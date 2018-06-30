/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format;

import java.util.Comparator;

import net.certiv.adept.ITextEdit;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.util.Strings;

public class TextEdit implements ITextEdit, Comparator<TextEdit> {

	private static final String Msg = "%6s @%s:%s [%s-%s] %s:%s '%s' -> '%s'";

	private int begIndex;			// beg token index exclusive
	private int endIndex;			// end token index exclusive

	private String existing;		// existing text to be replaced
	private String replacement;		// the replacement text

	private int priority;			// edit priority level

	private int replOffset;			// starting char offset inclusive
	private int replLen;			// existing length to replacement
	private int replLine;			// starting line
	private int replCol;			// starting col

	private String msg;				// edit type message

	/**
	 * Define an edit to replacement the existing text (should be ws only) between the given token
	 * indexes (exclusive) with the new given string value.
	 * <p>
	 * If a document starts with whitespace, beg could be null; if it ends with whitespace end will be
	 * EOF.
	 *
	 * @param begIndex left token index
	 * @param endIndex right token index
	 * @param existing existing text
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 */
	public TextEdit(AdeptToken beg, AdeptToken end, String existing, String replacement, int priority, String msg) {
		this.existing = existing;
		this.replacement = replacement;
		this.priority = priority;
		this.msg = msg;

		begIndex = beg != null ? beg.getTokenIndex() : 0;
		endIndex = end.getTokenIndex();
		if (beg != null) {
			replOffset = beg.getStopIndex() + 1;
			replLine = beg.getLine();
			replCol = beg.getCharPositionInLine();
		}
		replLen = end.getStartIndex() - replOffset;
	}

	/**
	 * Define an edit to replacement the existing text of a token (should be a comment) with the new
	 * given string value.
	 *
	 * @param token token representing the existing text
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 */
	public TextEdit(AdeptToken token, String replacement, int priority, String msg) {
		this.existing = token.getText();
		this.replacement = replacement;
		this.priority = priority;
		this.msg = msg;

		begIndex = endIndex = token.getTokenIndex();
		replOffset = token.getStartIndex();
		replLine = token.getLine();
		replCol = token.getCharPositionInLine();
		replLen = token.getStopIndex() - replOffset + 1;

	}

	public Region getRegion() {
		return Region.key(this);
	}

	public boolean changesSomething() {
		return !existing.equals(replacement);
	}

	public int begIndex() {
		return begIndex;
	}

	public int endIndex() {
		return endIndex;
	}

	@Override
	public String existing() {
		return existing;
	}

	@Override
	public String replacement() {
		return replacement;
	}

	/**
	 * Returns the priority of this edit. Valued 1 (low) ... n (high). Identifies the internal rule that
	 * created this edit. Hint for diferentiating between multiple edits that occur at the same
	 * location.
	 */
	public int priority() {
		return priority;
	}

	@Override
	public int replOffset() {
		return replOffset;
	}

	@Override
	public int replLen() {
		return replLen;
	}

	@Override
	public int replLine() {
		return replLine;
	}

	@Override
	public int replCol() {
		return replCol;
	}

	/** Trims upto n spaces from the end of the replacement string. */
	public void replTrimTailWidth(int nSpaces) {
		StringBuilder sb = new StringBuilder(replacement);
		int len = sb.length();
		int end = len - nSpaces;
		for (int idx = len - 1; idx > -1 && idx > end; idx--) {
			if (sb.charAt(idx) != Strings.SPC) break;
			sb.deleteCharAt(idx);
		}
		replacement = sb.toString();
	}

	public void replAppend(String text) {
		replacement += text;
	}

	public void replAppend(char c) {
		replacement += c;
	}

	public void setReplacement(String replacement) {
		this.replacement = replacement;
	}

	/** Updates the replacement string by changing the trailing HWs to the given string. */
	public void replUpdate(String replacement) {
		this.replacement = Strings.trimTrailinglHWs(this.replacement);
		this.replacement += replacement;
	}

	@Override
	public int compare(TextEdit e1, TextEdit e2) {
		if (e1.begIndex < e2.begIndex) return -1;
		if (e1.begIndex > e2.begIndex) return 1;
		if (e1.endIndex < e2.endIndex) return -1;
		if (e1.endIndex > e2.endIndex) return 1;
		return e1.replacement.compareTo(e2.replacement);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + begIndex;
		result = prime * result + ((replacement == null) ? 0 : replacement.hashCode());
		result = prime * result + endIndex;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		TextEdit other = (TextEdit) obj;
		if (begIndex != other.begIndex) return false;
		if (replacement == null) {
			if (other.replacement != null) return false;
		} else if (!replacement.equals(other.replacement)) return false;
		if (endIndex != other.endIndex) return false;
		return true;
	}

	@Override
	public String toString() {
		String now = Strings.encodeWS(existing);
		String rep = Strings.encodeWS(replacement);
		return String.format(Msg, msg + ":", replLine + 1, replCol + 1, begIndex, endIndex, replOffset, replLen, now,
				rep);
	}
}
