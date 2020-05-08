/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format;

import net.certiv.adept.lang.AdeptToken;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.model.Spacing;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Strings;

public class SpacingProcessor extends AbstractProcessor {

	public SpacingProcessor(FormatterOps ops) {
		super(ops);
	}

	// consider prior right match and current left match
	// favor left over right
	public void formatWhiteSpace() {
		ops.buildLinesIndexes();

		for (AdeptToken token : ops.data.index.keySet()) { // real tokens
			RefToken current = token.refToken();

			if (current.matched != null) {
				TextEdit edit = createEditLeft(current);
				if (edit != null) {
					ops.edits.put(edit.getRegion(), edit);
				}

			} else {
				RefToken prior = ops.data.getTokenRef(current.lIndex);
				if (prior != null && prior.matched != null) {
					TextEdit edit = createEditRight(prior);
					if (edit != null) {
						ops.edits.put(edit.getRegion(), edit);
					}
				}
			}
		}
	}

	private TextEdit createEditLeft(RefToken ref) {
		if (ref.lIndex == AdeptToken.BOF) return null;
		String repl = calcSpacing(ref.matched.lSpacing, ref.matched.lActual, ref.matched.dent.indents);
		return ops.createEdit(ref.lIndex, ref.index, ref.lActual, repl, 1, "L Nom");
	}

	private TextEdit createEditRight(RefToken ref) {
		if (ref.index > AdeptToken.EOF) return null;
		String repl = calcSpacing(ref.matched.rSpacing, ref.matched.rActual, ref.matched.dent.indents);
		return ops.createEdit(ref.index, ref.rIndex, ref.rActual, repl, 2, "R Nom");
	}

	// given desired spacing and existing ws, return replacement string
	protected String calcSpacing(Spacing spacing, String existing, int indents) {
		switch (spacing) {
			case HFLEX:
				String hflex = existing.replace(ops.tabEquiv, "\t");
				return hflex.replaceAll("\\t[ ]+(?=\\t)", "\t");

			case HSPACE:
				return Strings.SPACE;

			case NONE:
				return "";

			case VLINE:
				return Strings.EOL + Strings.createIndent(ops.settings.tabWidth, ops.settings.useTabs, indents);

			case VFLEX:
				String vflex = Strings.getN(adjVertSpacing(existing), Strings.EOL);
				return vflex + Strings.createIndent(ops.settings.tabWidth, ops.settings.useTabs, indents);

			default:
				return existing;
		}
	}

	// produce replacement indent string including any leading newlines for BOL
	protected String calcIndent(String existing, String matched, int indents) {
		String indentStr = "";
		if (!ops.settings.removeTrailingWS) {
			indentStr += Strings.leadHWs(existing);
		}
		int eline = adjVertSpacing(existing);
		int mline = adjVertSpacing(matched);
		int lines = Math.max(eline, mline);
		indentStr += Strings.getN(lines, Strings.EOL);
		indentStr += Strings.createIndent(ops.settings.tabWidth, ops.settings.useTabs, indents);
		return indentStr;
	}

	protected int adjVertSpacing(String text) {
		int lines = Strings.countVWS(text);
		if (ops.settings.removeBlankLines) {
			lines = Math.min(lines, ops.settings.keepNumBlankLines + 1);
		}
		return lines;
	}

	protected void showWhere(RefToken prior, RefToken present, RefToken matched, RefToken next) {
		String pname = prior != null ? ops.data.getTokenName(prior.type) : "{Nil}";
		String nname = next != null ? ops.data.getTokenName(next.type) : "{Nil}";
		String cname = ops.data.getTokenName(present.type);
		cname += matched != null ? "+" : "-";

		Log.debug(this, "   %s > %s > %s", pname, cname, nname);
	}
}
