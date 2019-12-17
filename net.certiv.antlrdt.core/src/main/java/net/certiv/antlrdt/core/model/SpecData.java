package net.certiv.antlrdt.core.model;

import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.dsl.core.model.IStatement.BaseType;
import net.certiv.dsl.core.model.builder.ISpecType;
import net.certiv.dsl.core.model.builder.IStmtData;

public class SpecData implements IStmtData {

	// overlay types
	public static final int COMBINED = 1 << 0;
	public static final int LEXER = 1 << 1;
	public static final int PARSER = 1 << 2;
	public static final int TREE = 1 << 3;
	public static final int FRAGMENT = 1 << 4;
	public static final int PROTECTED = 1 << 5;
	public static final int PUBLIC = 1 << 6;
	public static final int PRIVATE = 1 << 7;

	public int decoration;

	public BaseType baseType;		// basic model type
	public SpecType specType;		// specialized model type

	public ParseTree stmtNode;		// statement context
	public String ruleName;			// matched grammar rule name
	public String name;				// display name

	public ParseTree value;			// statement value context

	/** Represents a simple statement; name contains the display text. */
	public SpecData(BaseType baseType, SpecType specType, String ruleName, ParseTree stmtNode, String name) {
		this(baseType, specType, ruleName, stmtNode, name, null);
	}

	/** Represents some key/value pair-like statement. */
	public SpecData(BaseType baseType, SpecType specType, String ruleName, ParseTree stmtNode, String name,
			ParseTree value) {
		this.baseType = baseType;
		this.specType = specType;
		this.ruleName = ruleName;
		this.stmtNode = stmtNode;
		this.name = name;
		this.value = value;
	}

	public void setValue(ParseTree value) {
		this.value = value;
	}

	public void setBaseType(BaseType baseType) {
		this.baseType = baseType;
	}

	@Override
	public ISpecType getSpecType() {
		return specType;
	}

	@Override
	public void setSpecType(ISpecType specType) {
		this.specType = (SpecType) specType;
	}

	/** Sets the display icon decoration type. */
	public void setDecoration(int decoration) {
		this.decoration = decoration & 0x7F;
	}

	@Override
	public String toString() {
		return String.format("ModelData [name=%s]", name);
	}
}
