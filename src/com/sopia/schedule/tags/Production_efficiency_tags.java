package com.sopia.schedule.tags;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.schedule.entities.Production_efficiency;
import com.sopia.schedule.entities.Tags;

public class Production_efficiency_tags extends TagSupport{
	
	public String getFormatMonth(String month){
		String[] month_ = month.split("-");
		return month_[0]+"年"+month_[1]+"月";
	}
	
	public int doStartTag()
	{
		try
		{
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Map<String,List<Production_efficiency>> production_efficiency = 
				(Map<String,List<Production_efficiency>>)request.getAttribute("production_efficiency");
			
			List<Production_efficiency> list = null;
			
			
			if(production_efficiency != null){
				double all_sccb=0;
				double all_cz = 0;
				for(String key:production_efficiency.keySet()){
					double sccb = 0;
					double cz = 0;
					double xx = 0;
					
					double sccb1 = 0;
					double cz1 = 0;
					double xx1 = 0;
					
					list = production_efficiency.get(key);
					if(list != null){
						
						for(int i=0;i<list.size();i++){
							if(list.get(i).getType().equals("1")){
								sccb += list.get(i).getSccb();
								cz += list.get(i).getCz();
								xx += list.get(i).getXx();
							}else if(list.get(i).getType().equals("2")){
								sccb1 += list.get(i).getSccb();
								cz1 += list.get(i).getCz();
								xx1 += list.get(i).getXx();
							}
						}
						all_sccb += sccb + sccb1;
						all_cz += cz + cz1;
					}
					
					out.println("<tr>");
					out.println("<td align='center'>");
					out.println(getFormatMonth((String)key));
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println("生产完工单");
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println(sccb);
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println(cz);
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println(xx);
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println("<a href='myContactTags1_.action?tablename=SCWG'>查看</a>");
					out.println("</td>");
					out.println("</tr>");
					
					
					out.println("<tr>");
					out.println("<td align='center'>");
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println("委外加工单");
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println(sccb1);
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println(cz1);
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println(xx1);
					out.println("</td>");
					
					out.println("<td align='center'>");
					out.println("<a href='myContactTags1_.action?tablename=WWWG'>查看</a>");
					out.println("</td>");
					out.println("</tr>");
				}
				
				out.println("<tr>");
				out.println("<td align='center'>");
				out.println("合计");
				
				out.println("</td>");
				out.println("<td align='center'>");
				out.println("</td>");
				
				out.println("<td align='center'>");
				out.println(all_sccb);
				out.println("</td>");
				
				out.println("<td align='center'>");
				out.println(all_cz);
				out.println("</td>");
				
				out.println("<td align='center'>");
				out.println(all_cz-all_sccb);
				out.println("</td>");
				
				out.println("<td align='center'>");
				out.println("");
				out.println("</td>");
				out.println("</tr>");
			}
			
		} catch (Exception e)
		{
			e.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

}
