package net.certiv.antlrdt.ui.templates;

import net.certiv.dsl.ui.editor.text.completion.DslTemplateContextType;

public class AntlrContextType extends DslTemplateContextType {

	public static final String CONTEXT_TYPE_ID = "net.certiv.antlrdt.ui.DefaultContext";
	public static final String CUSTOM_TEMPLATES_KEY = "net.certiv.antlrdt.ui.CustomContext.templates";

	public static final String GRAMMAR_ID = "grammar";
	public static final String OPTIONS_ID = "options";

	public static final String[] CONTEXT_TYPE_IDS = { GRAMMAR_ID, OPTIONS_ID };

	// public static Set<AntlrContextType> create(String... ids) {
	// Set<AntlrContextType> types = new HashSet<>();
	// for (String id : ids) {
	// if (!id.isEmpty()) types.add(new AntlrContextType(id));
	// }
	// return types;
	// }

	public AntlrContextType(String id) {
		super(id);
	}

	public AntlrContextType(String id, String name) {
		super(id, name);
	}
}
