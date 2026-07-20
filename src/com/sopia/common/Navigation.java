package com.sopia.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.duman.entities.ElFunc;

/**
 * 位置导航标签
 * @author Administrator
 *
 */
public class Navigation extends TagSupport {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(Navigation.class);
	private String ivalue;

	public String getIvalue() {
		return ivalue;
	}

	public void setIvalue(String ivalue) {
		this.ivalue = ivalue;
	}

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			HttpServletRequest request=ServletActionContext.getRequest();
			ElFunc ef = (ElFunc) request.getSession().getAttribute("navfunc");
			out.println("<div>");
			out.print("<a target='_blank' href='index.action'>首页</a>&nbsp;>>&nbsp;");
			if(ef!=null)
			out.print(getNav(ef));
			out.print("<span>"+this.ivalue+"</span>");
			
			out.print("</div>");
			
			
//			JspWriter out = pageContext.getOut();
//			HttpServletRequest request=ServletActionContext.getRequest();
//			ElFunc ef = (ElFunc) request.getSession().getAttribute("navfunc");
//			out.print("<div>");
//			out.print("<table style='margin-top:-8px;width:100%'>");
//			out.print("<tr>");
//			out.print("<td>");
//			out.print("<a target='_blank' href='index.action'>首页</a>&nbsp;>>&nbsp;");
//			if(ef!=null)
//				out.print(getNav(ef));
//			out.print("<span>"+this.ivalue+"</span>");
//			out.print("</td>");
//			out.print("<td align='right' width='120px'>");
//			out.print("<a href=\"javascript:void(0);\"  target=\"base\"  onclick=\"full_screen(true);return false;\"><img src=\"images/full-screen.png\" border=\"0\"  style=\"vertical-align:middle\"></a>&nbsp;&nbsp;&nbsp;&nbsp;");
//			out.print("</td>");
//			out.print("</tr>");
//			out.print("</table>");
//			out.print("</div>");
			
		} catch (Exception ex) {
			logger.error("导航标签错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	/**
	 * 递归，获取导航的
	 * Description: 
	* @Version1.0 2012-5-11 下午05:07:25 by 闻益舜（wenyishun110@163.com）创建
	 * @param ef
	 * @return
	 */
	private String getNav(ElFunc ef){
		String s = "";
		if(ef.getParent()!=null){
			s = getNav(ef.getParent())+s;
		}
		if(ef.getName()!=null)
		s =s +"<span>"+ef.getName()+"</span>&nbsp;>>&nbsp;";
		return s;
	}
}
