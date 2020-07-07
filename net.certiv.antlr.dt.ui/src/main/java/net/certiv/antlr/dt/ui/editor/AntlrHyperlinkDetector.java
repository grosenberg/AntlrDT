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
