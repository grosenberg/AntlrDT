package net.certiv.antlrdt.vis.model;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;

import net.certiv.antlrdt.core.parser.AntlrToken;
import net.certiv.antlrdt.core.parser.IPathProcessor;
import net.certiv.antlrdt.core.parser.Target;

public class PathModel implements IPathProcessor, IModel {

	// private static final String DENT = " ";
	// private int level;

	// key = unique node key; value = node
	private final Map<String, PathNode> nodes = new LinkedHashMap<>();

	// key = node; value = out-bound connectors
	private final Map<PathNode, List<PathConnector>> connectors = new LinkedHashMap<>();

	// -------------------------------------------
	// Path walker callback

	private final Deque<PathNode> stack = new ArrayDeque<>();

	@Override
	public void addNode(ParserRuleContext ctx, AntlrToken term, Target kind) {
		// Log.info(this, String.format("Examine '%s' [%s]", term.getTextEncoded(),
		// kind));
		PathNode node = nodes.get(term.getText());
		if (node == null) {
			node = new PathNode(ctx, term, kind);
			nodes.put(term.getText(), node);
		}
		if (node != stack.peek()) {
			addConnector(stack.peek(), node);
			if (kind != Target.TERMINAL) {
				log(node.toString(), +1);
				stack.push(node);
			}
		}
	}

	@Override
	public void end() {
		if (stack.peek() != null) {
			PathNode node = stack.pop();
			log(node.toString(), -1);
		} else {
			log("NULL!", -1);
		}
	}

	private void log(String msg, int chg) {
		// if (chg > 0) {
		// String dent = Strings.dup(level, DENT);
		// Log.info(this, "=>> " + dent + msg);
		// level += chg;
		// } else {
		// level += chg;
		// level = level > 0 ? level : 0;
		// String dent = Strings.dup(level, DENT);
		// Log.info(this, "<<= " + dent + msg);
		// }
	}

	// ------------------------------------------------

	private void addConnector(PathNode parent, PathNode child) {
		if (parent != null) {
			// Log.info(this, String.format("%s -> %s", parent, child));
			PathConnector connector = new PathConnector(parent, child);
			addEndpoint(parent, connector);
			addEndpoint(child, connector);

			parent.addChild(child);
			child.addParent(parent);
		}
	}

	private void addEndpoint(PathNode node, PathConnector conn) {
		List<PathConnector> fanouts = connectors.get(node);
		if (fanouts == null) {
			fanouts = new ArrayList<>();
			connectors.put(node, fanouts);
		}
		fanouts.add(conn);
	}

	public PathNode get(String rulename) {
		return nodes.get(rulename);
	}

	/** Returns the direct callers of the given target. */
	public List<PathNode> findCallers(PathNode target) {
		return new ArrayList<>(target.getParents());
	}

	/** Returns the direct callees of the given target. */
	public List<PathNode> findCallees(PathNode target) {
		return new ArrayList<>(target.getChildren());
	}

	public List<PathNode> getNodeList() {
		return new ArrayList<>(connectors.keySet());
	}

	@Override
	public void clear() {
		connectors.clear();
	}

	@Override
	public void dispose() {
		connectors.clear();
	}

	@Override
	public String toString() {
		return "PathsModel [connectors=" + connectors.size() + "]";
	}
}
