package net.certiv.antlrdt.ui.graph;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.eclipse.core.resources.IStorage;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.ui.IPersistableElement;
import org.eclipse.ui.IStorageEditorInput;

public class NullEditorInput implements IStorageEditorInput {

	private final String inputString;

	public NullEditorInput(String inputString) {
		this.inputString = inputString;
	}

	@Override
	public boolean exists() {
		return false;
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		return null;
	}

	@Override
	public IPersistableElement getPersistable() {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Object getAdapter(Class adapter) {
		return null;
	}

	@Override
	public String getName() {
		return "input name";
	}

	@Override
	public String getToolTipText() {
		return "tool tip";
	}

	@Override
	public IStorage getStorage() throws CoreException {
		return new IStorage() {

			@Override
			public InputStream getContents() throws CoreException {
				return new ByteArrayInputStream(inputString.getBytes(StandardCharsets.UTF_8));
			}

			@Override
			public IPath getFullPath() {
				return null;
			}

			@Override
			public String getName() {
				return NullEditorInput.this.getName();
			}

			@Override
			public boolean isReadOnly() {
				return false;
			}

			@Override
			@SuppressWarnings({ "rawtypes", "unchecked" })
			public Object getAdapter(Class adapter) {
				return null;
			}
		};
	}
}
