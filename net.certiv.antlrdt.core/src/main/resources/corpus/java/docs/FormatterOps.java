/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format;

import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.certiv.adept.Settings;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.lang.ParseRecord;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

/** Operations to collect and manage {@code TextEdit}s during the multiple stages of formatting. */
public class FormatterOps {

	Document doc;
	ParseRecord data;
	Settings settings;
	String tabEquiv;

	StringBuilder contents;				// the formatted source text

	// ----------------------------------------------------------------

	// key=edit region; value=edit
	TreeMap<Region, TextEdit> edits;	// implementing edits

	// key=line number; value=list of tokens
	TreeMultilist<Integer, AdeptToken> lineTokensIndex;

	// key=token index; value=line number
	TreeMap<Integer, Integer> tokenLineIndex;

	// key=token index; value=align col
	HashMap<Integer, Integer> tokenAlignColIndex;

	// ----------------------------------------------------------------

	public FormatterOps(DocModel model, Settings settings) {
		this.doc = model.getDocument();
		this.data = doc.getParseRecord();
		this.settings = settings;
		this.tabEquiv = Strings.spaces(settings.tabWidth);

		edits = new TreeMap<>();
		contents = new StringBuilder();

		lineTokensIndex = new TreeMultilist<>();
		tokenLineIndex = new TreeMap<>();
		tokenAlignColIndex = new HashMap<>();
	}

	public void dispose() {
		doc = null;
		data = null;
		edits.clear();
		edits = null;
		contents = null;
		lineTokensIndex.clear();
		tokenLineIndex.clear();
		tokenAlignColIndex.clear();
	}

	// -----------------------------------------------------------------------------

	protected void addToAlignIndex(int index, int align) {
		tokenAlignColIndex.put(index, align);
	}

	protected int getAlign(int index) {
		Integer align = tokenAlignColIndex.get(index);
		return align != null ? align : 0;
	}

	// -----------------------------------------------------------------------------

	/**
	 * Rebuilds the line indexes to reflect edits between real tokens. Appends each real token to the
	 * modLineTokenIndex. Creates new lines as appropriate. Updates the {@code visCol} field of the
	 * given token to refect any formatting edits.
	 */
	protected void buildLinesIndexes() {
		lineTokensIndex.clear();
		tokenLineIndex.clear();

		// examine all real tokens
		AdeptToken prior = null;
		for (AdeptToken token : data.index.keySet()) {
			String ws = findWsLeft(token);
			int nls = Strings.countVWS(ws);

			int line = prior != null ? prior.getLine() : 0;
			line += nls + 1;	// native 1..n
			token.setLine(line);

			int from = 0;
			if (prior != null && nls == 0) {
				from = prior.visCol() + prior.getText().length();
			}
			int width = Strings.measureVisualWidth(ws, settings.tabWidth, from);
			token.setVisCol(from + width);

			lineTokensIndex.put(line, token);
			tokenLineIndex.put(token.getTokenIndex(), line);

			prior = token;
		}
	}

	/**
	 * Define an edit to replace the existing text (should be ws only) between the given tokens
	 * (exclusive) with the new given string value.
	 *
	 * @param beg left token
	 * @param end right token
	 * @param existing existing text
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 * @throws IllegalArgumentException
	 */
	protected TextEdit updateOrCreateCommentEdit(AdeptToken token, String replacement, int priority, String msg) {
		TextEdit edit = edits.get(Region.key(token, token));
		if (edit != null) {
			edit.setReplacement(replacement);
		} else {
			edit = new TextEdit(token, replacement, priority, msg);
			edits.put(edit.getRegion(), edit);
		}
		return edit;
	}

	/**
	 * Create an edit implementing the necessary whitespace change to move the given token to the given
	 * visual column. Update the visual column of any tokens to the right of the given token.
	 *
	 * @param lineNum
	 * @param token
	 * @param toVisCol
	 */
	protected void prepEditAndShiftLine(int lineNum, AdeptToken token, int toVisCol) {
		if (token.visCol() == toVisCol) return;

		List<AdeptToken> fullLine = lineTokensIndex.get(lineNum);
		int idx = fullLine.indexOf(token);
		updateOrCreateEditLeft(token, idx, toVisCol);

		ListIterator<AdeptToken> remainder = fullLine.listIterator(idx + 1);
		AdeptToken prior = token;
		while (remainder.hasNext()) {
			AdeptToken next = remainder.next();
			int from = prior.visCol() + prior.getText().length();
			String ws = findWsLeft(token);
			int width = Strings.measureVisualWidth(ws, settings.tabWidth, from);
			token.setVisCol(from + width);
			prior = next;
		}
	}

	// -----------------------------------------------------------------------------

