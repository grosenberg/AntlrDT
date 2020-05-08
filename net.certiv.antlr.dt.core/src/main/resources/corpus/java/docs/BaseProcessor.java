/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.core;

import org.antlr.v4.runtime.RecognitionException;

import net.certiv.adept.Settings;
import net.certiv.adept.Tool;
import net.certiv.adept.core.util.Facet;
import net.certiv.adept.lang.Builder;
import net.certiv.adept.lang.ISourceParser;
import net.certiv.adept.model.Document;
import net.certiv.adept.tool.ErrorType;
import net.certiv.adept.util.Log;
import net.certiv.adept.util.Time;

public abstract class BaseProcessor {

	protected CoreMgr mgr;
	protected Settings settings;
	protected Builder builder;

	public BaseProcessor(CoreMgr mgr, Settings settings) {
		this.mgr = mgr;
		this.settings = settings;
	}

	/**
	 * Process the given document.
	 * <p>
	 * The steps are:
	 * <ol>
	 * <li>build a parser appropriate for the document content
	 * <li>parse the document content
	 * <li>build working indexes against the parse-tree and token stream
	 * <li>define a line-oriented indentation profile
	 * <li>locate and characterize possibly alignable symbols
	 * <li>extract the feature set from the parse-tree
	 * </ol>
	 *
	 * @param doc the document containing the content to process
	 * @param check if {@code true}, stop after generation of the parse tree.
	 * @return {@code true} on success
	 */
	public boolean processDocument(Document doc, boolean check) {
		Time.start(Facet.PARSE);
		ISourceParser parser = mgr.getLanguageParser();
		builder = new Builder(mgr, doc);
		try {
			parser.process(builder, doc);
		} catch (RecognitionException e) {
			Log.error(this, ErrorType.PARSE_ERROR.msg + ": " + doc.getPathname());
			Tool.errMgr.toolError(ErrorType.PARSE_ERROR, doc.getPathname());
			return false;
		} catch (Exception e) {
			Log.error(this, ErrorType.PARSE_FAILURE.msg + ": " + doc.getPathname());
			Tool.errMgr.toolError(ErrorType.PARSE_FAILURE, e, doc.getPathname());
			return false;
		}

		if (check) return false;

		builder.index();

		try {
			parser.defineIndentation(builder.indenter());
			parser.locateAlignables(builder.aligner());
			parser.extractFeatures(builder);
			builder.extractCommentFeatures();
		} catch (Exception e) {
			Log.error(this, ErrorType.VISITOR_FAILURE.msg + ": " + doc.getPathname(), e);
			Tool.errMgr.toolError(ErrorType.VISITOR_FAILURE, e, doc.getPathname());
			return false;
		}

		Time.stop(Facet.PARSE);
		return true;
	}

	public CoreMgr getMgr() {
		return mgr;
	}

	public Settings getSettings() {
		return settings;
	}

	public Builder getBuilder() {
		return builder;
	}
}
