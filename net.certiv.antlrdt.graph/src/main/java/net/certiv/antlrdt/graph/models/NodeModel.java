package net.certiv.antlrdt.graph.models;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import javafx.scene.paint.Color;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.core.resources.IFile;
import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.gef.graph.Edge;
import org.eclipse.gef.graph.Graph;
import org.eclipse.gef.graph.Node;
import org.eclipse.gef.layout.LayoutProperties;
import org.eclipse.swt.graphics.RGB;

import com.google.common.collect.Lists;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.parser.ITargetInfo;
import net.certiv.antlrdt.core.parser.Target;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.FragmentRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.LexerRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.ParserRuleSpecContext;
import net.certiv.antlrdt.core.parser.gen.AntlrDT4Parser.TerminalContext;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.graph.shapes.NodeShape;
import net.certiv.antlrdt.graph.view.tree.PathInfo;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.preferences.DslPrefsManager;
import net.certiv.dsl.core.util.Chars;
import net.certiv.dsl.core.util.Strings;

public class NodeModel extends Node implements IModel {

	private final PropertyChangeSupport queue = new PropertyChangeSupport(this);

	private final List<EdgeModel> incoming = Lists.newArrayList();
	private final List<EdgeModel> outgoing = Lists.newArrayList();

	private DslPrefsManager prefsMgr;
	private ImageManager imgMgr;
	private boolean hidden;
	private NodeShape shape;

	protected ParseTree ctx;
	protected ITargetInfo info;

	public NodeModel() {
		super();

		prefsMgr = AntlrDTCore.getDefault().getPrefsManager();
		imgMgr = AntlrDTUI.getDefault().getImageManager();

		LayoutProperties.setLocation(this, new Point());
		LayoutProperties.setSize(this, new Dimension());
	}

	public void init(ParseTree ctx, ITargetInfo info) {
		this.ctx = ctx;
		this.info = info;
	}

	public IFile getGrammar() {
		return info.getGrammar();
	}

	public void addOutgoingConnection(EdgeModel conn) {
		outgoing.add(conn);
		fire(PROP_OUTGOING_CONNECTIONS, null, conn);
	}

	public void removeOutgoingConnection(EdgeModel conn) {
		outgoing.remove(conn);
		fire(PROP_OUTGOING_CONNECTIONS, conn, null);
	}

	public List<EdgeModel> getOutgoingConnections() {
		return outgoing;
	}

	public void addIncomingConnection(EdgeModel conn) {
		incoming.add(conn);
		fire(PROP_INCOMING_CONNECTIONS, null, conn);
	}

	public void removeIncomingConnection(EdgeModel conn) {
		incoming.remove(conn);
		fire(PROP_INCOMING_CONNECTIONS, conn, null);
	}

	public List<EdgeModel> getIncomingConnections() {
		return incoming;
	}

	// Property Change Support
	public void fire(String prop, Object oldValue, Object newValue) {
		queue.firePropertyChange(prop, oldValue, newValue);
	}

	public void addPropertyChangeListener(PropertyChangeListener listener) {
		queue.addPropertyChangeListener(listener);
	}

	public void removePropertyChangeListener(PropertyChangeListener listener) {
		queue.removePropertyChangeListener(listener);
	}

	@Override
	public Set<Edge> getAllIncomingEdges() {
		return new HashSet<>(incoming);
	}

	@Override
	public Set<Edge> getIncomingEdges() {
		return new HashSet<>(incoming);
	}

	@Override
	public Set<Edge> getAllOutgoingEdges() {
		return new HashSet<>(outgoing);
	}

	@Override
	public Set<Edge> getOutgoingEdges() {
		return new HashSet<>(outgoing);
	}

	@Override
	public Set<Node> getAllPredecessorNodes() {
		return super.getPredecessorNodes();
	}

	@Override
	public Set<Node> getAllSuccessorNodes() {
		return super.getSuccessorNodes();
	}

	@Override
	public Graph getNestedGraph() {
		throw new IllegalArgumentException("Nested graphs not supported");
	}

	@Override
	public void setNestedGraph(Graph nestedGraph) {
		throw new IllegalArgumentException("Nested graphs not supported");
	}

	// -----------------
	// Shape support

	public NodeShape getShape() {
		return shape;
	}

	/** Call back providing a reference to the {@code NodeShape} instance. */
	public void setShape(NodeShape shape) {
		this.shape = shape;
	}

	public boolean isHidden() {
		return hidden;
	}

	public void setHidden(boolean hidden) {
		this.hidden = hidden;
	}

	/** Return a URL string defining an icon image to be used to decorate the {@code NodeShape}. */
	public String getIconUrl() {
		if (ctx instanceof ParserRuleSpecContext) return imgMgr.getUrl(imgMgr.IMG_NODE_PARSER).toExternalForm();
		if (ctx instanceof LexerRuleSpecContext) return imgMgr.getUrl(imgMgr.IMG_NODE_LEXER).toExternalForm();
		if (ctx instanceof FragmentRuleSpecContext) return imgMgr.getUrl(imgMgr.IMG_NODE_FRAGMENT).toExternalForm();
		if (ctx instanceof TerminalContext) return imgMgr.getUrl(imgMgr.IMG_NODE_TERMINAL).toExternalForm();

		if (ctx instanceof ErrorNode) return imgMgr.getUrl(imgMgr.ERROR_NODE).toExternalForm();
		if (ctx instanceof TerminalNode) return imgMgr.getUrl(imgMgr.IMG_OBJ_TERMINAL).toExternalForm();
		return imgMgr.getUrl(imgMgr.IMG_OBJ_RULE).toExternalForm();
	}

