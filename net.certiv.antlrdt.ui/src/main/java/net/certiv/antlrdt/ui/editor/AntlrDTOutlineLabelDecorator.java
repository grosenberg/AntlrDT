package net.certiv.antlrdt.ui.editor;

import net.certiv.antlrdt.core.parser.ModelData;
import net.certiv.antlrdt.ui.AntlrDTImages;
import net.certiv.antlrdt.ui.AntlrDTUI;
import net.certiv.dsl.core.model.IDslElement;
import net.certiv.dsl.ui.editor.OutlineLabelDecorator;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

public class AntlrDTOutlineLabelDecorator extends OutlineLabelDecorator {

	public AntlrDTOutlineLabelDecorator() {
		super();
	}

	private AntlrDTImages getImageProvider() {
		return (AntlrDTImages) AntlrDTUI.getDefault().getImageProvider();
	}

	public String decorateText(String text) {
		switch (getElementKind()) {
			case IDslElement.MODULE:
				if (hasData()) {
					ModelData data = (ModelData) getData();
					return data.key;
				}
				return text;
			case IDslElement.STATEMENT:
				if (hasData()) {
					ModelData data = (ModelData) getData();
					switch (data.mType) {
						case Option:
							return data.key + " = " + data.value.getText();
						case Token:
							return data.key;
						case AtAction:
							if (!data.key.isEmpty()) {
								return data.key + "::" + data.value.getText();
							}
							return data.value.getText();
						default:
							return data.key;
					}
				}
				return text;
			case IDslElement.BLOCK:
				return text;
		}
		return text;
	}

	public Image decorateImage(Image image) {
		// create the base image
		ImageDescriptor baseImage = createBaseImageDescriptor(image);
		int type = 0;
		switch (getElementKind()) {
			case IDslElement.MODULE:
				baseImage = getImageProvider().DESC_OBJ_MODULE;
				type = 1;
				break;
			case IDslElement.STATEMENT:
				baseImage = getImageProvider().DESC_OBJ_STATEMENT;
				type = 2;
				if (hasData()) {
					ModelData data = (ModelData) getData();
					switch (data.mType) {
						case Options:
							baseImage = getImageProvider().DESC_OBJ_OPTION;
							type = 3;
							break;
						case Option:
							baseImage = getImageProvider().DESC_OBJ_ENUM;
							type = 4;
							break;
						case Tokens:
							baseImage = getImageProvider().DESC_OBJ_PACKAGE;
							type = 5;
							break;
						case Token:
							baseImage = getImageProvider().DESC_OBJ_MESSAGE;
							type = 6;
							break;
						case LexerRule:
							baseImage = getImageProvider().DESC_OBJ_LEXER;
							type = 8;
							if (addOverlay(data.decoration & ModelData.FRAGMENT)) {
								baseImage = createOverlayImageDescriptor(baseImage,
										getImageProvider().DESC_OVR_FRAGMENT, TOP_RIGHT);
								type = 9;
							}
							break;
						case ParserRule:
							baseImage = getImageProvider().DESC_OBJ_PARSER;
							type = 10;
							if (addOverlay(data.decoration & ModelData.PROTECTED)) {
								baseImage = createOverlayImageDescriptor(baseImage,
										getImageProvider().DESC_OVR_PROTECTED, TOP_RIGHT);
								type = 11;
							} else if (addOverlay(data.decoration & ModelData.PRIVATE)) {
								baseImage = createOverlayImageDescriptor(baseImage, getImageProvider().DESC_OVR_PRIVATE,
										TOP_RIGHT);
								type = 12;
							}
							break;
						case AtAction:
							baseImage = getImageProvider().DESC_OBJ_ACTION;
							type = 13;
							break;
						default:
							baseImage = getImageProvider().DESC_OBJ_REQUIRED;
							type = 7;
							break;
					}
				}
				break;
			case IDslElement.BLOCK:
				baseImage = getImageProvider().DESC_OBJ_BLOCK;
				type = 14;
				break;
		}
		Image img = fetchImage(type);
		if (img == null) {
			img = baseImage.createImage();
			storeImage(type, img);
		}
		return img;
	}
}
