package net.certiv.antlrdt.ui.graph;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.forms.ManagedForm;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.ScrolledForm;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.zest.core.viewers.GraphViewer;

/** Create the form view */
public class ViewForm {

	private ScrolledForm form;
	private FormToolkit toolkit;
	private ManagedForm managedForm;
	private GraphViewer viewer;

	public ViewForm(Composite parent, FormToolkit toolkit) {
		this.toolkit = toolkit;
		form = this.toolkit.createScrolledForm(parent);
		managedForm = new ManagedForm(this.toolkit, this.form);
		// this.toolkit.decorateFormHeading(form.getForm());

		FillLayout layout = new FillLayout();
		layout.marginHeight = 2;
		layout.marginWidth = 2;
		form.getBody().setLayout(layout);
		createGraphSection(form.getBody());
	}

	private void createGraphSection(Composite parent) {
		Section section = this.toolkit.createSection(parent, ExpandableComposite.NO_TITLE);
		viewer = new DslGraphViewer(section, SWT.NONE);
		section.setClient(viewer.getControl());
	}

	public GraphViewer getGraphViewer() {
		return viewer;
	}

	public ScrolledForm getForm() {
		return form;
	}

	public ManagedForm getManagedForm() {
		return managedForm;
	}
}
