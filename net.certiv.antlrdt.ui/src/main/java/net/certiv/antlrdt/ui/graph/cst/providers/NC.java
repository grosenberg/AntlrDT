package net.certiv.antlrdt.ui.graph.cst.providers;

import org.eclipse.swt.graphics.RGB;

public enum NC {
	CST_LIGHT_BLUE(216, 228, 248),
	CST_DARK_BLUE(1, 70, 122),
	CST_GREY_BLUE(139, 150, 171),
	CST_LIGHT_BLUE_CYAN(213, 243, 255),
	CST_LIGHT_YELLOW(255, 255, 206),
	CST_GRAY(128, 128, 128),
	CST_LIGHT_GRAY(220, 220, 220),
	CST_BLACK(0, 0, 0),
	CST_RED(255, 0, 0),
	CST_DARK_RED(127, 0, 0),
	CST_ORANGE(255, 196, 0),
	CST_YELLOW(255, 255, 0),
	CST_GREEN(0, 255, 0),
	CST_DARK_GREEN(0, 127, 0),
	CST_LIGHT_GREEN(96, 255, 96),
	CST_CYAN(0, 255, 255),
	CST_BLUE(0, 0, 255),
	CST_WHITE(255, 255, 255),
	CST_EDGE_WEIGHT_00(192, 192, 255),
	CST_EDGE_WEIGHT_01(64, 128, 225),
	CST_EDGE_WEIGHT_02(32, 32, 128),
	CST_EDGE_WEIGHT_03(0, 0, 128),
	CST_EDGE_DEFAULT(64, 64, 128),
	CST_EDGE_HIGHLIGHT(192, 32, 32),
	CST_DISABLED(230, 240, 250),

	;

	private RGB _rgb;

	private NC(int r, int g, int b) {
		_rgb = new RGB(r, g, b);
	}

	public RGB rgb() {
		return _rgb;
	}
}
