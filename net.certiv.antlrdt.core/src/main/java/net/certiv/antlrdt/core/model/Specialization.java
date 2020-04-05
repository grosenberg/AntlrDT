package net.certiv.antlrdt.core.model;

import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.dsl.core.model.ModelType;
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

	public SpecializedType specializedType;		// specialized model type

	/** Represents a simple statement; name contains the display text. */
	public Specialization(ModelType modelType, SpecializedType specializedType, String ruleName, ParseTree stmtNode,
			String name) {
		this(modelType, specializedType, ruleName, stmtNode, name, null);
	}

	/** Represents some key/value pair-like statement. */
	public Specialization(ModelType modelType, SpecializedType specializedType, String ruleName, ParseTree stmtNode,
			String name, ParseTree value) {

		super(modelType, ruleName, stmtNode, name, value);
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
			return (Specialization) this.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException("Shoud never occur", e);
		}
	}

	@Override
	public String toString() {
		return String.format("ModelData [name=%s]", name);
	}
}
