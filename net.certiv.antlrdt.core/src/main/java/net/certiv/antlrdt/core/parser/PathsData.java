package net.certiv.antlrdt.core.parser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenSource;

public class PathsData {

	public static final NilToken NILTOKEN = new NilToken();
	public static final int NILTOKEN_TYPE = -3;

	private Elements elements;
	private Map<String, Entry> entries;

	public PathsData() {
		super();
		this.elements = new Elements();
		this.entries = new LinkedHashMap<>();
	}

	/**
	 * Adds a path element to the data set.
	 * 
	 * @param ref containing rule
	 * @param term the term contained by the rule
	 * @param ruleType true if ref represents a parser rule; otherwise a lexer rule
	 * @param fragment true if ref represents a lexer fragment rule
	 */
	public void addPathsElement(Token ref, Token term, boolean parserRule, boolean fragment) {
		term = term != null ? term : NILTOKEN;
		elements.add(ref, term, parserRule, fragment);
	}

	public Collection<Entry> rules() {
		return entries.values();
	}

	public Entry namedEntry(String ruleName) {
		for (Entry item : rules()) {
			if (item.getRefName().equals(ruleName)) return item;
		}
		return null;
	}

	public List<PathsNode> getCalledRules(Entry rule) {
		Entry entry = elements.fwd.get(rule.getRefName());
		if (entry != null) return entry.relations;
		return Collections.emptyList();
	}

	public List<PathsNode> getCallingRules(Entry term) {
		Entry entry = elements.rev.get(term.getRefName());
		if (entry != null) return entry.relations;
		return Collections.emptyList();
	}

	public class Elements {

		Map<String, Entry> fwd; // fwd: rule -> terms contained
		Map<String, Entry> rev; // rev: term -> rules that contain them

		Elements() {
			super();
			fwd = new LinkedHashMap<>();
			rev = new LinkedHashMap<>();
		}

		void add(Token ref, Token term, boolean parserRule, boolean fragment) {
			// create forward entry
			Entry entry = fwd.get(ref.getText());
			if (entry == null) {
				entry = new Entry(ref, parserRule, fragment);
				fwd.put(ref.getText(), entry);
			}
			entry.add(PathsNode.get(term));

			// add to entryList; replaces reverse entries with forward
			entries.put(entry.getRefName(), entry);

			// create reverse entry
			entry = rev.get(term.getText());
			if (entry == null) {
				entry = new Entry(term);
				rev.put(term.getText(), entry);
			}
			entry.add(PathsNode.get(ref));

			// add to entryList if unique
			if (entries.get(entry.getRefName()) == null) {
				entries.put(entry.getRefName(), entry);
			}
		}

		// void dumpElements() {
		// Log.debug(this, "Entries dump");
		// for (Entry entry : entries.values()) {
		// Log.debug(this, " " + entry.getRefName());
		// }
		// Log.debug(this, "");
		// Log.debug(this, "Elements dump: fwd");
		// for (Entry entry : fwd.values()) {
		// StringBuilder sb = new StringBuilder();
		// for (PathsNode tok : entry.relations) {
		// sb.append(tok.getRuleName() + " ");
		// }
		// Log.debug(this, " " + entry.getRefName() + "-> " + sb.toString());
		// }
		// Log.debug(this, "");
		// Log.debug(this, "Elements dump: rev");
		// for (Entry entry : rev.values()) {
		// StringBuilder sb = new StringBuilder();
		// for (PathsNode tok : entry.relations) {
		// sb.append(tok.getRuleName() + " ");
		// }
		// Log.debug(this, " " + entry.getRefName() + "-> " + sb.toString());
		// }
		// }
	}

	// used for both forward and reverse relations
	// 'parserRule' and 'fragment' valid only if 'forward'
	public static class Entry {

		PathsNode ref;				// containing rule (rev: child term)
		List<PathsNode> relations;	// child terms (rev: rules containing child term)

		// forward entries
		public Entry(Token ref, boolean parserRule, boolean fragment) {
			this.ref = PathsNode.get(ref, parserRule, fragment);
			this.relations = new ArrayList<>();
		}

		// reverse entries
		Entry(Token ref) {
			this.ref = PathsNode.get(ref);
			this.relations = new ArrayList<>();
		}

		void add(PathsNode entry) {
			this.relations.add(entry);
		}

		public PathsNode getPathNode() {
			return ref;
		}

		public String getRefName() {
			return ref.ref.getText();
		}

		public List<PathsNode> getRelations() {
			return relations;
		}

		public boolean isTerminal() {
			return ref.terminal;
		}

		public boolean isParserRule() {
			return ref.parserRule;
		}

		public boolean isFragment() {
			return ref.fragment;
		}
	}

	public static class NilToken implements Token {

		@Override
		public String getText() {
			return "<Nil>";
		}

		@Override
		public int getType() {
			return NILTOKEN_TYPE;
		}

		@Override
		public int getLine() {
			return 0;
		}

		@Override
		public int getCharPositionInLine() {
			return 0;
		}

		@Override
		public int getChannel() {
			return 0;
		}

		@Override
		public int getTokenIndex() {
			return 0;
		}

		@Override
		public int getStartIndex() {
			return 0;
		}

		@Override
		public int getStopIndex() {
			return 0;
		}

		@Override
		public TokenSource getTokenSource() {
			return null;
		}

		@Override
		public CharStream getInputStream() {
			return null;
		}
	}

	// private void dumpElement(Token ref, Token term, boolean parserRule, boolean fragment) {
	// String parts = ref.getText() + "->" + term.getText();
	// String state = "[" + parserRule + ", " + fragment + "]";
	// Log.debug(this, "Element: " + parts + state);
	// }
}
