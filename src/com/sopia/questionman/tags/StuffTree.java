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
import com.sopia.questionman.entities.StuffLib;

public class StuffTree extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(StuffTree.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession();
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			StuffLib qlb = (StuffLib) request.getAttribute("stuffTree");
			
			String role;
			if(session.getAttribute("roleid") != null ){
				role = session.getAttribute("roleid").toString();				
			}else{//前台未登录  游客显示
				role = "1";
			}
			
			String itype="";
//			setItype(null);
			if(getItype()!= null && getItype().equals("ra_1no")){
				 itype = (session.getAttribute("roleid").toString().equals("1")&&getItype().equals("ra_1no"))?"ra":"ra_1no";
			}else{
				itype =getItype();
			}
			
			if(session.getAttribute("roleid").toString().equals("1")&&"OP".equals(getItype())==false){
				itype="ra";
			}
			if(getItype()==null){ 
				//setItype("ra1");
				itype="ra1";
			}
			if("cb_2".equals(getItype())){
				itype="cb_2";
			}
			
			out
					.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n"
							+ "<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n"
							+ "	<script type=\"text/javascript\">\n" + "<!--\n");
			out.println("var d"
					+ getDid()
					+ " = new dTree('"
					+ getItype()
					+ "','"
					+ getIname()
					+ "',"
					+ (getIvalue() == null || "".equals(getIvalue()) ? qlb
							.getId() : getIvalue()) + ", 'd" + getDid()
					+ "');\n");
			if(getItype()!= null && getItype().equals("OP")){//OP  可操作类型
				writeChildsOP(out, qlb , role);
			}else if(getItype()!= null && getItype().equals("cb_2")){
				writeChildsCb_2(out, qlb);
			}else{
				writeChilds(out, qlb);				
			}
			//writeChilds(out, qlb);
			out.println("document.write(d" + getDid() + ");\n" + "//-->\n"
					+ "</script>");
		} catch (Exception ex) {
			logger.error("文件夹显示失败",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		StuffLib qlb = (StuffLib) obj;
		List<StuffLib> qlbChild = qlb.getChilds();

		String href1 = getHref() == null || "".equals(getHref()) ? ""
				: getHref() + qlb.getId();
		if (qlb.getLevel() == 0)
			if (!getRootAble()) {
				out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
						+ qlb.getTitle() + "');\n");
			} else {
				out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
						+ qlb.getTitle() + "','" + href1 + "');\n");
			}
		else
			out.print("d" + getDid() + ".add(" + qlb.getId() + ","
					+ qlb.getParent().getId() + ",'" + qlb.getTitle() + "','"
					+ href1 + "');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			StuffLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}
	
	public void writeChildsOP(JspWriter out, Object obj,String role) throws Exception {
		StuffLib qlb = (StuffLib) obj;
		List<StuffLib> qlbChild = qlb.getChilds();

		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getLevel()==0)
			if(!getRootAble()){
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getTitle()+"');\n");
			}
			else{
				if(role.equals("1")){
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getTitle()+"','"+href1+"');\n");
				}else{
					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getTitle()+"');\n");
				}
			}
		else{
			out.print("d" + getDid() + ".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getTitle()+"','"+href1+"');\n");
		}
		for (int i = 0; i < qlbChild.size(); i++) {
			StuffLib qlbi = qlbChild.get(i);
			writeChildsOP(out, qlbi,role);
		}
	}
	
	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
		StuffLib qlb = (StuffLib) obj;
		List<StuffLib> qlbChild = qlb.getChilds();

		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getLevel()==0)
			if(!getRootAble()){
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getTitle()+"');\n");
			}
			else{
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getTitle()+"','"+href1+"');\n");
			}
		else{
			//判断是否已有该权限，来决定chkbox的是否选中
			List treeAllId=(List)pageContext.getRequest().getAttribute("treeAllId");
			int ischk=0;
			if(treeAllId.contains(qlb.getId())==true){
				ischk=1;
			}
			out.print("d" + getDid() + ".add2("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getTitle()+"','"+href1+"','"+ischk+"','"+this.getTreeType()+"');\n");
		}
		for (int i = 0; i < qlbChild.size(); i++) {
			StuffLib qlbi = qlbChild.get(i);
			writeChildsCb_2(out, qlbi);
		}
	}
}
/**
 * 
 * for (int j = 0; j < qlb.getLevel(); j++) { out.println("--"); } if
 * (qlb.getLevel() == 0 && !getRootAble()) { out.println(qlb.getName() + "<br>
 * "); } else out.println("<a href='" + getHref() + qlb.getId() + "'>" +
 * qlb.getName() + "</a><br>
 * "); for (int i = 0; i < qlbChild.size(); i++) { ExamPaperLib qlbi =
 * qlbChild.get(i); writeChilds(out, qlbi); }
 */
