package com.sopia.userDemo.entities;
/**
 * 列JS校验规则和列名关联信息
 * @author TMK
 * 对应表为Eluser_js
 */
public class ELUserColumnJs {
	
	private String column_name;//列名
	private int show_type;//表示页面类型：（1：添加页面、2：修改页面、3：查看页面、4注册页面、5：修改个人页面、6：查看个人页面、7：列表页面）
	private String check_js_type;//字符串类型，保存数字合  列js校验规则（1：数字、2：字母、3：数字和字母、4：中文和字母、5：中文和数字、6：中文、7：身份证、8：唯一、9：不限）
	//数据库保存为（1===2===3）
	public ELUserColumnJs(){}
	public ELUserColumnJs(String column_name,int show_type,String check_js_type){
		this.column_name = column_name;
		this.show_type = show_type;
		this.check_js_type = check_js_type;
	}
	public String getShow_typeName(){
		if(this.show_type == 1){
			return "添加页面";
		}else if(this.show_type == 2){
			return "修改页面";
		}else if(this.show_type == 4){
			return "注册页面";
		}else if(this.show_type == 5){
			return "修改个人页面";
		}else{
			return "未知页面";
		}
	}
	
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public int getShow_type() {
		return show_type;
	}
	public void setShow_type(int show_type) {
		this.show_type = show_type;
	}
	public String getCheck_js_type() {
		return check_js_type;
	}
	public void setCheck_js_type(String check_js_type) {
		this.check_js_type = check_js_type;
	}
	
	
	

}
