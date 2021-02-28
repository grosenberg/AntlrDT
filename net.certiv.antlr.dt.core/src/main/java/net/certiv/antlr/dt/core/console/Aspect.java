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
package net.certiv.antlr.dt.core.console;

import net.certiv.dsl.core.console.CS;
import net.certiv.dsl.core.console.IAspect;
import net.certiv.common.util.Strings;

public enum Aspect implements IAspect {

	BUILDER("Builder"),
	LEXER("Lexer"),
	FACTORY("Token factory"),
	STRATEGY("Error strategy"),
	PARSER("Parser"),
	TREE("Tree"),

	SECURITY("Security access"),
	INVOKE("Invocation target"),

	GENERAL("General"),
	NO_METHOD("No such method"),
	NO_ACCESS("No access"),
	NO_ARG("Illegal argument(s)"),
	INSTANCE("Instantiation"),
	EXEC("Execution"),

	;

	private String _desc;
	private String _infoColor;
	private String _errsColor;
	private int _infoFontStyle;
	private int _errsFontStyle;
	private String _encoding;

	Aspect(String desc) {
		this(desc, null, null, 0, 0, Strings.UTF_8);
	}

	Aspect(String desc, String info, String errs, int infoStyle, int errsStyle, String encoding) {
		_desc = desc;

		_infoColor = info != null ? info : IAspect.super.info();
		_errsColor = errs != null ? errs : IAspect.super.error();
		_infoFontStyle = infoStyle;
		_errsFontStyle = errsStyle;
		_encoding = encoding;
	}

	@Override
	public String info() {
		return _infoColor;
	}

	@Override
	public String error() {
		return _errsColor;
	}

	@Override
	public String getDesc() {
		return _desc;
	}

	@Override
	public int getFontStyle(CS kind) {
		return CS.ERROR == kind ? _errsFontStyle : _infoFontStyle;
	}

	@Override
	public String getEncoding() {
		return _encoding;
	}

	@Override
	public String toString() {
		return _desc;
	}
}
