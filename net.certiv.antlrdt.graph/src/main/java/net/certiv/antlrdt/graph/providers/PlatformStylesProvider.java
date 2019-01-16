package net.certiv.antlrdt.graph.providers;

import java.io.File;
import java.io.FilenameFilter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import com.google.common.collect.Maps;

import net.certiv.dsl.core.util.CoreUtil;

public class PlatformStylesProvider implements IPlatformStylesProvider {

	private static final String CSS_FOLDER = "/styles";

	@Override
	public Map<String, URL> provide() {
		HashMap<String, URL> styleSheets = Maps.newLinkedHashMap();
		File folder = new File(CoreUtil.getPlatformLocation() + CSS_FOLDER);
		if (folder.exists() && folder.isDirectory()) {
			File[] files = folder.listFiles((FilenameFilter) (dir, name) -> {
				return name.endsWith(".css");
			});

			for (File file : files) {
				try {
					URL url = file.toURI().toURL();
					styleSheets.put(file.getName(), url);
				} catch (MalformedURLException e) {}
			}
		}
		return styleSheets;
	}
}
