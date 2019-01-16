package net.certiv.antlrdt.graph.parts;

import java.util.Collections;
import java.util.List;

import org.eclipse.gef.fx.anchors.IAnchor;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.mvc.fx.parts.AbstractContentPart;
import org.eclipse.gef.mvc.fx.parts.IBendableContentPart;
import org.eclipse.gef.mvc.fx.parts.IVisualPart;
import org.eclipse.gef.mvc.fx.providers.IAnchorProvider;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.SetMultimap;

import net.certiv.antlrdt.graph.models.EdgeModel;
import net.certiv.antlrdt.graph.models.NodeModel;
import net.certiv.antlrdt.graph.shapes.EdgeShape;

public class TreeEdgePart extends AbstractContentPart<EdgeShape> implements IBendableContentPart<EdgeShape> {

	public static final String SOURCE_ROLE = "SOURCE";
	public static final String TARGET_ROLE = "TARGET";

	private List<BendPoint> bendPoints = Lists.newArrayList();

	protected TreeEdgePart() {
		super();
	}

	@Override
	public EdgeModel getContent() {
		return (EdgeModel) super.getContent();
	}

	@Override
	public List<BendPoint> getContentBendPoints() {
		return bendPoints;
	}

	@Override
	public void setContentBendPoints(List<BendPoint> bendPoints) {
		this.bendPoints = bendPoints != null ? bendPoints : Lists.newArrayList();
	}

	@Override
	protected EdgeShape doCreateVisual() {
		return new EdgeShape();
	}

	@Override
	protected void doRefreshVisual(EdgeShape visual) {
		visual.setRouter(getContent().getRouter());
	}

	@Override
	protected void doAttachToAnchorageVisual(IVisualPart<?> anchorage, String role) {
		IAnchorProvider adaptor = anchorage.getAdapter(IAnchorProvider.class);
		if (adaptor != null) {
			IAnchor anchor = adaptor.get(this, role);
			if (anchor != null) {
				if (SOURCE_ROLE.equals(role)) {
					getVisual().setStartAnchor(anchor);
				} else if (TARGET_ROLE.equals(role)) {
					getVisual().setEndAnchor(anchor);
				}
				return;
			}
		}

		throw new IllegalArgumentException("Cannot attach to anchor with role <" + role + ">.");
	}

	@Override
	protected void doDetachFromAnchorageVisual(IVisualPart<?> anchorage, String role) {
		Connection connection = getVisual();
		if (role.equals(SOURCE_ROLE)) {
			Point startPoint = connection.getStartPoint();
			connection.setStartPoint(startPoint == null ? new Point() : startPoint);
		} else if (role.equals(TARGET_ROLE)) {
			Point endPoint = connection.getEndPoint();
			connection.setEndPoint(endPoint == null ? new Point() : endPoint);
		} else {
			throw new IllegalArgumentException("Cannot detach from anchor with role <" + role + ">.");
		}
	}

	@Override
	protected void doAttachToContentAnchorage(Object anchorage, String role) {
		if (SOURCE_ROLE.equals(role)) {
			getContent().setSource((NodeModel) anchorage);
		} else if (TARGET_ROLE.equals(role)) {
			getContent().setTarget((NodeModel) anchorage);
		} else {
			throw new IllegalArgumentException("Cannot attach to content anchorage with role <" + role + ">.");
		}
	}

	@Override
	protected void doDetachFromContentAnchorage(Object contentAnchorage, String role) {
		if (SOURCE_ROLE.equals(role)) {
			getContent().setSource(null);
		} else if (TARGET_ROLE.equals(role)) {
			getContent().setTarget(null);
		} else {
			throw new IllegalArgumentException("Cannot detach from content anchorage with role <" + role + ">.");
		}
	}

	@Override
	protected SetMultimap<? extends Object, String> doGetContentAnchorages() {
		SetMultimap<Object, String> anchorages = HashMultimap.create();
		anchorages.put(getContent().getSource(), SOURCE_ROLE);
		anchorages.put(getContent().getTarget(), TARGET_ROLE);
		return anchorages;
	}

	@Override
	protected List<? extends Object> doGetContentChildren() {
		return Collections.emptyList();
	}
}
