package com.sopia.schedule.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.Department;
import com.sopia.schedule.TagsUtil;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.Tags;

/**
 * 列表上的搜索标签
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class Combinesearch  extends TagSupport 
{
	private String listname;
	private int list_size;
	

	public int getList_size() {
		return list_size;
	}

	public void setList_size(int list_size) {
		this.list_size = list_size;
	}
	
	@SuppressWarnings("unchecked")
	public int doStartTag() 
	{
		try 
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();

			List<Tags> list_tags = (List<Tags>) request.getAttribute("list_tags");
			
			list_size = list_tags.size();
			for(int i=0;i<list_tags.size();i++){
				if(list_tags.get(i).getDisplay_type().equals("富文本")){
					list_size--;
					break;
				}
			}
			
			int id=0;
			if(request.getAttribute("id")!=null)
				id=(Integer)request.getAttribute("id");
			
			int rx = 0;
			if(request.getAttribute("rx")!=null)
				rx=(Integer)request.getAttribute("rx");
			
			String realname = "";
			if(request.getAttribute("realname")!=null)
				realname=(String)request.getAttribute("realname");
			
			outPut(list_tags, out, rx, realname);
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	public void outPut(List<Tags> list_tags, JspWriter out, int rx, String realname) throws IOException{
		int control_tr = 0;
		int control_size = 0;
		TagsUtil.outPutCombinesearch(list_tags, out, rx, realname,control_tr,control_size);
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}


}
