package net.certiv.antlrdt.ui.view.tokens;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.SashForm;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;

import net.certiv.antlrdt.ui.view.providers.ErrorsViewerLabelProvider;
import net.certiv.antlrdt.ui.view.providers.ListViewerContentProvider;
import net.certiv.antlrdt.ui.view.providers.TokensViewerLabelProvider;

public class TokensBlock {

	private static final int flags = SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;
	private static final String[] tokTitles = { "Idx", "Token", "Line", "Col", "Text" };
	private static final int[] tokWidths = { 40, 100, 40, 40, 240 };
	private static final String[] errTitles = { "Idx", "Line", "Col", "Message" };
	private static final int[] errWidths = { 40, 40, 40, 340 };

	private TableViewer tokViewer;
	private TableViewer errViewer;

	public TokensBlock(TokensView view, Composite tokComp) {
		createTokensBlock(view, tokComp);
	}

	private void createTokensBlock(TokensView view, Composite parent) {

		SashForm sash = new SashForm(parent, SWT.VERTICAL | SWT.SMOOTH);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(sash);

		{ // tokens
			Group tokGroup = new Group(sash, SWT.NONE);
			GridDataFactory.fillDefaults().indent(0, 6).grab(true, true).span(1, 1).applyTo(tokGroup);
			GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(tokGroup);
			tokGroup.setText("Token Stream");

			tokViewer = new TableViewer(tokGroup, flags);
			tokViewer.setContentProvider(new ListViewerContentProvider());
			tokViewer.setLabelProvider(new TokensViewerLabelProvider());
			tokViewer.getTable().setHeaderVisible(true);
			tokViewer.getTable().setLinesVisible(true);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(tokViewer.getControl());

			tokViewer.getTable().addMouseListener(new MouseAdapter() {

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					Point pt = new Point(e.x, e.y);
					TableItem item = tokViewer.getTable().getItem(pt);
					String sIdx = item.getText(0);
					try {
						int idx = Integer.parseInt(sIdx);
						view.openSourceFileAtToken(idx);
					} catch (NumberFormatException e1) {}
				}
			});

			for (int i = 0; i < tokTitles.length; i++) {
				TableColumn col = new TableColumn(tokViewer.getTable(), SWT.NONE);
				col.setText(tokTitles[i]);
				col.setMoveable(false);
				col.setWidth(tokWidths[i]);
			}
		} // tokens

		{ // errors
			Group errGroup = new Group(sash, SWT.NONE);
			GridDataFactory.fillDefaults().indent(0, 6).grab(true, true).span(1, 1).applyTo(errGroup);
			GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(errGroup);
			errGroup.setText("Lex/Parse Errors");

			errViewer = new TableViewer(errGroup, flags);
			errViewer.setContentProvider(new ListViewerContentProvider());
			errViewer.setLabelProvider(new ErrorsViewerLabelProvider());
			errViewer.getTable().setHeaderVisible(true);
			errViewer.getTable().setLinesVisible(true);
			GridDataFactory.fillDefaults().grab(true, true).applyTo(errViewer.getControl());

			errViewer.getTable().addMouseListener(new MouseAdapter() {

				@Override
				public void mouseDoubleClick(MouseEvent e) {
					Point pt = new Point(e.x, e.y);
					TableItem item = errViewer.getTable().getItem(pt);
					String sIdx = item.getText(0);
					try {
						int idx = Integer.parseInt(sIdx);
						idx = idx >= 0 ? idx : 0;
						view.openSourceFileAtToken(idx);
					} catch (NumberFormatException e1) {}
				}
			});

			for (int i = 0; i < errTitles.length; i++) {
				TableColumn col = new TableColumn(errViewer.getTable(), SWT.NONE);
				col.setText(errTitles[i]);
				col.setMoveable(false);
				col.setWidth(errWidths[i]);
			}
		} // errors

		sash.setWeights(new int[] { 4, 1 });
	}

	public TableViewer getTokensViewer() {
		return tokViewer;
	}

	public TableViewer getErrorsViewer() {
		return errViewer;
	}

	public void clear() {
		tokViewer.setInput(null);
		errViewer.setInput(null);
	}
}
