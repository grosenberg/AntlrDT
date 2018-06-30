/*******************************************************************************
 * Copyright (c) 2017, 2018 Certiv Analytics. All rights reserved.
 * Use of this file is governed by the Eclipse Public License v1.0
 * that can be found in the LICENSE.txt file in the project root,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.adept.tool;

public class LangDescriptor {

	public String name;					// lang identifier name
	public String ext;					// corpus file extension
	public String corpusRoot;			// location of the corpus
	public int tabWidth;				// of indents in the corpus files

	public LangDescriptor() {}

	public LangDescriptor(String name, String ext, String corpusRoot, int tabWidth) {
		this.name = name;
		this.ext = ext;
		this.corpusRoot = corpusRoot;
		this.tabWidth = tabWidth;
	}
}
