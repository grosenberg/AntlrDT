package net.certiv.antlr.dt.vis.parse;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.console.Aspect;
import net.certiv.antlr.dt.core.model.Target;
import net.certiv.antlr.dt.core.parser.ITargetInfo;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.vis.model.TreeModel;
import net.certiv.antlr.dt.vis.views.tokens.Trace;
import net.certiv.antlr.dt.vis.views.tree.TreeProcessor;
import net.certiv.dsl.core.console.CS;
import net.certiv.dsl.core.console.ConsoleRecordFactory.ConsoleRecord;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.util.ExceptUtil;
import net.certiv.dsl.jdt.util.DynamicLoader;
import net.certiv.dsl.jdt.util.JdtUtil;

class TargetUnit implements ITargetInfo {

	private static final Class<?>[] lexParams = new Class[] { CharStream.class };
	private static final Class<?>[] parserParams = new Class[] { TokenStream.class };

	private TargetAssembly assembly;
	private IFile grammar;
	private Class<?> parserClass;
	private Class<?> lexerClass;

	private Class<?> factoryClass;
	private Class<?> errorClass; // parser error handler

	private TreeModel model;
	private ParseTree tree;
	private CodePointCharStream input;

	private List<Token> tokens = new ArrayList<>();
	private List<ProblemRecord> problems = new ArrayList<>();
	private List<ConsoleRecord> errors = new ArrayList<>();
	private List<String[]> trace = new ArrayList<>();

	private String[] ruleNames;
	private String[] tokenNames;
	private String[] modeNames;
	private String mainRuleName;

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
	 * @param assembly a data record describing the source grammar
	 * @param srcGrammar the source grammar file
	 * @param content the content of the source grammar file
	 */
	public TargetUnit(TargetAssembly assembly, IFile grammar, String content) {
		this.assembly = assembly;
		this.grammar = grammar;
		this.content = content;
	}

	public void exec() {
		thread = Thread.currentThread();
		ClassLoader parent = thread.getContextClassLoader();
		IProject project = assembly.getProject();

		try (DynamicLoader loader = DynamicLoader.create(project, parent)) {
			thread.setContextClassLoader(loader);
			if (buildClasses(loader)) {
				generate(content);
			}

		} catch (ClassCastException ex) {
			reportError(Aspect.BUILDER, ex, "Type error %s", ex.getMessage());

		} catch (Exception ex) {
			reportError(Aspect.BUILDER, ex, "Caught error %s", ex.getMessage());

		} finally {
			thread.setContextClassLoader(parent);
		}
	}

