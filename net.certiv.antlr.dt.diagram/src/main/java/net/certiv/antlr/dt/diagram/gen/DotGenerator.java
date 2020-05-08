package net.certiv.antlr.dt.diagram.gen;

import java.util.HashMap;
import java.util.Map;

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
import net.certiv.dsl.core.preferences.DslPrefsManager;
import net.certiv.dsl.core.util.Strings;

/** A graphviz DOT source generator. */
public class DotGenerator {

	private static final String DIAGRAM_STG = "templates/diagram.stg";
	private static final String DIAGRAM = "diagram";
	private static final String EDGE = "edge";
	private static final String NODE = "node";

	private static final String NORMAL = "normal";
	private static final String TB = "TB";
	private static final String LR = "LR";

	private final STGroup stg = new STGroupFile(DIAGRAM_STG);

	private final DslPrefsManager mgr;

	public DotGenerator(DslPrefsManager mgr) {
		this.mgr = mgr;
	}

	public String generate(DslParseRecord rec) {
		DotListener dl = new DotListener(rec);
		try {
			ParseTreeWalker.DEFAULT.walk(dl, rec.tree);
			return dl.toString();
		} catch (Exception e) {
			Log.error(this, "Generate failed: %s", e.getMessage());
			return Strings.EMPTY;
		}
	}

	public class DotListener extends AntlrDT4ParserBaseListener {

		private final Map<ParseTree, String> contexts = new HashMap<>();
		private int cnt = 1;

		private final DslParseRecord rec;
		private ST diagram;

		public DotListener(DslParseRecord rec) {
			this.rec = rec;
		}

		@Override
		public void enterEveryRule(ParserRuleContext ctx) {
			if (diagram == null) init(ctx);

			Names names = new Names(ctx, cnt);

			ST st = stg.getInstanceOf(NODE);
			st.add("name", names.target);
			st.add("shape", "ellipse");
			st.add("color", "black");
			st.add("label", names.label);
			st.add("fontname", "Arial");
			st.add("fontsize", 12);
			diagram.add("nodes", st);

			st = stg.getInstanceOf(EDGE);
			st.add("source", names.source);
			st.add("target", names.target);
			st.add("style", "solid");
			st.add("color", "black");
			st.add("arrowhead", NORMAL);
			diagram.add("edges", st);
		}

		@Override
		public void visitTerminal(TerminalNode node) {
			Names names = new Names(node, cnt);

			ST st = stg.getInstanceOf(NODE);
			st.add("name", names.target);
			st.add("shape", "ellipse");
			st.add("color", "blue");
			st.add("label", names.label);
			st.add("fontname", "Arial");
			st.add("fontsize", 12);
			diagram.add("nodes", st);

			st = stg.getInstanceOf(EDGE);
			st.add("source", names.source);
			st.add("target", names.target);
			st.add("style", "solid");
			st.add("color", "blue");
			st.add("arrowhead", NORMAL);
			diagram.add("edges", st);
		}

		@Override
		public void visitErrorNode(ErrorNode node) {
			Names names = new Names(node, cnt);

			ST st = stg.getInstanceOf(NODE);
			st.add("name", names.target);
			st.add("shape", "ellipse");
			st.add("color", "red");
			st.add("label", names.label);
			st.add("fontname", "Arial");
			st.add("fontsize", 12);
			diagram.add("nodes", st);

			st = stg.getInstanceOf(EDGE);
			st.add("source", names.source);
			st.add("target", names.target);
			st.add("style", "solid");
			st.add("color", "red");
			st.add("arrowhead", NORMAL);
			diagram.add("edges", st);
		}

		@Override
		public String toString() {
			return diagram.render();
		}

		private void init(ParserRuleContext ctx) {
			String name = rec.unit.getPackageName();
			String rank = mgr.getBoolean(Prefs.DIAGRAM_ORIENT_LR) ? LR : TB;

			ST st = stg.getInstanceOf("graph");
			st.add("rankdir", rank);
			st.add("title", name);
			st.add("fontname", "Times New Roman");
			st.add("fontsize", 24);

			diagram = stg.getInstanceOf(DIAGRAM);
			diagram.add("name", name);
			diagram.add("graph", st);
		}

		private class Names {
			String source = Strings.EMPTY;
			String target;
			String label;

			public Names(ParserRuleContext ctx, int cnt) {
				ParserRuleContext parent = ctx.getParent();
				if (parent != null) {
					source = contexts.get(parent);
				}

				label = AntlrDT4Parser.ruleNames[ctx.getRuleIndex()];
				target = String.format("%s-%s", label, cnt);

				contexts.put(ctx, target);
				cnt++;
			}

			public Names(TerminalNode node, int cnt) {
				ParserRuleContext parent = (ParserRuleContext) node.getParent();
				if (parent != null) {
					source = contexts.get(parent);
				}

				Token token = node.getSymbol();
				String tname = AntlrDT4Parser.VOCABULARY.getSymbolicName(token.getType());
				String text = Strings.ellipsize(token.getText(), 24);
				target = String.format("%s-%s", tname, cnt);
				label = String.format("%s '%s'", tname, text);

				contexts.put(node, target);
				cnt++;
			}
		}
	}
}
