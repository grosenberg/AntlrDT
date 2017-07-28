package net.certiv.antlrdt.ui;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ltk.core.refactoring.Refactoring;

import net.certiv.dsl.ui.refactoring.wizards.IWizardsFactory;
import net.certiv.dsl.ui.refactoring.wizards.RenameRefactoringWizard;

public class WizardsFactory implements IWizardsFactory {

	public RenameRefactoringWizard createRenameRefactoringWizard(Refactoring refactoring, String defaultPageTitle,
			String inputPageDescription, ImageDescriptor inputPageImageDescriptor, String pageContextHelpId) {

		RenameRefactoringWizard renameRefactoringWizard = new RenameRefactoringWizard(refactoring, defaultPageTitle,
				inputPageDescription, inputPageImageDescriptor, pageContextHelpId);

		return renameRefactoringWizard;
	}
}
