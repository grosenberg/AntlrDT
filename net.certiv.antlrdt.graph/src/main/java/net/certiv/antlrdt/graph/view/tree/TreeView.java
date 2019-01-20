package net.certiv.antlrdt.graph.view.tree;

import java.util.Collections;

import javafx.application.Platform;

import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.widgets.Composite;

import com.google.inject.Guice;

import net.certiv.antlrdt.graph.GraphUI;
import net.certiv.antlrdt.graph.IEvents;
import net.certiv.antlrdt.graph.actions.Layout;
import net.certiv.antlrdt.graph.actions.Router;
import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.parse.TargetBuilder;
import net.certiv.antlrdt.graph.view.GraphFXView;

public class TreeView extends GraphFXView {

	public static final String VIEW_ID = "net.certiv.antlrdt.graph.view.tree.TreeView";

	public static final String StylesCss = TreeView.class.getResource("/css/styles.css").toExternalForm(); //$NON-NLS-1$

	private TargetBuilder builder;
	private DiagramModel diagModel;

	public TreeView() {
		super(Guice.createInjector(new TreeViewModule()));
	}

	@Override
	public void createPartControl(final Composite parent) {
		super.createPartControl(parent);
		getContentViewer().getCanvas().getStylesheets().add(StylesCss);
	}

	/** Refreshes the constructed graph */
	@Override
	public void refresh() {
		GraphUI.getDefault().getEventManager().postEvent(IEvents.TOPIC_REFRESH, true);
	}

	/** Refreshes the constructed graph using the graph model from the given builder. */
	public void refresh(TargetBuilder builder) {
		this.builder = builder;
		refresh(true);
	}

	/**
	 * Refreshes the constructed graph. If {@code rebuild} is set, the graph model fetched from the
	 * existing parse-tree model.
	 */
	public void refresh(boolean rebuild) {
		if (rebuild || diagModel == null) {
			// if (diagModel != null) diagModel.dispose();
			if (builder != null && builder.isValid()) {
				diagModel = builder.getModel();
			}
		}

		IDialogSettings ds = GraphUI.getSettings();
		diagModel.setLayout(Layout.getEnum(ds.get(NODE_LAYOUT)));
		diagModel.setRouter(Router.getEnum(ds.get(EDGE_ROUTER)));
		setGraph(diagModel);
	}

	public void setGraph(DiagramModel model) {
		InfiniteCanvasViewer viewer = (InfiniteCanvasViewer) getContentViewer();
		if (viewer == null) throw new IllegalStateException("Invalid content viewer.");

		viewer.getContents().setAll(Collections.singleton(model));
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				model.doLayout();
				resetViewport();
			}
		});
	}

	@Override
	public void dispose() {
		super.dispose();
	}
}
