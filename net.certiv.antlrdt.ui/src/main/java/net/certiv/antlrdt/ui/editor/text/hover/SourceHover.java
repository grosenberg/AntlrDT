package net.certiv.antlrdt.ui.editor.text.hover;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.ui.IEditorPart;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.text.DslWordFinder;
import net.certiv.dsl.ui.text.hover.DslSourceHover;

public class SourceHover extends DslSourceHover {

	private DslWordFinder finder;

	public SourceHover(IEditorPart editor, IDslPrefsManager store) {
		super(editor, store);
		finder = new DslWordFinder();
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
	public IRegion findWord(IDocument doc, int offset) {
		return finder.findWord(doc, offset);
	}
}
