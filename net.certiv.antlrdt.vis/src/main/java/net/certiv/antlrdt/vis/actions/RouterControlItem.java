package net.certiv.antlrdt.vis.actions;

import org.eclipse.jface.action.ControlContribution;
import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.nebula.widgets.tablecombo.TableCombo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableItem;

import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.antlrdt.vis.views.IAdjustableViewPart;

public class RouterControlItem extends ControlContribution {

	public static final String ID = "net.certiv.antlrdt.vis.actions.RouterControlItem";

	private IAdjustableViewPart view;
	private TableCombo combo;

	public RouterControlItem(IAdjustableViewPart view) {
		super(ID);
		this.view = view;
	}

	@Override
	protected Control createControl(Composite parent) {
		combo = new TableCombo(parent, SWT.READ_ONLY | SWT.BORDER | SWT.SINGLE);
		Table table = combo.getTable();
		table.setLayoutData(new GridData(240, SWT.DEFAULT));
		ImageManager imgMgr = AntlrUI.getDefault().getImageManager();
		for (Router layout : Router.values()) {
			TableItem row = new TableItem(table, SWT.NONE);
			row.setImage(0, imgMgr.get(layout.getImageDescriptor()));
			row.setText(layout.getDisplayName());
		}

		combo.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				int idx = combo.getSelectionIndex();
				TableItem row = combo.getTable().getItem(idx);
				Router router = Router.getEnum(row.getText());
				if (router == null) router = Router.FAN;
				view.setRouter(router);
			}
		});

		IDialogSettings settings = AntlrUI.getDefault().getDialogSettings();
		IDialogSettings section = settings.getSection(AntlrUI.PA_DIALOG_SEC);
		String router = section.get(IAdjustableViewPart.KEY_ROUTER);
		combo.setText(router);

		return combo;
	}
}
