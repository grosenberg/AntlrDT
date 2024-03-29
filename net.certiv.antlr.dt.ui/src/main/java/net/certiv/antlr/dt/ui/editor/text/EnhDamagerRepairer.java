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

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.TextPresentation;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.TypedRegion;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.rules.ITokenScanner;

import net.certiv.antlr.dt.ui.editor.Partitions;
import net.certiv.common.log.Log;

public class EnhDamagerRepairer extends DefaultDamagerRepairer {

	protected TextAttribute leadingTextAttribute;

	/**
	 * Creates a damager/repairer that uses the given scanner and returns the given
	 * default text attribute if the current token does not carry a text attribute.
	 */
	public EnhDamagerRepairer(ITokenScanner scanner, TextAttribute namedAttribute) {
		super(scanner);
		leadingTextAttribute = namedAttribute;
	}

	/** Creates a two-tone presentation */
	@Override
	public void createPresentation(TextPresentation presentation, ITypedRegion chRegion) {
		if (leadingTextAttribute == null) {
			super.createPresentation(presentation, chRegion);

		} else {
			ITypedRegion lRegion;
			try {
				lRegion = TextUtilities.getPartition(fDocument, Partitions.PARTITIONING, chRegion.getOffset(), false);
			} catch (BadLocationException e) {
				Log.error( "Region [offset=" + chRegion.getOffset() + ", len=" + chRegion.getLength() + ", type="
						+ chRegion.getType() + "]", e);
				super.createPresentation(presentation, chRegion); // bail
				return;
			}

			// just name part
			int len = getNameLen(fDocument, lRegion.getOffset(), lRegion.getLength());
			if (chRegion.getOffset() > lRegion.getOffset() + len) {
				super.createPresentation(presentation, chRegion);
				return;
			}

			// just action part
			if (chRegion.getOffset() + chRegion.getLength() <= lRegion.getOffset() + len) {
				addRange(presentation, chRegion.getOffset(), chRegion.getLength(), leadingTextAttribute);
				return;
			}

			// color the name part
			int delta = lRegion.getOffset() + len - chRegion.getOffset();
			addRange(presentation, chRegion.getOffset(), delta, leadingTextAttribute);

			// color the action part remainder
			TypedRegion defRegion = new TypedRegion(lRegion.getOffset() + len, chRegion.getLength() - delta,
					chRegion.getType());
			super.createPresentation(presentation, defRegion);
		}
	}

	// initial portion of the region, upto the open-brace
	private int getNameLen(IDocument document, int beg, int len) {
		for (int idx = beg; idx < beg + len; idx++) {
			try {
				if (document.getChar(idx) == '{') {
					return (idx > beg) ? idx - beg : 0;
				}
			} catch (BadLocationException e) {}
		}
		return beg;
	}
}
