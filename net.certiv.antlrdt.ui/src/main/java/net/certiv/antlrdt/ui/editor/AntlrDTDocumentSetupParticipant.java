package net.certiv.antlrdt.ui.editor;

import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.ui.editor.DslDocumentSetupParticipant;

/**
 * Reference in the extension point is used to associate the contextTypeId with a file extension
 */
public class AntlrDTDocumentSetupParticipant extends DslDocumentSetupParticipant {

	public AntlrDTDocumentSetupParticipant() {
		super(AntlrDTUI.getDefault().getTextTools());
	}
}
