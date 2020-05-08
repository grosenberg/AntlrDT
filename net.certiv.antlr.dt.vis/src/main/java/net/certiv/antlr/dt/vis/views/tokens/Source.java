package net.certiv.antlr.dt.vis.views.tokens;

import java.io.File;
import java.io.IOException;

import net.certiv.dsl.core.util.FileUtils;
import net.certiv.dsl.core.util.Strings;

public class Source {
	String path;
	String name;

	public Source() {
		this("", "");
	}

	public Source(String path, String name) {
		super();
		this.path = path != null ? path : "";
		this.name = name != null ? name : "";
	}

	public boolean isEmpty() {
		return path.isEmpty() && name.isEmpty();
	}

	public String getPath() {
		return path;
	}

	public String getName() {
		return name;
	}

	public String getExt() {
		if (name == null) return null;
		int dot = name.lastIndexOf(".");
		if (dot == -1 || dot + 1 == name.length()) return "";
		return name.substring(dot + 1);
	}

	public String getPathname() {
		String pathname = name != null ? name : "";
		if (path != null && !path.equals("")) {
			if (path.equals("/")) {
				pathname = path + pathname;
			} else {
				pathname = path + "/" + pathname;
			}
		}
		return pathname;
	}

	public String getContent() {
		File file = getFile();
		if (file.isFile()) {
			try {
				return FileUtils.readToString(file);
			} catch (IOException e) {}
		}
		return "";
	}

	public String[] getLines() {
		File file = getFile();
		if (file.isFile()) {
			try {
				return FileUtils.readToLines(file);
			} catch (IOException e) {}
		}
		return Strings.EMPTY_STRINGS;
	}

	public File getFile() {
		return new File(getPathname());
	}

	public boolean exists() {
		return getFile().exists();
	}

	public boolean fileExists() {
		File file = getFile();
		return file.exists() && file.isFile();
	}

	@Override
	public String toString() {
		return getPathname();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		Source other = (Source) obj;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		if (path == null) {
			if (other.path != null) return false;
		} else if (!path.equals(other.path)) return false;
		return true;
	}
}
