package net.certiv.antlr.dt.diagram.preferences.blocks;

import java.util.List;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.ui.forms.widgets.FormToolkit;

import net.certiv.antlr.dt.core.preferences.PrefsKey;
import net.certiv.antlr.dt.diagram.preferences.tabs.BlackFridayTab;
import net.certiv.antlr.dt.diagram.preferences.tabs.ExternalTab;
import net.certiv.antlr.dt.diagram.preferences.tabs.FlexmarkTab;
import net.certiv.antlr.dt.diagram.preferences.tabs.PandocTab;
import net.certiv.dsl.core.color.DslColorManager;
import net.certiv.dsl.core.preferences.DslPrefsManagerDelta;
import net.certiv.dsl.ui.preferences.blocks.AbstractConfigBlock;
import net.certiv.dsl.ui.preferences.pages.IDslPreferencePage;
import net.certiv.dsl.ui.preferences.tabs.AbstractTab;
import net.certiv.dsl.ui.util.SWTFactory;

public class ConvertersConfigBlock extends AbstractConfigBlock {

	private TabFolder folder;
	private PandocTab pandocTab;
	private FlexmarkTab fmarkTab;
	private BlackFridayTab blackFridayTab;
	private ExternalTab externalTab;

	public ConvertersConfigBlock(IDslPreferencePage page, DslPrefsManagerDelta delta, FormToolkit formkit,
			DslColorManager colorMgr) {
		super(page, delta, formkit, colorMgr);
	}

	@Override
	protected List<String> createDeltaMatchKeys(List<String> keys) {

		keys.add(PrefsKey.KEY_PANDOC);
		keys.add(PrefsKey.EDITOR_PANDOC_PROGRAM);
		keys.add(PrefsKey.EDITOR_PANDOC_TEMPLATES);
		keys.add(PrefsKey.EDITOR_PANDOC_TEMPLATE);
		keys.add(PrefsKey.EDITOR_PANDOC_ADDTOC);
		keys.add(PrefsKey.EDITOR_PANDOC_SMART);
		keys.add(PrefsKey.EDITOR_PANDOC_MATHJAX);

		keys.add(PrefsKey.KEY_FLEXMARK);
		keys.add(PrefsKey.EDITOR_FLEXMARK_PROFILE);
		keys.add(PrefsKey.EDITOR_FLEXMARK_MATHJAX);

		keys.add(PrefsKey.KEY_BLACKFRIDAY);
		keys.add(PrefsKey.EDITOR_BLACKFRIDAY_PROGRAM);
		keys.add(PrefsKey.EDITOR_BLACKFRIDAY_ADDTOC);
		keys.add(PrefsKey.EDITOR_BLACKFRIDAY_SMART);

		keys.add(PrefsKey.KEY_EXTERNAL);
		keys.add(PrefsKey.EDITOR_EXTERNAL_COMMAND);

		keys.add(PrefsKey.KEY_GRAPHS);
		keys.add(PrefsKey.EDITOR_DOTMODE_ENABLED);
		keys.add(PrefsKey.EDITOR_UMLMODE_ENABLED);
		keys.add(PrefsKey.EDITOR_DOT_PROGRAM);

		return keys;
	}

	@Override
	public Composite createContents(Composite parent) {
		Composite contents = super.createContents(parent);
		folder = addTabFolder(contents, SWT.NONE, GridData.BEGINNING, GridData.BEGINNING, true, true, SWT.DEFAULT);
		Dialog.applyDialogFont(folder);

		createTab(Cnv.PANDOC);
		createTab(Cnv.FLEXMARK);
		createTab(Cnv.BLACKFRIDAY);
		createTab(Cnv.EXTERNAL);

		Composite graphs = SWTFactory.createGroupComposite(contents, 3, 3, "Graph Generators");
		addCheckBox(graphs, "Enable PlantUML generator", EDITOR_UMLMODE_ENABLED, 3, 4);
		addCheckBox(graphs, "Enable Graphviz Dot generator", EDITOR_DOTMODE_ENABLED, 3, 4);

		addProgramField(graphs, EDITOR_DOT_PROGRAM, 4, 3, "Dot executable", 50, AbstractTab.validFilePath);

		SWTFactory.createVerticalSpacer(contents, 1);

		return contents;
	}

	private void createTab(Cnv type) {
		Composite comp = SWTFactory.createComposite(folder, 1, 1, GridData.FILL_HORIZONTAL);
		TabItem tab = new TabItem(folder, SWT.NONE, type.index());
		tab.setText(type.title());
		tab.setControl(comp);

		SWTFactory.createVerticalSpacer(comp, 1);

		switch (type.key()) {
			case KEY_PANDOC:
				pandocTab = new PandocTab(this, comp, delta, type.desc());
				break;

			case KEY_FLEXMARK:
				fmarkTab = new FlexmarkTab(this, comp, delta, type.desc());
				break;

			case KEY_BLACKFRIDAY:
				blackFridayTab = new BlackFridayTab(this, comp, delta, type.desc());
				break;

			case KEY_EXTERNAL:
				externalTab = new ExternalTab(this, comp, delta, type.desc());
				break;
		}
		SWTFactory.createVerticalSpacer(comp, 1);
	}

	public void selectTab(int tabId) {
		folder.setSelection(tabId);
	}

	public void validateSettings(String key) {
		switch (key) {
			case KEY_PANDOC:
				pandocTab.validateSettings();
				break;

			case KEY_FLEXMARK:
				fmarkTab.validateSettings();
				break;

			case KEY_BLACKFRIDAY:
				blackFridayTab.validateSettings();
				break;

			case KEY_EXTERNAL:
				externalTab.validateSettings();
				break;
		}
	}
}
