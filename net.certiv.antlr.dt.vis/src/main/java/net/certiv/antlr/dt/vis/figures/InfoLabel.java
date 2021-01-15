/*******************************************************************************
 * Copyright 2009-2020, Gerald B. Rosenberg, Certiv Analytics.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package net.certiv.antlr.dt.vis.figures;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;

import net.certiv.dsl.core.util.Strings;

public class InfoLabel extends Label {

	private static final String DATA_LABEL = "Signature:" + Strings.EOL + "Source:" + Strings.EOL + "Location:";

	private InfoLabel self;
	private EnhTipHelper helper;
	private Label titleBar;

	public InfoLabel(EnhTipHelper helper, Image image, String name, String content) {
		super();
		this.self = this;
		this.helper = helper;

		setBackgroundColor(ColorConstants.tooltipBackground);
		setForegroundColor(ColorConstants.tooltipForeground);

		setBorder(new MarginBorder(3));
		setLayoutManager(new BorderLayout());
		add(createTitleBar(image, name), BorderLayout.TOP);
		add(createDataBlock(content), BorderLayout.CENTER);
		addMouseMotionListener(new MouseTracker());
	}

	private IFigure createTitleBar(Image image, String name) {
		titleBar = new Label();
		titleBar.setLayoutManager(new BorderLayout());
		titleBar.setLabelAlignment(PositionConstants.LEFT);
		titleBar.setFont(createBoldFont(titleBar.getFont()));
		titleBar.setBorder(new MarginBorder(2));
		titleBar.setIcon(image);
		titleBar.setText(name);
		return titleBar;
	}

	private Font createBoldFont(Font font) {
		Font f = (font != null) ? font : Display.getDefault().getSystemFont();
		FontData fontData = f.getFontData()[0];
		fontData.setStyle(SWT.BOLD);
		return new Font(Display.getCurrent(), fontData);
	}

	private IFigure createDataBlock(String content) {
		Label dataBlock = new Label();
		dataBlock.setLayoutManager(new BorderLayout());
		dataBlock.add(createLabelBlock(), BorderLayout.LEFT);
		dataBlock.add(createContentBlock(content), BorderLayout.CENTER);
		return dataBlock;
	}

	private IFigure createLabelBlock() {
		Label labelBlock = new Label();
		labelBlock.setLayoutManager(new BorderLayout());
		labelBlock.setLabelAlignment(PositionConstants.LEFT);
		labelBlock.setBorder(new MarginBorder(3));
		labelBlock.setText(DATA_LABEL);
		return labelBlock;
	}

	private IFigure createContentBlock(String content) {
		Label contentBlock = new Label();
		contentBlock.setLayoutManager(new BorderLayout());
		contentBlock.setLabelAlignment(PositionConstants.LEFT);
		contentBlock.setBorder(new MarginBorder(3));
		contentBlock.setText(content);
		return contentBlock;
	}

	@Override
	public void paint(Graphics g) {
		g.setBackgroundColor((ColorConstants.tooltipBackground));
		g.fillRectangle(bounds);
		Rectangle tb = titleBar.getBounds();
		g.drawLine(tb.x, tb.y + tb.height, tb.x + tb.width - 1, tb.y + tb.height);
		super.paint(g);
	}

	protected class MouseTracker extends MouseMotionListener.Stub {

		@Override
		public void mouseEntered(MouseEvent me) {
			helper.mouseEntered(me, self);
		}

		@Override
		public void mouseExited(MouseEvent me) {
			helper.mouseExited(me, self);
		}
	}
}
