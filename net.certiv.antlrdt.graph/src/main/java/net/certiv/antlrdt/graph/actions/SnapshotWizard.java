package net.certiv.antlrdt.graph.actions;

import javafx.scene.image.WritableImage;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.wizard.Wizard;
import org.eclipse.ui.IExportWizard;
import org.eclipse.ui.IWorkbench;

import net.certiv.antlrdt.ui.AntlrDTUI;

public class SnapshotWizard extends Wizard implements IExportWizard {

	private SnapshotWizardPage page;

	private WritableImage image;
	private int width;
	private int height;

	/** Creates a wizard for exporting snapshots to the local file system. */
	public SnapshotWizard(WritableImage image, int width, int height) {
		super();
		this.image = image;
		this.width = width;
		this.height = height;

		IDialogSettings workbenchSettings = AntlrDTUI.getDefault().getDialogSettings();
		IDialogSettings section = workbenchSettings.getSection("SnapshotWizard");//$NON-NLS-1$
		if (section == null) {
			section = workbenchSettings.addNewSection("SnapshotWizard");//$NON-NLS-1$
		}
		setDialogSettings(section);
	}

	@Override
	public void addPages() {
		super.addPages();
		page = new SnapshotWizardPage(image, width, height);
		addPage(page);
	}

	@Override
	public void init(IWorkbench workbench, IStructuredSelection currentSelection) {
		setWindowTitle("Snapshot Export Wizard");
		setNeedsProgressMonitor(true);
	}

	@Override
	public boolean performFinish() {
		return page.finish();
	}
}
