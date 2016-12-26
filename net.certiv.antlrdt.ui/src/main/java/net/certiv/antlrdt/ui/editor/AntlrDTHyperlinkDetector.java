package net.certiv.antlrdt.ui.editor;

import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.dsl.core.model.IDslElement;
import net.certiv.dsl.ui.editor.hyperlink.DslElementHyperlinkDetector;

public class AntlrDTHyperlinkDetector extends DslElementHyperlinkDetector {

	public AntlrDTHyperlinkDetector(ITextEditor editor) {
		super(editor);
	}

	/** Filter/modify the elements for display on the popup */
	@Override
	public IDslElement[] selectOpenableElements(IDslElement[] elements) {
		return elements;
	}
}
