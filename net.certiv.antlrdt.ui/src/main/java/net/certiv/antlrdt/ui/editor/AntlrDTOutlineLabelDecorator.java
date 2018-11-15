package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlrdt.core.parser.ModelData;
import net.certiv.antlrdt.ui.AntlrDTImages;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.model.IDslElement;
import net.certiv.dsl.ui.editor.OutlineLabelDecorator;

public class AntlrDTOutlineLabelDecorator extends OutlineLabelDecorator {

	public AntlrDTOutlineLabelDecorator() {
		super();
	}

	@Override
	public String decorateText(String text) {
		switch (getElementKind()) {
			case IDslElement.MODULE:
				if (hasData()) {
					ModelData data = (ModelData) getData();
					return data.key;
				}
				return text;

			case IDslElement.DECLARATION:
				return text;

			case IDslElement.STATEMENT:
			case IDslElement.FIELD:
				if (hasData()) {
					ModelData data = (ModelData) getData();
					switch (data.mType) {
						case Option:
							return data.key + " = " + data.value.getText();

						case Options:
						case Channel:
						case Import:
						case Token:
							return data.key;

						case AtAction:
							if (!data.key.isEmpty()) {
								return data.key + "::" + data.value.getText();
							}
							return data.value.getText();

						case ParserRule:
						case LexerRule:
						case Value:
							return data.key;

						case LabelId:
							return data.key + " (label)";

						default:
							return data.key + " (literal)";
					}
				}
				return text;

			case IDslElement.BEG_BLOCK:
				if (text.equals(":")) return "Rule elements";
				return "Alt group";

			case IDslElement.END_BLOCK:
				return null;
		}
		return text + " [" + getElementKind() + "]";
	}

	@Override
	public Image decorateImage(Image image) {
		AntlrDTImages provider = AntlrDTUI.getDefault().getImageProvider();
		ImageDescriptor desc = null;

		switch (getElementKind()) {
			case IDslElement.DSL_CONTAINER:
			case IDslElement.DSL_UNIT:
				desc = provider.DESC_OBJ_PACKAGE;
				break;

			case IDslElement.MODULE:
				desc = provider.DESC_OBJS_MODULE;
				break;

			case IDslElement.DECLARATION:
				desc = provider.DESC_OBJS_CORE;
				break;

			case IDslElement.STATEMENT:
			case IDslElement.FIELD:
				desc = provider.DESC_OBJS_STATEMENT;
				if (hasData()) {
					ModelData data = (ModelData) getData();
					switch (data.mType) {
						case Options:
							desc = provider.DESC_OBJ_OPTION;
							break;
						case Option:
							desc = provider.DESC_OBJ_ENUM;
							break;
						case Tokens:
							desc = provider.DESC_OBJ_PACKAGE;
							break;
						case Token:
							desc = provider.DESC_OBJ_MESSAGE;
							break;
						case LexerRule:
							desc = provider.DESC_OBJ_LEXER;
							if (addOverlay(data.decoration & ModelData.FRAGMENT)) {
								desc = createOverlayImageDescriptor(desc, provider.DESC_OVR_FRAGMENT, TOP_RIGHT);
							}
							break;
						case ParserRule:
							desc = provider.DESC_OBJ_PARSER;
							if (addOverlay(data.decoration & ModelData.PROTECTED)) {
								desc = createOverlayImageDescriptor(desc, provider.DESC_OVR_PROTECTED, TOP_RIGHT);
							} else if (addOverlay(data.decoration & ModelData.PRIVATE)) {
								desc = createOverlayImageDescriptor(desc, provider.DESC_OVR_PRIVATE, TOP_RIGHT);
							}
							break;
						case Mode:
							desc = provider.DESC_OBJ_MODE;
							break;
						case AtAction:
							desc = provider.DESC_OBJ_ACTION;
							break;
						case LabelId:
							desc = provider.DESC_OBJ_LABEL;
							break;
						case Value:
							desc = provider.DESC_OBJS_PROTECTED_METHOD;
							break;
						default:
							desc = provider.UNKNOWN_NODE;
							break;
					}
				}
				break;

			case IDslElement.BEG_BLOCK:
				if (getStatement().getElementName().equals(":")) {
					desc = provider.DESC_OBJ_RULE_COLON;
				} else {
					desc = provider.DESC_OBJ_EXTEND;
				}
				break;

			case IDslElement.END_BLOCK:
				break;

			default:
				desc = createMissingImageDescriptor(image);

		}
		return findImage(desc);
	}
}
