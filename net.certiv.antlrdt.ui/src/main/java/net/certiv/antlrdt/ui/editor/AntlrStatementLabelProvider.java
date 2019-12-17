package net.certiv.antlrdt.ui.editor;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlrdt.core.model.SpecData;
import net.certiv.antlrdt.ui.AntlrUI;
import net.certiv.antlrdt.ui.ImageManager;
import net.certiv.dsl.core.model.IDslElement;
import net.certiv.dsl.ui.DslImageDescriptor;
import net.certiv.dsl.ui.editor.StatementLabelProvider;

public class AntlrStatementLabelProvider extends StatementLabelProvider {

	public AntlrStatementLabelProvider() {
		super(AntlrUI.getDefault().getImageManager());
	}

	@Override
	public String decorateText(String text) {
		switch (getElementKind()) {
			case IDslElement.MODULE:
				if (hasData()) {
					SpecData data = (SpecData) getData();
					return data.name;
				}
				return text;

			case IDslElement.DECLARATION:
				return text;

			case IDslElement.STATEMENT:
			case IDslElement.FIELD:
				if (hasData()) {
					SpecData data = (SpecData) getData();
					switch (data.specType) {
						case Option:
							return data.name + " = " + data.value.getText();

						case Options:
						case Channel:
						case Import:
						case Token:
							return data.name;

						case AtAction:
							if (!data.name.isEmpty()) {
								return data.name + "::" + data.value.getText();
							}
							return data.value.getText();

						case ParserRule:
						case LexerRule:
						case Value:
							return data.name;

						case Label:
							return data.name + " (label)";

						default:
							return data.name + " (literal)";
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
		ImageManager mgr = (ImageManager) imgMgr;
		ImageDescriptor desc = null;

		switch (getElementKind()) {
			case IDslElement.DSL_UNIT:
				desc = mgr.getDescriptor(mgr.IMG_OBJ_PACKAGE);
				break;

			case IDslElement.MODULE:
				desc = mgr.getDescriptor(mgr.IMG_OBJS_MODULE);
				break;

			case IDslElement.DECLARATION:
				desc = mgr.getDescriptor(mgr.IMG_OBJS_CORE);
				break;

			case IDslElement.STATEMENT:
			case IDslElement.FIELD:
				desc = mgr.getDescriptor(mgr.IMG_OBJS_STATEMENT);
				if (hasData()) {
					SpecData data = (SpecData) getData();
					switch (data.specType) {
						case Options:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_OPTION);
							break;
						case Option:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_ENUM);
							break;
						case Tokens:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_PACKAGE);
							break;
						case Token:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_MESSAGE);
							break;
						case LexerRule:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_LEXER);
							if (hasOverlay(data.decoration & SpecData.FRAGMENT)) {
								desc = createOverlayImageDescriptor(desc, mgr.getDescriptor(mgr.IMG_OVR_FRAGMENT),
										DslImageDescriptor.TOP_RIGHT);
							}
							break;
						case ParserRule:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_PARSER);
							if (hasOverlay(data.decoration & SpecData.PROTECTED)) {
								desc = createOverlayImageDescriptor(desc, mgr.getDescriptor(mgr.IMG_OVR_PROTECTED),
										DslImageDescriptor.TOP_RIGHT);
							} else if (hasOverlay(data.decoration & SpecData.PRIVATE)) {
								desc = createOverlayImageDescriptor(desc, mgr.getDescriptor(mgr.IMG_OVR_PRIVATE),
										DslImageDescriptor.TOP_RIGHT);
							}
							break;
						case Mode:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_MODE);
							break;
						case AtAction:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_ACTION);
							break;
						case Label:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_LABEL);
							break;
						case Value:
							desc = mgr.getDescriptor(mgr.IMG_OBJS_PROTECTED_METHOD);
							break;
						default:
							desc = mgr.getDescriptor(mgr.IMG_OBJS_UNKNOWN);
							break;
					}
				}
				break;

			case IDslElement.BEG_BLOCK:
				if (getStatement().getElementName().equals(":")) {
					desc = mgr.getDescriptor(mgr.IMG_OBJ_RULE_COLON);
				} else {
					desc = mgr.getDescriptor(mgr.IMG_OBJ_EXTEND);
				}
				break;

			case IDslElement.END_BLOCK:
				break;

			default:
				desc = ImageDescriptor.getMissingImageDescriptor();

		}
		return mgr.get(desc);
	}
}
