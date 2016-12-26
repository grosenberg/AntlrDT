package net.certiv.antlrdt.core.parser;

import net.certiv.antlrdt.core.formatter.AntlrDTSourceFormatter;
import net.certiv.dsl.core.formatter.IDslCodeFormatter;
import net.certiv.dsl.core.parser.DslSourceParser;
import net.certiv.dsl.core.parser.DslSourceParserFactory;

public class AntlrDTSourceParserFactory extends DslSourceParserFactory {

	public AntlrDTSourceParserFactory() {
		super();
	}

	@Override
	public DslSourceParser createSourceParser() {
		return new AntlrDTSourceParser();
	}

	@Override
	public IDslCodeFormatter createCodeFormatter() {
		return new AntlrDTSourceFormatter();
	}
}
