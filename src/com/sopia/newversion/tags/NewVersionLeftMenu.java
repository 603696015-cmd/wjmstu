package com.sopia.newversion.tags;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import com.sopia.duman.entities.ElFunc;

@SuppressWarnings("serial")
public class NewVersionLeftMenu extends TagSupport{
	private List<ElFunc> menus;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		JspWriter out = pageContext.getOut();
		ServletRequest request = pageContext.getRequest();
		menus = (List<ElFunc>)request.getAttribute("menus");
		
		try {
			outPut(out, menus);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return TagSupport.SKIP_BODY;
	}
	

	public void outPut(JspWriter out,List<ElFunc> menus) throws IOException {
		if(menus!=null && menus.size()>0){
			out.println("<dl class=\"slideNav\">");
			out.println("<dt>");
			out.println("<a href=\"user_center.action\" target=\"_parent\"><h2>个人中心</h2></a>");
			out.println("</dt>");
			for(int i=0;i<menus.size();i++){
				out.println("<dd><a   href=\"javascript:listChildFunc("+menus.get(i).getId()+",'centerul',this);\">"+menus.get(i).getName()+"</a></dd>");
			}
			out.println("</dl>");
		}
	}

	public List<ElFunc> getMenus() {
		return menus;
	}

	public void setMenus(List<ElFunc> menus) {
		this.menus = menus;
	}
	
	
	

}
