package net.certiv.antlrdt.core.parser;

import java.util.Collection;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Lexer;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ActionContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.GrammarSpecContext;
import net.certiv.antlrdt.core.parser.gen.CodeAssistVisitor;
import net.certiv.antlrdt.core.parser.gen.PathVisitor;
import net.certiv.antlrdt.core.parser.gen.StructureVisitor;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.parser.DslParseErrorListener;
import net.certiv.dsl.core.parser.DslSourceParser;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.core.util.Log.LogLevel;
import net.certiv.dsl.core.util.Strings;

public class AntlrDTSourceParser extends DslSourceParser {

	private String packageName;

	public AntlrDTSourceParser() {
		super();
		Log.setLevel(this, LogLevel.Info);
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	/** Builds a ParseTree for the given content representing the source of a corresponding unit. */
	@Override
	public ParseTree parse(String name, String content, DslParseErrorListener errListener)
			throws RecognitionException, Exception {
		Log.info(this, "Parse [name=" + name + "]");

		input = CharStreams.fromString(content, name);
		lexer = new AntlrDT4Lexer(input);

		AntlrDT4TokenFactory factory = new AntlrDT4TokenFactory(input);
		lexer.setTokenFactory(factory);
		tokens = new CommonTokenStream(lexer);

		parser = new AntlrDT4Parser(tokens);
		parser.setTokenFactory(factory);
		parser.removeErrorListeners();
		parser.addErrorListener(errListener);
		GrammarSpecContext parseTree = ((AntlrDT4Parser) parser).grammarSpec();

		return parseTree;
	}

	/** Make the code unit internal element structure. */
	@Override
	public void buildStructure() {
		try {
			StructureVisitor walker = new StructureVisitor(tree);
			walker.setMaker(this);
			walker.findAll();
		} catch (IllegalArgumentException e) {
			Log.error(this, "Model - Outline processing error", e);
		}
	}

	/** Tree walker used to identify the code elements signficant in CodeAssist operations. */
	@Override
	public void buildCodeAssist() {
		try {
			CodeAssistVisitor walker = new CodeAssistVisitor(tree);
			walker.setHelper(this);
			walker.findAll();
		} catch (Exception e) {
			Log.error(this, "CodeAssist - Tree walk error", e);
		}
	}

	public PathsData buildPathsData() {
		PathsData data = new PathsData();
		try {
			PathVisitor walker = new PathVisitor(tree);
			walker.setHelper(data);
			walker.findAll();
		} catch (IllegalArgumentException e) {
			Log.error(this, "Paths - Tree walk error", e);
		}
		return data;
	}

	// /////////////////////////////////////////////////////////////////////////////////

	public String resolvePackageName() {
		if (packageName == null) extractPackage();
		return packageName;
	}

	/**
	 * Tree pattern matcher used to identify package defining statements NOTE: compare to implementation
	 * in core builder
	 */
	private void extractPackage() {
		Log.debug(this, "ExtractPackage [root=" + (tree != null ? "not null" : "null") + "]");

		try {
			Collection<ParseTree> actions = XPath.findAll(tree, "/grammarSpec/prequelConstruct/action", parser);
			for (ParseTree action : actions) {
				ActionContext ctx = (ActionContext) action;
				if (ctx.id().getText().equals("header")) {
					String content = Strings.deQuote(ctx.actionBlock().getText().trim());
					String[] lines = content.split("\\n");
					for (String line : lines) {
						if (line.trim().startsWith("package")) {
							int beg = line.indexOf("package") + "package".length() + 1;
							int end = line.lastIndexOf(';');
							packageName = line.substring(beg, end).trim();
							break;
						}
					}
				}
			}
		} catch (Exception e) {
			Log.error(this, "ExtractPackage - Tree walk error", e);
		}
	}
}
