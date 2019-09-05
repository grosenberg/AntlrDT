package net.certiv.antlrdt.graph.actions;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javafx.embed.swt.SWTFXUtils;
import javafx.scene.image.WritableImage;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.ScrollBar;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.eclipse.ui.dialogs.IOverwriteQuery;

import net.certiv.antlrdt.graph.view.tree.TreeView;
import net.certiv.dsl.core.util.Strings;

public class SnapshotWizardPage extends WizardPage implements Listener, IOverwriteQuery {

	protected Combo pathnameCombo;
	private Button browseButton;
	private Button overwriteExistingCheckbox;
	protected Text descText;

	// dialog store id constants
	private static final String STORE_DESTINATION_NAMES_ID = "SnapShotExport.STORE_DESTINATION_NAMES_ID";//$NON-NLS-1$
	private static final String STORE_OVERWRITE_EXISTING_FILES_ID = "SnapShotExport.STORE_OVERWRITE_EXISTING_FILES_ID";//$NON-NLS-1$
	private static final String EXPORT_SNAPSHOT = "exportSnapshot"; // //$NON-NLS-1$

	private static final String STORE_DESTINATION_ID = null;
	private static final int COMBO_HISTORY_LENGTH = 5;

	public static final String PNG = "png";
	private static final int flags = SWT.BORDER | SWT.NO_BACKGROUND | SWT.NO_REDRAW_RESIZE | SWT.V_SCROLL
			| SWT.H_SCROLL;

	private String currentMessage;
	private WritableImage image;
	private Image img;
	private int width;
	private int height;

	public SnapshotWizardPage(WritableImage image, int width, int height) {
		super(EXPORT_SNAPSHOT);
		this.image = image;
		this.width = width;
		this.height = height;
	}

	@Override
	public void createControl(Composite parent) {
		initializeDialogUnits(parent);
		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout());
		composite.setLayoutData(new GridData(GridData.FILL_BOTH));

		createPreviewGroup(composite);
		createDestinationGroup(composite);
		createOptionsGroup(composite);

		restoreWidgetValues();

		// can not finish initially, but don't want to start with an error message either
		if (!validDestination()) setPageComplete(false);

		setControl(composite);

