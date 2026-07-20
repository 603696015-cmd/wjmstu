package com.sopia.classman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.classman.entities.ElClType;
import com.sopia.common.ElTag;

public class ClTypeSelect extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(ClTypeSelect.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ElClType obj = (ElClType) request.getAttribute("cltypeTree");
			writeChilds(out, obj);
		} catch (Exception ex) {
			logger.error("培训班类别列表下拉列表错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		ElClType qlb = (ElClType) obj;
		List<ElClType> qlbChild = qlb.getChild();
		
		out.println("<option value='" + qlb.getId() + "'");
		if(getSelectid()==qlb.getId()){
			out.println("selected = 'selected'");
		}
				out.println(">");
		for (int j = 0; j < qlb.getLevel(); j++) {
			out.println("--");
		}
		out.println(qlb.getName() + " </option>");
		for (int i = 0; i < qlbChild.size(); i++) {
			ElClType qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}
}
