package com.sopia.common;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 分页标签====北京市卫生局
 * @author Administrator
 *
 */
public class PaginationCisco extends TagSupport {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(Pagination.class);
	
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			out.print("<div style='margin-top:10px;' class=\"meneame\">");
			int count = 0;
			if (null != request.getAttribute("count"))
				count = (Integer) request.getAttribute("count");
			int pageNow = 0;
			if (null != request.getAttribute("pN"))
				pageNow = (Integer) request.getAttribute("pN");
			int pageSize = 0;
			if (null != request.getAttribute("pS"))
				pageSize = (Integer) request.getAttribute("pS");
			int pageCount = 0;
			if(pageSize==0) pageSize=10;
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
				out.print("<a href=\"javascript:void(0);\">[首页]</a>");
				out.print("<a href=\"javascript:void(0);\">[上一页]</a>");
			}
			if (pageCount > 0) {
				out
						.print("<select  name=select class=select001 onchange='page(this.options[this.selectedIndex].value)'>");
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
				out.print("<a href=\"javascript:void(0);\">[下一页]</a>");
				out.print("<a href=\"javascript:void(0);\">[末页]</a>");
			}
			out.print("<span class=select002>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>共</b>" + count
					+ "<b>条</b></span>");
			out.print("</div>");
		} catch (Exception ex) {
			logger.error("分页标签错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
}
