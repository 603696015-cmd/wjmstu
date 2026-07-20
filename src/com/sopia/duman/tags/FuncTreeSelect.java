package com.sopia.duman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.duman.entities.ElFunc;

public class FuncTreeSelect extends ElTag {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5714530967503948064L;
	private static final Log logger = LogFactory.getLog(FuncTreeSelect.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ElFunc cts = (ElFunc) request.getAttribute("funcTree");
			writeChilds(out, cts);
		} catch (Exception ex) {
			logger.error("功能树下拉列表显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		ElFunc qlb = (ElFunc) obj;
		List<ElFunc> qlbChild = qlb.getChild();
		int level = qlb.getLevel();
		
		if(level%2==0){
//			out.print("<optgroup label=\""+qlb.getName()+"\"></optgroup>");
			out.println("<option style='color:blue' value='" + qlb.getId() + "'");
			if(getSelectid()==qlb.getId()){
				out.println("selected = 'selected'");
			}
			out.println(">");
			for (int j = 0; j < qlb.getLevel(); j++) { out.println("--"); }
			 
			out.println(qlb.getName() + " </option>");
		}
		else{
			out.println("<option value='" + qlb.getId() + "'");
			if(getSelectid()==qlb.getId()){
				out.println("selected = 'selected'");
			}
			out.println(">");
			for (int j = 0; j < qlb.getLevel(); j++) { out.println("--"); }
			 
			out.println(qlb.getName() + " </option>");
		}
		
		
		for (int i = 0; i < qlbChild.size(); i++) {
			ElFunc qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}
}
