package net.certiv.antlrdt.vis.parse;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.vis.views.tokens.Source;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.preferences.DslPrefsManager;
import net.certiv.dsl.core.util.Chars;

public class GrammarRecord {

	private static final IPath DEF_SNIPPETS_DIR = new Path("test.snippets");

	public static final String PROJECT = ".project";
	public static final String GRAMMAR = ".grammar";

	public static final String SNIPPET_DIR = ".snippetDir";
	public static final String SNIPPET_EXT = ".snippetExt";

	public static final String TOKEN_FACTORY = ".tokenFactory";
	public static final String TOKEN = ".token";
	public static final String PARSER_STRATEGY = ".parserStrategy";
	public static final String PARSER_TRACE = ".parserTrace";

	private static final String PATH = ".path";
	private static final String NAME = ".name";

	private DslPrefsManager store;
	private IProject project;
	private IFile grammar;

	private Source snippetsDir;
	private String snippetsExt = "*";

	private Source tokenFactory;
	private Source token;
	private Source errorStrategy;
	private boolean traceParser;

	private String snippetDir; // defines the source data directory

	private String basePathame = ""; // derived values
	private String parserPathname = "";
	private String lexerPathname = "";
	private String recId;

	public GrammarRecord(IProject project, IFile grammar) {
		super();
		this.project = project;
		this.grammar = grammar;
		init();
	}

	private void init() {
		store = AntlrCore.getDefault().getPrefsManager();

		genPackageNames();
		genID();
		snippetDir = "";
		if (project.exists()) {
			IPath path = project.getLocation();
			if (project.exists(DEF_SNIPPETS_DIR)) {
				path = path.append(DEF_SNIPPETS_DIR);
			}
			snippetDir = path.toString();
		} else {
			Log.error(this, "Project does not exist: " + project.getName());
		}
	}

	private void genPackageNames() {
		AntlrCore core = AntlrCore.getDefault();
		ICodeUnit unit = core.getDslModel().create(grammar);
		String pkgName = core.getLangManager().resolveGrammarPackage(unit);

		String filename = grammar.getName();
		int dot = filename.lastIndexOf(Chars.DOT);
		filename = filename.substring(0, dot);

		dot = -1;
		if (filename.endsWith("Lexer")) {
			dot = filename.lastIndexOf("Lexer");

		} else if (filename.endsWith("Parser")) {
			dot = filename.lastIndexOf("Parser");
		}

		if (dot > -1) {
			this.basePathame = pkgName + "." + filename.substring(0, dot);
			this.parserPathname = basePathame + "Parser";
			this.lexerPathname = basePathame + "Lexer";
		} else {
			Log.error(this, "Unrecognizable grammar name: " + filename);
		}
	}

	// id encodes project relative grammar name
	private void genID() {
		String ext = grammar.getFileExtension();
		this.recId = store.bind("{DSL_ID}." + basePathame.replaceAll("/", ".") + "." + ext);
	}

	public void load() {
		String path = store.getString(null, recId + SNIPPET_DIR + PATH, snippetDir);
		String name = store.getString(null, recId + SNIPPET_DIR + NAME, "");
		snippetsDir = new Source(path, name);

		snippetsExt = store.getString(null, recId + SNIPPET_EXT + NAME, "*");

		String pathFac = store.getString(null, recId + TOKEN_FACTORY + PATH, "");
		String nameFac = store.getString(null, recId + TOKEN_FACTORY + NAME, "");
		tokenFactory = new Source(pathFac, nameFac);

		String pathTok = store.getString(null, recId + TOKEN + PATH, "");
		String nameTok = store.getString(null, recId + TOKEN + NAME, "");
		token = new Source(pathTok, nameTok);

		String pathErr = store.getString(null, recId + PARSER_STRATEGY + PATH, "");
		String nameErr = store.getString(null, recId + PARSER_STRATEGY + NAME, "");
		errorStrategy = new Source(pathErr, nameErr);

		traceParser = store.getBoolean(null, recId + PARSER_TRACE + NAME, false);
	}

	public void validate() {
		store.setValue(null, recId + SNIPPET_DIR + PATH, snippetsDir.getPath());
		if (snippetsDir.getName().trim().isEmpty()) {
		}

		store.setValue(null, recId + SNIPPET_EXT + NAME, snippetsExt);

		store.setValue(null, recId + TOKEN_FACTORY + PATH, tokenFactory.getPath());
		store.setValue(null, recId + TOKEN_FACTORY + NAME, tokenFactory.getName());

		store.setValue(null, recId + TOKEN + PATH, token.getPath());
		store.setValue(null, recId + TOKEN + NAME, token.getName());

		store.setValue(null, recId + PARSER_STRATEGY + PATH, errorStrategy.getPath());
		store.setValue(null, recId + PARSER_STRATEGY + NAME, errorStrategy.getName());

		store.setValue(null, recId + PARSER_TRACE + NAME, traceParser);
	}

	public void save() {
		store.setValue(null, recId + SNIPPET_DIR + PATH, snippetsDir.getPath());
		store.setValue(null, recId + SNIPPET_DIR + NAME, snippetsDir.getName());

		store.setValue(null, recId + SNIPPET_EXT + NAME, snippetsExt);

		if (tokenFactory.isEmpty()) {
			store.setToDefault(recId + TOKEN_FACTORY + PATH);
			store.setToDefault(recId + TOKEN_FACTORY + NAME);
		} else {
			store.setValue(null, recId + TOKEN_FACTORY + PATH, tokenFactory.getPath());
			store.setValue(null, recId + TOKEN_FACTORY + NAME, tokenFactory.getName());
		}

		if (token.isEmpty()) {
			store.setToDefault(recId + TOKEN + PATH);
			store.setToDefault(recId + TOKEN + NAME);
		} else {
			store.setValue(null, recId + TOKEN + PATH, token.getPath());
			store.setValue(null, recId + TOKEN + NAME, token.getName());
		}

		if (errorStrategy.isEmpty()) {
			store.setToDefault(recId + PARSER_STRATEGY + PATH);
			store.setToDefault(recId + PARSER_STRATEGY + NAME);
		} else {
			store.setValue(null, recId + PARSER_STRATEGY + PATH, errorStrategy.getPath());
			store.setValue(null, recId + PARSER_STRATEGY + NAME, errorStrategy.getName());
		}

		store.setValue(null, recId + PARSER_TRACE + NAME, traceParser);

		store.save();
	}

	public IProject getProject() {
		return project;
	}

	public IFile getGrammar() {
		return grammar;
	}

	public String getParserFQName() {
		return parserPathname;
	}

	public String getLexerFQName() {
		return lexerPathname;
	}

	public Source getSnippetsDir() {
		return snippetsDir;
	}

	public void setSnippetsDir(Source snippetsDir) {
		this.snippetsDir = snippetsDir;
	}

	public String getSnippetsExt() {
		return snippetsExt;
	}

	public void setSnippetsExt(String snippetsExt) {
		this.snippetsExt = snippetsExt;
	}

	public Source getTokenFactory() {
		return tokenFactory;
	}

	public void setTokenFactory(Source tokenFactory) {
		this.tokenFactory = tokenFactory;
	}

	public Source getCustomToken() {
		return token;
	}

	public void setCustomToken(Source token) {
		this.token = token;
	}

	public Source getErrorStrategy() {
		return errorStrategy;
	}

	public void setErrorStrategy(Source errorStrategy) {
		this.errorStrategy = errorStrategy;
	}

	public boolean getTraceParser() {
		return traceParser;
	}

	public void setTraceParser(boolean selected) {
		this.traceParser = selected;
	}
}
