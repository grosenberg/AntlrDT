package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.core.AntlrCore;
import net.certiv.antlrdt.core.formatter.AntlrDTSourceFormatter;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.editor.completion.AntlrCompletionProcessor;
import net.certiv.antlrdt.ui.editor.strategies.AntlrDTAutoEditDocStrategy;
import net.certiv.antlrdt.ui.editor.strategies.SmartAutoEditStrategy;
import net.certiv.antlrdt.ui.editor.text.ScannerAction;
import net.certiv.antlrdt.ui.editor.text.ScannerCommentJD;
import net.certiv.antlrdt.ui.editor.text.ScannerCommentML;
import net.certiv.antlrdt.ui.editor.text.ScannerCommentSL;
import net.certiv.antlrdt.ui.editor.text.ScannerKeyWord;
import net.certiv.antlrdt.ui.editor.text.ScannerString;
import net.certiv.antlrdt.ui.formatter.strategies.ActionCodeFormattingStrategy;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.IColorManager;
import net.certiv.dsl.core.preferences.DslPrefsManager;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.core.preferences.consts.Formatter;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.DoubleClickStrategy;
import net.certiv.dsl.ui.editor.DslPresentationReconciler;
import net.certiv.dsl.ui.editor.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.editor.reconcile.DslReconciler;
import net.certiv.dsl.ui.editor.text.completion.DslCompletionProcessor;
import net.certiv.dsl.ui.formatter.strategies.DslFormattingStrategy;

public class AntlrSourceViewerConfiguration extends DslSourceViewerConfiguration {

	private DoubleClickStrategy doubleClickStrategy;
	private ScannerCommentJD commentJDScanner;
	private ScannerCommentML commentMLScanner;
	private ScannerCommentSL commentSLScanner;
	private ScannerKeyWord keyScanner;
	private ScannerString stringScanner;
	private ScannerAction actionScanner;

	public AntlrSourceViewerConfiguration(IColorManager colorManager, IDslPrefsManager store, ITextEditor editor,
			String partitioning) {
		super(colorManager, store, editor, partitioning);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	private DslPrefsManager getPrefsMgr() {
		return getDslCore().getPrefsManager();
	}

	@Override
	protected void initializeScanners() {
		IDslPrefsManager store = getPrefStore();
		commentJDScanner = new ScannerCommentJD(store);
		commentMLScanner = new ScannerCommentML(store);
		commentSLScanner = new ScannerCommentSL(store);
		keyScanner = new ScannerKeyWord(store);
		stringScanner = new ScannerString(store);
		actionScanner = new ScannerAction(store);
	}

	@Override
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return Partitions.getAllContentTypes();
	}

	@Override
	public ITextDoubleClickStrategy getDoubleClickStrategy(ISourceViewer sourceViewer, String contentType) {
		if (doubleClickStrategy == null) {
			doubleClickStrategy = new DoubleClickStrategy();
		}
		return doubleClickStrategy;
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer sourceViewer) {
		PresentationReconciler reconciler = new DslPresentationReconciler();
		reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(sourceViewer));

		buildRepairer(reconciler, commentJDScanner, Partitions.COMMENT_JD);
		buildRepairer(reconciler, commentMLScanner, Partitions.COMMENT_ML);
		buildRepairer(reconciler, commentSLScanner, Partitions.COMMENT_SL);
		buildRepairer(reconciler, keyScanner, IDocument.DEFAULT_CONTENT_TYPE);
		buildRepairer(reconciler, stringScanner, Partitions.STRING);
		buildRepairer(reconciler, actionScanner, Partitions.ACTION);

		return reconciler;
	}

	@Override
	public void handlePropertyChangeEvent(PropertyChangeEvent event) {
		if (commentJDScanner.affectsBehavior(event)) commentJDScanner.adaptToPreferenceChange(event);
		if (commentMLScanner.affectsBehavior(event)) commentMLScanner.adaptToPreferenceChange(event);
		if (commentSLScanner.affectsBehavior(event)) commentSLScanner.adaptToPreferenceChange(event);
		if (keyScanner.affectsBehavior(event)) keyScanner.adaptToPreferenceChange(event);
		if (stringScanner.affectsBehavior(event)) stringScanner.adaptToPreferenceChange(event);
		if (actionScanner.affectsBehavior(event)) actionScanner.adaptToPreferenceChange(event);
	}

	@Override
	public boolean affectsTextPresentation(PropertyChangeEvent event) {
		return keyScanner.affectsBehavior(event) //
				|| actionScanner.affectsBehavior(event) //
				|| stringScanner.affectsBehavior(event) //
				|| commentJDScanner.affectsBehavior(event) //
				|| commentMLScanner.affectsBehavior(event) //
				|| commentSLScanner.affectsBehavior(event);
	}

	@Override
	public DslReconciler getReconciler(ISourceViewer viewer) {
		DslReconciler reconciler = super.getReconciler(viewer);

		AntlrReconcilingStrategy strategy = new AntlrReconcilingStrategy(getEditor(), viewer);
		reconciler.setReconcilingStrategy(strategy, IDocument.DEFAULT_CONTENT_TYPE);

		return reconciler;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer viewer, String contentType) {
		String partitioning = getConfiguredDocumentPartitioning(viewer);
		switch (contentType) {

			case Partitions.COMMENT_JD:
			case Partitions.COMMENT_ML:
				return new IAutoEditStrategy[] { new AntlrDTAutoEditDocStrategy(partitioning) };

			default:
				return new IAutoEditStrategy[] { new SmartAutoEditStrategy(partitioning) };
		}
	}

	@Override
	public IContentFormatter getContentFormatter(ISourceViewer viewer) {
		MultiPassContentFormatter formatter = (MultiPassContentFormatter) super.getContentFormatter(viewer);

		AntlrDTSourceFormatter srcFormatter = new AntlrDTSourceFormatter();
		formatter.setMasterStrategy(new DslFormattingStrategy(getPrefsMgr(), srcFormatter));

		if (getPrefsMgr().getBoolean(Formatter.FORMATTER_NATIVE_ENABLE)) {
			formatter.setSlaveStrategy(new ActionCodeFormattingStrategy(), Partitions.ACTION);
		}
		return formatter;
	}

	@Override
	protected void alterContentAssistant(ContentAssistant assistant) {
		DslCompletionProcessor processor = new AntlrCompletionProcessor(getEditor(), assistant,
				IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
	}

	@Override
	protected String getCommentPrefix() {
		return "//";
	}
}
