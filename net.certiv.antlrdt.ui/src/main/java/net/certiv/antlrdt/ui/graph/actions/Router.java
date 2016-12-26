package net.certiv.antlrdt.ui.graph.actions;

import org.eclipse.draw2d.ConnectionRouter;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.ManhattanConnectionRouter;

import net.certiv.antlrdt.ui.graph.layouts.BranchedConnectionRouter;

public enum Router {

	BRANCHED("Branched", new BranchedConnectionRouter()), //
	FAN("Fan", new FanRouter()), //
	MANHATTAN("Manhattan", new ManhattanConnectionRouter());

	private String routerName;
	private ConnectionRouter routerAlgorithm;

	Router(String name, ConnectionRouter router) {
		routerName = name;
		routerAlgorithm = router;
	}

	public String getName() {
		return routerName;
	}

	public ConnectionRouter getRouter() {
		return routerAlgorithm;
	}

	public static Router getEnum(String name) {
		if (name != null) {
			for (Router value : values()) {
				if (value.getName().toLowerCase().equals(name.toLowerCase())) return value;
			}
		}
		return null;
	}
}
