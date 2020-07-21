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

import java.util.List;

import net.certiv.antlr.dt.core.console.Aspect;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.console.ConsoleRecordFactory.ConsoleRecord;
import net.certiv.dsl.core.console.CS;
import net.certiv.dsl.ui.console.StyledConsole;

public class AntlrConsole extends StyledConsole {

	public static final String CONSOLE_TYPE = "antlr_console"; //$NON-NLS-1$

	public AntlrConsole() {
		super(AntlrUI.getDefault(), "AntlrDT Console", CONSOLE_TYPE, null, true);
	}

	@Override
	public void append(List<ConsoleRecord> records, boolean clear) {
		super.append(records, clear);
		append(Aspect.GENERAL, CS.NORMAL, null, "%s\n", BREAK);
	}
}
