package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentExtension3;
import org.eclipse.jface.text.source.DefaultCharacterPairMatcher;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;

import net.certiv.antlrdt.core.AntlrDTCore;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.antlrdt.ui.editor.folding.AntlrDTFoldingStructureProvider;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.preferences.DslPrefsKey;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.editor.DslEditor;
import net.certiv.dsl.ui.text.folding.IFoldingStructureProvider;

public class AntlrDTEditor extends DslEditor {

	public static final String EDITOR_ID = "net.certiv.antlrdt.ui.editor.AntlrDTEditor";
	public static final String EDITOR_CONTEXT = "#AntlrDTEditorContext";
	public static final String RULER_CONTEXT = "#AntlrDTRulerContext";

	private static final String[] EDITOR_KEY_SCOPE = new String[] { "net.certiv.antlrdt.ui.antlrdtEditorScope" };
	private static final String MARK_OCCURRENCES_ANNOTATION_TYPE = "net.certiv.antlrdt.ui.occurrences";

	private DefaultCharacterPairMatcher pairMatcher = null;
	private IFoldingStructureProvider foldingProvider = null;

	public AntlrDTEditor() {
		super();
		// must init on construction
		pairMatcher = new DefaultCharacterPairMatcher(AntlrDTTextTools.PAIRS, Partitions.ANTLRDT_PARTITIONING);
	}

	@Override
	protected void initializeEditor() {
		super.initializeEditor();
		setEditorContextMenuId(EDITOR_CONTEXT);
		setRulerContextMenuId(RULER_CONTEXT);
	}

	@Override
	public String getEditorId() {
		return EDITOR_ID;
	}

	@Override
	public String getMarkOccurrencesAnnotationType() {
		return MARK_OCCURRENCES_ANNOTATION_TYPE;
	}

	// //////////////////////////////////////////////////////////////////////////

	@Override
	public DslUI getDslUI() {
		return AntlrDTUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrDTCore.getDefault();
	}

	@Override
	public char[] getBrackets() {
		return AntlrDTTextTools.PAIRS;
	}

	@Override
	protected IFoldingStructureProvider createFoldingStructureProvider() {
		if (foldingProvider == null) {
			foldingProvider = new AntlrDTFoldingStructureProvider();
		}
		return foldingProvider;
	}

	// /////////////////////////////////////////////////////////////////////////

	@Override
	protected AntlrDTOutlinePage doCreateOutlinePage() {
		return new AntlrDTOutlinePage(this, getPreferenceStore());
	}

	@Override
	protected void connectPartitioningToElement(IEditorInput input, IDocument document) {
		if (document instanceof IDocumentExtension3) {
			IDocumentExtension3 extension = (IDocumentExtension3) document;
			if (extension.getDocumentPartitioner(Partitions.ANTLRDT_PARTITIONING) == null) {
				AntlrDTDocumentSetupParticipant participant = new AntlrDTDocumentSetupParticipant();
				participant.setup(document);
			}
		}
	}

	// //////////////////////////////////////////////////////////////////////////

	@Override
	protected void initializeKeyBindingScopes() {
		setKeyBindingScopes(EDITOR_KEY_SCOPE);
	}

	@Override
	protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		support.setCharacterPairMatcher(pairMatcher);
		support.setMatchingCharacterPainterPreferenceKeys(bind(DslPrefsKey.EDITOR_MATCHING_BRACKETS),
				bind(DslPrefsKey.EDITOR_MATCHING_BRACKETS_COLOR));
		super.configureSourceViewerDecorationSupport(support);
	}
}
