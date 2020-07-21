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

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroup;
import org.stringtemplate.v4.STGroupFile;

import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4ParserBaseListener;
import net.certiv.antlr.dt.diagram.preferences.Prefs;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.core.util.stores.Pair;

/** A graphviz DOT source generator. */
public class DotBuilder {

	private static final String DIAGRAM_STG = "templates/diagram.stg";
	private static final String DIAGRAM = "diagram";
	private static final String EDGE = "edge";
	private static final String NODE = "node";

	public static final String NORMAL = "normal";
	public static final String TB = "TB";
	public static final String LR = "LR";

	private final STGroup stg = new STGroupFile(DIAGRAM_STG);

	private final PrefsManager mgr;

	public DotBuilder(PrefsManager mgr) {
		this.mgr = mgr;
	}

	public String generate(DslParseRecord rec) {
		DotGenListener tgt = new DotGenListener(rec);
		try {
			ParseTreeWalker.DEFAULT.walk(tgt, rec.getTree());
			return tgt.render();

		} catch (Exception e) {
			Log.error(this, "Generate failed: %s", e.getMessage());
			return Strings.EMPTY;
		}
	}

	public class DotGenListener extends AntlrDT4ParserBaseListener {

		private final DslParseRecord rec;
		private ST diagram;

		public DotGenListener(DslParseRecord rec) {
			this.rec = rec;
		}

		private void init(ParserRuleContext ctx) {
			String name = rec.unit.getPackageName();
			String rank = mgr.getString(Prefs.DIAGRAM_ORIENT);

			ST st = stg.getInstanceOf("graph");
			st.add("rankdir", rank);
			st.add("title", name);
			st.add("fontname", "Times New Roman");
			st.add("fontsize", 24);

			diagram = stg.getInstanceOf(DIAGRAM);
			diagram.add("name", name);
			diagram.add("graph", st);
		}

		@Override
		public void enterEveryRule(ParserRuleContext ctx) {
			if (diagram == null) init(ctx);

			String target = target(ctx);

			ST st = stg.getInstanceOf(NODE);
			st.add("name", target);
			st.add("shape", "ellipse");
			st.add("color", "black");
			st.add("label", target);
			st.add("fontname", "Arial");
			st.add("fontsize", 12);
			diagram.add("nodes", st);

			st = stg.getInstanceOf(EDGE);
			st.add("source", source(ctx));
			st.add("target", target);
			st.add("style", "solid");
			st.add("color", "black");
			st.add("arrowhead", NORMAL);
			diagram.add("edges", st);
		}

		@Override
		public void visitTerminal(TerminalNode node) {
			Pair<String, String> target = target(node);

			ST st = stg.getInstanceOf(NODE);
			st.add("name", target.left);
			st.add("shape", "ellipse");
			st.add("color", "blue");
			st.add("label", target.right);
			st.add("fontname", "Arial");
			st.add("fontsize", 12);
			diagram.add("nodes", st);

			st = stg.getInstanceOf(EDGE);
			st.add("source", source(node));
			st.add("target", target.left);
			st.add("style", "solid");
			st.add("color", "blue");
			st.add("arrowhead", NORMAL);
			diagram.add("edges", st);
		}

		@Override
		public void visitErrorNode(ErrorNode node) {
			Pair<String, String> target = target(node);

			ST st = stg.getInstanceOf(NODE);
			st.add("name", target.left);
			st.add("shape", "ellipse");
			st.add("color", "red");
			st.add("label", target.right);
			st.add("fontname", "Arial");
			st.add("fontsize", 12);
			diagram.add("nodes", st);

			st = stg.getInstanceOf(EDGE);
			st.add("source", source(node));
			st.add("target", target.left);
			st.add("style", "solid");
			st.add("color", "red");
			st.add("arrowhead", NORMAL);
			diagram.add("edges", st);
		}

		public String render() {
			return diagram.render();
		}

		@Override
		public String toString() {
			return render();
		}
	}

	public String source(ParseTree node) {
		ParserRuleContext parent = (ParserRuleContext) node.getParent();
		if (parent == null) return Strings.EMPTY;
		return AntlrDT4Parser.ruleNames[parent.getRuleIndex()];
	}

	public String target(ParserRuleContext ctx) {
		return AntlrDT4Parser.ruleNames[ctx.getRuleIndex()];
	}

	public Pair<String, String> target(TerminalNode node) {
		Token token = node.getSymbol();
		String name = AntlrDT4Parser.VOCABULARY.getSymbolicName(token.getType());
		String text = Strings.ellipsize(Strings.encode(token.getText()), 24);
		return Pair.of(name, String.format("{%s|%s}", name, text));
	}
}
