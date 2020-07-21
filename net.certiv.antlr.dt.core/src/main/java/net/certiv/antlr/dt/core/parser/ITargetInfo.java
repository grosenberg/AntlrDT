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

import org.eclipse.core.resources.IFile;

import net.certiv.antlr.dt.core.model.Target;

public interface ITargetInfo {

	/** Returns the definitional type of this target info. */
	Target getTargetType();

	/** Returns the file containing the grammar corresponding to this target info. */
	IFile getGrammar();

	/** Returns the name of the main rule in the grammar corresponding to this target info. */
	String getMainRuleName();

	/** Returns the grammar rule names defined for the grammar corresponding to this target info. */
	String[] getRuleNames();

	/** Returns the grammar token names defined for the grammar corresponding to this target info. */
	String[] getTokenNames();

	/** Returns the grammar mode names defined for the grammar corresponding to this target info. */
	String[] getModeNames();
}
