/*******************************************************************************
 * Copyright (c) 2000, 2009 IBM Corporation and others. All rights reserved. This program and the
 * accompanying materials are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html Contributors: IBM Corporation - initial API and
 * implementation Certiv Analytics - enhancements
 *******************************************************************************/
package net.certiv.antlrdt.ui.graph;

import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.PopUpHelper;
import org.eclipse.draw2d.ToolTipHelper;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.events.MouseTrackAdapter;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.preferences.PrefsKey;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.graph.figures.InfoLabel;
import net.certiv.dsl.core.preferences.IDslPrefsManager;

/**
 * This class is used by EnhSWTEventDispatcher as support to display Figure tooltips on a mouse
 * hover event. Tooltips are drawn directly below the cursor unless the display does not allow, in
 * which case the tooltip will be drawn directly above the cursor. Tooltips will be displayed with a
 * LineBorder. The background of the tooltips will be the standard SWT tooltipBackground color
 * unless the Figure's tooltip has set its own background.
 */
public class EnhTipHelper extends ToolTipHelper /* PopUpHelper */ {

	private Timer timer;
	private IFigure currentTipSource;

	private MouseAdapter2d nodeWatcher;

	/**
	 * Constructs a EnhTipHelper to be associated with Control <i>c</i>.
	 * 
	 * @param c the control
	 * @since 2.0
	 */
	public EnhTipHelper(Control c) {
		super(c/* , SWT.TOOL | SWT.ON_TOP */);
		getShell().setBackground(ColorConstants.tooltipBackground);
		getShell().setForeground(ColorConstants.tooltipForeground);
	}

