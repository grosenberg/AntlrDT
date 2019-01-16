package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.eclipse.core.runtime.IPath;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Lexer;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlrdt.core.parser.gen.StructureVisitor;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.log.Log.LogLevel;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.builder.DslModelMaker;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.parser.DslSourceParser;

public class AntlrSourceParser extends DslSourceParser {

	private static final AntlrTokenFactory TokenFactory = new AntlrTokenFactory();

	public AntlrSourceParser(DslParseRecord record) {
		super(record);
		Log.setLevel(this, LogLevel.Info);
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	public void parse() {
		record.cs = CharStreams.fromString(record.doc.get());
		Lexer lexer = new AntlrDT4Lexer(record.cs);
		lexer.setTokenFactory(TokenFactory);
		lexer.removeErrorListeners();
		lexer.addErrorListener(getDslErrorListener());

		record.ts = new CommonTokenStream(lexer);
		record.parser = new AntlrDT4Parser(record.ts);
		record.parser.setTokenFactory(TokenFactory);
		record.parser.removeErrorListeners();
		record.parser.addErrorListener(getDslErrorListener());
		record.tree = ((AntlrDT4Parser) record.parser).grammarSpec();
	}

	@Override
	public void analyzeStructure(DslModelMaker maker) {
		try {
			StructureVisitor visitor = new StructureVisitor(record.tree);
			visitor.setSourceName(getPackageName(record.unit));
			visitor.setMaker(maker);
			visitor.findAll();
		} catch (IllegalArgumentException e) {
			getDslErrorListener().generalError("Model analysis: %s @%s:%s", e);
		}
	}

	private String getPackageName(ICodeUnit unit) {
		IPath path = unit.getPath().makeRelativeTo(unit.getSourceRoot().getPath());
		return path.toString().replace('/', '.');
	}

	@Override
	public boolean modelContributor() {
		return true;
	}
}
