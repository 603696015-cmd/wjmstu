package com.sopia.schedule.tags.dataallocation;


import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElException;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.TagsResolveHTMLForList;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.Tags;

/**
 * ��ݷ���
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class DataAllocationList extends TagSupport{
	private String tablename;
	private List<Tags> list_tags;
	private List<Map<String,String>> list_designe;

	private String type;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			tablename = (String)request.getAttribute("tablename");
			list_tags = (List<Tags>)request.getAttribute("list_tags");
			list_designe = (List<Map<String,String>>)request.getAttribute("list_designe");
			
			outPut(pageContext,list_tags,list_designe,out);
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	public void outPut(PageContext pageContext,List<Tags> list_tags,List<Map<String,String>> list_designe,JspWriter out) throws IOException, NumberFormatException, ElException{
		TagsResolveHTMLForList.resolveHTMLForDataAllocation(getType(),pageContext, list_tags, list_designe, out);
	}

	public List<Tags> getList_tags() {
		return list_tags;
	}

	public void setList_tags(List<Tags> list_tags) {
		this.list_tags = list_tags;
	}

	public List<Map<String, String>> getList_designe() {
		return list_designe;
	}

	public void setList_designe(List<Map<String, String>> list_designe) {
		this.list_designe = list_designe;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	
	
	
}
