/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept;

public interface ITextEdit {

	/** Returns the existing text to be replaced. */
	String existing();

	/** Returns the replacement text to be applied by this edit. */
	String replacement();

	/** Returns the starting stream char offset for the replacement text (0-based). */
	int replOffset();

	/** Returns the char length of the existing text that is to be replaced. */
	int replLen();

	/** Returns the document line for the replacement text (0-based). */
	int replLine();

	/** Returns the document column for the replacement text (0-based) */
	int replCol();
}
