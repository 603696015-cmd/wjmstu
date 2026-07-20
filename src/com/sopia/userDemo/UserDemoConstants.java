package com.sopia.userDemo;

import java.util.HashMap;

public class UserDemoConstants {
	
	public static final String TABLENAME = "eluser_tmk";//用户新表名
	public static final String USERDEMOCOLUMN_TABLE = "userdemocolumn";//保存用户表中各个字段的信息表
	public static final String JSTYPE_TABLE = "eluser_js_type";//js校验实体表
	public static final String ELUSER_JS = "eluser_js";//保存字段的js校验规则
	public static final String ELUSER_PAGE_TYPE = "eluser_page_type";//保存不同字段类型的范围表
	public static final String ELUSER_PAGE_INFO = "eluser_page_info";//保存页面信息	
	
	public static final String SYSTEMCOLUMN = "系统自带字段";
	public static final String SHUJUZIDIANCOLUMN = "数据字典字段";
	public static final String ZDYCOLUMN = "自定义字段";
	
	public static final int SHOW_ADD = 1;
	public static final int SHOW_UPDATE = 2;
	public static final int SHOW_VIEW = 3;
	public static final int SHOW_LIST = 4;
	
	public static final String FOLDER = "admin\\eluserdemo\\";//上传的指定文件夹

}
