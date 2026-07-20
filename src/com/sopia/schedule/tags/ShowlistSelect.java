package com.sopia.schedule.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.schedule.entities.Tags;

public class ShowlistSelect extends TagSupport
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

	public int doStartTag()
	{
		try
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();

			
			
			List<Tags> list_tags = (List<Tags>) request
					.getAttribute("list_tags");
			
			// List<Tags> list_tags = (List<Tags>)
			// request.getAttribute(listname);

//			String tablename="";
//			tablename=list_tags.get(0).getTable_name();
			
			List<Map<String, String>> list_designe = (List<Map<String, String>>) request
					.getAttribute("list_designe");
			
			
			// 显示table标题
			String width="";int k=0;
			for(int i=0;i<list_tags.size();i++)
			{
				if(list_tags.get(i).getList_display()==1)
					k++;
			}
			
			k=k*100;
			if(k<1000)
				width=" width='100%'" ;
			else
				width = " width='"+k+"px'";
			
			out.println("<table "+width+" align='center' cellpadding='1' cellspacing='1'>");
			
			out.println("<tr>");
			for (int i = 0; i < list_tags.size(); i++)
			{
				out.println("<th></th>");
				if (list_tags.get(i).getList_display() == 1)// display
				{

					out.println("<th><a href=\"javascript:columnsearch('"+ list_tags.get(i).getColumn_name()+"');\" >" + list_tags.get(i).getName_display()
							+ "</a></th>");

				}
			}// for

			out.println("<th><a href=\"javascript:columnsearch('status');\" >状态</a></th>");
			out.println("</tr>");
			// request.setAttribute("list_tags",
			// list_tags);//.setAttribute("list_tags", list_tags);

			
			out.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");
			
			// 显示列表值
			for (int i = 0; i < list_designe.size(); i++)
			{
				out.println("<tr>");
				out.println("<td><input type='radio' name='radio' value='"+list_designe.get(i).get("id")+"'></td>");
				// out.println("<input type='text' />");
				// Iterator iterator =
				// list_designe.get(i).entrySet().iterator();
				// // while(iterator.hasNext())
				// // {
				// java.util.Map.Entry entry =
				// (java.util.Map.Entry)iterator.next();

				for (int j = 0; j < list_tags.size(); j++)
				{
					if (list_tags.get(j).getList_display() == 1)
					{
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if (str == null)
							str = "";
						out.println("<td  id='"+list_tags.get(j).getColumn_name()+":"+j+"' align='center'>");
						//显示类型为附件的时候
						if(list_tags.get(j).getDisplay_type().equals("附件上传"))
						{
							String str2[]=str.split("==");
							out.println("<label>" + str2[0] + "</label>");
						}
//						if(list_tags.get(j).getDisplay_type().equals("日期")){
//							if(list_tags.get(j).getJindutiao() == 1){//显示进度条
//								out.println("<div id='time_jindu_div' style='border: 1px dotted #FF6633;width:200px'><img height='14' src='images/jd.gif' width='"+str+"%'  id='show_time_jindu'/></div>");
//							}else{
//								out.println("<label>" + str + "</label>");
//							}
//						}
						//百分比
						if(list_tags.get(j).getDisplay_type().equals("百分比"))
						{
							if(list_tags.get(j).getJindutiao() == 1){//显示进度条
								out.println("<div id='jindutiao_div' style='border: 1px dotted #FF6633;width:200px'><img height='14' src='images/jd.gif' width='"+str+"%'  id='show_jindutiao'/></div>");
							}else{
								if(str.equals("")){
									out.println("<label>" + 0 + "%</label>");
								}else{
									out.println("<label>" + str + "%</label>");
								}
							}
						}
						else
						{
							out.println("<label>" + str + "</label>");
						}
						out.println("</td>");
					}
					// }
				}
				out.println("<td align='center'>");
				out.println(list_designe.get(i).get("status"));
				out.println("</td>");
				out.println("</tr>");
			}// for

			// out.println(con.getId());
			// out.println(con.getTheme());
			// out.println("<%= %>");
			out.println("</tbody>");
			out.println("</table>");
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
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
