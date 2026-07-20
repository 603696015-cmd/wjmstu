package com.sopia.schedule.tags;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.Department;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.TagsUtil;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.CurrentUser;
import com.sopia.schedule.entities.Tags;

/**
 * 查看页面标签
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class Getview  extends TagSupport 
{
	private String listname;


	@SuppressWarnings("unchecked")
	public int doStartTag() 
	{
		try 
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			CurrentUser currentUser = (CurrentUser)request.getAttribute("currentUser");

			List<Tags> list_tags = (List<Tags>) request.getAttribute("list_tags");
			
			List<Integer> list_ricktext = new ArrayList<Integer>();
			
			outPut(list_tags,out,currentUser,list_ricktext);
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	/**
	 * 输出HTML和js
	 * @param list_tags
	 * @param out
	 * @param currentUser
	 * @param list_ricktext
	 * @throws IOException
	 */
	public void outPut(List<Tags> list_tags,JspWriter out,CurrentUser currentUser,List<Integer> list_ricktext) throws IOException{
		int v = TagsUtil.outPutMyloadJs(list_tags, out);
		if(v!=-1){
			list_ricktext.add(v);
		}
		//-----------------------------------jsp---------------------------
		int control_tr=0;
		int control_size=0;
		TagsUtil.outPutViewHTML(list_tags, out, currentUser, control_tr, control_size, list_ricktext);
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}


}

