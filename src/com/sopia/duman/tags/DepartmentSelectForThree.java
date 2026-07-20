package com.sopia.duman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.duman.entities.Department;
/**
 * 只显示根节点、一级节点、二级节点
 * @author Administrator
 *
 */
public class DepartmentSelectForThree extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(DepartmentList.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Department qlb = (Department) request.getAttribute("depTree");
			writeChilds(out, qlb);
		} catch (Exception ex) {
			logger.error("部门下拉框显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		Department qlb = (Department) obj;
		if(qlb.getLevel()<3){
			List<Department> qlbChild = qlb.getChild();
			if (qlb.getId() < 0) {
				out.println("<optgroup label='" +qlb.getName()+ "'></optgroup>");
			} else {
				if(qlb.getId()!=0){
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
			}
			if(qlbChild!=null)
			for (int i = 0; i < qlbChild.size(); i++) {
				Department qlbi = qlbChild.get(i);
				writeChilds(out, qlbi);
			}
		}
	}
}
