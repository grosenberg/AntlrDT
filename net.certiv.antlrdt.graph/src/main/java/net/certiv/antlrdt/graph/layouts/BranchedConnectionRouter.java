package net.certiv.antlrdt.graph.layouts;

import java.util.List;

import org.eclipse.gef.fx.nodes.AbstractRouter;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.euclidean.Vector;
import org.eclipse.gef.geometry.planar.IGeometry;
import org.eclipse.gef.geometry.planar.Line;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.geometry.planar.Rectangle;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.graph.shapes.EdgeShape;
import net.certiv.dsl.core.preferences.DslPrefsManager;

//
// +-----------+
// |           |
// |           +---+\
// |           |     \         +--------------+
// +-----------+      \        |              |
//                     \+----->+              |
//                             |              |
//                             +--------------+
//
public class BranchedConnectionRouter extends AbstractRouter {

	private Branched style = Branched.LEFT_RIGHT;

	public enum Branched {
		TOP_BOTTOM,
		LEFT_RIGHT,
		MID_POINTS,
		NEAREST;
	}

	enum Side {
		TOP,
		RIGHT,
		BOTTOM,
		LEFT;
	}

	// private final Table<Connection, Integer, Polygon[]> cache = HashBasedTable.create();
	private int begOffset = 16;
	private int endOffset = 32;
	private int sibOffset = 32;

	public BranchedConnectionRouter() {
		super();
		DslPrefsManager prefsMgr = AntlrDTCore.getDefault().getPrefsManager();
		begOffset = prefsMgr.getInt(PrefsKey.PT_SOURCE_LEAD);
		endOffset = prefsMgr.getInt(PrefsKey.PT_TARGET_LEAD);
		sibOffset = prefsMgr.getInt(PrefsKey.PT_SIBLING_SPACING);
	}

	public BranchedConnectionRouter(Branched style) {
		this();
		this.style = style;
	}

	public void setStyle(Branched style) {
		this.style = style;
	}

	@Override
	protected EdgeShape getConnection() {
		return (EdgeShape) super.getConnection();
	}

	private static final int OUT = 1;
	private static final int IN = -1;

	@Override
	protected Point getAnchoredReferencePoint(List<Point> points, int index) {
		if (index < 0 || index >= points.size()) throw new IndexOutOfBoundsException();

		IGeometry source = getAnchorageGeometry(index);
		int dir = index < points.size() - 1 ? OUT : IN;
		int tgtIndex = findReferenceIndex(points, index, source, dir);
		IGeometry target = getAnchorageGeometry(tgtIndex);

		if (target == null || source == null) return points.get(tgtIndex);

		// find opposite reference index
		int anchorIndex = index == 0 ? points.size() - 1 : 0;
		int step = index < points.size() - 1 ? IN : OUT;
		int oppositeReferenceIndex = findReferenceIndex(points, anchorIndex, source, step);

		if (getAnchorageGeometry(oppositeReferenceIndex) == null) {
			return points.get(oppositeReferenceIndex);
		}

		return getReferencePoint(source.getBounds(), target.getBounds(), dir);
	}

	private Point getReferencePoint(Rectangle source, Rectangle target, int dir) {
		switch (style) {
			case LEFT_RIGHT:
			default:
				return dir == OUT ? source.getRight() : source.getLeft();

			case TOP_BOTTOM:
				return dir == OUT ? source.getBottom() : source.getTop();

			case MID_POINTS:
				Point midPoint = null;
				double nearest = 0;
				for (Line ln : source.getOutlineSegments()) {
					Point midpoint = ln.split(0.5)[0].getP2(); // line mid-point
					double dist = target.getCenter().getDistance(midpoint);
					if (midPoint == null || dist < nearest) {
						nearest = dist;
						midPoint = midpoint;
					}
				}
				return midPoint;

			case NEAREST:
				Point nearPoint = null;
				Point center = target.getCenter();
				double nearestDistance = 0;
				for (Line l : source.getOutlineSegments()) {
					Point projection = l.getProjection(center);
					double distance = center.getDistance(projection);
					if (nearPoint == null || distance < nearestDistance) {
						nearestDistance = distance;
						nearPoint = projection;
					}
				}
				return nearPoint;
		}
	}

