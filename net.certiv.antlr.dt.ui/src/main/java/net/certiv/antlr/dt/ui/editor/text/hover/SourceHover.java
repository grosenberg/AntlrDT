package net.certiv.antlr.dt.ui.editor.text.hover;

import org.eclipse.ui.IEditorPart;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.text.hover.DslSourceHover;

public class SourceHover extends DslSourceHover {

	public SourceHover(IEditorPart editor, IPrefsManager store) {
		super(editor, store);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}
}
