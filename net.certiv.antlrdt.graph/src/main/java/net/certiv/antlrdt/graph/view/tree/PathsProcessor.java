package net.certiv.antlrdt.graph.view.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.tree.ParseTree;
import org.eclipse.gef.graph.Edge;

import com.google.common.collect.Maps;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.builder.AntlrDTBuilder;
import net.certiv.antlrdt.core.parser.IPathProcessor;
import net.certiv.antlrdt.core.parser.Target;
import net.certiv.antlrdt.core.parser.gen.PathVisitor;
import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.models.EdgeModel;
import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.model.DslSourceRoot;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.builder.ModelManager;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.preferences.consts.Builder;

public class PathsProcessor implements IPathProcessor {

	private final Deque<NodeModel> stack = new ArrayDeque<>();

	// key = rule context; value = context model
	private final Map<ParseTree, NodeModel> contexts = Maps.newLinkedHashMap();
	// key = rule name; value = context model
	private final Map<String, NodeModel> names = Maps.newLinkedHashMap();

	private AntlrDTCore core;
	private DslParseRecord record;
	private DiagramModel model;

	public PathsProcessor(DslParseRecord record) {
		this.record = record;
		core = AntlrDTCore.getDefault();
		model = new DiagramModel();
	}

	public DiagramModel getModel(PathsView pathsView, String rulename) {
		// -- get all related units
		List<ICodeUnit> related = getRelated(record.unit);

		try {

			// -- collect all rule names -> make nodeModels
			for (ICodeUnit unit : related) {
				DslParseRecord rec = unit.getParseRecord();
				if (rec != null && rec.tree != null) {
					PathVisitor walker = new PathVisitor(rec.tree);
					walker.setHelper(this);
					walker.find("parserRule", "lexerRule", "fragmentRule");
				}
			}

			// -- collect all rule alts -> make edgeModels
			for (ICodeUnit unit : related) {
				DslParseRecord rec = unit.getParseRecord();
				if (rec != null && rec.tree != null) {
					PathVisitor walker = new PathVisitor(rec.tree);
					walker.setHelper(this);
					walker.findAll();
				}
			}
		} catch (Exception e) {
			Log.error(this, "Error in paths walk: " + e.getMessage(), e);
			return null;
		}

		NodeModel target = names.get(rulename);
		if (target == null) return null;
		unhideRootPaths(target);
		return model;
	}

	private List<ICodeUnit> getRelated(ICodeUnit unit) {
		Set<ICodeUnit> units = new HashSet<>();
		units.add(unit);

		ModelManager mgr = core.getModelManager();
		for (ICodeUnit related : mgr.getIndexer().getRelated(unit)) {
			boolean ok = valid(related);
			if (ok) units.add(related);
		}
		List<ICodeUnit> results = new ArrayList<>(units);
		results.sort(AntlrDTBuilder.NameComp);
		return results;
	}

	private boolean valid(ICodeUnit unit) {
		if (unit == null) return false;
		if (unit.hasErrors()) return false;
		DslSourceRoot srcRoot = unit.getSourceRoot();
		if (srcRoot == null || srcRoot.isOutputRoot()) return false;
		if (!allowNativeSourcePath() && srcRoot.isNativeSourceRoot()) return false;
		if (!allowExtraSourcePath() && srcRoot.isExtraSourceRoot()) return false;

		return true;
	}

	/** allow if on a native source path */
	private boolean allowNativeSourcePath() {
		return core.getPrefsManager().getBoolean(Builder.BUILDER_PERMIT_NATIVE_SOURCE_PATH);
	}

	/** allow if on a special path */
	private boolean allowExtraSourcePath() {
		return core.getPrefsManager().getBoolean(Builder.BUILDER_PERMIT_EXTRA_SOURCE_PATH);
	}

	private void unhideRootPaths(NodeModel target) {
		target.setHidden(false);
		for (Edge in : target.getAllIncomingEdges()) {
			EdgeModel edge = (EdgeModel) in;
			edge.setHidden(false);
			NodeModel source = edge.getSource();
			if (source.isHidden()) unhideRootPaths(source);
		}
	}

