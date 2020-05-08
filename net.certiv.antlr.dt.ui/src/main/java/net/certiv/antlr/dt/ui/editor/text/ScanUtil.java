package net.certiv.antlr.dt.ui.editor.text;

import org.eclipse.jface.text.rules.ICharacterScanner;

import net.certiv.dsl.core.log.Log;
import net.certiv.dsl.ui.editor.scanners.IPrefixRuleBasedScanner;

/** TODO: need to refactor */
public class ScanUtil {

	private static final WhitespaceDetector ws = new WhitespaceDetector();

	/**
	 * Determines whether the current character stream matches the given character
	 * sequence followed by 0 or more white space characters and an open brace.
	 *
	 * @param scanner the current character steam
	 * @param sequence the sequence to match
	 * @return true if the sequence matches; false otherwise
	 */
	public static boolean matchSeqWsOpenBrace(ICharacterScanner scanner, char[] sequence) {
		if (matchSequence(scanner, sequence) && matchWsOpenBrace(scanner)) {
			return true;
		}
		return false;
	}

	/**
	 * Determines whether the current character stream matches 0 or more white space
	 * characters followed by an open brace character.
	 *
	 * @param scanner the current character stream
	 * @return true if the pattern matches
	 */
	public static boolean matchWsOpenBrace(ICharacterScanner scanner) {
		int c;
		int idx = 1;
		do {
			c = scanner.read();
			if (c == '{') return true;
			if (!ws.isWhitespace((char) c)) break;
			idx++;
		} while (c != ICharacterScanner.EOF);
		// must be EOF, non-whitespace, or not an open brace
		scanner.unread();
		for (int jdx = idx - 1; jdx > 0; jdx--) {
			scanner.unread();
		}
		return false;
	}

	/**
	 * Determines whether the current character stream matches the given sequence. The
	 * character stream is rewound only on a match failure.
	 *
	 * @param scanner the current character steam
	 * @param sequence the sequence to match
	 * @return true if the sequence matches; false otherwise
	 */
	public static boolean matchSequence(ICharacterScanner scanner, char[] sequence) {
		char style = sequence[sequence.length - 1];
		if (style == '*') {
			return matchSequence(scanner, sequence, false) && matchWord(scanner, sequence, false);
		} else if (style == '[') {
			return matchSequence(scanner, sequence, false) && matchParam(scanner, sequence, false);
		} else {
			return matchSequence(scanner, sequence, false);
		}
	}

	public static boolean matchWord(ICharacterScanner scanner, char[] sequence, boolean rewind) {
		int c;
		int count = 0;

		do { // consume whitespace
			c = scanner.read();
			count++;
			if (!ws.isWhitespace((char) c)) {
				scanner.unread();
				count--;
				break;
			}
		} while (c != ICharacterScanner.EOF);
		if (c == ICharacterScanner.EOF) {
			for (int idx = count; idx > 0; idx--) {
				scanner.unread();
			}
			return false;
		}

		do { // consume non-whitespace
			c = scanner.read();
			count++;
			if (ws.isWhitespace((char) c)) {
				scanner.unread();
				count--;
				break;
			}
		} while (c != ICharacterScanner.EOF);
		if (c == ICharacterScanner.EOF) {
			for (int idx = count; idx > 0; idx--) {
				scanner.unread();
			}
			return false;
		}

		if (rewind) {
			for (int jdx = count; jdx > 0; jdx--) {
				scanner.unread();
			}
		}
		return true;
	}

	public static boolean matchParam(ICharacterScanner scanner, char[] sequence, boolean rewind) {
		int c;
		int count = 0;

		do { // consume whitespace
			c = scanner.read();
			count++;
			if (!ws.isWhitespace((char) c)) {
				scanner.unread();
				count--;
				break;
			}
		} while (c != ICharacterScanner.EOF);
		if (c == ICharacterScanner.EOF) {
			for (int idx = count; idx > 0; idx--) {
				scanner.unread();
			}
			return false;
		}

		do { // consume to end bracket
			c = scanner.read();
			count++;
			if (c == ']') {
				break;
			}
		} while (c != ICharacterScanner.EOF);
		if (c == ICharacterScanner.EOF) {
			for (int idx = count; idx > 0; idx--) {
				scanner.unread();
			}
			return false;
		}

		if (rewind) {
			for (int jdx = count; jdx > 0; jdx--) {
				scanner.unread();
			}
		}
		return true;
	}

