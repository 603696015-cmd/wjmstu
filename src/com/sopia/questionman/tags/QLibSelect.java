package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.questionman.entities.QuestionLib;

public class QLibSelect extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(QLibSelect.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			QuestionLib qlb = (QuestionLib) request.getAttribute("qlbTree");
			writeChilds(out, qlb);
		} catch (Exception ex) {
			logger.error("试题类别树下拉显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		QuestionLib qlb = (QuestionLib) obj;
		List<QuestionLib> qlbChild = qlb.getChild();
		if (qlb.getId() < 0) {
			out.println("<optgroup label='" + qlb.getName() + "'></optgroup>");
		} else {

			out.println("<option haschild="+(qlbChild.size()==0?0:1)+" value='" + qlb.getId() + "'");
			if (getSelectid() == qlb.getId()) {
				out.println("selected = 'selected'");
			}
			out.println(">");
			for (int j = 0; j < qlb.getLevel(); j++) {
				out.println("--");
			}
			out.println(qlb.getName() + " </option>");
		}
		for (int i = 0; i < qlbChild.size(); i++) {
			QuestionLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}

}
