package net.certiv.antlr.dt.ui.console;

import net.certiv.dsl.ui.console.StyledConsoleFactory;

public class AntlrConsoleFactory extends StyledConsoleFactory {

	private static AntlrConsoleFactory _factory;

	public static AntlrConsoleFactory getFactory() {
		if (_factory == null) {
			_factory = new AntlrConsoleFactory();
		}
		return _factory;
	}

	@Override
	public AntlrConsole getConsole() {
		return (AntlrConsole) super.getConsole();
	}

	@Override
	protected AntlrConsole createConsole() {
		return new AntlrConsole();
	}
}
