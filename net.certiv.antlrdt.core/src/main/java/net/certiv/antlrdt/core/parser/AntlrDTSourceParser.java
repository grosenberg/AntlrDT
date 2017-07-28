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
import net.certiv.antlrdt.core.parser.gen.OutlineVisitor;
import net.certiv.antlrdt.core.parser.gen.PathVisitor;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.parser.DslParseErrorListener;
import net.certiv.dsl.core.parser.DslSourceParser;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.core.util.Log.LogLevel;
import net.certiv.dsl.core.util.Strings;

public class AntlrDTSourceParser extends DslSourceParser {

	private String packageName;
	private PathsData pathsData;

	public AntlrDTSourceParser() {
		super();
		Log.setLevel(this, LogLevel.Info);
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	/**
	 * Builds a ParseTree for the given content representing the source of a
	 * corresponding module (file).
	 */
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
		parser.removeErrorListeners(); // remove ConsoleErrorListener to reduce the noise
		parser.addErrorListener(errListener); // add error listener to feed error markers
		GrammarSpecContext parseTree = ((AntlrDT4Parser) parser).grammarSpec();

		return parseTree;
	}

	/**
	 * Build the internal minimal model used as the structure basis for the outline
	 * view, etc.
	 */
	@Override
	public void buildModel() {
		Log.debug(this, "Model [root=" + (parseTree != null ? "not null" : "null") + "]");

		try {
			OutlineVisitor outline = new OutlineVisitor(parseTree);
			outline.setHelper(this);
			outline.findAll();
		} catch (IllegalArgumentException e) {
			Log.error(this, "Model - Outline processing error", e);
		}

		collectPathsData();
	}

	public void collectPathsData() {
		try {
			PathVisitor paths = new PathVisitor(parseTree);
			this.pathsData = new PathsData();
			paths.setHelper(pathsData);
			paths.findAll();
		} catch (IllegalArgumentException e) {
			Log.error(this, "Model - Paths processing error", e);
		}
	}

	public PathsData getPathsData() {
		return pathsData;
	}

	/**
	 * Tree pattern matcher used to identify the code elements that may be
	 * signficant in CodeAssist operations
	 */
	@Override
	public void buildCodeAssist() {
		Log.debug(this, "CodeAssist [root=" + (parseTree != null ? "not null" : "null") + "]");

		try {
			Collection<ParseTree> ruleNames = XPath.findAll(parseTree, "//RULE_REF", parser);
			ruleNames.addAll(XPath.findAll(parseTree, "//TOKEN_REF", parser));
			codeAssist(ruleNames);
		} catch (Exception e) {
			Log.error(this, "CodeAssist - Tree walk error", e);
		}
	}

	// /////////////////////////////////////////////////////////////////////////////////

	public String resolvePackageName() {
		if (packageName == null)
			extractPackage();
		return packageName;
	}

	/**
	 * Tree pattern matcher used to identify package defining statements NOTE:
	 * compare to implementation in core builder
	 */
	private void extractPackage() {
		Log.debug(this, "ExtractPackage [root=" + (parseTree != null ? "not null" : "null") + "]");

		try {
			Collection<ParseTree> actions = XPath.findAll(parseTree, "/grammarSpec/prequelConstruct/action", parser);
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
