/*******************************************************************************
 * Copyright 2005-2020, Gerald B. Rosenberg, Certiv Analytics and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Certiv Analytics
 *     The Chisel Group, University of Victoria
 *     IBM CAS, IBM Toronto Lab
 *******************************************************************************/
package net.certiv.antlr.dt.vis.graph;

import org.eclipse.draw2d.SWTEventDispatcher;

import net.certiv.antlr.dt.vis.figures.EnhTipHelper;

public interface IEnhGraphViewer {

	SWTEventDispatcher getEventDispatcher();

	EnhTipHelper getCoolTipHelper();
}
