package com.sopia.userDemo.entities;

import java.util.List;

/**保存用户管理表中字段信息表
 * 
 * @author TMK
 * 对应表为userdemocolumn
 */
public class ELUserColumn {
	private int type;			//列类型 1：系统自带、2：数据字典、3：自定义
	private String column_name;		//数据库列名
	private String description;	//描述
	private int show_add;		//添加页面是否显示
	private int show_update;	//修改页面是否显示
	private int show_view ;		//查看页面是否显示
	private int show_register;	//注册页面是否显示
	private int show_user_update;//个人修改页面是否显示
	private int show_user_view;//个人查看页面是否显示
	private int show_list ; 	//列表页是否显示
	private String column_type ;//字段类型
	private String format;			//格式或者长度
	private int show_page_type;		//页面显示的方式
	//0：文本框、1：单选、2：复选、3：下拉选项、4：大文本、5：数字、6：图片、7：附件、8：编辑器、9视频
	
	private List<ELUserPage> elUserPages;	//列的范围默认设置
	private List<ELUserColumnJs> elUserJses;		//列的JS校验
	
	
	public ELUserColumn(){}
	public ELUserColumn(String column_name,String description,int show_add,int show_update,int show_view,int show_register,int show_user_update,int show_user_view, int show_list,String column_type,String format){
		this.column_name = column_name;
		this.description = description;
		this.show_add = show_add;
		this.show_update = show_update;
		this.show_view = show_view;
		this.show_register = show_register;
		this.show_user_update = show_user_update;
		this.show_user_view = show_user_view;
		this.show_list = show_list;
		this.column_type = column_type;
		this.format = format;
	}
	public ELUserColumn(String column_name,String description,int show_add,int show_update,int show_view,int show_register,int show_user_update,int show_user_view, int show_list,String column_type,String format,int show_page_type){
		this.column_name = column_name;
		this.description = description;
		this.show_add = show_add;
		this.show_update = show_update;
		this.show_view = show_view;
		this.show_register = show_register;
		this.show_user_update = show_user_update;
		this.show_user_view = show_user_view;
		this.show_list = show_list;
		this.column_type = column_type;
		this.format = format;
		this.show_page_type = show_page_type;
	}
	public ELUserColumn(int type,String column_name,String description,int show_add,int show_update,int show_view,int show_register,int show_user_update,int show_user_view,int show_list,String column_type,String format,int show_page_type){
		this.type =type;
		this.column_name = column_name;
		this.description = description;
		this.show_add = show_add;
		this.show_update = show_update;
		this.show_view = show_view;
		this.show_register = show_register;
		this.show_user_update = show_user_update;
		this.show_user_view = show_user_view;
		this.show_list = show_list;
		this.column_type = column_type;
		this.format = format;
		this.show_page_type = show_page_type;
	}
	
	public String getShow_page_typeName(){
		if(this.show_page_type == 0){
			return "文本";
		}else if(this.show_page_type == 1){
			return "单选";
		}else if(this.show_page_type == 2){
			return "复选";
		}else if(this.show_page_type == 3){
			return "下拉选项";
		}else if(this.show_page_type == 4){
			return "大文本";
		}else if(this.show_page_type == 5){
			return "数字";
		}else if(this.show_page_type == 6){
			return "图片";
		}else if(this.show_page_type == 7){
			return "附件";
		}else if(this.show_page_type == 8){
			return "编辑器";
		}else if(this.show_page_type == 9){
			return "视频";
		}else {
			return "未知类型";
		}
	}
	public String getColumn_typeName(){
		if(this.column_type.equals("varchar2")){
			return "字符串";
		}else if(this.column_type.equals("number")){
			return "数字";
		}else if(this.column_type.equals("date")){
			return "时间";
		}else{
			return "未知类型的字段";
		}
	}
	public String getTypeName(){
		if(this.type == 1){
			return "系统自带字段";
		}else if(this.type == 2){
			return "数据字典字段";
		}else if(this.type == 3){
			return "自定义添加字段";
		}else{
			return "未知类型的字段";
		}
	}
	
	
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	public String getColumn_type() {
		return column_type;
	}
	public void setColumn_type(String column_type) {
		this.column_type = column_type;
	}
	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getShow_list() {
		return show_list;
	}
	public void setShow_list(int show_list) {
		this.show_list = show_list;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public int getShow_add() {
		return show_add;
	}
	public void setShow_add(int show_add) {
		this.show_add = show_add;
	}
	public int getShow_update() {
		return show_update;
	}
	public void setShow_update(int show_update) {
		this.show_update = show_update;
	}
	public int getShow_view() {
		return show_view;
	}
	public void setShow_view(int show_view) {
		this.show_view = show_view;
	}
	public int getShow_register() {
		return show_register;
	}
	public void setShow_register(int show_register) {
		this.show_register = show_register;
	}
	public int getShow_user_update() {
		return show_user_update;
	}
	public void setShow_user_update(int show_user_update) {
		this.show_user_update = show_user_update;
	}
	public int getShow_user_view() {
		return show_user_view;
	}
	public void setShow_user_view(int show_user_view) {
		this.show_user_view = show_user_view;
	}
	public int getShow_page_type() {
		return show_page_type;
	}
	public void setShow_page_type(int show_page_type) {
		this.show_page_type = show_page_type;
	}
	public List<ELUserPage> getElUserPages() {
		return elUserPages;
	}
	public void setElUserPages(List<ELUserPage> elUserPages) {
		this.elUserPages = elUserPages;
	}
	public List<ELUserColumnJs> getElUserJses() {
		return elUserJses;
	}
	public void setElUserJses(List<ELUserColumnJs> elUserJses) {
		this.elUserJses = elUserJses;
	}
	
	
	
	
}
