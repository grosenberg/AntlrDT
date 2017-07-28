package net.certiv.antlrdt.ui.editor;

import java.util.ArrayList;

import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.ui.IActionBars;

import net.certiv.antlrdt.ui.AntlrDTImages;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.filter.AtFilter;
import net.certiv.antlrdt.ui.editor.filter.OptionsFilter;
import net.certiv.antlrdt.ui.editor.filter.RuleFilter;
import net.certiv.antlrdt.ui.editor.filter.TokenFilter;
import net.certiv.dsl.ui.editor.DslEditor;
import net.certiv.dsl.ui.editor.DslOutlinePage;
import net.certiv.dsl.ui.viewsupport.DslFilterAction;
import net.certiv.dsl.ui.viewsupport.DslFilterActionGroup;

public class AntlrDTOutlinePage extends DslOutlinePage {

	public AntlrDTOutlinePage(DslEditor editor, IPreferenceStore store) {
		super(AntlrDTUI.getDefault(), editor, store);
	}

	@Override
	protected ILabelDecorator getLabelDecorator() {
		return new AntlrDTOutlineLabelDecorator();
	}

	@Override
	public void registerSpecialToolbarActions(IActionBars actionBars) {

		IToolBarManager toolBarManager = actionBars.getToolBarManager();
		DslFilterActionGroup fMemberFilterActionGroup = new DslFilterActionGroup(viewer, store);
		String title, helpContext;
		ArrayList<DslFilterAction> actions = new ArrayList<DslFilterAction>(4);
		AntlrDTImages imgProvider = (AntlrDTImages) AntlrDTUI.getDefault().getImageProvider();

		// fill-in actions rule
		title = ActionMessages.MemberFilterActionGroup_hide_rules_label;
		helpContext = "";
		DslFilterAction hideRules = new DslFilterAction(fMemberFilterActionGroup, title, new RuleFilter(), helpContext,
				true);
		hideRules.setDescription(ActionMessages.MemberFilterActionGroup_hide_rules_description);
		hideRules.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_rules_tooltip);
		ImageDescriptor ruleDesc = imgProvider.DESC_OBJ_PARSER_FILTER;
		hideRules.setHoverImageDescriptor(ruleDesc);
		hideRules.setImageDescriptor(ruleDesc);
		actions.add(hideRules);

		// fill-in actions lexer rules
		title = ActionMessages.MemberFilterActionGroup_hide_tokens_label;
		helpContext = "";
		DslFilterAction hideTokens = new DslFilterAction(fMemberFilterActionGroup, title, new TokenFilter(),
				helpContext, true);
		hideTokens.setDescription(ActionMessages.MemberFilterActionGroup_hide_tokens_description);
		hideTokens.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_tokens_tooltip);
		ImageDescriptor tokenDesc = imgProvider.DESC_OBJ_LEXER_FILTER;
		hideTokens.setHoverImageDescriptor(tokenDesc);
		hideTokens.setImageDescriptor(tokenDesc);
		actions.add(hideTokens);

		// fill-in actions options
		title = ActionMessages.MemberFilterActionGroup_hide_options_label;
		helpContext = "";
		DslFilterAction hideOptions = new DslFilterAction(fMemberFilterActionGroup, title, new OptionsFilter(),
				helpContext, true);
		hideOptions.setDescription(ActionMessages.MemberFilterActionGroup_hide_options_description);
		hideOptions.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_options_tooltip);
		ImageDescriptor optDesc = imgProvider.DESC_OBJ_OPTIONS_FILTER;
		hideOptions.setHoverImageDescriptor(optDesc);
		hideOptions.setImageDescriptor(optDesc);
		actions.add(hideOptions);

		// fill-in actions at
		title = ActionMessages.MemberFilterActionGroup_hide_at_label;
		helpContext = "";
		DslFilterAction atElements = new DslFilterAction(fMemberFilterActionGroup, title, new AtFilter(), helpContext,
				true);
		atElements.setDescription(ActionMessages.MemberFilterActionGroup_hide_at_description);
		atElements.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_at_tooltip);
		ImageDescriptor atDesc = imgProvider.DESC_OBJ_ACTIONS_FILTER;
		atElements.setHoverImageDescriptor(atDesc);
		atElements.setImageDescriptor(atDesc);
		actions.add(atElements);

		// order corresponds to order in toolbar
		DslFilterAction[] fFilterActions = actions.toArray(new DslFilterAction[actions.size()]);
		fMemberFilterActionGroup.setActions(fFilterActions);
		fMemberFilterActionGroup.contributeToToolBar(toolBarManager);
	}
}
