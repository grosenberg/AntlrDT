package net.certiv.antlrdt.ui.editor;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.preference.IPreferenceStore;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IContentProvider;
import org.eclipse.jface.viewers.ILabelDecorator;
import org.eclipse.ui.IActionBars;

import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.antlrdt.ui.editor.filter.AtFilter;
import net.certiv.antlrdt.ui.editor.filter.OptionsFilter;
import net.certiv.antlrdt.ui.editor.filter.RuleFilter;
import net.certiv.antlrdt.ui.editor.filter.TokenFilter;
import net.certiv.dsl.core.model.IDslElement;
import net.certiv.dsl.core.model.ISourceRef;
import net.certiv.dsl.ui.editor.DslEditor;
import net.certiv.dsl.ui.editor.DslOutlinePage;
import net.certiv.dsl.ui.viewsupport.DslFilterAction;
import net.certiv.dsl.ui.viewsupport.DslFilterActionGroup;

public class AntlrDTOutlinePage extends DslOutlinePage {

	protected class GrammarDataProvider extends OutlineDataProvider {

		@Override
		protected IDslElement[] filterChildren(ISourceRef node) {
			List<IDslElement> filtered = new ArrayList<>();
			IDslElement[] children = node.getChildren();
			for (int idx = 0; idx < children.length; idx++) {
				IDslElement child = children[idx];
				if (endBlock(child)) continue;
				if (firstFieldOfStatement(idx, child)) continue;
				if (impliedImport(child)) continue;

				filtered.add(child);
			}
			return filtered.toArray(new IDslElement[filtered.size()]);
		}

		private boolean endBlock(IDslElement child) {
			return child.getKind() == IDslElement.END_BLOCK;
		}

		private boolean firstFieldOfStatement(int idx, IDslElement child) {
			if (idx == 0 && child.getKind() == IDslElement.FIELD) {
				if (child.getParent() != null && (child.getParent().getKind() == IDslElement.STATEMENT
						|| child.getParent().getKind() == IDslElement.DECLARATION)) {
					return true;
				}
			}
			return false;
		}

		private boolean impliedImport(IDslElement child) {
			return child.getKind() == IDslElement.IMPORT && child.getParent().getKind() == IDslElement.DECLARATION;
		}
	}

	public AntlrDTOutlinePage(DslEditor editor, IPreferenceStore store) {
		super(AntlrUI.getDefault(), editor, store);
	}

	@Override
	protected ILabelDecorator getLabelDecorator() {
		return new AntlrStatementLabelProvider();
	}

	@Override
	protected IContentProvider getOutlineDataProvider() {
		return new GrammarDataProvider();
	}

	@Override
	public void registerSpecialToolbarActions(IActionBars actionBars, IToolBarManager barMgr, IMenuManager menuMgr) {
		DslFilterActionGroup group = new DslFilterActionGroup(viewer, store);
		List<DslFilterAction> actions = buildActions(group);
		group.setActions(actions.toArray(new DslFilterAction[actions.size()]));
		group.contributeToToolBar(barMgr);
	}

	// order defines the order in toolbar
	private List<DslFilterAction> buildActions(DslFilterActionGroup group) {
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		List<DslFilterAction> actions = new ArrayList<>();
		String title, helpContext;

		// fill-in actions rule
		title = ActionMessages.MemberFilterActionGroup_hide_rules_label;
		helpContext = "";
		DslFilterAction hideRules = new DslFilterAction(group, title, new RuleFilter(), helpContext, true);
		hideRules.setDescription(ActionMessages.MemberFilterActionGroup_hide_rules_description);
		hideRules.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_rules_tooltip);
		ImageDescriptor ruleDesc = imgMgr.getDescriptor(imgMgr.IMG_OBJ_PARSER_FILTER);
		hideRules.setHoverImageDescriptor(ruleDesc);
		hideRules.setImageDescriptor(ruleDesc);
		actions.add(hideRules);

		// fill-in actions lexer rules
		title = ActionMessages.MemberFilterActionGroup_hide_tokens_label;
		helpContext = "";
		DslFilterAction hideTokens = new DslFilterAction(group, title, new TokenFilter(), helpContext, true);
		hideTokens.setDescription(ActionMessages.MemberFilterActionGroup_hide_tokens_description);
		hideTokens.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_tokens_tooltip);
		ImageDescriptor tokenDesc = imgMgr.getDescriptor(imgMgr.IMG_OBJ_LEXER_FILTER);
		hideTokens.setHoverImageDescriptor(tokenDesc);
		hideTokens.setImageDescriptor(tokenDesc);
		actions.add(hideTokens);

		// fill-in actions options
		title = ActionMessages.MemberFilterActionGroup_hide_options_label;
		helpContext = "";
		DslFilterAction hideOptions = new DslFilterAction(group, title, new OptionsFilter(), helpContext, true);
		hideOptions.setDescription(ActionMessages.MemberFilterActionGroup_hide_options_description);
		hideOptions.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_options_tooltip);
		ImageDescriptor optDesc = imgMgr.getDescriptor(imgMgr.IMG_OBJ_OPTIONS_FILTER);
		hideOptions.setHoverImageDescriptor(optDesc);
		hideOptions.setImageDescriptor(optDesc);
		actions.add(hideOptions);

		// fill-in actions at
		title = ActionMessages.MemberFilterActionGroup_hide_at_label;
		helpContext = "";
		DslFilterAction atElements = new DslFilterAction(group, title, new AtFilter(), helpContext, true);
		atElements.setDescription(ActionMessages.MemberFilterActionGroup_hide_at_description);
		atElements.setToolTipText(ActionMessages.MemberFilterActionGroup_hide_at_tooltip);
		ImageDescriptor atDesc = imgMgr.getDescriptor(imgMgr.IMG_OBJ_ACTIONS_FILTER);
		atElements.setHoverImageDescriptor(atDesc);
		atElements.setImageDescriptor(atDesc);
		actions.add(atElements);

		return actions;
	}
}
