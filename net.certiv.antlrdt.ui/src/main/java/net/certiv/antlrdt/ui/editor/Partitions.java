package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.text.IDocument;

public class Partitions {

	public final static String PARTITIONING = "__antlrdt_partitioning";

	public static final String COMMENT_JD = "__comment_java_doc";
	public static final String COMMENT_ML = "__comment_multi_line";
	public static final String COMMENT_SL = "__comment_single_line";
	public static final String STRING = "__string";
	public static final String ACTION = "__action";

	// Action block partitions are defined by any balanced brace pair, excluding those
	// with these prefixes and postfixes:
	public static final String[] PREFIXES = new String[] { "options", "tokens", "channels" };
	public static final String[] PREDICATES = new String[] { "?" };

	/** Partition type groups by similar treatment */
	public static final String[] SPECIAL_TYPES = new String[] { COMMENT_JD, COMMENT_ML, COMMENT_SL, STRING, ACTION };
	public static final String[] STRING_AND_COMMENT_TYPES = new String[] { COMMENT_JD, COMMENT_ML, COMMENT_SL, STRING };
	public static final String[] COMMENT_TYPES = new String[] { COMMENT_JD, COMMENT_ML, COMMENT_SL };
	public static final String[] STRING_TYPES = new String[] { STRING };

	private Partitions() {}

	public static String[] getContentTypes() {
		return getContentTypes(null);
	}

	public static String[] getCommentContentTypes() {
		return COMMENT_TYPES;
	}

	public static String[] getStringContentTypes() {
		return STRING_TYPES;
	}

	public static String[] getAllContentTypes() {
		return getContentTypes(IDocument.DEFAULT_CONTENT_TYPE);
	}

	public static String[] getContentTypes(String type) {
		if (type != null) {
			String[] dest = new String[SPECIAL_TYPES.length + 1];
			dest[0] = type;
			System.arraycopy(SPECIAL_TYPES, 0, dest, 1, SPECIAL_TYPES.length);
			return dest;
		}
		return SPECIAL_TYPES;
	}
}
