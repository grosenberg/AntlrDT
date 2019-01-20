package net.certiv.antlrdt.graph.view;

public enum PathOp {
	FULL_BUILD("Root Target", "Rebuild and show root path"),
	ADD_CALLERS("Add Callers", "Add calling nodes"),
	ADD_CALLEES("Add Callees", "Add called nodes"),
	REMOVE_NODE("Remove Node", "Remove node and connections"),

	;

	private String _text;
	private String _tooltip;

	PathOp(String text, String tooltip) {
		_text = text;
		_tooltip = tooltip;
	}

	public String text() {
		return _text;
	}

	public String tooltip() {
		return _tooltip;
	}
}
