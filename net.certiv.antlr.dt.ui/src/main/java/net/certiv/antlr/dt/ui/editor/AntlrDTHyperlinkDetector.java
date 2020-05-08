package net.certiv.antlr.dt.ui.editor;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.dsl.core.model.DslModel;
import net.certiv.dsl.ui.editor.hyperlink.DslHyperlinkDetector;

public class AntlrDTHyperlinkDetector extends DslHyperlinkDetector {

	public AntlrDTHyperlinkDetector() {
		super();
	}

	@Override
	public DslModel getDslModel() {
		return AntlrCore.getDefault().getDslModel();
	}
}
