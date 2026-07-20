package com.sopia.schedule.tags;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElException;
import com.sopia.duman.entities.Department;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.Contact;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.ModuleZDY;
import com.sopia.schedule.entities.Tags;

public class FinalDepartShowlistForProduce extends TagSupport
{
	private String listname;
	private String delaction;
	private String viewaction;
	private String updateaction;
	private Integer final_;
	
	public String getStatus_chinese(int status) throws ElException{
		TagsDaoImpl tagsDao = new TagsDaoImpl();
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		List<CustomAudit> cas =  tagsDao.get_audits_by_tablename((String)request.getAttribute("tablename"));
		if(status == 0){
			return "已创建";
		}
		if(status == 2){return "修改等待中";}
		if(status == 3){return "删除等待中";}
		if(status == 5){return "初审等待中";}
		if(status == 6){return "初审通过";}
		if(status == 7){return "初审不通过";}
		if(status == 8){return "终审等待中";}
		if(status == 9){return "终审通过";}
		if(status == 10){return "终审不通过";}
		for(int i=0;i<cas.size();i++){
			if(status == Integer.parseInt(cas.get(i).getAuditOrder())*2 + 10){
				return cas.get(i).getAuditName() + "通过";
			}
			if(status == Integer.parseInt(cas.get(i).getAuditOrder())*2 + 10 + 1){
				return cas.get(i).getAuditName() + "不通过";
			}
		}
		return "";
	}

	public Integer getFinal_() {
		return final_;
	}

	public void setFinal_(Integer final_) {
		this.final_ = final_;
	}

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

			List<Map<String, String>> list_designe = (List<Map<String, String>>) request
					.getAttribute("list_designe");
			
			ModuleManage moduleManage =  (ModuleManage)request.getAttribute("moduleManage");
			ModuleZDY moduleZDY = (ModuleZDY)request.getAttribute("moduleZDY");
			
			final_ = (Integer)request.getAttribute("final_");
			System.out.println(final_);
			// 显示table标题
			String width="";int k=0;
			for(int i=0;i<list_tags.size();i++)
			{
				if(list_tags.get(i).getDepartsearch_display()==1)
					k++;
			}
			
			k=k*100;
			if(k<1000)
				width=" width='100%'" ;
			else
				width = " width='"+k+"px'";
			
			out.println("<table "+width+" align='center' cellpadding='1' cellspacing='1'>");
			out.println("<tr>");
			out.println("<th width='20'></th>");
			for (int i = 0; i < list_tags.size(); i++)
			{
				if (list_tags.get(i).getDepartsearch_display() == 1)// display
				{

//					out.println("<th>" + list_tags.get(i).getName_display()
//							+ "</th>");
					out.println("<th><a href=\"javascript:columnsearch('"+ list_tags.get(i).getColumn_name()+"');\" >" + list_tags.get(i).getName_display()
							+ "</a></th>");
					
				}
			}// for

			
//			out.println("<th><a href=\"javascript:columnsearch('e.username');\" >用户</a></th>");
	//		out.println("<th><a href=\"javascript:columnsearch('principalname');\" >负责人</a></th>");
//			out.println("<th><a href=\"javascript:columnsearch('d.name');\" >部门</a></th>");
			out.println("<th><a href=\"javascript:columnsearch('t.status');\" >状态</a></th>");
			
