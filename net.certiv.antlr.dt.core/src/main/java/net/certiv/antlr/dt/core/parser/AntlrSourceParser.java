package net.certiv.antlr.dt.core.parser;

import org.antlr.runtime.ANTLRStringStream;
import org.antlr.v4.Tool;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.tool.Grammar;
import org.antlr.v4.tool.ast.GrammarRootAST;

import org.eclipse.core.runtime.IPath;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.builder.AntlrBuilder;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Lexer;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlr.dt.core.parser.gen.StructureVisitor;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.builder.ToolErrorListener;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.log.Log.LogLevel;
import net.certiv.dsl.core.model.builder.ModelBuilder;
import net.certiv.dsl.core.parser.DslErrorListener;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.parser.DslSourceParser;

public class AntlrSourceParser extends DslSourceParser {

	private static final AntlrTokenFactory TokenFactory = new AntlrTokenFactory();

	public AntlrSourceParser(DslParseRecord record) {
		super(record);
		Log.setLevel(this, LogLevel.Debug);
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

			String name = record.unit.getFile().getName();
			String content = getContent();
			DslErrorListener errs = getErrorListener();

			record.cs = CharStreams.fromString(content, name);
			Lexer lexer = new AntlrDT4Lexer(record.cs);
			lexer.setTokenFactory(TokenFactory);
			lexer.removeErrorListeners();
			lexer.addErrorListener(errs);

			record.ts = new CommonTokenStream(lexer);
			record.parser = new AntlrDT4Parser(record.ts);
			record.parser.setTokenFactory(TokenFactory);
			record.parser.removeErrorListeners();
			record.parser.addErrorListener(errs);
			record.tree = ((AntlrDT4Parser) record.parser).grammarSpec();

			lint(record, name, content);

			return null;

		} catch (Exception | Error e) {
			getErrorListener().generalError(ERR_PARSER, e);
			return e;
		}
	}

	@Override
	public Throwable analyze(ModelBuilder builder) {
		try {
			StructureVisitor visitor = new StructureVisitor(record.tree);
			visitor.setSourceName(record.unit.getPackageName());
			visitor.setBuilder(builder);
			builder.beginAnalysis();
			visitor.findAll();
			builder.endAnalysis();
			return null;

		} catch (Exception | Error e) {
			getErrorListener().generalError(ERR_ANALYSIS, e);
			return e;
		}
	}

	/**
	 * Convenience method to lint a string representing an ANTLR grammar.
	 * <p>
	 * Has Antlr3 dependency.
	 *
	 * @param record the parse record instance to receive errors/warnings
	 * @param name the display/filename of the grammar
	 * @param content the grammar source content
	 */
	public void lint(DslParseRecord record, String name, String content) {
		IPath output = AntlrBuilder.determineBuildPath(record.unit.getLanguageMgr(), record.unit);
		if (output == null) return;

		Tool tool = new Tool(new String[] { "-o", output.toString() });
		tool.removeListeners();
		tool.addListener(new ToolErrorListener(record));

		ANTLRStringStream in = new ANTLRStringStream(content);
		GrammarRootAST ast = tool.parse(name, in);
		Grammar g = tool.createGrammar(ast);
		g.fileName = name;
		tool.process(g, false);
	}
}
