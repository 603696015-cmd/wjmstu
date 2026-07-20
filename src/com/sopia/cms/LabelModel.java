package com.sopia.cms;
 
public class LabelModel {
	private String labelType;//标签类型
	private String modelType;//模板类型
	private int modelId;//模板编号
	private int record;//获取记录数
	private int contentType;//内容类型 hot 
	private int contentLength;//内容显示长度
	private int titleLength;//标题长度
	private int row;//显示几列
	private String styles;//样式
	public String getLabelType() {
		return labelType;
	}
	public void setLabelType(String labelType) {
		this.labelType = labelType;
	}
	public String getModelType() {
		return modelType;
	}
	public void setModelType(String modelType) {
		this.modelType = modelType;
	}
	public int getModelId() {
		return modelId;
	}
	public void setModelId(int modelId) {
		this.modelId = modelId;
	}
	public int getRecord() {
		return record;
	}
	public void setRecord(int record) {
		this.record = record;
	}
	public int getContentType() {
		return contentType;
	}
	public void setContentType(int contentType) {
		this.contentType = contentType;
	}
	public int getContentLength() {
		return contentLength;
	}
	public void setContentLength(int contentLength) {
		this.contentLength = contentLength;
	}
	public int getRow() {
		return row;
	}
	public void setRow(int row) {
		this.row = row;
	} 
	public  String  getStyles() {
		return styles;
	}
	public void setStyles( String  styles) {
		this.styles = styles;
	}
	public int getTitleLength() {
		return titleLength;
	}
	public void setTitleLength(int titleLength) {
		this.titleLength = titleLength;
	}
	
}