	/**
	 * Determines whether the current character stream matches the given sequence. The
	 * character stream is rewound even on match if rewind is true.
	 *
	 * @param scanner the current character steam
	 * @param sequence the sequence to match
	 * @param rewind whether to rewind on match as well as on non-match
	 * @return true if the sequence matches; false otherwise
	 */
	public static boolean matchSequence(ICharacterScanner scanner, char[] sequence, boolean rewind) {
		// String pm = getPossibleMatch(scanner, sequence.length);

		// if (debug2) {
		// char x = (char) scanner.read();
		// if (x == '\r' || x == '\n') {
		// Log.trace(ScanUtil.class, "Examine [scan=" + "" + ", seq=" +
		// String.copyValueOf(sequence)
		// + "]");
		// } else {
		// Log.trace(ScanUtil.class, "Examine [scan=" + x + ", seq=" +
		// String.copyValueOf(sequence)
		// + "]");
		// }
		// scanner.unread();
		// }

		int idx = 0;
		do {
			int c = scanner.read();
			Log.trace(ScanUtil.class, "Examine [c=" + (char) c + ", seq=" + sequence[idx] + "]");
			if (c == ICharacterScanner.EOF) {
				return false;
			} else if (c != sequence[idx]) {
				Log.trace(ScanUtil.class, "Non-match [c=" + (char) c + ", seq=" + sequence[idx] + "]");
				scanner.unread();
				for (int jdx = idx; jdx > 0; jdx--) {
					scanner.unread();
				}
				return false;
			}
			idx++;
		} while (idx < sequence.length);
		if (rewind) {
			scanner.unread();
			for (int jdx = idx; jdx > 0; jdx--) {
				scanner.unread();
			}
		}
		return true;
	}

	/**
	 * Determines if any of the given Pattern sequences are a prefix to an open brace at
	 * the current document position. The character sequences are considered qualifying,
	 * so true is returned on first found.
	 *
	 * @param scanner the current character steam
	 * @param sequences array of strings to match
	 * @return true if one of the sequences match; false otherwise
	 */
	public static boolean matchSequenceWsOpenBrace(ICharacterScanner scanner, String[] sequences) {
		int c = scanner.read();
		if (c == '{') {
			String prefix = ((IPrefixRuleBasedScanner) scanner).getPrefix();
			for (String sequence : sequences) {
				if (prefix.equals(sequence)) return false;
			}
			return true; // no disqualifying prefix found
		}
		// no initial brace found
		scanner.unread();
		return false;
	}

	/**
	 * First finds the balanced close brace in the character stream. Then determines if
	 * any of the given character sequences follow 0 or more white space characters. The
	 * character sequences are considered disqualifying, so true is returned only if none
	 * are found.
	 *
	 * @param scanner the current character steam
	 * @param sequences array of strings to match
	 * @return true if none of the sequences match; false otherwise
	 */
	public static boolean matchCloseBraceWsSequence(ICharacterScanner scanner, String[] sequences) {
		if (matchCloseBrace(scanner)) {
			for (String sequence : sequences) {
				if (matchWsSeq(scanner, sequence.toCharArray(), true)) {
					Log.trace(ScanUtil.class, "Matched predicate sequence");
					return false;
				}
			}
			Log.trace(ScanUtil.class, "Matched action sequence [char=" + on(scanner) + "]");
			return true;
		}
		Log.debug(ScanUtil.class, "Missing close brace");
		return false;
	}

	/**
	 * Scans forward in the character stream to find a close brace; initial position is
	 * inside the first opening brace. Only matches on a balanced brace pair. Ignores
	 * braces in strings.
	 *
	 * @param scanner
	 * @param sequences
	 */
	public static boolean matchCloseBrace(ICharacterScanner scanner) {
		int braceCount = 1;
		int idx = 1;
		int c;

		do {
			c = scanner.read();
			if (c == '"' || c == '\'') { // quote, skip to close quote
				matchEndQuote(scanner, c);
			} else if (c == '/') { // all types of comments, skip to end
				matchCommentEnd(scanner, c);
			} else if (c == '{') { // open brace, increment balance count
				braceCount++;
			} else if (c == '}') { // close brace, decrement balance count
				braceCount--;
			}
			idx++;
		} while (c != ICharacterScanner.EOF && braceCount > 0);
		if (braceCount > 0) { // failed
			scanner.unread();
			for (int jdx = idx - 1; jdx > 0; jdx--) {
				scanner.unread();
			}
			return false;
		}
		Log.trace(ScanUtil.class, "matchCloseBrace [char=" + (char) c + "]");
		return true;
	}

