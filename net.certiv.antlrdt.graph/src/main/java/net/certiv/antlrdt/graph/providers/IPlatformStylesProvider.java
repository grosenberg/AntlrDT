package net.certiv.antlrdt.graph.providers;

import java.net.URL;
import java.util.Map;

public interface IPlatformStylesProvider {

	Map<String, URL> provide();
}
