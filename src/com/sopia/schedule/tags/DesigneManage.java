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
import com.sopia.schedule.entities.CurrentUser;
import com.sopia.schedule.entities.Tags;

public class DesigneManage extends TagSupport
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

			CurrentUser currentUser = (CurrentUser)request.getAttribute("currentUser");
			
			List<Tags> list_tags = (List<Tags>) request
					.getAttribute("list_tags");
			// List<Tags> list_tags = (List<Tags>)
			// request.getAttribute(listname);

			// List<Map<String, String>> list_designe = (List<Map<String,
			// String>>) request
			// .getAttribute("list_designe");
			// 显示table标题

			// 项目名称 顺序号 项目类型 是否显示
//---------------------------js  验证----------------------------
			
			out.println("<script type='text/javascript'>");
			
			out.println("function checkRate(value)" +
			"{ " +
			"     var re = /^[1-9]+[0-9]*]*$/ ;"+   ///^[0-9]+.?[0-9]*$/; " +  //判断字符串是否为数字     //判断正整数 /^[1-9]+[0-9]*]*$/   
			"     if (!re.test(value))" +
			"    {   " +
			"	        return false;" +
			"	     }" +
			" return true;" +
			"	}" );
			
			out.println("function doSubmit(){");
			
			
			for(int i=0;i<list_tags.size();i++)
			{
				
				out.println(
						"if(!checkRate(document.all.sn_"+list_tags.get(i).getId()+".value))" +
						"{" +
						"   alert('"+list_tags.get(i).getName_display()+"顺序号只能正整数！！！');" +
						"	return false;" +
						"} ");
				
				if(!list_tags.get(i).getDisplay_type().equals("富文本"))
				{
				
					out.println(
							"if(parseInt(document.all.sn_"+list_tags.get(i).getId()+".value)>parseInt(60000)||" +
									"parseInt(document.all.sn_"+list_tags.get(i).getId()+".value)<parseInt(0))" +
							"{" +
							"   alert('"+list_tags.get(i).getName_display()+"顺序号不能超过60000且不能为负数！！！');" +
							"	return false;" +
							"} ");
				}
			}
			
			
			


			
			
			out.println("}");
			out.println("</script>");
