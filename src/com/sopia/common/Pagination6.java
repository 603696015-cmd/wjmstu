package com.sopia.common;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

/**
 * 分页标签1
 * @author Administrator
 *
 */
public class Pagination6 extends TagSupport {
	private static final long serialVersionUID = 3119679319963664116L;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			out.print("<div style='margin-top:10px;'>");
			int count = 0;
			if (null != request.getAttribute("count"))
				count = (Integer) request.getAttribute("count");
			int pageNow = 0;
			if (null != request.getAttribute("pN6"))
				pageNow = (Integer) request.getAttribute("pN6");
			int pageSize = 0;
			if (null != request.getAttribute("pS6"))
				pageSize = (Integer) request.getAttribute("pS6");
			int pageCount = 0;
			if(pageSize==0) pageSize=3;
			if (count % pageSize == 0) {
				pageCount = count / pageSize;
			} else {
				pageCount = count / pageSize + 1;
			}
			if (pageNow > 0) {
				out.print("<a style='cursor: hand' href='javascript:page("
						+ (0) + ")'>[首页]</a>");
				out.print("<a style='cursor: hand' href='javascript:page("
						+ (pageNow - 1) + ")'>[上一页]</a>");
			} else {
				out.print("[首页]");
				out.print("[上一页]");
			}
			if (pageCount > 0) {
				out
						.print("<select  onchange='page(this.options[this.selectedIndex].value)'>");
				for (int i = 0; i < pageCount; i++) {
					if(pageNow==i)
					out.println("<option value='" + i + "' selected='selected'>" + (i + 1)
							+ "</option>");
					else{
						out.println("<option value='" + i + "'>" + (i + 1)
								+ "</option>");
					}

				}
				out.println("</select> ");
			}
			if (pageNow < pageCount - 1) {
				out.print("<a style='cursor: hand' href='javascript:page("
						+ (pageNow + 1) + ")'>[下一页]</a>");
				out.print("<a style='cursor: hand' href='javascript:page("
						+ (pageCount - 1) + ")'>[末页]</a>");
			} else {
				out.print("[下一页]");
				out.print("[末页]");
			}
			out.print("<span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
					+ "<b>条</b></span>");
			out.print("</div>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}
}
