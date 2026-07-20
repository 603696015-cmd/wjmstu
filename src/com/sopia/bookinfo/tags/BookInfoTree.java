package com.sopia.bookinfo.tags;


	import java.util.ArrayList;
	import java.util.List;

	import javax.servlet.ServletRequest;
	import javax.servlet.http.HttpServletRequest;
	import javax.servlet.http.HttpSession;
	import javax.servlet.jsp.JspWriter;
	import javax.servlet.jsp.tagext.TagSupport;

	import org.apache.struts2.ServletActionContext;

	import com.sopia.ElConstants;
import com.sopia.bookinfo.entities.BookTypeTree;
import com.sopia.bookman.entities.BookType;
	import com.sopia.cms.entities.ColumnTemplate;
	import com.sopia.cms.impl.TemplateDaoImpl;
	import com.sopia.common.ElTag;

	import com.sopia.courseman.entities.EroomLib;
import com.sopia.questionman.entities.QuestionLib;

	public class BookInfoTree extends ElTag {
		private static final long serialVersionUID = 3119679319963664116L;

		@SuppressWarnings("unchecked")
//	public int doStartTag() {
//		try {
//			JspWriter out = pageContext.getOut();
//			ServletRequest request = pageContext.getRequest();
//			BookTypeTree qlb = (BookTypeTree) request.getAttribute("bookTypeTree");
//			writeChilds(out,qlb ) ;
//			
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return TagSupport.SKIP_BODY;
//	}
//	public void writeChilds(JspWriter out, Object obj) throws Exception {
//		BookTypeTree qlb = (BookTypeTree)obj;
//		List<BookTypeTree> qlbChild= qlb.getChild();
//		for (int j = 0; j < qlb.getLevel(); j++) {
//			out.println("--");
//		}
//		if (qlb.getLevel()==0&&!getRootAble()) {
//			out.println(qlb.getName()+"<br>");
//			}
//		else
//		out.println("<a href='"+getHref()+qlb.getId()+"'>"+qlb.getName()+"</a><br>");
//		for (int i = 0; i < qlbChild.size(); i++) {
//			BookTypeTree qlbi = qlbChild.get(i);
//			writeChilds(out, qlbi );
//		}
//	}
		
//		public int doStartTag() {
//		try {
//			HttpServletRequest requset = ServletActionContext.getRequest();
//			HttpSession session = requset.getSession();
//			JspWriter out = pageContext.getOut();
//			ServletRequest request = pageContext.getRequest();
//			BookTypeTree cts = (BookTypeTree) request.getAttribute("bookTypeTree");
//			// 判断用户是否是超级管理员。
//			// itype=1时,当getItype()为ra（单选）时:根目录可操作
//			// itype！=1时,当getItype()为ra（单选）时:根目录不可操作。
//			// getIvalue().equals(null)在修改某些课程的时候不能用ra_1no
//			// hwc
//			String role;
//			if (session.getAttribute("roleid") != null) {
//				role = session.getAttribute("roleid").toString();
//			} else {// 前台未登录 游客显示
//				role = "1";
//			}
//			String itype = "";
//			// setItype(null);
//			// System.out.println(itype);
//			// System.out.println(getItype());
//			if (getItype() != null && getItype().equals("ra_1no")) {
//				itype = (role.equals("1") && getItype().equals("ra_1no")) ? "ra"
//						: "ra_1no";
//			} else {
//				itype = getItype();
//			}
//			// System.out.println(getItype());
//
//			if (role.equals("1") && "OP".equals(getItype()) == false) {
//				itype = "ra";
//			}
//			if (getItype() == null) {
//				// setItype("ra1");
//				itype = "ra1";
//			}
//			if ("cb_2".equals(getItype())) {
//				itype = "cb_2";
//			}
//			if ("ra_f".equals(getItype())) {
//				itype = "ra_f";
//			}
//			out
//					.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/dtree.css\" />\n"
//							+ "<script type=\"text/javascript\" src=\"js/tree/dtree.js\"></script>\n"
//							+ "	<script type=\"text/javascript\">\n" + "<!--\n");
//			out.println("var d"
//					+ getDid()
//					+ " = new dTree('"
//					+ itype
//					+ "','"
//					+ getIname()
//					+ "',"
//					+ (getIvalue() == null || "".equals(getIvalue()) ? 1
//							: getIvalue()) + ", 'd" + getDid() + "');\n");
//			if ("font".equals(getTreeType())) {
//				writeChildsTemplate(out, cts);
//			} else if (getItype() != null && getItype().equals("OP")) {// OP
//				// 可操作类型
//				writeChildsOP(out, cts, role);
//			} else if (getItype() != null && getItype().equals("cb_2")) {
//				writeChildsCb_2(out, cts);
//			} else {
//				writeChilds(out, cts);
//			}
//			out.println("document.write(d" + getDid() + ");\n" + "//-->\n"
//					+ "</script>");
//		} catch (Exception ex) {
//			ex.printStackTrace();
//		}
//		return TagSupport.SKIP_BODY;
//	}
//
//	public void writeChilds(JspWriter out, Object obj) throws Exception {
//		BookTypeTree qlb = (BookTypeTree) obj;
//		if (qlb != null) {
//			List<BookTypeTree> qlbChild = qlb.getChild();
//			String href1 = getHref() == null || "".equals(getHref()) ? ""
//					: getHref() + qlb.getId();
//			if (qlb.getLevel() == 0)
//				if (!getRootAble()) {
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//							+ qlb.getName() + "');\n");
//				} else {
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//							+ qlb.getName() + "','" + href1 + "');\n");
//				}
//			else
//				out.print("d" + getDid() + ".add(" + qlb.getId() + ","
//						+ qlb.getParent().getId() + ",'" + qlb.getName()
//						+ "','" + href1 + "');\n");
//			for (int i = 0; i < qlbChild.size(); i++) {
//				BookTypeTree qlbi = qlbChild.get(i);
//				if (qlbi.getId() == this.getIid()) {
//					writeChilds2(out, qlbi);
//				} else {
//					writeChilds(out, qlbi);
//				}
//			}
//		}
//	}
//
//	public void writeChilds2(JspWriter out, Object obj) throws Exception {
//		BookTypeTree qlb = (BookTypeTree) obj;
//		if (qlb != null) {
//			List<BookTypeTree> qlbChild = qlb.getChild();
//			String href1 = getHref() == null || "".equals(getHref()) ? ""
//					: getHref() + qlb.getId();
//			if (qlb.getLevel() == 0)
//				if (!getRootAble()) {
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//							+ qlb.getName() + "');\n");
//				} else {
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//							+ qlb.getName() + "','" + href1 + "');\n");
//				}
//			else
//				out.print("d" + getDid() + ".add(" + qlb.getId() + ","
//						+ qlb.getParent().getId() + ",'" + qlb.getName()
//						+ "','" + href1 + "','-3');\n");
//			for (int i = 0; i < qlbChild.size(); i++) {
//				BookTypeTree qlbi = qlbChild.get(i);
//				writeChilds2(out, qlbi);
//			}
//		}
//	}
//
//	// 用户角色不为1（超级管理员）时，可操作节点时根节点无超链接
//	public void writeChildsOP(JspWriter out, Object obj, String role)
//			throws Exception {
//		BookTypeTree qlb = (BookTypeTree) obj;
//		List<BookTypeTree> qlbChild = qlb.getChild();
//		// qlbChild=new ArrayList<BookTypeTree>();
//		// if(qlbChild.size()<=0){
//		// return;
//		// }
//		String href1 = getHref() == null || "".equals(getHref()) ? ""
//				: getHref() + qlb.getId();
//		if (qlb.getLevel() == 0)
//			if (!getRootAble()) {
//				out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//						+ qlb.getName() + "');\n");
//			} else {
//				if (role.equals("1")) {
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//							+ qlb.getName() + "','" + href1 + "');\n");
//				} else {
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//							+ qlb.getName() + "');\n");
//				}
//			}
//		else
//			out.print("d" + getDid() + ".add(" + qlb.getId() + ","
//					+ qlb.getParent().getId() + ",'" + qlb.getName() + "','"
//					+ href1 + "');\n");
//		for (int i = 0; i < qlbChild.size(); i++) {
//			BookTypeTree qlbi = qlbChild.get(i);
//			writeChilds(out, qlbi);
//		}
//	}
//
//	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
//		BookTypeTree qlb = (BookTypeTree) obj;
//		if (qlb != null) {
//			List<BookTypeTree> qlbChild = qlb.getChild();
//			String href1 = getHref() == null || "".equals(getHref()) ? ""
//					: getHref() + qlb.getId();
//			if (qlb.getLevel() == 0) {
//				if (!getRootAble()) {
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//							+ qlb.getName() + "');\n");
//				} else {
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//							+ qlb.getName() + "','" + href1 + "');\n");
//				}
//			} else {
//				// 判断是否已有该权限，来决定chkbox的是否选中
//				List treeAllId = (List) pageContext.getRequest().getAttribute(
//						"treeAllId");
//				int ischk = 0;
//				if (treeAllId.contains(qlb.getId()) == true) {
//					ischk = 1;
//				}
//				// System.out.println(ischk);
//				// System.out.println(this.getTreeType());
//				out.print("d" + getDid() + ".add2(" + qlb.getId() + ","
//						+ qlb.getParent().getId() + ",'" + qlb.getName()
//						+ "','" + href1 + "','" + ischk + "','"
//						+ this.getTreeType() + "');\n");
//			}
//			for (int i = 0; i < qlbChild.size(); i++) {
//				BookTypeTree qlbi = qlbChild.get(i);
//				writeChildsCb_2(out, qlbi);
//			}
//		}
//	}
//
//	public void writeChildsTemplate(JspWriter out, Object obj) throws Exception {
//		BookTypeTree qlb = (BookTypeTree) obj;
//		if (qlb != null) {
//			List<BookTypeTree> qlbChild = qlb.getChild();
//			String href1 = getHref() == null || "".equals(getHref()) ? ""
//					: getHref() + qlb.getId();
//			List<ColumnTemplate> t = new TemplateDaoImpl()
//					.listColumnTmpByType("kc");
//			Boolean flag=false;
//			for (ColumnTemplate columnTemplate : t) {
//				if (qlb.getId() == columnTemplate.getColumnId()) {
//					flag=true; 
//				}
//			}
//			if (qlb.getLevel() == 0) {
//				if (!getRootAble()) {
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
//							+ qlb.getName() + "');\n");
//				} else { 
//					if(flag){
//					out.print("d" + getDid() + ".add(" + qlb.getId()
//							+ ",-1,'" + qlb.getName()
//							+ "','elfrontman/cms/KC" + qlb.getId()
//							+ ".jsp');\n"); 
//				} else {
//					out.print("d" + getDid() + ".add(" + qlb.getId()
//							+ ",-1,'" + qlb.getName() + "','');\n"); 
//				}
//				}
//			} else { 
//				if(flag){ 
//					out.print("d" + getDid() + ".add(" + qlb.getId() + ","
//							+ qlb.getParent().getId() + ",'" + qlb.getName()
//							+ "','elfrontman/cms/KC" + qlb.getId() + ".jsp');\n");
//				 }else{
//					 out.print("d" + getDid() + ".add(" + qlb.getId() + ","
//								+ qlb.getParent().getId() + ",'" + qlb.getName()
//								+ "','');\n");
//				}
//			}
//			for (int i = 0; i < qlbChild.size(); i++) {
//				BookTypeTree qlbi = qlbChild.get(i);
//				writeChildsTemplate(out, qlbi);
//			}
//		}
//	}
	public int doStartTag() {
		try {
			HttpServletRequest requset = ServletActionContext.getRequest();
			HttpSession session = requset.getSession();
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			BookTypeTree cts = (BookTypeTree) request.getAttribute("bookTypeTree");
			// 判断用户是否是超级管理员。
			// itype=1时,当getItype()为ra（单选）时:根目录可操作
			// itype！=1时,当getItype()为ra（单选）时:根目录不可操作。
			// getIvalue().equals(null)在修改某些课程的时候不能用ra_1no
			// hwc
			String role;
			if (session.getAttribute("roleid") != null) {
				role = session.getAttribute("roleid").toString();
			} else {// 前台未登录 游客显示
				role = "1";
			}
			String itype = "";
			// setItype(null);
			// System.out.println(itype);
			// System.out.println(getItype());
			if (getItype() != null && getItype().equals("ra_1no")) {
				itype = (role.equals("1") && getItype().equals("ra_1no")) ? "ra"
						: "ra_1no";
			} else {
				itype = getItype();
			}
			// System.out.println(getItype());

			if (role.equals("1") && "OP".equals(getItype()) == false) {
				itype = "ra";
			}
			if (getItype() == null) {
				// setItype("ra1");
				itype = "ra1";
			}
			if ("cb_2".equals(getItype())) {
				itype = "cb_2";
			}
			if ("ra_f".equals(getItype())) {
				itype = "ra_f";
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
					+ (getIvalue() == null || "".equals(getIvalue()) ? 1
							: getIvalue()) + ", 'd" + getDid() + "');\n");
			if ("font".equals(getTreeType())) {
				writeChildsTemplate(out, cts);
			} else if (getItype() != null && getItype().equals("OP")) {// OP
				// 可操作类型
				writeChildsOP(out, cts, role);
			} else if (getItype() != null && getItype().equals("cb_2")) {
				writeChildsCb_2(out, cts);
			} else {
				writeChilds(out, cts);
			}
			out.println("document.write(d" + getDid() + ");\n" + "//-->\n"
					+ "</script>");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		BookTypeTree qlb = (BookTypeTree) obj;
		if (qlb != null) {
			List<BookTypeTree> qlbChild = qlb.getChild();
			String href1 = getHref() == null || "".equals(getHref()) ? ""
					: getHref() + qlb.getId();
			if (qlb.getLevel() == 0)
				if (!getRootAble()) {
					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
							+ qlb.getName() + "');\n");
				} else {
					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
							+ qlb.getName() + "','" + href1 + "');\n");
				}
			else
				out.print("d" + getDid() + ".add(" + qlb.getId() + ","
						+ qlb.getParent().getId() + ",'" + qlb.getName()
						+ "','" + href1 + "');\n");
			for (int i = 0; i < qlbChild.size(); i++) {
				BookTypeTree qlbi = qlbChild.get(i);
				if (qlbi.getId() == this.getIid()) {
					writeChilds2(out, qlbi);
				} else {
					writeChilds(out, qlbi);
				}
			}
		}
	}

	public void writeChilds2(JspWriter out, Object obj) throws Exception {
		BookTypeTree qlb = (BookTypeTree) obj;
		if (qlb != null) {
			List<BookTypeTree> qlbChild = qlb.getChild();
			String href1 = getHref() == null || "".equals(getHref()) ? ""
					: getHref() + qlb.getId();
			if (qlb.getLevel() == 0)
				if (!getRootAble()) {
					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
							+ qlb.getName() + "');\n");
				} else {
					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
							+ qlb.getName() + "','" + href1 + "');\n");
				}
			else
				out.print("d" + getDid() + ".add(" + qlb.getId() + ","
						+ qlb.getParent().getId() + ",'" + qlb.getName()
						+ "','" + href1 + "','-3');\n");
			for (int i = 0; i < qlbChild.size(); i++) {
				BookTypeTree qlbi = qlbChild.get(i);
				writeChilds2(out, qlbi);
			}
		}
	}

	// 用户角色不为1（超级管理员）时，可操作节点时根节点无超链接
	public void writeChildsOP(JspWriter out, Object obj, String role)
			throws Exception {
		BookTypeTree qlb = (BookTypeTree) obj;
		List<BookTypeTree> qlbChild = qlb.getChild();
		// qlbChild=new ArrayList<BookTypeTree>();
		// if(qlbChild.size()<=0){
		// return;
		// }
		String href1 = getHref() == null || "".equals(getHref()) ? ""
				: getHref() + qlb.getId();
		if (qlb.getLevel() == 0)
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
			BookTypeTree qlbi = qlbChild.get(i);
			writeChilds(out, qlbi);
		}
	}

	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
		BookTypeTree qlb = (BookTypeTree) obj;
		if (qlb != null) {
			List<BookTypeTree> qlbChild = qlb.getChild();
			String href1 = getHref() == null || "".equals(getHref()) ? ""
					: getHref() + qlb.getId();
			if (qlb.getLevel() == 0) {
				if (!getRootAble()) {
					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
							+ qlb.getName() + "');\n");
				} else {
					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
							+ qlb.getName() + "','" + href1 + "');\n");
				}
			} else {
				// 判断是否已有该权限，来决定chkbox的是否选中
				List treeAllId = (List) pageContext.getRequest().getAttribute(
						"treeAllId");
				int ischk = 0;
				if (treeAllId.contains(qlb.getId()) == true) {
					ischk = 1;
				}
				// System.out.println(ischk);
				// System.out.println(this.getTreeType());
				out.print("d" + getDid() + ".add2(" + qlb.getId() + ","
						+ qlb.getParent().getId() + ",'" + qlb.getName()
						+ "','" + href1 + "','" + ischk + "','"
						+ this.getTreeType() + "');\n");
			}
			for (int i = 0; i < qlbChild.size(); i++) {
				BookTypeTree qlbi = qlbChild.get(i);
				writeChildsCb_2(out, qlbi);
			}
		}
	}

	public void writeChildsTemplate(JspWriter out, Object obj) throws Exception {
		BookTypeTree qlb = (BookTypeTree) obj;
		if (qlb != null) {
			List<BookTypeTree> qlbChild = qlb.getChild();
			String href1 = getHref() == null || "".equals(getHref()) ? ""
					: getHref() + qlb.getId();
			List<ColumnTemplate> t = new TemplateDaoImpl()
					.listColumnTmpByType("kc");
			Boolean flag=false;
			for (ColumnTemplate columnTemplate : t) {
				if (qlb.getId() == columnTemplate.getColumnId()) {
					flag=true; 
				}
			}
			if (qlb.getLevel() == 0) {
				if (!getRootAble()) {
					out.print("d" + getDid() + ".add(" + qlb.getId() + ",-1,'"
							+ qlb.getName() + "');\n");
				} else { 
					if(flag){
					out.print("d" + getDid() + ".add(" + qlb.getId()
							+ ",-1,'" + qlb.getName()
							+ "','elfrontman/cms/KC" + qlb.getId()
							+ ".jsp');\n"); 
				} else {
					out.print("d" + getDid() + ".add(" + qlb.getId()
							+ ",-1,'" + qlb.getName() + "','');\n"); 
				}
				}
			} else { 
				if(flag){ 
					out.print("d" + getDid() + ".add(" + qlb.getId() + ","
							+ qlb.getParent().getId() + ",'" + qlb.getName()
							+ "','elfrontman/cms/KC" + qlb.getId() + ".jsp');\n");
				 }else{
					 out.print("d" + getDid() + ".add(" + qlb.getId() + ","
								+ qlb.getParent().getId() + ",'" + qlb.getName()
								+ "','');\n");
				}
			}
			for (int i = 0; i < qlbChild.size(); i++) {
				BookTypeTree qlbi = qlbChild.get(i);
				writeChildsTemplate(out, qlbi);
			}
		}
	}
	}

