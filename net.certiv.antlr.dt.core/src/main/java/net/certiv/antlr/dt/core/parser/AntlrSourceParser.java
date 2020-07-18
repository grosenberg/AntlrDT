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
import net.certiv.antlr.dt.core.builder.BuildUtil;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Lexer;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlr.dt.core.parser.gen.StructureVisitor;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.builder.ToolErrorListener;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.log.Log.LogLevel;
import net.certiv.dsl.core.model.ModelException;
import net.certiv.dsl.core.model.builder.ModelBuilder;
import net.certiv.dsl.core.parser.DslErrorListener;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.parser.DslSourceParser;
import net.certiv.dsl.core.parser.Origin;

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
		DslErrorListener listener = getErrorListener();
		try {
			String name = record.unit.getFile().getName();
			String content = getContent();

			record.setCharStream(CharStreams.fromString(content, name));
			Lexer lexer = new AntlrDT4Lexer(record.getCharStream());
			lexer.setTokenFactory(TokenFactory);
			lexer.removeErrorListeners();
			lexer.addErrorListener(listener);

			record.setTokenStream(new CommonTokenStream(lexer));
			AntlrDT4Parser parser = new AntlrDT4Parser(record.getTokenStream());
			parser.setTokenFactory(TokenFactory);
			parser.removeErrorListeners();
			parser.addErrorListener(listener);
			record.setParser(parser);
			record.setTree(parser.grammarSpec());

			try {
				lint(record, name, content);
			} catch (Exception | Error e) {
				listener.generalError(Origin.BUILDER, ERR_PARSER, e);
				return e;
			}

		} catch (Exception | Error e) {
			listener.generalError(Origin.GENERAL, ERR_PARSER, e);
			return e;
		}
		return null;
	}

	/**
	 * Convenience method to lint a string representing an ANTLR grammar. Caution: called
	 * after the source is parsed, but before the new model is built; any existing model
	 * might be invalid.
	 * <p>
	 * Has an indirect Antlr3 dependency.
	 *
	 * @param record the parse record instance to receive errors/warnings
	 * @param name the display/filename of the grammar
	 * @param content the grammar source content
	 * @throws ModelException exception code details cause
	 */
	public void lint(DslParseRecord record, String name, String content) throws ModelException {
		IPath output = BuildUtil.resolveOutputPath(record);
		Tool tool = new Tool(new String[] { "-o", output.toString() });
		tool.removeListeners();
		tool.addListener(new ToolErrorListener(record));

		ANTLRStringStream in = new ANTLRStringStream(content);
		GrammarRootAST ast = tool.parse(name, in);
		Grammar g = tool.createGrammar(ast);
		g.fileName = name;
		tool.process(g, false);
	}

	@Override
	public Throwable analyze(ModelBuilder builder) {
		try {
			StructureVisitor visitor = new StructureVisitor(record.getTree());
			visitor.setBuilder(builder);
			builder.beginAnalysis();
			visitor.findAll();
			builder.endAnalysis();

			return null;

		} catch (Exception | Error e) {
			getErrorListener().generalError(Origin.ANALYSIS, ERR_ANALYSIS, e);
			return e;
		}
	}
}
