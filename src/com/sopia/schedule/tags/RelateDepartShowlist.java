package com.sopia.schedule.tags;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElException;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.Tags;

/**
 * 选择相关数据列表标签
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class RelateDepartShowlist extends TagSupport
{
	private String listname;
	private String delaction;
	private String viewaction;
	private String updateaction;
	
	private String radio;

	public String getRadio() {
		return radio;
	}

	public void setRadio(String radio) {
		this.radio = radio;
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

	public int doStartTag()
	{
		try
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();

			radio = (String)request.getAttribute("radio");
			
			List<Tags> list_tags = (List<Tags>) request
					.getAttribute("list_tags");

			List<Map<String, String>> list_designe = (List<Map<String, String>>) request
					.getAttribute("list_designe");
			
			String columnname=(String)request.getAttribute("columnname");
			
			// 显示table标题
			out.println("<tr>");
			out.println("<th></th>");
			for (int i = 0; i < list_tags.size(); i++)
			{
				if (list_tags.get(i).getDepartsearch_display() == 1)// display
				{
					if(!list_tags.get(i).getDisplay_type().equals("相关负责人")){
						out.println("<th>" + list_tags.get(i).getName_display()
								+ "</th>");
					}
				}
			}// for

			
			out.println("<th>用户</th>");
			out.println("<th>部门</th>");
			out.println("<th>状态</th>");
			
			
			out.println("</tr>");

			// 显示列表值
			for (int i = 0; i < list_designe.size(); i++)
			{

				out.println("<tr>");
				if(radio != null && radio.equals("1")){
					out.println("<td><input type='radio' name='check' value='"+list_designe.get(i).get("id")+"=="+columnname+"_-_"+list_designe.get(i).get(columnname)+"'></td>");
				}else {
					out.println("<td><input type='checkbox' name='check' value='"+list_designe.get(i).get("id")+"=="+columnname+"_-_"+list_designe.get(i).get(columnname)+"'></td>");
				}

				for (int j = 0; j < list_tags.size(); j++)
				{
					if (list_tags.get(j).getDepartsearch_display() == 1)
					{
						String str = list_designe.get(i).get(
								list_tags.get(j).getColumn_name());
						if (str == null)
							str = "";
						
						if(!list_tags.get(j).getDisplay_type().equals("相关负责人")){
							out.println("<td  align='center'>");
							//显示类型为附件的时候
							if(list_tags.get(j).getDisplay_type().equals("附件上传"))
							{
								String str2[]=str.split("==");
								out.println("<label>" + str2[0] + "</label>");
							}
							else if(list_tags.get(j).getDisplay_type().equals("分级下拉选项")){
								//获取最后一级的选项
								if(!str.equals("")){
									String[] ary = str.split("___");
									if(ary!=null&&ary.length>0){
										for(int m=0;m<ary.length;m++){
											if(m == ary.length - 1)
												str = ary[m].split("__")[1];
										}
									}
									if(str.equals("请选择"))		str = "";
								}
								out.println("<label>" + str + "</label>");
							}
							else
							{
								out.println("<label>" + str + "</label>");
							}
						}else {
							out.println("<td  align='center' style='display:none;'>");
						}
						out.println("</td>");
					}
					// }
				}
				
				out.println("<td align='center'>"+list_designe.get(i).get("username")+"</td>");//用户名
				out.println("<td align='center'>"+list_designe.get(i).get("name")+"</td>");//部门名称
				out.println("<td align='center'>"+getStatus_chinese(Integer.parseInt(list_designe.get(i).get("status")))+"</td>");//状态
				
				
				out.println("</tr>");
			}// for

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


