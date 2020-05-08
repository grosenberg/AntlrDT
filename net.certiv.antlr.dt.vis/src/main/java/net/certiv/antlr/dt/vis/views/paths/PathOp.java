package net.certiv.antlr.dt.vis.views.paths;

public enum PathOp {
	FULL_BUILD,		// complete path rebuild
	ADD_CALLERS,	// add super paths to selected path node
	ADD_CALLEES,	// add sub paths to selected path node
	REMOVE_NODE;	// remove selected path node
}
