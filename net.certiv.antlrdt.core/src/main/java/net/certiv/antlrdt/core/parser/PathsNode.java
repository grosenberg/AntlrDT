package net.certiv.antlrdt.core.parser;

import java.util.LinkedHashMap;

import org.antlr.v4.runtime.Token;

public class PathsNode {

	private static final LinkedHashMap<String, PathsNode> cache = new LinkedHashMap<>();

	Token ref;				// node
	boolean terminal;		// terminal entry node
	boolean parserRule;		// is parser rule
	boolean fragment;		// is fragment rule

	/** Get a path node for forward entry nodes */
	public static PathsNode get(Token ref, boolean parserRule, boolean fragment) {
		PathsNode node = get(ref);
		node.parserRule = parserRule;
		node.fragment = fragment;
		return node;
	}

	/** Get a path node for reverse entry nodes & forward terminals */
	public static PathsNode get(Token term) {
		PathsNode node = cache.get(term.getText());
		if (node == null) {
			node = new PathsNode(term);
			node.terminal = true;
			cache.put(term.getText(), node);
		}
		return node;
	}

	/** Clears the cache */
	public static void clear() {
		cache.clear();
	}

	private PathsNode(Token ref) {
		this.ref = ref;
	}

	public Token getRef() {
		return ref;
	}

	public String getRuleName() {
		return ref.getText();
	}

	public boolean isTerminal() {
		return terminal;
	}

	public boolean isParserRule() {
		return parserRule;
	}

	public boolean isLexerRule() {
		return !parserRule;
	}

	public boolean isFragment() {
		return fragment;
	}
}
