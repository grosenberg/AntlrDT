package net.certiv.antlrdt.graph.actions;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.common.adapt.IAdaptable;
import org.eclipse.gef.mvc.fx.ui.actions.AbstractViewerActionGroup;
import org.eclipse.gef.mvc.fx.viewer.IViewer;

public class GraphActionGroup extends AbstractViewerActionGroup {

	public GraphActionGroup() {
		super();
	}

	@Override
	public List<IAdaptable.Bound<IViewer>> createContributions() {
		List<IAdaptable.Bound<IViewer>> dependents = new ArrayList<>();
		dependents.add(new SnapshotContributionItem());
		dependents.add(new LayoutContributionItem());
		dependents.add(new RouterContributionItem());
		return dependents;
	}
}
