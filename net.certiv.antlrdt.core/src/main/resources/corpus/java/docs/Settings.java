/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept;

import java.nio.file.Path;

import com.google.gson.annotations.Expose;

/** Settings. */
public class Settings {

	// settings to use when analyzing a source document

	@Expose public Boolean backup = true;
	@Expose public Boolean check;						// limit to checking parse operations
	@Expose public Boolean learn;						// store document to corpus
	@Expose public Boolean rebuild;						// force parsing of corpus documents
	@Expose public Boolean save;

	@Expose public String corpusRoot;					// path of corpus root directory
	@Expose public String lang;
	@Expose public String config;						// settings file pathname
	@Expose public String verbose;
	@Expose public Integer tabWidth = 4;

	// settings that control formatting

	@Expose public Boolean format = true;
	@Expose public Boolean breakLongLines = false;
	@Expose public Boolean alignFields = false;
	@Expose public Boolean alignComments = true;

	@Expose public Boolean formatComments = true;
	@Expose public Boolean formatHdrComment = false;
	@Expose public Boolean removeBlankLinesComment = false;

	@Expose public Boolean useTabs = true;
	@Expose public Boolean removeBlankLines = true;		// remove blank lines in code
	@Expose public Integer keepNumBlankLines = 1;		// but keep at least this number
	@Expose public Boolean removeTrailingWS = true;
	@Expose public Boolean forceLastLineBlank = true;	// file must end with blank line

	@Expose public Boolean joinLines = true;			// allow if indicated by feature match

	@Expose public Integer lineWidth = 120;				// target formatted width of lines
	@Expose public Integer commentWidth = 100;			// ... that end with a comment

	// dynamic settings not persisted

	public Path corpusDir;								// root dir + lang
	public String corpusExt;							// lang extention
	public Integer corpusTabWidth = 4;
}
