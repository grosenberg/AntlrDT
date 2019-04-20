package net.certiv.antlrdt.ui.editor.completion;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.antlrdt.ui.editor.strategies.AntlrAutoEditSemicolonStrategy;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.consts.Editor;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.completion.DslCompletionProposal;

public class AntlrProposal extends DslCompletionProposal {

	public AntlrProposal(String completion, int start, int length, Image image, StyledString label, int relevance,
			boolean inDoc) {
		super(completion, start, length, image, label, relevance, inDoc);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	@Override
	public char[] getPrefixCompletionTextStops() {
		return new char[] { LBRACK, LBRACE };
	}

	@Override
	public String getIdString() {
		return getDisplayString();
	}

	@Override
	protected boolean isSmartTrigger(char trigger) {
		return trigger == SEMI && getPrefsMgr().getBoolean(Editor.EDITOR_SMART_SEMICOLON)
				|| trigger == LBRACE && getPrefsMgr().getBoolean(Editor.EDITOR_SMART_OPENING_BRACE);
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

		AntlrAutoEditSemicolonStrategy strategy = new AntlrAutoEditSemicolonStrategy(Partitions.PARTITIONING);
		strategy.customizeDocumentCommand(document, cmd);

		replace(document, cmd.offset, cmd.length, cmd.text);
		setCursorPosition(cmd.caretOffset - getReplacementOffset() + cmd.text.length());
	}
}
