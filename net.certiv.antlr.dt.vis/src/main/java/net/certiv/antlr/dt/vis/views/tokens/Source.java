package net.certiv.antlr.dt.vis.views.tokens;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

import net.certiv.dsl.core.util.FileUtils;
import net.certiv.dsl.core.util.Strings;

public class Source {

	final String path;
	final String name;

	public Source() {
		this(Strings.EMPTY, Strings.EMPTY);
	}

	public Source(String path, String name) {
		super();
		this.path = path != null ? path : Strings.EMPTY;
		this.name = name != null ? name : Strings.EMPTY;
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

	public String getPathname() {
		if (path.equals(Strings.EMPTY)) return name;
		if (path.equals(Strings.SLASH)) return path + name;
		return path + Strings.SLASH + name;
	}

	public String getExt() {
		return FileUtils.getExt(getPathname());
	}

	public boolean exists() {
		return getFile().isFile();
	}

	public File getFile() {
		return new File(getPathname());
	}

	public String getContent() {
		File file = getFile();
		if (file.isFile()) {
			try {
				return FileUtils.readToString(file);
			} catch (IOException e) {}
		}
		return Strings.EMPTY;
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

	@Override
	public int hashCode() {
		return Objects.hash(name, path);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (!(obj instanceof Source)) return false;
		Source other = (Source) obj;
		return Objects.equals(name, other.name) && Objects.equals(path, other.path);
	}

	@Override
	public String toString() {
		return getPathname();
	}
}
