/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.vis.parse;

import java.util.concurrent.TimeUnit;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.builder.BuildUtil;
import net.certiv.antlr.dt.vis.tokens.Source;
import net.certiv.common.log.Log;
import net.certiv.common.util.Chars;
import net.certiv.common.util.Strings;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.ModelException;
import net.certiv.dsl.core.model.ModelStatus;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.preferences.PrefsManager;

public class TargetAssembly {

	private static final IPath DEF_SNIPPETS_DIR = new Path("test.snippets");

	public static final String PARSER = "Parser";
	public static final String LEXER = "Lexer";
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

	private AntlrCore core;
	private PrefsManager store;
	private IProject project;
	private ICodeUnit unit;

	private Source snippetsDir;
	private String snippetsExt = Strings.STAR;

	private Source tokenFactory;
	private Source token;
	private Source errorStrategy;
	private boolean trace;

	private String snippetDir; // defines the source data directory

	private String pkgname = Strings.EMPTY; // derived values
	private String pkgnameParser = Strings.EMPTY;
	private String pkgnameLexer = Strings.EMPTY;
	private String recId;

	public TargetAssembly(ICodeUnit unit) {
		this.unit = unit;
		core = AntlrCore.getDefault();
		store = core.getPrefsManager();
	}

	public void prepare() throws ModelException {
		try {
			boolean ok = unit.tryLock(500, TimeUnit.MILLISECONDS);
			if (!ok) throw new ModelException(ModelStatus.NO_LOCK, "Failed to obtain unit lock.");
		} catch (InterruptedException e) {
			Log.error(this, e.getMessage());
			throw new ModelException(ModelStatus.NO_LOCK, e);
		}

		String pkg = null;
		try {
			DslParseRecord record = unit.getDefaultParseRecord();
			Log.info(this, "Target tree: %s", record.check());
			pkg = BuildUtil.grammarDefinedPackage(record);
			Log.info(this, "Target pkg:  %s", pkg);

		} finally {
			unit.unlock();
		}

		prepNames(pkg);
		prepRecId();
		prepSnippedDir();
	}

	private void prepNames(String pkg) {
		String name = unit.getElementName();
		int dot = name.lastIndexOf(Chars.DOT);
		name = name.substring(0, dot); // remove extension

		dot = -1;
		if (name.endsWith(LEXER)) {
			dot = name.lastIndexOf(LEXER);
		} else if (name.endsWith(PARSER)) {
			dot = name.lastIndexOf(PARSER);
		}

		if (dot > -1) {
			pkgname = pkg + Strings.DOT + name.substring(0, dot);
			pkgnameParser = pkgname + PARSER;
			pkgnameLexer = pkgname + LEXER;

		} else {
			Log.error(this, "Unrecognizable grammar name: " + pkgname);
		}
	}

	// recId encodes project relative grammar name
	private void prepRecId() {
		String ext = unit.getFile().getFileExtension();
		recId = store.bind("{DSL_ID}." + pkgname + Strings.DOT + ext);
	}

	private void prepSnippedDir() throws ModelException {
		snippetDir = Strings.EMPTY;
		project = unit.getProject();
		if (!project.exists()) throw new ModelException(ModelStatus.INVALID_RESOURCE, "Project does not exist");

		IPath path = project.getLocation();
		if (project.exists(DEF_SNIPPETS_DIR)) {
			path = path.append(DEF_SNIPPETS_DIR);
		}
		snippetDir = path.toString();
	}

	public void load() {
		String path = store.getString(recId + SNIPPET_DIR + PATH, snippetDir);
		String name = store.getString(recId + SNIPPET_DIR + NAME, Strings.EMPTY);

		snippetsDir = new Source(path, name);
		snippetsExt = store.getString(recId + SNIPPET_EXT + NAME, Strings.STAR);

		String pathFac = store.getString(recId + TOKEN_FACTORY + PATH, Strings.EMPTY);
		String nameFac = store.getString(recId + TOKEN_FACTORY + NAME, Strings.EMPTY);

		tokenFactory = new Source(pathFac, nameFac);

		String pathTok = store.getString(recId + TOKEN + PATH, Strings.EMPTY);
		String nameTok = store.getString(recId + TOKEN + NAME, Strings.EMPTY);

		token = new Source(pathTok, nameTok);

		String pathErr = store.getString(recId + PARSER_STRATEGY + PATH, Strings.EMPTY);
		String nameErr = store.getString(recId + PARSER_STRATEGY + NAME, Strings.EMPTY);

		errorStrategy = new Source(pathErr, nameErr);
		trace = store.getBoolean(recId + PARSER_TRACE + NAME, false);
	}

	public void save() {
		store.setValue(recId + SNIPPET_DIR + PATH, snippetsDir.getPath());
		store.setValue(recId + SNIPPET_DIR + NAME, snippetsDir.getName());

		store.setValue(recId + SNIPPET_EXT + NAME, snippetsExt);

		if (tokenFactory.isEmpty()) {
			store.setToDefault(recId + TOKEN_FACTORY + PATH);
			store.setToDefault(recId + TOKEN_FACTORY + NAME);
		} else {
			store.setValue(recId + TOKEN_FACTORY + PATH, tokenFactory.getPath());
			store.setValue(recId + TOKEN_FACTORY + NAME, tokenFactory.getName());
		}

		if (token.isEmpty()) {
			store.setToDefault(recId + TOKEN + PATH);
			store.setToDefault(recId + TOKEN + NAME);
		} else {
			store.setValue(recId + TOKEN + PATH, token.getPath());
			store.setValue(recId + TOKEN + NAME, token.getName());
		}

		if (errorStrategy.isEmpty()) {
			store.setToDefault(recId + PARSER_STRATEGY + PATH);
			store.setToDefault(recId + PARSER_STRATEGY + NAME);
		} else {
			store.setValue(recId + PARSER_STRATEGY + PATH, errorStrategy.getPath());
			store.setValue(recId + PARSER_STRATEGY + NAME, errorStrategy.getName());
		}

		store.setValue(recId + PARSER_TRACE + NAME, trace);

		store.save();
	}

	public IProject getProject() {
		return project;
	}

	public IFile getGrammar() {
		return unit.getFile();
	}

	public String getParserFQName() {
		return pkgnameParser;
	}

	public String getLexerFQName() {
		return pkgnameLexer;
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
		return trace;
	}

	public void setTraceParser(boolean selected) {
		trace = selected;
	}
}
