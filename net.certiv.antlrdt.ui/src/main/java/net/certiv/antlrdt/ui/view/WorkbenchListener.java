package net.certiv.antlrdt.ui.view;

import org.eclipse.core.filebuffers.IFileBuffer;
import org.eclipse.core.filebuffers.IFileBufferListener;
import org.eclipse.core.resources.IResourceChangeEvent;
import org.eclipse.core.resources.IResourceChangeListener;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.ui.IPartListener2;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.IWorkbenchPartReference;

class WorkbenchListener implements ISelectionListener, IFileBufferListener, IDocumentListener,
		ISelectionChangedListener, IDoubleClickListener, IPartListener2, IResourceChangeListener {

	public void resourceChanged(IResourceChangeEvent event) {}

	public void selectionChanged(IWorkbenchPart part, ISelection selection) {}

	public void selectionChanged(SelectionChangedEvent event) {}

	public void partVisible(IWorkbenchPartReference partRef) {}

	public void partHidden(IWorkbenchPartReference partRef) {}

	public void partClosed(IWorkbenchPartReference partRef) {}

	public void partActivated(IWorkbenchPartReference partRef) {}

	public void partDeactivated(IWorkbenchPartReference partRef) {}

	public void partBroughtToTop(IWorkbenchPartReference partRef) {}

	public void partInputChanged(IWorkbenchPartReference partRef) {}

	public void partOpened(IWorkbenchPartReference partRef) {}

	public void documentChanged(DocumentEvent event) {}

	public void bufferDisposed(IFileBuffer buffer) {}

	public void documentAboutToBeChanged(DocumentEvent event) {}

	public void bufferContentAboutToBeReplaced(IFileBuffer buffer) {}

	public void bufferContentReplaced(IFileBuffer buffer) {}

	public void bufferCreated(IFileBuffer buffer) {}

	public void dirtyStateChanged(IFileBuffer buffer, boolean isDirty) {}

	public void stateChangeFailed(IFileBuffer buffer) {}

	public void stateChanging(IFileBuffer buffer) {}

	public void stateValidationChanged(IFileBuffer buffer, boolean isStateValidated) {}

	public void underlyingFileDeleted(IFileBuffer buffer) {}

	public void underlyingFileMoved(IFileBuffer buffer, IPath path) {}

	public void doubleClick(DoubleClickEvent event) {}
}