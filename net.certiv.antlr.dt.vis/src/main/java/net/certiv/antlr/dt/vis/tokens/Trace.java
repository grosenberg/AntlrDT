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
package net.certiv.antlr.dt.vis.tokens;

import net.certiv.dsl.core.util.Strings;

public enum Trace {
	ENTRY,
	TERMINAL,
	ERROR,
	EXIT;

	@Override
	public String toString() {
		return Strings.capitalize(name());
	}
}
