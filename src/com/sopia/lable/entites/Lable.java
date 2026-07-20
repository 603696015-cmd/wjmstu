package com.sopia.lable.entites;

import java.sql.Timestamp;
import java.util.List;

/**
 * 自定义标签父类
 * @author Administrator
 *
 */
public class Lable {
	private String tablestr;
	private String fieldstr;
	private String sqlCondition;
	private String lable;
	private Integer pageSize;
	private String name;
	
	private String order;
	private String sql;
	private Integer type;
	
	private int labletreeid;
	private LableTree lableTree;
	
	private String keyword;
	
	
	
	private String  orderstatus;
	
	private List<TableField>  field;
	private Timestamp createtime;
	
	
	public Timestamp getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Timestamp createtime) {
		this.createtime = createtime;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public LableTree getLableTree() {
		return lableTree;
	}

	public void setLableTree(LableTree lableTree) {
		this.lableTree = lableTree;
	}

	public int getLabletreeid() {
		return labletreeid;
	}

	public void setLabletreeid(int labletreeid) {
		this.labletreeid = labletreeid;
	}

	public List<TableField> getField() {
		return field;
	}

	public void setField(List<TableField> field) {
		this.field = field;
	}

	public String getOrdername(){
		
		 if("desc".equals(orderstatus))
			 return 	"降序";
		 if("asc".equals(orderstatus))
			 return   "升序";
		 else{
			 return  "无设置";
		 }
	}

	public String getOrderstatus() {
		return orderstatus;
	}
	public void setOrderstatus(String orderstatus) {
		this.orderstatus = orderstatus;
	}
	
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		this.sql = sql;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getSqlCondition() {
		return sqlCondition;
	}
	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}
	public String getLable() {
		return lable;
	}
	public void setLable(String lable) {
		this.lable = lable;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	public Lable() {
		// TODO Auto-generated constructor stub
	}
	public String getTablestr() {
		return tablestr;
	}
	public void setTablestr(String tablestr) {
		this.tablestr = tablestr;
	}
	public String getFieldstr() {
		return fieldstr;
	}
	public void setFieldstr(String fieldstr) {
		this.fieldstr = fieldstr;
	}
	
	

}
