package net.certiv.antlrdt.core.lang;

import java.util.Arrays;
import java.util.List;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import net.certiv.antlrdt.core.parser.AntlrSourceParser;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.lang.Imports;
import net.certiv.dsl.core.lang.LanguageManager;
import net.certiv.dsl.core.lang.RootEntry;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.parser.DslSourceParser;
import net.certiv.dsl.core.util.Chars;
import net.certiv.dsl.core.util.Strings;

public class AntlrLangManager extends LanguageManager {

	public static final String DSL_NAME = "antlr";
	public static final List<String> EXTENSIONS = Arrays.asList("g4");

	// maven standard source root paths - project relative
	private static final String SRC = "src/main/antlr4";
	private static final String[] EXC = { "attic", "bin", "lib", "src/main/antlr4/import" };
	private static final String OUT = "target/classes";

	public AntlrLangManager(DslCore core) {
		super(core);
	}

	@Override
	public String getDslName() {
		return DSL_NAME;
	}

	@Override
	public DslSourceParser createSourceParser(ICodeUnit unit, String langId) {
		return new AntlrSourceParser(unit.getParseRecord(langId));
	}

	@Override
	public List<String> getDslFileExtensions() {
		return EXTENSIONS;
	}

	@Override
	public Imports getImportsSpec() {
		return Imports.SPEC;
	}

	@Override
	public List<RootEntry> getLangSourceRoots() {
		return Arrays.asList(RootEntry.source(SRC).exclude(EXC).out(OUT));
	}

	@Override
	public List<IPath> importNameToPathname(String name) {
		IPath path = new Path(name.replace(Chars.DOT, Chars.SLASH));
		path = path.addFileExtension(EXTENSIONS.get(0));
		return Arrays.asList(path);
	}

	@Override
	public IPath resolveGrammarPackagePath(ICodeUnit unit) {
		String pkg = resolveGrammarPackage(unit);
		if (!Strings.empty(pkg)) {
			return new Path(pkg.replace(Chars.DOT, Chars.SLASH));
		}

		if (unit.getSourceRoot() == null) return null;
		return unit.getProjectRelativePath().removeLastSegments(1);
	}
}
