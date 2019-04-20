package net.certiv.antlrdt.core.formatter;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.formatter.DslCodeFormatter;

public class AntlrDTSourceFormatter extends DslCodeFormatter {

	public AntlrDTSourceFormatter() {
		super();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}
}
