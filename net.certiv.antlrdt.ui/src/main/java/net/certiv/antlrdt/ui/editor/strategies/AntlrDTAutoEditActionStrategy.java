package net.certiv.antlrdt.ui.editor.strategies;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DefaultIndentLineAutoEditStrategy;
import org.eclipse.jface.text.DocumentCommand;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITypedRegion;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.Partitions;
import net.certiv.dsl.core.preferences.DslPrefsManager;
import net.certiv.dsl.ui.text.DslTextTools;
import net.certiv.dsl.ui.text.util.AutoEdit;
import net.certiv.dsl.ui.text.util.LineRegion;

public class AntlrDTAutoEditActionStrategy extends DefaultIndentLineAutoEditStrategy {

	private static final String[] ALIGNTERMS = new String[] { "{" };
	private static final String[] CLOSETERMS = new String[] { ")", "}" };

	private String partitioning;

	public AntlrDTAutoEditActionStrategy(String partitioning) {
		super();
		this.partitioning = partitioning;
	}

	public DslPrefsManager getPrefsMgr() {
		return AntlrDTCore.getDefault().getPrefsManager();
	}

	public DslTextTools getTools() {
		return AntlrDTUI.getDefault().getTextTools();
	}

	@Override
	public void customizeDocumentCommand(IDocument doc, DocumentCommand cmd) {
		if (getPrefsMgr().isSmartMode()) {
			if (AutoEdit.isNewLineInsertionCommand(doc, cmd)) {
				insertAfterNewLine(doc, cmd);
			} else if (AutoEdit.isSingleCharactedInsertionOrReplaceCommand(cmd)) {
				if (cmd.offset != -1) smartInsertCharacter(doc, cmd);
			}
		} else {
			super.customizeDocumentCommand(doc, cmd);
		}
	}

	private void insertAfterNewLine(IDocument doc, DocumentCommand cmd) {
		try {
			StringBuilder buf = new StringBuilder(cmd.text);
			LineRegion lr = new LineRegion(doc, cmd);
			String adjIndent = adjustIndent(doc, cmd, lr, getPrefsMgr().getTabSize());
			buf.append(adjIndent);

			IRegion rightWord = AutoEdit.locateTextRight(doc, lr);
			if (rightWord != null) { // trim to word
				cmd.length = rightWord.getOffset() - lr.pos;
			} else if (lr.lineEnd > lr.pos) { // trim trailing WS
				cmd.length = lr.lineEnd - lr.pos;
			}
			cmd.text = buf.toString();
		} catch (BadLocationException e) {
			e.printStackTrace();
			super.customizeDocumentCommand(doc, cmd);
		}
	}

	// Evaluate current line to determine an alignment column
	private String adjustIndent(IDocument doc, DocumentCommand cmd, LineRegion lr, int tabSize)
			throws BadLocationException {

		int alignCol = findAlignmentColumn(doc, lr, tabSize, 100, partitioning, Partitions.ANTLRDT_PARTITIONING);

		// if at line beginning, no appended indent
		if (alignCol <= 0) return "";

		IRegion rightWord = AutoEdit.locateTextRight(doc, lr);
		ITypedRegion part = AutoEdit.partition(doc, lr.pos);
		int oBrace = AutoEdit.findMatchingCharacterInPartition(doc, part.getOffset(), '{', part.getType(), 40);
		int cBrace = part.getOffset() + part.getLength() - 1;

		if (rightWord != null) {
			// if before first open brace, prefix indent one from alignment col
			if (rightWord.getOffset() == oBrace) {
				return getPrefsMgr().getIndentByVirtualSize(alignCol + getPrefsMgr().getIndentationSize());
			}

			// if before last close brace, align with open brace or reduce indent
			if (rightWord.getOffset() == cBrace) {
				int lnStart = AutoEdit.lineOffset(doc, oBrace);
				int oBraceCol = AutoEdit.calculateVisualLength(doc, tabSize, lnStart, oBrace);
				alignCol = oBraceCol <= alignCol ? oBraceCol : alignCol - getPrefsMgr().getIndentationSize();
				alignCol = alignCol > 0 ? alignCol : 0;
				return getPrefsMgr().getIndentByVirtualSize(alignCol);
			}
		}

		// if on open brace line, align with first word, else indent from open brace
		if (AutoEdit.lineOffset(doc, lr.pos) == AutoEdit.lineOffset(doc, oBrace)) {
			int lnStart = AutoEdit.lineOffset(doc, oBrace);
			int wordStart = AutoEdit.findEndOfWhiteSpace(doc, oBrace + 1, lr.pos);
			if (wordStart < lr.pos) {
				alignCol = AutoEdit.calculateVisualLength(doc, tabSize, lnStart, wordStart);
			} else {
				int oBraceCol = AutoEdit.calculateVisualLength(doc, tabSize, lnStart, oBrace);
				alignCol = oBraceCol + getPrefsMgr().getIndentationSize();
			}
			return getPrefsMgr().getIndentByVirtualSize(alignCol);
		}

		// otherwise, indent to alignment col
		return getPrefsMgr().getIndentByVirtualSize(alignCol);
	}

