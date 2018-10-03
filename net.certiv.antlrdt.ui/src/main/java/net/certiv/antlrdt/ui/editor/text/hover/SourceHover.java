package net.certiv.antlrdt.ui.editor.text.hover;

import org.eclipse.ui.IEditorPart;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.hover.DslSourceHover;

public class SourceHover extends DslSourceHover {

	public SourceHover(IEditorPart editor, IDslPrefsManager store) {
		super(editor, store);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}
}
