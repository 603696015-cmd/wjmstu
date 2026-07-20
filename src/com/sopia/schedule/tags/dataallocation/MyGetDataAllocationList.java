package com.sopia.schedule.tags.dataallocation;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.common.ElException;
import com.sopia.schedule.DataAllocationUtil;
import com.sopia.schedule.ScheduleUtil;
import com.sopia.schedule.dao.impl.TagsDaoImpl;
import com.sopia.schedule.entities.CustomAudit;
import com.sopia.schedule.entities.ModuleManage;
import com.sopia.schedule.entities.ModuleZDY;
import com.sopia.schedule.entities.Tags;

public class MyGetDataAllocationList extends TagSupport {
	private String type;
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

	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			List<Tags> list_tags = (List<Tags>) request.getAttribute("list_tags");


			List<Map<String, String>> list_designe = (List<Map<String, String>>) request.getAttribute("list_designe");
			
			ModuleManage moduleManage =  (ModuleManage)request.getAttribute("moduleManage");

			writeout(list_tags,list_designe,out);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
	
	public void writeout(List<Tags> list_tags,List<Map<String,String>> list_designe,JspWriter out) throws IOException, NumberFormatException, ElException{
		// 显示table标题
		String type = getType();
		String width = "";
		int k = 0;
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getList_display() == 1)
				k++;
		}

		k = k * 100;
		if (k < 1000)
			width = " width='100%'";
		else
			width = " width='" + k + "px'";

		out.println("<table " + width
				+ " align='center' cellpadding='1' cellspacing='1'>");

		out.println("<tr >");
		if(type!=null&&type.equals("1")){
			out.println("<tr>");
			out.println("<td colspan=20><div id=\"Div_ToolsBar\"></div></td>");
			out.println("</tr>");
		}
		out.println("<th width='20'></th>");
		for (int i = 0; i < list_tags.size(); i++) {
			if (list_tags.get(i).getList_display() == 1)// display
			{
				out.println("<th><a id= '"
						+ list_tags.get(i).getColumn_name() + "_" + i
						+ "' href=\"javascript:columnsearch('"
						+ list_tags.get(i).getColumn_name() + "');\" >"
						+ list_tags.get(i).getName_display() + "</a></th>");
			}
			// 当为工作日志表的时候增加一列显示差值
			if (i == list_tags.size() - 1)
				if (list_tags.get(i).getTable_name().equals("GRRZ")) {
					out
							.println("<th style='color:red'><span>差值</span></th>");
				}
		}// for
		out.println("<th><a href=\"javascript:columnsearch('status');\" >状态</a></th>");
		
		out.println("<th>审核状态</th>");
		out.println("<th>开始时间</th>");
		out.println("<th>结束时间</th>");
