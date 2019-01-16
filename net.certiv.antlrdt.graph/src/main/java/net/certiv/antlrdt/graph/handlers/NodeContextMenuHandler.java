package net.certiv.antlrdt.graph.handlers;

import java.util.ArrayList;

import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import org.eclipse.gef.mvc.fx.handlers.AbstractHandler;
import org.eclipse.gef.mvc.fx.handlers.IOnClickHandler;
import org.eclipse.gef.mvc.fx.models.HoverModel;
import org.eclipse.gef.mvc.fx.parts.IContentPart;
import org.eclipse.gef.mvc.fx.parts.IRootPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.policies.DeletionPolicy;

import net.certiv.antlrdt.graph.parts.TreeEdgePart;

public class NodeContextMenuHandler extends AbstractHandler implements IOnClickHandler {

	@Override
	public void click(MouseEvent event) {
		if (!event.isSecondaryButtonDown()) return;

		MenuItem deleteNodeItem = new MenuItem("Delete Node");
		deleteNodeItem.setOnAction((e) -> {
			HoverModel hover = getHost().getViewer().getAdapter(HoverModel.class);
			if (getHost() == hover.getHover()) hover.clearHover();

			// query DeletionPolicy for the removal of the host part
			IRootPart<? extends Node> root = getHost().getRoot();
			DeletionPolicy policy = root.getAdapter(DeletionPolicy.class);
			init(policy);

			// delete all anchored connection parts
			for (IVisualPart<? extends Node> part : new ArrayList<>(getHost().getAnchoredsUnmodifiable())) {
				if (part instanceof TreeEdgePart) {
					policy.delete((IContentPart<? extends Node>) part);
				}
			}

			// delete the node part
			policy.delete((IContentPart<? extends Node>) getHost());
			// commit(policy);
		});

		Menu colorMenu = createChangeColorMenu();
		// Menu textMenu = createChangeTextsMenu();

		ContextMenu menu = new ContextMenu(colorMenu, deleteNodeItem);
		menu.show((Node) event.getTarget(), event.getScreenX(), event.getScreenY());
	}

	private Menu createChangeColorMenu() {
		Menu colorMenu = new Menu("Change Color");
		Color[] colors = { Color.ALICEBLUE, Color.BURLYWOOD, Color.YELLOW, Color.RED, Color.CHOCOLATE,
				Color.GREENYELLOW, Color.WHITE };
		String[] names = { "ALICEBLUE", "BURLYWOOD", "YELLOW", "RED", "CHOCOLATE", "GREENYELLOW", "WHITE" };

		for (int i = 0; i < colors.length; i++) {
			colorMenu.getItems().add(getColorMenuItem(names[i], colors[i]));
		}
		return colorMenu;
	}

	// private Menu createChangeTextsMenu() {
	// Menu textsMenu = new Menu("Change");
	//
	// TreeNodePart host = (TreeNodePart) getHost();
	//
	// MenuItem titleItem = new MenuItem("Title ...");
	// titleItem.setOnAction((e) -> {
	// try {
	// String newTitle = showDialog(host.getContent().getTitle(), "Enter new Title...");
	// ITransactionalOperation op = new SetTreeNodeTitleOperation(host, newTitle);
	// host.getRoot().getViewer().getDomain().execute(op, null);
	// } catch (ExecutionException e1) {
	// e1.printStackTrace();
	// }
	//
	// });
	//
	// MenuItem descrItem = new MenuItem("Description ...");
	// descrItem.setOnAction((e) -> {
	// try {
	// String newDescription = showDialog(host.getContent().getDescription(), "Enter new
	// Description...");
	// ITransactionalOperation op = new SetTreeNodeDescriptionOperation(host, newDescription);
	// host.getRoot().getViewer().getDomain().execute(op, null);
	// } catch (ExecutionException e1) {
	// e1.printStackTrace();
	// }
	// });
	//
	// textsMenu.getItems().addAll(titleItem, descrItem);
	//
	// return textsMenu;
	// }

	private MenuItem getColorMenuItem(String name, Color color) {
		Rectangle graphic = new Rectangle(20, 20);
		graphic.setFill(color);
		graphic.setStroke(Color.BLACK);
		MenuItem item = new MenuItem(name, graphic);
		// item.setOnAction((e) -> submitColor(color));
		return item;
	}

	// private String showDialog(String defaultValue, String title) {
	// TextInputDialog dialog = new TextInputDialog(defaultValue);
	// dialog.setTitle(title);
	// dialog.setGraphic(null);
	// dialog.setHeaderText("");
	//
	// Optional<String> result = dialog.showAndWait();
	// String entered = defaultValue;
	//
	// if (result.isPresent()) {
	// entered = result.get();
	// }
	// return entered;
	// }

	// private void submitColor(Color color) {
	// if (getHost() instanceof TreeNodePart) {
	// TreeNodePart host = (TreeNodePart) getHost();
	// SetTreeNodeColorOperation op = new SetTreeNodeColorOperation(host, color);
	// try {
	// host.getViewer().getDomain().execute(op, null);
	// } catch (ExecutionException e) {
	// e.printStackTrace();
	// }
	// }
	// }

}
