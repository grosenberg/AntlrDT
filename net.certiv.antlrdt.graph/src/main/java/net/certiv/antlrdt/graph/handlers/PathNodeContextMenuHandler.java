package net.certiv.antlrdt.graph.handlers;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import org.eclipse.core.commands.ExecutionException;
import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.models.HoverModel;
import org.eclipse.gef.mvc.fx.operations.ITransactionalOperation;

import net.certiv.antlrdt.graph.operations.PathsOperation;
import net.certiv.antlrdt.graph.parts.TreeNodePart;
import net.certiv.antlrdt.graph.view.Kind;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.log.Log;

public class PathNodeContextMenuHandler extends AbstractHandler implements IOnClickHandler {

	private ImageManager imgMgr;

	protected PathNodeContextMenuHandler() {
		super();
		imgMgr = AntlrDTUI.getDefault().getImageManager();
	}

	@Override
	public void click(MouseEvent event) {
		if (!event.isSecondaryButtonDown()) return;

		ContextMenu menu = makeMenu();
		menu.show((Node) event.getTarget(), event.getScreenX(), event.getScreenY());
	}

	private ContextMenu makeMenu() {

		MenuItem addCallers = new MenuItem(Kind.ADD_CALLERS.text());
		addCallers.setGraphic(new ImageView(imgMgr.getUrl(imgMgr.IMG_ACTION_ADD_CALLERS).toExternalForm()));
		addCallers.setOnAction((e) -> {
			TreeNodePart host = (TreeNodePart) getHost();
			HoverModel hover = getHost().getViewer().getAdapter(HoverModel.class);
			if (host == hover.getHover()) hover.clearHover();

			try {
				ITransactionalOperation op = new PathsOperation(host, Kind.ADD_CALLERS);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException ex) {
				Log.error(this, ex.getMessage());
			}
		});

		MenuItem addCallees = new MenuItem(Kind.ADD_CALLEES.text());
		addCallees.setGraphic(new ImageView(imgMgr.getUrl(imgMgr.IMG_ACTION_ADD_PATHS).toExternalForm()));
		addCallees.setOnAction((e) -> {
			TreeNodePart host = (TreeNodePart) getHost();
			HoverModel hover = getHost().getViewer().getAdapter(HoverModel.class);
			if (host == hover.getHover()) hover.clearHover();

			try {
				ITransactionalOperation op = new PathsOperation(host, Kind.ADD_CALLEES);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException ex) {
				Log.error(this, ex.getMessage());
			}
		});

		MenuItem removeNode = new MenuItem(Kind.REMOVE_NODE.text());
		removeNode.setGraphic(new ImageView(imgMgr.getUrl(imgMgr.IMG_ACTION_REMOVE_PATHS).toExternalForm()));
		removeNode.setOnAction((e) -> {
			TreeNodePart host = (TreeNodePart) getHost();
			HoverModel hover = getHost().getViewer().getAdapter(HoverModel.class);
			if (host == hover.getHover()) hover.clearHover();

			try {
				ITransactionalOperation op = new PathsOperation(host, Kind.REMOVE_NODE);
				host.getRoot().getViewer().getDomain().execute(op, null);
			} catch (ExecutionException ex) {
				Log.error(this, ex.getMessage());
			}
		});

		return new ContextMenu(addCallees, addCallers, new SeparatorMenuItem(), removeNode);
	}
}
