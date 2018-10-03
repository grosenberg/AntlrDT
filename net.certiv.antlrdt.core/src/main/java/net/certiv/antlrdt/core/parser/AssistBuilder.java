package net.certiv.antlrdt.core.parser;

import java.util.List;

import org.antlr.v4.runtime.Token;

import net.certiv.dsl.core.model.DslModelManager;
import net.certiv.dsl.core.model.ICodeUnit;
import net.certiv.dsl.core.parser.DslSourceParser;

public class AssistBuilder {

	private DslModelManager modelMgr;
	private ICodeUnit unit;

	public AssistBuilder(DslSourceParser srcParser) {
		this.modelMgr = srcParser.getDslCore().getModelManager();
		this.unit = srcParser.getRecord().unit;
	}

	/**
	 * Call back from the tree walker used to gather all of the nodes that may be considered for code
	 * assist. Each token represents a simple code element.
	 */
	public void addCodeAssistElement(Token token) {
		if (token != null) {
			modelMgr.addCodeAssistElement(unit, token);
		}
	}

	/**
	 * Call back from the tree walker used to gather all of the nodes that may be considered for code
	 * assist. Each token represents a simple code element.
	 */
	public void addCodeAssistElement(List<Token> tokens) {
		for (Token token : tokens) {
			addCodeAssistElement(token);
		}
	}
}
