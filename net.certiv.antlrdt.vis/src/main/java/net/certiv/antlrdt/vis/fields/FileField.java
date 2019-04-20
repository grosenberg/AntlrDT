package net.certiv.antlrdt.vis.fields;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import net.certiv.antlrdt.vis.views.tokens.Source;
import net.certiv.dsl.core.util.CoreUtil;
import net.certiv.dsl.ui.fields.TextField;

public class FileField extends TextField {

	private Shell shell;
	private Source source;
	private String defDir;

	/**
	 * Gets a file name. Operates on workspace relative paths. Displays the filename
	 * only.
	 */
	public FileField(Shell shell, Composite comp, int style, String id, String labelTxt, int hSpan, Source source,
			String defDir) {
		super(comp, style, id, labelTxt, hSpan);
		this.shell = shell;
		this.source = source;
		this.defDir = defDir;
	}

	@Override
	protected Composite createField() {
		Composite comp = super.createField();

		Button button = new Button(comp, SWT.PUSH);
		button.setText(" Browse ");
		GridDataFactory.fillDefaults().hint(60, SWT.DEFAULT).applyTo(button);
		button.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				FileDialog dialog = new FileDialog(shell, SWT.SINGLE);

				String path = "";
				String name = "";
				String ext = source.getExt();

				IPath fname = new Path(source.getPathname());
				fname = CoreUtil.getWorkspaceLocation().append(fname);
				if (fname.toFile().isFile()) {
					path = fname.removeLastSegments(1).toString();
					name = fname.lastSegment().toString();
				} else {
					path = CoreUtil.getWorkspaceLocation().append(defDir).toString();
				}
				if (ext == null || ext.trim().equals("")) ext = "*";

				dialog.setFilterPath(path);
				dialog.setFileName(name);
				dialog.setFilterExtensions(new String[] { "*." + ext });

				if (dialog.open() == null) return; 				// cancelled
				if (dialog.getFileName().trim().isEmpty()) { 	// entry cleared
					setSource(new Source());					// set to empty source
					fireContentChangedEvent("");
				} else {										// validate
					fname = new Path(dialog.getFilterPath()).append(new Path(dialog.getFileName()));
					if (fname.toFile().isFile()) {				// change only if valid
						fname = fname.makeRelativeTo(CoreUtil.getWorkspaceLocation());
						path = fname.removeLastSegments(1).toString();
						name = fname.lastSegment().toString();
						Source source = new Source(path, name);
						setSource(source);
						fireContentChangedEvent(dialog.getFileName());
					}
				}
			}
		});
		return comp;
	}

	public Source getSource() {
		String name = getText().trim();
		if (name.isEmpty()) {
			setSource(new Source());
		} else if (!name.equals(source.getName())) {
			setSource(new Source(source.getPath(), name));
		}
		return source;
	}

	public Source setSource(Source source) {
		Source prior = this.source;
		this.source = source;
		setText(source.getName());
		return prior;
	}

	@Override
	public int hSpanAdjust() {
		return 2;
	}
}
