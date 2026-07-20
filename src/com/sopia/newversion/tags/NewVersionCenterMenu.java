package com.sopia.newversion.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.ElFunc;

public class NewVersionCenterMenu extends TagSupport {
	private List<ElFunc> menus;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		menus = (List<ElFunc>) request.getAttribute("menus_three");

		try {
			outPut(out, menus);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return TagSupport.SKIP_BODY;
	}

	public void outPut(JspWriter out, List<ElFunc> menus) throws IOException {
		String actionName = "";
		String params = "";
		if (menus != null && menus.size() > 0) {
			out.println("<ul class=\"kcList clearfix\" id=\"centerul\">");
			for (int i = 0; i < menus.size(); i++) {
				actionName = menus.get(i).getFunccode() + ".action";
				params = menus.get(i).getParams();
				if(params!=null && !params.equals("")){
					actionName += "?" + params;
				}
				out.println("<li>"
								+
								"<div class=\"kcList-in\">"
								+
								"<div class=\"bd\"> <a class=\"pic\" href=\""+actionName+"\">");
								
				if(menus.get(i).getLinkimg()==null||menus.get(i).getLinkimg().equals("")){
					out.println("<img src=\"images/default_link.jpg\" alt=\"\"/>");
				}else{
					out.println("<img src=\""+menus.get(i).getLinkMainimg()+"\" alt=\"\"/>");
				}
				out.println("</a><p><a target='_blank' style='line-height:40px;' href=\""+actionName+"\">"+menus.get(i).getName()+"</a></p>"
								+ "</div>" +
								"</div>" + "</li>");
			}
			out.println("</ul>");
		}
	}

	public List<ElFunc> getMenus() {
		return menus;
	}

	public void setMenus(List<ElFunc> menus) {
		this.menus = menus;
	}

}
