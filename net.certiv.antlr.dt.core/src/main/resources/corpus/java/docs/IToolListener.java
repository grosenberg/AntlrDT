/*
 * Copyright (c) 2012-2018 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */
package net.certiv.adept.tool;

/**
 * Defines behavior of object able to handle error messages from ANTLR including both tool errors
 * like "can't write file" and grammar ambiguity warnings. To avoid having to change tools that use
 * ANTLR (like GUIs), I am wrapping error data in Message objects and passing them to the listener.
 * In this way, users of this interface are less sensitive to changes in the info I need for error
 * messages.
 */
public interface IToolListener {

	public void info(String msg);

	public void warn(Messages msg);

	public void error(Messages msg);
}
