package net.certiv.antlr.dt.ui.editor;

import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.ui.editor.DslDocumentSetupParticipant;

/**
 * Reference in the extension point is used to associate the contextTypeId with
 * a file extension
 */
public class AntlrDocumentSetupParticipant extends DslDocumentSetupParticipant {

	public AntlrDocumentSetupParticipant() {
		super(AntlrUI.getDefault().getTextTools());
	}
}