	private int findAlignmentColumn(IDocument doc, LineRegion lr, int tabSize, int limit, String contentType,
			String partitioning) throws BadLocationException {

		int partOffset = AutoEdit.partitionOffset(doc, lr.pos);
		int pos = lr.pos < lr.indentCol ? lr.indentCol : lr.pos;

		if (pos == lr.lineBeg || pos == lr.indentCol || pos == partOffset) {
			return AutoEdit.calculateVisualLength(doc, tabSize, lr.lineBeg, pos);
		}

		int offset = pos - 1;
		while (offset > 0 && offset > lr.indentCol && offset > partOffset && offset > pos - limit) {
			char c = doc.getChar(offset);
			if (AutoEdit.matchTerms(ALIGNTERMS, doc, offset)) {
				break;
			} else if (AutoEdit.matchTerms(CLOSETERMS, doc, offset)) {
				offset = AutoEdit.skipToMatchingChar(doc, offset - 1, c, partitioning,
						Partitions.STRING_AND_COMMENT_TYPES, limit);
			}
			offset--;
		}
		return AutoEdit.calculateVisualLength(doc, tabSize, lr.lineBeg, offset);
	}

	private void smartInsertCharacter(IDocument doc, DocumentCommand cmd) {
		char newChar = cmd.text.charAt(0);
		try {
			switch (newChar) {
				case '(':
				case '{':
				case '[':
					cmd = AutoEdit.autoClose(getTools(), doc, cmd, getPrefsMgr().closeBrackets(),
							getPrefsMgr().closeStrings());
					break;
				case '}':
				case ')':
				case ']':
					cmd = AutoEdit.autoClose(getTools(), doc, cmd, getPrefsMgr().closeBrackets(),
							getPrefsMgr().closeStrings());
					break;
				case '\t':
					cmd = smartInsertTab(doc, cmd, getPrefsMgr().getTabSize());
					break;
			}
		} catch (BadLocationException e) {
			e.printStackTrace();
		}
	}

	private DocumentCommand smartInsertTab(IDocument doc, DocumentCommand cmd, int tabSize)
			throws BadLocationException {

		LineRegion lr = new LineRegion(doc, cmd);
		IRegion rightText = AutoEdit.locateTextRight(doc, lr);

		if (lr.pos <= lr.indentCol) {
			if (rightText == null) {
				int prevLine = AutoEdit.getLastNonEmptyLine(doc, lr.lineNum, null);
				LineRegion lrp = new LineRegion(doc, prevLine);
				int alignCol = findAlignmentColumn(doc, lrp, tabSize, 100, partitioning,
						Partitions.ANTLRDT_PARTITIONING);
				cmd.offset = lr.lineBeg;
				cmd.length = lr.lineEnd - lr.lineBeg;
				cmd.text = getPrefsMgr().getIndentByVirtualSize(alignCol);
			} else if (rightText.getOffset() > lr.pos) {
				cmd.length = 0;
				cmd.text = "";
				cmd.caretOffset = rightText.getOffset();
				cmd.shiftsCaret = false;
			}
		}
		return cmd;
	}
}
