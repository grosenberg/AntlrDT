package net.certiv.antlr.dt.ui.editor;

import static net.certiv.dsl.ui.editor.text.completion.engines.IPrefixStops.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.jface.text.IAutoEditStrategy;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.ITextDoubleClickStrategy;
import org.eclipse.jface.text.contentassist.ContentAssistant;
import org.eclipse.jface.text.formatter.IContentFormatter;
import org.eclipse.jface.text.formatter.MultiPassContentFormatter;
import org.eclipse.jface.text.presentation.IPresentationReconciler;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.jface.util.PropertyChangeEvent;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.core.formatter.AntlrSourceFormatter;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.editor.outline.AntlrStatementLabelProvider;
import net.certiv.antlr.dt.ui.editor.strategies.AntlrDTAutoEditDocStrategy;
import net.certiv.antlr.dt.ui.editor.strategies.SmartAutoEditStrategy;
import net.certiv.antlr.dt.ui.editor.text.ScannerAction;
import net.certiv.antlr.dt.ui.editor.text.ScannerCommentJD;
import net.certiv.antlr.dt.ui.editor.text.ScannerCommentML;
import net.certiv.antlr.dt.ui.editor.text.ScannerCommentSL;
import net.certiv.antlr.dt.ui.editor.text.ScannerKeyword;
import net.certiv.antlr.dt.ui.editor.text.ScannerString;
import net.certiv.antlr.dt.ui.formatter.strategies.ActionCodeFormattingStrategy;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.IColorManager;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.core.preferences.IPrefsManager;
import net.certiv.dsl.core.preferences.consts.Formatter;
import net.certiv.dsl.ui.DslImageManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.DoubleClickStrategy;
import net.certiv.dsl.ui.editor.DslEditor;
import net.certiv.dsl.ui.editor.DslSourceViewerConfiguration;
import net.certiv.dsl.ui.editor.reconcile.PresentationReconciler;
import net.certiv.dsl.ui.editor.text.completion.CompletionCategory;
import net.certiv.dsl.ui.editor.text.completion.CompletionProcessor;
import net.certiv.dsl.ui.editor.text.completion.engines.FieldEngine;
import net.certiv.dsl.ui.editor.text.completion.engines.ICompletionEngine;
import net.certiv.dsl.ui.editor.text.completion.engines.KeywordEngine;
import net.certiv.dsl.ui.editor.text.completion.engines.TemplateEngine;
import net.certiv.dsl.ui.formatter.strategies.DslFormattingStrategy;

public class AntlrSourceViewerConfiguration extends DslSourceViewerConfiguration {

	private DoubleClickStrategy doubleClickStrategy;

	private AntlrSemanaticAnalyzer grammarAnalyzer;

	private ScannerCommentJD commentJDScanner;
	private ScannerCommentML commentMLScanner;
	private ScannerCommentSL commentSLScanner;
	private ScannerKeyword keywordScanner;
	private ScannerString stringScanner;
	private ScannerAction actionScanner;

