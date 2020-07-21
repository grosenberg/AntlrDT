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
package net.certiv.antlr.dt.ui.editor;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.dsl.core.model.DslModel;
import net.certiv.dsl.ui.editor.hyperlink.DslHyperlinkDetector;

public class AntlrHyperlinkDetector extends DslHyperlinkDetector {

	public AntlrHyperlinkDetector() {
		super();
	}

	@Override
	public DslModel getDslModel() {
		return AntlrCore.getDefault().getDslModel();
	}

	@Override
	protected boolean isDslLikeFilename(String name) {
		return AntlrCore.getDefault().getLangManager().isDslLikeFilename(name);
	}
}
