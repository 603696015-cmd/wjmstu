package com.sopia.schedule.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.Department;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.Tags;


public class Mytest  extends TagSupport 
{
	private String listname;
	
	public int doStartTag() 
	{
		try 
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();

			List<Tags> list_tags = (List<Tags>) request.getAttribute("list_tags");
			//List<Tags> list_tags = (List<Tags>) request.getAttribute(listname);
			
			for(int i=0;i<list_tags.size();i++)
			{
				if(list_tags.get(i).getAdd_display()==1)//display 是否显示
				{
					out.println("<tr>");
					
					out.println("<td>");
					out.println(list_tags.get(i).getName_display()+":");
					out.println("</td>");
					
					out.println("<td>");
					//out.println("<input type='text' />");
					if(list_tags.get(i).getDisplay_type().equals("文本")||
							list_tags.get(i).getDisplay_type().equals("实数")||
							list_tags.get(i).getDisplay_type().equals("整数"))
					{
						out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"' size='50' /");
					}
					else if(list_tags.get(i).getDisplay_type().equals("日期"))
					{
						out.println("<input class='Wdate'  readonly='readonly' type='text'  name='"+list_tags.get(i).getColumn_name()+"' " +
								" onClick='setday(this)' id='releasetime'   size='50'   />");
					}
					else if(list_tags.get(i).getDisplay_type().equals("下拉选项"))
					{
						String str[]=list_tags.get(i).getDefault_value().split("==");
						String str_select_head="<select  name='"+list_tags.get(i).getColumn_name()+"' >";
						String str_default="<option value=''>请选择</option>";
						String str_select_tail="</select>";
						String str_select_body="";
						if(str.length>0)
						{
							for(int j=0;j<str.length;j++)
							{
								
								str_select_body+="<option value='"+str[j]+"'>"+str[j]+"</option>";
							}
						}
						
						out.println(str_select_head+str_default+str_select_body+str_select_tail);
					}
					else if(list_tags.get(i).getDisplay_type().equals("大文本"))
					{
						out.println("<textarea cols='40' rows='10' name='"+list_tags.get(i).getColumn_name()+"'></textarea>");
					}
					else if(list_tags.get(i).getDisplay_type().equals("附件上传"))
					{
						out.println("<script type='text/javascript'>	"	+
									" function addStuff_"+list_tags.get(i).getId()+"() {	 " +
									"   if(document.getElementById('"+list_tags.get(i).getColumn_name()+"').value=='')" +
									"	{" +
									"		alert('请先填写附件名称！！！');" +
									"		return false;" +
									"	}" +
									" 	width=600;	"	+
									" 	height=400;	"	+
									"  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"	+
									"  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	" +
								
									" 	if(null==rv){	"	+
									" 	 	alert('您没选择东西！'); 	"	+
									" 	 	return ;		"	+
									" 	 } 					"	+
						//			" 	 document.getElementById('"+list_tags.get(i).getColumn_name()+"_').innerHTML=rv;	alert('1');		"	+
									" 	 document.getElementById('"+list_tags.get(i).getColumn_name()+"_').value=rv; 		"	+
									" 	 } " +
									" </script> ");
						
						out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"' id='"+list_tags.get(i).getColumn_name()+"' />");//addr
						out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"_' id='"+list_tags.get(i).getColumn_name()+"_'  readonly />");//addr
						out.println("<a  onClick='addStuff_"+list_tags.get(i).getId()+"()'>浏览资源库</a>");
					}
					else if(list_tags.get(i).getDisplay_type().equals("图片"))
					{
						out.println("<script type='text/javascript'>	"	+
								" function addStuff_"+list_tags.get(i).getId()+"() {	 " +
								" 	width=600;	"	+
								" 	height=400;	"	+
								"  	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	"	+
								"  	var rv = window.showModalDialog('question_stuffList.action',null,sFeature);	" +
							
								" 	if(null==rv){	"	+
								" 	 	alert('您没选择东西！'); 	"	+
								" 	 	return ;		"	+
								" 	 } 					" +
								"	 var pos = '.' + rv.replace(/.+\\./, ''); " +
								"   	"+
								"	if(!(pos=='.jpg'||pos=='.png'||pos=='.bmp'))	" +
								"	{	" +
								"		alert('只能上传.jpg,.png,.bmp格式的图片');" +
								"		return ;" +
								"	}"	+
					//			" 	 document.getElementById('"+list_tags.get(i).getColumn_name()+"_').innerHTML=rv;	alert('1');		"	+
								" 	 document.getElementById('"+list_tags.get(i).getColumn_name()+"_').value=rv; 		"	+
								" 	 } " +
								" </script> ");
					
						out.println("高<input type='text' name='"+list_tags.get(i).getColumn_name()+"_h'  size='5' />");//height
						out.println("宽<input type='text' name='"+list_tags.get(i).getColumn_name()+"_w'  size='5'  />");//width
						out.println("<input type='text' name='"+list_tags.get(i).getColumn_name()+"' id='"+list_tags.get(i).getColumn_name()+"_'  readonly />");//addr
						out.println("<a  onClick='addStuff_"+list_tags.get(i).getId()+"()'>浏览资源库</a>");
					}
					
					out.println("</td>");
					out.println("</tr>");
				}
			}
			
			request.setAttribute("list_tags", list_tags);//.setAttribute("list_tags", list_tags);
			
			
			
//			out.println(con.getId());
//			out.println(con.getTheme());
		//	out.println("<%= %>");
			
			
		} catch (Exception e) 
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public String getListname() {
		return listname;
	}

	public void setListname(String listname) {
		this.listname = listname;
	}


}
