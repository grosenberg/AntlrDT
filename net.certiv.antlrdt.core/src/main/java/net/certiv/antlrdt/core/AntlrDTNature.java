package net.certiv.antlrdt.core;

import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.lang.DslNature;

public class AntlrDTNature extends DslNature {

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}
}
