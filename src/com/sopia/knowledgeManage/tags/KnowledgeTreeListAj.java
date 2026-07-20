package com.sopia.knowledgeManage.tags;

import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.sopia.common.ElTag;
import com.sopia.knowledgeManage.entities.KnowledgeTree;
import com.sopia.schedule.entities.xialajibie.SelectLevel;

public class KnowledgeTreeListAj extends ElTag {
	private static final long serialVersionUID = 3119679319963664116L;
	private static final Log logger = LogFactory.getLog(KnowledgeTreeListAj.class);
	@SuppressWarnings("unchecked")
	public int doStartTag() {
		try {
			JspWriter out = pageContext.getOut();
			ServletRequest request = pageContext.getRequest();
			
			
			KnowledgeTree cts = null;
			
			cts = (KnowledgeTree) request.getAttribute("knowledgeTree");
			
			
			if (cts == null)
				return TagSupport.SKIP_BODY;
			
			out.println("<link rel=\"stylesheet\" type=\"text/css\" href=\"js/tree/wtree.css\" />\n"
							+ "<script type=\"text/javascript\" src=\"js/tree/wtree_KnowledgeTree.js\"></script>\n"
							+ "<script type=\"text/javascript\" src=\"js/jquery.js\"></script>\n"
							+ "	<script type=\"text/javascript\">\n"
							+ "\n"
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
			writeChilds(out, cts);
			out.println("w" + getDid() + ".doShow();" + "\n"+ "</script>");
		} catch (Exception ex) {
			logger.error("下拉选项列表ajax显示错误",ex);
		}
		return TagSupport.SKIP_BODY;
	}

	public void writeChilds(JspWriter out, Object obj) throws Exception {
		KnowledgeTree qlb = (KnowledgeTree) obj;
		List<KnowledgeTree> qlbChild = qlb.getChild();
		int pid = qlb.getParent()==null?0:qlb.getParent().getId();
		out.print("w" + getDid() + ".addNode(" + qlb.getId() + ",'" + qlb.getName() + "',"
				+ pid + ","+(qlb.getClassCount()>0?true:false)+",'"+qlb.getBh()+"',"+qlb.getLid()+","+qlb.getRid()+");\n");
		if (qlbChild != null)
			for (int i = 0; i < qlbChild.size(); i++) {
				KnowledgeTree qlbi = qlbChild.get(i);
				writeChilds(out, qlbi);
			}
	}


	// 用户角色不为1（超级管理员）时，可操作节点时根节点无超链接
	public void writeChildsOP(JspWriter out, Object obj, String role)
			throws Exception {
		KnowledgeTree qlb = (KnowledgeTree) obj;
		List<KnowledgeTree> qlbChild = qlb.getChild();
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
			KnowledgeTree qlbi = qlbChild.get(i);
			writeChildsOP(out, qlbi, role);
		}
	}

	public void writeChildsCb_2(JspWriter out, Object obj) throws Exception {
		KnowledgeTree qlb = (KnowledgeTree) obj;
		List<KnowledgeTree> qlbChild = qlb.getChild();
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
			out.print("d" + getDid() + ".add2(" + qlb.getId() + ","
					+ qlb.getParent().getId() + ",'" + qlb.getName() + "','"
					+ href1 + "','" + ischk + "','" + this.getTreeType()
					+ "');\n");
		}
		if (qlbChild != null)
			for (int i = 0; i < qlbChild.size(); i++) {
				KnowledgeTree qlbi = qlbChild.get(i);
				writeChildsCb_2(out, qlbi);
			}
	}
}
