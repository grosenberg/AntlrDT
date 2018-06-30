package net.certiv.antlrdt.ui.view.tokens;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Log;

public class GrammarRecord {

	private static final IPath DEF_SNIPPETS_DIR = new Path("test.snippets");

	public static final String PROJECT = ".project";
	public static final String GRAMMAR = ".grammar";

	public static final String SNIPPET_DIR = ".snippetDir";
	public static final String SNIPPET_EXT = ".snippetExt";

	public static final String TOKEN_FACTORY = ".tokenFactory";
	public static final String PARSER_STRATEGY = ".parserStrategy";

	private static final String PATH = ".path";
	private static final String NAME = ".name";

	private IProject project;
	private IFile grammar;

	private Source snippetsDir;
	private String snippetsExt = "*";

	private Source tokenFactory;
	private Source errorStrategy;

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
		genPackageNames();
		genID();
		this.snippetDir = "";
		if (project.exists()) {
			IPath path = CoreUtil.getWorkspaceLocation().append(project.getFullPath());
			if (project.exists(DEF_SNIPPETS_DIR)) {
				path = path.append(DEF_SNIPPETS_DIR);
			}
			snippetDir = path.toString();
		} else {
			Log.error(this, "Project does not exist: " + project.getName());
		}
	}

	private void genPackageNames() {
		ICodeUnit unit = AntlrDTCore.getDefault().getModelManager().create(grammar);
		String pkgName = unit.resolveParserPackageName();

		String filename = grammar.getName();
		int dot = filename.lastIndexOf('.');
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
		this.recId = "{DSL_ID}." + basePathame.replaceAll("/", ".") + "." + ext;
	}

	public void load() {
		String path = getPrefs().getString(null, recId + SNIPPET_DIR + PATH, snippetDir);
		String name = getPrefs().getString(null, recId + SNIPPET_DIR + NAME, "");
		snippetsDir = new Source(path, name);

		snippetsExt = getPrefs().getString(null, recId + SNIPPET_EXT + NAME, "*");

		String path1 = getPrefs().getString(null, recId + TOKEN_FACTORY + PATH, "");
		String name1 = getPrefs().getString(null, recId + TOKEN_FACTORY + NAME, "");
		tokenFactory = new Source(path1, name1);

		String path2 = getPrefs().getString(null, recId + PARSER_STRATEGY + PATH, "");
		String name2 = getPrefs().getString(null, recId + PARSER_STRATEGY + NAME, "");
		errorStrategy = new Source(path2, name2);
	}

	public void validate() {
		getPrefs().setValue(null, recId + SNIPPET_DIR + PATH, snippetsDir.getPath());
		if (snippetsDir.getName().trim().isEmpty()) {}

		getPrefs().setValue(null, recId + SNIPPET_EXT + NAME, snippetsExt);

		getPrefs().setValue(null, recId + TOKEN_FACTORY + PATH, tokenFactory.getPath());
		getPrefs().setValue(null, recId + TOKEN_FACTORY + NAME, tokenFactory.getName());

		getPrefs().setValue(null, recId + PARSER_STRATEGY + PATH, errorStrategy.getPath());
		getPrefs().setValue(null, recId + PARSER_STRATEGY + NAME, errorStrategy.getName());
	}

	public void save() {
		getPrefs().setValue(null, recId + SNIPPET_DIR + PATH, snippetsDir.getPath());
		getPrefs().setValue(null, recId + SNIPPET_DIR + NAME, snippetsDir.getName());

		getPrefs().setValue(null, recId + SNIPPET_EXT + NAME, snippetsExt);

		if (tokenFactory.isEmpty()) {
			getPrefs().setToDefault(recId + TOKEN_FACTORY + PATH);
			getPrefs().setToDefault(recId + TOKEN_FACTORY + NAME);
		} else {
			getPrefs().setValue(null, recId + TOKEN_FACTORY + PATH, tokenFactory.getPath());
			getPrefs().setValue(null, recId + TOKEN_FACTORY + NAME, tokenFactory.getName());
		}

		if (errorStrategy.isEmpty()) {
			getPrefs().setToDefault(recId + PARSER_STRATEGY + PATH);
			getPrefs().setToDefault(recId + PARSER_STRATEGY + NAME);
		} else {
			getPrefs().setValue(null, recId + PARSER_STRATEGY + PATH, errorStrategy.getPath());
			getPrefs().setValue(null, recId + PARSER_STRATEGY + NAME, errorStrategy.getName());
		}

		getPrefs().save();
	}

	private IDslPrefsManager getPrefs() {
		return AntlrDTCore.getDefault().getPrefsManager();
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

	public Source getErrorStrategy() {
		return errorStrategy;
	}

	public void setErrorStrategy(Source errorStrategy) {
		this.errorStrategy = errorStrategy;
	}
}
