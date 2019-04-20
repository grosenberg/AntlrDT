package net.certiv.antlrdt.vis.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import net.certiv.antlrdt.vis.views.paths.PathView;

public class OpenPathsViewHandler extends AbstractHandler {

	public OpenPathsViewHandler() {}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(PathView.ID);
		} catch (PartInitException e) {
			throw new ExecutionException(e.getMessage(), e.getCause());
		}
		return null;
	}
}