	/**
	 * Determines whether the current character stream matches 0 or more white space
	 * characters followed by the given character sequence. The character stream is only
	 * rewound on match failure.
	 *
	 * @param scanner the current character stream
	 * @param sequence the sequence to match
	 * @return true if the pattern matches
	 */
	public static boolean matchWsSeq(ICharacterScanner scanner, char[] sequence) {
		return matchWsSeq(scanner, sequence, false);
	}

	/**
	 * Determines whether the current character stream matches 0 or more white space
	 * characters followed by the given character sequence. The character stream is
	 * rewound on match, if rewind is true, and on match failure.
	 *
	 * @param scanner the current character stream
	 * @param sequence the sequence to match
	 * @param rewind whether to rewind on match as well as on non-match
	 * @return true if the pattern matches
	 */
	public static boolean matchWsSeq(ICharacterScanner scanner, char[] sequence, boolean rewind) {
		int c;
		int idx = 1;

		while (true) {
			c = scanner.read();
			if (!ws.isWhitespace((char) c)) {
				scanner.unread();
				break;
			} else if (c == ICharacterScanner.EOF) {
				for (int jdx = idx - 1; jdx > 0; jdx--) {
					scanner.unread();
				}
				Log.trace(ScanUtil.class, "matchWsSeq0 [on=" + (char) c + "]");
				return false;
			}
			idx++;
		}
		Log.trace(ScanUtil.class, "matchWsSeq1 [count=" + (idx - 1) + ", on=" + (char) c + "]");

		boolean matched = matchSequence(scanner, sequence, rewind);
		if (!matched || rewind) {
			for (int jdx = idx - 1; jdx > 0; jdx--) {
				scanner.unread();
			}
		}
		Log.trace(ScanUtil.class, "matchWsSeq2 [count=" + (idx - 1) + ", on=" + (char) c + "]");
		if (matched) return true;
		return false;
	}

	/**
	 * Scan forward to end of quoted string, skipping any included braces.
	 *
	 * @param scanner the character stream
	 * @param quoteChar the quote character to match
	 */
	public static void matchEndQuote(ICharacterScanner scanner, int quoteChar) {
		int c;
		int count = 0;
		do {
			c = scanner.read();
			count++;
		} while (c != quoteChar && c != ICharacterScanner.EOF);
		if (c == ICharacterScanner.EOF) {
			for (int idx = count; idx > 0; idx--) {
				scanner.unread();
			}
		}
	}

	/**
	 * Scan forward to end of possible comment, skipping any included braces.
	 *
	 * @param scanner
	 * @param commentChar
	 */
	public static void matchCommentEnd(ICharacterScanner scanner, int commentChar) {
		int c;
		int pc;
		int count = 0;

		// validate comment
		c = scanner.read();
		count++;
		if (c == '/') { // skip to end of line
			do {
				c = scanner.read();
				count++;
			} while (c != '\n' && c != '\r' && c != ICharacterScanner.EOF);
			if (c == ICharacterScanner.EOF) {
				for (int idx = count; idx > 0; idx--) {
					scanner.unread();
				}
			}
		} else if (c == '*') { // skip comment
			c = scanner.read();
			count++;
			do {
				pc = c;
				c = scanner.read();
				count++;
				if (pc == '*' && c == '/') break;
			} while (c != ICharacterScanner.EOF);
			if (c == ICharacterScanner.EOF) {
				for (int idx = count; idx > 0; idx--) {
					scanner.unread();
				}
			}
		} else { // not a comment
			scanner.unread();
		}
	}

	@SuppressWarnings("unused")
	private static void log(String msg, ICharacterScanner scanner) {
		Log.debug(ScanUtil.class, msg + " [col=" + scanner.getColumn() + ", char=" + on(scanner) + "]");
	}

	private static char on(ICharacterScanner scanner) {
		int c = scanner.read();
		scanner.unread();
		return (char) c;
	}
}
