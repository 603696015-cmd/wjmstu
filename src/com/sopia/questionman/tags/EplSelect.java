package com.sopia.questionman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.ServletActionContext;

import com.sopia.common.ElTag;
import com.sopia.questionman.entities.ExamPaperLib;

public class EplSelect extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(EplSelect.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ExamPaperLib obj = (ExamPaperLib) request.getAttribute("eplTree");

			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession();
			String role = session.getAttribute("roleid").toString();
			//当角色不为1（超级管理员）时，select的根不允许选用
			if(role.equals("1")) 
				writeChilds(out, obj);
			else
				writeChilds_1no(out, obj);				
		} catch (Exception ex) {
			logger.error("试卷类别下拉框显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		ExamPaperLib qlb = (ExamPaperLib) obj;
		List<ExamPaperLib> qlbChild = qlb.getChild(); 
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
			ExamPaperLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}
	
	public void writeChilds_1no(JspWriter out, Object obj) throws Exception {
		ExamPaperLib qlb = (ExamPaperLib) obj;
		List<ExamPaperLib> qlbChild = qlb.getChild();
		if (qlb.getId() < 0) {
			out.println("<optgroup label='" +qlb.getName()+ "'></optgroup>");
		} else {
			out.println("<option value='" + qlb.getId() + "'");
			if(getSelectid()==qlb.getId()){
				out.println("selected = 'selected'");
			}
					out.println(">");
			for (int j = 0; j < qlb.getLevel(); j++) {
				out.println("--");
			}
			out.println(qlb.getName() + " </option>");
		}
		for (int i = 0; i < qlbChild.size(); i++) {
			ExamPaperLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}
}
