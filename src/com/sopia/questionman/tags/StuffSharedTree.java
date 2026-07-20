package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.questionman.entities.StuffLib;

public class StuffSharedTree extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(StuffSharedTree.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			setDid(1);
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			StuffLib qlb = (StuffLib) request.getAttribute("stuffSharedTree");
			out
					.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n"
							+ "<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n"
							+ "	<script type=\"text/javascript\">\n" + "<!--\n");
			out.println("var d"
					+ getDid()
					+ " = new dTree('"
					+ getItype()
					+ "','"
					+ getIname()
					+ "',"
					+ (getIvalue() == null || "".equals(getIvalue()) ? qlb
							.getId() : getIvalue()) + ", 'd" + getDid()
					+ "');\n");
			writeChilds(out, qlb);
			out.println("document.write(d" + getDid() + ");\n" + "//-->\n"
					+ "</script>");
		} catch (Exception ex) {
			logger.error("共享文件夹结构显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		StuffLib qlb = (StuffLib) obj;
		List<StuffLib> qlbChild = qlb.getChilds();

		String href1 = getHref() == null || "".equals(getHref()) ? ""
				: getHref() + qlb.getId();
		if (qlb.getLevel() == 0)
			if (!getRootAble()) {
				out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
						+ qlb.getTitle() + "');\n");
			} else {
				out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
						+ qlb.getTitle() + "','" + href1 + "');\n");
			}
		else
			out.print("d" + getDid() + ".add(" + qlb.getId() + ","
					+ qlb.getParent().getId() + ",'" + qlb.getTitle() + "','"
					+ href1 + "');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			StuffLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}
}
/**
 * 
 * for (int j = 0; j < qlb.getLevel(); j++) { out.println("--"); } if
 * (qlb.getLevel() == 0 && !getRootAble()) { out.println(qlb.getName() + "<br>
 * "); } else out.println("<a href='" + getHref() + qlb.getId() + "'>" +
 * qlb.getName() + "</a><br>
 * "); for (int i = 0; i < qlbChild.size(); i++) { ExamPaperLib qlbi =
 * qlbChild.get(i); writeChilds(out, qlbi); }
 */
