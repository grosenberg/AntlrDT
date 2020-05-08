/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.format;

public class RegionException extends RuntimeException {

	private Region o1;
	private Region o2;

	public RegionException(Region o1, Region o2, String msg) {
		super(msg);
		this.o1 = o1;
		this.o2 = o2;
	}

	@Override
	public String getMessage() {
		return String.format("%s: %s %s", super.getMessage(), o1, o2);
	}
}
