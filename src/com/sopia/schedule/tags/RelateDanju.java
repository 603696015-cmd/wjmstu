package com.sopia.schedule.tags;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.schedule.entities.Tags;

public class RelateDanju extends TagSupport{
	
	public int doStartTag()
	{
		try
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Map<String, List<Map<String, Object>>> map_list_map = (Map<String, List<Map<String, Object>>>) request
					.getAttribute("map_list_map");
			
			
			/**
			 */
			if(map_list_map != null){
				out.println("<tr>");
				for(String key:map_list_map.keySet()){
					out.println("<td>"+key+"</td>");
				}
				out.println("</tr>");
				
				out.println("<tr>");
				
				
				if(map_list_map.get("收款单") == null&&
						map_list_map.get("付款单") == null&&
						map_list_map.get("应收") == null&&
						map_list_map.get("应付") == null&&
						map_list_map.get("其他收入") == null&&
						map_list_map.get("费用支出") == null){
					out.println("<td align='center' height='100px'>无数据</td>");
				}
				if(map_list_map.get("收款单") != null){
					out.println("<td>" +
							"<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" );
							for(Map<String,Object> map:map_list_map.get("收款单")){
								if((String)map.get("name") != null && !((String)map.get("name")).equals(""))
									out.println("<tr><td><a href='viewContactTags.action?tablename=SK&id="+map.get("id")+"'>"+(String)map.get("name")+"</></td></tr>");
							}
					out.println("</table>" +
							"</td>");
				}
				
				if(map_list_map.get("付款单") != null){
					out.println("<td>" +
							"<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" );
							for(Map<String,Object> map:map_list_map.get("付款单")){
								if((String)map.get("name") != null && !((String)map.get("name")).equals(""))
									out.println("<tr><td><a href='viewContactTags.action?tablename=FK&id="+map.get("id")+"'>"+(String)map.get("name")+"</></td></tr>");
							}
					out.println("</table>" +
							"</td>");
				}
				
				if(map_list_map.get("应收") != null){
					out.println("<td>" +
							"<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" );
							for(Map<String,Object> map:map_list_map.get("应收")){
								if((String)map.get("name") != null && !((String)map.get("name")).equals(""))
									out.println("<tr><td><a href='viewContactTags.action?tablename=YS&id="+map.get("id")+"'>"+(String)map.get("name")+"</></td></tr>");
							}
					out.println("</table>" +
							"</td>");
				}
				
				if(map_list_map.get("应付") != null){
					out.println("<td>" +
							"<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" );
							for(Map<String,Object> map:map_list_map.get("应付")){
								if((String)map.get("name") != null && !((String)map.get("name")).equals(""))
									out.println("<tr><td><a href='viewContactTags.action?tablename=YF&id="+map.get("id")+"'>"+(String)map.get("name")+"</></td></tr>");
							}
					out.println("</table>" +
							"</td>");
				}
				
				if(map_list_map.get("其他收入") != null){
					out.println("<td>" +
							"<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" );
							for(Map<String,Object> map:map_list_map.get("其他收入")){
								if((String)map.get("name") != null && !((String)map.get("name")).equals(""))
									out.println("<tr><td><a href='viewContactTags.action?tablename=QTSR&id="+map.get("id")+"'>"+(String)map.get("name")+"</></td></tr>");
							}
					out.println("</table>" +
							"</td>");
				}
				
				if(map_list_map.get("费用支出") != null){
					out.println("<td>" +
							"<table border=\"0\" cellspacing=\"0\" cellpadding=\"0\">" );
							for(Map<String,Object> map:map_list_map.get("费用支出")){
								if((String)map.get("name") != null && !((String)map.get("name")).equals(""))
									out.println("<tr><td><a href='viewContactTags.action?tablename=FYZC&id="+map.get("id")+"'>"+(String)map.get("name")+"</></td></tr>");
							}
					out.println("</table>" +
							"</td>");
				}
				out.println("</tr>");
			}
			
			
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

}
