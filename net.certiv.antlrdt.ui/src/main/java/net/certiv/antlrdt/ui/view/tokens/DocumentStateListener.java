package net.certiv.antlrdt.ui.view.tokens;

import org.eclipse.ui.texteditor.IElementStateListener;

class DocumentStateListener implements IElementStateListener {

	@Override
	public void elementDirtyStateChanged(Object element, boolean isDirty) {}

	@Override
	public void elementContentAboutToBeReplaced(Object element) {}

	@Override
	public void elementContentReplaced(Object element) {}

	@Override
	public void elementDeleted(Object element) {}

	@Override
	public void elementMoved(Object originalElement, Object movedElement) {}
}
