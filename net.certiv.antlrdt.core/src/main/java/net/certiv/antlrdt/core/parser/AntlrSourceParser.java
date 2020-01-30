package net.certiv.antlrdt.core.parser;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;

import org.eclipse.core.runtime.IPath;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Lexer;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlrdt.core.parser.gen.StructureVisitor;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.log.Log.LogLevel;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.builder.ModelBuilder;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.parser.DslSourceParser;
import net.certiv.dsl.core.util.Chars;

public class AntlrSourceParser extends DslSourceParser {

	private static final AntlrTokenFactory TokenFactory = new AntlrTokenFactory();

	public AntlrSourceParser(DslParseRecord record) {
		super(record);
		Log.setLevel(this, LogLevel.Info);
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	@Override
	public boolean doAnalysis() {
		return true;
	}

	@Override
	public Throwable parse() {
		try {
			record.cs = CharStreams.fromString(getContent(), record.unit.getFile().getName());
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
			return null;

		} catch (Exception | Error e) {
			getDslErrorListener().generalError(ERR_PARSER, e);
			return e;
		}
	}

	@Override
	public Throwable analyze(ModelBuilder builder) {
		try {
			StructureVisitor visitor = new StructureVisitor(record.tree);
			visitor.setSourceName(getPackageName(record.unit));
			visitor.setBuilder(builder);
			builder.beginAnalysis();
			visitor.findAll();
			builder.endAnalysis();
			return null;

		} catch (Exception | Error e) {
			getDslErrorListener().generalError(ERR_ANALYSIS, e);
			return e;
		}
	}

	private String getPackageName(ICodeUnit unit) {
		IPath path = unit.getProjectRelativePath();
		IPath root = unit.getSourceRoot();
		path = path.makeRelativeTo(root);
		return path.toString().replace(Chars.SLASH, Chars.DOT);
	}
}
