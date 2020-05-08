package net.certiv.antlr.dt.core.model;

import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.dsl.core.model.builder.ISpecializedType;
import net.certiv.dsl.core.model.builder.ModelSpecialization;

public class Specialization extends ModelSpecialization {

	// overlay types
	public static final int COMBINED = 1 << 0;
	public static final int LEXER = 1 << 1;
	public static final int PARSER = 1 << 2;
	public static final int TREE = 1 << 3;
	public static final int FRAGMENT = 1 << 4;
	public static final int PROTECTED = 1 << 5;
	public static final int PUBLIC = 1 << 6;
	public static final int PRIVATE = 1 << 7;
	public static final int SYNTHETIC = 1 << 8;

	public SpecializedType specializedType;		// specialized model type

	/** Represents a simple statement; name contains the display text. */
	public Specialization(SpecializedType specializedType, String ruleName, ParseTree stmtNode, String name) {
		this(specializedType, ruleName, stmtNode, name, null);
	}

	/** Represents some key/value pair-like statement. */
	public Specialization(SpecializedType specializedType, String ruleName, ParseTree stmtNode, String name,
			ParseTree value) {

		super(ruleName, stmtNode, name, value);
		this.specializedType = specializedType;
	}

	@Override
	public ISpecializedType getSpecializedType() {
		return specializedType;
	}

	@Override
	public void setSpecializedType(ISpecializedType specializedType) {
		this.specializedType = (SpecializedType) specializedType;
	}

	@Override
	public Specialization copy() {
		try {
			return (Specialization) clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Shoud never occur", e);
		}
	}
}
