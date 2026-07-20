package com.sopia.schedule.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.Department;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.Tags;


public class CombinesearchForProduce  extends TagSupport 
{
	private String listname;
	private List<String> moduleids;
	
	public int doStartTag() 
	{
		try 
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			moduleids = (List<String>)request.getAttribute("moduleids");
			
			out.println("<TD vAlign=center align=middle width=120 bgColor=#ffffff rowSpan=100>" +
			"<INPUT class=btn1_mouseout onMouseOver=\"this.className='btn1_mouseover'\" onMouseOut=\"this.className='btn1_mouseout'\" onclick=search(); type=button value=开始搜索></TD>");
			
			String modules = "";
			modules += "<tr>" +
			"<td>模块名称(实际为表名)</td>" +
			"<td>" +
			"<select name='moduleStatus.moduleid' onchange='this.value=this.options[this.selectedIndex].value'>" +
			"<option value=''>请选择模块(即表名)</option>" +
			"";
			for(String str:moduleids){
				modules += "<option value='"+str.split("==")[1]+"'>" +
				str.split("==")[0] +
				"</option>";
			}
			modules += "</select>" +
			"</td>" ;
			
			String danjustatus="";
			danjustatus += 
					"<td>单据状态</td>" +
					"<td>" +
					"<select name='moduleStatus.status' onchange='this.value=this.options[this.selectedIndex].value'>" +
					"<option value=''>请选择单据状态</option>" +
					"<option value='其他'>其他</option>" +			//0 或者 2
//					"<option value='初审等待中'>初审等待中</option>" +	//2
//					"<option value='终审等待中'>终审等待中</option>" +	//3
//					"<option value='修改等待中'>修改等待中</option>" +	//4
//					"<option value='删除等待中'>删除等待中</option>" +	//5
					"<option value='通过'>通过</option>" +				//1
					"</select>" +
					"</td>" +
					"</tr>";
			
			out.println(modules + danjustatus);
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}

	public List<String> getModuleids() {
		return moduleids;
	}

	public void setModuleids(List<String> moduleids) {
		this.moduleids = moduleids;
	}


}
