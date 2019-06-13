package net.certiv.antlrdt.vis.parse;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.misc.ParseCancellationException;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeListener;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

import net.certiv.antlrdt.core.parser.ITargetInfo;
import net.certiv.antlrdt.core.parser.Target;
import net.certiv.antlrdt.vis.model.TreeModel;
import net.certiv.antlrdt.vis.views.parse.TreeProcessor;
import net.certiv.antlrdt.vis.views.tokens.Trace;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.jdt.util.DynamicLoader;
import net.certiv.dsl.jdt.util.JdtUtil;

class TargetUnit implements ITargetInfo {

	private static final Class<?>[] lexParams = new Class[] { CharStream.class };
	private static final Class<?>[] parserParams = new Class[] { TokenStream.class };

	private GrammarRecord record;
	private IFile grammar;
	private Class<?> parserClass;
	private Class<?> lexerClass;

	private Class<?> factoryClass;
	@SuppressWarnings("unused") private Class<?> tokenClass;
	private Class<?> errorClass; // parser error handler

	private TreeModel model;
	private ParseTree tree;
	private List<Token> tokens;
	private CodePointCharStream input;
	private List<ErrorRecord> errors;
	private String[] ruleNames;
	private String[] tokenNames;
	private String[] modeNames;
	private String mainRuleName;
	private List<String[]> trace = new ArrayList<>();

	private Thread thread;
	private boolean terminate;
	private String content;

	private class ParseTerminator implements ParseTreeListener {

		@Override
		public void enterEveryRule(ParserRuleContext ctx) {
			if (terminate) terminate(ctx);
		}

		@Override
		public void visitTerminal(TerminalNode node) {
			if (terminate) terminate((ParserRuleContext) node.getParent());
		}

		@Override
		public void visitErrorNode(ErrorNode node) {
			if (terminate) terminate((ParserRuleContext) node.getParent());
		}

		@Override
		public void exitEveryRule(ParserRuleContext ctx) {
			if (terminate) terminate(ctx);
		}

		private void terminate(ParserRuleContext ctx) {
			Lexer recognizer = (Lexer) ctx.start.getTokenSource();
			RecognitionException e = new RecognitionException("Terminated", recognizer, input, ctx);
			for (ParserRuleContext context = ctx; context != null; context = context.getParent()) {
				context.exception = e;
			}
			throw new ParseCancellationException("Parser execution timeout", e);
		}
	}

	public class TraceListener implements ParseTreeListener {

		@Override
		public void enterEveryRule(ParserRuleContext ctx) {
			addTrace(Trace.ENTRY, ctx);
		}

		@Override
		public void visitTerminal(TerminalNode node) {
			addTrace(Trace.TERMINAL, node);
		}

		@Override
		public void visitErrorNode(ErrorNode node) {
			addTrace(Trace.ERROR, node);
		}

		@Override
		public void exitEveryRule(ParserRuleContext ctx) {
			addTrace(Trace.EXIT, ctx);
		}
	}

	/**
	 * Loads and holds the classes that corresponding to the current editor content: the
	 * source grammar.
	 *
	 * @param record a data record describing the source grammar
	 * @param srcGrammar the source grammar file
	 * @param content the content of the source grammar file
	 */
	public TargetUnit(GrammarRecord record, IFile grammar, String content) {
		this.record = record;
		this.grammar = grammar;
		this.content = content;
	}

	public void exec() {
		thread = Thread.currentThread();
		ClassLoader parent = thread.getContextClassLoader();
		IProject project = record.getProject();

		try (DynamicLoader loader = DynamicLoader.create(project, parent)) {
			thread.setContextClassLoader(loader);
			if (buildClasses(loader)) generate(content);
		} catch (IOException e) {
			Log.info(this, "Restoring classloader after failure");
		} finally {
			thread.setContextClassLoader(parent);
		}
	}

	public void terminate() {
		Log.warn(this, "Terminating Parse.");
		terminate = true;
	}

	public void addTrace(Trace kind, ParserRuleContext ctx) {
		String rulename = ruleNames[ctx.getRuleIndex()];
		String tokenmsg = kind == Trace.ENTRY ? ctx.getStart().toString() : ctx.getStop().toString();
		String desc = String.format("%sing rule '%s' on token %s", kind.toString(), rulename, tokenmsg);
		trace.add(new String[] { String.valueOf(trace.size() + 1), desc });
		Log.info(this, desc);
	}

	public void addTrace(Trace kind, TerminalNode node) {
		String desc = String.format(" - %s: token %s", kind.toString(), node.getSymbol().toString());
		trace.add(new String[] { String.valueOf(trace.size() + 1), desc });
		Log.info(this, desc);
	}

	public TreeModel getModel() {
		return model;
	}

	public List<String[]> getTrace() {
		return trace;
	}

	public ParseTree getTree() {
		return tree;
	}

	public List<Token> getTokens() {
		return tokens;
	}

	public List<ErrorRecord> getErrors() {
		return errors;
	}

	@Override
	public IFile getGrammar() {
		return grammar;
	}

	@Override
	public String getMainRuleName() {
		return mainRuleName;
	}

	@Override
	public String[] getRuleNames() {
		return ruleNames;
	}

	@Override
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override
	public String[] getModeNames() {
		return modeNames;
	}