//----------------------------------------------------------			
			out.println("<table cellpadding='1' cellspacing='1' width='1000'>");
			out.println("<caption><span style= 'color:red' id='caption'><span></caption>");
			out.println("<tr>");
			out.println("<th>项目名称</th>");
			out.println("<th>顺序号</th>");
			out.println("<th>项目类型</th>");
			out.println("<th>是否显示</th>");
			out.println("<th colspan='2'>操作</th>");
			out.println("</tr>");
			
			out.println("<tbody onMouseOut=\"changeback()\" onMouseOver=\"changeto()\">");
			
			//当前用户信息
			if(currentUser != null){
				out.println("<tr>");
				out.println("<td align='center'><span style='color:red'>当前用户信息(用户/部门)</span>");
				out.println("</td>");
				out.println("<td align='center'>-</td><td align='center'>-</td>");
				out.println("<td>");
				
				out.println("<input type='checkbox'   name='user_add'  ");
				if(currentUser.getUser_add() == 1){
					out.println("value=1 checked ");
				}else {
					out.println("value=0 ");
				}
				out.println("onclick='check_user(this);' ");
				out.println("/>添加页");
				
				out.println("<input type='checkbox'   name='user_update'  ");
				if(currentUser.getUser_update() == 1){
					out.println("value=1 checked ");
				}else {
					out.println("value=0 ");
				}
				out.println("onclick='check_user(this);' ");
				out.println("/>修改页");
				
				out.println("<input type='checkbox'   name='user_view'  ");
				if(currentUser.getUser_view() == 1){
					out.println("value=1 checked ");
				}else {
					out.println("value=0 ");
				}
				out.println("onclick='check_user(this);' ");
				out.println("/>查看页");
				out.println("</td>");
				
				out.println("<td align='center'>-</td><td align='center'>-</td>");
				out.println("</tr>");
			}
			
			
			 for (int i = 0; i < list_tags.size(); i++)
			 {
				 out.println("<tr>");
				 
				 out.println("<td align='center'>");
				 out.println(list_tags.get(i).getName_display()+"(<span style='color:red'>"+list_tags.get(i).getColumn_name()+"</span>)");
				 out.println("</td>");
				 
				 out.println("<td >");
				 if(!list_tags.get(i).getDisplay_type().equals("富文本"))
					 out.println("<input type='text' name='sn_"+list_tags.get(i).getId()+"' value='"+list_tags.get(i).getSn()+"' /> ");
				 else
					 out.println("<input type='hidden' name='sn_"+list_tags.get(i).getId()+"' value='"+list_tags.get(i).getSn()+"' /> ");
				 out.println("</td>");
							
				 out.println("<td align='center'>");
				 out.println(list_tags.get(i).getDisplay_type());
				 out.println("</td>");	
							
				 out.println("<td >");

				 if(list_tags.get(i).getCannot_modify()==1)
				 {
					 out.println("<span style='color:red' >此项必须显示</span>");
				 }
				 else
				 {
					 String str="";
					 if(list_tags.get(i).getAdd_display()==1) str="checked";
					 else str="";
					 out.println("<input type='checkbox' name='display_"+list_tags.get(i).getId()+"' value ='add_display' "+str+" />添加页");
					 if(list_tags.get(i).getUpdate_display()==1) str="checked";
					 else str="";
					 out.println("<input type='checkbox' name='display_"+list_tags.get(i).getId()+"' value ='update_display' "+str+"/>修改页");
					 if(list_tags.get(i).getView_display()==1) str="checked";
					 else str="";
					 out.println("<input type='checkbox' name='display_"+list_tags.get(i).getId()+"' value ='view_display'  "+str+"/>查看页");
					 if(!list_tags.get(i).getDisplay_type().equals("音频")&&!list_tags.get(i).getDisplay_type().equals("附件上传")&&!list_tags.get(i).getDisplay_type().equals("大文本")&&!list_tags.get(i).getDisplay_type().equals("图片")&&!list_tags.get(i).getDisplay_type().equals("富文本"))
					 {
						 if(list_tags.get(i).getList_display()==1) str="checked";
						 else str="";
						 out.println("<input type='checkbox' name='display_"+list_tags.get(i).getId()+"' value ='list_display'   "+str+"/>列表页");
						 if(list_tags.get(i).getMutilsearch_display()==1) str="checked";
						 else str="";
						 out.println("<input type='checkbox' name='display_"+list_tags.get(i).getId()+"' value ='mutilsearch_display'  "+str+"/>组合查询");
						 if(list_tags.get(i).getDepartsearch_display()==1) str="checked";
						 else str="";
						 out.println("<input type='checkbox' name='display_"+list_tags.get(i).getId()+"' value ='departsearch_display'  "+str+"/>部门查询");
						 //out.println("id:"+list_tags.get(i).getId());
						 
					 }
					 if(!list_tags.get(i).getDisplay_type().equals("富文本"))
					 {
						 if(list_tags.get(i).getRequired()==1) str="checked";
						 else str="";
						 out.println("<input type='checkbox' name='display_"+list_tags.get(i).getId()+"' value ='required'  "+str+"/>是否必填");
					 }
				 }
				 
				 out.println("</td>");	
				 out.println("<td><a href='deleteDesigneTags.action?tablename="+list_tags.get(i).getTable_name()+"&columnName="+list_tags.get(i).getColumn_name()+"&id="+list_tags.get(i).getId()+"'>删除</td>");
				 out.println("<td><a href='updateDesigneTagsInit.action?id="+list_tags.get(i).getId()+"&tablename="+list_tags.get(i).getTable_name()+"'>修改</td>");
				 out.println("</tr>");
							
			 }// for
			 
			 out.println("<tr>");
			 out.println("<td colspan='6'  align='center'>");
			 out.println("<span style='color:red'>顺序号最大值不超过60000！！！</span>");
			 out.println("</td>");
			 out.println("</tr>");
			 
			 out.println("<tbody>");
			 
			 out.println("</table>");
			// out.println("<th>操作</th>");
			// out.println("</tr>");
			// request.setAttribute("list_tags",
			// list_tags);//.setAttribute("list_tags", list_tags);

			// 显示列表值
			// for (int i = 0; i < list_designe.size(); i++)
			// {
			//
			// out.println("<tr>");
			//
			// // out.println("<input type='text' />");
			// // Iterator iterator =
			// // list_designe.get(i).entrySet().iterator();
			// // // while(iterator.hasNext())
			// // // {
			// // java.util.Map.Entry entry =
			// // (java.util.Map.Entry)iterator.next();
			//
			// for (int j = 0; j < list_tags.size(); j++)
			// {
			// if (list_tags.get(j).getList_display() == 1)
			// {
			// String str = list_designe.get(i).get(
			// list_tags.get(j).getColumn_name());
			// if (str == null)
			// str = "";
			// out.println("<td align='center'>");
			// //显示类型为附件的时候
			// if(list_tags.get(j).getDisplay_type().equals("附件上传"))
			// {
			// String str2[]=str.split("==");
			// out.println("<label>" + str2[0] + "</label>");
			// }
			// else
			// {
			// out.println("<label>" + str + "</label>");
			// }
			// out.println("</td>");
			// }
			// // }
			// }
			// out.println("<td align='center'>");
			// out.println("<a href='javascript:view("
			// + list_designe.get(i).get("id") + ")'>查看</a>");
			// out.println("<a href='javascript:update("
			// + list_designe.get(i).get("id") + ")'>修改</a>");
			// out.println("<a href='javascript:del("
			// + list_designe.get(i).get("id") + ")'>删除</a>");
			// out.println("</td>");
			// out.println("</tr>");
			// }// for

			// out.println(con.getId());
			// out.println(con.getTheme());
			// out.println("<%= %>");

		}
		catch (Exception e)
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