//		out.println("<th>查看</th>");
//		out.println("<th>学习</th>");
		
		out.println("</tr>");

		out.println("<tbody onMouseOut='changeback()' onMouseOver='changeto()'>");

		// 显示列表值
		double chazhi = 0.0;
		for (int i = 0; i < list_designe.size(); i++) {
			out.println("<tr>");
			out.println("<td width='20' height='20' align='center'>" +
					"<input type='checkbox' onclick='clickcheckbox();' value='"+list_designe.get(i).get("id")+"' name='id_'/>"+
					"</td>");
			
			for (int j = 0; j < list_tags.size(); j++) {
				if (list_tags.get(j).getList_display() == 1) {
					String str = list_designe.get(i).get(
							list_tags.get(j).getColumn_name());
					if(list_tags.get(j).getDisplay_type().equals("日期")){
						str = ScheduleUtil.dateFormat(str, list_tags.get(j).getTimeformat());
					}else{
						if (str == null)
							str = "";
					}

					if (list_tags.get(j).getJindutiao() == 1) {
						out.println("<td  id='"
								+ list_tags.get(j).getColumn_name() + ":"
								+ j + "' align='left'>");
					} else {
						out.println("<td  id='"
								+ list_tags.get(j).getColumn_name() + ":"
								+ j + "' align='center'>");
					}
					// 显示类型为附件的时候
					if (list_tags.get(j).getDisplay_type().equals("附件上传")) {
						String str2[] = str.split("==");
						out.println("<label>" + str2[0] + "</label>");
					}
					// 百分比
					if (list_tags.get(j).getDisplay_type().equals("百分比")) {
						if (list_tags.get(j).getJindutiao() == 1) {// 显示进度条
							if (str == null || str.equals(""))
								str = "0";
							BigDecimal bg = new BigDecimal(str);
							str = String
									.valueOf(bg.setScale(2,
											BigDecimal.ROUND_HALF_UP)
											.doubleValue());
							out
									.println("<center><table width='100px' border='0' cellspacing='1' ><tr><td><div  id='jindutiao_div__"
											+ list_tags.get(j)
													.getColumn_name()
											+ ":"
											+ j
											+ "' style='border: 1px dotted #FF6633;width:80px'><img height='14' src='images/jd.gif' width='"
											+ str
											+ "%'  id='show_jindutiao__"
											+ list_tags.get(j)
													.getColumn_name()
											+ ":"
											+ j
											+ "'/></div></td><td><center><span style='color:red;'>"
											+ str
											+ "%</span></center></td></tr></table></center>");
						} else {
							if (str.equals("")) {
								out.println("<label>" + 0 + "%</label>");
							} else {
								out.println("<label>" + str + "%</label>");
							}
						}
					} else {
						out.println("<label>" + str + "</label>");
					}
					out.println("</td>");
				}
			}
			
			
			// 当为工作日志表的时候增加一列显示差值
			if (list_tags.get(0).getTable_name().equals("GRRZ")) {
				String a = "0";
				String b = "0";
				if (!list_designe.get(i).get("GRRZ_LDPF").equals("")) {
					a = list_designe.get(i).get("GRRZ_LDPF");
				}
				if (!list_designe.get(i).get("GRRZ_ZWPF").equals("")) {
					b = list_designe.get(i).get("GRRZ_ZWPF");
				}
				double d = Double.parseDouble(a) - Double.parseDouble(b);
				chazhi += d;
				out.println("<td align='center'><span style='color:red'>"
						+ d + "</span></td>");
			}
			out.println("<td align='center'>"+getStatus_chinese(Integer.parseInt(list_designe.get(i).get("status")))+"</td>");//状态
			
			out.println("<td align='center'>");
			if(Integer.parseInt(list_designe.get(i).get("tdastatus")) == 2){
				out.println("<span style='color:red'>"+DataAllocationUtil.getTDAStatus(Integer.parseInt(list_designe.get(i).get("tdastatus")))+"</span");
			}else{
				out.println(DataAllocationUtil.getTDAStatus(Integer.parseInt(list_designe.get(i).get("tdastatus"))));
			}
			
			out.println("</td>");
			
			out.println("<td align='center'>"+list_designe.get(i).get("tdabegintime")+"");
			out.println("<td align='center'>"+list_designe.get(i).get("tdaendtime")+"</td>");
//			out.println("<td align='center'>");
//			if(list_designe.get(i).get("tdastatus").equals("1")||list_designe.get(i).get("tdastatus").equals("2")){
//				out.println("<a href='javascript:view("+list_designe.get(i).get("id")+","+list_designe.get(i).get("userid")+");' >查看</a>");
//			}
//			out.println("</td>");
//			
//			out.println("<td align='center'>");
//			if(list_designe.get(i).get("tdastatus").equals("1")||list_designe.get(i).get("tdastatus").equals("2")){
//				out.println("<a href='javascript:learn("+list_designe.get(i).get("id")+","+list_designe.get(i).get("userid")+")' >学习</a>");
//			}
//			out.println("</td>");
			

			
		}
		out.println("</tr>");

		// 当为工作日志表的时候增加一列显示差值
		if (list_tags.get(0).getTable_name().equals("GRRZ")) {
			out.println("<td align='center'><span style='color:red'>"
					+ chazhi + "</span></td>");
		}
		out.println("</tr>");

		out.println("</tbody>");
		out.println("</table>");
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}


}
