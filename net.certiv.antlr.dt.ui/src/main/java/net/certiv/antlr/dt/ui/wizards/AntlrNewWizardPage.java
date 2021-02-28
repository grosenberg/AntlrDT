/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.ui.wizards;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;

import net.certiv.common.util.Chars;
import net.certiv.common.util.Strings;
import net.certiv.dsl.ui.fields.ContentChangedEvent;
import net.certiv.dsl.ui.fields.IContentChangedListener;
import net.certiv.dsl.ui.fields.ITextButtonAdapter;
import net.certiv.dsl.ui.fields.SelectionField;
import net.certiv.dsl.ui.fields.TextButtonField;
import net.certiv.dsl.ui.wizard.DslFileWizard;
import net.certiv.dsl.ui.wizard.DslFileWizardPage;

/**
 * Wizard UI to obtain the grammar location and the file name. Will accept no extension OR
 * just g4.
 */
public class AntlrNewWizardPage extends DslFileWizardPage {

	private final static String PACKAGE = "package";	//$NON-NLS-1$
	private final static String SUPER = "superclass";	//$NON-NLS-1$
	private static final String FRAGMENTS = "fragments";	//$NON-NLS-1$
	private final static String UNICODE = "superclass";	//$NON-NLS-1$

	private TextButtonField pkgField;
	private TextButtonField superField;
	private SelectionField fragmentsField;
	private SelectionField unicodeField;
	private String importTxt = Strings.EMPTY;
	private boolean genFragments;
	private boolean genUnicode;

	public AntlrNewWizardPage(DslFileWizard wizard, IStructuredSelection selection) {
		super("AntlrNewWizardPage", wizard, selection);

		setTitle("Create Antlr Grammar");
		setDescription("Create new Antlr grammar");
		setFilename("Grammar");
		setFileExtension("g4");
	}

	@Override
	protected void createCustomGroup(Composite topLevel) {
		Composite container = new Composite(topLevel, SWT.NONE);
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
					String result = choosePackage();
					pkgField.setText(result);
					break;
				case SUPER:
					String sType = chooseSuperClass();
					superField.setText(sType);
					importTxt = sType;
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
	public String getPackageName() {
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
	protected String getInitialContents() {
		return null;
	}

	/**
	 * Create multiple files, returning the primary.
	 * <p>
	 * {@inheritDoc}
	 */
	@Override
	public IFile createNewFile() {
		final String filename = getFilename();
		final IPath base = getContainerFullPath();
		final String packageName = getPackageName();
		final String superclass = getSuperClass();
		final String importTxt = getImportTxt();
		final boolean fragments = getFragments();
		final boolean unicode = getUnicode();

		int dot = filename.lastIndexOf(Chars.DOT);
		String name = (dot != -1) ? filename.substring(0, dot) : filename;
		name = Strings.titleCase(name);

		String content = Gen.content("Parser", name, packageName, superclass, importTxt);
		IFile parserFile = createNewFile(base, name + "Parser.g4", getInitialContentStream(content));

		content = Gen.content("Lexer", name, packageName, superclass, importTxt);
		createNewFile(base, name + "Lexer.g4", getInitialContentStream(content));

		if (fragments) {
			content = Gen.content("Fragments", name);
			createNewFile(base, name + "Fragments.g4", getInitialContentStream(content));
		}

		if (unicode) {
			content = Gen.content("Unicode", name);
			createNewFile(base, name + "Unicode.g4", getInitialContentStream(content));
		}

		return parserFile;
	}
}
