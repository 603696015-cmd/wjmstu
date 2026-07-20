package com.sopia.schedule.entities;
/**
 * 图片之前保存方式为100==50==elstuffs/1325/1403.jpg
 * 现在将同时保存在模块表中和tb_pic表中
 * @author Taomingke
 *
 */
public class TBPicture {
	private String tablename;	//模块名
	private String columnname;	//列名
	private int id;				
	private int width;
	private int height;
	
	public TBPicture(){}
	
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	
}
