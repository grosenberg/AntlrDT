/*******************************************************************************
 * Copyright (c) 2012, 2020 Certiv Analytics.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package net.certiv.antlr.dt.ui.editor.outline;

import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;

import net.certiv.antlr.dt.core.model.Specialization;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.dsl.ui.DslImageDescriptor;
import net.certiv.dsl.ui.editor.outline.OutlineLabelProvider;

public class AntlrStatementLabelProvider extends OutlineLabelProvider {

	public AntlrStatementLabelProvider() {
		super(AntlrUI.getDefault().getImageManager());
	}

	@Override
	public String decorateText(String text) {
		Specialization data;
		switch (getStatementType()) {
			case MODULE:
				if (!hasData()) return text;
				data = (Specialization) getData();
				return data.name;

			case IMPORT:
				if (!hasData()) return text;
				data = (Specialization) getData();
				return data.name;

			case DECLARATION:
				return text;

			case STATEMENT:
			case FIELD:
				if (!hasData()) return text;

				data = (Specialization) getData();
				switch (data.specializedType) {
					case Option:
						return data.name + " = " + data.value.getText();

					case Options:
					case Channel:
					case Import:
					case Token:
						return data.name;

					case AtAction:
						if (data.name.isEmpty()) return data.value.getText();
						return data.name + "::" + data.value.getText();

					case ParserRule:
					case LexerRule:
					case Value:
						return data.name;

					case Label:
						return data.name + " (label)";

					default:
						return data.name + " (literal)";
				}

			case BEG_BLOCK:
				if (text.equals(":")) return "Rule elements";
				return "Alt group";

			case END_BLOCK:
			default:
				break;
		}
		return text + " [" + getStatementType() + "]";
	}

	@Override
	public Image decorateImage(Image image) {
		ImageManager mgr = (ImageManager) imgMgr;
		ImageDescriptor desc = null;

		switch (getStatementType()) {
			case MODULE:
				desc = mgr.getDescriptor(mgr.IMG_OBJ_GRAMMAR);
				break;

			case IMPORT:
				desc = mgr.getDescriptor(mgr.IMG_OBJS_INCLUDE);
				if (hasData()) {
					Specialization data = (Specialization) getData();
					if (hasOverlay(data.decoration & Specialization.SYNTHETIC)) {
						desc = createOverlayImageDescriptor(desc,
								mgr.getDescriptor(mgr.IMG_OVR_EXTERNAL_FILE), DslImageDescriptor.TOP_RIGHT);
					}
				}
				break;

			case DECLARATION:
				desc = mgr.getDescriptor(mgr.IMG_OBJ_PACKAGE);
				break;

			case STATEMENT:
			case FIELD:
				desc = mgr.getDescriptor(mgr.IMG_OBJS_STATEMENT);
				if (hasData()) {
					Specialization data = (Specialization) getData();
					switch (data.specializedType) {
						case Options:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_OPTIONS);
							break;
						case Option:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_OPTION);
							break;
						case Tokens:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_ENUM);
							break;
						case Token:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_TERMINAL);
							break;

						case Import:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_IMPORT);
							break;

						case Channel:
							desc = mgr.getDescriptor(mgr.IMG_LCL_GOTO);
							break;

						case ParserRuleName:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_PARSER);
							break;

						case LexerRuleName:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_LEXER);
							break;

						case FragmentRuleName:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_LEXER);
							desc = createOverlayImageDescriptor(desc, mgr.getDescriptor(mgr.IMG_OVR_FRAGMENT),
									DslImageDescriptor.TOP_RIGHT);

							break;

						case ModeName:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_MODE);
							break;

						case ParserRule:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_PARSER);
							if (hasOverlay(data.decoration & Specialization.PROTECTED)) {
								desc = createOverlayImageDescriptor(desc,
										mgr.getDescriptor(mgr.IMG_OVR_PROTECTED),
										DslImageDescriptor.TOP_RIGHT);
							} else if (hasOverlay(data.decoration & Specialization.PRIVATE)) {
								desc = createOverlayImageDescriptor(desc,
										mgr.getDescriptor(mgr.IMG_OVR_PRIVATE), DslImageDescriptor.TOP_RIGHT);
							}
							break;
						case LexerRule:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_LEXER);
							if (hasOverlay(data.decoration & Specialization.FRAGMENT)) {
								desc = createOverlayImageDescriptor(desc,
										mgr.getDescriptor(mgr.IMG_OVR_FRAGMENT),
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
							desc = mgr.getDescriptor(mgr.IMG_OBJ_VALUE);
							break;

						case ActionBlock:
						case Definition:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_REPEATED);
							break;

						case Block:
							desc = mgr.getDescriptor(mgr.IMG_OBJS_BLOCK);
							break;

						case RuleRef:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_RULE);
							break;

						case ParserAtomRef:
						case LexerAtomRef:
						case Terminal:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_TERMINAL);
							break;

						case Range:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_RANGE);
							break;
						case Set:
							desc = mgr.getDescriptor(mgr.IMG_OBJ_SET);
							break;

						case Unknown:
						default:
							desc = mgr.getDescriptor(mgr.IMG_OBJS_UNKNOWN);
							break;
					}
				}
				break;

			case BLOCK:
			case BEG_BLOCK:
				if (getStatement().getElementName().equals(":")) {
					desc = mgr.getDescriptor(mgr.IMG_OBJ_RULE_COLON);
				} else {
					desc = mgr.getDescriptor(mgr.IMG_OBJ_EXTEND);
				}
				break;

			case END_BLOCK:
				break;

			default:
				desc = ImageDescriptor.getMissingImageDescriptor();

		}
		return mgr.get(desc);
	}
}