		giveFocusToDestination();
		Dialog.applyDialogFont(composite);
	}

	protected void createPreviewGroup(Composite parent) {
		Composite preview = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout();
		layout.numColumns = 3;
		preview.setLayout(layout);
		preview.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		final Canvas canvas = new Canvas(preview, flags);
		canvas.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		canvas.setBackground(canvas.getDisplay().getSystemColor(SWT.COLOR_WHITE));

		final Point origin = new Point(0, 0);
		canvas.setBounds(TreeView.PAD, TreeView.PAD, width + TreeView.PAD, height + TreeView.PAD);

		ImageData imgData = SWTFXUtils.fromFXImage(image, null);
		img = new Image(parent.getDisplay(), imgData);

		final ScrollBar hBar = canvas.getHorizontalBar();
		hBar.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				int hSelection = hBar.getSelection();
				int destX = -hSelection - origin.x;
				Rectangle rect = img.getBounds();
				canvas.scroll(destX, 0, 0, 0, rect.width, rect.height, false);
				origin.x = -hSelection;
			}
		});

		final ScrollBar vBar = canvas.getVerticalBar();
		vBar.addListener(SWT.Selection, new Listener() {

			@Override
			public void handleEvent(Event e) {
				int vSelection = vBar.getSelection();
				int destY = -vSelection - origin.y;
				Rectangle rect = img.getBounds();
				canvas.scroll(0, destY, 0, 0, rect.width, rect.height, false);
				origin.y = -vSelection;
			}
		});
		canvas.addListener(SWT.Resize, new Listener() {
			@Override
			public void handleEvent(Event e) {
				Rectangle rect = img.getBounds();
				Rectangle client = canvas.getClientArea();
				hBar.setMaximum(rect.width);
				vBar.setMaximum(rect.height);
				hBar.setThumb(Math.min(rect.width, client.width));
				vBar.setThumb(Math.min(rect.height, client.height));
				int hPage = rect.width - client.width;
				int vPage = rect.height - client.height;
				int hSelection = hBar.getSelection();
				int vSelection = vBar.getSelection();
				if (hSelection >= hPage) {
					if (hPage <= 0) hSelection = 0;
					origin.x = -hSelection;
				}
				if (vSelection >= vPage) {
					if (vPage <= 0) vSelection = 0;
					origin.y = -vSelection;
				}
				canvas.redraw();
			}
		});
		canvas.addListener(SWT.Paint, new Listener() {
			@Override
			public void handleEvent(Event e) {
				GC gc = e.gc;
				gc.drawImage(img, origin.x, origin.y);
				Rectangle rect = img.getBounds();
				Rectangle client = canvas.getClientArea();
				int marginWidth = client.width - rect.width;
				if (marginWidth > 0) {
					gc.fillRectangle(rect.width, 0, marginWidth, client.height);
				}
				int marginHeight = client.height - rect.height;
				if (marginHeight > 0) {
					gc.fillRectangle(0, rect.height, client.width, marginHeight);
				}
			}
		});
	}

	/** Create the export destination specification widgets */
	protected void createDestinationGroup(Composite parent) {
		Composite dest = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).applyTo(dest);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(dest);

		Label label = new Label(dest, SWT.NONE);
		label.setText("Destination");

		// destination name entry field
		pathnameCombo = new Combo(dest, SWT.SINGLE | SWT.BORDER);
		pathnameCombo.addListener(SWT.Modify, this);
		pathnameCombo.addListener(SWT.Selection, this);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(pathnameCombo);

		// destination browse button
		browseButton = new Button(dest, SWT.PUSH);
		browseButton.setText("Browse");
		setButtonLayoutData(browseButton);
		browseButton.addListener(SWT.Selection, this);

		new Label(parent, SWT.NONE); // vertical spacer
	}

	/** Create the export options specification widgets. */
	protected void createOptionsGroup(Composite parent) {
		Composite options = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().applyTo(options);
		GridDataFactory.fillDefaults().grab(true, false).applyTo(options);

		overwriteExistingCheckbox = new Button(options, SWT.CHECK | SWT.LEFT);
		overwriteExistingCheckbox.setText("Overwrite Existing");
	}

	/** Check whether the internal state of the page is complete and update the dialog */
	public void setPageComplete() {
		boolean complete = true;
		if (!determinePageCompletion()) complete = false;
		super.setPageComplete(complete);
	}

	/**
	 * The Finish button was pressed. Try to do the required work now and answer a boolean indicating
	 * success. If false is returned then the wizard will not close.
	 *
	 * @return boolean
	 */
	public boolean finish() {
		saveWidgetValues();
		if (img != null) img.dispose();
		return true;
	}

	protected String getDestinationValue() {
		String idealSuffix = getOutputSuffix();
		String destinationText = pathnameCombo.getText().trim();
		if (destinationText.length() != 0 && !destinationText.endsWith(File.separator)) {
			int dot = destinationText.lastIndexOf('.');
			if (dot != -1) {
				// the last path separator index
				int idx = destinationText.lastIndexOf(File.separator);
				if (idx != -1 && dot < idx) {
					destinationText += idealSuffix;
				}
			} else {
				destinationText += idealSuffix;
			}
		}
		return destinationText;
	}

	protected String getOutputSuffix() {
		return Strings.DOT + PNG;
	}

	/**
	 * Creates a new button with the given id.
	 * <p>
	 * The {@code Dialog} implementation of this framework method creates a standard push button,
	 * registers for selection events including button presses and registers default buttons with its
	 * shell. The button id is stored as the buttons client data. Note that the parent's layout is
	 * assumed to be a GridLayout and the number of columns in this layout is incremented. Subclasses
	 * may override.
	 * 
	 *
	 * @param parent the parent composite
	 * @param id the id of the button (see {@code IDialogConstants.*_ID} constants for standard
	 *            dialog button ids)
	 * @param label the label from the button
	 * @param defaultButton {@code true} if the button is to be the default button, and
	 *            {@code false} otherwise
	 */
	protected Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		// increment the number of columns in the button bar
		((GridLayout) parent.getLayout()).numColumns++;

		Button button = new Button(parent, SWT.PUSH);
		button.setFont(parent.getFont());

		setButtonLayoutData(button);

		button.setData(Integer.valueOf(id));
		button.setText(label);

		if (defaultButton) {
			Shell shell = parent.getShell();
			if (shell != null) {
				shell.setDefaultButton(button);
			}
			button.setFocus();
		}
		return button;
	}

	/** Add the passed value to the destination widget's history */
	protected void addDestinationItem(String value) {
		pathnameCombo.add(value);
	}

	/**
	 * Validate the destination group.
	 *
	 * @return {@code true} if the group is valid. If not set the error message and return
	 *         {@code false}.
	 */
	protected boolean validateDestinationGroup() {
		if (!validDestination()) {
			currentMessage = "Invalid Destination";
			return false;
		}
		return true;
	}

	protected boolean validDestination() {
		File file = new File(getDestinationValue());
		return !(file.getPath().length() <= 0 || file.isDirectory());
	}

	protected boolean ensureDirectoryExists(File directory) {
		if (!directory.exists()) {
			if (!queryYesNoQuestion("Create Target Directory?")) return false;
			if (!directory.mkdirs()) {
				MessageDialog.open(MessageDialog.ERROR, getContainer().getShell(), "Error", "Directory Creation Error",
						SWT.SHEET);
				return false;
			}
		}
		return true;
	}

	/**
	 * Displays a Yes/No question to the user with the specified message and returns the user's
	 * response.
	 *
	 * @param message the question to ask
	 * @return {@code true} for Yes, and {@code false} for No
	 */
	protected boolean queryYesNoQuestion(String message) {
		MessageDialog dialog = new MessageDialog(getContainer().getShell(), "Question", (Image) null, message,
				MessageDialog.NONE, 0, IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL) {
			@Override
			protected int getShellStyle() {
				return super.getShellStyle() | SWT.SHEET;
			}
		};
		// ensure yes is the default
		return dialog.open() == 0;
	}

	/**
	 * If the target for export does not exist then attempt to create it. Answer a boolean indicating
	 * whether the target exists (ie.- if it either pre-existed or this method was able to create it)
	 *
	 * @return boolean
	 */
	protected boolean ensureTargetIsValid(File file) {
		if (file.exists()) {
			if (!overwriteExistingCheckbox.getSelection()) {
				String msg = String.format("Overwrite '%s'?", file.getAbsolutePath());
				if (!queryYesNoQuestion(msg)) return false;
			}
			file.delete();
		} else if (!file.isDirectory()) {
			File parent = file.getParentFile();
			if (parent != null) file.getParentFile().mkdirs();
		}
		return true;
	}

	protected void saveWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings.getArray(STORE_DESTINATION_NAMES_ID);
			if (directoryNames == null) {
				directoryNames = new String[0];
			}

			directoryNames = addToHistory(directoryNames, getDestinationValue());
			settings.put(STORE_DESTINATION_NAMES_ID, directoryNames);
			String current = getDestinationValue();
			if (current != null && !current.equals("")) { //$NON-NLS-1$
				settings.put(STORE_DESTINATION_ID, current);
			}

			if (overwriteExistingCheckbox != null) {
				settings.put(STORE_OVERWRITE_EXISTING_FILES_ID, overwriteExistingCheckbox.getSelection());
			}
		}
	}

	/**
	 * Returns whether this page is complete. This determination is made based upon the current contents
	 * of this page's controls. Subclasses wishing to include their controls in this determination
	 * should override the hook methods {@code validateSourceGroup} and/or
	 * {@code validateOptionsGroup}.
	 *
	 * @return {@code true} if this page is complete, and {@code false} if incomplete
	 * @see #validateSourceGroup
	 * @see #validateOptionsGroup
	 */
	protected boolean determinePageCompletion() {
		// validate groups in order of priority so error message is the most important one
		boolean complete = validateDestinationGroup();

		// Avoid draw flicker by not clearing the error message unless all is valid.
		if (complete) {
			setErrorMessage(null);
		} else {
			setErrorMessage(currentMessage);
		}
		return complete;
	}

	/**
	 * Set the current input focus to self's destination entry field
	 */
	protected void giveFocusToDestination() {
		pathnameCombo.setFocus();
	}

	/** Handle all events and enablements for widgets in this page */
	@Override
	public void handleEvent(Event e) {
		Widget source = e.widget;
		if (source == browseButton) handleDestinationBrowseButtonPressed();
		updatePageCompletion();
	}

	/**
	 * Open an appropriate destination browser so that the user can specify a source to import from
	 */
	protected void handleDestinationBrowseButtonPressed() {
		FileDialog dialog = new FileDialog(getContainer().getShell(), SWT.SAVE | SWT.SHEET);
		dialog.setText("Save Snapshot");
		dialog.setFilterPath(getDestinationValue());
		dialog.setFilterExtensions(new String[] { "*.png", "*.*" }); //$NON-NLS-1$ //$NON-NLS-2$
		String selectedFileName = dialog.open();

		if (selectedFileName != null) {
			setDestinationValue(selectedFileName);
		}
	}

	/**
	 * Determine if the page is complete and update the page appropriately.
	 */
	protected void updatePageCompletion() {
		boolean pageComplete = determinePageCompletion();
		setPageComplete(pageComplete);
		if (pageComplete) setMessage(null);
	}

	/**
	 * Adds an entry to a history, while taking care of duplicate history items and excessively long
	 * histories. The assumption is made that all histories should be of length
	 * {@code WizardDataTransferPage.COMBO_HISTORY_LENGTH}.
	 *
	 * @param history the current history
	 * @param newEntry the entry to add to the history
	 */
	protected String[] addToHistory(String[] history, String newEntry) {
		List<String> hist = new ArrayList<>(Arrays.asList(history));
		addToHistory(hist, newEntry);
		String[] r = new String[hist.size()];
		hist.toArray(r);
		return r;
	}

	/**
	 * Adds an entry to a history, while taking care of duplicate history items and excessively long
	 * histories. The assumption is made that all histories should be of length
	 * {@code WizardDataTransferPage.COMBO_HISTORY_LENGTH}.
	 *
	 * @param history the current history
	 * @param newEntry the entry to add to the history
	 */
	protected void addToHistory(List<String> history, String newEntry) {
		history.remove(newEntry);
		history.add(0, newEntry);
		if (history.size() > COMBO_HISTORY_LENGTH) {
			history.remove(COMBO_HISTORY_LENGTH);
		}
	}

	/**
	 * Hook method for restoring widget values to the values that they held last time this wizard was
	 * used to completion.
	 */
	protected void restoreWidgetValues() {
		IDialogSettings settings = getDialogSettings();
		if (settings != null) {
			String[] directoryNames = settings.getArray(STORE_DESTINATION_NAMES_ID);
			if (directoryNames != null) {
				// destination
				setDestinationValue(directoryNames[0]);
				for (String directoryName : directoryNames) {
					addDestinationItem(directoryName);
				}

				String current = settings.get(STORE_DESTINATION_ID);
				if (current != null) {
					setDestinationValue(current);
				}
				// options
				if (overwriteExistingCheckbox != null) {
					overwriteExistingCheckbox.setSelection(settings.getBoolean(STORE_OVERWRITE_EXISTING_FILES_ID));
				}
			}
		}
	}

	/** Set the contents of self's destination specification widget to the passed value */
	protected void setDestinationValue(String value) {
		pathnameCombo.setText(value);
	}

	/**
	 * The {@code WizardDataTransfer} implementation of this {@code IOverwriteQuery} method
	 * asks the user whether the existing resource at the given path should be overwritten.
	 *
	 * @param pathString
	 * @return the user's reply: one of {@code "YES"}, {@code "NO"}, {@code "ALL"}, or
	 *         {@code "CANCEL"}
	 */
	@Override
	public String queryOverwrite(String pathString) {
		String messageString = String.format("Overwrite existing '%s'?", pathString);
		final MessageDialog dialog = new MessageDialog(getContainer().getShell(), "Question", null, messageString,
				MessageDialog.QUESTION, 0, IDialogConstants.YES_LABEL, IDialogConstants.YES_TO_ALL_LABEL,
				IDialogConstants.NO_LABEL, IDialogConstants.NO_TO_ALL_LABEL, IDialogConstants.CANCEL_LABEL) {
			@Override
			protected int getShellStyle() {
				return super.getShellStyle() | SWT.SHEET;
			}
		};
		String[] response = new String[] { YES, ALL, NO, NO_ALL, CANCEL };
		// run in syncExec because callback is from an operation,
		// which is probably not running in the UI thread.
		getControl().getDisplay().syncExec(() -> dialog.open());
		return dialog.getReturnCode() < 0 ? CANCEL : response[dialog.getReturnCode()];
	}
}
