package com.sopia.schedule.tags;


import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.Department;



public class MyRelateTree extends TagSupport 
{
	private String depid;
	private String tablename;
	private String is_judge;
	private String columnName;
	
	public String getIs_judge() {
		return is_judge;
	}
	public void setIs_judge(String is_judge) {
		this.is_judge = is_judge;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public String getTablename()
	{
		return tablename;
	}
	public void setTablename(String tablename)
	{
		this.tablename = tablename;
	}
	public int doStartTag() 
	{
		
		
		try 
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			String columnname=(String)request.getAttribute("columnname");

			if(columnName!=null&&!columnName.equals("")){
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n" +
						"<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n" +
						"	<script type=\"text/javascript\">\n" +
						"<!--\n" +
						" var d = new dTree(' ',' ', 0, 'd');  " +//创建一个树对象   
						" d.add(0,-1,'本人添加和负责','relateColumn.action?tablename="+tablename+"&columnname="+columnname+"&columnName="+columnName+"&control=1&is_judge="+is_judge+"&rn='+Math.random()); "+
						" document.write(d);" +
						" //-->  "+   
						"</script>   "	); 
			}else{
				out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n" +
						"<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n" +
						"	<script type=\"text/javascript\">\n" +
						"<!--\n" +
						" var d = new dTree(' ',' ', 0, 'd');  " +//创建一个树对象   
						" d.add(0,-1,'本人添加和负责','relateColumn.action?tablename="+tablename+"&columnname="+columnname+"&control=1&is_judge="+is_judge+"&rn='+Math.random()); "+
						" document.write(d);" +
						" //-->  "+   
						"</script>   "	); 
			}
			
			
		}
		catch (Exception ex)
		{
			ex.printStackTrace();
		}
			

		return TagSupport.SKIP_BODY;
	}
	public String getDepid()
	{
		return depid;
	}
	public void setDepid(String depid)
	{
		this.depid = depid;
	}




}