	public void terminate() {
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

	public boolean isValid() {
		return tree != null;
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

	public List<ProblemRecord> getProblems() {
		return problems;
	}

	public List<ConsoleRecord> getErrors() {
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
		parserClass = loadFqClass(Aspect.PARSER, loader, assembly.getParserFQName());
		lexerClass = loadFqClass(Aspect.LEXER, loader, assembly.getLexerFQName());
		factoryClass = loadClass(Aspect.FACTORY, loader, assembly.getTokenFactory().getPathname());
		errorClass = loadClass(Aspect.STRATEGY, loader, assembly.getErrorStrategy().getPathname());
		return parserClass != null && lexerClass != null;
	}

	private Class<?> loadFqClass(Aspect aspect, DynamicLoader loader, String fqName) {
		report(aspect, "Loading class for '%s'.", fqName);
		if (fqName.isEmpty()) {
			reportError(aspect, null, "%s classname is 'null'; fix source grammar errors.", aspect);
			return null;
		}

		if (fqName.startsWith("null.")) {
			reportError(aspect, null, "%s classname is uninitialized; re-save the source grammar.", aspect);
			return null;
		}

		try {
			return loader.loadClass(fqName);
		} catch (ClassNotFoundException e) {
			reportError(aspect, e, "%s: failed to load class '%s' (%s)", aspect, fqName, e.getMessage());
			return null;
		}
	}

	private Class<?> loadClass(Aspect aspect, DynamicLoader loader, String name) {
		if (name.isEmpty()) return null;

		IProject project = assembly.getProject();
		String fqname = JdtUtil.determineFQName(project, name);
		if (fqname == null) {
			reportError(aspect, null, "%s: failed to determing package name for '%s'", aspect, name);
			return null;
		}
		return loadFqClass(aspect, loader, fqname);
	}

	private void generate(String content) {
		if (content.isEmpty()) {
			reportError(Aspect.LEXER, null, "No grammar content to evaluate!");
			return;
		}

		input = CharStreams.fromString(content);
		problems = new ArrayList<>();

		// lexer

		report(Aspect.LEXER, "Constructing '%s'", lexerClass.getSimpleName());
		Lexer lexer = null;
		try {
			Constructor<?> ctor = lexerClass.getConstructor(lexParams);
			lexer = (Lexer) ctor.newInstance(input);
			lexer.addErrorListener(new ErrorListener(Aspect.LEXER, problems));
			tokenNames = getTokenNames(lexer);
			modeNames = lexer.getModeNames();

		} catch (NoSuchMethodException ex) {
			reportEx(Aspect.LEXER, Aspect.NO_METHOD, ex);
		} catch (SecurityException ex) {
			reportEx(Aspect.LEXER, Aspect.SECURITY, ex);
		} catch (InstantiationException ex) {
			reportEx(Aspect.LEXER, Aspect.INSTANCE, ex);
		} catch (IllegalAccessException ex) {
			reportEx(Aspect.LEXER, Aspect.NO_ACCESS, ex);
		} catch (IllegalArgumentException ex) {
			reportEx(Aspect.LEXER, Aspect.NO_ARG, ex);
		} catch (InvocationTargetException ex) {
			reportEx(Aspect.LEXER, Aspect.INVOKE, ex);
		}
		if (lexer == null) return;

		// token factory

		TokenFactory<?> tokenFactory = null;
		if (factoryClass != null) {
			report(Aspect.LEXER, "Constructing '%s'", factoryClass.getSimpleName());
			try {
				Constructor<?> ctor = factoryClass.getConstructor((Class<?>[]) null);
				tokenFactory = (TokenFactory<?>) ctor.newInstance((Object[]) null);
				lexer.setTokenFactory(tokenFactory);

			} catch (NoSuchMethodException ex) {
				reportEx(Aspect.FACTORY, Aspect.NO_METHOD, ex);
			} catch (SecurityException ex) {
				reportEx(Aspect.FACTORY, Aspect.SECURITY, ex);
			} catch (InstantiationException ex) {
				reportEx(Aspect.FACTORY, Aspect.INSTANCE, ex);
			} catch (IllegalAccessException ex) {
				reportEx(Aspect.FACTORY, Aspect.NO_ACCESS, ex);
			} catch (IllegalArgumentException ex) {
				reportEx(Aspect.FACTORY, Aspect.NO_ARG, ex);
			} catch (InvocationTargetException ex) {
				reportEx(Aspect.FACTORY, Aspect.INVOKE, ex);
			}
		}

		// token stream

		report(Aspect.LEXER, "Filling token stream.");
		CommonTokenStream tokenStream = new CommonTokenStream(lexer);
		tokenStream.fill();
		tokens = tokenStream.getTokens();
		if (tokens.isEmpty()) {
			reportError(Aspect.LEXER, null, "No tokens generated! The lexer appears to have recognized nothing.");
			return;
		}

		// parser

		report(Aspect.PARSER, "Constructing '%s'", parserClass.getSimpleName());
		Parser parser = null;
		try {
			Constructor<?> ctor = parserClass.getConstructor(parserParams);
			parser = (Parser) ctor.newInstance(tokenStream);
			ruleNames = parser.getRuleNames();

			parser.removeErrorListeners();
			parser.addErrorListener(new ErrorListener(Aspect.PARSER, problems));
			if (factoryClass != null) parser.setTokenFactory(tokenFactory);

			parser.addParseListener(new ParseTerminator());
			if (assembly.getTraceParser()) parser.addParseListener(new TraceListener());

			Log.info(this, "Parser constructed ...");

		} catch (NoSuchMethodException ex) {
			reportEx(Aspect.PARSER, Aspect.NO_METHOD, ex);
		} catch (SecurityException ex) {
			reportEx(Aspect.PARSER, Aspect.SECURITY, ex);
		} catch (InstantiationException ex) {
			reportEx(Aspect.PARSER, Aspect.INSTANCE, ex);
		} catch (IllegalAccessException ex) {
			reportEx(Aspect.PARSER, Aspect.NO_ACCESS, ex);
		} catch (IllegalArgumentException ex) {
			reportEx(Aspect.PARSER, Aspect.NO_ARG, ex);
		} catch (InvocationTargetException ex) {
			reportEx(Aspect.PARSER, Aspect.INVOKE, ex);
		}
		if (parser == null) return;

		if (ruleNames == null || ruleNames.length == 0) {
			reportError(Aspect.LEXER, null, "No rule names generated! Parser appears to be empty.");
			return;
		}

		// error strategy

		if (errorClass != null) {
			report(Aspect.PARSER, "Constructing '%s'", errorClass.getSimpleName());
			try {
				Constructor<?> ctor = errorClass.getConstructor((Class<?>[]) null);
				ANTLRErrorStrategy strategy = (ANTLRErrorStrategy) ctor.newInstance((Object[]) null);
				parser.setErrorHandler(strategy);
				Log.info(this, "Error strategy added ...");

			} catch (NoSuchMethodException ex) {
				reportEx(Aspect.STRATEGY, Aspect.NO_METHOD, ex);
			} catch (SecurityException ex) {
				reportEx(Aspect.STRATEGY, Aspect.SECURITY, ex);
			} catch (InstantiationException ex) {
				reportEx(Aspect.STRATEGY, Aspect.INSTANCE, ex);
			} catch (IllegalAccessException ex) {
				reportEx(Aspect.STRATEGY, Aspect.NO_ACCESS, ex);
			} catch (IllegalArgumentException ex) {
				reportEx(Aspect.STRATEGY, Aspect.NO_ARG, ex);
			} catch (InvocationTargetException ex) {
				reportEx(Aspect.STRATEGY, Aspect.INVOKE, ex);
			}
		}

		// select first parser rule

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
			reportError(Aspect.PARSER, null, "Failed to identify the main rule!");
			return;
		}
		report(Aspect.PARSER, "Using '%s' as the main rule.", mainRuleName);

		// generate the parse tree

		try {
			tree = (ParseTree) mainRule.invoke(parser);

		} catch (IllegalAccessException ex) {
			reportEx(Aspect.TREE, Aspect.NO_ACCESS, ex);
		} catch (IllegalArgumentException ex) {
			reportEx(Aspect.TREE, Aspect.NO_ARG, ex);
		} catch (InvocationTargetException ex) {
			reportEx(Aspect.TREE, Aspect.INVOKE, ex);
		}

		if (tree == null) {
			reportError(Aspect.TREE, null, "No parse tree generated!");
		}

		model = new TreeModel();
		model.setVocab(ruleNames, tokenNames);
		ParseTreeWalker.DEFAULT.walk(new TreeProcessor(model), tree);
		report(Aspect.TREE, "Parse tree generated!");
	}

	private void reportEx(Aspect aspect, Aspect type, Exception ex) {
		reportError(aspect, ex, "Failed due to '%s' exception: %s.", type, ExceptUtil.getMessage(ex));
	}

	private void reportError(Aspect aspect, Throwable ex, String fmt, Object... args) {
		errors.add(AntlrCore.getDefault().consoleError(aspect, ex, fmt, args));
		if (ex != null) {
			Throwable cause = ex.getCause();
			if (cause != null) { // handle nested errors
				reportError(aspect, cause, "Caused by:\n\t%s", cause.getMessage());
			}
		}
	}

	private void report(Aspect aspect, String format, Object... args) {
		AntlrUI.getDefault().consoleAppend(aspect, CS.INFO, format, args);
	}

	@SuppressWarnings("deprecation")
	private String[] getTokenNames(Lexer lexer) {
		return lexer.getTokenNames();
	}
}
