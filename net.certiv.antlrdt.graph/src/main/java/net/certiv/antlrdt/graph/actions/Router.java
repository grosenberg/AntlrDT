package net.certiv.antlrdt.graph.actions;

import org.eclipse.gef.fx.nodes.AbstractRouter;
import org.eclipse.gef.fx.nodes.OrthogonalRouter;
import org.eclipse.gef.fx.nodes.StraightRouter;
import org.eclipse.jface.resource.ImageDescriptor;

import net.certiv.antlrdt.graph.layouts.BranchedConnectionRouter;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.util.Reflect;
import net.certiv.dsl.core.util.Strings;

public enum Router {

	BRANCHED("Branched", "IMG_LAYOUT_CALL", new BranchedConnectionRouter()),
	FAN("Fan", "IMG_LAYOUT_GRAPHFLOW", new StraightRouter()),
	MANHATTAN("Orthogonal", "IMG_LAYOUT_TREE_ORTHO", new OrthogonalRouter());

	private String routerName;
	private String imageKey;
	private AbstractRouter algorithm;

	Router(String name, String key, AbstractRouter router) {
		routerName = name;
		imageKey = key;
		algorithm = router;
	}

	public String getDisplayName() {
		return routerName;
	}

	public ImageDescriptor getImageDescriptor() {
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		Object key = Reflect.get(imgMgr, imageKey, true);
		if (key == null) return null;
		return imgMgr.getDescriptor((String) key);
	}

	public String getImageUrlname() {
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		Object value = Reflect.get(imgMgr, imageKey, true);
		if (value == null) return Strings.EMPTY_STRING;
		return imgMgr.getUrl((String) value).toExternalForm();
	}

	public AbstractRouter getRouter() {
		return algorithm;
	}

	public static Router getEnum(String name) {
		if (name != null) {
			for (Router value : values()) {
				if (value.getDisplayName().toLowerCase().equals(name.toLowerCase())) return value;
			}
		}
		return null;
	}
}
