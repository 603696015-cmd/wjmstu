package com.sopia.lable.entites;

public class Mode {
	
	private   String   name  ;// 模块名称
	private   String   tableName ;//模块表名
	private   String   TypetableName ;//模块类别表名
	private   String   modeJspName ;//模块绑定JSP名称
	private   String   modeContentJspName;//模块内容页绑定的JSP名称
	private   int      typeid;//模块类型   1 系统模块 还是 2自定义模块
	private   int      id;
	private   int      bindid;//
	private   int      bindtypeid;//	绑定类型1    模块 2 类别 3内容页
	private	   int		modeJspid;//绑定的模板ID
	private   int		bindtypestatus;//继承方式 1 ，向下继承 ，2当前有效
	private   int      typebindId;
	private   String   key;  //主键字段名
	private   String   typefield;  //类别字段名	
	
	
	

	public String getTypefield() {
		return typefield;
	}


	public void setTypefield(String typefield) {
		this.typefield = typefield;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	}


	public int getTypebindId() {
		return typebindId;
	}


	public void setTypebindId(int typebindId) {
		this.typebindId = typebindId;
	}


	public int getBindtypestatus() {
		return bindtypestatus;
	}


	public void setBindtypestatus(int bindtypestatus) {
		this.bindtypestatus = bindtypestatus;
	}


	public int getBindtypeid() {
		return bindtypeid;
	}


	public void setBindtypeid(int bindtypeid) {
		this.bindtypeid = bindtypeid;
	}


	public int getModeJspid() {
		return modeJspid;
	}


	public void setModeJspid(int modeJspid) {
		this.modeJspid = modeJspid;
	}


	public int getBindid() {
		return bindid;
	}


	public void setBindid(int bindid) {
		this.bindid = bindid;
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getTypeid() {
		return typeid;
	}


	public void setTypeid(int typeid) {
		this.typeid = typeid;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getTableName() {
		return tableName;
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public String getTypetableName() {
		return TypetableName;
	}


	public void setTypetableName(String typetableName) {
		TypetableName = typetableName;
	}




	public String getModeJspName() {
		return modeJspName;
	}


	public void setModeJspName(String modeJspName) {
		this.modeJspName = modeJspName;
	}


	public String getModeContentJspName() {
		return modeContentJspName;
	}


	public void setModeContentJspName(String modeContentJspName) {
		this.modeContentJspName = modeContentJspName;
	}


	public String getTypeName() {
		if   (this.typeid==1 ) return  "系统模块";
		else{
			return "自定义模块";
		}
		
	}


	
	
	
	

}
