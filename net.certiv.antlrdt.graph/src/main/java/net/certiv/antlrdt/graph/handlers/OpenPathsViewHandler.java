package net.certiv.antlrdt.graph.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import net.certiv.antlrdt.graph.view.tree.PathsView;

public class OpenPathsViewHandler extends AbstractHandler {

	public OpenPathsViewHandler() {}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(PathsView.VIEW_ID);
		} catch (PartInitException e) {
			throw new ExecutionException(e.getMessage(), e.getCause());
		}
		return null;
	}
}
