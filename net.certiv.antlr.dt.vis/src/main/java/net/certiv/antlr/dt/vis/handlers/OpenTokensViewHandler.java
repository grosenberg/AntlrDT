package net.certiv.antlr.dt.vis.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.handlers.HandlerUtil;

import net.certiv.antlr.dt.vis.views.tokens.TokensView;

public class OpenTokensViewHandler extends AbstractHandler {

	public OpenTokensViewHandler() {}

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			HandlerUtil.getActiveWorkbenchWindow(event).getActivePage().showView(TokensView.VIEW_ID);
		} catch (PartInitException e) {
			throw new ExecutionException(e.getMessage(), e.getCause());
		}
		return null;
	}
}
