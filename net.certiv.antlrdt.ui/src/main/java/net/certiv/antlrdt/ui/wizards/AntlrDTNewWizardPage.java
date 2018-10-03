package net.certiv.antlrdt.ui.wizards;

import org.eclipse.jdt.core.IPackageFragment;
import org.eclipse.jdt.core.IType;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.jdt.util.DialogUtil;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.fields.ContentChangedEvent;
import net.certiv.dsl.ui.fields.IContentChangedListener;
import net.certiv.dsl.ui.fields.ITextButtonAdapter;
import net.certiv.dsl.ui.fields.SelectionField;
import net.certiv.dsl.ui.fields.TextButtonField;
import net.certiv.dsl.ui.wizards.DslContainerWizardPage;

/**
 * Wizard UI to obtain the grammar location and the file name. Will accept no extension OR just g4.
 */
public class AntlrDTNewWizardPage extends DslContainerWizardPage {

	private final static String PACKAGE = "package";	//$NON-NLS-1$
	private final static String SUPER = "superclass";	//$NON-NLS-1$
	private static final String FRAGMENTS = "fragments";	//$NON-NLS-1$
	private final static String UNICODE = "superclass";	//$NON-NLS-1$

	private TextButtonField pkgField;
	private TextButtonField superField;
	private SelectionField fragmentsField;
	private SelectionField unicodeField;
	private String importTxt = "";
	private boolean genFragments;
	private boolean genUnicode;

	public AntlrDTNewWizardPage(IStructuredSelection selection) {
		super("newPage", selection);
	}

	@Override
	public void createControl(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);
		GridLayoutFactory.fillDefaults().spacing(6, 9).margins(6, 6).applyTo(container);

		setFileName("Grammar");
		setFileExtension("g4");
		createContainerControl(container);
		createSubControls(container);

		setControl(container);
		setErrorMessage(null);
		setMessage(null);
		validatePage();
	}

	private void createSubControls(Composite parent) {
		Composite container = new Composite(parent, SWT.NONE);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(container);
		GridLayoutFactory.fillDefaults().numColumns(3).margins(6, 6).applyTo(container);

		FieldsAdapter adapter = new FieldsAdapter();

		pkgField = new TextButtonField(container, SWT.NONE, PACKAGE, "Package:", 3, "Select", adapter);
		superField = new TextButtonField(container, SWT.NONE, SUPER, "Super class:", 3, "Select", adapter);
		fragmentsField = new SelectionField(container, SWT.CHECK, FRAGMENTS, "Generate fragment examples", 3);
		fragmentsField.addContentChangedListener(adapter);
		unicodeField = new SelectionField(container, SWT.CHECK, UNICODE, "Generate Unicode ranges", 3);
		unicodeField.addContentChangedListener(adapter);
	}

	private class FieldsAdapter implements ITextButtonAdapter, IContentChangedListener {

		@Override
		public void buttonPressed(TextButtonField field, String id, SelectionEvent e) {
			switch (id) {
				case PACKAGE:
					IPackageFragment result = DialogUtil.choosePackage();
					pkgField.setText(result.getElementName());
					break;
				case SUPER:
					IType sType = DialogUtil.chooseSuperClass();
					superField.setText(sType.getElementName());
					importTxt = sType.getFullyQualifiedName();
					break;
			}
		}

		@Override
		public void contentChanged(ContentChangedEvent event) {
			switch (event.getId()) {
				case FRAGMENTS:
					genFragments = (boolean) event.getNewValue();
					break;
				case UNICODE:
					genUnicode = (boolean) event.getNewValue();
					break;
			}
		}
	}

	/** Returns the text of the package input field. */
	public String getPackageText() {
		return pkgField.getText();
	}

	/** Returns the content of the superclass input field. */
	public String getSuperClass() {
		return superField.getText();
	}

	/** Returns the text of for the import */
	public String getImportTxt() {
		return importTxt;
	}

	public boolean getFragments() {
		return genFragments;
	}

	public boolean getUnicode() {
		return genUnicode;
	}

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}
}