	/** Unhide the nodes connected as outbound path 'callees' of the given rulename identified node. */
	public void addCalleePaths(String rulename) {
		NodeModel source = names.get(rulename);
		for (Edge out : source.getAllOutgoingEdges()) {
			EdgeModel edge = (EdgeModel) out;
			if (edge.isHidden()) {
				unhide(edge.getTarget());
			}
		}
	}

	/** Unhide the nodes connected as inbound path 'callers' of the given rulename identified node. */
	public void addCallerPaths(String rulename) {
		NodeModel target = names.get(rulename);
		for (Edge in : target.getAllIncomingEdges()) {
			EdgeModel edge = (EdgeModel) in;
			if (edge.isHidden()) {
				unhide(edge.getSource());
			}
		}
	}

	public void removeNode(String rulename) {
		NodeModel source = names.get(rulename);
		source.setHidden(true);
		for (Edge in : source.getAllIncomingEdges()) {
			EdgeModel edge = (EdgeModel) in;
			if (!edge.isHidden()) {
				edge.setHidden(true);
				NodeModel node = edge.getSource();
				if (orphan(node)) node.setHidden(true);
			}
		}
		for (Edge out : source.getAllOutgoingEdges()) {
			EdgeModel edge = (EdgeModel) out;
			if (!edge.isHidden()) {
				edge.setHidden(true);
				NodeModel node = edge.getTarget();
				if (orphan(node)) node.setHidden(true);
			}
		}
	}

	// unhides the given target and its edges to any other existing visible nodes
	private void unhide(NodeModel target) {
		target.setHidden(false);
		for (Edge in : target.getAllIncomingEdges()) {
			EdgeModel edge = (EdgeModel) in;
			if (!edge.getSource().isHidden()) edge.setHidden(false);
		}
		for (Edge out : target.getAllOutgoingEdges()) {
			EdgeModel edge = (EdgeModel) out;
			if (!edge.getTarget().isHidden()) edge.setHidden(false);
		}
	}

	private boolean orphan(NodeModel source) {
		for (Edge in : source.getAllIncomingEdges()) {
			EdgeModel edge = (EdgeModel) in;
			if (!edge.isHidden()) return false;
		}
		for (Edge out : source.getAllOutgoingEdges()) {
			EdgeModel edge = (EdgeModel) out;
			if (!edge.isHidden()) return false;
		}
		return true;
	}

	// -------------------------------------------
	// Tree walker callbacks

	@Override
	public void addRuleSpec(ParserRuleContext ctx, Token term, Target kind) {
		NodeModel nodeModel = contexts.get(ctx);
		if (nodeModel == null) {
			nodeModel = new NodeModel();
			nodeModel.init(ctx, new PathInfo(term, kind, record));
			nodeModel.setHidden(true);
			contexts.put(ctx, nodeModel);
			names.put(term.getText(), nodeModel);
			model.addNode(nodeModel);
		}
		stack.push(nodeModel);
	}

	@Override
	public void addRuleAlt(ParserRuleContext ctx, Token term) {
		NodeModel source = stack.peek();
		NodeModel target = names.get(term.getText());
		if (target == null) {
			target = new NodeModel();
			target.init(ctx, new PathInfo(term, Target.ALT, record));
			target.setHidden(true);
			contexts.put(ctx, target);
			names.put(term.getText(), target);
			model.addNode(target);
		}

		if (!connected(source, target)) {
			EdgeModel edgeModel = new EdgeModel(model, source, target);
			edgeModel.setHidden(true);
			source.addOutgoingConnection(edgeModel);
			target.addIncomingConnection(edgeModel);
			model.addEdge(edgeModel);
		}
	}

	@Override
	public void endRule() {
		stack.pop();
	}

	private boolean connected(NodeModel source, NodeModel target) {
		for (Edge edge : source.getAllOutgoingEdges()) {
			if (edge.getTarget().equals(target)) return true;
		}
		return false;
	}
}
