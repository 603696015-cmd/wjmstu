package com.sopia.lucene.file;

import java.util.List;

import com.sopia.lucene.index.FileBean;

public class FileResult {
	private List<FileBean> beans;
	private Integer count;

	public FileResult() {
	}

	public FileResult(List<FileBean> beans, Integer count) {
		this.beans = beans;
		this.count = count;
	}

	public List<FileBean> getBeans() {
		return beans;
	}

	public void setBeans(List<FileBean> beans) {
		this.beans = beans;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

}