			out.println("<th>操作</th>");
			out.println("<th >模块名称</th>");
			out.println("</tr>");
			// request.setAttribute("list_tags",
			// list_tags);//.setAttribute("list_tags", list_tags);
			out.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");
			// 显示列表值
			for (int i = 0; i < list_designe.size(); i++)
			{

				out.println("<tr>");
				out.println("<td width='20' height='20' align='center'>" +
						"<input type='checkbox' value='"+list_designe.get(i).get("id")+"' name='id_'/>"+
						"</td>");
				// out.println("<input type='text' />");
				// Iterator iterator =
				// list_designe.get(i).entrySet().iterator();
				// // while(iterator.hasNext())
				// // {
				// java.util.Map.Entry entry =
				// (java.util.Map.Entry)iterator.next();
				for (int j = 0; j < list_tags.size(); j++)
				{
					if (list_tags.get(j).getDepartsearch_display() == 1)
					{
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if(list_tags.get(j).getDisplay_type().equals("日期")){
							str = ScheduleUtil.dateFormat(str, list_tags.get(j).getTimeformat());
						}else{
							if (str == null)
								str = "";
						}
//						out.println("<td  align='center'>");
						if(list_tags.get(j).getJindutiao() == 1){
							out.println("<td  id='"+list_tags.get(j).getColumn_name()+":"+j+"' align='left'>");
						}else{
							out.println("<td  id='"+list_tags.get(j).getColumn_name()+":"+j+"' align='center'>");
						}
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
								BigDecimal bg = new BigDecimal(str); 
								str = String.valueOf(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
								out.println("<center><table width='100px' border='0' cellspacing='1' ><tr><td><div  id='jindutiao_div__"+list_tags.get(j).getColumn_name()+":"+j+"' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"+str+"%'  id='show_jindutiao__"+list_tags.get(j).getColumn_name()+":"+j+"'/></div></td><td><center><span style='color:red;'>"+str+"%</span></center></td></tr></table></center>");
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
				}
//				out.println("<td align='center'>"+list_designe.get(i).get("username")+"</td>");//用户名
		/*		
				out.println("<script type='text/javascript'>" +
						" function add_"+list_tags.get(i).getId()+"() "+
						" {			" +
						" 	width=800;	" +
						" 	height=600;	" +
						"   	var sFeature='dialogWidth:'+width+'px;dialogHeight:'+height+'px;Status:0;resizable:1;help:0';	" +
						" 	  	var rv = window.showModalDialog('getRelateEluserInfo.action?&rn='+Math.random(),null,sFeature);	" +
			//			" alert(rv);" +
						"var display='';" +
						"var returnvalue='';" +
						"if(rv!=null)" +
						"{" +
					//	"  alert(rv);" +
						" document.getElementById('contactid').value="+list_designe.get(i).get("id")+"; "+
						" viewContact.action='updatePrincipal.action?principalid='+rv;"+
						" viewContact.submit();		" +
						"}" +
						"" +
						"" +
						"}" +
						"" +
						"" +
						"" +//id==column==content
						" </script> ");
				
				
				String principalname=list_designe.get(i).get("principalname");
				if(principalname==null) principalname="";
				out.println("<td align='center'>"+principalname+
						"<input type='button' value='修改' onclick='add_"+list_tags.get(i).getId()+"();' /></td>");//负责人
		*/		
//				out.println("<td align='center'>"+list_designe.get(i).get("name")+"</td>");//部门名称
				out.println("<td align='center'>"+getStatus_chinese(Integer.parseInt(list_designe.get(i).get("status")))+"</td>");//状态
				
				out.println("<td align='center'>");
				
				
				if(moduleZDY!=null&&moduleZDY.getAddjsp()!=null&&!moduleZDY.getAddjsp().equals("")){
					out.println("<a href='javascript:view_ZDY("
							+ list_designe.get(i).get("id") + ")'>查看</a>");
				}else{
					out.println("<a href='javascript:view("
							+ list_designe.get(i).get("id") + ")'>查看</a>");
				}
				
				out.println("<td align='center'>" + list_designe.get(i).get("moduleid") + "</td>");
				
//				//审核选项
//				if(list_designe.get(i).get("status").equals("已创建"))
//				{
//					out.println("<a href='javascript:verify_pass("
//							+ list_designe.get(i).get("id") + ")'>审核通过</a>");
//				}
//				else if(list_designe.get(i).get("status").equals("审核通过"))	
//				{
//					out.println("<a href='javascript:verify_nopass("
//							+ list_designe.get(i).get("id") + ")'>审核不通过</a>");
//				}
//				else if(list_designe.get(i).get("status").equals("审核不通过"))	
//				{
//					out.println("<a href='javascript:verify_pass("
//							+ list_designe.get(i).get("id") + ")'>审核通过</a>");
//				}
				
				out.println("</td>");
				out.println("</tr>");
			}// for

			// out.println(con.getId());
			// out.println(con.getTheme());
			// out.println("<%= %>");
			out.println("<tr>");
			for(int i=0;i<list_tags.size();i++)
			{
				if(list_tags.get(i).getDepartsearch_display() == 1)
				{
					out.println("<td>");
					if(list_tags.get(i).getSum_display()==1)
					{
						if(list_tags.get(i).getColumn_type().equals("number"))
							out.println(list_tags.get(i).getSum_i());
						else if(list_tags.get(i).getColumn_type().equals("float"))
							out.println(list_tags.get(i).getSum_f());
							
					}
					out.println("</td>");
				}
			}
			out.println("<td colspan="+list_tags.size() + 2+" align='center'>合计</td>");
			out.println("</tr>");
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
