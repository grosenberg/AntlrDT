package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlrdt.core.parser.ModelData;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.model.IDslElement;
import net.certiv.dsl.ui.DslImageDescriptor;
import net.certiv.dsl.ui.editor.OutlineLabelDecorator;

public class AntlrDTOutlineLabelDecorator extends OutlineLabelDecorator {

	public AntlrDTOutlineLabelDecorator() {
		super(AntlrUI.getDefault().getImageManager());
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
		ImageManager aImgMgr = (ImageManager) imgMgr;
		ImageDescriptor desc = null;

		switch (getElementKind()) {
			case IDslElement.DSL_UNIT:
				desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_PACKAGE);
				break;

			case IDslElement.MODULE:
				desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJS_MODULE);
				break;

			case IDslElement.DECLARATION:
				desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJS_CORE);
				break;

			case IDslElement.STATEMENT:
			case IDslElement.FIELD:
				desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJS_STATEMENT);
				if (hasData()) {
					ModelData data = (ModelData) getData();
					switch (data.mType) {
						case Options:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_OPTION);
							break;
						case Option:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_ENUM);
							break;
						case Tokens:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_PACKAGE);
							break;
						case Token:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_MESSAGE);
							break;
						case LexerRule:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_LEXER);
							if (hasOverlay(data.decoration & ModelData.FRAGMENT)) {
								desc = createOverlayImageDescriptor(desc,
										aImgMgr.getDescriptor(aImgMgr.IMG_OVR_FRAGMENT), DslImageDescriptor.TOP_RIGHT);
							}
							break;
						case ParserRule:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_PARSER);
							if (hasOverlay(data.decoration & ModelData.PROTECTED)) {
								desc = createOverlayImageDescriptor(desc,
										aImgMgr.getDescriptor(aImgMgr.IMG_OVR_PROTECTED), DslImageDescriptor.TOP_RIGHT);
							} else if (hasOverlay(data.decoration & ModelData.PRIVATE)) {
								desc = createOverlayImageDescriptor(desc,
										aImgMgr.getDescriptor(aImgMgr.IMG_OVR_PRIVATE), DslImageDescriptor.TOP_RIGHT);
							}
							break;
						case Mode:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_MODE);
							break;
						case AtAction:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_ACTION);
							break;
						case LabelId:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_LABEL);
							break;
						case Value:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJS_PROTECTED_METHOD);
							break;
						default:
							desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJS_UNKNOWN);
							break;
					}
				}
				break;

			case IDslElement.BEG_BLOCK:
				if (getStatement().getElementName().equals(":")) {
					desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_RULE_COLON);
				} else {
					desc = aImgMgr.getDescriptor(aImgMgr.IMG_OBJ_EXTEND);
				}
				break;

			case IDslElement.END_BLOCK:
				break;

			default:
				desc = ImageDescriptor.getMissingImageDescriptor();

		}
		return aImgMgr.get(desc);
	}
}
