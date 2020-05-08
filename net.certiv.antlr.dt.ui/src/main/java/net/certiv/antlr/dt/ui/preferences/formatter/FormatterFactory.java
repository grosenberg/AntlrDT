package net.certiv.antlr.dt.ui.preferences.formatter;

import java.net.URL;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.formatter.AntlrSourceFormatter;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.formatter.IDslCodeFormatter;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.formatter.DslFormatterFactory;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialog;
import net.certiv.dsl.ui.formatter.IFormatterModifyDialogOwner;

public class FormatterFactory extends DslFormatterFactory {

	private IDslCodeFormatter formatter;

	public FormatterFactory() {
		super();
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
	public IFormatterModifyDialog createDialog(IFormatterModifyDialogOwner owner) {
		return new ModifyDialog(owner, this);
	}

	@Override
	public URL getPreviewContent() {
		return getClass().getResource("FormatPreview.g4");
	}

	@Override
	public IDslCodeFormatter createFormatter() {
		if (formatter == null) {
			formatter = new AntlrSourceFormatter();
		}
		return formatter;
	}
}
