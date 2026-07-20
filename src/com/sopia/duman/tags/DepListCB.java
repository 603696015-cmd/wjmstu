package com.sopia.duman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.duman.entities.Department;

public class DepListCB extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(DepListCB.class);
	private String attrname;
	private String inputname;
	public String getAttrname() {
		return attrname;
	}
	public void setAttrname(String attrname) {
		this.attrname = attrname;
	}
	public String getInputname() {
		return inputname;
	}
	public void setInputname(String inputname) {
		this.inputname = inputname;
	}
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Department cts = (Department) request.getAttribute("depTree");
			out.println("	<script type=\"text/javascript\">\n"+
		"<!--\n"+
		"var "+attrname+" = new dTree('"+attrname+"','"+inputname+"');\n");
			writeChilds(out,cts) ;
			out.println("document.write("+attrname+");\n"+
			"//-->\n"+
			"</script>");
		} catch (Exception ex) {
			logger.error("部门树（带checkbox)显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}
	public void writeChilds(JspWriter out, Object obj) throws Exception {
		Department qlb = (Department)obj;
		List<Department> qlbChild= qlb.getChild();
//		for (int j = 0; j < qlb.getLevel(); j++) {
//			out.println("--");
//		}
		if(qlb.getLevel()==0)
			//out.println( qlb.getName()+" <br>");
			if(!getRootAble()){
				out.print( attrname+".add("+qlb.getId()+",-1,'"+qlb.getName()+"');");
			}
			else{
				out.print( attrname+".add("+qlb.getId()+",-1,'"+qlb.getName()+"');");
			}
		else
		//out.println("<a href='"+getHref()+qlb.getId()+"'>"+qlb.getName()+"</a><br>");
			out.print( attrname+".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"' );");
		for (int i = 0; i < qlbChild.size(); i++) {
			Department qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}
	}
	/*public void writeChilds(JspWriter out, Object obj) throws Exception {
		CourseType qlb = (CourseType)obj;
		List<CourseType> qlbChild= qlb.getChild();
		for (int j = 0; j < qlb.getLevel(); j++) {
			out.println("--");
		}
		if(qlb.getLevel()==0&&!getRootAble())
			out.println( qlb.getName()+" <br>");
		else
		out.println("<a href='"+getHref()+qlb.getId()+"'>"+qlb.getName()+"</a><br>");
		for (int i = 0; i < qlbChild.size(); i++) {
			CourseType qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}
	}*/
}
