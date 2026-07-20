package com.sopia.schedule.tags.template;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.schedule.entities.Tags;

/**
 * ½âÎö×Ö¶ÎÃû³Æ
 */
public class TBHTMLName extends TagSupport {
	
	private String iname;
	private String tablename;
	private List<Tags> list_tags ;
	
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			list_tags = (List<Tags>) request.getAttribute("list_tags");
			iname = getIname();
			tablename = (String)request.getAttribute("tablename");
			
			if(list_tags!=null&&list_tags.size()>0){
				for(Tags tags:list_tags){
					if(tags!=null&&!tags.getColumn_name().equals("")){
						if(tags.getColumn_name().equals(iname)){
							out.println(tags.getName_display());
							break;
						}
					}
				}
			}
			
			
		}catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public String getIname() {
		return iname;
	}

	public void setIname(String iname) {
		this.iname = iname;
	}

	public String getTablename() {
		return tablename;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public List<Tags> getList_tags() {
		return list_tags;
	}

	public void setList_tags(List<Tags> list_tags) {
		this.list_tags = list_tags;
	}
	
	

}
