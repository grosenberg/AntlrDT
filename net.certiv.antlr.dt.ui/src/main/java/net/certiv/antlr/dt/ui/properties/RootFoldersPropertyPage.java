package net.certiv.antlr.dt.ui.properties;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IAdaptable;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.model.IDslProject;
import net.certiv.dsl.core.preferences.PrefsDeltaManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.preferences.blocks.IPreferenceConfigBlock;
import net.certiv.dsl.ui.preferences.blocks.RootConfigBlock;
import net.certiv.dsl.ui.preferences.pages.AbstractPropertyPage;

public class RootFoldersPropertyPage extends AbstractPropertyPage {

	public RootFoldersPropertyPage() {
		super();
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	@Override
	protected IPreferenceConfigBlock createConfigurationBlock(PrefsDeltaManager delta) {
		return new RootConfigBlock(this, delta, getFormkit(), getColorRegistry(), getProject());
	}

	private IDslProject getProject() {
		IAdaptable adaptable = getElement();
		if (adaptable == null) return null;
		IProject project = adaptable.getAdapter(IProject.class);
		if (project == null) return null;
		return getDslCore().getDslModel().create(project);
	}
}
