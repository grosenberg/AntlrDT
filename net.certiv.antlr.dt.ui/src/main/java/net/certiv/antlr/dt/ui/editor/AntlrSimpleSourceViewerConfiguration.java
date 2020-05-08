package net.certiv.antlr.dt.ui.editor;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.hyperlink.IHyperlinkDetector;
import org.eclipse.jface.text.information.IInformationPresenter;
import org.eclipse.jface.text.source.IAnnotationHover;
import org.eclipse.jface.text.source.ISourceViewer;

import net.certiv.dsl.core.color.IColorManager;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.ui.editor.DslEditor;

public class AntlrSimpleSourceViewerConfiguration extends AntlrSourceViewerConfiguration {

	public AntlrSimpleSourceViewerConfiguration(IPrefsManager store, DslEditor editor, String partitioning) {
		super(null, store, editor, partitioning);
	}

	public AntlrSimpleSourceViewerConfiguration(IColorManager colorMgr, IPrefsManager store, DslEditor editor,
			String partitioning, boolean configureFormatter) {
		super(colorMgr, store, editor, partitioning);
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer viewer, String contentType) {
		return null;
	}

	@Override
	public IAnnotationHover getAnnotationHover(ISourceViewer viewer) {
		return null;
	}

	@Override
	public IAnnotationHover getOverviewRulerAnnotationHover(ISourceViewer viewer) {
		return null;
	}

	@Override
	public int[] getConfiguredTextHoverStateMasks(ISourceViewer viewer, String contentType) {
		return null;
	}

	@Override
	public ITextHover getTextHover(ISourceViewer viewer, String contentType, int stateMask) {
		return null;
	}

	@Override
	public ITextHover getTextHover(ISourceViewer viewer, String contentType) {
		return null;
	}

	@Override
	public void specializeContentAssistant(ContentAssistant assistant) {}

	@Override
	public IContentFormatter getContentFormatter(ISourceViewer viewer) {
		return null;
	}

	@Override
	public IInformationControlCreator getInformationControlCreator(ISourceViewer viewer) {
		return null;
	}

	@Override
	public IInformationPresenter getInformationPresenter(ISourceViewer viewer) {
		return null;
	}

	public IInformationPresenter getOutlinePresenter(ISourceViewer viewer, boolean doCodeResolve) {
		return null;
	}

	public IInformationPresenter getHierarchyPresenter(ISourceViewer viewer, boolean doCodeResolve) {
		return null;
	}

	@Override
	public IHyperlinkDetector[] getHyperlinkDetectors(ISourceViewer viewer) {
		return null;
	}
}