	public AntlrSourceViewerConfiguration(IColorManager colorMgr, IPrefsManager store, DslEditor editor,
			String partitioning) {
		super(AntlrCore.getDefault(), colorMgr, store, editor, partitioning);
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	private PrefsManager getPrefsMgr() {
		return getDslCore().getPrefsManager();
	}

	@Override
	protected void initializeScanners() {
		IPrefsManager store = getPrefStore();
		commentJDScanner = new ScannerCommentJD(store);
		commentMLScanner = new ScannerCommentML(store);
		commentSLScanner = new ScannerCommentSL(store);
		keywordScanner = new ScannerKeyword(store);
		stringScanner = new ScannerString(store);
		actionScanner = new ScannerAction(store);

		grammarAnalyzer = new AntlrSemanaticAnalyzer(getDslUI());
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
	public void specializeContentAssistant(ContentAssistant assistant) {
		AntlrStatementLabelProvider provider = new AntlrStatementLabelProvider();
		DslImageManager imgMgr = getDslUI().getImageManager();
		Image image = imgMgr.get(imgMgr.IMG_OBJS_KEYWORD);
		Set<Character> stops = new HashSet<>(Arrays.asList(LBRACE, LBRACE, LPAREN, COLON, COMMA, SEMI, PIPE, AT));
		// Set<AntlrContextType> types =
		// AntlrContextType.create(AntlrContextType.GRAMMAR_ID,
		// AntlrContextType.OPTIONS_ID);

		ICompletionEngine keywords = new KeywordEngine(image, stops, ScannerKeyword.KEYWORDS);
		ICompletionEngine fields = new FieldEngine(provider, stops);
		ICompletionEngine templates = new TemplateEngine(provider, stops/* , types */);

		CompletionCategory lang = new CompletionCategory("Antlr", true, false, keywords, fields);
		CompletionCategory tmpl = new CompletionCategory("Antlr Templates", false, true, templates);
		CompletionProcessor proc = new CompletionProcessor(getDslUI(), assistant, lang, tmpl);
		assistant.setContentAssistProcessor(proc, IDocument.DEFAULT_CONTENT_TYPE);
	}

	@Override
	public void handlePropertyChangeEvent(PropertyChangeEvent event) {
		if (keywordScanner.affectsBehavior(event)) keywordScanner.adaptToPreferenceChange(event);
		if (grammarAnalyzer.affectsBehavior(event)) grammarAnalyzer.adaptToPreferenceChange(event);

		if (commentJDScanner.affectsBehavior(event)) commentJDScanner.adaptToPreferenceChange(event);
		if (commentMLScanner.affectsBehavior(event)) commentMLScanner.adaptToPreferenceChange(event);
		if (commentSLScanner.affectsBehavior(event)) commentSLScanner.adaptToPreferenceChange(event);
		if (stringScanner.affectsBehavior(event)) stringScanner.adaptToPreferenceChange(event);
		if (actionScanner.affectsBehavior(event)) actionScanner.adaptToPreferenceChange(event);
	}

	@Override
	public boolean affectsTextPresentation(PropertyChangeEvent event) {
		return keywordScanner.affectsBehavior(event) //
				|| grammarAnalyzer.affectsBehavior(event) //
				|| actionScanner.affectsBehavior(event) //
				|| stringScanner.affectsBehavior(event) //
				|| commentJDScanner.affectsBehavior(event) //
				|| commentMLScanner.affectsBehavior(event) //
				|| commentSLScanner.affectsBehavior(event);
	}

	@Override
	public IPresentationReconciler getPresentationReconciler(ISourceViewer viewer) {
		PresentationReconciler reconciler = new PresentationReconciler(getDslUI());
		reconciler.setDocumentPartitioning(getConfiguredDocumentPartitioning(viewer));

		buildRepairer(getEditor(), viewer, reconciler, grammarAnalyzer, IDocument.DEFAULT_CONTENT_TYPE);
		buildRepairer(reconciler, keywordScanner, IDocument.DEFAULT_CONTENT_TYPE);
		buildRepairer(reconciler, commentJDScanner, Partitions.COMMENT_JD);
		buildRepairer(reconciler, commentMLScanner, Partitions.COMMENT_ML);
		buildRepairer(reconciler, commentSLScanner, Partitions.COMMENT_SL);
		buildRepairer(reconciler, stringScanner, Partitions.STRING);
		buildRepairer(reconciler, actionScanner, Partitions.ACTION);

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

		AntlrSourceFormatter srcFormatter = new AntlrSourceFormatter();
		formatter.setMasterStrategy(new DslFormattingStrategy(getPrefsMgr(), srcFormatter));

		if (getPrefsMgr().getBoolean(Formatter.FORMATTER_NATIVE_ENABLE)) {
			formatter.setSlaveStrategy(new ActionCodeFormattingStrategy(), Partitions.ACTION);
		}
		return formatter;
	}

	@Override
	protected String getCommentPrefix() {
		return "//";
	}
}
