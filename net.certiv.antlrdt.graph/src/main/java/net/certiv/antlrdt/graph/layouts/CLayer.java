package net.certiv.antlrdt.graph.layouts;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import org.eclipse.gef.geometry.planar.Dimension;
import org.eclipse.gef.geometry.planar.Point;
import org.eclipse.gef.layout.LayoutProperties;

import com.google.common.collect.Lists;

import net.certiv.antlrdt.graph.layouts.CoffmanGrahamLayoutAlgorithm.Flow;

public class CLayer implements Iterable<CNode> {

	final CGraph graph;
	final Flow style;

	final Point loc = new Point();
	final Dimension size = new Dimension();

	final List<CNode> cnodes = Lists.newArrayList();

	public CLayer(CGraph graph, Flow style) {
		this.graph = graph;
		this.style = style;
	}

	public void add(CNode cnode) {
		cnodes.add(cnode);
		Dimension nsize = LayoutProperties.getSize(cnode.node);
		switch (style) {
			case LEFT_RIGHT:
				size.width = Math.max(size.width, nsize.width);
				size.height += nsize.height + graph.sSiblings;
				break;

			case TOP_BOTTOM:
				size.width += nsize.width + graph.sDepth;
				size.height = Math.max(size.height, nsize.height);
				break;
		}
	}

	@Override
	public Iterator<CNode> iterator() {
		return cnodes.iterator();
	}

	public double getSpread() {
		switch (style) {
			default:
			case LEFT_RIGHT:
				return size.height;

			case TOP_BOTTOM:
				return size.width;
		}
	}

	public Point setDepth(Point loc) {
		cnodes.sort(Collections.reverseOrder());
		double space = graph.sSiblings / 2;
		switch (style) {
			case LEFT_RIGHT:
				this.loc.x = loc.x;
				this.loc.y = loc.y - size.height / 2;
				loc.x += size.width + graph.sDepth;

				double incY = this.loc.y;
				for (CNode cnode : cnodes) {
					incY += space;
					cnode.loc.x = this.loc.x;
					cnode.loc.y = incY;
					Dimension nsize = LayoutProperties.getSize(cnode.node);
					incY += nsize.height + space;
				}
				break;

			case TOP_BOTTOM:
				this.loc.x = loc.x - size.getWidth() / 2;
				this.loc.y = loc.y;
				loc.y += size.height + graph.sDepth;

				double incX = this.loc.x;
				for (CNode cnode : cnodes) {
					incX += space;
					cnode.loc.x = incX;
					cnode.loc.y = this.loc.y;
					Dimension nsize = LayoutProperties.getSize(cnode.node);
					incX += nsize.width + space;
				}
				break;
		}
		return loc;
	}

	@Override
	public String toString() {
		return String.format("CLayer %s @%s:%s [%s:%s] '%s'", style, (int) loc.x, (int) loc.y, (int) size.width,
				(int) size.height, cnodes);
	}
}
