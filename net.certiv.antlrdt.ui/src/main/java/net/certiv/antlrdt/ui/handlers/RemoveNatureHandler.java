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

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.dsl.core.log.Log;

/**
 * RemoveNature handler extends AbstractHandler, an IHandler base class.
 *
 * @see org.eclipse.core.commands.IHandler
 * @see org.eclipse.core.commands.AbstractHandler
 */
public class RemoveNatureHandler extends AbstractHandler {

	@Override
	public Object execute(ExecutionEvent event) throws ExecutionException {
		ISelection selection = HandlerUtil.getCurrentSelection(event);
		if (selection != null && selection instanceof IStructuredSelection) {
			for (Object element : ((IStructuredSelection) selection).toArray()) {
				IProject project = null;
				if (element instanceof IProject) {
					project = (IProject) element;
				} else if (element instanceof IAdaptable) {
					project = ((IAdaptable) element).getAdapter(IProject.class);
				}
				if (project != null) {
					toggleNature(project);
				}
			}
		}
		return null;
	}

	/**
	 * Removes AntlrDT builder/nature on a project. Nature is automatically added
	 * when an AntlrDT editor is opened within a project.
	 *
	 * @param project to have sample nature removed
	 */
	private void toggleNature(IProject project) {
		try {
			IProjectDescription description = project.getDescription();
			String[] natures = description.getNatureIds();

			for (int idx = 0; idx < natures.length; idx++) {
				if (AntlrCore.getDefault().getNatureId().equals(natures[idx])) {
					String[] newNatures = new String[natures.length - 1];
					System.arraycopy(natures, 0, newNatures, 0, idx);
					System.arraycopy(natures, idx + 1, newNatures, idx, natures.length - idx - 1);
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
