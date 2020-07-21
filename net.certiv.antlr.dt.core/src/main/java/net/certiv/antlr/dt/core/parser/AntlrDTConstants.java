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

public class AntlrDTConstants {

	// public final static String ANTLR_PARTITIONING = "__antlr_partitioning";
	// public static int AntlrAttributeModifier = Modifiers.USER_MODIFIER * 4;

	public final static int Fragment = 0x2000;
	public final static int Combined = 0x2000 << 1;
	public final static int Parser = 0x2000 << 2;
	public final static int Lexer = 0x2000 << 3;
	public final static int Tree = 0x2000 << 4;

}
