package net.certiv.antlrdt.graph.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.mvc.fx.ui.actions.AbstractViewerActionGroup;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

import net.certiv.antlrdt.graph.view.GraphFXView;

public class LayoutActionGroup extends AbstractViewerActionGroup {

	private GraphFXView view;

	public LayoutActionGroup(GraphFXView graphFXView) {
		super();
		this.view = graphFXView;
	}

	@Override
	public List<IAdaptable.Bound<IViewer>> createContributions() {
		List<IAdaptable.Bound<IViewer>> dependents = new ArrayList<>();
		dependents.add(new SnapshotContributionItem());
		dependents.add(new LayoutContributionItem(view));
		dependents.add(new RouterContributionItem(view));
		return dependents;
	}
}