	/**
	 * Sets the LightWeightSystem's contents to the passed tooltip, and displays the tip. The tip
	 * will be displayed only if the tip source is different than the previously viewed tip source.
	 * (i.e. The cursor has moved off of the previous tooltip source figure.)
	 * <p>
	 * The tooltip will be painted directly below the cursor if possible, otherwise it will be
	 * painted directly above cursor.
	 * 
	 * @param hoverSource the figure over which the hover event was fired
	 * @param tip the tooltip to be displayed
	 * @param eventX the x coordinate of the hover event
	 * @param eventY the y coordinate of the hover event
	 * @since 2.0
	 */
	@Override
	public void displayToolTipNear(IFigure hoverSource, IFigure tip, int eventX, int eventY) {
		boolean advancedMode = getPrefs().matches(PrefsKey.PT_TOOLTIP_TYPE, PrefsKey.PT_TTT_ENH);
		if (tip != null && (advancedMode || hoverSource != currentTipSource)) {
			getLightweightSystem().setContents(tip);
			currentTipSource = hoverSource;
			Point displayPoint = computeWindowLocation(tip, eventX, eventY);
			Dimension shellSize = getLightweightSystem().getRootFigure().getPreferredSize()
					.getExpanded(getShellTrimSize());
			setShellBounds(displayPoint.x, displayPoint.y, shellSize.width, shellSize.height);
			show();

			if (advancedMode) {
				if (nodeWatcher == null) nodeWatcher = new NodeWatcher();
				currentTipSource.addMouseListener(nodeWatcher);
				// currentTipSource.addMouseMotionListener(nodeWatcher);
			} else {
				timer = new Timer(true);
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						Display.getDefault().syncExec(new Runnable() {
							@Override
							public void run() {
								hide();
								timer.cancel();
							}
						});
					}
				}, getPrefs().getInt(PrefsKey.PT_TIP_DURATION));
			}
		}
	}

	/*
	 * Calculates the location where the tooltip will be painted. Returns this as a Point. Tooltip
	 * will be painted directly below the cursor if possible, otherwise it will be painted directly
	 * above cursor. Note: watch for swt/draw2d Point confusion
	 */
	private Point computeWindowLocation(IFigure tip, int eventX, int eventY) {
		int enhGapX = getPrefs().getInt(PrefsKey.PT_GAP_ENH_X);
		int enhGapY = getPrefs().getInt(PrefsKey.PT_GAP_ENH_Y);
		int floatGap = getPrefs().getInt(PrefsKey.PT_GAP_FLOAT);

		// set default tip location
		Point preferredLocation = new Point(eventX, eventY + floatGap);

		Rectangle r = new Rectangle(currentTipSource.getBounds());
		if (getPrefs().matches(PrefsKey.PT_ENH_POSITION, PrefsKey.PT_ENH_ALIGNED)) {
			// adjust for zoomed size of the GraphNode
			DslGraphViewer viewer = (DslGraphViewer) AntlrDTUI.getDefault().getCstEditor().getZoomableViewer();
			double scale = viewer.getGraphControl().getRootLayer().getScale();
			r.scale(scale);
			// adjust for location of viewport
			Point vLoc = viewer.getGraphControl().getViewport().getViewLocation();
			r.translate(vLoc.negate());
			// update tip location
			Point offset = new Point(r.x + enhGapX, r.y + r.height + enhGapY);
			preferredLocation = new Point(control.toDisplay(offset.getSWTPoint()));
		}

		// Adjust location if tip is going to fall outside display
		org.eclipse.swt.graphics.Rectangle clientArea = control.getDisplay().getClientArea();
		Dimension tipSize = getLightweightSystem().getRootFigure().getPreferredSize().getExpanded(getShellTrimSize());
		if (preferredLocation.y + tipSize.height > clientArea.height) {
			if (getPrefs().matches(PrefsKey.PT_ENH_POSITION, PrefsKey.PT_ENH_ALIGNED)) {
				Point offset = new Point(r.x, r.y - (tipSize.height + enhGapY));
				preferredLocation.y = control.toDisplay(offset.getSWTPoint()).y;
			} else {
				preferredLocation.y = eventY - tipSize.height - floatGap;
			}
		}
		if (preferredLocation.x + tipSize.width > clientArea.width) {
			preferredLocation.x -= (preferredLocation.x + tipSize.width) - clientArea.width;
		}
		return preferredLocation;
	}

	/**
	 * Disposes of the tooltip's shell and kills the timer.
	 * 
	 * @see PopUpHelper#dispose()
	 */
	@Override
	public void dispose() {
		if (isShowing()) {
			timer.cancel();
			hide();
		}
		getShell().dispose();
	}

	/**
	 * @see PopUpHelper#hookShellListeners()
	 */
	@Override
	protected void hookShellListeners() {
		// Close the tooltip window if the mouse enters the tooltip
		getShell().addMouseTrackListener(new MouseTrackAdapter() {
			@Override
			public void mouseEnter(org.eclipse.swt.events.MouseEvent e) {
				boolean advancedMode = getPrefs().matches(PrefsKey.PT_TOOLTIP_TYPE, PrefsKey.PT_TTT_ENH);
				if (!advancedMode && isShowing()) {
					hide();
					currentTipSource = null;
					timer.cancel();
				}
			}
		});
	}

	/**
	 * Displays the hover source's tooltip if a tooltip of another source is currently being
	 * displayed.
	 * 
	 * @param figureUnderMouse the figure over which the cursor was when called
	 * @param tip the tooltip to be displayed
	 * @param eventX the x coordinate of the cursor
	 * @param eventY the y coordinate of the cursor
	 * @since 2.0
	 */
	@Override
	public void updateToolTip(IFigure figureUnderMouse, IFigure tip, int eventX, int eventY) {
		/*
		 * If the cursor is not on any Figures, it has been moved off of the source control. Hide
		 * the tool tip.
		 */
		boolean advancedMode = getPrefs().matches(PrefsKey.PT_TOOLTIP_TYPE, PrefsKey.PT_TTT_ENH);
		if (advancedMode && isShowing()) {
			hoverEnded();
		} else {
			if (figureUnderMouse == null) {
				if (isShowing()) {
					hide();
					timer.cancel();
				}
			}
			// Makes tooltip appear without a hover event if a tip is currently being
			// displayed
			if (isShowing() && figureUnderMouse != currentTipSource) {
				hide();
				timer.cancel();
				displayToolTipNear(figureUnderMouse, tip, eventX, eventY);
			} else {
				if (!isShowing() && figureUnderMouse != currentTipSource) currentTipSource = null;
			}
		}
	}

	/**
	 * Starts the interval timer to delay killing the InfoLabel until the mouse (possibly) moves
	 * over the InfoLabel. If the kill timer ends normally, then the InfoLabel is closed.
	 * 
	 * @param me
	 */
	public void hoverEnded() {
		if (timer != null) timer.cancel();
		timer = new Timer(true);
		timer.schedule(new KillTask(), getPrefs().getInt(PrefsKey.PT_KILL_DELAY));
	}

	private IDslPrefsManager getPrefs() {
		return AntlrDTCore.getDefault().getPrefsManager();
	}

	private class KillTask extends TimerTask {
		@Override
		public void run() {
			Display.getDefault().syncExec(new Runnable() {
				@Override
				public void run() {
					if (isShowing()) {
						hide();
						timer.cancel();
					}
				}
			});
		}
	}

	public void mouseEntered(MouseEvent me, InfoLabel self) {
		timer.cancel();
	}

	public void mouseExited(MouseEvent me, InfoLabel self) {
		if (isShowing()) hide();
	}

	private class NodeWatcher extends MouseAdapter2d {

		@Override
		public void mousePressed(MouseEvent me) {
			if (currentTipSource != null) {
				// Log.debug(this, "Pressed");
				if (isShowing()) hide();
			}
		}
	}
}
