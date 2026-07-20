package com.sopia.courseman.tags;

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
import com.sopia.courseman.entities.EroomLib;

public class EroomLibSelect extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(ErbatchLibSelect.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			EroomLib qlb = (EroomLib) request.getAttribute("eroomLibTree");

			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession();  
			String role = session.getAttribute("roleid").toString();
			//当角色不为1（超级管理员）时，select的根不允许选用
			if(role.equals("1")) 
				writeChilds(out, qlb);
			else
				writeChilds_1no(out,qlb);				
 
		} catch (Exception ex) {
			logger.error("考场类别列表显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		EroomLib qlb = (EroomLib) obj;
		List<EroomLib> qlbChild = qlb.getChild();
			out.println("<option value='" + qlb.getId() + "'");
			if (getSelectid() == qlb.getId()) {
				out.println("selected = 'selected'");
			}
			out.println(">");
			for (int j = 0; j < qlb.getLevel(); j++) {
				out.println("--");
			}
			out.println(qlb.getName() + " </option>");
		for (int i = 0; i < qlbChild.size(); i++) {
			EroomLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}

	public void writeChilds_1no(JspWriter out, Object obj) throws Exception {
		EroomLib qlb = (EroomLib) obj;
		List<EroomLib> qlbChild = qlb.getChild();
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
			EroomLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}

}
