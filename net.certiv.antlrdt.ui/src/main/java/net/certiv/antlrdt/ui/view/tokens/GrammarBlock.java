package net.certiv.antlrdt.ui.view.tokens;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Text;

public class GrammarBlock {

	private Text text;
	private ControlDecoration decorator;

	public GrammarBlock(TokensView view, Composite comp) {
		createGrammarBlock(view, comp);
	}

	private void createGrammarBlock(TokensView view, Composite parent) {

		Group srcGroup = new Group(parent, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, false).span(1, 1).applyTo(srcGroup);
		GridLayoutFactory.fillDefaults().numColumns(2).margins(4, 4).spacing(TokensView.hSp, TokensView.vSp)
				.applyTo(srcGroup);
		srcGroup.setText("Grammar");

		text = new Text(srcGroup, SWT.SINGLE | SWT.READ_ONLY | SWT.BORDER);
		decorator = view.createDecoration(text);
		GridDataFactory.fillDefaults().indent(6, 0).align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(text);

		Button buttonSelect = new Button(srcGroup, SWT.PUSH);
		buttonSelect.setText(" Integration ");
		GridDataFactory.fillDefaults().hint(80, SWT.DEFAULT).applyTo(buttonSelect);
		buttonSelect.addSelectionListener(new SelectionAdapter() {

			@Override
			public void widgetSelected(SelectionEvent e) {
				view.execGrammarDialogHandler();
			}
		});
	}

	public Text getTextControl() {
		return text;
	}

	/** Set text; return true if content changed */
	public boolean setText(String value) {
		String prior = text.getText();
		text.setText(value);
		return !prior.equals(value);
	}

	public ControlDecoration getDecorator() {
		return decorator;
	}
}
