package net.certiv.antlrdt.graph.view;

public enum Kind {
	FULL_BUILD("Root Path", "Rebuild and show root path"),
	ADD_CALLEES("Add Callees", "Add called nodes"),
	ADD_CALLERS("Add Callers", "Add calling nodes"),
	REMOVE_NODE("Remove Node", "Remove node and connections"),

	;

	private String _text;
	private String _tooltip;

	Kind(String text, String tooltip) {
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
