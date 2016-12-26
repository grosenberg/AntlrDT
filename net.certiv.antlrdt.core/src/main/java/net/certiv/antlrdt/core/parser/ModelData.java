package net.certiv.antlrdt.core.parser;

import java.util.List;

import org.antlr.v4.runtime.tree.ParseTree;

import net.certiv.dsl.core.model.IStatement.Form;
import net.certiv.dsl.core.model.IStatement.Realm;
import net.certiv.dsl.core.model.IStatement.Type;

public class ModelData {

	// overlay type
	public static final int COMBINED = 1 << 0;
	public static final int LEXER = 1 << 1;
	public static final int PARSER = 1 << 2;
	public static final int TREE = 1 << 3;
	public static final int FRAGMENT = 1 << 4;
	public static final int PROTECTED = 1 << 5;
	public static final int PUBLIC = 1 << 6;
	public static final int PRIVATE = 1 << 7;

	public int decoration = 0;
	public Type type = Type.VARIABLE;
	public Form form = Form.DECLARATION;
	public Realm realm = Realm.LOCAL;

	public ModelType mType;
	public ParseTree rootNode;
	public ParseTree name;
	public String key;
	public ParseTree value;
	public ParseTree mod;
	public ParseTree var;
	public ParseTree num;
	public ParseTree id;
	public ParseTree lhs;
	public ParseTree rhs;
	public List<ParseTree> listName;
	public List<ParseTree> listRet;

	public ModelData(ModelType mType, ParseTree rootNode, String key) {
		this.mType = mType;
		this.rootNode = rootNode;
		this.key = key;
	}

	public ModelData(ModelType mType, ParseTree rootNode, String key, ParseTree value) {
		this.mType = mType;
		this.rootNode = rootNode;
		this.key = key;
		this.value = value;
	}

	public ModelData(ModelType mType, ParseTree rootNode, ParseTree mod, List<ParseTree> listId) {
		this.mType = mType;
		this.mod = mod;
	}

	public ModelData(ModelType mType, ParseTree rootNode, ParseTree mod, ParseTree t, ParseTree var, ParseTree num) {
		this.mType = mType;
		this.rootNode = rootNode;
		this.mod = mod;
		this.var = var;
		this.num = num;
	}

	public ModelData(ModelType mType, ParseTree rootNode, ParseTree mod, ParseTree id, ParseTree num) {
		this.mType = mType;
		this.rootNode = rootNode;
		this.mod = mod;
		this.id = id;
		this.num = num;
	}

	public ModelData(ModelType mType, ParseTree rootNode, ParseTree lhs, ParseTree rhs) {
		this.mType = mType;
		this.rootNode = rootNode;
		this.lhs = lhs;
		this.rhs = rhs;
	}

	public ModelData(ModelType mType, ParseTree rootNode, ParseTree name, List<ParseTree> listName,
			List<ParseTree> listRet) {
		this.mType = mType;
		this.rootNode = rootNode;
		this.name = name;
		this.listName = listName;
		this.listRet = listRet;
	}

	public ModelData(ModelType mType, ParseTree rootNode, List<ParseTree> listName) {
		this.mType = mType;
		this.rootNode = rootNode;
		this.listName = listName;
	}

	public void setDecoration(int decoration) {
		this.decoration = decoration;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public void setForm(Form form) {
		this.form = form;
	}

	public void setScope(Realm realm) {
		this.realm = realm;
	}
}
