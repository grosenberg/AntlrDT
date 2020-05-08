/*******************************************************************************
 * Copyright 2009, Gerald B. Rosenberg, Certiv Analytics and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Certiv Analytics
 *******************************************************************************/
package net.certiv.antlr.dt.vis.figures;

import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;

public class StdToolTip extends Label {

	public StdToolTip() {
		super();
		setBorder(new MarginBorder(3));
	}
}
