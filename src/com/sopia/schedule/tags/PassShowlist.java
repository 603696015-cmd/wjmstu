package com.sopia.schedule.tags;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.TagsResolveHTMLForList;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.ModuleZDY;
import com.sopia.schedule.entities.Tags;
/**
 * 公共查询页列表
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class PassShowlist extends TagSupport
{
	private String listname;
	private String delaction;
	private String viewaction;
	private String updateaction;
	private String actionName;
	
	private String type;
	

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}

	public String getViewaction()
	{
		return viewaction;
	}

	public void setViewaction(String viewaction)
	{
		this.viewaction = viewaction;
	}

	public String getUpdateaction()
	{
		return updateaction;
	}

	public void setUpdateaction(String updateaction)
	{
		this.updateaction = updateaction;
	}
	
	@SuppressWarnings("unchecked")
	public int doStartTag()
	{
		try
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();

			actionName = (String)request.getAttribute("actionName");
			
			List<Tags> list_tags = (List<Tags>) request
					.getAttribute("list_tags");
			
			List<Map<String, String>> list_designe = (List<Map<String, String>>) request
					.getAttribute("list_designe");
			
			ModuleManage moduleManage =  (ModuleManage)request.getAttribute("moduleManage");
			ModuleZDY moduleZDY = (ModuleZDY)request.getAttribute("moduleZDY");
			
			
			outPut(pageContext,list_tags,out,moduleZDY,list_designe,actionName,moduleManage);

		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public void outPut(PageContext pageContext,List<Tags> list_tags,JspWriter out,ModuleZDY moduleZDY,List<Map<String,String>> list_designe,String actionName,ModuleManage moduleManage) throws IOException, NumberFormatException, ElException{
		TagsResolveHTMLForList.resolveHTMLForMyPassContactTags(getType(),pageContext,list_tags, out, moduleZDY, list_designe, actionName,moduleManage);
	}
	
	public String getListname()
	{
		return listname;
	}

	public void setListname(String listname)
	{
		this.listname = listname;
	}

	public String getDelaction()
	{
		return delaction;
	}

	public void setDelaction(String delaction)
	{
		this.delaction = delaction;
	}

}
