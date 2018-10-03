package net.certiv.antlrdt.core.parser;

import java.util.Collection;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.xpath.XPath;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Lexer;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ActionContext;
import net.certiv.antlrdt.core.parser.gen.PathVisitor;
import net.certiv.antlrdt.core.parser.gen.StructureVisitor;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.model.builder.DslModelMaker;
import net.certiv.dsl.core.parser.DslSourceParser;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.core.util.Log.LogLevel;
import net.certiv.dsl.core.util.Strings;

public class AntlrDTSourceParser extends DslSourceParser {

	private static final AntlrDT4TokenFactory Factory = new AntlrDT4TokenFactory();

	private String packageName;

	public AntlrDTSourceParser() {
		super();
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
		lexer.setTokenFactory(Factory);
		lexer.removeErrorListeners();
		lexer.addErrorListener(getDslErrorListener());

		record.ts = new CommonTokenStream(lexer);
		record.parser = new AntlrDT4Parser(record.ts);
		record.parser.setTokenFactory(Factory);
		record.parser.removeErrorListeners();
		record.parser.addErrorListener(getDslErrorListener());
		record.tree = ((AntlrDT4Parser) record.parser).grammarSpec();
	}

	@Override
	public void analyzeStructure(DslModelMaker maker) {
		try {
			StructureVisitor visitor = new StructureVisitor(record.tree);
			visitor.setMaker(maker);
			visitor.findAll();
		} catch (IllegalArgumentException e) {
			getDslErrorListener().generalError("Model analysis: %s @%s:%s", e);
		}
	}

	// /** Tree walker used to identify the code elements signficant in CodeAssist operations. */
	// public void buildCodeAssist() {
	// try {
	// CodeAssistVisitor walker = new CodeAssistVisitor(record.tree);
	// walker.setHelper(new AssistBuilder(this));
	// walker.findAll();
	// } catch (Exception e) {
	// getDslErrorListener().generalError("CodeAssist: %s @%s:%s", e);
	// }
	// }

	public PathsData buildPathsData() {
		PathsData data = new PathsData();
		try {
			PathVisitor walker = new PathVisitor(record.tree);
			walker.setHelper(data);
			walker.findAll();
		} catch (IllegalArgumentException e) {
			getDslErrorListener().generalError("Paths: %s @%s:%s", e);
		}
		return data;
	}

	// /////////////////////////////////////////////////////////////////////////////////

	public String resolvePackageName() {
		if (packageName == null) extractPackage();
		return packageName;
	}

	// Tree pattern matcher used to extract the package defining statement
	private void extractPackage() {
		try {
			Collection<ParseTree> actions = XPath.findAll(record.tree, "/grammarSpec/prequelConstruct/action",
					record.parser);

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

	@Override
	public void build() {}

	@Override
	public boolean modelContributor() {
		return false;
	}
}
