package net.certiv.antlrdt.graph.layouts;

import java.util.List;

import javafx.geometry.Bounds;

import org.eclipse.gef.fx.nodes.AbstractRouter;
import org.eclipse.gef.fx.nodes.Connection;
import org.eclipse.gef.geometry.planar.Point;

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

	private int vDst = 20;
	private int begOffset = 16;
	private int endOffset = 24;

	private class Branch {
		Point beg;
		Point bend1;
		Point bend2;
		Point end;

		Branch(Point beg, Point end) {
			this.beg = beg;
			this.end = end;
			bend1 = new Point(beg.x + begOffset, beg.y);
			bend2 = new Point(end.x - endOffset, end.y);
		}
	}

	public BranchedConnectionRouter() {
		super();
	}

	@Override
	protected Point getAnchoredReferencePoint(List<Point> points, int index) {
		if (index < 0 || index >= points.size()) throw new IndexOutOfBoundsException();

		Connection conn = getConnection();
		Branch branch = calcBranchPath(conn);

		// ???

		return null;
	}

	// adjusting bendpoints for appearence
	private Branch calcBranchPath(Connection conn) {
		Branch branch = new Branch(conn.getStartPoint(), conn.getEndPoint());

		Bounds begBounds = conn.getStartAnchor().getAnchorage().getBoundsInLocal();
		Bounds endBounds = conn.getEndAnchor().getAnchorage().getBoundsInLocal();
		double height = (begBounds.getHeight() + endBounds.getHeight()) / 4;

		if (branch.beg.y + vDst < branch.end.y - vDst) {
			branch.bend1.y += vDst;
			branch.bend2.y -= vDst;

		} else if (branch.beg.y + height < branch.end.y) {
			double midY = (branch.end.y - branch.beg.y) / 2;
			branch.bend1.y += midY;
			branch.bend2.y -= midY;

		} else if (branch.beg.y <= branch.end.y) {
			branch.bend2.y += vDst;
			branch.bend1.y = branch.bend2.y;

		} else if (branch.beg.y - vDst > branch.end.y + vDst) {
			branch.bend1.y -= vDst;
			branch.bend2.y += vDst;

		} else if (branch.beg.y - height > branch.end.y) {
			double midY = (branch.beg.y - branch.end.y) / 2;
			branch.bend1.y -= midY;
			branch.bend2.y += midY;

		} else if (branch.beg.y > branch.end.y) {
			branch.bend2.y -= vDst;
			branch.bend1.y = branch.bend2.y;
		}

		return branch;
	}
}
