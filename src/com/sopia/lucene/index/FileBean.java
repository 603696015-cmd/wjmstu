package com.sopia.lucene.index;

import java.io.File;

public class FileBean {
	private String name;
	private String path;
	private String type;
	private File file;

	public FileBean() {
	}

	public FileBean(File file) {
		this.setName(file.getName());
		this.setPath(file.getAbsolutePath());
		String s[] = file.getName().split("[.]");
		this.setType(s[s.length > 0 ? s.length - 1 : 0]);
		this.file = file;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
