package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.dsl.core.model.DslModel;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.ui.editor.hyperlink.DslHyperlinkDetector;
import net.certiv.dsl.ui.editor.text.DslWordFinder;

public class AntlrDTHyperlinkDetector extends DslHyperlinkDetector {

	private DslWordFinder finder;

	public AntlrDTHyperlinkDetector() {
		super();
		finder = new DslWordFinder();
	}

	@Override
	public IRegion findWord(IDocument doc, int offset) {
		return finder.findWord(doc, offset);
	}

	@Override
	public String getWord(IDocument doc, IRegion word) {
		return finder.getWord(doc, word);
	}

	/** Filter/modify the elements for display on the popup */
	@Override
	public IStatement[] selectOpenableElements(IStatement[] elements) {
		return elements;
	}

	@Override
	public DslModel getDslModel() {
		return AntlrCore.getDefault().getDslModel();
	}
}
