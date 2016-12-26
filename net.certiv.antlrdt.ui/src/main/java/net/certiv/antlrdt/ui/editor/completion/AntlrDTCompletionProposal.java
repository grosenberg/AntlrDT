package net.certiv.antlrdt.ui.editor.completion;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.antlrdt.ui.editor.strategies.AntlrDTAutoEditSemicolonStrategy;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.text.completion.DslCompletionProposal;

public class AntlrDTCompletionProposal extends DslCompletionProposal {

	public AntlrDTCompletionProposal(String completion, int start, int length, Image image, StyledString label,
			int relevance, boolean inDoc) {
		super(completion, start, length, image, label, relevance, inDoc);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	public char[] getPrefixCompletionTextStops() {
		return new char[] { LBRACK };
	}

	@Override
	public String getIdString() {
		return getDisplayString();
	}

	@Override
	protected boolean isSmartTrigger(char trigger) {
		return trigger == ';' && getDslCore().getPrefsManager().getBoolean(PrefsKey.EDITOR_SMART_SEMICOLON);
		// || trigger == '{' &&
		// getDslCore().getPrefsManager().getBoolean(PrefsKey.EDITOR_SMART_OPENING_BRACE);
	}

	@Override
	protected void handleSmartTrigger(IDocument document, char trigger, int referenceOffset)
			throws BadLocationException {
		DocumentCommand cmd = new DocumentCommand() {};

		cmd.offset = referenceOffset;
		cmd.length = 0;
		cmd.text = Character.toString(trigger);
		cmd.doit = true;
		cmd.shiftsCaret = true;
		cmd.caretOffset = getReplacementOffset() + getCursorPosition();

		AntlrDTAutoEditSemicolonStrategy strategy = new AntlrDTAutoEditSemicolonStrategy(
				Partitions.ANTLRDT_PARTITIONING);
		strategy.customizeDocumentCommand(document, cmd);

		replace(document, cmd.offset, cmd.length, cmd.text);
		setCursorPosition(cmd.caretOffset - getReplacementOffset() + cmd.text.length());
	}
}
