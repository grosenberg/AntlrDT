/*******************************************************************************
 * Copyright (c) 2010-2015 Gerald Rosenberg & others. All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the standard 3-clause BSD License.  A copy of the License
 * is provided with this distribution in the License.txt file.
 *******************************************************************************/
package net.certiv.antlrdt.ui.wizards;

import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

public class Gen {

	public static final String TEMPLATE_ROOT = "/templates";

	private Gen() {}

	public static String content(String template, String name) {
		STGroup stg = new STGroupFile(TEMPLATE_ROOT + "/" + template + ".stg");
		ST st = stg.getInstanceOf(template);
		StringBuilder sb = new StringBuilder();
		st.add("name", name);
		sb.append(st.render());
		return sb.toString();
	}

	public static String content(String template, String name, String pkg, String superclass, String importTxt) {
		STGroup stg = new STGroupFile(TEMPLATE_ROOT + "/" + template + ".stg");
		ST st = stg.getInstanceOf(template);
		st.add("name", name);
		st.add("package", pkg);
		st.add("superclass", superclass);
		st.add("importTxt", importTxt);
		StringBuilder sb = new StringBuilder();
		sb.append(st.render());
		return sb.toString();
	}
}
