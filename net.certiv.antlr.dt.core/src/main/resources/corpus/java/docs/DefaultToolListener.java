/*
 * Copyright (c) 2012-2018 The ANTLR Project. All rights reserved.
 * Use of this file is governed by the BSD 3-clause license that
 * can be found in the LICENSE.txt file in the project root.
 */
package net.certiv.adept.tool;

import org.stringtemplate.v4.ST;

public class DefaultToolListener implements IToolListener {

	private Level level;

	public ITool tool;

	public DefaultToolListener(ITool tool) {
		this.tool = tool;
		this.level = Level.WARN;
	}

	public void setLevel(Level level) {
		this.level = level;
	}

	@Override
	public void info(String msg) {
		if (skip(Level.INFO)) return;
		if (tool.getErrMgr().formatWantsSingleLineMessage()) {
			msg = msg.replace('\n', ' ');
		}
		System.out.println(msg);
	}

	@Override
	public void warn(Messages msg) {
		if (skip(Level.WARN)) return;
		ST msgST = tool.getErrMgr().getMessageTemplate(msg);
		String outputMsg = msgST.render();
		if (tool.getErrMgr().formatWantsSingleLineMessage()) {
			outputMsg = outputMsg.replace('\n', ' ');
		}
		System.err.println(outputMsg);
	}

	@Override
	public void error(Messages msg) {
		if (skip(Level.ERROR)) return;
		ST msgST = tool.getErrMgr().getMessageTemplate(msg);
		String outputMsg = msgST.render();
		if (tool.getErrMgr().formatWantsSingleLineMessage()) {
			outputMsg = outputMsg.replace('\n', ' ');
		}
		System.err.println(outputMsg);
	}

	private boolean skip(Level target) {
		switch (this.level) {
			default:
			case INFO:
				return false;
			case WARN:
				if (target != Level.INFO) return false;
				return true;
			case ERROR:
				if (target == Level.ERROR) return false;
				return true;
			case QUIET:
				return true;
		}
	}
}
