package net.certiv.antlrdt.ui.view.tokens;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ANTLRErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CodePointCharStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenFactory;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;

import net.certiv.antlrdt.ui.graph.cst.CstProcessor;
import net.certiv.antlrdt.ui.graph.cst.ErrorListener;
import net.certiv.antlrdt.ui.graph.cst.ErrorRecord;
import net.certiv.antlrdt.ui.graph.cst.ErrorSrc;
import net.certiv.antlrdt.ui.graph.cst.model.CstModel;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.core.util.eclipse.DynamicLoader;
import net.certiv.dsl.core.util.eclipse.JdtUtil;

class TargetUnit {

	private static final Class<?>[] lexParams = new Class[] { CharStream.class };
	private static final Class<?>[] parserParams = new Class[] { TokenStream.class };

	private GrammarRecord record;
	private Class<?> parserClass;
	private Class<?> lexerClass;

	private Class<?> factoryClass;
	private Class<?> errorClass; // parser error handler

	private CstModel model;
	private ParseTree tree;
	private List<Token> tokens;
	private CodePointCharStream input;
	private ArrayList<ErrorRecord> errors;
	private String[] ruleNames;
	private String[] tokenNames;
	private String mainRuleName;

	/**
	 * Loads and holds the classes that corresponding to the current editor content: the source grammar.
	 *
	 * @param record a data record describing the source grammar
	 * @param srcGrammar the source grammar file
	 * @param content the content of the source grammar file
	 */
	public TargetUnit(GrammarRecord record, IFile srcGrammar, String content) {
		Log.setLevel(this, Log.LogLevel.Debug);
		this.record = record;

		Thread thread = Thread.currentThread();
		ClassLoader parent = thread.getContextClassLoader();
		IProject project = record.getProject();

		DynamicLoader loader;
		try {
			loader = DynamicLoader.create(project, parent);
			thread.setContextClassLoader(loader);
		} catch (MalformedURLException e) {
			Log.info(this, "Restoring classloader after failure");
			thread.setContextClassLoader(parent);
			return;
		}

		try {
			if (buildClasses(loader)) generate(content);
		} finally {
			Log.info(this, "Restoring classloader after generate");
			thread.setContextClassLoader(parent);
		}
	}

	public CstModel getModel() {
		return model;
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

	public String getMainRuleName() {
		return mainRuleName;
	}

	public String[] getRuleNames() {
		return ruleNames;
	}

	public String[] getTokenNames() {
		return tokenNames;
	}

	private boolean buildClasses(DynamicLoader loader) {
		parserClass = loadFqClass(loader, record.getParserFQName());
		lexerClass = loadFqClass(loader, record.getLexerFQName());
		factoryClass = loadClass(loader, record.getTokenFactory().getName());
		errorClass = loadClass(loader, record.getErrorStrategy().getName());
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

			parser.removeErrorListeners();
			parser.addErrorListener(new ErrorListener(ErrorSrc.PARSER, errors));
			if (factoryClass != null) parser.setTokenFactory(tokenFactory);

			ruleNames = parser.getRuleNames();
			tokenNames = parser.getTokenNames();

			Log.info(this, "Parser constructed ...");
		} catch (Exception e) {
			Log.error(this, "Failed to instantiate parser (" + e.getMessage() + ")");
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

		// generate the parse tree

		try {
			tree = (ParseTree) mainRule.invoke(parser);
			Log.info(this, "Tree generated [exists=" + (tree != null) + "] ...");
			if (tree == null) return;
		} catch (Exception e) {
			Log.error(this, "Failed generating parse tree (" + e.getMessage() + ")", e);
			return;
		}

		// generate model by walking the tree

		CstProcessor walker = new CstProcessor(new CstModel());
		walker.walk(walker, tree);
		model = walker.getModel();

		Log.info(this, "Graph model generated [nodes=" + (model.getNodeList().size() + "]."));
	}
}
