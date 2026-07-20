package com.sopia.schedule.tags;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElException;
import com.sopia.schedule.TagsResolveHTMLForList;
import com.sopia.schedule.entities.Tags;
/**
 * 查看页面显示相关字段的完整
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class ShowlistRelate extends TagSupport
{
	private String listname;
	private String delaction;
	private String viewaction;
	private String updateaction;

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
			
			List<Tags> list_tags = (List<Tags>) request.getAttribute("list_tags");
			
			Map<String,List<Tags>> list_tags_relate = (Map<String,List<Tags>>) request.getAttribute("list_tags_relate");
			Map<String,List<Map<String,String>>> list_designe_relate = (Map<String,List<Map<String,String>>>) request.getAttribute("list_designe_relate");
			
			outPut(out,pageContext,list_designe_relate,list_tags_relate,list_tags);
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	public void outPut(JspWriter out,PageContext pageContext,Map<String,List<Map<String,String>>> list_designe_relate,Map<String,List<Tags>> list_tags_relate,List<Tags> list_tags) throws NumberFormatException, IOException, ElException{
		TagsResolveHTMLForList.resolveHTMLForShowlistRelate(out, pageContext, list_designe_relate, list_tags_relate, list_tags);
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
