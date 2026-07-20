package com.sopia.questionman.entities;

/**
 * office文档转换的jpg
 * @author taomingke
 *
 */
public class StuffOfficeJpg {
	private int id;				//图片由office文档转换数据库id
	private String nameBig;		//大图路径
	private String nameSmall;	//小图路径
	private String folder;		//图片所在文件夹
	private String title;		//图片alt
	
	public String getNameBig() {
		return nameBig;
	}
	public void setNameBig(String nameBig) {
		this.nameBig = nameBig;
	}
	public String getNameSmall() {
		return nameSmall;
	}
	public void setNameSmall(String nameSmall) {
		this.nameSmall = nameSmall;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFolder() {
		return folder;
	}
	public void setFolder(String folder) {
		this.folder = folder;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	

}
