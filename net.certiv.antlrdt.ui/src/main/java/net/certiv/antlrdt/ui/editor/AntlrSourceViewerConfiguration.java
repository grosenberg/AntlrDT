package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.TextAttribute;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.presentation.PresentationReconciler;
import org.eclipse.jface.text.rules.DefaultDamagerRepairer;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.ui.texteditor.ITextEditor;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.core.formatter.AntlrDTSourceFormatter;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.completion.AntlrCompletionProcessor;
import net.certiv.antlrdt.ui.editor.strategies.AntlrDTAutoEditDocStrategy;
import net.certiv.antlrdt.ui.editor.strategies.SmartAutoEditStrategy;
import net.certiv.antlrdt.ui.editor.strategies.SmartIndentStrategy;
import net.certiv.antlrdt.ui.editor.text.EnhDamagerRepairer;
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
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.core.util.eclipse.TabStyle;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.DoubleClickStrategy;
import net.certiv.dsl.ui.editor.DslPresentationReconciler;
import net.certiv.dsl.ui.editor.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.editor.reconcile.DslReconciler;
import net.certiv.dsl.ui.editor.scanners.AbstractBufferedRuleBasedScanner;
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
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
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
	public String[] getIndentPrefixes(ISourceViewer sourceViewer, String contentType) {
		if (getPrefsMgr().getTabStyle() == TabStyle.SPACES) {
			return new String[] { Strings.dup(getPrefsMgr().getIndentationSize(), Strings.SPC) };
		} else {
			return new String[] { "\t" };
		}
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

	protected void buildRepairer(PresentationReconciler reconciler, AbstractBufferedRuleBasedScanner scanner,
			String token, TextAttribute namedAttribute) {
		DefaultDamagerRepairer dr = new EnhDamagerRepairer(scanner, namedAttribute);
		reconciler.setDamager(dr, token);
		reconciler.setRepairer(dr, token);
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

	/**
	 * Determines whether the preference change encoded by the given event changes the behavior of one
	 * of its contained components.
	 *
	 * @param event the event to be investigated
	 * @return {@code true} if event causes a behavioral change
	 */
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

		AntlrReconcilingStrategy antlr = new AntlrReconcilingStrategy(getEditor(), viewer);
		reconciler.setReconcilingStrategy(antlr, IDocument.DEFAULT_CONTENT_TYPE);

		return reconciler;
	}

	@Override
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer viewer, String contentType) {
		String partitioning = getConfiguredDocumentPartitioning(viewer);
		IAutoEditStrategy[] strategies;
		switch (contentType) {
			case Partitions.COMMENT_JD:
			case Partitions.COMMENT_ML:
				strategies = new IAutoEditStrategy[] { new AntlrDTAutoEditDocStrategy(partitioning) };
				break;
			case Partitions.ACTION:
				strategies = new IAutoEditStrategy[] { new SmartIndentStrategy(partitioning, Partitions.ACTION) };
				break;
			case Partitions.STRING:
			default:
				strategies = new IAutoEditStrategy[] { new SmartAutoEditStrategy(partitioning) };
		}
		return strategies;
	}

	@Override
	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		MultiPassContentFormatter formatter = (MultiPassContentFormatter) super.getContentFormatter(sourceViewer);

		AntlrDTSourceFormatter srcFormatter = new AntlrDTSourceFormatter();
		formatter.setMasterStrategy(new DslFormattingStrategy(getPrefsMgr(), srcFormatter));

		if (getPrefsMgr().getBoolean(Formatter.FORMATTER_NATIVE_ENABLE)) {
			formatter.setSlaveStrategy(new ActionCodeFormattingStrategy(), Partitions.ACTION);
			// formatter.setSlaveStrategy(new GrammarCommentFormattingStrategy(), Partitions.COMMENT_JD);
			// formatter.setSlaveStrategy(new GrammarCommentFormattingStrategy(), Partitions.COMMENT_ML);
			// formatter.setSlaveStrategy(new GrammarCommentFormattingStrategy(), Partitions.COMMENT_SL);
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