	protected void updateOrCreateEditLeft(AdeptToken token, int pos, int toVisCol) {
		TextEdit edit = findOrCreateEditLeft(token);
		if (pos == 0) { // at BOL
			String ws = createWs(0, toVisCol);
			edit.replUpdate(ws);
		} else {
			AdeptToken prior = data.tokenIndex.get(edit.begIndex());
			int from = prior.visCol() + prior.getText().length();
			if (from > toVisCol) {
				String msg = String.format("Err: shift %s to %s: %s", from, toVisCol, token.toString());
				Log.error(this, msg);
				return;
			}
			edit.replUpdate(createWs(from, toVisCol));
		}
		token.setVisCol(toVisCol);
	}

	private String createWs(int from, int to) {
		if (settings.useTabs) {
			return Strings.createVisualWs(settings.tabWidth, from, to);
		}
		return Strings.spaces(to - from);
	}

	/** Returns the left adjacent TextEdit . */
	protected TextEdit findOrCreateEditLeft(AdeptToken token) {
		TextEdit edit = findEditLeft(token);
		if (edit == null) {
			AdeptToken prior = findTokenLeft(token);
			edit = createEdit(prior, token);
			edits.put(edit.getRegion(), edit);
		}
		return edit;
	}

	/** Returns the left adjacent TextEdit or {@code null}. */
	protected TextEdit findEditLeft(AdeptToken token) {
		AdeptToken left = data.getRealLeft(token.getTokenIndex());
		return edits.get(Region.key(left, token));
	}

	protected Spacing findSpacingLeft(AdeptToken token) {
		String text = findWsLeft(token);
		return Spacing.characterize(text, settings.tabWidth);
	}

	protected String findWsLeft(AdeptToken token) {
		TextEdit edit = findEditLeft(token);
		if (edit != null) return edit.replacement();

		return token.refToken().lActual;
	}

	/** Returns the left nearest visible token or {@code null}. */
	protected AdeptToken findTokenLeft(AdeptToken token) {
		Entry<Integer, AdeptToken> entry = data.tokenIndex.lowerEntry(token.getTokenIndex());
		return entry != null ? entry.getValue() : null;
	}

	/** Returns the current line of the given token. */
	protected int findLine(AdeptToken token) {
		return tokenLineIndex.get(token.getTokenIndex());
	}

	/**
	 * Returns the position in the given current line of the given token. Returns {@code -1} if the
	 * token is not found on the given line.
	 */
	protected int indexInLine(int line, AdeptToken token) {
		return lineTokensIndex.get(line).indexOf(token);
	}

	/**
	 * Returns the token in the given line at the given index. Returns {@code null} if no token exists
	 * at the given line and index.
	 */
	protected AdeptToken getFromLine(int line, int idx) {
		List<AdeptToken> tokens = lineTokensIndex.get(line);
		if (tokens.size() > idx) return tokens.get(idx);
		return null;
	}

	/**
	 * Returns the token prior to the given token in the given line. Returns {@code null} if no such
	 * token exists.
	 */
	protected AdeptToken priorInLine(int line, AdeptToken token) {
		int idx = indexInLine(line, token);
		if (idx < 1) return null;
		return getFromLine(line, idx - 1);
	}

	// -----------------------------------------------------------------------------

	/**
	 * Define an edit for the existing text (should be ws only) between the given tokens (exclusive).
	 *
	 * @param prior left token
	 * @param token right token
	 * @throws IllegalArgumentException
	 */
	protected TextEdit createEdit(AdeptToken prior, AdeptToken token) {
		int priorIdx = prior != null ? prior.getTokenIndex() : 0;
		String existing = data.getTextBetween(priorIdx, token.getTokenIndex());
		return createEdit(prior, token, existing, existing, 0, "");
	}

	/**
	 * Define an edit to replace the existing text (should be ws only) between the given token indexes
	 * (exclusive) with the new given string value.
	 *
	 * @param beg left token index
	 * @param end right token index
	 * @param existing existing text
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 * @throws IllegalArgumentException
	 */
	protected TextEdit createEdit(int begIndex, int endIndex, String existing, String replacement, int priority,
			String msg) {
		AdeptToken beg = begIndex > -1 ? data.getToken(begIndex) : null;
		AdeptToken end = endIndex > -1 ? data.getToken(endIndex) : null;
		return createEdit(beg, end, existing, replacement, priority, msg);
	}

	/**
	 * Define an edit to replace the existing text (should be ws only) between the given tokens
	 * (exclusive) with the new given string value.
	 *
	 * @param beg left token
	 * @param end right token
	 * @param existing existing text
	 * @param replacement replacement text
	 * @param priority relative edit priority
	 * @param msg edit description
	 * @throws IllegalArgumentException
	 */
	protected TextEdit createEdit(AdeptToken beg, AdeptToken end, String existing, String replacement, int priority,
			String msg) {

		if ((beg == null && end == null) || existing == null || replacement == null) {
			throw new IllegalArgumentException("Malformed text edit create request.");
		}
		if (end == null) end = data.getToken(data.getTokenStream().size() - 1);
		if (msg == null) msg = "";
		return new TextEdit(beg, end, existing, replacement, priority, msg);
	}
}
