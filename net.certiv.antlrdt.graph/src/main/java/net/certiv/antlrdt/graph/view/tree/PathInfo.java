package net.certiv.antlrdt.graph.view.tree;

import org.antlr.v4.runtime.Token;
import org.eclipse.core.resources.IFile;

import net.certiv.antlrdt.core.parser.ITargetInfo;
import net.certiv.antlrdt.core.parser.Target;
import net.certiv.dsl.core.parser.DslParseRecord;
import net.certiv.dsl.core.util.Strings;

public class PathInfo implements ITargetInfo {

	public final Token term;
	public final Target type;

	private boolean isParent;
	private String[] rulenames = Strings.EMPTY_STRINGS;;
	private String[] tokennames = Strings.EMPTY_STRINGS;
	private IFile file;

	public PathInfo(Token term, Target type, DslParseRecord record) {
		this.term = term;
		this.type = type;
		init(record);
	}

	@SuppressWarnings("deprecation")
	public void init(DslParseRecord record) {
		file = record.unit.getFile();
		rulenames = record.parser.getRuleNames();
		tokennames = record.parser.getTokenNames();
	}

	@Override
	public Target getTargetType() {
		return type;
	}

	@Override
	public IFile getGrammar() {
		return file;
	}

	@Override
	public String getMainRuleName() {
		return Strings.EMPTY_STRING;
	}

	@Override
	public String[] getRuleNames() {
		return rulenames;
	}

	@Override
	public String[] getTokenNames() {
		return tokennames;
	}

	@Override
	public String[] getModeNames() {
		return Strings.EMPTY_STRINGS;
	}

	public Token getPathTerm() {
		return term;
	}

	public boolean isParserRule() {
		return type == Target.PARSER;
	}

	public boolean isLexerRule() {
		return type == Target.LEXER;
	}

	public boolean isFragmentRule() {
		return type == Target.FRAGMENT;
	}

	public boolean isModeStatement() {
		return type == Target.MODE;
	}

	public boolean isParent() {
		return isParent;
	}

	public PathInfo asParent() {
		this.isParent = true;
		return this;
	}

	public PathInfo asChild() {
		this.isParent = false;
		return this;
	}
}
