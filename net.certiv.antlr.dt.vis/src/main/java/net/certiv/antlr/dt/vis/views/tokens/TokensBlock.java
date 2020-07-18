package net.certiv.antlr.dt.vis.views.tokens;

import java.util.List;

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

public class TokensBlock {

	private static final int flags = SWT.SINGLE | SWT.FULL_SELECTION | SWT.H_SCROLL | SWT.V_SCROLL;

	private static final String[] TokTitles = { "Idx", "Token", "Line", "Col", "Mode", "Text" };
	private static final int[] TokWidths = { 40, 100, 40, 40, 60, 300 };

	private static final String[] ProblemTitles = { "Idx", "Line", "Col", "Message" };
	private static final int[] ProblemWidths = { 40, 40, 40, 450 };

	private SashForm sash;
	private TableViewer tokViewer;
	private TableViewer prbViewer;

	public TokensBlock(TokensView view, Composite comp) {
		sash = new SashForm(comp, SWT.VERTICAL | SWT.SMOOTH);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(sash);

		tokViewer = tokens(view, comp, sash);
		prbViewer = problems(view, comp, sash);

		sash.setWeights(new int[] { 4, 2 });
	}

	private TableViewer tokens(TokensView view, Composite parent, Composite sash) {
		Group tokGroup = new Group(sash, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, true).span(1, 1).applyTo(tokGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(tokGroup);
		tokGroup.setText("Token Stream");

		TableViewer viewer = new TableViewer(tokGroup, flags);
		viewer.setContentProvider(new TokensViewContentProvider());
		viewer.setLabelProvider(new TokensLabelProvider(parent.getDisplay()));
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());

		viewer.getTable().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Point pt = new Point(e.x, e.y);
				TableItem item = viewer.getTable().getItem(pt);
				try {
					int num = Math.max(0, Integer.parseInt(item.getText(0)));
					view.openSourceFileAtToken(num);
				} catch (NumberFormatException nfe) {}
			}
		});

		for (int idx = 0; idx < TokTitles.length; idx++) {
			TableColumn col = new TableColumn(viewer.getTable(), SWT.NONE);
			col.setText(TokTitles[idx]);
			col.setMoveable(false);
			col.setWidth(TokWidths[idx]);
		}
		return viewer;
	}

	private TableViewer problems(TokensView view, Composite parent, Composite sash) {
		Group errGroup = new Group(sash, SWT.NONE);
		GridDataFactory.fillDefaults().indent(0, 6).grab(true, true).span(1, 1).applyTo(errGroup);
		GridLayoutFactory.fillDefaults().margins(6, 6).applyTo(errGroup);
		errGroup.setText("Lex/Parse Errors");

		TableViewer viewer = new TableViewer(errGroup, flags);
		viewer.setContentProvider(new TokensViewContentProvider());
		viewer.setLabelProvider(new ProblemsLabelProvider(parent.getDisplay()));
		viewer.getTable().setHeaderVisible(true);
		viewer.getTable().setLinesVisible(true);
		GridDataFactory.fillDefaults().grab(true, true).applyTo(viewer.getControl());

		viewer.getTable().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				Point pt = new Point(e.x, e.y);
				TableItem item = viewer.getTable().getItem(pt);
				try {
					int num = Math.max(0, Integer.parseInt(item.getText(0)));
					view.openSourceFileAtToken(num);
				} catch (NumberFormatException nfe) {}
			}
		});

		for (int idx = 0; idx < ProblemTitles.length; idx++) {
			TableColumn col = new TableColumn(viewer.getTable(), SWT.NONE);
			col.setText(ProblemTitles[idx]);
			col.setMoveable(false);
			col.setWidth(ProblemWidths[idx]);
		}
		return viewer;
	}

	public void setSashWeights(int[] weights) {
		sash.setWeights(weights);
	}

	public void setTokensInput(List<String[]> data) {
		tokViewer.setInput(data);
	}

	public void setProblemsInput(List<String[]> data) {
		prbViewer.setInput(data);
	}

	public TableViewer getTokensViewer() {
		return tokViewer;
	}

	public TableViewer getProblemsViewer() {
		return prbViewer;
	}

	public void clear() {
		tokViewer.setInput(null);
		prbViewer.setInput(null);
	}
}
