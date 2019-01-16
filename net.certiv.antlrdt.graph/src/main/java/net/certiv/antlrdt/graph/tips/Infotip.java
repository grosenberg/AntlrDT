package net.certiv.antlrdt.graph.tips;

import javafx.scene.control.Skin;
import javafx.scene.control.Tooltip;

import net.certiv.antlrdt.graph.models.NodeModel;

public class Infotip extends Tooltip {

	// ccs classes
	public static final String TIP_CLASS = "infotip";
	public static final String TIP_TITLE = "infotip-title";
	public static final String TIP_KEY = "infotip-key";
	public static final String TIP_VALUE = "infotip-value";

	private NodeModel model;

	public Infotip(NodeModel model) {
		super();
		this.model = model;
	}

	@Override
	protected Skin<?> createDefaultSkin() {
		return new InfotipSkin(new InfotipPane(this));
	}

	public NodeModel getModel() {
		return model;
	}
}
