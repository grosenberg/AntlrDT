/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format;

import java.util.List;

import net.certiv.adept.format.plan.Group;
import net.certiv.adept.format.plan.Scheme;
import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.unit.AdeptComp;
import net.certiv.adept.unit.TableMultilist;
import net.certiv.adept.unit.TreeMultilist;
import net.certiv.adept.util.Strings;

/**
 * Aligns identified source elements: fields and comments. Alignment results are realized as edits
 * left of the aligned tokens. Updates the {@code visCol} of the aligned element and that of all
 * real tokens to the right on the same line.
 * <p>
 * Depends on the {@code visCol} comment token value being accurate and on the formatter line
 * indexes.
 */
public class AlignProcessor extends AbstractProcessor {

	public AlignProcessor(FormatterOps ops) {
		super(ops);
	}

	public void alignFields() {
		ops.buildLinesIndexes();

		for (Group group : ops.data.groupIndex) {

			TableMultilist<Scheme, Integer, AdeptToken> members = group.getMembers();
			for (Scheme align : members.keySet()) {
				TreeMultilist<Integer, AdeptToken> lines = members.get(align);
				lines = update(lines);
				switch (align) {
					case PAIR:
						handlePairAlign(lines);
						break;

					case LIST:
						handleListAlign(lines, false);
						break;

					case GROUP:
						handleListAlign(lines, false);
						break;

					default:
						break;
				}
			}
		}
	}

	/** Align on common nearest, non-overlapping tab stop. */
	public void alignComments() {
		ops.buildLinesIndexes();

		for (Group group : ops.data.groupIndex) {
			TreeMultilist<Integer, AdeptToken> lines = group.get(Scheme.COMMENT);
			if (lines != null) {
				lines = update(lines);
				handleListAlign(lines, true);
			}
		}
	}

	/**
	 * Align on common nearest minimum, non-overlapping position.
	 * <p>
	 * TODO: for a single line pair, need to consider the prior line to determine alignment cols.
	 */
	private void handlePairAlign(TreeMultilist<Integer, AdeptToken> lines) {
		if (lines.isEmpty()) return;
		if (lines.size() > 1) {
			handleListAlign(lines, false);
			return;
		}

		int modLine = lines.lastKey();
		for (AdeptToken token : lines.get(modLine)) {
			int rem = token.visCol() % ops.settings.tabWidth;
			if (rem > 0) {
				int toVisCol = token.visCol() + ops.settings.tabWidth - rem;
				ops.prepEditAndShiftLine(modLine, token, toVisCol);
			}
		}
	}

	private void handleListAlign(TreeMultilist<Integer, AdeptToken> alignables, boolean tabAlignFirst) {
		int colsToAlign = countMaxCols(alignables);

		for (int col = 0; col < colsToAlign; col++) {
			int alignCol = 0;
			if (col == 0 && tabAlignFirst) {
				alignCol = minTabCol(alignables, col);
			} else {
				alignCol = minCol(alignables, col);
			}

			for (int lineNum : alignables.keySet()) {
				List<AdeptToken> tokens = alignables.get(lineNum);
				if (tokens.size() > col) {
					AdeptToken alignToken = tokens.get(col);
					if (alignCol != alignToken.visCol()) {
						ops.prepEditAndShiftLine(lineNum, alignToken, alignCol);
					}
				}
			}
		}
	}

	// Update line numers to account for edits
	private TreeMultilist<Integer, AdeptToken> update(TreeMultilist<Integer, AdeptToken> lines) {
		TreeMultilist<Integer, AdeptToken> updated = new TreeMultilist<>();
		updated.setValueComparator(AdeptComp.Instance);
		for (AdeptToken token : lines.valuesAll()) {
			Integer modLine = ops.tokenLineIndex.get(token.getTokenIndex());
			updated.put(modLine, token);
		}
		return updated;
	}

	private int minTabCol(TreeMultilist<Integer, AdeptToken> group, int col) {
		int minCol = minCol(group, col);
		return Strings.nextTabCol(minCol, ops.settings.tabWidth);
	}

	private int minCol(TreeMultilist<Integer, AdeptToken> alignables, int col) {
		int minCol = 0;
		for (int line : alignables.keySet()) {
			List<AdeptToken> alignTokens = alignables.get(line);
			if (alignTokens.size() > col) {
				AdeptToken alignable = alignTokens.get(col);
				AdeptToken prior = ops.priorInLine(line, alignable);

				if (prior != null) {
					minCol = Math.max(minCol, prior.visCol() + prior.getText().length() + 1);
				} else {
					minCol = Math.max(minCol, alignable.dent().indents * ops.settings.tabWidth);
				}
			}
		}
		return minCol;
	}

	private int countMaxCols(TreeMultilist<Integer, AdeptToken> alignables) {
		int max = 0;
		for (Integer num : alignables.keySet()) {
			max = Math.max(max, alignables.get(num).size());
		}
		return max;
	}
}
