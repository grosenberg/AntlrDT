package net.certiv.antlrdt.vis.actions;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.ui.IEditorPart;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.antlrdt.ui.editor.AntlrDTEditor;
import net.certiv.antlrdt.vis.views.IAdjustableViewPart;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.model.IStatement;
import net.certiv.dsl.core.model.Statement;
import net.certiv.dsl.core.preferences.DslPrefsManager;
import net.certiv.dsl.core.util.CoreUtil;

public class LinkControlItem extends ControlContribution {

	public static final String ID = "net.certiv.antlrdt.vis.actions.LinkControlItem";

	private final IAdjustableViewPart view;
	private final String linkKey;

	public LinkControlItem(IAdjustableViewPart view) {
		super(ID);
		this.view = view;

		linkKey = view.getId() + ".linking";
	}

	@Override
	protected Control createControl(Composite parent) {
		final DslPrefsManager store = AntlrCore.getDefault().getPrefsManager();
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();

		Button link = new Button(parent, SWT.TOGGLE);
		link.setImage(imgMgr.get(imgMgr.IMG_LCL_SYNC));
		link.setToolTipText("Link with editor");
		link.setSelection(store.getBoolean(linkKey));
		link.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean linking = link.getSelection();
				store.setValue(linkKey, linking);

				if (linking) {
					IEditorPart part = CoreUtil.getActiveEditor();
					if (part instanceof AntlrDTEditor) {
						AntlrDTEditor editor = (AntlrDTEditor) part;
						ISelection selection = editor.getSelectionProvider().getSelection();

						if (selection instanceof StructuredSelection) {
							for (Object elem : ((IStructuredSelection) selection).toList()) {
								if (elem instanceof Statement) {
									IStatement statement = (IStatement) elem;
									ICodeUnit unit = statement.getCodeUnit();
									view.select(unit, statement);
									return;
								}
							}
						}
					}
				}
			}
		});

		return link;
	}
}