	@Override
	public Target getTargetType() {
		return Target.UNIT;
	}

	private boolean buildClasses(DynamicLoader loader) {
		parserClass = loadFqClass(loader, record.getParserFQName());
		lexerClass = loadFqClass(loader, record.getLexerFQName());
		factoryClass = loadClass(loader, record.getTokenFactory().getPathname());
		tokenClass = loadClass(loader, record.getCustomToken().getPathname());
		errorClass = loadClass(loader, record.getErrorStrategy().getPathname());
		return parserClass != null && lexerClass != null;
	}

	private Class<?> loadFqClass(DynamicLoader loader, String fqName) {
		if (fqName.isEmpty()) return null;
		Log.info(this, "Loading " + fqName);
		try {
			return loader.loadClass(fqName);
		} catch (ClassNotFoundException e) {
			Log.error(this, "Failed to load class '" + fqName + "' (" + e.getMessage() + ")");
		}
		return null;
	}

	private Class<?> loadClass(DynamicLoader loader, String name) {
		if (name.isEmpty()) return null;
		IProject project = record.getProject();
		String fqname = JdtUtil.determineFQName(project, name);
		if (fqname == null) return null;
		return loadFqClass(loader, fqname);
	}

	@SuppressWarnings("deprecation")
	private void generate(String sourceText) {
		if (sourceText.length() == 0) return;
		input = CharStreams.fromString(sourceText);
		Object[] lexInput = new Object[] { input };

		// lexer

		Lexer lexer;
		try {
			Constructor<?> lexerConstructor = lexerClass.getConstructor(lexParams);
			lexer = (Lexer) lexerConstructor.newInstance(lexInput);
			tokenNames = lexer.getTokenNames();
			modeNames = lexer.getModeNames();

			Log.info(this, "Lexer constructed ...");
		} catch (Exception e) {
			Log.error(this, "Failed to instantiate lexer (" + e.getMessage() + ")");
			return;
		}

		// token factory

		errors = new ArrayList<>();
		TokenFactory<?> tokenFactory = null;
		if (factoryClass != null) {
			try {
				Constructor<?> factoryConstructor = factoryClass.getConstructor((Class<?>[]) null);
				tokenFactory = (TokenFactory<?>) factoryConstructor.newInstance((Object[]) null);
				lexer.setTokenFactory(tokenFactory);
				lexer.addErrorListener(new ErrorListener(ErrorSrc.LEXER, errors));
				Log.info(this, "Token factory added ...");
			} catch (Exception e) {
				Log.error(this, "Failed to instantiate token factory (" + e.getMessage() + ")");
				return;
			}
		}

		// token stream

		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		tokenStream.fill();
		tokens = tokenStream.getTokens();
		Object[] parserInput = new Object[] { tokenStream };
		Log.info(this, "Token stream generated ...");

		// parser

		Parser parser;
		try {
			Constructor<?> parserConstructor = parserClass.getConstructor(parserParams);
			parser = (Parser) parserConstructor.newInstance(parserInput);
			ruleNames = parser.getRuleNames();

			parser.removeErrorListeners();
			parser.addErrorListener(new ErrorListener(ErrorSrc.PARSER, errors));
			if (factoryClass != null) parser.setTokenFactory(tokenFactory);

			parser.addParseListener(new ParseTerminator());
			if (record.getTraceParser()) parser.addParseListener(new TraceListener());

			Log.info(this, "Parser constructed ...");
		} catch (Exception e) {
			Log.error(this, "Failed to instantiate parser (" + e.getMessage() + ")");
			MessageDialog.openError(Display.getDefault().getActiveShell(), "Error", e.getMessage());
			return;
		}

		if (ruleNames == null || ruleNames.length == 0) return;

		// error strategy

		if (errorClass != null) {
			try {
				Constructor<?> errorConstructor = errorClass.getConstructor((Class<?>[]) null);
				ANTLRErrorStrategy errorStrategy = (ANTLRErrorStrategy) errorConstructor.newInstance((Object[]) null);
				parser.setErrorHandler(errorStrategy);
				Log.info(this, "Error strategy added ...");
			} catch (Exception e) {
				Log.error(this, "Failed to instantiate error strategy (" + e.getMessage() + ")");
				return;
			}
		}

		// find first parser rule

		Method[] methods = parserClass.getMethods();
		Method mainRule = null;
		for (Method method : methods) {
			if (method.getName().equals(ruleNames[0])) {
				mainRule = method;
				mainRuleName = mainRule.getName();
				break;
			}
		}
		if (mainRule == null) {
			Log.info(this, "Failed to identify a main rule");
			return;
		}
		Log.info(this, "Main rule selected (" + mainRuleName + ") ...");

		try {
			Log.info(this, "Building tree ...");
			tree = (ParseTree) mainRule.invoke(parser);
			Log.info(this, "Tree generated.");
		} catch (Exception e) {
			Log.error(this, "Failed generating parse tree (" + e.getMessage() + ")", e);
		}

		if (tree != null) {
			Log.info(this, "Generating graph model...");
			model = new TreeModel();
			model.setVocab(ruleNames, tokenNames);
			ParseTreeWalker.DEFAULT.walk(new TreeProcessor(model), tree);
		}
	}
}
