/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.core.util;

/** Keys for use with performance timing. */
public enum Facet {
	LOAD,
	BUILD,
	PARSE,
	MATCH,
	FORMAT,
	EXECUTE;
}