	/** Return the full text of the node context. */
	public String getText() {
		TerminalNode node = null;
		if (ctx instanceof RuleNode) {

			// specific to Antlr grammar
			if (ctx instanceof ParserRuleSpecContext) {
				if (info.getTargetType() == Target.ALT) {
					return ((PathInfo) info).getPathTerm().getText();
				}
				node = ((ParserRuleSpecContext) ctx).RULE_REF();
			}
			if (ctx instanceof LexerRuleSpecContext) {
				if (info.getTargetType() == Target.ALT) {
					return ((PathInfo) info).getPathTerm().getText();
				}
				node = ((LexerRuleSpecContext) ctx).TOKEN_REF();
			}
			if (ctx instanceof FragmentRuleSpecContext) {
				if (info.getTargetType() == Target.ALT) {
					return ((PathInfo) info).getPathTerm().getText();
				}
				node = ((FragmentRuleSpecContext) ctx).TOKEN_REF();
			}
			if (ctx instanceof TerminalContext) {
				if (info.getTargetType() == Target.ALT) {
					return ((PathInfo) info).getPathTerm().getText();
				}
				node = ((TerminalContext) ctx).TOKEN_REF();
			}

			if (node != null) {
				try {
					return node.toString();
				} catch (Exception e) {}
			}

			// client grammar
			int idx = ((RuleContext) ctx).getRuleIndex();
			String[] ruleNames = info.getRuleNames();
			idx = ruleNames != null && idx >= 0 && idx < ruleNames.length ? idx : -1;
			return idx > -1 ? ruleNames[idx] : "<unknown>";
		}

		node = (TerminalNode) ctx;
		try {
			return node.toString();
		} catch (Exception e) {
			int ttype = node.getSymbol().getType();
			String[] tokenNames = info.getTokenNames();
			ttype = tokenNames != null && ttype >= 0 && ttype < tokenNames.length ? ttype : -1;
			return ttype > -1 ? tokenNames[ttype] : "<unknown>";
		}
	}

	/** Returns the ellipsized text suitable for use in the {@code NodeShape}. */
	public String getDisplayText() {
		return Strings.displyEscape(getText(), 36);
	}

	public String getCssID() {
		if (ctx instanceof RuleNode) return "rule";
		if (ctx instanceof ErrorNode) return "errorNode";
		return "terminalNode";
	}

	public String getCssClass() {
		if (ctx instanceof TerminalNode && Chars.isWhitespace(ctx.getText())) return "whitespace";
		return Strings.EMPTY_STRING;
	}

	public Color getColor() {
		if (ctx instanceof RuleNode) return convert(PrefsKey.PT_NODE_RULE);
		if (ctx instanceof ErrorNode) return convert(PrefsKey.PT_NODE_ERROR);
		if (ctx instanceof TerminalNode) return convert(PrefsKey.PT_NODE_TERMINAL);
		return convert(PrefsKey.PT_NODE_BACKGROUND);
	}

	/**
	 * Bounds are initially set in response to a call from shape creation. Subsequently, bounds may be
	 * set/altered in response to node transformations. In both cases, size and location are held as
	 * node attributes as necessary to support layout.
	 */
	public void setBounds(Rectangle bounds) {
		setBounds(bounds.getLocation(), bounds.getSize());
	}

	public void setBounds(Point loc, Dimension size) {
		// Log.info(this, String.format("SetBounds (location): %s '%s'.", loc, getText()));
		LayoutProperties.setLocation(this, loc);
		LayoutProperties.setSize(this, size);
	}

	public Point getLocation() {
		return LayoutProperties.getLocation(this);
	}

	public Dimension getSize() {
		return LayoutProperties.getSize(this);
	}

	public Rectangle getBounds() {
		return new Rectangle(getLocation(), getSize());
	}

	public ParseTree getParserRuleContext() {
		return ctx;
	}

	public String getNodeDescription() {
		if (ctx instanceof ErrorNode) return "Error Node";
		if (ctx instanceof TerminalNode) return "Terminal Node";
		return "Parser Rule";
	}

	public String getNodeType() {
		if (ctx instanceof TerminalNode) {
			Token token = ((TerminalNode) ctx).getSymbol();
			return info.getTokenNames()[token.getType()];
		}

		ParserRuleContext context = ((ParserRuleContext) ctx);
		return info.getTokenNames()[context.getRuleIndex()];
	}

	public int getNodeSourceLine() {
		if (ctx instanceof TerminalNode) {
			return ((TerminalNode) ctx).getSymbol().getLine();
		} else {
			return ((ParserRuleContext) ctx).getStart().getLine();
		}
	}

	public int getNodeSourceOffsetInLine() {
		if (ctx instanceof TerminalNode) {
			return ((TerminalNode) ctx).getSymbol().getCharPositionInLine();
		} else {
			return ((ParserRuleContext) ctx).getStart().getCharPositionInLine();
		}
	}

	public int getNodeSourceLength() {
		return ctx.getText().length();
	}

	private Color convert(String key) {
		RGB c = prefsMgr.getColor(key).getRGB();
		return Color.rgb(c.red, c.blue, c.green);
	}

	public void dispose() {
		incoming.clear();
		outgoing.clear();
		info = null;
		ctx = null;
		shape = null;
	}

	@Override
	public int hashCode() {
		return Objects.hash(ctx);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (!(obj instanceof NodeModel)) return false;
		NodeModel other = (NodeModel) obj;
		return Objects.equals(ctx, other.ctx);
	}
}
