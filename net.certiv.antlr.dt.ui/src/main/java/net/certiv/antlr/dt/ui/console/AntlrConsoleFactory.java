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
package net.certiv.antlr.dt.ui.console;

import net.certiv.dsl.ui.console.StyledConsoleFactory;

public class AntlrConsoleFactory extends StyledConsoleFactory {

	private static AntlrConsoleFactory _factory;

	public static AntlrConsoleFactory getFactory() {
		if (_factory == null) {
			_factory = new AntlrConsoleFactory();
		}
		return _factory;
	}

	@Override
	public AntlrConsole getConsole() {
		return (AntlrConsole) super.getConsole();
	}

	@Override
	protected AntlrConsole createConsole() {
		return new AntlrConsole();
	}
}
