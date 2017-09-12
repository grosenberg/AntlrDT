package net.certiv.antlrdt.ui.editor;

import org.eclipse.jdt.internal.ui.text.java.JavaAutoIndentStrategy;
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
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.completion.AntlrDTCompletionProcessor;
import net.certiv.antlrdt.ui.editor.strategies.AntlrDTAutoEditDocStrategy;
import net.certiv.antlrdt.ui.editor.strategies.AntlrDTAutoEditStrategy;
import net.certiv.antlrdt.ui.editor.strategies.AntlrDTAutoEditStringStrategy;
import net.certiv.antlrdt.ui.editor.text.EnhDamagerRepairer;
import net.certiv.antlrdt.ui.editor.text.ScannerAction;
import net.certiv.antlrdt.ui.editor.text.ScannerCommentJD;
import net.certiv.antlrdt.ui.editor.text.ScannerCommentML;
import net.certiv.antlrdt.ui.editor.text.ScannerCommentSL;
import net.certiv.antlrdt.ui.editor.text.ScannerKeyWord;
import net.certiv.antlrdt.ui.editor.text.ScannerString;
import net.certiv.antlrdt.ui.formatter.strategies.ActionCodeFormattingStrategy;
import net.certiv.antlrdt.ui.formatter.strategies.GrammarCommentFormattingStrategy;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.IColorManager;
import net.certiv.dsl.core.preferences.DslPrefsManager;
import net.certiv.dsl.core.preferences.IDslPrefsManager;
import net.certiv.dsl.core.preferences.consts.Formatter;
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.core.util.TabStyle;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.DoubleClickStrategy;
import net.certiv.dsl.ui.text.AbstractBufferedRuleBasedScanner;
import net.certiv.dsl.ui.text.DslPresentationReconciler;
import net.certiv.dsl.ui.text.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.text.completion.DslCompletionProcessor;

@SuppressWarnings("restriction")
public class AntlrDTSourceViewerConfiguration extends DslSourceViewerConfiguration {

	private DoubleClickStrategy doubleClickStrategy;
	private ScannerCommentJD commentJDScanner;
	private ScannerCommentML commentMLScanner;
	private ScannerCommentSL commentSLScanner;
	private ScannerKeyWord keyScanner;
	private ScannerString stringScanner;
	private ScannerAction actionScanner;

	public AntlrDTSourceViewerConfiguration(IColorManager colorManager, IDslPrefsManager store, ITextEditor editor,
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
	public String[] getConfiguredContentTypes(ISourceViewer sourceViewer) {
		return Partitions.getContentTypes(IDocument.DEFAULT_CONTENT_TYPE);
	}

	/**
	 * Loads content formatters into the SourceViewer for execution on receipt of a ISourceViewer.FORMAT
	 * command.
	 * <p>
	 * The master strategy utilizes the DSL formatter tree grammar to drive formatting of the default
	 * partition. The slave strategies are executed to format particular non-default partitions.
	 * </p>
	 * <p>
	 * Two built-in non-default partition strategies are provided:
	 * <code>CommentFormattingStrategy()</code> and <code>JavaFormattingStrategy()</code> that use the
	 * JDT formatter and global JDT formatting preferences. The comment strategy can format stand-alone
	 * single-line, mutiple-line, and JavaDoc-style comments. The JavaCode strategy can format discrete
	 * blocks of otherwise standard Java code, including embedded comments.
	 * </p>
	 *
	 * @param sourceViewer
	 *            the viewer that will contain the content to format
	 * @return the content formatter
	 */
	@Override
	public IContentFormatter getContentFormatter(ISourceViewer sourceViewer) {
		MultiPassContentFormatter formatter = (MultiPassContentFormatter) super.getContentFormatter(sourceViewer);
		if (getPrefsMgr().getBoolean(Formatter.FORMATTER_NATIVE_ENABLE)) {
			formatter.setSlaveStrategy(new ActionCodeFormattingStrategy(), Partitions.ACTION);
			formatter.setSlaveStrategy(new GrammarCommentFormattingStrategy(), Partitions.COMMENT_JD);
			formatter.setSlaveStrategy(new GrammarCommentFormattingStrategy(), Partitions.COMMENT_ML);
			formatter.setSlaveStrategy(new GrammarCommentFormattingStrategy(), Partitions.COMMENT_SL);
		}
		return formatter;
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
			return new String[] { Strings.getNSpaces(getPrefsMgr().getIndentationSize()) };
		} else {
			return new String[] { "\t" };
		}
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

	/**
	 * Adapts the behavior of the contained components to the change encoded in the given event.
	 *
	 * @param event
	 *            the event to which to adapt
	 */
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
	 * @param event
	 *            the event to be investigated
	 * @return <code>true</code> if event causes a behavioral change
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
	public IAutoEditStrategy[] getAutoEditStrategies(ISourceViewer viewer, String contentType) {
		String partitioning = getConfiguredDocumentPartitioning(viewer);
		IAutoEditStrategy strategy;
		switch (contentType) {
			case Partitions.COMMENT_JD:
			case Partitions.COMMENT_ML:
				strategy = new AntlrDTAutoEditDocStrategy(partitioning);
				break;
			case Partitions.STRING:
				strategy = new AntlrDTAutoEditStringStrategy(partitioning);
				break;
			case Partitions.ACTION:
				strategy = new JavaAutoIndentStrategy(partitioning, null, viewer);
				break;
			default:
				strategy = new AntlrDTAutoEditStrategy(partitioning);
		}
		return new IAutoEditStrategy[] { strategy };
	}

	@Override
	protected void alterContentAssistant(ContentAssistant assistant) {
		DslCompletionProcessor processor = new AntlrDTCompletionProcessor(getEditor(), assistant,
				IDocument.DEFAULT_CONTENT_TYPE);
		assistant.setContentAssistProcessor(processor, IDocument.DEFAULT_CONTENT_TYPE);
	}

	// @Override
	// public IHyperlinkDetector getDslElementHyperlinkDetector(ITextEditor textEditor) {
	// return new AntlrDTHyperlinkDetector(textEditor);
	// }

	@Override
	protected String getCommentPrefix() {
		return "//";
	}

	// protected void initializeQuickOutlineContexts(InformationPresenter
	// presenter,
	// IInformationProvider provider) {
	// presenter.setInformationProvider(provider, Partitions.COMMENT_JD);
	// presenter.setInformationProvider(provider, Partitions.COMMENT_ML);
	// presenter.setInformationProvider(provider, Partitions.ACTION);
	// }
	//
	// @Override
	// protected void alterContentAssistant(ContentAssistant assistant) {
	// DslCompletionProcessor processor;
	// processor = new AntlrDTCompletionProcessor(getEditor(), assistant,
	// IDocument.DEFAULT_CONTENT_TYPE);
	// assistant.setContentAssistProcessor(processor,
	// IDocument.DEFAULT_CONTENT_TYPE);
	// processor = new AntlrDTCompletionProcessor(getEditor(), assistant,
	// Partitions.ACTION);
	// assistant.setContentAssistProcessor(processor, Partitions.ACTION);
	// processor = new AntlrDTCompletionProcessor(getEditor(), assistant,
	// Partitions.COMMENT_JD);
	// assistant.setContentAssistProcessor(processor, Partitions.COMMENT_JD);
	// }
}
