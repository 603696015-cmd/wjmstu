package com.sopia.knowledgeman.tag;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.duman.entities.Department;

public class KltypeDepList extends TagSupport {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(KltypeDepList.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Department qlb = (Department) request.getAttribute("dtree");
			List<Department> deps = (List<Department>)request.getAttribute("deps");
			writeChilds(out,qlb ,deps) ;
		} catch (Exception ex) {
			logger.error("知识类别-部门列表显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	public void writeChilds(JspWriter out, Object obj,List<Department> deps) throws Exception {
		Department qlb = (Department)obj;
		List<Department> qlbChild= qlb.getChild();
		for (int j = 0; j < qlb.getLevel(); j++) {
			out.println("--");
		}
		boolean checked = false;
		if(null!=deps)
		for (int i = 0; i < deps.size(); i++) {
			if(deps.get(i).getId()==qlb.getId()){
				checked = true;
				break;
			}
		}
		/*if(qlb.getId()==0){
			out.println("<input type='checkbox' checked="+checked+" name='kltype.deps.id' value='"+qlb.getId()+"'/>"+qlb.getName()+"<br>");
		}
		else{*/
		if(checked)
			out.println("<input type='checkbox' checked='checked' name='kltype.deps.id' value='"+qlb.getId()+"'/>"+qlb.getName()+" <br>");
		else
			out.println("<input type='checkbox' name='kltype.deps.id' value='"+qlb.getId()+"'/>"+qlb.getName()+" <br>");
//		}
		for (int i = 0; i < qlbChild.size(); i++) {
			Department qlbi = qlbChild.get(i);
			writeChilds(out, qlbi,deps );
		}
	}
 
}
