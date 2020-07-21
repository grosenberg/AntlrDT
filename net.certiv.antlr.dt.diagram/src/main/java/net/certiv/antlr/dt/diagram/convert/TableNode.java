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
package net.certiv.antlr.dt.diagram.convert;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.certiv.antlr.runtime.xvisitor.util.Strings;

public class TableNode {

	public static final String[][] StdTableAttrs = { //
			{ "border", "0" }, { "cellborder", "1" }, { "cellspacing", "0" }, { "cellpadding", "4" } //
	};

	public static class Attr {
		public final String key;
		public final String value;

		public Attr(String[] attr) {
			key = attr != null && attr.length > 0 ? attr[0] : Strings.EMPTY;
			value = attr != null && attr.length > 1 ? attr[1] : Strings.EMPTY;
		}
	}

	public static class TableRow {
		public final List<TableCell> cells = new ArrayList<>();

		public TableRow(TableCell... cells) {
			this.cells.addAll(Arrays.asList(cells));
		}
	}

	public static class TableCell {
		public final String text;
		public final List<Attr> attrs = new ArrayList<>();

		public TableCell(String text, List<Attr> attrs) {
			this.text = text;
			this.attrs.addAll(attrs);
		}
	}

	// -----------------------------------------------

	public final List<Attr> attrs = new ArrayList<>();
	public final List<TableRow> rows = new ArrayList<>();

	public TableNode(String[]... attrs) {
		this.attrs.addAll(make(attrs));
	}

	public void addRow(TableCell... cells) {
		TableRow row = new TableRow(cells);
		rows.add(row);
	}

	public TableCell createCell(String text, String[]... attrs) {
		return new TableCell(text, make(attrs));
	}

	private List<Attr> make(String[][] attrs) {
		List<Attr> buf = new ArrayList<>();
		for (String[] attr : attrs) {
			buf.add(new Attr(attr));
		}
		return buf;
	}
}
