package com.sopia.duman.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.duman.entities.Department;
import com.sopia.duman.entities.ELUser;

public class DepUserList extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(DepUserList.class);
	private String attrname;
	private String inputname;
	
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Department cts = (Department) request.getAttribute(attrname);
			out.println("	<script type=\"text/javascript\">\n"+
		"<!--\n"+
		"var "+attrname+" = new dTree('"+attrname+"');\n");
			writeChilds(out,cts) ;
			out.println("document.write("+attrname+");\n"+
			"//-->\n"+
			"</script>");
		} catch (Exception ex) {
			logger.error("≤ø√≈ ˜œ‘ æ¥ÌŒÛ",ex);
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
				out.print(""+attrname+".add("+qlb.getId()+",-1,'"+qlb.getName()+"');");
			}
			else{
				out.print(""+attrname+".add("+qlb.getId()+",-1,'"+qlb.getName()+"','');");
			}
		else
		//out.println("<a href='"+getHref()+qlb.getId()+"'>"+qlb.getName()+"</a><br>");
			out.print(""+attrname+".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+getHref()+qlb.getId()+"');");
		List<ELUser> users = qlb.getUsers();
		if(users!=null)
		for (int i = 0; i < users.size(); i++) {
			out.print(""+attrname+".add("+qlb.getId()+users.get(i).getId()+","+qlb .getId()+",'"+users.get(i).getRealname()+"["+users.get(i).getUsername()+"]"+"-x--x-"+users.get(i).getId()+"-x--x-"+inputname+"','');");
		}
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
}
