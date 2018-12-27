package net.certiv.antlrdt.graph.models;

import java.util.List;

import javafx.scene.paint.Color;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RuleContext;
import org.antlr.v4.runtime.tree.ErrorNode;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.RuleNode;
import org.antlr.v4.runtime.tree.TerminalNode;
import org.eclipse.core.resources.IFile;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;
import org.eclipse.swt.graphics.RGB;

import com.google.common.collect.Lists;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.preferences.DslPrefsManager;
import net.certiv.dsl.core.util.Chars;
import net.certiv.dsl.core.util.Strings;

public class NodeModel extends BaseModel {

	private final List<EdgeModel> incoming = Lists.newArrayList();
	private final List<EdgeModel> outgoing = Lists.newArrayList();

	protected ParseTree ctx;

	private DslPrefsManager prefsMgr;
	private ImageManager imgMgr;
	private Rectangle bounds;

	public NodeModel(ParseTree ctx, IFile grammar, String[] ruleNames, String[] tokenNames) {
		super(grammar, ruleNames, tokenNames);
		this.ctx = ctx;

		prefsMgr = AntlrDTCore.getDefault().getPrefsManager();
		imgMgr = AntlrDTUI.getDefault().getImageManager();
	}

	/** Return a URL string defining an icon image to be used to decorate the {@code NodeShape}. */
	public String getIconUrl() {
		if (ctx instanceof RuleNode) return imgMgr.getUrl(imgMgr.IMG_OBJ_RULE).toExternalForm();
		if (ctx instanceof ErrorNode) return imgMgr.getUrl(imgMgr.ERROR_NODE).toExternalForm();
		return imgMgr.getUrl(imgMgr.IMG_OBJ_TERMINAL).toExternalForm();
	}

	/** Return the full text of the node context. */
	public String getText() {
		if (ctx instanceof TerminalNode) {
			TerminalNode node = (TerminalNode) ctx;
			try {
				return node.toString();
			} catch (Exception e) {
				int ttype = node.getSymbol().getType();
				ttype = tokenNames != null && ttype >= 0 && ttype < tokenNames.length ? ttype : -1;
				return ttype > -1 ? tokenNames[ttype] : "<unknown>";
			}
		}
		if (ctx instanceof RuleNode) {
			RuleContext node = (RuleContext) ctx;
			int idx = node.getRuleIndex();
			idx = ruleNames != null && idx >= 0 && idx < ruleNames.length ? idx : -1;
			return idx > -1 ? ruleNames[idx] : "<unknown>";
		}
		return "";
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

	public Rectangle getBounds() {
		if (bounds == null) {
			double w = 24 + getDisplayText().length() * 8;
			bounds = new Rectangle(ranPoint(), new Point(w, 24));
		}
		return bounds;
	}

	public void setBounds(Rectangle bounds) {
		if (this.bounds != bounds) {
			Rectangle prior = this.bounds;
			this.bounds = bounds;
			fire(PROP_BOUNDS, prior, bounds);
		}
	}

	public Point getLocation() {
		Rectangle b = getBounds();
		return b.getLocation();
	}

	public void setLocation(Point location) {
		Rectangle b = getBounds();
		setBounds(b.setLocation(location));
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

	public void dispose() {
		for (EdgeModel in : incoming) {
			in.dispose();
		}
		for (EdgeModel out : outgoing) {
			out.dispose();
		}
		incoming.clear();
		outgoing.clear();
		bounds = null;
		ctx = null;
	}
}
