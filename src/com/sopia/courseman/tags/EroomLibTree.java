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

public class EroomLibTree extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(EroomLibTree.class);

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			EroomLib cts = (EroomLib) request.getAttribute("eroomLibTree");
			
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
			if(getItype()!= null && "1".equals(role) && getItype().equals("ra_2no")){//针对考场类别添加时，类别为ra_2no，超级管理员不能选择根节点
				itype ="ra";
			}
			if(getItype()!= null &&getItype().equals("cb_2")){
				itype="cb_2";
			}
			// writeChilds(out,cts) ;
			out
					.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n"
							+ "<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n"
							+ "	<script type=\"text/javascript\">\n" + "<!--\n");
			out.println("var d" + getDid() + " = new dTree('"
					+ itype
					+ "','"
					+ getIname()
					+ "',"
					+ (getIvalue() == null || "".equals(getIvalue()) ? 1
							: getIvalue()) + ", 'd" + getDid() + "');\n");
			
			if(getItype()!= null && getItype().equals("OP")){//OP  可操作类型
				writeChildsOP(out, cts , role);
			}else if(getItype()!= null && getItype().equals("cb_2")){
				writeChildsCb_2(out, cts);
			}else{
				writeChilds(out, cts);				
			} 
			out.println("document.write(d" + getDid() + ");\n"+
			"//-->\n"+
			"</script>");
		} catch (Exception ex) {
			logger.error("考试类别树，显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		EroomLib qlb = (EroomLib)obj;
		List<EroomLib> qlbChild= qlb.getChild();
		//String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId()+"&examRoom.valid=-1&examRoom.classid=-1";
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
			EroomLib qlbi = qlbChild.get(i);
			if(qlbi.getId()==this.getIid()){
				writeChilds2(out, qlbi );
			}else{
				writeChilds(out, qlbi );
			}
		}
	}
	
	public void writeChilds2(JspWriter out, Object obj) throws Exception {
		EroomLib qlb = (EroomLib)obj;
		List<EroomLib> qlbChild= qlb.getChild();
		//String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId();
		String href1 = getHref()==null||"".equals(getHref())?"":getHref()+qlb.getId()+"&examRoom.valid=-1&examRoom.classid=-1";
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
			EroomLib qlbi = qlbChild.get(i);
			writeChilds2(out, qlbi );
		} 
	}

	//用户角色不为1（超级管理员）时，可操作节点时根节点无超链接
	public void writeChildsOP(JspWriter out, Object obj ,String role) throws Exception {
		EroomLib qlb = (EroomLib)obj;
		List<EroomLib> qlbChild= qlb.getChild();
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
			EroomLib qlbi = qlbChild.get(i);
			writeChilds(out, qlbi );
		} 
	}
	
	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
		EroomLib qlb = (EroomLib)obj;
		List<EroomLib> qlbChild= qlb.getChild();
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
		for (int i = 0; i < qlbChild.size(); i++) {
			EroomLib qlbi = qlbChild.get(i);
			writeChildsCb_2(out, qlbi );
		} 
	}

}
