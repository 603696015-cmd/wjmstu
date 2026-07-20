package com.sopia.schedule.tags;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.Department;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.Tags;

public class ChargeShowlist extends TagSupport
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
			out.println("<tr>");
			for (int i = 0; i < list_tags.size(); i++)
			{
				if (list_tags.get(i).getList_display() == 1)// display
				{

					out.println("<th>" + list_tags.get(i).getName_display()
							+ "</th>");

				}
			}// for
			out.println("<th>负责人</th>");
			out.println("<th>状态</th>");
			out.println("<th>操作</th>");
			out.println("</tr>");
			// request.setAttribute("list_tags",
			// list_tags);//.setAttribute("list_tags", list_tags);

			// 显示列表值
			for (int i = 0; i < list_designe.size(); i++)
			{

				out.println("<tr>");

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
						out.println("<td  align='center'>");
						//显示类型为附件的时候
						if(list_tags.get(j).getDisplay_type().equals("附件上传"))
						{
							String str2[]=str.split("==");
							out.println("<label>" + str2[0] + "</label>");
						}
						//百分比
						if(list_tags.get(j).getDisplay_type().equals("百分比"))
						{
							if(list_tags.get(j).getJindutiao() == 1){//显示进度条
								out.println("<div id='jindutiao_div__"+list_tags.get(j).getColumn_name()+":"+j+"' style='border: 1px dotted #FF6633;width:99%'><img height='14' src='images/jd.gif' width='"+str+"%'  id='show_jindutiao__"+list_tags.get(j).getColumn_name()+":"+j+"'/></div>");
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
				out.println(list_designe.get(i).get("principal"));
				out.println("</td>");
				out.println("<td align='center'>");
				out.println(list_designe.get(i).get("status"));
				out.println("</td>");
				//out.println("<td align='center>vvvvvv</td>");
				out.println("<td align='center'>");
				out.println("<a href='javascript:view("
						+ list_designe.get(i).get("id") + ")'>查看</a>");
				
				if(list_designe.get(i).get("status").equals("已创建"))
				{
					out.println("<a href='javascript:update("
							+ list_designe.get(i).get("id") + ")'>修改</a>");
					out.println("<a href='javascript:del("
							+ list_designe.get(i).get("id") + ")'>删除</a>");
					out.println("<a href='javascript:commit("
							+ list_designe.get(i).get("id") + ")'>提交初审</a>");
				}
				if(list_designe.get(i).get("status").equals("通过"))
				{
					out.println("<a href='javascript:apply_update("
							+ list_designe.get(i).get("id") + ")'>申请修改</a>");
					out.println("<a href='javascript:apply_del("
							+ list_designe.get(i).get("id") + ")'>申请删除</a>");
				}
				out.println("</td>");
				out.println("</tr>");
			}// for

			// out.println(con.getId());
			// out.println(con.getTheme());
			// out.println("<%= %>");

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
