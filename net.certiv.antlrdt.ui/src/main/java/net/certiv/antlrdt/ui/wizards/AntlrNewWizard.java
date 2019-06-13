package net.certiv.antlrdt.ui.wizards;

import org.eclipse.core.resources.IContainer;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.resource.ImageDescriptor;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.ui.DslImageManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.wizards.DslBaseWizard;

/** Create a new grammar resource in the indicated container. */
public class AntlrNewWizard extends DslBaseWizard {

	private AntlrNewWizardPage page;

	public AntlrNewWizard() {
		super("AntlrNewWizard");
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
	public ImageDescriptor getPageImageDescriptor() {
		DslImageManager mgr = getDslUI().getImageManager();
		return mgr.getDescriptor(mgr.IMG_WIZBAN_NEW_FILE);
	}

	@Override
	public String getWindowShellTitle() {
		return "New Antlr grammar";
	}

	@Override
	public void addPages() {
		page = new AntlrNewWizardPage(this, getSelection());
		page.setTitle("Grammar");
		page.setDescription("Create new Antlr grammar");
		addPage(page);
	}

	@Override
	protected void finishPage(IProgressMonitor monitor) throws InterruptedException, CoreException {

		final String filename = page.getFileName();
		final IPath containerPath = page.getContainerFullPath();
		final String packageName = page.getPackageText();
		final String superclass = page.getSuperClass();
		final String importTxt = page.getImportTxt();
		final boolean fragments = page.getFragments();
		final boolean unicode = page.getUnicode();

		monitor.beginTask("Creating " + filename, 2);
		IWorkspaceRoot root = CoreUtil.getWorkspaceRoot();
		IContainer container = (IContainer) root.findMember(containerPath);
		if (!container.exists() || !(container instanceof IContainer)) {
			throwCoreException("Container '" + filename + "' does not exist.");
			return;
		}

		int dot = filename.lastIndexOf('.');
		String name = (dot != -1) ? filename.substring(0, dot) : filename;
		name = Strings.titleCase(name);

		IFile file = saveFile(container, name + "Parser.g4",
				Gen.content("Parser", name, packageName, superclass, importTxt), monitor);
		saveFile(container, name + "Lexer.g4", Gen.content("Lexer", name, packageName, superclass, importTxt), monitor);

		if (fragments) saveFile(container, name + "Fragments.g4", Gen.content("Fragments", name), monitor);
		if (unicode) saveFile(container, name + "Unicode.g4", Gen.content("Unicode", name), monitor);

		monitor.worked(1);

		monitor.setTaskName("Opening file for editing...");
		openEditor(file);
		monitor.worked(1);
	}
}
