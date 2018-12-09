package net.certiv.antlrdt.ui.handlers;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IProjectDescription;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.handlers.HandlerUtil;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.dsl.core.log.Log;

/**
 * RemoveNature handler extends AbstractHandler, an IHandler base class.
 * 
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RemoveNatureHandler extends AbstractHandler {

	/**
	 * The constructor.
	 */
	public RemoveNatureHandler() {}

	/**
	 * the command has been executed, so extract extract the needed information from the application
	 * context.
	 */
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection != null && selection instanceof IStructuredSelection) {
			for (Object element : ((IStructuredSelection) selection).toArray()) {
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = (IProject) ((IAdaptable) element).getAdapter(IProject.class);
				}
				if (project != null) {
					toggleNature(project);
				}
			}
		}
		return null;
	}

	/**
	 * Removes AntlrDT builder/nature on a project. Nature is automatically added when an AntlrDT
	 * editor is opened within a project.
	 * 
	 * @param project to have sample nature removed
	 */
	private void toggleNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (int i = 0; i < natures.length; ++i) {
				if (AntlrDTCore.getDefault().getNatureId().equals(natures[i])) {
					String[] newNatures = new String[natures.length - 1];
					System.arraycopy(natures, 0, newNatures, 0, i);
					System.arraycopy(natures, i + 1, newNatures, i, natures.length - i - 1);
					description.setNatureIds(newNatures);
					project.setDescription(description, null);
					Log.info(this, "Antlr Builder Nature removed [project=" + project.getName() + "]");
					return;
				}
			}
		} catch (CoreException e) {
			Log.error(this, "Failed in removing nature", e);
		}
	}
}
