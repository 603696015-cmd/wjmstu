package com.sopia.shebeipinggu.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.struts2.ServletActionContext;

import com.sopia.common.ElTag;
import com.sopia.duman.entities.Department;
import com.sopia.pfms.entities.ProductType;

public class InsuranceCategoryList_AJ extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;

	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession();
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			ProductType cts = (ProductType) request.getAttribute("ptypeTree");
			if (cts == null)
				return TagSupport.SKIP_BODY;
			// String role;
			// if(session.getAttribute("roleid") != null ){
			// role = session.getAttribute("roleid").toString();
			// }else{//前台未登录 游客显示
			// role = "1";
			// }
			// String itype="";
			// if(getItype()!= null && getItype().equals("ra_1no")){
			// itype =
			// (role.toString().equals("1")&&getItype().equals("ra_1no"))?"ra":"ra_1no";
			// }else{
			// itype =getItype();
			// }
			// if(role.equals("1")&&"OP".equals(getItype())==false){
			// itype="ra";
			// }
			// if(getItype()==null){
			// //setItype("ra1");
			// itype="ra1";
			// }
			// if("cb_2".equals(getItype())){
			// itype="cb_2";
			// }
			// out
			// .println("<link rel=\"stylesheet\" type=\"text/css\"
			// href=\"js/tree/dtree.css\" />\n"
			// + "<script type=\"text/javascript\"
			// src=\"js/tree/dtree_dep.js\"></script>\n"
			// + "<script type=\"text/javascript\"
			// src=\"js/jquery.js\"></script>\n"
			// + " <script type=\"text/javascript\">\n"
			// + "<!--\n"
			// + "var d"
			// + getDid()
			// + " = new dTree('"
			// + getItype()
			// + "','"
			// + getIname()
			// + "','"
			// + (getIvalue() == null || "".equals(getIvalue()) ? 1
			// : getIvalue())
			// + "','d"
			// + getDid()
			// + "','"
			// + getHref() + "');\n");
			out
					.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/wtree.css\" />\n"
							+ "<script type=\"text/javascript\" src=\"js/tree/wtree_dep.js\"></script>\n"
							+ "<script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n"
							+ "	<script type=\"text/javascript\">\n"
							+ "<!--\n"
							+ "var w"
							+ getDid()
							+ " = new WTree('w"
							+ getDid()
							+ "',"
							+ getRootAble()
							+ ",'"
							+ getItype()
							+ "','"
							+ getIname() + "','"+( getHref() == null || "".equals(getHref()) ? ""
									: getHref())
							+"');\n");
			// if(getItype()!= null && getItype().equals("OP")){//OP 可操作类型
			// writeChildsOP(out, cts , role);
			// }else if(getItype()!= null && getItype().equals("cb_2")){
			// writeChildsCb_2(out, cts);
			// }else{
			writeChilds(out, cts);
			// }
			// writeChilds(out,cts) ;
			out
					.println("w" + getDid() + ".doShow();" + "//-->\n"
							+ "</script>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		ProductType qlb = (ProductType) obj;
		List<ProductType> qlbChild = qlb.getChild();
//		String href1 = getHref() == null || "".equals(getHref()) ? ""
//				: getHref() + qlb.getId();
		int pid = qlb.getParent()==null?0:qlb.getParent().getId();
		out.print("w" + getDid() + ".addNode(" + qlb.getId() + ",'" + qlb.getName() + "',"
				+ pid + ");\n");
		if (qlbChild != null)
			for (int i = 0; i < qlbChild.size(); i++) {
				ProductType qlbi = qlbChild.get(i);
				writeChilds(out, qlbi);
			}
	}

	// public void writeChilds(JspWriter out, Object obj) throws Exception {
	// Department qlb = (Department) obj;
	// List<Department> qlbChild = qlb.getChild();
	// String href1 = getHref() == null || "".equals(getHref()) ? ""
	// : getHref() + qlb.getId();
	// if (qlb.getParent() == null || qlb.getParent().getId() == 0)
	// if (!getRootAble()) {
	// out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
	// + qlb.getName() + "');\n");
	// } else {
	// out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
	// + qlb.getName() + "','" + href1 + "');\n");
	// }
	// else
	// out.print("d" + getDid() + ".add2(" + qlb.getId() + ","
	// + qlb.getParent().getId() + ",'" + qlb.getName() + "','"
	// + href1 + "','0','no','" + qlb.getBh() + "');\n");
	// if (qlbChild != null)
	// for (int i = 0; i < qlbChild.size(); i++) {
	// Department qlbi = qlbChild.get(i);
	// writeChilds(out, qlbi);
	// }
	// }

	// 用户角色不为1（超级管理员）时，可操作节点时根节点无超链接
	public void writeChildsOP(JspWriter out, Object obj, String role)
			throws Exception {
		ProductType qlb = (ProductType) obj;
		List<ProductType> qlbChild = qlb.getChild();
		// qlbChild=new ArrayList<CourseType>();
		// if(qlbChild.size()<=0){
		// return;
		// }
		String href1 = getHref() == null || "".equals(getHref()) ? ""
				: getHref() + qlb.getId();
		if (qlb.getParent() == null || qlb.getParent().getId() == 0)
			if (!getRootAble()) {
				out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
						+ qlb.getName() + "');\n");
			} else {
				if (role.equals("1")) {
					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
							+ qlb.getName() + "','" + href1 + "');\n");
				} else {
					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
							+ qlb.getName() + "');\n");
				}
			}
		else
			out.print("d" + getDid() + ".add(" + qlb.getId() + ","
					+ qlb.getParent().getId() + ",'" + qlb.getName() + "','"
					+ href1 + "');\n");
		for (int i = 0; i < qlbChild.size(); i++) {
			ProductType qlbi = qlbChild.get(i);
			writeChildsOP(out, qlbi, role);
		}
	}

	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
		ProductType qlb = (ProductType) obj;
		List<ProductType> qlbChild = qlb.getChild();
		String href1 = getHref() == null || "".equals(getHref()) ? ""
				: getHref() + qlb.getId();
		if (qlb.getLevel() == 0)
			if (!getRootAble()) {
				out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
						+ qlb.getName() + "');\n");
			} else {
				out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
						+ qlb.getName() + "','" + getHref() + qlb.getId()
						+ "');\n");
			}
		else {
			// 判断是否已有该权限，来决定chkbox的是否选中
			List treeAllId = (List) pageContext.getRequest().getAttribute(
					"treeAllId");
			int ischk = 0;
			if (treeAllId != null) {
				if (treeAllId.contains(qlb.getId()) == true) {
					ischk = 1;
				}
			}
			// System.out.println(ischk);
			// System.out.println(this.getTreeType());
			out.print("d" + getDid() + ".add2(" + qlb.getId() + ","
					+ qlb.getParent().getId() + ",'" + qlb.getName() + "','"
					+ href1 + "','" + ischk + "','" + this.getTreeType()
					+ "');\n");
		}
		if (qlbChild != null)
			for (int i = 0; i < qlbChild.size(); i++) {
				ProductType qlbi = qlbChild.get(i);
				writeChildsCb_2(out, qlbi);
			}
	}
}
