package net.certiv.antlrdt.ui.templates;

import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.text.templates.ContextTypeRegistry;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.dsl.ui.templates.DslTemplateAccess;

/**
 * Provides access to the template delta.
 */
public class AntlrDTTemplateAccess extends DslTemplateAccess {

	private static AntlrDTTemplateAccess instance;

	public static AntlrDTTemplateAccess getInstance() {
		if (instance == null) {
			instance = new AntlrDTTemplateAccess();
		}
		return instance;
	}

	protected String getContextTypeId() {
		return AntlrDTTemplateContextType.ANTLRDT_CONTEXT_TYPE_ID;
	}

	protected String getCustomTemplatesKey() {
		return AntlrDTTemplateContextType.ANTLRDT_CUSTOM_TEMPLATES_KEY;
	}

	protected IPreferenceStore getPreferenceStore() {
		return AntlrDTCore.getDefault().getPrefsManager();
	}

	@Override
	public ContextTypeRegistry getContextTypeRegistry() {
		if (fRegistry == null) {
			super.getContextTypeRegistry();
			fRegistry.addContextType(AntlrDTTemplateContextType.GRAMMAR_CONTEXT_TYPE_ID);
			fRegistry.addContextType(AntlrDTTemplateContextType.OPTIONS_CONTEXT_TYPE_ID);
			fRegistry.addContextType(AntlrDTTemplateContextType.ACTIONS_CONTEXT_TYPE_ID);
			fRegistry.addContextType(AntlrDTTemplateContextType.JAVADOC_CONTEXT_TYPE_ID);
		}
		return fRegistry;
	}
}