	/**
	 * Iterates the connection's points starting at the first candidate index ( <i>anchorIndex</i> +
	 * <i>step</i>) and stepping by the given step. Returns the index of the first point that is not
	 * contained within the given anchorage geometry. If all points are contained within the given
	 * anchorage geometry, the first reference candidate (i.e. <i>anchorIndex</i> + <i>step</i>) is
	 * returned.
	 *
	 * @param connection The connection.
	 * @param anchorIndex The start index within the connection's points.
	 * @param anchorageGeometry The anchorage geometry.
	 * @param step The step that is used to iterate the connection's points.
	 * @return The index of the first point that is not contained within the anchorage geometry.
	 */
	private int findReferenceIndex(List<Point> points, int anchorIndex, IGeometry anchorageGeometry, int step) {
		int startIndex = anchorIndex + step;
		for (int idx = startIndex; step < 0 ? idx >= 0 : idx < points.size(); idx += step) {
			Point point = points.get(idx);
			if (!anchorageGeometry.contains(point)) return idx;
		}
		return startIndex;
	}

	@Override
	protected Vector route(ControlPointManipulator cpm, Vector inDirection, Vector outDirection) {
		if (Math.abs(outDirection.x) <= 0.05 && Math.abs(outDirection.y) <= 0.05) {
			return inDirection;	// effectively touching
		}
		if (isHorizontal(outDirection) || isVertical(outDirection)) {
			return super.route(cpm, inDirection, outDirection);	// effectively straight
		}
		return routeSegment(cpm.getConnection(), cpm, inDirection, outDirection, cpm.getIndex(), cpm.getPoint());
	}

	protected Vector routeSegment(Connection conn, ControlPointManipulator cpm, Vector in, Vector out, int idx,
			Point currentPoint) {

		cpm.setRoutingData(idx + 1, currentPoint, out);

		if (Math.hypot(out.x, out.y) > begOffset + endOffset) {
			cpm.addRoutingPoints(idx + 1, currentPoint, points(out));
		}

		return out;
	}

	private double[] points(Vector vec) {
		double lead = begOffset + endOffset;

		if (style == Branched.LEFT_RIGHT) {
			if (vec.x > lead) {
				// target fully follows source: simple 2 bend edge
				return new double[] { //
						begOffset, 0, //
						vec.x - endOffset, vec.y //
				};
			}

			// (vec.x <= lead) : target close to or leads source: loop edge around
			double yOffset = sibOffset * 0.75;
			if (vec.y < 0) yOffset *= -1;

			if (Math.abs(vec.y) < sibOffset * 2) {
				// nodes are close: loop edge around both nodes
				return new double[] { //
						begOffset, 0, //
						begOffset, -yOffset,  //
						vec.x / 2, -yOffset, //
						vec.x - endOffset, vec.y - yOffset, //
						vec.x - endOffset, vec.y //
				};
			}

			// sufficiently distant to loop edge between nodes
			return new double[] { //
					begOffset, 0, //
					begOffset, yOffset,  //
					vec.x - endOffset, vec.y - yOffset, //
					vec.x - endOffset, vec.y //
			};
		}

		// TOP_BOTTOM
		if (vec.y > lead) {
			// target fully follows source: simple 2 bend edge
			return new double[] { //
					0, begOffset, //
					vec.x, vec.y - endOffset //
			};
		}

		// (vec.x <= lead) : target close to or leads source: loop edge around
		double yOffset = sibOffset * 0.75;
		if (vec.y < 0) yOffset *= -1;

		if (Math.abs(vec.y) < sibOffset * 2) {
			// nodes are close: loop edge around both nodes
			return new double[] { //
					0, begOffset, //
					-yOffset, begOffset, //
					-yOffset, vec.y / 2, //
					vec.x - yOffset, vec.y - endOffset, //
					vec.x, vec.y - endOffset //
			};
		}

		// sufficiently distant to loop edge between nodes
		return new double[] { //
				0, begOffset, //
				yOffset, begOffset, //
				vec.x - yOffset, vec.y - endOffset, //
				vec.x, vec.y - endOffset //
		};
	}

	private boolean isHorizontal(Vector direction) {
		return Math.abs(direction.y) < 0.5 && Math.abs(direction.x) > Math.abs(direction.y);
	}

	private boolean isVertical(Vector direction) {
		return Math.abs(direction.y) > Math.abs(direction.x) && Math.abs(direction.x) < 0.5;
	}
}
