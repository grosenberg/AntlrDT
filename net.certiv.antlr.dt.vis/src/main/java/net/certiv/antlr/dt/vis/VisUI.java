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
package net.certiv.antlr.dt.vis;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import net.certiv.antlr.dt.core.AntlrCore;
import net.certiv.antlr.dt.ui.AntlrUI;
import net.certiv.antlr.dt.ui.ImageManager;
import net.certiv.common.log.Level;
import net.certiv.common.log.Log;
import net.certiv.dsl.core.DslCore;
import net.certiv.dsl.core.color.DslColorRegistry;
import net.certiv.dsl.core.lang.LanguageManager;
import net.certiv.dsl.core.model.DslModel;
import net.certiv.dsl.core.preferences.PrefsManager;
import net.certiv.dsl.ui.DslUI;
import net.certiv.dsl.ui.IDslUI;

public class VisUI extends AbstractUIPlugin implements IDslUI {

	private static VisUI plugin;

	public VisUI() {
		super();
	}

	@Override
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		Log.defLevel(Level.DEBUG);
	}

	@Override
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	public static VisUI getDefault() {
		return plugin;
	}

	@Override
	public DslUI getDslUI() {
		return AntlrUI.getDefault();
	}

	@Override
	public DslCore getDslCore() {
		return AntlrCore.getDefault();
	}

	@Override
	public String getPluginId() {
		return plugin.getBundle().getSymbolicName();
	}

	@Override
	public LanguageManager getLanguageManager() {
		return getDslCore().getLangManager();
	}

	@Override
	public PrefsManager getPrefsManager() {
		return getDslCore().getPrefsManager();
	}

	/** Returns the Dsl model. */
	public DslModel getDslModel() {
		return getDslCore().getDslModel();
	}

	public DslColorRegistry getColorRegistry() {
		return getDslCore().getColorRegistry();
	}

	public ImageManager getImageManager() {
		return (ImageManager) getDslUI().getImageManager();
	}
}
