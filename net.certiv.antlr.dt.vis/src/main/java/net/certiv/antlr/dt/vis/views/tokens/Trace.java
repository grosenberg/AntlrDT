package net.certiv.antlr.dt.vis.views.tokens;

import net.certiv.dsl.core.util.Strings;

public enum Trace {
	ENTRY,
	TERMINAL,
	ERROR,
	EXIT;

	@Override
	public String toString() {
		return Strings.capitalize(name());
	}
}
