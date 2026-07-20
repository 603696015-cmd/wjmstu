package com.sopia.common.BeanGenerator;

public class BeanSqlSplice {
	public int IC_id;//InsuranceCategories  ID
	public String viewType;//显示类型
	public String tableName;//表名
	public String column_name;//列名
	public String view_name;//页面显示名称
	public String parametersType;// 参数类型
	public String createSQL;//创建表sql
	public String addSQL;//增加数据sql
	public String addColumnSQL;//增加列数据sql
	public String viewType_value;//列数据范围
	
	public String relateTableName;//关联表
	public String relateColumnName;//关联列
	
	public int is_qiuji;//是否求积	0：不求积、1：作为求积的一方
	public int is_qiuhe;//是否求和
	public int is_zuoweiji;//是否作为积
	public int is_zuoweihe;//是否作为和
	public int from_entity;//单价是否从保险产品中获取
	public String qiujiRelateColumn;//求积关联字段
	public String qiuheRelateColumn;//求和关联字段
	
	

	public int getFrom_entity() {
		return from_entity;
	}
	public void setFrom_entity(int from_entity) {
		this.from_entity = from_entity;
	}
	public String getQiuheRelateColumn() {
		return qiuheRelateColumn;
	}
	public void setQiuheRelateColumn(String qiuheRelateColumn) {
		this.qiuheRelateColumn = qiuheRelateColumn;
	}
	public String getQiujiRelateColumn() {
		return qiujiRelateColumn;
	}
	public void setQiujiRelateColumn(String qiujiRelateColumn) {
		this.qiujiRelateColumn = qiujiRelateColumn;
	}
	public int getIs_zuoweiji() {
		return is_zuoweiji;
	}
	public void setIs_zuoweiji(int is_zuoweiji) {
		this.is_zuoweiji = is_zuoweiji;
	}
	public int getIs_zuoweihe() {
		return is_zuoweihe;
	}
	public void setIs_zuoweihe(int is_zuoweihe) {
		this.is_zuoweihe = is_zuoweihe;
	}
	public int getIs_qiuji() {
		return is_qiuji;
	}
	public void setIs_qiuji(int is_qiuji) {
		this.is_qiuji = is_qiuji;
	}
	public int getIs_qiuhe() {
		return is_qiuhe;
	}
	public void setIs_qiuhe(int is_qiuhe) {
		this.is_qiuhe = is_qiuhe;
	}
	public String getViewType_value() {
		return viewType_value;
	}
	public void setViewType_value(String viewType_value) {
		this.viewType_value = viewType_value;
	}
	public BeanSqlSplice(){ 
	}	
	public BeanSqlSplice(String tableName){
		this.tableName = tableName;
	}
	public BeanSqlSplice(int id ,String tableName){
		this.IC_id = id;
		this.tableName = tableName;
	}	
	public String getCreateSQL() {
		return createSQL;
	}
	public void setCreateSQL(String createSQL) {
		this.createSQL = createSQL;
	}
	public String getViewType() {
		return viewType;
	}
	public String[] getViewType_() { 
		return "单行文本==多行文本==下拉列表==数字类型==小数==日期==单选==复选==图片==富文本".split("==");
	} 
	public void setViewType(String viewType) {
		this.viewType = viewType;
	}


	public String getTableName() {
		return tableName.toUpperCase();
	}


	public void setTableName(String tableName) {
		this.tableName = tableName;
	}


	public void setParametersType(String parametersType) {
		this.parametersType = parametersType;
	}

	public String getParametersType() {
		return parametersType;
	}
	public String[] getParametersTypes_(){ 
		return "字符类型==数字类型==小数类型==时间类型==BLOB".split("==");
	}
	public String getParametersType_(){ 
		if(this.parametersType.equals("数字类型"))
			return "NUMBER";
		if(this.parametersType.equals("小数类型"))
			return "NUMBER(16,2)";
		if(this.parametersType.equals("字符类型"))
			return "varchar2(4000)";
		if(this.parametersType.equals("时间类型")) 
			return "TIMESTAMP";
		if(this.parametersType.equals("BLOB")) 
			return "BLOB";
		return "";
	}
	public int getIC_id() {
		return IC_id;
	}
	public void setIC_id(int ic_id) {
		IC_id = ic_id;
	}
	public String getAddSQL() {
		return addSQL;
	}
	public void setAddSQL(String addSQL) {
		this.addSQL = addSQL;
	}    
	public String getColumn_name() {
		return column_name;
	}
	public void setColumn_name(String column_name) {
		this.column_name = column_name;
	}
	public String getColumn_name_() {
		return "KS_"+column_name;
	}
	public String getView_name() {
		return view_name;
	}
	public void setView_name(String view_name) {
		this.view_name = view_name;
	}
	
	public String getRelateTableName() {
		return relateTableName;
	}
	public void setRelateTableName(String relateTableName) {
		this.relateTableName = relateTableName;
	}
	public String getRelateColumnName() {
		return relateColumnName;
	}
	public void setRelateColumnName(String relateColumnName) {
		this.relateColumnName = relateColumnName;
	}
	//增加列 需同时执行getAddColumnSQL_  与 getAddColumnSQL_comment
	public String getAddColumnSQL_() {
		String sql = " alter table "+this.getTableName()+" add "+this.getColumn_name_()+" "+ this.getParametersType_();
		return sql;
	}
	public String getAddColumnSQL_comment() {
		String sql;
		if(this.viewType_value != null && !this.viewType_value.equals(""))
			sql = " comment on column "+this.tableName+"."+this.getColumn_name_()+"  is '"+this.view_name+"=="+this.viewType+"=="+this.viewType_value+"'";
		else 
			sql = " comment on column "+this.tableName+"."+this.getColumn_name_()+"  is '"+this.view_name+"=="+this.viewType+"'";
		return sql;
	}
	public String getAddSQL_() {
		String addSql ="insert into "+this.tableName+" (IC_id) values ("+this.IC_id+")";
		return addSql;
	} 
	
	public String getAddColumnRelateInfo(){
		String sql = "insert into ic_column_relate (table_name,column_name,relate_table_name,relate_column_name)" +
				" values ('"+this.getTableName()+"','"+this.getColumn_name_()+"','"+this.getRelateTableName()+"','"+this.getRelateColumnName()+"')";
		return sql;
	}
	public String getAddColumnSQL() {
		return addColumnSQL;
	}
	public void setAddColumnSQL(String addColumnSQL) {
		this.addColumnSQL = addColumnSQL;
	}
	
	public String getAddColumnSQL_qiuji_qiuhe(){
		String sql = "insert into ic_column_qiuji_qiuhe (table_name,column_name,is_qiuhe,is_qiuji,is_zuoweihe,is_zuoweiji,qiuhe_column_name,qiuji_column_name,from_entity)" +
		" values ('"+this.getTableName()+"','"+this.getColumn_name_().toUpperCase()+"','"+this.getIs_qiuhe()+"','"+this.getIs_qiuji()+"','"+this.getIs_zuoweihe()+"','"+this.getIs_zuoweiji()+"','"+this.getQiuheRelateColumn()+"','"+this.getQiujiRelateColumn()+"','"+this.getFrom_entity()+"')";
		return sql;
	}
	
}
