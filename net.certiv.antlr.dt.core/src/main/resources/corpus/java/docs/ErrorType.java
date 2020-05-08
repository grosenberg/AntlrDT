/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.tool;

public enum ErrorType {

	DIR_NOT_FOUND(1, "directory not found: <arg>", ErrorSeverity.ERROR),

	DIR_IS_FILE(2, "directory is a file: <arg>", ErrorSeverity.ERROR),

	CANNOT_OPEN_FILE(3, "cannot find or open file: <arg><if(exception&&verbose)>; reason: <exception><endif>",
			ErrorSeverity.ERROR),

	CANNOT_READ_FILE(4, "cannot read file <arg>: <arg2>", ErrorSeverity.ERROR),

	CANNOT_WRITE_FILE(5, "cannot write file <arg>: <arg2>", ErrorSeverity.ERROR),

	INTERNAL_ERROR(20,
			"internal error: <arg> <arg2><if(exception&&verbose)>: <exception>"
					+ "<stackTrace; separator=\"\\n\"><endif>",
			ErrorSeverity.ERROR),

	// ---

	INVALID_CMDLINE_ARG(100, "unknown command-line option <arg>", ErrorSeverity.ERROR),
	CONFIG_FAILURE(101, "Configuration failure", ErrorSeverity.ERROR),
	INVALID_VERBOSE_LEVEL(103, "Invalid verbosity verbose: <arg>", ErrorSeverity.ERROR),

	// ---

	PARSE_FAILURE(200, "Failed to parse: <arg>", ErrorSeverity.ERROR),
	PARSE_ERROR(201, "Error in parse: <arg>", ErrorSeverity.ERROR),
	VISITOR_FAILURE(230, "Failed to walk: <arg>", ErrorSeverity.ERROR),
	VISITOR_ERROR(231, "Error in visitor: <arg>", ErrorSeverity.ERROR),

	// ---

	MODEL_BUILD_FAILURE(300, "Failed model build: <arg>", ErrorSeverity.ERROR),
	MODEL_LOAD_FAILURE(301, "Failed model load: <arg>", ErrorSeverity.ERROR),
	MODEL_SAVE_FAILURE(302, "Failed model save: <arg>", ErrorSeverity.ERROR),
	MODEL_VALIDATE_FAILURE(303, "Model validation failed: <arg>", ErrorSeverity.ERROR),

	// ---

	;

	public final String msg;
	public final int code;
	public final ErrorSeverity severity;

	ErrorType(int code, String msg, ErrorSeverity severity) {
		this.code = code;
		this.msg = msg;
		this.severity = severity;
	}
}
