package net.certiv.antlr.dt.core.formatter;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.formatter.DslCodeFormatter;

public class AntlrSourceFormatter extends DslCodeFormatter {

	public AntlrSourceFormatter() {
		super();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}
}
