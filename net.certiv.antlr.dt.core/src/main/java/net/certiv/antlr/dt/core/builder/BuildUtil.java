package net.certiv.antlr.dt.core.builder;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.antlr.v4.runtime.tree.RuleNode;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.ActionContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4Parser.OptionContext;
import net.certiv.antlr.dt.core.parser.gen.AntlrDT4ParserBaseVisitor;
import net.certiv.dsl.core.model.ModelException;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.util.Chars;
import net.certiv.dsl.core.util.Strings;
import net.certiv.dsl.core.util.antlr.GrammarUtil;

/** @see GrammarUtil */
public class BuildUtil {

	private static final String HEADER = "header";

	private static final String HDRSpec = "^\\h*@(?:(?:parser|lexer)\\:\\:)?header\\s*\\{(.*?)\\}";
	private static final String PKGSpec = "^\\h*package\\h+(\\S+?)\\h*;";
	private static final Pattern HDR = Pattern.compile(HDRSpec, Pattern.MULTILINE | Pattern.DOTALL);
	private static final Pattern PKG = Pattern.compile(PKGSpec, Pattern.MULTILINE);

	private static final String OPTSpec = "^\\h*options\\s*\\{(.*?)\\}";
	private static final String KEYSpec = "^\\h*(\\S+?)\\h*=\\h*(\\S+?)\\h*;";
	private static final Pattern OPT = Pattern.compile(OPTSpec, Pattern.MULTILINE | Pattern.DOTALL);
	private static final Pattern KEY = Pattern.compile(KEYSpec, Pattern.MULTILINE);

	private BuildUtil() {}

	/**
	 * Returns the absolute filesystem path to the build output location for the current
	 * grammar.
	 * <p>
	 * Resolves the location on (1) the package name specified in the first header block
	 * of the grammar; or (2) the corresponding code unit default build output location.
	 *
	 * @param record the grammar parse record
	 * @return an absolute output location path
	 * @throws ModelException exception code details cause
	 */
	public static IPath resolveOutputPath(DslParseRecord record) throws ModelException {
		IPath pkg = outputPackage(record);

		IPath path = null;
		if (pkg != null) {
			path = record.unit.getSourceRoot();
			if (path != null) path = path.append(pkg);

		} else {
			path = record.unit.getLanguageMgr().getBuildOutputPath(record.unit);
		}

		path = record.unit.getProject().getLocation().append(path);
		return path;
	}

	/**
	 * Returns the dotted-form package name of generated files. Prefers the
	 * grammar-defined package name. Otherwise, returns the package name of the grammar
	 * unit.
	 *
	 * @param record the grammar parse record
	 * @return the resolved output package name
	 */
	public static String outputPackageName(DslParseRecord record) {
		String pkg = grammarDefinedPackage(record);
		return pkg != null ? pkg : grammarUnitPackageName(record);
	}

	/**
	 * Returns the path-form package name of generated files. Prefers the grammar-defined
	 * package name. Otherwise, returns the package name of the grammar unit.
	 *
	 * @param record the grammar parse record
	 * @return the resolved path-form output package name
	 */
	public static IPath outputPackage(DslParseRecord record) {
		String pkg = grammarDefinedPackage(record);
		if (pkg != null) return new Path(pkg.replace(Chars.DOT, Chars.SLASH));

		return grammarUnitPackage(record);
	}

	/**
	 * Returns the dotted-form package name of the grammar unit.
	 *
	 * @param record the grammar parse record
	 * @return the dotted-form resolved package name
	 */
	private static String grammarUnitPackageName(DslParseRecord record) {
		return grammarUnitPackage(record).toString().replace(Chars.SLASH, Chars.DOT);
	}

	/**
	 * Returns the path-form package name of the grammar unit.
	 *
	 * @param record the grammar parse record
	 * @return the resolved path-form package name
	 */
	private static IPath grammarUnitPackage(DslParseRecord record) {
		IPath path = record.unit.getPackagePath(); // relative to source root!
		return path.removeLastSegments(1).removeTrailingSeparator();
	}

	/**
	 * Returns grammar-defined dotted-form package name. Returns {@code Strings.UNKNOWN}
	 * if not found.
	 *
	 * @param record the grammar parse record
	 * @return the resolved grammar-defined dotted-form package name
	 */
	public static String grammarDefinedPackageDisplayName(DslParseRecord record) {
		String pkg = grammarDefinedPackage(record);
		return pkg != null ? pkg : Strings.UNKNOWN;
	}

	/**
	 * Returns the dotted-form package name defined in the grammar top-most header block,
	 * or {@code null} if not found.
	 * <p>
	 * Visits the reconcile parse-tree, if present, or {@code regex}s the full grammar
	 * source content.
	 *
	 * @param record the grammar parse record
	 * @return the resolved package name or {@code null} if not found
	 */
	public static String grammarDefinedPackage(DslParseRecord record) {
		if (record.hasTree()) {
			AntlrDT4ParserBaseVisitor<String> PkgVisitor = new AntlrDT4ParserBaseVisitor<String>() {

				@Override
				protected boolean shouldVisitNextChild(RuleNode node, String result) {
					return result == null ? true : false;
				}

				@Override
				public String visitAction(ActionContext ctx) {
					String name = ctx.id().getText();
					if (HEADER.equals(name)) {
						String text = GrammarUtil.getText(ctx.actionBlock().ACTION_CONTENT());
						Matcher m = PKG.matcher(text.trim());
						if (m.find()) return m.group(1).trim();
					}
					return null;
				}
			};

			return PkgVisitor.visit(record.getTree());

		}

		String content = record.unit.getContent();
		Matcher m = HDR.matcher(content);
		if (m.find()) {
			String hdrText = m.group(1);
			m = PKG.matcher(hdrText);
			if (m.find()) return m.group(1).trim();
		}
		return null;
	}

	/**
	 * Returns the value defined for the given option name, or {@code null} if the options
	 * block or named key is not found.
	 * <p>
	 * Visits the reconcile parse-tree, if present, or {@code regex}s the full grammar
	 * source content.
	 *
	 * @param record the grammar parse record
	 * @param keyname the option name key
	 * @return the value assigned to the key or {@code null} if not found
	 */
	public static String grammarDefinedOptionValue(DslParseRecord record, String keyname) {
		if (record.hasTree()) {
			AntlrDT4ParserBaseVisitor<String> visitor = new AntlrDT4ParserBaseVisitor<String>() {

				@Override
				protected boolean shouldVisitNextChild(RuleNode node, String result) {
					return result == null ? true : false;
				}

				@Override
				public String visitOption(OptionContext ctx) {
					if (ctx.id().getText().equals(keyname)) {
						return ctx.optionValue().getText();
					}
					return null;
				}
			};

			return visitor.visit(record.getTree());
		}

		String content = record.unit.getContent();
		Matcher m = OPT.matcher(content);
		if (m.find()) {
			String optText = m.group(1);
			m = KEY.matcher(optText);
			if (m.find()) {
				if (m.group(1).equals(keyname)) {
					return m.group(2);
				}
			}
		}
		return null;
	}
}
