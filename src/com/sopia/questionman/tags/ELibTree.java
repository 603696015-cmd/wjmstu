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

public class ELibTree extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(ELibTree.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ExamPaperLib qlb = (ExamPaperLib) request.getAttribute("eplTree");
			
			//判断用户是否是超级管理员。 
			//itype=1时,当getItype()为ra（单选）时:根目录可操作
			//itype！=1时,当getItype()为ra（单选）时:根目录不可操作。
			//getIvalue().equals(null)在修改某些课程的时候不能用ra_1no
			//hwc
			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession();
			String role = session.getAttribute("roleid").toString();
			String itype ;
			if(getItype()!= null && getItype().equals("ra_1no")){
			 itype = (session.getAttribute("roleid").toString().equals("1")&&getItype().equals("ra_1no"))?"ra":"ra_1no";
			}else{
				itype =getItype();
			}
			if(getItype()==null){
				setItype("OP");
			}
			if(session.getAttribute("roleid").toString().equals("1")&&"OP".equals(getItype())==false){
				itype="ra";
			}
			if(getItype()==null){
				//setItype("ra1");
				itype="ra1";
			}
			if(getItype().equals("cb_2")){
				itype="cb_2";
			}
			if("ra_f".equals(getItype())){
				itype="ra_f";
			}
			out
					.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n"
							+ "<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n"
							+ "	<script type=\"text/javascript\">\n" + "<!--\n");
			out.println("var d"
					+ getDid()
					+ " = new dTree('"
					+ itype
					+ "','"
					+ getIname()
					+ "',"
					+ (getIvalue() == null || "".equals(getIvalue()) ? qlb.getId()
							: getIvalue()) + ", 'd" + getDid() + "');\n");
			if(getItype()!= null && getItype().equals("OP")){//OP  可操作类型
				writeChildsOP(out, qlb , role);
			}else if(getItype()!= null && getItype().equals("cb_2")){
				writeChildsCb_2(out, qlb);
			}else{
				writeChilds(out, qlb);				
			}
			out.println("document.write(d" + getDid() + ");\n" + "//-->\n"
					+ "</script>");
		} catch (Exception ex) {
			logger.error("试卷类别库 显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		ExamPaperLib qlb = (ExamPaperLib) obj;
		List<ExamPaperLib> qlbChild = qlb.getChild();
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getLevel()==0)
			if(!getRootAble()){
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
			}
			else{
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
			}
		else
			out.print("d" + getDid() + ".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			ExamPaperLib qlbi = qlbChild.get(i);
			if(qlbi.getId()==this.getIid()){
				writeChilds2(out, qlbi );
			}else{
				writeChilds(out, qlbi );
			}
		}	
	}
	
	public void writeChilds2(JspWriter out, Object obj) throws Exception {
		ExamPaperLib qlb = (ExamPaperLib) obj;
		List<ExamPaperLib> qlbChild = qlb.getChild();
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getLevel()==0)
			if(!getRootAble()){
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
			}
			else{
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
			}
		else
			out.print("d" + getDid() + ".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"','-3');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			ExamPaperLib qlbi = qlbChild.get(i);
			writeChilds2(out, qlbi );
		}	
	}

	//用户角色不为1（超级管理员）时，可操作节点时根节点无超链接
	public void writeChildsOP(JspWriter out, Object obj ,String role) throws Exception {
		ExamPaperLib qlb = (ExamPaperLib) obj;
		List<ExamPaperLib> qlbChild = qlb.getChild();

		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getLevel()==0)
			if(!getRootAble()){
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
			}
			else{
//				if(role.equals("1")){
//					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
//				}else{
//					out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
//				}
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
			}
		else
			out.print("d" + getDid() + ".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			ExamPaperLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		}	
	}
	
	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
		ExamPaperLib qlb = (ExamPaperLib) obj;
		List<ExamPaperLib> qlbChild = qlb.getChild();

		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		if(qlb.getLevel()==0)
			if(!getRootAble()){
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"');\n");
			}
			else{
				out.print("d" + getDid() + ".add("+qlb.getId()+",-1,'"+qlb.getName()+"','"+href1+"');\n");
			}
		else{
			//判断是否已有该权限，来决定chkbox的是否选中
			List treeAllId=(List)pageContext.getRequest().getAttribute("treeAllId");
			int ischk=0;
			if(treeAllId.contains(qlb.getId())==true){
				ischk=1;
			}
			out.print("d" + getDid() + ".add2("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"','"+ischk+"','"+this.getTreeType()+"');\n");
		}
			//out.print("d" + getDid() + ".add("+qlb.getId()+","+qlb.getParent().getId()+",'"+qlb.getName()+"','"+href1+"');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			ExamPaperLib qlbi = qlbChild.get(i);
			writeChildsCb_2(out, qlbi );
		}	
	}
}
/**

for (int j = 0; j < qlb.getLevel(); j++) {
	out.println("--");
}
if (qlb.getLevel() == 0 && !getRootAble()) {
	out.println(qlb.getName() + "<br>");
} else
	out.println("<a href='" + getHref() + qlb.getId() + "'>"
			+ qlb.getName() + "</a><br>");
for (int i = 0; i < qlbChild.size(); i++) {
	ExamPaperLib qlbi = qlbChild.get(i);
	writeChilds(out, qlbi);
}
*/