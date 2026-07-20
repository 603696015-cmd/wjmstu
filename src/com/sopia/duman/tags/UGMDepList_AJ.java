package com.sopia.duman.tags;

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
import com.sopia.duman.entities.Department;

public class UGMDepList_AJ extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(UGMDepList_AJ.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			HttpServletRequest requset = ServletActionContext.getRequest();
//			HttpSession session = requset.getSession();
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			Department cts = (Department) request.getAttribute("depTree");
			if(cts==null ) return TagSupport.SKIP_BODY;
			out
			.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n"
					+ "<script type=\"text/javascript\" src=\"js/tree/ugmdtree_dep.js\"></script>\n"
					+ "<script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n"
					+ "	<script type=\"text/javascript\">\n" + "<!--\n"+"var d"+getDid()+" = new dTree('"+  getItype()
					+ "','"
					+ getIname()
					+ "','"
					+ (getIvalue() == null || "".equals(getIvalue()) ? 1
							: getIvalue()) + "','d"+getDid()+"','"+getHref()+"');\n");
				writeChilds(out, cts);				
			out.println("document.write(d"+getDid()+");\n"+
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
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getParent()==null||qlb.getParent().getId()==0)
			if(!getRootAble()){
				out.print("d"+getDid()+".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
			}
			else{
				out.print("d"+getDid()+".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
			}
		else
			out.print("d"+getDid()+".add2("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"','0','no','"+qlb.getBh()+"');\n");
		if(qlbChild!=null)
		for (int i = 0; i < qlbChild.size(); i++) {
			Department qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}
	}
}
