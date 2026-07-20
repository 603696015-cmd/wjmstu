package com.sopia.courseman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.courseman.entities.EroomBatchLib;

public class ErbatchLibSelect extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(ErbatchLibSelect.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			EroomBatchLib qlb = (EroomBatchLib) request.getAttribute("erbatchLibTree");
			writeChilds(out, qlb);
		} catch (Exception ex) {
			logger.error("批量场次类别显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		EroomBatchLib qlb = (EroomBatchLib) obj;
		List<EroomBatchLib> qlbChild = qlb.getChild();
		if (qlb.getId() < 0) {
			out.println("<optgroup label='" + qlb.getName() + "'></optgroup>");
		} else {

			out.println("<option value='" + qlb.getId() + "'");
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
			EroomBatchLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}

}
