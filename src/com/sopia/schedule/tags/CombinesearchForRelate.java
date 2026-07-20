package com.sopia.schedule.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.schedule.TagsUtil;
import com.sopia.schedule.entities.Tags;

/**
 * 选择相关数据时页面上的搜索标签
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class CombinesearchForRelate  extends TagSupport 
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
			
			outPut(list_tags,out);
			
			
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	/**
	 * 输出选择相关页面的搜索标签
	 * @param list_tags
	 * @param out
	 * @throws IOException
	 */
	public void outPut(List<Tags> list_tags,JspWriter out) throws IOException{
		TagsUtil.outPutCombinesearchForRelate(list_tags, out);
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}


}
