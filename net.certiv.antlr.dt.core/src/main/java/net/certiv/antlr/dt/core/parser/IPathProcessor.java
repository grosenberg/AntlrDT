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

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.antlr.dt.core.model.Target;

public interface IPathProcessor {

	void addNode(ParserRuleContext rule, AntlrToken term, Target kind);

	void end();
}
