package com.sopia.courseman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.courseman.entities.CourseType;

public class CourseTypeSelect extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(CourseTypeSelect.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			CourseType qlb = (CourseType) request.getAttribute("ctypeTree");
			writeChilds(out, qlb);
		} catch (Exception ex) {
			logger.error("课程类别列表标签错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		CourseType clb = (CourseType) obj;
		List<CourseType> clbChild = clb.getChild();
		if (clb.getId() < 0) {
			out.println("<optgroup label='" +clb.getName()+ "'></optgroup>");
		} else {
			out.println("<option value='" + clb.getId() + "'");
			if (getSelectid() == clb.getId()) {
				out.println("selected = 'selected'");
			}
			out.println(">");
			for (int j = 0; j < clb.getLevel(); j++) {
				out.println("--");
			}
			out.println(clb.getName() + " </option>");
		}

		for (int i = 0; i < clbChild.size(); i++) {
			CourseType clbi = clbChild.get(i);
			writeChilds(out, clbi);
		}
	}
}
