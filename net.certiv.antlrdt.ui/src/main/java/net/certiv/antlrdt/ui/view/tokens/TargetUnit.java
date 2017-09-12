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
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Log;
import net.certiv.dsl.core.util.loader.ClassLoaderFactory;

class TargetUnit {

	private static final Class<?>[] lexParams = new Class[] { CharStream.class };
	private static final Class<?>[] parserParams = new Class[] { TokenStream.class };

	private GrammarRecord record;
	private Class<?> parserClass;
	private Class<?> lexerClass;

	private Class<?> factoryClass;
	private Class<?> errorClass; // parser.addErrorHandler()

	private String parserName;
	private String lexerName;

	private CstModel model;
	private ParseTree tree;
	private List<Token> tokens;
	private CodePointCharStream input;
	private ArrayList<ErrorRecord> errors;
	private String[] ruleNames;
	private String[] tokenNames;
	private String mainRuleName;

	public TargetUnit(GrammarRecord record, IFile srcGrammar, String content) {
		Log.setLevel(this, Log.LogLevel.Debug);
		this.record = record;
		evalGrammar(srcGrammar);

		Thread thread = Thread.currentThread();
		ClassLoader parent = thread.getContextClassLoader();
		IProject project = record.getProject();

		ClassLoader projectLoader;
		try {
			projectLoader = ClassLoaderFactory.create(parent, project);
			thread.setContextClassLoader(projectLoader);
		} catch (MalformedURLException e) {
			Log.info(this, "Restoring classloader after failure");
			thread.setContextClassLoader(parent);
			return;
		}

		try {
			if (buildClasses(projectLoader)) generate(content);
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

	private void evalGrammar(IFile srcGrammar) {
		String name = srcGrammar.getName();
		int dot = name.lastIndexOf('.');
		if (dot < 1) return;
		name = name.substring(0, dot);
		if (name.endsWith("Lexer")) {
			lexerName = name;
			dot = name.lastIndexOf("Lexer");
			parserName = name.substring(0, dot) + "Parser";
		} else if (name.endsWith("Parser")) {
			parserName = name;
			dot = name.lastIndexOf("Parser");
			lexerName = name.substring(0, dot) + "Lexer";
		} else {
			parserName = name + "Parser";
			lexerName = name + "Lexer";
		}
	}

	private boolean buildClasses(ClassLoader projLoader) {
		if (parserName != null && lexerName != null) {
			parserClass = loadClass(projLoader, parserName);
			lexerClass = loadClass(projLoader, lexerName);
			factoryClass = loadClass(projLoader, record.getTokenFactory().getName().replace(".java", ""));
			errorClass = loadClass(projLoader, record.getErrorStrategy().getName().replace(".java", ""));
		}
		return parserClass != null && lexerClass != null;
	}

	private Class<?> loadClass(ClassLoader projLoader, String name) {
		if (name.equals("")) return null;
		IProject project = record.getProject();
		String fqname = CoreUtil.determineFQName(project, name);
		Log.info(this, "Loading " + fqname);
		try {
			return projLoader.loadClass(fqname);
		} catch (ClassNotFoundException e) {
			Log.error(this, "Failed to load class '" + fqname + "' (" + e.getMessage() + ")");
		}
		return null;
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
