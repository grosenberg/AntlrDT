package net.certiv.antlrdt.graph.operations;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.commands.operations.AbstractOperation;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;
import org.eclipse.ui.IViewPart;

import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.antlrdt.graph.parts.TreeNodePart;
import net.certiv.antlrdt.graph.view.PathOp;
import net.certiv.antlrdt.graph.view.tree.PathsView;
import net.certiv.dsl.core.util.CoreUtil;

public class PathsOperation extends AbstractOperation implements ITransactionalOperation {

	private final TreeNodePart nodePart;
	private final PathOp op;

	public PathsOperation(TreeNodePart nodePart, PathOp op) {
		super("Change graph");
		this.nodePart = nodePart;
		this.op = op;
	}

	@Override
	public IStatus execute(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		IViewPart view = CoreUtil.getActivePage().findView(PathsView.VIEW_ID);
		if (view instanceof PathsView) {
			NodeModel model = nodePart.getContent();
			String rulename = model.getText();
			((PathsView) view).updateGraph(null, rulename, op);
			return Status.OK_STATUS;
		}
		return Status.CANCEL_STATUS;
	}

	@Override
	public boolean isContentRelevant() {
		return true;
	}

	@Override
	public boolean isNoOp() {
		return false;
	}

	@Override
	public IStatus redo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return Status.OK_STATUS;
	}

	@Override
	public IStatus undo(IProgressMonitor monitor, IAdaptable info) throws ExecutionException {
		return Status.OK_STATUS;
	}
}
