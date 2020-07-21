/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.vis.layouts;

import org.eclipse.draw2d.AbstractRouter;
import org.eclipse.draw2d.Connection;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.dsl.core.preferences.IPrefsManager;

public class BranchedConnectionRouter extends AbstractRouter {

	// TODO: need to calculate the vertical distance offset value
	private int vDst = 20;
	private int begOffset = 16;
	private int endOffset = 24;

	public BranchedConnectionRouter() {
		super();
		init();
	}

	private void init() {
		begOffset = getPrefs().getInt(PrefsKey.PT_SOURCE_LEAD);
		endOffset = getPrefs().getInt(PrefsKey.PT_TARGET_LEAD);
	}

	private IPrefsManager getPrefs() {
		return AntlrCore.getDefault().getPrefsManager();
	}

	@Override
	public void route(Connection conn) {
		PointList points = conn.getPoints();
		points.removeAllPoints();

		Label ls = (Label) conn.getSourceAnchor().getOwner();
		Label lt = (Label) conn.getTargetAnchor().getOwner();

		Point beg0Point = getBegPoint(conn, ls);
		Point end0Point = getEndPoint(conn, lt);

		conn.translateToRelative(beg0Point);
		conn.translateToRelative(end0Point);

		Point beg1Point = new Point(beg0Point.x + begOffset, beg0Point.y);
		Point end1Point = new Point(end0Point.x - endOffset, end0Point.y);

		points.addPoint(beg0Point);
		points.addPoint(beg1Point);

		calcRouteBetween(conn, points, ls, beg1Point, end1Point);

		points.addPoint(end1Point);
		points.addPoint(end0Point);
		conn.setPoints(points);
	}

	protected Point getBegPoint(Connection conn, Label ls) {
		Point ref = conn.getSourceAnchor().getReferencePoint();
		ref.x += ls.getBounds().width;
		return new Point(conn.getSourceAnchor().getLocation(ref));
	}

	protected Point getEndPoint(Connection connection, Label lt) {
		Point ref = connection.getTargetAnchor().getReferencePoint();
		ref.x -= lt.getBounds().width;
		return new Point(connection.getTargetAnchor().getLocation(ref));
	}

	protected void calcRouteBetween(Connection conn, PointList points, Label ls, Point beg1Point, Point end1Point) {
		if (beg1Point.x <= end1Point.x) return;

		int height = ls.getBounds().height;
		int width = ls.getBounds().width;

		Point beg2Point = new Point(beg1Point.x, beg1Point.y);
		Point end2Point = new Point(end1Point.x, end1Point.y);

		if (beg1Point.y + vDst < end1Point.y - vDst) {
			beg2Point.y += vDst;
			end2Point.y -= vDst;
		} else if (beg1Point.y + height < end1Point.y) {
			int midY = (end1Point.y - beg1Point.y) / 2;
			beg2Point.y += midY;
			end2Point.y -= midY;
		} else if (beg1Point.y <= end1Point.y) {
			end2Point.y += vDst;
			beg2Point.y = end2Point.y;
		} else if (beg1Point.y - vDst > end1Point.y + vDst) {
			beg2Point.y -= vDst;
			end2Point.y += vDst;
		} else if (beg1Point.y - height > end1Point.y) {
			int midY = (beg1Point.y - end1Point.y) / 2;
			beg2Point.y -= midY;
			end2Point.y += midY;
		} else if (beg1Point.y > end1Point.y) {
			end2Point.y -= vDst;
			beg2Point.y = end2Point.y;
		}

		int stopX = beg1Point.x - begOffset - width;
		int midX = (beg1Point.x - end1Point.x) / 2;
		Point beg3Point = new Point(beg2Point.x - midX, beg2Point.y);

		if (beg2Point.x - midX < stopX) {
			beg3Point.x = stopX;
		}

		points.addPoint(beg2Point);
		points.addPoint(beg3Point);
		points.addPoint(end2Point);
	}
}
