package net.certiv.antlrdt.graph.view.tree;

import java.util.Collections;

import javafx.application.Platform;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.gef.mvc.fx.viewer.InfiniteCanvasViewer;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.views.contentoutline.ContentOutline;

import com.google.inject.Guice;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.graph.GraphUI;
import net.certiv.antlrdt.graph.actions.Layout;
import net.certiv.antlrdt.graph.actions.Router;
import net.certiv.antlrdt.graph.models.DiagramModel;
import net.certiv.antlrdt.graph.view.GraphFXView;
import net.certiv.antlrdt.graph.view.Kind;
import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.IModuleStmt;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.core.model.Statement;

public class PathsView extends GraphFXView implements ISelectionListener {

	public static final String VIEW_ID = "net.certiv.antlrdt.graph.view.tree.PathsView";

	public static final String StylesCss = PathsView.class.getResource("/css/styles.css").toExternalForm(); //$NON-NLS-1$

	public static final String ADD_CALLERS = "addCallers";
	public static final String ADD_CALLEES = "addCallees";
	public static final String REMOVE_NODE = "removeNode";

	private AntlrDTCore core;
	private PathsProcessor paths;
	private DiagramModel diagModel;

	public PathsView() {
		super(Guice.createInjector(new PathsViewModule()));
		core = AntlrDTCore.getDefault();
	}

	@Override
	public void createPartControl(Composite parent) {
		super.createPartControl(parent);
		getContentViewer().getCanvas().getStylesheets().add(StylesCss);

		// calls #selectionChanged in response to outline (and other) selections
		getSelectionService().addSelectionListener(this);
	}

	/**
	 * Operational entry point: a selection in the outline view designates an element for the generation
	 * of a root to selected element path graph.
	 */
	@Override
	public void selectionChanged(IWorkbenchPart part, ISelection sel) {
		if (part instanceof ContentOutline) {
			if (sel instanceof IStructuredSelection) {
				IStructuredSelection selection = (IStructuredSelection) sel;
				for (Object elem : selection.toList()) {
					if (elem instanceof Statement && !(elem instanceof IModuleStmt)) {
						IStatement statement = (IStatement) elem;
						IResource res = statement.getResource();
						String name = statement.getElementName();
						Log.info(this, "Requesting path build for " + name);
						updateGraph(res, name, Kind.FULL_BUILD);
					}
				}
			}
		}
	}

	/**
	 * Trigger graph update with new input. The {@code kind} parameter specifies whether to (1) generate
	 * and install a complete root to selected element diagModel; or (2) add or remove the sub path
	 * segments of the {@code ruleName} identified path node in the existing diagModel.
	 */
	public void updateGraph(IResource res, String ruleName, Kind kind) {
		if (ruleName != null) {
			switch (kind) {
				case ADD_CALLERS:
					paths.addCallerPaths(ruleName);
					break;

				case ADD_CALLEES:
					paths.addCalleePaths(ruleName);
					break;

				case REMOVE_NODE:
					paths.removeNode(ruleName);
					break;

				case FULL_BUILD:
				default:
					ICodeUnit unit = core.getModelManager().create((IFile) res);
					paths = new PathsProcessor(unit.getParseRecord());
					diagModel = paths.getModel(this, ruleName);
			}
			if (diagModel != null) refresh();
		}
	}

	public PathsProcessor getPathsProcessor() {
		return paths;
	}

	@Override
	public void refresh() {
		IDialogSettings ds = GraphUI.getSettings();
		diagModel.setLayout(Layout.getEnum(ds.get(NODE_LAYOUT)));
		diagModel.setRouter(Router.getEnum(ds.get(EDGE_ROUTER)));
		InfiniteCanvasViewer viewer = (InfiniteCanvasViewer) getContentViewer();
		if (viewer == null) throw new IllegalStateException("Invalid content viewer.");

		viewer.getContents().setAll(Collections.singleton(diagModel));
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				diagModel.doLayout();
				resetViewport();
			}
		});
	}

	private ISelectionService getSelectionService() {
		return getSite().getService(ISelectionService.class);
	}

	@Override
	public void dispose() {
		getSelectionService().removeSelectionListener(this);
		super.dispose();
	}
}
