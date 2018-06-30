/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.core;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.certiv.adept.Settings;
import net.certiv.adept.Tool;
import net.certiv.adept.core.util.Facet;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.lang.antlr.parser.AntlrSourceParser;
import net.certiv.adept.lang.java.parser.JavaSourceParser;
import net.certiv.adept.lang.xvisitor.parser.XVisitorSourceParser;
import net.certiv.adept.model.CorpusModel;
import net.certiv.adept.model.DocModel;
import net.certiv.adept.model.Document;
import net.certiv.adept.model.Feature;
import net.certiv.adept.model.RefToken;
import net.certiv.adept.model.load.CorpusDocs;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.unit.TreeMultiset;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;

public class CoreMgr {

	private Settings settings;
	private DocModel docModel;
	private CorpusModel corModel;
	private ThreadGroup group;

	// key=docId; value=document
	private Map<Integer, Document> documents;

	public CoreMgr() {
		super();
	}

	/** Loads the corpus model. Builds the model if requested or required. */
	public boolean loadCorpusModel(Settings settings, List<String> pathnames) {
		this.settings = settings;

		CorpusProcessor cp = new CorpusProcessor(this, settings, pathnames);
		cp.loadModel();
		corModel = cp.getCorpusModel();

		return cp.isConsistent() ? true : false;
	}

	/**
	 * Executes against each of the documents identified by the given pathnames to parse the document
	 * and format matched elements consistent with the corpus model.
	 * <p>
	 * This is the primary user-oriented entry point.
	 *
	 * @param settings configuration parameters and related data
	 * @param pathnames identifies the documents to be evaluated
	 */
	public void execute(Settings settings, List<String> pathnames) {
		Time.start(Facet.EXECUTE);
		documents = loadDocuments(pathnames);
		Log.info(this, documents.size() + " source documents to process.");
		for (Document doc : documents.values()) {
			if (settings.learn) {
				CorpusDocs.writeDocument(settings.corpusDir, doc);
			} else {
				DocProcessor proc = new DocProcessor(this, doc, settings);
				boolean ok = proc.processDocument(doc, settings.check);
				docModel = proc.createDocModel();
				if (ok) {
					proc.match(corModel);
					proc.formatDocument();
				}
				Feature.clearPool();
				proc.dispose();
				// docModel.dispose();
			}
		}
		Time.stop(Facet.EXECUTE);
	}

	private Map<Integer, Document> loadDocuments(List<String> pathnames) {
		Map<Integer, Document> documents = new HashMap<>();
		for (String pathname : pathnames) {
			try {
				Document doc = loadDocument(pathname);
				documents.put(doc.getDocId(), doc);
			} catch (IOException e) {}
		}
		return documents;
	}

	private Document loadDocument(String pathname) throws IOException {
		File file = new File(pathname);
		if (!file.exists()) {
			Tool.errMgr.toolError(ErrorType.CANNOT_OPEN_FILE, pathname);
			throw new IOException("Source file does not exist: " + pathname);
		}

		byte[] content;
		try {
			content = Files.readAllBytes(file.toPath());
		} catch (IOException e) {
			Tool.errMgr.toolError(ErrorType.CANNOT_READ_FILE, pathname);
			throw e;
		}
		return new Document(pathname, settings.tabWidth, new String(content, StandardCharsets.UTF_8));
	}

	// ----

	/** Returns the source document identified by the given document id, or {@code null}. */
	public Document getSourceDocument(int docId) {
		if (documents == null) return null;
		return documents.get(docId);
	}

	/**
	 * Returns the filepath name of the source document identified by the given document id, or
	 * {@code null}.
	 */
	public String getSourceDocname(int docId) {
		Document doc = getSourceDocument(docId);
		if (doc != null) {
			return doc.getPathname();
		}
		return null;
	}

	/**
	 * Returns the filepath name of the corpus document identified by the given document id, or
	 * {@code null}.
	 */
	public String getCorpusDocname(int docId) {
		if (corModel == null) return null;
		return corModel.getPathname(docId);
	}

	/** Return the document model */
	public DocModel getDocModel() {
		return docModel;
	}

	/** Return the corpus model */
	public CorpusModel getCorpusModel() {
		return corModel;
	}

	public int getTabWidth() {
		return settings.tabWidth;
	}

	public int getCorpusTabWidth() {
		return settings.corpusTabWidth;
	}

	/** Returns the sets of best possible matches for the given feature, ordered by similarity. */
	public TreeMultiset<Double, RefToken> getMatches(Feature source, RefToken ref) {
		return corModel.getScoredMatches(source, ref);
	}

	public Feature getMatchingFeature(Feature source) {
		return corModel.getMatchingFeature(source);
	}

	// ----

	public ISourceParser getLanguageParser() {
		switch (settings.lang) {
			case "antlr":
				return new AntlrSourceParser();
			case "java":
				return new JavaSourceParser();
			case "xvisitor":
				return new XVisitorSourceParser();
		}
		return null;
	}

	public List<Integer> excludedLangTypes() {
		return getLanguageParser().excludedTypes();
	}

	public ThreadGroup getThreadGroup() {
		if (group == null) group = new ThreadGroup("Adept");
		return group;
	}
}
