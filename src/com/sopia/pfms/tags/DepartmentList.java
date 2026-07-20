package com.sopia.pfms.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;


import com.sopia.common.ElTag;
import com.sopia.duman.entities.Department;

public class DepartmentList extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			List<Department> qlb = (List<Department>) request.getAttribute("departments");
			writeChilds(out, qlb);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	@SuppressWarnings("unchecked")
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		List<Department> qlbChild = (List<Department>) obj;
		if(qlbChild != null){
			
			
			for (int i = 0; i < qlbChild.size(); i++) {
				
				Department qlbi = qlbChild.get(i);
				if(qlbi.getParent().getId() == 1){
					out.println("<tr>" +
							"<td width=\"30\" align=\"left\" valign=\"middle\"><img src=\"front/images/arrow_r.gif\" width=\"11\" height=\"8\"></td>" +
							"<td width=\"220\" height=\"33\" align=\"left\" valign=\"middle\" class=\"STYLE5\"><a href='"+ getHref() + "" + qlbi.getId() + "'>"+qlbi.getName()+"</a></td>" +
							"</tr>");
					
					int id = qlbi.getId();
					
					out.println("<tr>" +
					"<td height=\"33\" colspan=\"2\" align=\"left\" valign=\"middle\">");
					
					System.out.println(qlbChild.size());
					for(int j=0;j<qlbChild.size();j++){
						Department q = qlbChild.get(j);
						
							
						if( id == q.getParent().getId()){
							out.println("<a href='" + getHref() + "" + q.getId() + "'>" + q.getName() + "</a> £ü ");
						}
						
					}
					
					out.println("</td></tr>");
				}
				
			}
		}
	}
}
